package com.example.neslihan.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.neslihan.myapplication.Adapter.KategoriDoldurAdapter;
import com.example.neslihan.myapplication.Adapter.OzelListKategoriAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListKullaniciUrunAdaptor;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class UrunDetayActivity   extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/Urun";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "Urun";

    private int UrunId;
    private TextView txtUrunBaslik;
    private   TextView txtUrunTanim;
    private EditText txtUrunAciklama;
    private   TextView txtmalzemeler;
    private  TextView txtYapilmaSuresi;
    private ImageView imageViewProf;
    private ImageView imgyorumprofil;
    private  TextView txtyorumprofil;
    private Button txtlinkYorumYaz;
    private Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_detay);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("uId");
        this.UrunId=id;
        this.context=UrunDetayActivity.this;
        imageViewProf = findViewById(R.id.imgUrunDetay);
        //spUrunKat =findViewById(R.id.spUrunKat);
        txtUrunBaslik = findViewById(R.id.txtUrunBaslik);
        txtUrunTanim = findViewById(R.id.txtUrunTanim);
        txtUrunAciklama = findViewById(R.id.txtUrunAciklama);
        txtmalzemeler = findViewById(R.id.txtmalzemeler);
        txtYapilmaSuresi = findViewById(R.id.txtYapilmaSuresi);
        imgyorumprofil = findViewById(R.id.imgyorumprofil);
        txtyorumprofil = findViewById(R.id.txtyorumprofil);
        txtlinkYorumYaz = findViewById(R.id.txtlinkYorumYaz);
        txtlinkYorumYaz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent yorumIntent = new Intent(getApplicationContext(), UrunYorumActivity.class);
                yorumIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                yorumIntent.putExtra("uId", UrunId);
                startActivity(yorumIntent);
            }
        });
        new ConversionAsyncTask().execute();
        statusBarColor();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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

    public class ConversionAsyncTask extends AsyncTask<Void, Void, Void> {
        JSONObject urun;
        public ConversionAsyncTask() {
            super();
        }
        @Override
        protected Void  doInBackground(Void... params) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("urunId", UrunId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String responseJSON = response.toString();
                urun = new JSONObject(responseJSON);
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
        protected void onPostExecute(Void result) {
            if (urun != null) {
                try {
                    int Id = urun.getInt("Id");
                    int KullaniciId = urun.getInt("KullaniciId");
                    int KategoriId = urun.getInt("KategoriId");
                    String UrunBaslik = urun.getString("UrunBaslik");
                    String UrunTanim = urun.getString("UrunTanim");
                    String UrunAciklama = urun.getString("UrunAciklama");
                    String Malzemeler = urun.getString("Malzemeler");
                    String YapilmaSuresi = urun.getString("YapilmaSuresi");
                    String UrunResim = urun.getString("UrunResim");
                    txtUrunBaslik.setText(UrunBaslik);
                    txtUrunTanim.setText(UrunTanim);
                    txtUrunAciklama.setText(UrunAciklama);
                    String htmlText = " %s ";
                   /* WebView webView = (WebView) findViewById(R.id.webView1);
                    webView.loadData(String.format(htmlText, UrunAciklama), "text/html", "utf-8");*/


                    txtmalzemeler.setText(Malzemeler);
                    txtYapilmaSuresi.setText(YapilmaSuresi +" Saat");
                    int resIDProf = context.getResources().getIdentifier(UrunResim.substring(0,UrunResim.length()-4), "drawable", getPackageName());
                    imageViewProf.setBackgroundResource(resIDProf);
                    /*
                    File file = new File(UrunResim);
                    FileInputStream streamIn = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Uri bitmap = Uri.fromFile(file);
                    imageViewProf.setImageURI(bitmap);
                    */

                    txtyorumprofil.setText(LoginActivity.user.getName());
                     resIDProf = context.getResources().getIdentifier(LoginActivity.user.getGorsel().substring(0,LoginActivity.user.getGorsel().length()-4), "drawable", getPackageName());
                    imgyorumprofil.setBackgroundResource(resIDProf);

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
        getSupportActionBar().setTitle("Ürün Bilgileri");
    }



}
