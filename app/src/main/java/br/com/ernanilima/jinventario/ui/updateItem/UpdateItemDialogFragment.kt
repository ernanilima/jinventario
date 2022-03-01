package br.com.ernanilima.jinventario.ui.updateItem

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeStockCountInsertBinding
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.ui.camera.CameraScanner
import br.com.ernanilima.jinventario.ui.camera.MLKit
import br.com.ernanilima.jinventario.ui.camera.ZXing
import br.com.ernanilima.jinventario.util.Filtro
import br.com.ernanilima.jinventario.util.Format
import br.com.ernanilima.jinventario.util.Utils

typealias PositiveResultCallback = (StockCountItem) -> Unit

class UpdateItemDialogFragment constructor(
    val fragment: Fragment
) : DialogFragment() {

    private lateinit var binding: FragmentAppHomeStockCountInsertBinding
    private lateinit var aDialog: AlertDialog
    private lateinit var registerForResult: ActivityResultLauncher<Array<String>>
    private lateinit var zxing: ZXing

    val settings: Settings? = AppActivity.settings

    private val permission = Manifest.permission.CAMERA

    private var stockCountItem: StockCountItem? = null
    private var positiveResultCallback: PositiveResultCallback? = null

    /**
     * @param stockCountItem StockCountItem - item coletado para alterar
     */
    fun setStockCountItem(stockCountItem: StockCountItem): UpdateItemDialogFragment {
        this.stockCountItem = stockCountItem
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("Alteração")
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Confirmar", null)

        // ativar botao neutro, abre a camera scanner
        if (settings == null || settings.cameraScanner)
            alertDialog.setNeutralButton("Camera Como Scanner", null)

        // botao neutro e confirmar sao implementados em outro metodo
        // dessa forma o botao nao fecha o dialog sem validar os campos

        return alertDialog.create();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAppHomeStockCountInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()

        // atribuicoes para o dialog
        aDialog = dialog as AlertDialog
        aDialog.setView(binding.root)
        isCancelable = false
        aDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupUi() {
        // VALIDAR PERMISSAO (CAMERA)
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        registerForResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                permissions -> if (permissions.containsValue(true)) { openCameraScanner() }
        }

        // CAMERA TIPO ZXING
        zxing = ZXing(this)

        // insere os dados que devem ser editados
        stockCountItem.also {
            it?.barcode?.let { bc -> binding.fieldBarcode.setText(bc) }
            it?.unitPrice?.let { pun -> binding.fieldUnitPrice.setText(Format.PRICE_PTBR.format(pun).replace("R$ ", "")) }
            it?.numberOfBoxes?.let { nofbxs -> binding.fieldNumberOfBoxes.setText(nofbxs) }
            it?.numberPerBox?.let { nperbx -> binding.fieldNumberPerBox.setText(nperbx) }
        }

        // REQUISICOES DO CAMPO CODIGO DE BARRAS
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutBarcode)
            setMin(1)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO PRECO UNITARIO
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutUnitPrice)
            setMaskMonetary(true)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO QUANTIDADE DE CAIXAS
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutNumberOfBoxes)
            setMin(1)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO QUANTIDADE POR CAIXA
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutNumberPerBox)
            setMin(1)
            setRequired(true)
        }.build()
    }

    override fun onStart() {
        super.onStart()
        // desabilita os botoes que nao devem ser exibidos
        Utils.disableOption(binding.btnCameraScanner)
        Utils.disableOption(binding.btnOk)

        // ativar metodo para botao neutro, abre a camera scanner
        if (settings == null || settings.cameraScanner) {
            val buttonNeutral: Button = aDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            buttonNeutral.setOnClickListener { openCameraScanner() }
        }

        // desativa a opcao de inserir o preco do produto
        if (settings == null || !settings.showPrice)
            Utils.disableOption(binding.layoutUnitPrice)

        // metodo para o botao positivo
        val buttonPositive: Button = aDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        buttonPositive.setOnClickListener { confirm() }
    }

    private fun openCameraScanner() {
        if (isPermissionValid()) {
            if (settings == null || settings.cameraScannerMlkit) {
                CameraScanner(MLKit(this).apply {
                    setPositiveResult { barcode ->
                        setResultCameraScanner(barcode)
                    }
                }).show()
            } else if (settings.cameraScannerZxing) {
                CameraScanner(zxing.apply {
                    setPositiveResult { barcode ->
                        setResultCameraScanner(barcode)
                    }
                }).show()
            }
        }
    }

    private fun setResultCameraScanner(barcode: String) {
        val context = requireParentFragment().requireContext()
        DeviceHelper.showKeyboard(binding.fieldBarcode, context)
        binding.fieldBarcode.requestFocus()
        binding.fieldBarcode.setText(barcode)
    }

    private fun confirm() {
        if (validate()) {
            stockCountItem!!.also { item ->
                item.barcode = binding.fieldBarcode.text.toString()
                item.unitPrice = Filtro.pDouble(binding.fieldUnitPrice.text.toString())
                item.numberOfBoxes = binding.fieldNumberOfBoxes.text.toString()
                item.numberPerBox = binding.fieldNumberPerBox.text.toString()
            }
            positiveResultCallback?.invoke(stockCountItem!!)
            dismiss() // fecha o dialog
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        // campo codigo de barras
        Validator.apply {
            if (isEmpty(binding.fieldBarcode.text.toString())) {
                showError(binding.layoutBarcode, requireActivity())
                isValid = false
            }
        }

        // campo preco unitario
        if (settings != null && settings.showPrice) {
            Validator.apply {
                if (isEmpty(binding.fieldUnitPrice.text.toString())) {
                    showError(binding.layoutUnitPrice, requireActivity())
                    isValid = false
                }
            }
        }

        // campo quantidade de caixas
        Validator.apply {
            if (isEmpty(binding.fieldNumberOfBoxes.text.toString())) {
                showError(binding.layoutNumberOfBoxes, requireActivity())
                isValid = false
            }
        }

        // campo quantidade por caixa
        Validator.apply {
            if (isEmpty(binding.fieldNumberPerBox.text.toString())) {
                showError(binding.layoutNumberPerBox, requireActivity())
                isValid = false
            }
        }

        return isValid
    }

    private fun isPermissionValid(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // se a api do aparelho for igual ou maior que 23

            // verifica se a permissao ja foi dada
            val isPermission: Boolean = ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) == PackageManager.PERMISSION_GRANTED

            // se a permissao foi dada, continua
            if (isPermission)
                return true

            // solicita permissao para o usuario
            registerForResult.launch(arrayOf(permission))
        } else {
            // se for uma versao mais antiga nao precisa de solicitacao de permissao
            // basta informar a permissao no manifest
            return true
        }

        // exibe um snackbar quando nao tem a permissao
        val context = requireParentFragment().requireContext()
        SnackbarCustom.warning(context, getString(R.string.msg_without_camera_permission))
        //Snackbar
        //    .make(binding.root, getString(R.string.msg_without_camera_permission), Snackbar.LENGTH_LONG)
        //    .show()
        return false
    }

    fun setPositiveResult(positiveResultCallback: PositiveResultCallback): UpdateItemDialogFragment {
        this.positiveResultCallback = positiveResultCallback
        return this
    }

    fun show() {
        this.show(fragment.parentFragmentManager, this::class.java.simpleName)
    }
}
