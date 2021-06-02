package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IInicioApp;
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter;

public class InicioAppFragment extends Fragment implements IInicioApp.InicioAppView {

    private IInicioApp.InicioAppPresenter pInicioApp;
    private AppCompatButton btn_novacontagem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_inicio_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pInicioApp = new InicioAppPresenter(this);

        //INICIALIZA
        // nome_local = nome_no_xml
        btn_novacontagem = view.findViewById(R.id.btn_novacontagem);

        // ACAO DE BOTOES
        btn_novacontagem.setOnClickListener(v -> pInicioApp.novaContagem());

    }
}
