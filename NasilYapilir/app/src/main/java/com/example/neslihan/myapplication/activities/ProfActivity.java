package com.example.neslihan.myapplication.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neslihan.myapplication.Adapter.OzelListKategoriAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListKullaniciUrunAdaptor;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ProfActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static Context context;
    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/getKullaniciUrunListYorum";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "getKullaniciUrunListYorum";

    private static List<Urun> urunList;
    public static GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        init();
        context=getApplicationContext();
        gridview = (GridView) findViewById(R.id.gridview);

        ImageView imageViewProf = findViewById(R.id.imageViewProf);
        int resIDProf = context.getResources().getIdentifier(LoginActivity.user.getGorsel().substring(0,LoginActivity.user.getGorsel().length()-4), "drawable", getPackageName());
        imageViewProf.setImageResource(resIDProf);
        int Id=LoginActivity.user.getId();
        TextView txtkuladk = findViewById(R.id.txtkuladk);
        TextView textViewProfArkRes = findViewById(R.id.textViewProfArkRes);
        resIDProf = context.getResources().getIdentifier(LoginActivity.user.getArka().substring(0,LoginActivity.user.getArka().length()-4), "drawable", getPackageName());
        textViewProfArkRes.setBackgroundResource(resIDProf);

        textViewProfArkRes.setText((LoginActivity.user.getName()));
        txtkuladk.setText((LoginActivity.user.getName()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C34678")));
        statusBarColor();
        new ConversionAsyncTask().execute();

    }
    private void init() {
        urunList=new ArrayList<>();context=ProfActivity.this;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent accountsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(accountsIntent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    public class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;

        public ConversionAsyncTask() {
            super();
        }
        @Override
        protected Object doInBackground(Object[] objects) {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KullaniciId", LoginActivity.user.getId());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String responseJSON = response.toString();
                jarray = new JSONArray(responseJSON);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected void onPostExecute(Object o) {
            if (jarray != null) {
                try {
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject urun = jarray.getJSONObject(i);
                        int Id = urun.getInt("Id");
                        int KullaniciId = urun.getInt("KullaniciId");
                        int KategoriId = urun.getInt("KategoriId");
                        String UrunBaslik = urun.getString("UrunBaslik");
                        String UrunTanim = urun.getString("UrunTanim");
                        String UrunAciklama = urun.getString("UrunAciklama");
                        String Malzemeler = urun.getString("Malzemeler");
                        int YapilmaSuresi = urun.getInt("YapilmaSuresi");
                        String UrunResim = urun.getString("UrunResim");
                        int BegeniSayisi = urun.getInt("BegeniSayisi");
                        int yorumSayisi = urun.getInt("yorumSayisi");
                        Urun urunl = new Urun(Id, KullaniciId, KategoriId, UrunBaslik,UrunTanim,UrunAciklama, Malzemeler,YapilmaSuresi,UrunResim,BegeniSayisi,yorumSayisi);
                        urunList.add(urunl);


                    }
                    OzelListKullaniciUrunAdaptor adapter = new OzelListKullaniciUrunAdaptor(context, urunList);
                    gridview.setAdapter(adapter);
                } catch (Exception e) {

                }
            }
        }

    }
    private void statusBarColor() {
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBar));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C34678")));
        getSupportActionBar().setTitle("Profilim");

    }



}
