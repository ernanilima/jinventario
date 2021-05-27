package br.com.ernanilima.jinventario.service.validation;

import android.view.inputmethod.EditorInfo;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;

public class ValidarCampo {

    /** Verifica se o campo eh vazio e invalido
     * Invalido seria campo de email nao combinar com email
     * @param campo TextInputLayout - campo a ser validado
     * @param mensagemErro String - mensagem para o erro
     * @return boolean - true se nenhum erro encontrado */
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

    /** Verifica se o campo tem a quantidade minima do parametro
     * @param campo TextInputLayout - campo a ser validado
     * @param qtdMinima int - quantidade minima aceita
     * @return boolean - true se nenhum erro encontrado */
    public static boolean qtdCaracteres(TextInputLayout campo, int qtdMinima) {
        String stringDoCampo = campo.getEditText().getText().toString();

        if (stringDoCampo.length() < qtdMinima) {
            // com erro
            campo.setErrorEnabled(true);
            campo.setError(MensagemAlerta.getMsgMinCaracteres(qtdMinima));
            campo.getEditText().requestFocus();
            return false;
        }

        // sem erro
        campo.setErrorEnabled(false);
        return true;
    }

    /** Verifica se senhas sao iguais
     * Usado para cadastrar ou mudar a senha
     * @param campoSenha1 TextInputLayout - campo de senha 1
     * @param campoSenha2 TextInputLayout - campo de senha 2
     * @param mensagemErro String - mensagem de erro
     * @return boolean - true se nenhum erro encontrado */
    public static boolean senhasIguais(TextInputLayout campoSenha1, TextInputLayout campoSenha2, String mensagemErro) {
        String senha1 = campoSenha1.getEditText().getText().toString();
        String senha2 = campoSenha2.getEditText().getText().toString();

        if (!senha1.equals("") && !senha1.equals(senha2)) {
            // com erro
            campoSenha1.setErrorEnabled(true);
            campoSenha1.setError(mensagemErro);
            campoSenha2.setErrorEnabled(true);
            campoSenha2.setError(mensagemErro);
            campoSenha1.getEditText().requestFocus();
            return false;
        }

        // sem erro
        campoSenha1.setErrorEnabled(false);
        campoSenha2.setErrorEnabled(false);
        return true;
    }
}
