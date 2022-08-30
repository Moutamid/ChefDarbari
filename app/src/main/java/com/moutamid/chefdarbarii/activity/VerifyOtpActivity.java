package com.moutamid.chefdarbarii.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.chefdarbarii.R;

import java.util.Objects;

public class VerifyOtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        Objects.requireNonNull(getSupportActionBar()).hide();

    }
}