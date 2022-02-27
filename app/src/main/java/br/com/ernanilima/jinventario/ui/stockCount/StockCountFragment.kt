package br.com.ernanilima.jinventario.ui.stockCount

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.R.drawable.ic_current_count
import br.com.ernanilima.jinventario.adapter.StockCountRecyclerAdapter
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.data.result.ResultTypeSettings
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeStockCountBinding
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.warning
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.service.component.SwipeHelper
import br.com.ernanilima.jinventario.ui.camera.CameraScanner
import br.com.ernanilima.jinventario.ui.camera.MLKit
import br.com.ernanilima.jinventario.ui.camera.ZXing
import br.com.ernanilima.jinventario.util.Filtro
import br.com.ernanilima.jinventario.util.Utils
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockCountFragment : Fragment(), IStockCount.IFragment {

    private var _binding: FragmentAppHomeStockCountBinding? = null
    private val binding get() = _binding!!
    private val stockCountViewModel: StockCountViewModel by viewModels()
    private var _registerForResult: ActivityResultLauncher<Array<String>>? = null
    private val registerForResult get() = _registerForResult!!

    private var _idStockCount = MutableLiveData<Long>()
    private val idStockCount get() = _idStockCount

    private var _stockCountRecyclerAdapter: StockCountRecyclerAdapter? = null
    private val stockCountRecyclerAdapter get() = _stockCountRecyclerAdapter!!

    private val permission = Manifest.permission.CAMERA

    private var _zxing: ZXing? = null
    private val zxing get() = _zxing!!

    companion object { const val ID_STOCK_COUNT: String = "ID_STOCK_COUNT" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // argumento recebido de outro fragment, basicamente recebe o codigo de uma nova contagem ou de uma existente
        parentFragmentManager.setFragmentResultListener(
            this.javaClass.simpleName, this, {
                _: String?, result: Bundle ->
                _idStockCount.postValue(result.getLong(ID_STOCK_COUNT))
                stockCountViewModel.setIdStockCount(result.getLong(ID_STOCK_COUNT))
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAppHomeStockCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        // captura o drawer layout
        val drawerLayout = requireActivity().findViewById(R.id.drawer_layout) as DrawerLayout
        // captura o navigation view
        val navigationView = requireActivity().findViewById(R.id.nav_view) as NavigationView

        // captura o item do menu do drawer layout
        navigationView.menu.findItem(R.id.viewStockCount).apply {
            // preenche o titulo quando popular o codigo da contagem recebida por parametro
            idStockCount.observe(viewLifecycleOwner, { id ->
                setIcon(ic_current_count)
                title = getString(R.string.s_menu_current_count, id.toString())
                val actionBar: ActionBar = (requireActivity() as AppCompatActivity).supportActionBar!!
                actionBar.title = title
            })
            // ao clicao no botao 'Contagem N:*'
            setOnMenuItemClickListener {
                drawerLayout.closeDrawers()
                true
            }

            // exibe o item como selecionado na navegacao do drawer layout
            isCheckable = true
            isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        // VALIDAR PERMISSAO (CAMERA)
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        _registerForResult = registerForActivityResult(RequestMultiplePermissions()) {
                permissions -> permissions.containsValue(true).ifTrue { openCameraScanner() }
        }

        // CAMERA TIPO ZXING
        _zxing = ZXing(this)

        binding.include.btnCameraScanner.setOnClickListener { openCameraScanner() }
        binding.include.btnOk.setOnClickListener { newItem() }
        binding.include.fieldNumberPerBox.setOnClickListener { newItem() }

        // REQUISICOES DO CAMPO CODIGO DE BARRAS
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.include.layoutBarcode)
            setMin(1)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO PRECO UNITARIO
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.include.layoutUnitPrice)
            setMaskMonetary(true)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO QUANTIDADE DE CAIXAS
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.include.layoutNumberOfBoxes)
            setMin(1)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO QUANTIDADE POR CAIXA
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.include.layoutNumberPerBox)
            setMin(1)
            setRequired(true)
        }.build()
    }

    private fun setupListener() {
        // CONFIGURACAO
        stockCountViewModel.settingsResult.observe(viewLifecycleOwner, { result ->
            (result.contains(ResultTypeSettings.DONT_USE_PRICE)).ifTrue {
                Utils.disableOption(binding.include.layoutUnitPrice)
            }

            (result.contains(ResultTypeSettings.DONT_USE_CAMERA)).ifTrue {
                Utils.disableOption(binding.include.btnCameraScanner)
            }

            (result.contains(ResultTypeSettings.CAMERA_MLKIT)).ifTrue {
                CameraScanner(MLKit(this).apply {
                    setPositiveResult { barcode ->
                        setResultCameraScanner(barcode)
                    }
                }).show()
            }

            (result.contains(ResultTypeSettings.CAMERA_ZXING)).ifTrue {
                CameraScanner(zxing.apply {
                    setPositiveResult { barcode ->
                        setResultCameraScanner(barcode)
                    }
                }).show()
            }
        })

        // CONTAGEM
        stockCountViewModel.countResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                ResultTypeLocal.LIST_STOCK_COUNT_ITEM -> {
                    setupRecyclerAdapter()
                }
                ResultTypeLocal.NEW_STOCK_COUNT_ITEM -> {
                    stockCountRecyclerAdapter.notifyItemInserted(0)
                    binding.recyclerView.scrollToPosition(0)
                    clearFields()
                }
                ResultTypeLocal.UPDATE_STOCK_COUNT_ITEM -> {
                }
//                ResultTypeLocal.DELETE_STOCK_COUNT_ITEM -> {
//                    SimpleDialog(QuestionDialog(parentFragmentManager).apply {
//                        setMessage(getString(R.string.s_dialog_msg_delete_count_item, stockCountViewModel.stockCountItem.id.toString()))
//                        setNegativeButton {
//                            stockCountRecyclerAdapter.notifyItemChanged(stockCountViewModel.stockCountItem.index)
//                        }
//                        setPositiveButton {
//                            stockCountRecyclerAdapter.notifyItemRemoved(stockCountViewModel.stockCountItem)
//                            stockCountViewModel.deleteItem()
//                        }
//                    }).show()
//                }
            }
        })
    }

    private fun setupRecyclerAdapter() {
        // RECYCLER ADAPTER
        _stockCountRecyclerAdapter = StockCountRecyclerAdapter(this, stockCountViewModel.listItemStockCount())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        binding.recyclerView.adapter = stockCountRecyclerAdapter

        // SWIPE - DESLIZAR ITEM
        val swipeHelper = SwipeHelper()
        swipeHelper.setRecyclerView(binding.recyclerView)
    }

    private fun openCameraScanner() {
        isPermissionSalid().ifTrue {
            stockCountViewModel.openCameraScanner()
        }
    }

    private fun newItem() {
        validate().ifTrue {
            val stockCountItem = StockCountItem()
            stockCountItem.barcode = binding.include.fieldBarcode.text.toString()
            stockCountItem.unitPrice = Filtro.pDouble(binding.include.fieldUnitPrice.text.toString())
            stockCountItem.numberOfBoxes = binding.include.fieldNumberOfBoxes.text.toString()
            stockCountItem.numberPerBox = binding.include.fieldNumberPerBox.text.toString()
            stockCountViewModel.newItem(stockCountItem)
        }
    }

    override fun updateItem(stockCountItem: StockCountItem) {

    }

    /**
     * Solicita para o usuario a confirmacao para deletar item
     * @param stockCountItem StockCountItem - item para deletar
     */
    override fun deleteItemBySwipe(stockCountItem: StockCountItem) {
        SimpleDialog(QuestionDialog(parentFragmentManager).apply {
            setMessage(getString(R.string.s_dialog_msg_delete_count_item, stockCountItem.id.toString()))
            setNegativeButton {
                stockCountRecyclerAdapter.notifyItemChanged(stockCountItem.index)
            }
            setPositiveButton {
                stockCountRecyclerAdapter.notifyItemRemoved(stockCountItem)
                stockCountViewModel.deleteItem(stockCountItem)
            }
        }).show()
    }

    private fun setResultCameraScanner(barcode: String) {
        val context = requireParentFragment().requireContext()
        DeviceHelper.showKeyboard(binding.include.fieldBarcode, context)
        binding.include.fieldBarcode.requestFocus()
        binding.include.fieldBarcode.setText(barcode)
    }

    private fun clearFields() {
        binding.include.fieldBarcode.requestFocus()
        Utils.clearFields(binding.include.fieldBarcode, binding.include.fieldUnitPrice,
            binding.include.fieldNumberOfBoxes, binding.include.fieldNumberPerBox)
        Utils.clearErrors(binding.include.layoutBarcode, binding.include.layoutUnitPrice,
            binding.include.layoutNumberOfBoxes, binding.include.layoutNumberPerBox)
    }

    private fun validate(): Boolean {
        var isValid = true

        // campo codigo de barras
        Validator.apply {
            isEmpty(binding.include.fieldBarcode.text.toString()).ifTrue {
                showError(binding.include.layoutBarcode, requireActivity())
                isValid = !it
            }
        }

        // campo codigo de barras
        val settings: Settings? = stockCountViewModel.settings
        (settings != null && settings.showPrice).ifTrue {
            Validator.apply {
                isEmpty(binding.include.fieldUnitPrice.text.toString()).ifTrue {
                    showError(binding.include.layoutUnitPrice, requireActivity())
                    isValid = !it
                }
            }
        }

        // campo quantidade de caixas
        Validator.apply {
            isEmpty(binding.include.fieldNumberOfBoxes.text.toString()).ifTrue {
                showError(binding.include.layoutNumberOfBoxes, requireActivity())
                isValid = !it
            }
        }

        // campo quantidade por caixa
        Validator.apply {
            isEmpty(binding.include.fieldNumberPerBox.text.toString()).ifTrue {
                showError(binding.include.layoutNumberPerBox, requireActivity())
                isValid = !it
            }
        }

        return isValid
    }

    private fun isPermissionSalid(): Boolean {
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
        warning(context, getString(R.string.msg_without_camera_permission))
        return false
    }
}