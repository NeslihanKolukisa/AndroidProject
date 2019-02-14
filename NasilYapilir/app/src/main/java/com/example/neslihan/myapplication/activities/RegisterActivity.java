package com.example.neslihan.myapplication.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.helper.InputValidation;
import com.example.neslihan.myapplication.model.User;
import com.example.neslihan.myapplication.sql.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/Register";  // şu an login methodu üzerinde işlem yapacağız ok ? ok
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "Register";

    private final AppCompatActivity activity = RegisterActivity.this;
    private ScrollView ScrollView;


    private TextInputLayout textInputLayoutKullaniciAdi;
    private TextInputLayout textInputLayoutSifre;
    private TextInputLayout textInputConfirmPassword;
    private TextInputLayout textInputLayoutAd;
    private TextInputLayout textInputLayoutSoyad;
    private TextInputLayout textInputLayoutTC;
    private TextInputLayout textInputLayoutTelefon;
    private TextInputLayout textInputLayoutMail;
    private TextInputLayout textInputLayoutAdres;


    private TextInputEditText textInputEditTextKullaniciAdi;
    private TextInputEditText textInputEditTextSifre;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextAd;
    private TextInputEditText textInputEditTextSoyad;
    private TextInputEditText textInputEditTextTC;
    private TextInputEditText textInputEditTextTelefon;
    private TextInputEditText textInputEditTextMail;
    private TextInputEditText textInputEditTextAdres;

    private RadioGroup rol;

    private AppCompatButton appCompatButtonRegister;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;


    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
        statusBarColor();
    }

    private void statusBarColor() {
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBar));
        }
    }

    public void initViews() {
        ScrollView = findViewById(R.id.nestedScrollView);
        textInputLayoutKullaniciAdi = findViewById(R.id.textInputLayoutKullaniciAdi);
        textInputLayoutSifre = findViewById(R.id.textInputLayoutSifre);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        textInputLayoutAd = findViewById(R.id.textInputLayoutAd);
        textInputLayoutSoyad = findViewById(R.id.textInputLayoutSoyad);
        textInputLayoutTC = findViewById(R.id.textInputLayoutTC);
        textInputLayoutTelefon = findViewById(R.id.textInputLayoutTelefon);
        textInputLayoutMail = findViewById(R.id.textInputLayoutMail);
        textInputLayoutAdres = findViewById(R.id.textInputLayoutAdres);

        textInputEditTextKullaniciAdi = findViewById(R.id.textInputEditTextKullaniciAdi);
        textInputEditTextSifre = findViewById(R.id.textInputEditTextSifre);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextAd = findViewById(R.id.textInputEditTextAd);
        textInputEditTextSoyad = findViewById(R.id.textInputEditTextSoyad);
        textInputEditTextTC = findViewById(R.id.textInputEditTextTC);
        textInputEditTextTelefon = findViewById(R.id.textInputEditTextTelefon);
        textInputEditTextMail = findViewById(R.id.textInputEditTextMail);
        textInputEditTextAdres = findViewById(R.id.textInputEditTextAdres);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);


    }

    public boolean validate() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextAd, textInputLayoutAd, getString(R.string.error_message_name
        ))) {
            return false;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextSifre, textInputEditTextConfirmPassword, textInputConfirmPassword, getString(R.string.error_password_match))) {
            ScrollView.smoothScrollTo(0, 0);
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextSifre, textInputLayoutSifre, getString(R.string.error_message_password
        ))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextMail, textInputLayoutMail, getString(R.string.error_message_email
        ))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextMail, textInputLayoutMail, getString(R.string.error_message_email
        ))) {
            ScrollView.smoothScrollTo(0,250);

            return false;
        }

        return true;
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    new ConversionAsyncTask().execute();
                }

            }
        });
        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                new ConversionAsyncTask().execute();
                break;

            case R.id.textViewLinkRegister:
                //
                //
                break;
        }
        /*
        switch (v.getId())
        {
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.textViewLinkRegister:
              finish();
                break;
        }

        */
    }

    private class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        JSONObject kullanici;
        boolean state;

        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("KullaniciAd", textInputEditTextKullaniciAdi.getText().toString());
            Request.addProperty("Sifre", textInputEditTextSifre.getText().toString());
            Request.addProperty("Ad", textInputEditTextAd.getText().toString());
            Request.addProperty("Soyad", textInputEditTextSoyad.getText().toString());
            Request.addProperty("Tc", textInputEditTextTC.getText().toString());
            Request.addProperty("Telefon", textInputEditTextTelefon.getText().toString());
            Request.addProperty("Mail", textInputEditTextMail.getText().toString());
            Request.addProperty("Adres", textInputEditTextAdres.getText().toString());
            Request.addProperty("Rol", "");
            Request.addProperty("Gorsel", "userlogo.jpg");
            Request.addProperty("Arka", "profilarka.jpg");


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
            dialog = ProgressDialog.show(RegisterActivity.this, "", "Lütfen Bekleyin.", true);

        }


        @Override
        protected void onPostExecute(Object o) {

            dialog.dismiss();
            if (state) {
                Toast toast = Toast.makeText(getApplicationContext(), "Üye kaydınız başarıyla yapıldı. ", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Üye kaydınız yapılırken bir hata oluştu. ", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }


    private void postDataToSQLite() {
        /*
        if(!inputValidation.isInputEditTextFilled(textInputEditTextName,textInputLayoutName,getString(R.string.error_message_name
        ))){return;}
        if(!inputValidation.isInputEditTextFilled(textInputEditTextEmail,textInputLayoutEmail,getString(R.string.error_message_email
        ))){return;}
        if(!inputValidation.isInputEditTextEmail(textInputEditTextEmail,textInputLayoutEmail,getString(R.string.error_message_email
        ))){return;}
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword,textInputLayoutPassword,getString(R.string.error_message_password
        ))){return;}
        if(!inputValidation.isInputEditTextMatches(textInputEditTextPassword,textInputEditTextConfirmPassword,textInputLayoutConfirmPassword,getString(R.string.error_password_match)))
        {
            return;
        }
        String turkceKelime = textInputEditTextEmail.getText().toString();
        Cursor crs = dbHelper.getDatabase().query("Kullanıcılar", new String[]{ "KullaniciId" }, "Email = ?", new String[]{ turkceKelime }, null, null, null);
        if(crs.getCount()>0){
            Snackbar.make(ScrollView,getString(R.string.error_email_exits),Snackbar.LENGTH_LONG).show();
        }else{
            user.setName(textInputEditTextName.getText().toString());
            user.setEmail(textInputEditTextEmail.getText().toString());
            user.setPassword(textInputEditTextPassword.getText().toString());
            dbHelper.addUser(user);

            Snackbar.make(ScrollView,getString(R.string.success_message_register),Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }

*/
    }

    private void emptyInputEditText() {
        textInputEditTextKullaniciAdi.setText(null);
        textInputEditTextSifre.setText(null);
        textInputEditTextAd.setText(null);
        textInputEditTextSoyad.setText(null);
        textInputEditTextTC.setText(null);
        textInputEditTextTelefon.setText(null);
        textInputEditTextMail.setText(null);
        textInputEditTextAdres.setText(null);
    }


}
