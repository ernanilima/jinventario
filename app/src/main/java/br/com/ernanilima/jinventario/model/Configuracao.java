package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "CONFIGURACAO")
public class Configuracao {
    // construtores, gets e sets sao gerados automaticamente ao executar o projeto

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "ATIVAR_CAMERA")
    private boolean cameraScanner;

    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 647547117)
    public Configuracao() {}

    @Generated(hash = 1181914871)
    public Configuracao(Long id, boolean cameraScanner) {
        this.id = id;
        this.cameraScanner = cameraScanner;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getCameraScanner() {
        return this.cameraScanner;
    }
    public void setCameraScanner(boolean cameraScanner) {
        this.cameraScanner = cameraScanner;
    }
    
}
