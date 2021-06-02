package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.interfaces.IInicioApp;

public class InicioAppPresenter implements IInicioApp.InicioAppPresenter {

    private IInicioApp.InicioAppView vInicioApp;

    /** Construtor
     * @param vInicioApp IInicioApp.InicioAppView - view(fragment) do inicio do app */
    public InicioAppPresenter(IInicioApp.InicioAppView vInicioApp) {
        this.vInicioApp = vInicioApp;
    }

    @Override
    public void novaContagem() {
        System.out.println("NOVA CONTAGEM INICIADA");
    }
}
