package com.example.neslihan.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
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
import java.io.IOException;
import java.util.jar.Attributes;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/Login";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "Login";
    public static User user;
    private final AppCompatActivity activity = LoginActivity.this;
    private android.widget.ScrollView ScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private InputValidation inputValidation;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
        initObjects();
        statusBarColor();
        /*
        textInputEditTextEmail.setText("neslihan");
        textInputEditTextPassword.setText("12");*/
       /* new ConversionAsyncTask().execute();*/

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
    private void initViews() {
        ScrollView = (ScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
    }
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new ConversionAsyncTask().execute();
            }
        });
        textViewLinkRegister.setOnClickListener(this);
    }
    private void initObjects() {
        inputValidation = new InputValidation(activity);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email
        ))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email
        ))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password
        ))) {
            return;
        }
        String tkcK = textInputEditTextEmail.getText().toString();
        Cursor crs = dbHelper.getDatabase().query("Kullanici", new String[]{"KullaniciId"}, "Email = ?", new String[]{tkcK}, null, null, null);
        if (crs.getCount() > 0) {
            crs.moveToFirst();
            String kt = crs.getString(crs.getColumnIndex("Ad"));
            Intent accountsIntent = new Intent(activity, UsersActivity.class);
            accountsIntent.putExtra("EMAIL", kt);
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Snackbar.make(ScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
    private class ConversionAsyncTask extends AsyncTask {
        JSONArray jarray;
        JSONObject kullanici;
        @Override
        protected Object doInBackground(Object[] objects) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("userName", textInputEditTextEmail.getText().toString());
            Request.addProperty("password", textInputEditTextPassword.getText().toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String responseJSON = response.toString();

                kullanici = new JSONObject(responseJSON);

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
            if (kullanici != null) {
                try {
                    int Id = kullanici.getInt("Id");
                    String KullaniciAdi =  kullanici.getString("KullaniciAd");
                    String Password = kullanici.getString("Parola");
                    String Mail = kullanici.getString("Mail");
                    String Gorsel = kullanici.getString("Gorsel");
                    String Arka = kullanici.getString("Arka");
                    user=new User(Id,KullaniciAdi,Password,Mail,Gorsel,Arka);
                    Toast toast = Toast.makeText(getApplicationContext(), "Hoşgeldin " + user.getName(), Toast.LENGTH_LONG);
                    toast.show();
                    Intent accountsIntent = new Intent(activity, MainActivity.class);
                    accountsIntent.putExtra("EMAIL", KullaniciAdi);
                    accountsIntent.putExtra("Id", Id);
                    emptyInputEditText();
                    startActivity(accountsIntent);
                } catch (Exception e) {
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Hatalı girdiniz", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


}
