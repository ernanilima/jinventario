package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IConfiguracao;
import br.com.ernanilima.jinventario.presenter.ConfiguracaoPresenter;

public class ConfiguracaoFragment extends Fragment implements IConfiguracao.IView {

    private IConfiguracao.IPresenter pConfiguracao;
    private SwitchCompat swch_ativar_camera;
    private AppCompatButton btn_gravar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_configuracao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pConfiguracao = new ConfiguracaoPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        swch_ativar_camera = view.findViewById(R.id.swch_ativar_camera);
        btn_gravar = view.findViewById(R.id.btn_gravar);

        // ACAO DE BOTOES
        btn_gravar.setOnClickListener(v -> pConfiguracao.gravarConfiguracao());

    }

    @Override
    /** Retorna se configuracao foi selecionada */
    public boolean getConfigCameraScanner() {
        return swch_ativar_camera.isChecked();
    }
}
