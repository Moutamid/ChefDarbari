package com.moutamid.chefdarbarii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbarii.chef.ChefNavigationActivity;
import com.moutamid.chefdarbarii.utils.Constants;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            if (Constants.auth().getCurrentUser() == null)
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            else {
                if (Stash.getString(Constants.USER_TYPE).equals(Constants.AFFILIATE)) {
                    startActivity(new Intent(SplashActivity.this, AffiliateNavigationActivity.class)
                            .putExtra(Constants.PARAMS, Constants.AFFILIATE));
                } else {
                    startActivity(new Intent(SplashActivity.this, ChefNavigationActivity.class)
                            .putExtra(Constants.PARAMS, Constants.CHEF));
                }
            }
        }, 3000);

    }
}
