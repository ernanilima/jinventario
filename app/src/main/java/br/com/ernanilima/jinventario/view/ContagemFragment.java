package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.R;

public class ContagemFragment extends Fragment {

    public static final String CODIGO_CONTAGEM = "CodigoContagem";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // argumento recebido de outro fragment, basicamente recebe o codigo de uma nova contagem ou de uma existente
        getParentFragmentManager().setFragmentResultListener(this.getClass().getCanonicalName(), this,
                (requestKey, result) -> System.out.println(result.getLong(CODIGO_CONTAGEM)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contagem, container, false);
    }

}
