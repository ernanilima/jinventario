package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

@Entity(nameInDb = "USER")
public class User {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Index(unique = true)
    @Property(nameInDb = "EMAIL")
    private String email;
    @Transient
    private String password;
    @Property(nameInDb = "DEVICE_NAME")
    private String deviceName ;
    @Property(nameInDb = "DATE_SENDING_VERIFICATION")
    private Date dateSendingVerification;
    @Property(nameInDb = "DATE_SENDING_PASSWORD")
    private Date dateSendingPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // construtores, gets e sets sao gerados automaticamente ao executar o projeto
    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 152847302)
    public User(Long id, String email, String deviceName,
            Date dateSendingVerification, Date dateSendingPassword) {
        this.id = id;
        this.email = email;
        this.deviceName = deviceName;
        this.dateSendingVerification = dateSendingVerification;
        this.dateSendingPassword = dateSendingPassword;
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
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public Date getDateSendingVerification() {
        return this.dateSendingVerification;
    }
    public void setDateSendingVerification(Date dateSendingVerification) {
        this.dateSendingVerification = dateSendingVerification;
    }
    public Date getDateSendingPassword() {
        return this.dateSendingPassword;
    }
    public void setDateSendingPassword(Date dateSendingPassword) {
        this.dateSendingPassword = dateSendingPassword;
    }
}
