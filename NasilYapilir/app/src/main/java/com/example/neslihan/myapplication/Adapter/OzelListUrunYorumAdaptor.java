package com.example.neslihan.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.neslihan.myapplication.model.Favori;
import com.example.neslihan.myapplication.model.Kategori;
import com.example.neslihan.myapplication.model.Urun;
import com.example.neslihan.myapplication.model.Yorum;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;
public class OzelListUrunYorumAdaptor  extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;

    List<Yorum> list;
    public Yorum yorum;

    public OzelListUrunYorumAdaptor(Context context, List<Yorum> mlist) {
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
        final OzelListUrunYorumAdaptor.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.satiryorum, parent, false);
            listViewHolder.imgyorumprofil = (ImageView) convertView.findViewById(R.id.imgyorumprofil);
            listViewHolder.txtyorumprofil = (TextView) convertView.findViewById(R.id.txtyorumprofil);
            listViewHolder.txtyorummetni = (TextView) convertView.findViewById(R.id.txtyorummetni);
            listViewHolder.txtyorumtarih = (TextView) convertView.findViewById(R.id.txtyorumtarih);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        yorum = list.get(position);
        listViewHolder.txtyorumprofil.setText(yorum.getKullaniciAd());
        listViewHolder.txtyorummetni.setText(yorum.getMetin());
        listViewHolder.txtyorumtarih.setText(yorum.getTarih().substring(0,yorum.getTarih().length()-2));
        int resID = context.getResources().getIdentifier(yorum.getKullaniciGorsel().substring(0, yorum.getKullaniciGorsel().length() - 4), "drawable", context.getPackageName());
        listViewHolder.imgyorumprofil.setBackgroundResource(resID);
        return convertView;
    }
    public static class ViewHolder {
        ImageView imgyorumprofil;
        TextView txtyorumprofil;
        TextView txtyorummetni;
        TextView txtyorumtarih;
        Button picture;
    }
}
