package br.com.ernanilima.jinventario.interfaces;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public interface IEsqueceuSenha {
    interface EsqueceuSenhaView {
        TextInputLayout getCampoEmail();
    }

    interface EsqueceuSenhaPresenter {
        void gerarNovaSenha(View view);
    }
}
