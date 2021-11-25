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
import br.com.ernanilima.jinventario.adapter.ContagensEstoqueRecyclerAdapter
import br.com.ernanilima.jinventario.databinding.FragmentAppHomeBinding
import br.com.ernanilima.jinventario.databinding.NavHeaderBinding
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.service.component.SwipeHelper
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentAppHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private var raContagensEstoque: ContagensEstoqueRecyclerAdapter? = null

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

        // header do drawer layout
        val bindingHe = NavHeaderBinding.inflate(layoutInflater)
        bindingHe.campoNavNomeaparelho.text = homeViewModel.user.deviceName
        bindingHe.campoNavEmail.text = homeViewModel.user.email
        val menuItem = navigationView.menu.findItem(R.id.nav_contagem)
        menuItem.setIcon(R.drawable.ic_novo) // icone no item de menu
        menuItem.title = binding.btnNewCount.text // atribui no item de menu o mesmo texto do botao

        menuItem.setOnMenuItemClickListener { // ao clicao no botao nav_contagem
            drawerLayout.closeDrawers() // fecha o drawer layout
            newCount() // abre o dialog para criar nova contagem ou nao
            true
        }.setChecked(false).isCheckable = false // eh definido como false para evitar que exista navegacao no item, isso eh alterado ao abrir a nova contagem
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        binding.btnNewCount.setOnClickListener { newCount() }

        //SETs
        raContagensEstoque = ContagensEstoqueRecyclerAdapter(homeViewModel.listCounts())
        raContagensEstoque!!.setInicioAppPresenter(homeViewModel)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
        binding.recyclerView.adapter = raContagensEstoque
        val swipeHelper = SwipeHelper() // classe de slider no item
        swipeHelper.setRecyclerView(binding.recyclerView)
    }

    private fun setupListener() {
        homeViewModel.arguments.observe(viewLifecycleOwner, { result ->
            println("ARGUMENTO EH $result")
        })

        homeViewModel.countResult.observe(viewLifecycleOwner, { result ->
            println("RESULTADO DA CONTAGEM EH $result")
        })
    }

    private fun newCount() {
        SimpleDialog(QuestionDialog(parentFragmentManager).apply {
            setMessage("Nova contagem?")
            setPositiveButton {
                homeViewModel.newCount()
            }
        }).show()
    }
}