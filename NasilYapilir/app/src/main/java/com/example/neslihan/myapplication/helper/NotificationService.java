package com.example.neslihan.myapplication.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.neslihan.myapplication.Adapter.OzelListKullaniciUrunAdaptor;
import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.activities.LoginActivity;
import com.example.neslihan.myapplication.activities.ProfActivity;
import com.example.neslihan.myapplication.activities.UrunDetayActivity;
import com.example.neslihan.myapplication.model.Urun;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/pFavoriBildirimListesi";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "pFavoriBildirimListesi";

    public final static String SOAP_ACTIONl = "http://evdekiisim.somee.com/FavoriBildirimSil";
    private static final String METHOD_NAMEl = "FavoriBildirimSil";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        mTimer = new Timer();
        mTimer.schedule(timertask, 2000, 2 * 1000);
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Timer mTimer;
    TimerTask timertask = new TimerTask() {
        @Override
        public void run() {
            Log.e("Log", "Running");
            notifiy();
        }
    };

    @Override
    public void onDestroy() {
        try {
            mTimer.cancel();
            timertask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    static int degisken = 1;
    static Notification.Builder builder;
    static int requestID;

    public void notifiy() {

        new ConversionAsyncTask().execute();
    }


    public class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        boolean state;
        public ConversionAsyncTask() {
            super();
        }
        @Override
        protected Object doInBackground(Object[] objects) {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("AlanKulId", LoginActivity.user.getId());
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
            Requestl.addProperty("AlanKulId", LoginActivity.user.getId());
            SoapSerializationEnvelope envelopel = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelopel.dotNet = true;
            envelopel.setOutputSoapObject(Requestl);
            HttpTransportSE androidHttpTransportl = new HttpTransportSE(URL);
            try {
                androidHttpTransportl.call(SOAP_ACTIONl, envelopel);
                SoapPrimitive responsel = (SoapPrimitive) envelopel.getResponse();
                state = true;
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
                        JSONObject favoribildirim = jarray.getJSONObject(i);
                        int Id = favoribildirim.getInt("Id");
                        int GonderenKulId = favoribildirim.getInt("GonderenKulId");
                        int AlanKulId = favoribildirim.getInt("AlanKulId");
                        String KulAd = favoribildirim.getString("KulAd");
                        int urunId = favoribildirim.getInt("urunId");
                        String UrunBaslik = favoribildirim.getString("UrunBaslik");

                        /* */
                        String tittle="Nasıl Yapılır";
                        String subject="Nasıl Yapılır";
                        String body=KulAd + " adlı kullanıcı "+UrunBaslik+" ürününüzü beğendi";

                        Intent myIntent = new Intent(getApplicationContext(), UrunDetayActivity.class);
                        myIntent.putExtra("uId", urunId);

                        PendingIntent contentIntent =
                                PendingIntent.getActivity(getApplicationContext(), 0, myIntent, 0);

                        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notify=new Notification.Builder
                                (getApplicationContext())
                                .setContentTitle(tittle)
                                .setContentText(body)
                                .setContentTitle(subject)
                                .setContentIntent(contentIntent)
                                .setSmallIcon(R.drawable.iconmini).build();

                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        notif.notify(0, notify);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                    }

                } catch (Exception e) {

                }
            }
        }

    }

}
