package com.example.neslihan.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.neslihan.myapplication.R;

public class GirisEkrani extends Activity {
    public void onCreate(Bundle giris)
    {

        super.onCreate(giris);
        setContentView(R.layout.girisekrani);
        final Thread zamanlayici=new Thread(){
          public void run(){
              try {
                  sleep(4000);
              }
              catch (InterruptedException e)
              {
                  e.printStackTrace();
                  Log.i("tago","zamanlayici çalışmadı");
              }
              finally {
                  Intent accountsIntent = new Intent(getApplicationContext(), LoginActivity.class);
                  startActivity(accountsIntent);

              }
          }
        };
        zamanlayici.start();

    }

}
