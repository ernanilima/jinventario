package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "NOME_APARELHO")
public class NomeAparelho {
    // construtores, gets e sets sao gerados automaticamente ao executar o projeto

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "NOME")
    private String nome;

    @Generated(hash = 226785605)
    public NomeAparelho() {}

    @Generated(hash = 690834848)
    public NomeAparelho(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
