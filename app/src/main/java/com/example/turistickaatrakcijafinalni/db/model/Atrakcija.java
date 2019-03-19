package com.example.turistickaatrakcijafinalni.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Atrakcija.TABL_ATRACTION)
public class Atrakcija {

    public static final String TABL_ATRACTION = "atraction";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAZIV = "naziv";
    private static final String FIELD_OPIS = "opis";
    private static final String FIELD_SLIKA = "slika";
    private static final String FIELD_ADRESA = "adresa";
    private static final String FIELD_BROJ_TELEFONA = "brojTelefona";
    private static final String FIELD_WEB_ADRESA = "webAdresa";
    private static final String FIELD_RADNO_VREME_OD = "radnoVremeOD";
    private static final String FIELD_RADNO_VREME_DO = "radnoVremeDO";
    private static final String FIELD_CENA_ULAZNICE = "cenaUlaznice";
    private static final String FIELD_KOMENTARI = "komentari";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAZIV)
    private String naziv;

    @DatabaseField(columnName = FIELD_OPIS)
    private String opis;

    @DatabaseField(columnName = FIELD_SLIKA)
    private String slika;

    @DatabaseField(columnName = FIELD_ADRESA)
    private String adresa;

    @DatabaseField(columnName = FIELD_BROJ_TELEFONA)
    private int brojTelefona;

    @DatabaseField(columnName = FIELD_WEB_ADRESA)
    private String webAdresa;

    @DatabaseField(columnName = FIELD_RADNO_VREME_OD)
    private int radnoVremeOD;

    @DatabaseField(columnName = FIELD_RADNO_VREME_DO)
    private int radnoVremeDO;

    @DatabaseField(columnName = FIELD_CENA_ULAZNICE)
    private double cenaUlaznice;

    @ForeignCollectionField(columnName = FIELD_KOMENTARI, eager = true)
    private ForeignCollection<Komentari> komentari;

    public Atrakcija() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(int brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getWebAdresa() {
        return webAdresa;
    }

    public void setWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
    }

    public int getRadnoVremeOD() {
        return radnoVremeOD;
    }

    public void setRadnoVremeOD(int radnoVremeOD) {
        this.radnoVremeOD = radnoVremeOD;
    }

    public int getRadnoVremeDO() {
        return radnoVremeDO;
    }

    public void setRadnoVremeDO(int radnoVremeDO) {
        this.radnoVremeDO = radnoVremeDO;
    }

    public double getCenaUlaznice() {
        return cenaUlaznice;
    }

    public void setCenaUlaznice(double cenaUlaznice) {
        this.cenaUlaznice = cenaUlaznice;
    }

    public ForeignCollection<Komentari> getKomentari() {
        return komentari;
    }

    public void setKomentari(ForeignCollection<Komentari> komentari) {
        this.komentari = komentari;
    }

    public String toString() {
        return naziv;
    }

}
