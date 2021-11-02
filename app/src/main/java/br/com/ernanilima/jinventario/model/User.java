package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

@Entity(nameInDb = "USER")
public class User {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "EMAIL")
    private String email;
    @Transient
    private String password ;
    @Property(nameInDb = "DATA_ENVIO_VERIFICACAO")
    private Date dataEnvioVerificacao;
    @Property(nameInDb = "DATA_ENVIO_NOVA_SENHA")
    private Date dataEnvioNovaSenha;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // construtores, gets e sets sao gerados automaticamente ao executar o projeto
    // tudo abaixo eh construido automaticamente ao executar o projeto
    @Generated(hash = 839459599)
    public User(Long id, String email, Date dataEnvioVerificacao,
            Date dataEnvioNovaSenha) {
        this.id = id;
        this.email = email;
        this.dataEnvioVerificacao = dataEnvioVerificacao;
        this.dataEnvioNovaSenha = dataEnvioNovaSenha;
    }
    @Generated(hash = 586692638)
    public User() {
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
    public Date getDataEnvioNovaSenha() {
        return this.dataEnvioNovaSenha;
    }
    public void setDataEnvioNovaSenha(Date dataEnvioNovaSenha) {
        this.dataEnvioNovaSenha = dataEnvioNovaSenha;
    }
}
