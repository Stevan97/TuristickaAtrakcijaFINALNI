package com.example.turistickaatrakcijafinalni.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Komentari.TABL_KOM)
public class Komentari {

    public static final String TABL_KOM = "koments";
    private static final String FIELD_ID = "id";
    private static final String FIELD_KOMENTARI = "komentari";
    private static final String FIELD_ATRAKCIJA = "atrakcija";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_KOMENTARI)
    private String komentari;

    @DatabaseField(columnName = FIELD_ATRAKCIJA, foreign = true, foreignAutoRefresh = true)
    private Atrakcija atrakcija;

    public Komentari(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKomentari() {
        return komentari;
    }

    public void setKomentari(String komentari) {
        this.komentari = komentari;
    }

    public Atrakcija getAtrakcija() {
        return atrakcija;
    }

    public void setAtrakcija(Atrakcija atrakcija) {
        this.atrakcija = atrakcija;
    }

    public String toString() {
        return komentari;
    }

}
