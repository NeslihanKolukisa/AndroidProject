package com.example.neslihan.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neslihan.myapplication.Adapter.OzelListKategoriAdaptor;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.helper.InputValidation;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.User;
import com.example.neslihan.myapplication.sql.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KategoriSecActivity   extends AppCompatActivity implements View.OnClickListener {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/KategoriList";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "KategoriList";


    public static List<Kategori> kategoriList;
   // public static ListView katlist;
   public static GridView gridView;
   private static Context context;
   private Button katPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_sec);
        //katlist = findViewById(R.id.katlist);
        //getActionBar().hide();
        gridView = (GridView) findViewById(R.id.gridview);
        final LayoutInflater factory = getLayoutInflater();
        View multi =factory.inflate(R.layout.multic,null);
        katPicture =   multi.findViewById(R.id.picture);

/*
        katPicture.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

            }
        });*/
        initViews();
        initListeners();
        initObjects();
        init();
        statusBarColor();
        new ConversionAsyncTask().execute();
    }

    private void init() {
        kategoriList=new ArrayList<>();context=KategoriSecActivity.this;
    }


    private void initViews() {


    }

    private void initListeners() {
    //   katPicture.setOnClickListener(this);

    }

    private void initObjects() {

    }

    @Override
    public void onClick(View v) {
       // Toast toast = Toast.makeText(getApplicationContext(), "Hoşgeldin " , Toast.LENGTH_LONG);
        //toast.show();
    }





    public class ConversionAsyncTask extends AsyncTask<Void, Void, Void> {
        JSONArray jarray;
        public ConversionAsyncTask() {
            super();
            //my params here
        }
        @Override
        protected Void  doInBackground(Void... params) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
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
        protected void onPostExecute(Void result) {



            try {
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject kategori = jarray.getJSONObject(i);
                    int Id = kategori.getInt("Id");
                    String KategoriAd = kategori.getString("KategoriAd");
                    String Gorsel = kategori.getString("Gorsel");
                    Kategori mkategori = new Kategori(Id, KategoriAd,Gorsel);
                    KategoriSecActivity.kategoriList.add(mkategori);


                }
                OzelListKategoriAdaptor adapter=new OzelListKategoriAdaptor(context, kategoriList);
                gridView.setAdapter(adapter);
                kacadet();
            } catch (Exception e) {

            }
        }

    }

    private void kacadet() {
        int a=kategoriList.size();
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
        getSupportActionBar().setTitle("Kategori Seç");

    }

}
