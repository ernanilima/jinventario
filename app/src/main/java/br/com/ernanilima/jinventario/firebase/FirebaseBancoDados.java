package br.com.ernanilima.jinventario.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ernanilima.jinventario.dto.ItemContagemDTO;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.service.component.NomeAparelhoAutenticacao;
import br.com.ernanilima.jinventario.util.Utils;

public class FirebaseBancoDados {

    private static FirebaseBancoDados FIREBASE_DB;
    private IFirebaseAutenticacao iFirebaseAutenticacao;
    private DatabaseReference referencia;

    /** @return FirebaseDb - instancia da classe {@link FirebaseBancoDados} */
    public static FirebaseBancoDados getInstance() {
        if (FIREBASE_DB == null) {
            FIREBASE_DB = new FirebaseBancoDados();
        } return FIREBASE_DB;
    }

    /** Construtor */
    public FirebaseBancoDados() {
        this.referencia = FirebaseDatabase.getInstance().getReference();
        this.iFirebaseAutenticacao = new FirebaseAutenticacao();
    }

    /** Grava no firebase a lista de itens
     * @param daoSession DaoSession - sessao para obter o nome do aparelho
     * @param lsItensContagem List<ItemContagem> - lista de itens */
    public void setListaItensAlteradosColetados(DaoSession daoSession, List<ItemContagem> lsItensContagem) {
        // funciona no sdk 24, mas o aplicativo atualmente usa sdk 21
        // List<ItemContagemDTO> lsItemContagemDTO = mItemContagem.stream().map(ItemContagemDTO::new).collect(Collectors.toList());

        // lista de itens dto
        List<ItemContagemDTO> lsItemContagemDTO = new ArrayList<>();
        for (ItemContagem mItemContagem : lsItensContagem) {
            lsItemContagemDTO.add(new ItemContagemDTO(mItemContagem));
        }

        // grava a lista no firebase
        referencia
                // e-mail do usuario
                .child(Utils.converter(iFirebaseAutenticacao.getEmailUsuario()))
                    // nome do aparelho
                    .child(NomeAparelhoAutenticacao.getInstance(daoSession).getNomeAparelho())
                        // id da contagem
                        .child(lsItensContagem.get(0).getIdContagem().toString())
                            // item alterado ou coletado
                            .setValue(lsItemContagemDTO);
    }
}
