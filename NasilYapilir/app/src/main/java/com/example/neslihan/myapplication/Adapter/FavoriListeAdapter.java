package com.example.neslihan.myapplication.Adapter;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.neslihan.myapplication.Fragment.Tab2Fragment;
import com.example.neslihan.myapplication.activities.LoginActivity;
import com.example.neslihan.myapplication.activities.MainActivity;
import com.example.neslihan.myapplication.model.Favori;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FavoriListeAdapter extends BaseAdapter {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/KullanıcıFavList"; //degistir hangisi olcaksa
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "KullanıcıFavList";
    public static List<Favori> favoriListesi;

    public FavoriListeAdapter() {
        favoriListesi=new ArrayList<>();
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

        public ConversionAsyncTask() {
            super();
            //my params here
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("kulId", LoginActivity.user.getId());
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
            return "task finished";
        }
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected void onPostExecute(Object o) {
            try
            {
                for (int i = 0; i < jarray.length();i++)
                {
                    JSONObject urun = jarray.getJSONObject(i);
                    int Id = urun.getInt("Id");
                    int KullaniciId = urun.getInt("KullaniciId");
                    int UrunId = urun.getInt("UrunId");
                    int EkleyenKulId = urun.getInt("EkleyenKulId");
                    int KategoriId = urun.getInt("KategoriId");
                    String urunBaslik = urun.getString("UrunBaslik");
                    String urunTanim = urun.getString("UrunTanim");
                    String UrunAciklama = urun.getString("UrunAciklama");

                    String Malzemeler = urun.getString("Malzemeler");
                    int YapilmaSuresi = urun.getInt("YapilmaSuresi");
                    String UrunResim = urun.getString("UrunResim");
                    int BegeniSayisi= urun.getInt("BegeniSayisi");
                    int YorumSayisi= urun.getInt("YorumSayisi");
                    Favori favori = new Favori(Id,KullaniciId,UrunId,EkleyenKulId, KategoriId,urunBaslik, urunTanim,UrunAciklama,Malzemeler,YapilmaSuresi,UrunResim,BegeniSayisi,YorumSayisi);
                    favoriListesi.add(favori);

                }


            }
            catch (Exception e)
            {

            }
        }
    }
    public List<Favori> myHandledValueType()

    {
        return favoriListesi;
    }

}
