package com.moutamid.chefdarbarii.affiliate;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.chef.ChefNavigationActivity;
import com.moutamid.chefdarbarii.databinding.ActivityAffiliateNavigationBinding;
import com.moutamid.chefdarbarii.utils.Constants;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AffiliateNavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAffiliateNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffiliateNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarAffiliateNavigation.toolbar);
        /*binding.appBarAffiliateNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getServerKey();

        DrawerLayout drawer = binding.drawerLayoutAffiliate;
        binding.appBarAffiliateNavigation.affiliateMenuBtn.setOnClickListener(v -> {
            if (drawer.isOpen())
                drawer.close();
            else
                drawer.open();
        });

        NavigationView navigationView = binding.navViewAffiliate;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_add_jobs,
                R.id.navigation_my_bookings,
                R.id.navigation_customer_items,
                R.id.navigation_profile_affiliate)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_affiliate_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void getServerKey() {
        if (Stash.getString(Constants.KEY, "n").equals("n")) {
            ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(AffiliateNavigationActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            Constants.databaseReference()
                    .child("server_key")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if (snapshot.exists()) {
                                Stash.put(Constants.KEY, snapshot.getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AffiliateNavigationActivity.this, error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.affiliate_navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_affiliate_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}