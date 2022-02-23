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
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.R.drawable.ic_contagem
import br.com.ernanilima.jinventario.adapter.StockCountRecyclerAdapter
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeStockCountBinding
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.warning
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.service.component.SwipeHelper
import br.com.ernanilima.jinventario.util.Filtro
import br.com.ernanilima.jinventario.view.dialog.camera.CameraZXingDialogFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockCountFragment : Fragment() {

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
    private var dfCameraZXing: CameraZXingDialogFragment? = null

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
        // 'nav_stock_count' exibe outra informacao quando esta no fragment de contagem de estoque
        navigationView.menu.findItem(R.id.nav_stock_count).apply {
            idStockCount.observe(viewLifecycleOwner, { id ->
                setIcon(ic_contagem)
                title = "Contagem N: $id"
            })
            setOnMenuItemClickListener {
                drawerLayout.closeDrawers()
                true
            }

            // eh definido como false para evitar que exista navegacao no item,
            // isso eh alterado ao abrir a nova contagem
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
        dfCameraZXing = CameraZXingDialogFragment.novoDialog().setFragment(this)

        binding.include.btnCameraScanner.setOnClickListener { openCameraScanner() }
        binding.include.btnOk.setOnClickListener { newItem() }
    }

    private fun setupListener() {
        stockCountViewModel.countResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                ResultTypeLocal.LIST_STOCK_COUNT_ITEM -> {
                    setupRecyclerAdapter()
                }
                ResultTypeLocal.NEW_STOCK_COUNT_ITEM -> {
                    stockCountRecyclerAdapter.notifyItemInserted(0)
                    clearFields()
                }
                ResultTypeLocal.UPDATE_STOCK_COUNT_ITEM -> {
                }
                ResultTypeLocal.DELETE_STOCK_COUNT_ITEM -> {
                    SimpleDialog(QuestionDialog(parentFragmentManager).apply {
                        setMessage(getString(R.string.s_dialog_msg_delete_count_item, stockCountViewModel.stockCountItem.id.toString()))
                        setNegativeButton {
                            stockCountRecyclerAdapter.notifyItemChanged(stockCountViewModel.stockCountItem.index)
                        }
                        setPositiveButton {
                            stockCountRecyclerAdapter.notifyItemRemoved(stockCountViewModel.stockCountItem)
                            stockCountViewModel.deleteItem()
                        }
                    }).show()
                }
            }
        })
    }

    private fun setupRecyclerAdapter() {
        // RECYCLER ADAPTER
        _stockCountRecyclerAdapter = StockCountRecyclerAdapter(stockCountViewModel, stockCountViewModel.listItemStockCount())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        binding.recyclerView.adapter = stockCountRecyclerAdapter

        // SWIPE - DESLIZAR ITEM
        val swipeHelper = SwipeHelper()
        swipeHelper.setRecyclerView(binding.recyclerView)
    }

    private fun openCameraScanner() {
        isPermissionSalid().ifTrue {
            println("ABRIR CAMERA SCANNER")
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

    private fun clearFields() {
        binding.include.fieldBarcode.requestFocus()
        binding.include.fieldBarcode.setText("")
        binding.include.fieldUnitPrice.setText("")
        binding.include.fieldNumberOfBoxes.setText("")
        binding.include.fieldNumberPerBox.setText("")
    }

    private fun validate(): Boolean {
        var isValid = true
        // ...
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