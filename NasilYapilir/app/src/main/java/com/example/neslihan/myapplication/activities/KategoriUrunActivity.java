package com.example.neslihan.myapplication.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import com.example.neslihan.myapplication.Adapter.OzelListAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListKategoriUrunAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListKullaniciUrunAdaptor;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.model.Favori;
import com.example.neslihan.myapplication.model.Urun;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class KategoriUrunActivity extends AppCompatActivity {

    private static Context context;
    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/getKategoriUrunListYorum";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "getKategoriUrunListYorum";

    public final static String SOAP_ACTIONl = "http://evdekiisim.somee.com/KullanıcıFavList";
    private static final String METHOD_NAMEl = "KullanıcıFavList";

    private static List<Urun> urunList;
    private List<Favori> favList;
    public static GridView gridview;
    public TextView txtkatUyari;
    public String KategoriAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_urun);
        Bundle b = getIntent().getExtras();
        String kAd = b.getString("kAd");
        this.KategoriAd=kAd;
        init();
        context = getApplicationContext();
        gridview = (GridView) findViewById(R.id.gridview);
        txtkatUyari = (TextView) findViewById(R.id.txtkatUyari);

        statusBarColor();
        new ConversionAsyncTask().execute();
    }
    private void init() {
        urunList=new ArrayList<>();context=KategoriUrunActivity.this;
        favList=new ArrayList<>();
    }

    public class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        JSONArray jarrayl;
        public ConversionAsyncTask() {
            super();
        }
        @Override
        protected Object doInBackground(Object[] objects) {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KategoriAd", KategoriAd);
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


            SoapObject Requestl = new SoapObject(NAMESPACE, METHOD_NAMEl);
            Requestl.addProperty("kulId", LoginActivity.user.getId());
            SoapSerializationEnvelope envelopel = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelopel.dotNet = true;
            envelopel.setOutputSoapObject(Requestl);
            HttpTransportSE androidHttpTransportl = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTIONl, envelopel);
                SoapPrimitive responsel = (SoapPrimitive) envelopel.getResponse();
                String responseJSONl = responsel.toString();
                jarrayl = new JSONArray(responseJSONl);
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
                } catch (Exception e) {

                }
            }

            if (jarrayl != null) {
                try {
                    for (int i = 0; i < jarrayl.length(); i++) {
                        JSONObject urunfav = jarrayl.getJSONObject(i);
                        int Id = urunfav.getInt("Id");
                        int KullaniciId = urunfav.getInt("KullaniciId");
                        int UrunId = urunfav.getInt("UrunId");
                        int EkleyenKulId = urunfav.getInt("EkleyenKulId");
                        int KategoriId = urunfav.getInt("KategoriId");
                        String urunBaslik = urunfav.getString("UrunBaslik");
                        String urunTanim = urunfav.getString("UrunTanim");
                        String UrunAciklama = urunfav.getString("UrunAciklama");

                        String Malzemeler = urunfav.getString("Malzemeler");
                        int YapilmaSuresi = urunfav.getInt("YapilmaSuresi");
                        String UrunResim = urunfav.getString("UrunResim");
                        int BegeniSayisi= urunfav.getInt("BegeniSayisi");
                        Favori favori = new Favori(Id,KullaniciId,UrunId,EkleyenKulId, KategoriId,urunBaslik, urunTanim,UrunAciklama, Malzemeler,YapilmaSuresi,UrunResim,BegeniSayisi,0);
                        favList.add(favori);
                    }
                }
                catch (Exception e)
                {
                    e.getMessage();
                }
            }
            OzelListKategoriUrunAdaptor adapter = new OzelListKategoriUrunAdaptor(KategoriUrunActivity.this, urunList,favList);
            gridview.setAdapter(adapter);
            if(adapter.getCount()==0)
               txtkatUyari.setText("Kategoriye Ait Ürün Bulunamadı");
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
        getSupportActionBar().setTitle(KategoriAd + " Kategorisi Ürünleri");

    }



}
