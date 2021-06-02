package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.interfaces.IInicioApp;

public class InicioAppPresenter implements IInicioApp.Presenter {

    private IInicioApp.View vInicioApp;

    /** Construtor
     * @param vInicioApp IInicioApp.View - view(fragment) do inicio do app */
    public InicioAppPresenter(IInicioApp.View vInicioApp) {
        this.vInicioApp = vInicioApp;
    }

    @Override
    public void novaContagem() {
        System.out.println("NOVA CONTAGEM INICIADA");
    }
}
