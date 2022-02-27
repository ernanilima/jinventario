package br.com.ernanilima.jinventario.ui.home

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
import br.com.ernanilima.jinventario.adapter.HomeRecyclerAdapter
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.*
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeBinding
import br.com.ernanilima.jinventario.databinding.NavHeaderBinding
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.service.component.SwipeHelper
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.App.Companion.toStockCountFragment
import br.com.ernanilima.jinventario.ui.stockCount.StockCountFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), IHome.IFragment {

    private var _binding: FragmentAppHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private var _homeRecyclerAdapter: HomeRecyclerAdapter? = null
    private val homeRecyclerAdapter get() = _homeRecyclerAdapter!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAppHomeBinding.inflate(inflater, container, false)
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
        // captura o header do drawer layout
        val bindingHeader = NavHeaderBinding.bind(navigationView.getHeaderView(0))

        // adiciona os dados no header do drawer layout
        bindingHeader.fieldDeviceName.text = homeViewModel.user.deviceName
        bindingHeader.fieldEmail.text = homeViewModel.user.email

        // captura o item do menu do drawer layout
        // 'viewStockCount' exibe outra informacao quando esta no fragment de contagem de estoque
        navigationView.menu.findItem(R.id.viewStockCount).apply {
            icon = binding.btnNewCount.icon
            title = binding.btnNewCount.text
            setOnMenuItemClickListener {
                drawerLayout.closeDrawers()
                newCount() // abre o dialog para que seja confirmado a criacao de uma nova contagem
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
        // ACAO DE BOTAO
        binding.btnNewCount.setOnClickListener { newCount() }

        // ADAPTER
        _homeRecyclerAdapter = HomeRecyclerAdapter(this, homeViewModel.listStockCount())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        binding.recyclerView.adapter = homeRecyclerAdapter

        // SWIPE - DESLIZAR ITEM
        val swipeHelper = SwipeHelper()
        swipeHelper.setRecyclerView(binding.recyclerView)
    }

    private fun setupListener() {
        homeViewModel.countResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                NEW_STOCK_COUNT,
                UPDATE_STOCK_COUNT -> {
                    parentFragmentManager.setFragmentResult(StockCountFragment::class.simpleName!!, homeViewModel.arguments)
                    toStockCountFragment(this)
                }
            }
        })
    }

    /**
     * Exibe um dialog para que o usuario confirme a criacao de uma nova contagem de estoque
     */
    private fun newCount() {
        SimpleDialog(QuestionDialog(parentFragmentManager).apply {
            setMessage(getString(R.string.s_dialog_msg_new_count))
            setPositiveButton {
                homeViewModel.newCount()
            }
        }).show()
    }

    /**
     * Altera contagem
     * @param stockCount StockCount - contatem para alterar
     */
    override fun updateCount(stockCount: StockCount) {
        homeViewModel.updateCount(stockCount)
    }

    /**
     * Solicita para o usuario a confirmacao para deletar a contagem
     * @param stockCount StockCount - contatem para deletar
     */
    override fun deleteCountBySwipe(stockCount: StockCount) {
        SimpleDialog(QuestionDialog(parentFragmentManager).apply {
            setMessage(getString(R.string.s_dialog_msg_delete_count, stockCount.id.toString()))
            setNegativeButton {
                homeRecyclerAdapter.notifyItemChanged(stockCount.index)
            }
            setPositiveButton {
                homeRecyclerAdapter.notifyItemRemoved(stockCount)
                homeViewModel.deleteCount(stockCount)
            }
        }).show()
    }
}