package br.com.ernanilima.jinventario.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ernanilima.jinventario.dto.ItemContagemDTO;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.dao.DaoSession;
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

    /** Grava a contagem recebida no parametro caso ela nao exista
     * O proprio firebase verifica se nao existe
     * @param daoSession DaoSession - sessao para obter o nome do aparelho
     * @param mContagemEstoque ContagemEstoque - contagem atual */
    public void gravarContagem(DaoSession daoSession, ContagemEstoque mContagemEstoque) {
        getContagem(daoSession, mContagemEstoque).setValue("Contagem N˚" + mContagemEstoque.getId());
    }

    /** Apaga a contagem recebida no parametro caso ela exista
     * O proprio firebase verifica se existe
     * @param daoSession DaoSession - sessao para obter o nome do aparelho
     * @param mContagemEstoque ContagemEstoque - contagem atual */
    public void removerContagem(DaoSession daoSession, ContagemEstoque mContagemEstoque) {
        getContagem(daoSession, mContagemEstoque).removeValue();
    }

    /** Recupera a contagem para que seja usada em outro metodo para definir valor(gravar) ou apagar
     * @param daoSession DaoSession - sessao para obter o nome do aparelho
     * @param mContagemEstoque ContagemEstoque - contagem atual
     * @return DatabaseReference - referencia a contagem */
    private DatabaseReference getContagem(DaoSession daoSession, ContagemEstoque mContagemEstoque) {
        // recupera a contagem
        return referencia
                // e-mail do usuario
                .child(Utils.converter(iFirebaseAutenticacao.getEmailUsuario()))
                // nome do aparelho
                .child(NomeAparelhoAutenticacao.getInstance(daoSession).getNomeAparelho())
                // id da contagem
                .child(mContagemEstoque.getId().toString());
    }

    /** Grava no firebase a lista de itens
     * @param daoSession DaoSession - sessao para obter o nome do aparelho
     * @param mContagemEstoque ContagemEstoque - contagem atual
     * @param lsItensContagem List<ItemContagem> - lista de itens */
    public void gravarListaItensAlteradosColetados(DaoSession daoSession, ContagemEstoque mContagemEstoque, List<ItemContagem> lsItensContagem) {
        if (lsItensContagem.isEmpty()) {
            // caso a lista de itens nao tenha nada
            // a contagem eh definida como se tivesse acabado de ser criada
            gravarContagem(daoSession, mContagemEstoque);

        } else {
            // caso a lista de itens tenha algo coletado
            // atualiza a contagem com a lista de itens recebidos no parametro

            // lista de itens dto
            // o codigo abaixo comentado funciona no sdk 24, mas o aplicativo atualmente usa sdk 21
            // List<ItemContagemDTO> lsItemContagemDTO = mItemContagem.stream().map(ItemContagemDTO::new).collect(Collectors.toList());
            List<ItemContagemDTO> lsItemContagemDTO = new ArrayList<>();
            for (ItemContagem mItemContagem : lsItensContagem) {
                lsItemContagemDTO.add(new ItemContagemDTO(mItemContagem));
            }

            // captura a contagem e atribui a lista de itens dto na contagem
            getContagem(daoSession, mContagemEstoque).setValue(lsItemContagemDTO);
        }
    }
}
