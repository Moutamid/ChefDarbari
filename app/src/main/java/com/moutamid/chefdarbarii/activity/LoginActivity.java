package com.moutamid.chefdarbarii.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.activity.details.DetailsActivity;
import com.moutamid.chefdarbarii.databinding.ActivityLoginBinding;
import com.moutamid.chefdarbarii.models.AffiliateUserModel;
import com.moutamid.chefdarbarii.models.ChefUserModel;
import com.moutamid.chefdarbarii.utils.AppCOntext;
import com.moutamid.chefdarbarii.utils.Constants;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbarii.chef.ChefNavigationActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding b;

    String CURRENT_USER_TYPE = Constants.CHEF;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        b.privacyButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.chefdarbari.com/privacy.html"));
            startActivity(browserIntent);
        });

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
                Log.d(TAG, "onClick: ");
                String email = b.emailEt.getText().toString();
                String pass = b.password.getText().toString();

                if (email.isEmpty())
                    return;
                if (pass.isEmpty())
                    return;
                progressDialog.show();
                Constants.auth().signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            Log.d(TAG, "addOnCompleteListener: ");
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onClick: if (task.isSuccessful()) {");
                                String path;
                                if (CURRENT_USER_TYPE.equals(Constants.AFFILIATE)) {
                                    path = Constants.AFFILIATE;
                                } else {
                                    path = Constants.CHEF;
                                }
                                Constants.databaseReference().child(Constants.USERS)
                                        .child(path)
                                        .child(Constants.auth().getCurrentUser().getUid())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Log.d(TAG, "onDataChange: addValueEventListener");
                                                if (snapshot.exists()) {
                                                    Log.d(TAG, "onDataChange: if (snapshot.exists()) {");
                                                    if (CURRENT_USER_TYPE.equals(Constants.AFFILIATE)) {
                                                        Stash.put(Constants.CURRENT_AFFILIATE_MODEL,
                                                                snapshot.getValue(AffiliateUserModel.class));
                                                    } else {
                                                        Stash.put(Constants.CURRENT_CHEF_MODEL,
                                                                snapshot.getValue(ChefUserModel.class));
                                                    }
                                                    progressDialog.dismiss();
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
                                                    Log.d(TAG, "onDataChange: finish");
                                                } else {
                                                    Log.d(TAG, "onDataChange: } else {");
                                                    Toast.makeText(LoginActivity.this, "No data exist", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    Constants.auth().signOut();
                                                    Stash.clearAll();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.d(TAG, "onCancelled: ");
                                                Constants.auth().signOut();
                                                progressDialog.dismiss();
                                                Stash.clearAll();
                                                Toast.makeText(LoginActivity.this, error.toException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "onClick: } else {");
                                Constants.auth().signOut();
                                Stash.clearAll();
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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