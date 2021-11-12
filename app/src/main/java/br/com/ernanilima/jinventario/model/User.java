package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Index;

import java.util.Date;

@Entity(nameInDb = "USER")
public class User {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Index(unique = true)
    @Property(nameInDb = "EMAIL")
    private String email;
    @Property(nameInDb = "DEVICE_NAME")
    private String deviceName ;
    @Property(nameInDb = "DATE_SUBMIT_VERIFICATION")
    private Date dateSubmitVerification;
    @Property(nameInDb = "DATE_SUBMIT_NEW_PASSWORD")
    private Date dateSubmitNewPassword;

    // construtores, gets e sets sao gerados automaticamente ao executar o projeto
    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 367072297)
    public User(Long id, String email, String deviceName,
            Date dateSubmitVerification, Date dateSubmitNewPassword) {
        this.id = id;
        this.email = email;
        this.deviceName = deviceName;
        this.dateSubmitVerification = dateSubmitVerification;
        this.dateSubmitNewPassword = dateSubmitNewPassword;
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
    public Date getDateSubmitVerification() {
        return this.dateSubmitVerification;
    }
    public void setDateSubmitVerification(Date dateSubmitVerification) {
        this.dateSubmitVerification = dateSubmitVerification;
    }
    public Date getDateSubmitNewPassword() {
        return this.dateSubmitNewPassword;
    }
    public void setDateSubmitNewPassword(Date dateSubmitNewPassword) {
        this.dateSubmitNewPassword = dateSubmitNewPassword;
    }
}
