package br.com.ernanilima.jinventario.ui.home

import br.com.ernanilima.jinventario.model.StockCount

interface IHome {
    /** [HomeViewModel] */
    interface IViewModel {
        /**
         * Criar nova contagem de estoque
         */
        fun newCount()

        /**
         * Lista de contagens para popular o recycler view
         * @return List<StockCount>
         */
        fun listStockCount(): List<StockCount>

        /**
         * Alterar ao clicar no icone do recycler view
         * @param stockCount StockCount
         */
        fun updateCount(stockCount: StockCount)

        /**
         * Apaga contagem de estoque
         * @param stockCount StockCount
         */
        fun deleteCount(stockCount: StockCount? = null)
    }
}