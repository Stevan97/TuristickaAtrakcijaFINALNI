package com.example.turistickaatrakcijafinalni.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.turistickaatrakcijafinalni.db.model.Atrakcija;
import com.example.turistickaatrakcijafinalni.db.model.Komentari;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "db.glumci.30.v2";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private Dao<Atrakcija, Integer> getmAtrakcija = null;
    private Dao<Komentari, Integer> getmKomentari = null;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Atrakcija.class);
            TableUtils.createTable(connectionSource, Komentari.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Komentari.class, true);
            TableUtils.dropTable(connectionSource, Atrakcija.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Atrakcija, Integer> getAtrakcija() throws SQLException {
        if (getmAtrakcija == null) {
            getmAtrakcija = getDao(Atrakcija.class);
        }
        return getmAtrakcija;
    }

    public Dao<Komentari, Integer> getKomentari() throws SQLException {
        if (getmKomentari == null) {
            getmKomentari = getDao(Komentari.class);
        }
        return getmKomentari;
    }

    @Override
    public void close() {
        getmKomentari = null;
        getmAtrakcija = null;
        super.close();
    }
}