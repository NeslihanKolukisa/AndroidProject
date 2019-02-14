package com.example.neslihan.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.neslihan.myapplication.R;

public class Tab3Fragment extends Fragment {
    private static final String TAG="TAG3Fragment";

    private Button btnTest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab3_fragment,container,false);
        btnTest = view.findViewById(R.id.btnTest3);



        return  view;
    }
}
