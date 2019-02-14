package com.example.neslihan.myapplication.Adapter;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.neslihan.myapplication.Fragment.Tab1Fragment;
import com.example.neslihan.myapplication.activities.LoginActivity;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class FavoriEkleAdapter  extends BaseAdapter {
    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/FavoriEkle";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "FavoriEkle";

    public final static String SOAP_ACTIONl = "http://evdekiisim.somee.com/FavoriBildirimEkle";
    private static final String METHOD_NAMEl = "FavoriBildirimEkle";

    private Urun u;
    public FavoriEkleAdapter(Urun u) {
        this.u=u;
        new ConversionAsyncTask().execute();

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        JSONObject fav;
        boolean state;
        boolean statel;
        public ConversionAsyncTask() {
            super();
            //my params here
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KullaniciId", LoginActivity.user.getId());
            Request.addProperty("UrunId", u.getId());
            Request.addProperty("EkleyenKulId", u.getKullaniciId());
            Request.addProperty("KategoriId", u.getKategoriId());
            Request.addProperty("UrunBaslik", u.getUrunBaslik());
            Request.addProperty("UrunTanim", u.getUrunTanim());
            Request.addProperty("UrunAciklama", u.getUrunAciklama());
            Request.addProperty("Malzemeler", u.getMalzemeler());
            Request.addProperty("YapilmaSuresi", u.getYapilmaSuresi());
            Request.addProperty("UrunResim", u.getUrunResim());
            Request.addProperty("BegeniSayisi", u.getBegeniSayisi()+1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                state = true;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }




            SoapObject Requestl = new SoapObject(NAMESPACE, METHOD_NAMEl);
            Requestl.addProperty("GonderenKulId", LoginActivity.user.getId());
            Requestl.addProperty("AlanKulId", u.getKullaniciId());
            Requestl.addProperty("UrunId", u.getId());
            SoapSerializationEnvelope envelopel = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelopel.dotNet = true;
            envelopel.setOutputSoapObject(Requestl);
            HttpTransportSE androidHttpTransportl = new HttpTransportSE(URL);
            try {
                androidHttpTransportl.call(SOAP_ACTIONl, envelopel);
                SoapPrimitive responsel = (SoapPrimitive) envelopel.getResponse();
                statel = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(Object o) {
            BegeniArtirAdapter begeniArtirAdapter = new BegeniArtirAdapter(u,"artir");
        }
    }

}
