package br.com.ernanilima.jinventario.config;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import br.com.ernanilima.jinventario.dao.DaoMaster;
import br.com.ernanilima.jinventario.dao.DaoSession;

/** Classe criada para obter a sessao do banco de dados criado pelo greendao */
public class DbGreenDao extends Application {
    // IMPORTANTE: DaoMaster, DaoSession e DAOs sao criados automaticamente
    // ao executar o projeto. Isso esta descriminado no proprio github.
    // https://github.com/greenrobot/greenDAO

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "jinventario-db");
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getSessao() {
        return daoSession;
    }
}
