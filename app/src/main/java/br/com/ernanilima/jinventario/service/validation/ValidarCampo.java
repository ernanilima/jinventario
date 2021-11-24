package br.com.ernanilima.jinventario.service.validation;

import android.view.inputmethod.EditorInfo;

import com.google.android.material.textfield.TextInputLayout;

@Deprecated
public class ValidarCampo {

    /** Verifica se o campo eh vazio e invalido
     * Invalido seria campo de e-mail nao combinar com e-mail
     * @param campo TextInputLayout - campo a ser validado
     * @param mensagemErro String - mensagem para o erro
     * @return boolean - true se nenhum erro encontrado */
    @Deprecated
    public static boolean vazio(TextInputLayout campo, String mensagemErro) {
        String stringDoCampo = campo.getEditText().getText().toString();

        if (stringDoCampo.equals("") || stringDoCampo.equals("0") || tipoCampoValido(campo)) {
            // com erro
            campo.setErrorEnabled(true);
            campo.setError(mensagemErro);
            campo.getEditText().requestFocus();
            return false;
        }

        // sem erro
        campo.setErrorEnabled(false);
        return true;
    }

    /** Verifica o tipo do campo e se ele eh valido ou nao
     * @param campo TextInputLayout - campo a ser validado
     * @return boolean - true se o campo nao for valido */
    @Deprecated
    private static boolean tipoCampoValido(TextInputLayout campo) {
        // se campo eh do tipo email,
        // se campo nao combina com o regex que eh do tipo de email
        if (campo.getEditText().getInputType() == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1 &&
                !campo.getEditText().getText().toString().matches("^([\\w\\.])+@([a-zA-Z0-9])+(\\.([a-zA-Z])+)+$")) {

            // se combinar com if, se tiver erro
            return true;
        }

        // nenhum erro
        return false;
    }
}
