package com.moutamid.chefdarbari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.chefdarbari.databinding.ActivityHomeCheffBinding;

import java.util.ArrayList;

public class HomeActivityCheff extends AppCompatActivity {

    private ActivityHomeCheffBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeCheffBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


    }



}