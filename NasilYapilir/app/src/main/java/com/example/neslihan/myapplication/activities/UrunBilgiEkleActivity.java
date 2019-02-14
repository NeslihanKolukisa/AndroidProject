package com.example.neslihan.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UrunBilgiEkleActivity  extends AppCompatActivity {
    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/UrunEkle";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "UrunEkle";

    private TextInputLayout textInputLayoutUrunBaslik;
    private TextInputLayout textInputLayoutUrunTanim;
    private TextInputLayout textInputUrunAciklama;
    private TextInputLayout textInputLayoutMalzemeler;
    private TextInputLayout textInputLayoutYapilmaSuresi;


    private TextInputEditText textInputEditTextUrunBaslik;
    private TextInputEditText textInputEditTextUrunTanim;
    private TextInputEditText textInputEditTextUrunAciklama;
    private TextInputEditText textInputEditTextMalzemeler;
    private TextInputEditText textInputEditYapilmaSuresi;

    private AppCompatButton appCompatButtonUrunEkle;

    private   int katId;
    private  String resimPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_bilgi_ekle);
        Bundle b = getIntent().getExtras();
        int id = b.getInt("katId");
        resimPath = b.getString("resimPath");
        this.katId=id;

        initViews();
        initListeners();
        statusBarColor();
    }
    public void initViews() {

        textInputLayoutUrunBaslik = findViewById(R.id.textInputLayoutUrunBaslik);
        textInputLayoutUrunTanim = findViewById(R.id.textInputLayoutUrunTanim);
        textInputUrunAciklama = findViewById(R.id.textInputUrunAciklama);
        textInputLayoutMalzemeler= findViewById(R.id.textInputLayoutMalzemeler);
        textInputLayoutYapilmaSuresi = findViewById(R.id.textInputLayoutYapilmaSuresi);


        textInputEditTextUrunBaslik = findViewById(R.id.textInputEditTextUrunBaslik);
        textInputEditTextUrunTanim = findViewById(R.id.textInputEditTextUrunTanim);
        textInputEditTextUrunAciklama = findViewById(R.id.textInputEditTextUrunAciklama);
        textInputEditTextMalzemeler = findViewById(R.id.textInputEditTextMalzemeler);
        textInputEditYapilmaSuresi = findViewById(R.id.textInputEditYapilmaSuresi);
        appCompatButtonUrunEkle = findViewById(R.id.appCompatButtonUrunEkle);



    }


    private void initListeners() {
        appCompatButtonUrunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new ConversionAsyncTask().execute();
            }
        });
    }

    private class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        JSONObject urun;
        boolean state;

        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KullaniciId", LoginActivity.user.getId());
            Request.addProperty("KategoriId", katId);
            Request.addProperty("UrunBaslik", textInputEditTextUrunBaslik.getText().toString());
            Request.addProperty("UrunTanim", textInputEditTextUrunTanim.getText().toString());
            Request.addProperty("UrunAciklama", textInputEditTextUrunAciklama.getText().toString());
            Request.addProperty("Malzemeler", textInputEditTextMalzemeler.getText().toString());
            Request.addProperty("YapilmaSuresi", textInputEditYapilmaSuresi.getText().toString());
            Request.addProperty("UrunResim", "product.jpg");




            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                state = true;

            } catch (Exception e) {
                state = false;
            }
            return null;
        }

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(UrunBilgiEkleActivity.this, "", "Lütfen Bekleyin.", true);
        }


        @Override
        protected void onPostExecute(Object o) {

            dialog.dismiss();
            if (state) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ürün başarıyla kaydedildi. ", Toast.LENGTH_LONG);
                toast.show();
                Intent accountsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(accountsIntent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Ürün eklenirken bir hata ile karşılaşıldı. ", Toast.LENGTH_LONG);
                toast.show();
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
        getSupportActionBar().setTitle("Ürün Bilgilerini Ekle");

    }


}
