package com.moutamid.chefdarbari.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moutamid.chefdarbari.activity.details.DetailsActivity;
import com.moutamid.chefdarbari.utils.Constants;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbari.chef.ChefNavigationActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding b;

    String CURRENT_USER_TYPE = Constants.CHEF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        b.affiliateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_USER_TYPE = Constants.AFFILIATE;
                b.affiliateBtn.setBackgroundColor(getResources().getColor(R.color.default_blue));
                b.chefBtn.setBackgroundColor(getResources().getColor(R.color.default_grey));
            }
        });

        b.chefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_USER_TYPE = Constants.CHEF;
                b.chefBtn.setBackgroundColor(getResources().getColor(R.color.default_blue));
                b.affiliateBtn.setBackgroundColor(getResources().getColor(R.color.default_grey));
            }
        });

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_USER_TYPE.equals(Constants.AFFILIATE)) {
                    startActivity(new Intent(LoginActivity.this, AffiliateNavigationActivity.class)
                            .putExtra(Constants.PARAMS, CURRENT_USER_TYPE));
                } else {
                    startActivity(new Intent(LoginActivity.this, ChefNavigationActivity.class)
                            .putExtra(Constants.PARAMS, CURRENT_USER_TYPE));
                }
            }
        });
        findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DetailsActivity.class)
                        .putExtra(Constants.PARAMS, CURRENT_USER_TYPE));
            }
        });
    }
}