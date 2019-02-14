package com.example.neslihan.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.activities.KategoriSecActivity;
import com.example.neslihan.myapplication.activities.LoginActivity;
import com.example.neslihan.myapplication.activities.MainActivity;
import com.example.neslihan.myapplication.activities.UrunGorselEkleActivity;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.List;

public class OzelListKategoriAdaptor extends BaseAdapter {

    public final static String URL = "http://evdekiisim.somee.com/WebService1.asmx";
    public final static String SOAP_ACTION = "http://evdekiisim.somee.com/katIdGetir";
    public static final String NAMESPACE = "http://evdekiisim.somee.com/";
    private static final String METHOD_NAME = "katIdGetir";

    private String donusecekKat;
    private int donusenKat;
    public static String PACKAGE_NAME;
    LayoutInflater layoutInflater;
    List<Kategori> list;
    Context context;
    public Kategori kat;
    public OzelListKategoriAdaptor  (Context context, List<Kategori> mlist)
    {
        layoutInflater= (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list=mlist;
        this.context=context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            final ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.kategori, parent, false);
                listViewHolder.katAd = (TextView) convertView.findViewById(R.id.katAd);
                listViewHolder.katCizgi = convertView.findViewById(R.id.katCizgi);
                listViewHolder.imgKatResim = convertView.findViewById(R.id.imgKatResim);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }
            kat = list.get(position);
            listViewHolder.katAd.setText(kat.getKategoriAd());
            int resID = context.getResources().getIdentifier(kat.getGorsel().substring(0, kat.getGorsel().length() - 4), "drawable", context.getPackageName());
            listViewHolder.imgKatResim.setBackgroundResource(resID);
            listViewHolder.katAd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {


                        Intent myIntent = new Intent(context.getApplicationContext(), UrunGorselEkleActivity.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtra("katId", kat.getId());
                        context.getApplicationContext().startActivity(myIntent);
                    }
                    catch (Exception e)
                    {
                        e.getMessage();
                    }
                }
            });
            return convertView;
        }
        catch (Exception e)
        {

        }
        return null;
    }

    public static class ViewHolder{
        TextView katAd;
        ImageView katCizgi;
        ImageView imgKatResim;
    }
}
