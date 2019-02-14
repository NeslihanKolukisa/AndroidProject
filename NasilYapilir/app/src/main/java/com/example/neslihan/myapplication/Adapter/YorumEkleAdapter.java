package com.example.neslihan.myapplication.Adapter;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

public class YorumEkleAdapter extends BaseAdapter {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/YorumEkle";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "YorumEkle";
    String txtyorum;
    int UrunId;

    public YorumEkleAdapter(String txtYorum, int UrunId) {
        this.txtyorum=txtYorum;
        this.UrunId=UrunId;
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
        public ConversionAsyncTask() {
            super();
            //my params here
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KullaniciId", LoginActivity.user.getId());
            Request.addProperty("KullaniciAd", LoginActivity.user.getName());
            Request.addProperty("KullaniciGorsel", LoginActivity.user.getGorsel());
            Request.addProperty("UrunId", UrunId);
            Request.addProperty("Metin", txtyorum);
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
            return null;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(Object o) {
           // BegeniArtirAdapter begeniArtirAdapter = new BegeniArtirAdapter(u,"artir");
        }
    }
}
