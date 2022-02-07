//package br.com.ernanilima.jinventario.interfaces;
//
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.util.List;
//
//import br.com.ernanilima.jinventario.model.ItemContagem;
//import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
//import br.com.ernanilima.jinventario.service.validation.ValidarPermissoes;
//import br.com.ernanilima.jinventario.view.ContagemFragment;
//import br.com.ernanilima.jinventario.view.dialog.camera.CameraZXingDialogFragment;
//
//public interface IContagem {
//
//    /** Usado no fragment
//     * {@link ContagemFragment} */
//    interface IView {
//        Fragment requireParentFragment();
//        void setIdParaExibirNoTitulo(long idContagem);
//        void setRecyclerAdapter(List<ItemContagem> lsItensContagem);
//        void atualizarRecyclerAdapter();
//        void setItemAlterado(ItemContagem mItemContagem);
//        void setItemExcluido(ItemContagem mItemContagem);
//        ValidarPermissoes getValidarPermissoes();
//        CameraZXingDialogFragment getCameraZXingDialogFragment();
//        TextInputLayout getCampoCodbarras();
//        TextInputLayout getCampoPrecoUn();
//        TextInputLayout getCampoQtdDeCaixa();
//        TextInputLayout getCampoQtdPorCaixa();
//        void desativarUsoDaCamera();
//        void desativarInformarPreco();
//    }
//
//    /** Usado no presenter
//     * {@link ContagemPresenter} */
//    interface IPresenter extends IResultadoDialog, IResultadoCameraScanner, IResultadoPermissao {
//        void setCodigoContagem(Long idContagem);
//        void adicionarItemColetado();
//        void abrirCameraScanner();
//        void alterarItemColetado(ItemContagem mItemContagem);
//        void excluirItemColetado(ItemContagem mItemContagem);
//        void toolbarCompartilhar();
//        void popularDadosConfiguracao();
//    }
//}
