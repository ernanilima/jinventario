package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity(nameInDb = "EMAIL_ENVIADO")
public class EmailEnviado {
    // construtores, gets e sets sao gerados automaticamente
    // ao executar o projeto

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "EMAIL")
    private String email;
    @Property(nameInDb = "DATA_ENVIO_VERIFICACAO")
    private Date dataEnvioVerificacao;

    @Generated(hash = 750296506)
    public EmailEnviado() {}

    @Generated(hash = 1126840305)
    public EmailEnviado(Long id, String email, Date dataEnvioVerificacao) {
        this.id = id;
        this.email = email;
        this.dataEnvioVerificacao = dataEnvioVerificacao;
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
    public Date getDataEnvioVerificacao() {
        return this.dataEnvioVerificacao;
    }
    public void setDataEnvioVerificacao(Date dataEnvioVerificacao) {
        this.dataEnvioVerificacao = dataEnvioVerificacao;
    }
}
