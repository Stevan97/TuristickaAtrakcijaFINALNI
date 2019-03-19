package com.example.turistickaatrakcijafinalni.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turistickaatrakcijafinalni.R;
import com.example.turistickaatrakcijafinalni.adapters.DrawerAdapter;
import com.example.turistickaatrakcijafinalni.db.DatabaseHelper;
import com.example.turistickaatrakcijafinalni.db.model.Atrakcija;
import com.example.turistickaatrakcijafinalni.db.model.Komentari;
import com.example.turistickaatrakcijafinalni.dialogs.AboutDialog;
import com.example.turistickaatrakcijafinalni.model.NavigationItems;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TOAST_MESSAGE = "toast";
    private static final int SELECT_PICTURE = 1;
    private static final int MY_PERMISSION_REQUEST_MEDINA = 1;

    private int position = 1;
    private Atrakcija atrakcija = null;

    private DatabaseHelper databaseHelper = null;

    private Komentari komentari = null;

    private String imagePath = null;
    private ImageView preview = null;

    private ListView listView_DETAIL = null;
    private ForeignCollection<Komentari> komentariForeignCollection = null;
    private List<Komentari> komentariList = null;
    private ArrayAdapter<Komentari> komentariArrayAdapter = null;


    private SharedPreferences sharedPreferences = null;
    boolean showMessage = false;

    private Spannable message1 = null;
    private Spannable message2 = null;
    private Toast toast = null;
    private View toastView = null;
    private TextView textToast = null;

    private Intent intentPosition = null;
    private int idPosition = 0;

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private RelativeLayout drawerPane;
    private ArrayList<NavigationItems> drawerItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        navigationDrawer();

        prikaziDetaljeAtrakcije();


    }

    private void prikaziDetaljeAtrakcije() {
        intentPosition = getIntent();
        idPosition = intentPosition.getExtras().getInt("id");

        try {
            atrakcija = getDatabaseHelper().getAtrakcija().queryForId(idPosition);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextView naziv = findViewById(R.id.detail_naziv_atrakcije);
        message1 = new SpannableString("Naziv Atrakcije: ");
        message2 = new SpannableString(atrakcija.getNaziv());
        spannableStyle();
        naziv.setText(message1);
        naziv.append(message2);

        TextView opis = findViewById(R.id.detail_opis_atrakcije);
        message1 = new SpannableString("Opis Atrakcije: ");
        message2 = new SpannableString(atrakcija.getOpis());
        spannableStyle();
        opis.setText(message1);
        opis.append(message2);

        message1 = new SpannableString("Adresa Atrakcije: ");
        message2 = new SpannableString(atrakcija.getAdresa());
        spannableStyle();
        final TextView adresa = findViewById(R.id.detail_adresa_atrakcije);
        adresa.setText(message1);
        message2.setSpan(new UnderlineSpan(), 0, message2.length(), 0);
        adresa.append(message2);
        adresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                        + adresa.getText().toString()));
                startActivity(geoIntent);
            }
        });


        TextView brojTelefona = findViewById(R.id.detail_brojTelefona);
        message1 = new SpannableString("Broj Telefona Atrakcije: ");
        message2 = new SpannableString(String.valueOf(atrakcija.getBrojTelefona()));
        spannableStyle();
        brojTelefona.setText(message1);
        brojTelefona.append(message2);

        TextView cena = findViewById(R.id.detail_cena_ulaznice);
        message1 = new SpannableString("Cena Atrakcije: ");
        message2 = new SpannableString(String.valueOf(atrakcija.getCenaUlaznice()));
        spannableStyle();
        cena.setText(message1);
        cena.append(message2);

        TextView radnoVremeOD = findViewById(R.id.detail_radno_vreme_OD);
        message1 = new SpannableString("Radno vreme Atrakcije OD: ");
        message2 = new SpannableString(String.valueOf(atrakcija.getRadnoVremeOD()));
        spannableStyle();
        radnoVremeOD.setText(message1);
        radnoVremeOD.append(message2);

        TextView radnoVremeDO = findViewById(R.id.detail_radno_vreme_DO);
        message1 = new SpannableString(" DO:  ");
        message2 = new SpannableString(String.valueOf(atrakcija.getRadnoVremeDO()));
        spannableStyle();
        radnoVremeDO.setText(message1);
        radnoVremeDO.append(message2);

        TextView webAdresa = findViewById(R.id.detail_web_adresa);
        message1 = new SpannableString("WebSajt Atrakcije: ");
        message2 = new SpannableString(String.valueOf(atrakcija.getWebAdresa()));
        spannableStyle();
        webAdresa.setText(message1);
        webAdresa.append(message2);

        final ImageView imageView = findViewById(R.id.detail_image_view);
        imageView.setImageURI(Uri.parse(atrakcija.getSlika()));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DetailActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.full_screen);
                dialog.show();

                ImageView imageFullScreen = dialog.findViewById(R.id.full_screen_IMG);
                imageFullScreen.setImageURI(Uri.parse(atrakcija.getSlika()));
                imageFullScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        listView_DETAIL = findViewById(R.id.list_view_DETAIL);
        try {
            komentariForeignCollection = getDatabaseHelper().getAtrakcija().queryForId(idPosition).getKomentari();
            komentariList = new ArrayList<>(komentariForeignCollection);
            komentariArrayAdapter = new ArrayAdapter<>(DetailActivity.this, R.layout.list_array_adapter, R.id.list_array_text_view, komentariList);
            listView_DETAIL.setAdapter(komentariArrayAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void izmenaAtrakcije() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.izmena_atrakcije);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        intentPosition = getIntent();
        idPosition = intentPosition.getExtras().getInt("id");

        Button gallery = dialog.findViewById(R.id.izmeni_atrakciju_button_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelfPermission();
                preview = dialog.findViewById(R.id.izmeni_atrakciju_preview);
                select_picture();
            }
        });


        final EditText editNaziv = dialog.findViewById(R.id.izmeni_atrakciju_naziv);
        final EditText editOpis = dialog.findViewById(R.id.izmeni_atrakciju_opis);
        final EditText editAdresa = dialog.findViewById(R.id.izmeni_atrakciju_adresa);
        final EditText editBrojTelefona = dialog.findViewById(R.id.izmeni_atrakciju_brojTelefona);
        final EditText editWebAdresa = dialog.findViewById(R.id.izmeni_atrakciju_webAdresa);
        final EditText editRadnoVremeOD = dialog.findViewById(R.id.izmeni_atrakciju_radnoVremeOD);
        final EditText editRadnoVremeDO = dialog.findViewById(R.id.izmeni_atrakciju_radnoVremeDO);
        final EditText editCenaUlaznice = dialog.findViewById(R.id.izmeni_atrakciju_cena);

        Button confirm = dialog.findViewById(R.id.izmeni_atrakciju_button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNaziv.getText().toString().isEmpty()) {
                    editNaziv.setError("Polje naziv ne sme biti prazno!");
                    return;
                }
                if (editOpis.getText().toString().isEmpty()) {
                    editOpis.setError("Polje opis ne sme biti prazno!");
                    return;
                }
                if (editAdresa.getText().toString().isEmpty()) {
                    editAdresa.setError("Unesite pravu adresu da bi mogli da otvorite u MAPS!");
                    return;
                }
                if (editBrojTelefona.getText().toString().isEmpty() || editBrojTelefona.getText().toString().length() < 5
                        || editBrojTelefona.getText().toString().length() > 10) {
                    editBrojTelefona.setError("Broj telefona mora biti duzi od 5 i manji od 10 !");
                    return;
                }
                if (editWebAdresa.getText().toString().isEmpty()) {
                    editWebAdresa.setError("Format webSajta: www.google.com");
                    return;
                }
                if (editRadnoVremeOD.getText().toString().isEmpty()) {
                    editRadnoVremeOD.setError("Polje radno vreme OD ne sme biti prazno!");
                    return;
                }
                if (editRadnoVremeDO.getText().toString().isEmpty()) {
                    editRadnoVremeDO.setError("Polje radno vreme DO ne sme biti prazno!");
                    return;
                }
                if (editCenaUlaznice.getText().toString().isEmpty()) {
                    editCenaUlaznice.setError("Polje cena ulaznice ne sme biti prazno!");
                    return;
                }
                if (imagePath == null || imagePath.isEmpty() || preview == null) {
                    Toast.makeText(DetailActivity.this, "Morate odabrati sliku", Toast.LENGTH_LONG).show();
                    return;
                }

                String naziv = editNaziv.getText().toString();
                String opis = editOpis.getText().toString();
                String adresa = editAdresa.getText().toString();
                int brojTelefona = Integer.parseInt(editBrojTelefona.getText().toString());
                String webAdresa = editWebAdresa.getText().toString();
                int radnoVremeOD = Integer.parseInt(editRadnoVremeOD.getText().toString());
                int radnoVremeDO = Integer.parseInt(editRadnoVremeDO.getText().toString());
                double cenaUlaznice = Double.parseDouble(editCenaUlaznice.getText().toString());

                atrakcija.setNaziv(naziv);
                atrakcija.setOpis(opis);
                atrakcija.setAdresa(adresa);
                atrakcija.setBrojTelefona(brojTelefona);
                atrakcija.setWebAdresa(webAdresa);
                atrakcija.setRadnoVremeOD(radnoVremeOD);
                atrakcija.setRadnoVremeDO(radnoVremeDO);
                atrakcija.setCenaUlaznice(cenaUlaznice);
                atrakcija.setSlika(imagePath);


                try {
                    getDatabaseHelper().getAtrakcija().update(atrakcija);
                    resetImage();
                    dialog.dismiss();
                    startActivity(getIntent());
                    finish();
                    overridePendingTransition(0, 0);

                    message1 = new SpannableString("Uspesna izmena | Novo ime Atrakcije: ");
                    message2 = new SpannableString(atrakcija.getNaziv());
                    spannableStyle();

                    if (showMessage) {
                        toast = Toast.makeText(DetailActivity.this, "", Toast.LENGTH_LONG);
                        toastView = toast.getView();

                        textToast = toastView.findViewById(android.R.id.message);
                        textToast.setText(message1);
                        textToast.append(message2);
                        toast.show();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });


        Button cancel = dialog.findViewById(R.id.izmeni_atrakciju_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void izbrisiAtrakciju() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dijalog_izbrisi);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        intentPosition = getIntent();
        idPosition = intentPosition.getExtras().getInt("id");

        TextView text = dialog.findViewById(R.id.izbrisi_atrakciju_text);
        message1 = new SpannableString("Da li ste sigurni da zelite da izbrisete Atrakciju pod nazivom: ");
        message2 = new SpannableString(atrakcija.getNaziv());
        spannableStyle();
        text.setText(message1);
        text.append(message2);

        Button confirm = dialog.findViewById(R.id.izbrisi_atrakciju_button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    atrakcija = getDatabaseHelper().getAtrakcija().queryForId(idPosition);
                    komentariForeignCollection = getDatabaseHelper().getAtrakcija().queryForId(idPosition).getKomentari();
                    komentariList = new ArrayList<>(komentariForeignCollection);
                    getDatabaseHelper().getKomentari().delete(komentariList);
                    getDatabaseHelper().getAtrakcija().delete(atrakcija);
                    onBackPressed();

                    message1 = new SpannableString("Uspesno Izbrisana Atrakcija: ");
                    message2 = new SpannableString(atrakcija.getNaziv());
                    spannableStyle();

                    if (showMessage) {
                        toast = Toast.makeText(DetailActivity.this, "", Toast.LENGTH_LONG);
                        toastView = toast.getView();

                        textToast = toastView.findViewById(android.R.id.message);
                        textToast.setText(message1);
                        textToast.append(message2);
                        toast.show();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button cancel = dialog.findViewById(R.id.izbrisi_atrakciju_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void dodajKomentar() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dodaj_komentar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText editKom = dialog.findViewById(R.id.dodaj_komentar_komentar);

        Button confirm = dialog.findViewById(R.id.dodaj_komentar_button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editKom.getText().toString().isEmpty()) {
                    editKom.setError("Morate uneti komentar");
                    return;
                }

                String komentar = editKom.getText().toString();

                intentPosition = getIntent();
                idPosition = intentPosition.getExtras().getInt("id");

                try {
                    atrakcija = getDatabaseHelper().getAtrakcija().queryForId(idPosition);
                    komentari = new Komentari();
                    komentari.setKomentari(komentar);
                    komentari.setAtrakcija(atrakcija);
                    getDatabaseHelper().getKomentari().create(komentari);
                    dialog.dismiss();

                    osveziKomentare();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });

        Button cancel = dialog.findViewById(R.id.dodaj_komentar_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void osveziKomentare() {
        listView_DETAIL = findViewById(R.id.list_view_DETAIL);
        try {
            komentariForeignCollection = getDatabaseHelper().getAtrakcija().queryForId(idPosition).getKomentari();
            komentariList = new ArrayList<>(komentariForeignCollection);
            komentariArrayAdapter = new ArrayAdapter<>(DetailActivity.this, R.layout.list_array_adapter, R.id.list_array_text_view, komentariList);
            listView_DETAIL.setAdapter(komentariArrayAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void consultPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);
        showMessage = sharedPreferences.getBoolean(TOAST_MESSAGE, true);
    }

    /**
     * Navigaciona Fioka
     */
    private void navigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar_DETAIL);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_list);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }

        drawerItems.add(new NavigationItems("Turisticke Atrakcije", "Prikazuje listu Atrakcija", R.drawable.ic_show_all));
        drawerItems.add(new NavigationItems("Podesavanja", "Otvara Podesavanja Aplikacije", R.drawable.ic_settings));
        drawerItems.add(new NavigationItems("Informacije", "Informacije o Aplikaciji", R.drawable.ic_about_app));

        DrawerAdapter drawerAdapter = new DrawerAdapter(this, drawerItems);
        drawerListView = findViewById(R.id.nav_list_DETAIL);
        drawerListView.setAdapter(drawerAdapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        drawerTitle = getTitle();
        drawerLayout = findViewById(R.id.drawer_layout_DETAIL);
        drawerPane = findViewById(R.id.drawer_pane_DETAIL);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                super.onDrawerClosed(drawerView);
            }
        };

    }

    /**
     * OnItemClick iz NavigacioneFioke.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                onBackPressed();
            } else if (position == 1) {
                Intent intent = new Intent(DetailActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else if (position == 2) {
                AlertDialog aboutDialog = new AboutDialog(DetailActivity.this).prepareDialog();
                aboutDialog.show();
            }

            drawerLayout.closeDrawer(drawerPane);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_detail_delete:
                izbrisiAtrakciju();
                break;
            case R.id.menu_detail_update:
                izmenaAtrakcije();
                break;
            case R.id.menu_detail_dodaj_komentar:
                dodajKomentar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        consultPreferences();
        super.onResume();
    }

    private void spannableStyle() {
        message1.setSpan(new StyleSpan(Typeface.BOLD), 0, message1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message2.setSpan(new ForegroundColorSpan(getColor(R.color.colorRED)), 0, message2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

        super.onDestroy();
    }

    private void resetImage() {
        imagePath = "";
        preview = null;
    }

    private void select_picture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void checkSelfPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_MEDINA);
        } else {
            // if not accepted, do something
        }

    }

    /**
     * <- Metoda za ucitavanje slike * Cuvanje putanje do slike. * ->
     */
    public String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    if (selectedImageUri != null) {
                        imagePath = getImagePath(selectedImageUri);
                    }
                    if (preview != null) {
                        preview.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position", position);
    }

}
