package br.com.ernanilima.jinventario.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Collections;
import java.util.List;

public class Utils {

    /** Limpa os campos passados no parametro
     * @param node Object... - lista de campos */
    public static void limparCampo(Object... node) {
        for (Object nodes : node) {
            List<Object> lsNodes = Collections.singletonList(nodes);
            for (Object n : lsNodes) {
                if (n instanceof EditText)
                    ((EditText) n).setText("");
            }
        }
    }

    /** Converte o e-mail do usuario para que seja aceito no firebase
     * @param email String - e-mail do usuario
     * @return String - e-mail do usuario convertido */
    public static String converter(String email) {
        // realtime databese do firebase nao aceita '.', '#', '$', '[', ou ']'
        // abaixo o e-mail eh convertido para que seja aceito e sirva para gravar
        // o e-mail como chave no banco do firebase
        return email
                .replace(".", "P") // P = ponto
                .replace("#", "H") // H = hashtag
                .replace("$", "D") // D = dolar
                .replace("[", "AC") // AC = abre colchete
                .replace("]", "FC"); // FC = fecha colchete
    }

    public static void desativarOpcao(View field) {
        field.setVisibility(View.INVISIBLE);
        // largura do campo, nao pode ser 0(zero)
        field.getLayoutParams().width = 1;
        field.getLayoutParams().height = 1;
        // margem do campo
        ViewGroup.MarginLayoutParams paramsField = (ViewGroup.MarginLayoutParams) field.getLayoutParams();
        paramsField.topMargin = 0; paramsField.rightMargin = 0; paramsField.bottomMargin = 0; paramsField.leftMargin = 0;
    }
}
