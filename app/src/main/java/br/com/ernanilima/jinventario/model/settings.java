package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

@Entity(nameInDb = "SETTINGS")
public class settings implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "SHOW PRICE")
    private boolean showPrice;
    @Property(nameInDb = "CAMERA_SCANNER")
    private boolean cameraScanner;
    @Property(nameInDb = "CAMERA_SCANNER_MLKIT")
    private boolean cameraScannerMlkit;
    @Property(nameInDb = "CAMERA_SCANNER_ZXING")
    private boolean cameraScannerZxing;

    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 148091290)
    public settings(Long id, boolean showPrice, boolean cameraScanner,
            boolean cameraScannerMlkit, boolean cameraScannerZxing) {
        this.id = id;
        this.showPrice = showPrice;
        this.cameraScanner = cameraScanner;
        this.cameraScannerMlkit = cameraScannerMlkit;
        this.cameraScannerZxing = cameraScannerZxing;
    }
    @Generated(hash = 501373388)
    public settings() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getShowPrice() {
        return this.showPrice;
    }
    public void setShowPrice(boolean showPrice) {
        this.showPrice = showPrice;
    }
    public boolean getCameraScanner() {
        return this.cameraScanner;
    }
    public void setCameraScanner(boolean cameraScanner) {
        this.cameraScanner = cameraScanner;
    }
    public boolean getCameraScannerMlkit() {
        return this.cameraScannerMlkit;
    }
    public void setCameraScannerMlkit(boolean cameraScannerMlkit) {
        this.cameraScannerMlkit = cameraScannerMlkit;
    }
    public boolean getCameraScannerZxing() {
        return this.cameraScannerZxing;
    }
    public void setCameraScannerZxing(boolean cameraScannerZxing) {
        this.cameraScannerZxing = cameraScannerZxing;
    }
}
