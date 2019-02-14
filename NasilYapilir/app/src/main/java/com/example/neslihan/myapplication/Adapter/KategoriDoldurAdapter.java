package com.example.neslihan.myapplication.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.activities.KategoriSecActivity;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.Urun;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class KategoriDoldurAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/KategoriList";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "KategoriList";
    public  List<String> kategoriList;
    LayoutInflater layoutInflater;
    private Spinner spUrunKat;
    Context context;
    public KategoriDoldurAdapter  (Context context)
    {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        kategoriList=new ArrayList<>();
        View urundetay =layoutInflater.inflate(R.layout.activity_urun_detay,null);
       // spUrunKat = urundetay.findViewById(R.id.spUrunKat);
       // spUrunKat.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spUrunKat.setAdapter(dataAdapter);
        //new ConversionAsyncTask().execute();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ConversionAsyncTask extends AsyncTask<Void, Void, Void> {
        JSONArray jarray;

        public ConversionAsyncTask() {
            super();
            //my params here
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
                    Kategori mkategori = new Kategori(Id, KategoriAd, Gorsel);
                    kategoriList.add(KategoriAd);

                }
                View urundetay =layoutInflater.inflate(R.layout.activity_urun_detay,null);
                //spUrunKat = urundetay.findViewById(R.id.spUrunKat);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, kategoriList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               // spUrunKat.setAdapter(dataAdapter);




            } catch (Exception e) {

            }
        }
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
}
