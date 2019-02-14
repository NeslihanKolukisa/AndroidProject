package com.example.neslihan.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;
import com.example.neslihan.myapplication.activities.ProfActivity;
import com.example.neslihan.myapplication.activities.UrunDetayActivity;
import com.example.neslihan.myapplication.model.Favori;
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
import java.text.NumberFormat;
import java.util.List;

import static java.lang.System.in;

public class OzelListKategoriUrunAdaptor extends BaseAdapter {


    LayoutInflater layoutInflater;
    List<Urun> list;
    public static List<Favori> favList;
    Context context;
    public Urun urun;

    public OzelListKategoriUrunAdaptor(Context context, List<Urun> mlist,List<Favori> mllist) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = mlist;
        favList = mllist;
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
        final ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.satir, parent, false);
            listViewHolder.imgUrunResim = (ImageView) convertView.findViewById(R.id.imgUrunResim);
            listViewHolder.txtUrunlerimBaslik = (TextView) convertView.findViewById(R.id.txtUrunlerimBaslik);
            listViewHolder.satirTextkategori = (TextView) convertView.findViewById(R.id.satirTextkategori);
            listViewHolder.imgKalp = (ImageView) convertView.findViewById(R.id.imgKalp);
            listViewHolder.btnSatinAl = (Button) convertView.findViewById(R.id.btnSatinAl);
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
        File file = new File(urun.getUrunResim());
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
        FavoriListeAdapter list = new FavoriListeAdapter();
        listViewHolder.imgKalp.setImageResource(R.drawable.kalp);
        listViewHolder.imgKalp.setTag(R.drawable.kalp);
        for (Favori favori:favList) {
            if (favori.getUrunId() == urun.getId()) {
                listViewHolder.imgKalp.setImageResource(R.drawable.kalpb);
                listViewHolder.imgKalp.setTag(R.drawable.kalpb);
            }
        }
        listViewHolder.stUrunId = (urun.getId());
        listViewHolder.stKullaniciId = (urun.getKullaniciId());
        listViewHolder.stKategoriId = (urun.getKategoriId());
        listViewHolder.stUrunBaslik = (urun.getUrunBaslik());
        listViewHolder.stUrunTanim = (urun.getUrunTanim());
        listViewHolder.stUrunAciklama = (urun.getUrunAciklama());
        listViewHolder.stMalzemeler = (urun.getMalzemeler());
        listViewHolder.stYapilmaSuresi = (urun.getYapilmaSuresi());
        listViewHolder.stUrunResim = (urun.getUrunResim());
        listViewHolder.stBegeniSayisi = (urun.getBegeniSayisi());
        listViewHolder.stYorumSayisi = (urun.getyorumSayisi());


        listViewHolder.imgKalp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Urun urun = new Urun(listViewHolder.stUrunId,
                        listViewHolder.stKullaniciId,
                        listViewHolder.stKategoriId,
                        listViewHolder.stUrunBaslik.toString(),
                        listViewHolder.stUrunTanim.toString(),
                        listViewHolder.stUrunAciklama.toString(),
                        listViewHolder.stMalzemeler,
                        listViewHolder.stYapilmaSuresi,
                        listViewHolder.stUrunResim,
                        listViewHolder.stBegeniSayisi,
                        listViewHolder.stYorumSayisi);
                ImageView imageView = (ImageView) view;
                assert (R.id.imgKalp == imageView.getId());
                Integer integer = (Integer) listViewHolder.imgKalp.getTag();
                integer = integer == null ? 0 : integer;
                switch (integer) {
                    case (R.drawable.kalpb): {
                        listViewHolder.imgKalp.setTag((R.drawable.kalp));
                        listViewHolder.imgKalp.setImageResource((R.drawable.kalp));
                        FavoriSilAdapter favoriSilAdapter = new FavoriSilAdapter(urun);
                        listViewHolder.txtBegeniSayisi.setText(urun.getBegeniSayisi()-1 + "");
                        break;
                    }
                    case (R.drawable.kalp): {
                        listViewHolder.imgKalp.setTag((R.drawable.kalpb));
                        listViewHolder.imgKalp.setImageResource((R.drawable.kalpb));
                        FavoriEkleAdapter favoriEkleAdapter = new FavoriEkleAdapter(urun);
                        listViewHolder.txtBegeniSayisi.setText(urun.getBegeniSayisi()+1 + "");
                        break;
                    }
                }
            }
        });

        listViewHolder.imgUrunResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ImageView imageView = (ImageView) view;
                    int urunId = (int) imageView.getTag();
                    Intent myIntent = new Intent(context, UrunDetayActivity.class);
                    myIntent.putExtra("uId", urunId);
                    context.startActivity(myIntent);
                } catch (Exception e) {
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

        int stUrunId;
        int stKullaniciId;
        int stKategoriId;
        String stUrunBaslik;
        String stUrunTanim;
        String stUrunAciklama;
        String stMalzemeler;
        int stYapilmaSuresi;
        String stUrunResim;
        int stBegeniSayisi;
        int stYorumSayisi;
    }
}

