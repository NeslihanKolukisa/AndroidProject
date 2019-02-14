package com.example.neslihan.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.activities.LoginActivity;
import com.example.neslihan.myapplication.activities.UrunDetayActivity;
import com.example.neslihan.myapplication.activities.UrunGorselEkleActivity;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.Urun;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class OzelListKullaniciUrunAdaptor extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<Urun> list;
    Context context;
    public Urun urun;

    public OzelListKullaniciUrunAdaptor(Context context, List<Urun> mlist) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = mlist;
        this.context = context;
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
        final OzelListKullaniciUrunAdaptor.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.satir, parent, false);
            listViewHolder.imgUrunResim =(ImageView) convertView.findViewById(R.id.imgUrunResim);
            listViewHolder.txtUrunlerimBaslik = (TextView) convertView.findViewById(R.id.txtUrunlerimBaslik);
            listViewHolder.satirTextkategori = (TextView) convertView.findViewById(R.id.satirTextkategori);
            listViewHolder.imgKalp =(ImageView) convertView.findViewById(R.id.imgKalp);
            listViewHolder.btnSatinAl =(Button) convertView.findViewById(R.id.btnSatinAl);
            listViewHolder.txtBegeniSayisi = (TextView) convertView.findViewById(R.id.txtBegeniSayisi);
            listViewHolder.txtyorumSayisi = (TextView) convertView.findViewById(R.id.txtyorumSayisi);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        urun = list.get(position);
        listViewHolder.txtUrunlerimBaslik.setText(urun.getUrunBaslik());

        int resID = context.getResources().getIdentifier(urun.getUrunResim().substring(0, urun.getUrunResim().length() - 4), "drawable", context.getPackageName());
        listViewHolder.imgUrunResim.setBackgroundResource(resID);
        /*
        File file = new File(urun.getUrunResim()); //or any other format supported
        FileInputStream streamIn = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Uri bitmap = Uri.fromFile(file);
        listViewHolder.imgUrunResim.setImageURI(bitmap);
        */
        listViewHolder.imgUrunResim.setTag(urun.getId());
        listViewHolder.satirTextkategori.setText(urun.getUrunTanim());
        listViewHolder.txtBegeniSayisi.setText(urun.getBegeniSayisi() + "");
        listViewHolder.txtyorumSayisi.setText(urun.getyorumSayisi() + "");

        listViewHolder.imgUrunResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ImageView imageView = (ImageView) view;
                    int position = (int) imageView.getTag();
                    Intent myIntent = new Intent(context, UrunDetayActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtra("uId", position);
                    context.startActivity(myIntent);
                }
                catch (Exception e)
                {
                    e.getMessage();
                }
            }
        });
        return convertView;
    }
    public static class ViewHolder {
        ImageView imgUrunResim;
        ImageView imgKalp;
        TextView txtUrunlerimBaslik;
        TextView satirTextkategori;
        TextView txtBegeniSayisi;
        TextView txtyorumSayisi;
        Button btnSatinAl;

    }
}

