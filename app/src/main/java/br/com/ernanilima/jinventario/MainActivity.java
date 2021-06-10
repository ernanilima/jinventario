package br.com.ernanilima.jinventario;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.ernanilima.jinventario.service.validation.ValidarPermissoes;

public class MainActivity extends AppCompatActivity {
    // exibe todos os fragment da tela de login
    // tudo configurado no xml

    private String[] permissoes = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ValidarPermissoes.permissoes(this, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // verifica as permissoes ao abrir o activity
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                // se alguma permissao nao foi dada
                alertaValidacaoPermissao();
            }
        }
    }

    /** Exibe um dialog caso exista alguma permissao que nao foi dada */
    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão Negada");
        builder.setMessage("Para utilizar o aplicativo é necessário aceitar as permissões");
        builder.setPositiveButton("Finalizar", (dialog, which) -> finish());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}