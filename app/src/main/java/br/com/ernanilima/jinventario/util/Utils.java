package br.com.ernanilima.jinventario.util;

import android.widget.EditText;

import java.util.Collections;
import java.util.List;

public class Utils {

    public static void limparCampo(Object... node) {
        for (Object nodes : node) {
            List<Object> lsNodes = Collections.singletonList(nodes);
            for (Object n : lsNodes) {
                if (n instanceof EditText)
                    ((EditText) n).setText("");
            }
        }
    }
}
