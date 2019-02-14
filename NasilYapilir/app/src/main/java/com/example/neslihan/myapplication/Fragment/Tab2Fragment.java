package com.example.neslihan.myapplication.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.neslihan.myapplication.Adapter.OzelListAdaptor;
import com.example.neslihan.myapplication.Adapter.OzelListFavoriAdaptor;
import com.example.neslihan.myapplication.R;
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

public class Tab2Fragment extends Fragment {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/getUrunListYorum";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "getUrunListYorum";

    public final static String SOAP_ACTIONl = "http://evdekiisim.somee.com/KullanıcıFavList";
    private static final String METHOD_NAMEl = "KullanıcıFavList";

    private List<Favori> favList;
    private static List<Urun> urunList;
    public static GridView gridview;
    private static final String TAG="Tab1Fragment";
    ViewPager viewPager;
    private static Fragment context;


    public static ListView list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab2_fragment,container,false);

        gridview = (GridView) view.findViewById(R.id.gridview);
        init();
        new ConversionAsyncTask().execute();

        return  view;
    }
    private void init()
    {
        urunList=new ArrayList<>();
        favList=new ArrayList<>();
        context=Tab2Fragment.this;
    }
    public class ConversionAsyncTask extends AsyncTask<Void, Void, Void> {
        JSONArray jarray;
        JSONArray jarrayl;
        public ConversionAsyncTask() {
            super();
        }

        @Override
        protected Void doInBackground(Void... params) {

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

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            if (jarray != null) {
                try {
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject urun = jarray.getJSONObject(i);
                        int Id = urun.getInt("Id");
                        int KullaniciId = urun.getInt("KullaniciId");
                        int KategoriId = urun.getInt("KategoriId");
                        String urunBaslik = urun.getString("UrunBaslik");
                        String urunTanim = urun.getString("UrunTanim");
                        String UrunAciklama = urun.getString("UrunAciklama");
                        String Malzemeler = urun.getString("Malzemeler");
                        int YapilmaSuresi = urun.getInt("YapilmaSuresi");
                        String UrunResim = urun.getString("UrunResim");
                        int BegeniSayisi = urun.getInt("BegeniSayisi");
                        int yorumSayisi = urun.getInt("yorumSayisi");
                        Urun urunl = new Urun(Id, KullaniciId, KategoriId, urunBaslik, urunTanim, UrunAciklama, Malzemeler, YapilmaSuresi, UrunResim, BegeniSayisi,yorumSayisi);
                        urunList.add(urunl);
                    }
                }
                catch (Exception e)
                {
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

            OzelListFavoriAdaptor adapter = new OzelListFavoriAdaptor(context, urunList,favList);
            gridview.setAdapter(adapter);
        }
    }
}
