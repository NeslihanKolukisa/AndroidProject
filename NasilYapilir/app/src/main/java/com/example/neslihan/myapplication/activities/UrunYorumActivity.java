package com.example.neslihan.myapplication.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.neslihan.myapplication.Adapter.OzelListKullaniciUrunAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListUrunYorumAdaptor;
import com.example.neslihan.myapplication.Adapter.YorumEkleAdapter;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.User;
import com.example.neslihan.myapplication.model.Yorum;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class UrunYorumActivity   extends AppCompatActivity {


    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/UrunYorumList";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "UrunYorumList";

    private static List<Yorum> yorumlist;
    public static ListView listYorum;
    private Context context;
    private int UrunId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_yorum);
        Bundle b = getIntent().getExtras();
        int id = b.getInt("uId");
        this.UrunId=id;
        yorumlist=new ArrayList<>();
        this.context=UrunYorumActivity.this;
        listYorum = (ListView) findViewById(R.id.listYorum);
        statusBarColor();
        ImageView imgYorumGonder = findViewById(R.id.imgYorumGonder);
        final TextInputEditText txtYorumGonder = findViewById(R.id.txtYorumGonder);

        imgYorumGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtYorumGonder.getText()!=null) {
                    YorumEkleAdapter yorumEkleAdapter = new YorumEkleAdapter(txtYorumGonder.getText().toString(), UrunId);
                }
                finish();
                startActivity(getIntent());
                txtYorumGonder.setText("");

                Toast toast = Toast.makeText(getApplicationContext(), "Yorumunuz Kaydedildi ", Toast.LENGTH_LONG);
                toast.show();


            }});

        new ConversionAsyncTask().execute();
    }
    public class ConversionAsyncTask extends AsyncTask{
        JSONArray jarray;
        public ConversionAsyncTask() {
            super();
            //my params here
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("uId", UrunId);
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
                        JSONObject yorum = jarray.getJSONObject(i);
                        int Id = yorum.getInt("Id");
                        int KullaniciId = yorum.getInt("KullaniciId");
                        String KullaniciAd = yorum.getString("KullaniciAd");
                        String KullaniciGorsel = yorum.getString("KullaniciGorsel");
                        int UrunId = yorum.getInt("UrunId");
                        String Metin = yorum.getString("Metin");
                        String Tarih = yorum.getString("Tarih");
                        Yorum yoruml = new Yorum(Id, KullaniciId, KullaniciAd,KullaniciGorsel, UrunId,Metin,Tarih);
                        yorumlist.add(yoruml);
                    }

                    OzelListUrunYorumAdaptor adaptery = new OzelListUrunYorumAdaptor(context, yorumlist);
                    int a=adaptery.getCount();
                    listYorum.setAdapter(adaptery);
                }
                catch (Exception e) {
                    e.getMessage();
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
        getSupportActionBar().setTitle("YORUMLAR");

    }


}
