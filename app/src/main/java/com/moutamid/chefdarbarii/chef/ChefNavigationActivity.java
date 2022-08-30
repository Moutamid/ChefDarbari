package com.moutamid.chefdarbarii.chef;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.ActivityChefNavigationBinding;
import com.moutamid.chefdarbarii.utils.Constants;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ChefNavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityChefNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChefNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarChefNavigation.toolbar);
        Objects.requireNonNull(getSupportActionBar()).hide();

        FirebaseMessaging.getInstance().subscribeToTopic(Constants.CHEF_NOTIFICATIONS);

        DrawerLayout drawer = binding.drawerLayoutChef;
        binding.appBarChefNavigation.chefMenuBtn.setOnClickListener(v -> {
            if (drawer.isOpen())
                drawer.close();
            else
                drawer.open();
        });

        NavigationView navigationView = binding.navViewChef;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_accepted_jobs,
                R.id.navigation_completed_jobs,
                R.id.navigation_show_id,
                R.id.navigation_terms,
                R.id.navigation_profile
        )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_chef_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chef_navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_chef_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}