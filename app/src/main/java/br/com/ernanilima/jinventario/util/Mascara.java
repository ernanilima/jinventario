package br.com.ernanilima.jinventario.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascara {

    /** Faz com que o campo exiba valores monetarios
     * @param editText EditText - campo que deseja inserir a mascara */
    public static void valorMonetario(EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    editText.removeTextChangedListener(this);

                    // limpa pontos e visgulas caso tenha
                    String stringLimpa = s.toString().replaceAll("[R$,.]", "");

                    // converte a string para double
                    double valorDouble = Double.parseDouble(stringLimpa);
                    // formata a string para o formato de valor/preco
                    // divide o valor/preco para garantir que o valor sempre vai comecar por centavos
                    // Ex... 1 / 100 = 0,01
                    String formadoValor = Formatar.VALOR_RS.format((valorDouble / 100));

                    // adiciona o valor/preco formatado
                    editText.setText(formadoValor);
                    editText.setSelection(formadoValor.length());

                    editText.addTextChangedListener(this);
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }
        });

    }
}
