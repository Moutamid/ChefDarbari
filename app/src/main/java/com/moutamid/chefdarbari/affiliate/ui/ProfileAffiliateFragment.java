package com.moutamid.chefdarbari.affiliate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.moutamid.chefdarbari.DetailsActivity;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentProfileAffiliateBinding;

public class ProfileAffiliateFragment extends Fragment {

    private FragmentProfileAffiliateBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentProfileAffiliateBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        b.accountTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_account_type,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.savings) {
                            b.accountTypeTv.setText("Savings");
                        }
                        if (menuItem.getItemId() == R.id.current) {
                            b.accountTypeTv.setText("Current");

                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return root;
    }


}