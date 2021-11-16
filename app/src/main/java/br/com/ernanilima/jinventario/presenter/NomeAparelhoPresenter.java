package br.com.ernanilima.jinventario.presenter;

import javax.inject.Inject;

import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth;
import br.com.ernanilima.jinventario.interfaces.INomeAparelho;
import br.com.ernanilima.jinventario.model.User;
import br.com.ernanilima.jinventario.repository.UserRepository;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class NomeAparelhoPresenter implements INomeAparelho.IPresenter {

    private INomeAparelho.IView vNomeAparelho;
    private IFirebaseAuth iFirebaseAuth;
//    private DaoSession daoSession;
//    private NomeAparelhoDao dNomeAparelho;
    private UserRepository userDao;

    /** Construtor
     * @param vNomeAparelho INomeAparelho.IView - view(fragment) do nome do aparelho */
    @Inject
    public NomeAparelhoPresenter(INomeAparelho.IView vNomeAparelho, UserRepository userDao) {
        this.vNomeAparelho = vNomeAparelho;
        this.iFirebaseAuth = new FirebaseAuth();
        this.userDao = userDao;
//        // GREENDAO
//        this.daoSession = ((BaseApplication) this.vNomeAparelho.getApplication()).getSessao();
//        this.dNomeAparelho = daoSession.getNomeAparelhoDao();
    }

    @Override
    public void gravarNomeDoAparelho() {
        if (validarCampo()) {
            User user = userDao.findByEmail(iFirebaseAuth.getUserEmail());
            user.setDeviceName(vNomeAparelho.getCampoNomeAparelho().getEditText().getText().toString());
            userDao.update(user);

//            // gera um model com o nome do aparelho
//            NomeAparelho mNomeAparelho = new NomeAparelho(null, vNomeAparelho.getCampoNomeAparelho().getEditText().getText().toString());
//
//            // grava o model
//            dNomeAparelho.save(mNomeAparelho);

            // navega para a tela de login
            NavegacaoApp.abrirTelaActivityApp(vNomeAparelho.getApplication().getBaseContext());

            // finaliza a view para informar o nome do aparelho
            vNomeAparelho.finish();
        }
    }

    private boolean validarCampo() {
        return ValidarCampo.vazio(vNomeAparelho.getCampoNomeAparelho(), MensagensAlerta.NOME_APARELHO_INVALIDO.getMsg()) &&
                ValidarCampo.qtdCaracteres(vNomeAparelho.getCampoNomeAparelho(), 3);
    }
}
