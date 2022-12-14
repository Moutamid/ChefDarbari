package com.moutamid.chefdarbarii.utils;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.models.AffiliateUserModel;
import com.moutamid.chefdarbarii.models.ChefUserModel;

public class AppCOntext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);

        if (Constants.auth().getCurrentUser() == null)
            return;

        String path;
        if (Stash.getString(Constants.USER_TYPE).equals(Constants.CHEF)) {
            path = Constants.CHEF;
        } else {
            path = Constants.AFFILIATE;
        }

        Constants.databaseReference().child(Constants.USERS)
                .child(path)
                .child(Constants.auth().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (Stash.getString(Constants.USER_TYPE).equals(Constants.CHEF)) {
                                Stash.put(Constants.CURRENT_CHEF_MODEL,
                                        snapshot.getValue(ChefUserModel.class));
                            } else {
                                Stash.put(Constants.CURRENT_AFFILIATE_MODEL,
                                        snapshot.getValue(AffiliateUserModel.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AppCOntext.this, error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
