package br.com.ernanilima.jinventario.ui.home

import br.com.ernanilima.jinventario.interfaces.IResultadoDialog
import br.com.ernanilima.jinventario.model.ContagemEstoque

interface IHome {
    interface IViewModel : IResultadoDialog {
        fun newCount()
        fun listCounts(): List<ContagemEstoque>
        fun updateCount(contagemEstoque: ContagemEstoque)
        fun deleteCount(contagemEstoque: ContagemEstoque)
    }
}