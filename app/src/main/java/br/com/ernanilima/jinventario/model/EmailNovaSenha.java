package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity(nameInDb = "EMAIL_NOVA_SENHA")
public class EmailNovaSenha {
    // construtores, gets e sets sao gerados automaticamente ao executar o projeto

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "EMAIL")
    private String email;
    @Property(nameInDb = "DATA_ENVIO_NOVA_SENHA")
    private Date dataEnvioNovaSenha;


    @Generated(hash = 1234936148)
    public EmailNovaSenha() {}

    @Generated(hash = 1532615839)
    public EmailNovaSenha(Long id, String email, Date dataEnvioNovaSenha) {
        this.id = id;
        this.email = email;
        this.dataEnvioNovaSenha = dataEnvioNovaSenha;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDataEnvioNovaSenha() {
        return this.dataEnvioNovaSenha;
    }
    public void setDataEnvioNovaSenha(Date dataEnvioNovaSenha) {
        this.dataEnvioNovaSenha = dataEnvioNovaSenha;
    }
}
