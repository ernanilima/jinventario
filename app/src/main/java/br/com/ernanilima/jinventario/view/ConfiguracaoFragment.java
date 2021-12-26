package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IConfiguracao;
import br.com.ernanilima.jinventario.presenter.ConfiguracaoPresenter;

public class ConfiguracaoFragment extends Fragment implements IConfiguracao.IView {

    private IConfiguracao.IPresenter pConfiguracao;
    private CheckBox chbx_informar_preco_produto, chbx_camera_scanner;
    private RadioGroup radio_group;
    private RadioButton radio_camera_mlkit, radio_camera_zxing;
    private AppCompatButton btn_gravar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pConfiguracao = new ConfiguracaoPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        chbx_informar_preco_produto = view.findViewById(R.id.chbx_preco_produto);
        chbx_camera_scanner = view.findViewById(R.id.chbx_camera_scanner);
        radio_group = view.findViewById(R.id.radio_group);
        radio_camera_mlkit = view.findViewById(R.id.radio_camera_mlkit);
        radio_camera_zxing = view.findViewById(R.id.radio_camera_zxing);
        btn_gravar = view.findViewById(R.id.btn_save);

        // ACAO DE BOTOES
        chbx_camera_scanner.setOnCheckedChangeListener((buttonView, isChecked) -> usarCameraScanner(isChecked));
        btn_gravar.setOnClickListener(v -> pConfiguracao.gravarConfiguracao());

        pConfiguracao.popularDadosConfiguracao();
    }

    /** Usado para ativar/desativar radio button de tipo de camera
     * @param isChecked boolean - checkbox de usar camera ativado/desativado */
    private void usarCameraScanner(boolean isChecked) {
        radio_camera_mlkit.setEnabled(isChecked);
        radio_camera_zxing.setEnabled(isChecked);
    }

    @Override
    /** Retorna se configuracao foi selecionada */
    public boolean getConfigInformarPreco() {
        return chbx_informar_preco_produto.isChecked();
    }

    @Override
    /** Recebe a configuracao do campo */
    public void setConfigInformarPreco(boolean b) {
        chbx_informar_preco_produto.setChecked(b);
    }

    @Override
    /** Retorna se configuracao foi selecionada */
    public boolean getConfigCameraScanner() {
        return chbx_camera_scanner.isChecked();
    }

    @Override
    /** Recebe a configuracao do campo */
    public void setConfigCameraScanner(boolean b) {
        chbx_camera_scanner.setChecked(b);
    }

    @Override
    public boolean getConfigUsarCameraMlkit() {
        return radio_camera_mlkit.isChecked();
    }

    @Override
    public void setConfigUsarCameraMlkit(boolean b) {
        radio_camera_mlkit.setChecked(b);
    }

    @Override
    public boolean getConfigUsarCameraZxing() {
        return radio_camera_zxing.isChecked();
    }

    @Override
    public void setConfigUsarCameraZxing(boolean b) {
        radio_camera_zxing.setChecked(b);
    }
}
