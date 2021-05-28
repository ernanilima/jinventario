package br.com.ernanilima.jinventario.view.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ernanilima.jinventario.R;

public abstract class ToastPersonalizado {

    private static Toast toast;
    private static TextView mensagem;

    public static void sucesso(Context context, String mensagemSucesso) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_sucesso, null);
        mensagem = view.findViewById(R.id.campo_msg_erro);
        mensagem.setText(mensagemSucesso);
        toast = new Toast(view.getContext());
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /** Exibe toast personalizado para erro
     * @param context Context - context da tela onde ocorreu o erro
     * @param mensagemErro String - mensgem de erro para exibir no toast */
    public static void erro(Context context, String mensagemErro) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_erro, null);
        mensagem = view.findViewById(R.id.campo_msg_erro);
        mensagem.setText(mensagemErro);
        toast = new Toast(view.getContext());
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
