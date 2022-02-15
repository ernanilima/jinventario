package br.com.ernanilima.jinventario.ui.stockCount

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.R.drawable.ic_contagem
import br.com.ernanilima.jinventario.adapter.StockCountRecyclerAdapter
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeStockCountBinding
import br.com.ernanilima.jinventario.service.component.SwipeHelper
import br.com.ernanilima.jinventario.service.validation.ValidarPermissoes
import br.com.ernanilima.jinventario.view.dialog.camera.CameraZXingDialogFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockCountFragment : Fragment() {

    private var _binding: FragmentAppHomeStockCountBinding? = null
    private val binding get() = _binding!!
    private val stockCountViewModel: StockCountViewModel by viewModels()

    private var _stockCountRecyclerAdapter: StockCountRecyclerAdapter? = null
    private val stockCountRecyclerAdapter get() = _stockCountRecyclerAdapter!!

    private val permissions = arrayOf(Manifest.permission.CAMERA)
    private var vPermissoes: ValidarPermissoes? = null
    private var dfCameraZXing: CameraZXingDialogFragment? = null

    companion object { val ID_STOCK_COUNT: String = "ID_STOCK_COUNT" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // argumento recebido de outro fragment, basicamente recebe o codigo de uma nova contagem ou de uma existente
        parentFragmentManager.setFragmentResultListener(
            this.javaClass.simpleName, this, {
                _: String?, result: Bundle -> stockCountViewModel.setIdStockCount(result.getLong(ID_STOCK_COUNT))
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
            setIcon(ic_contagem)
            // title = binding.btnNewCount.text
            setOnMenuItemClickListener {
                drawerLayout.closeDrawers()
                true
            }

            // eh definido como false para evitar que exista navegacao no item,
            // isso eh alterado ao abrir a nova contagem
            isCheckable = false
            isChecked = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        // CLASSE PARA VALIDAR PERMISSOES
        vPermissoes = ValidarPermissoes.novaValidacao().setFragment(this).setPermissoes(this.permissions)

        // CAMERA TIPO ZXING
        dfCameraZXing = CameraZXingDialogFragment.novoDialog().setFragment(this)

        _stockCountRecyclerAdapter = StockCountRecyclerAdapter(stockCountViewModel, stockCountViewModel.listItemStockCount())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        binding.recyclerView.adapter = stockCountRecyclerAdapter

        // SWIPE - DESLIZAR ITEM
        val swipeHelper = SwipeHelper()
        swipeHelper.setRecyclerView(binding.recyclerView)
    }

    private fun setupListener() {

    }
}