package com.moutamid.chefdarbarii.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.moutamid.chefdarbarii.activity.details.DetailsActivity;
import com.moutamid.chefdarbarii.databinding.ActivityLoginBinding;
import com.moutamid.chefdarbarii.utils.Constants;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbarii.chef.ChefNavigationActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding b;

    String CURRENT_USER_TYPE = Constants.CHEF;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

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
                String email = b.emailEt.getText().toString();
                String pass = b.password.getText().toString();

                if (email.isEmpty())
                    return;
                if (pass.isEmpty())
                    return;
                progressDialog.show();
                Constants.auth().signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    if (CURRENT_USER_TYPE.equals(Constants.AFFILIATE)) {
                                        Stash.put(Constants.USER_TYPE, Constants.AFFILIATE);
                                        startActivity(new Intent(LoginActivity.this, AffiliateNavigationActivity.class)
                                                .putExtra(Constants.PARAMS, CURRENT_USER_TYPE));
                                    } else {
                                        Stash.put(Constants.USER_TYPE, Constants.CHEF);
                                        startActivity(new Intent(LoginActivity.this, ChefNavigationActivity.class)
                                                .putExtra(Constants.PARAMS, CURRENT_USER_TYPE));
                                    }
                                }else {
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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