package com.moutamid.chefdarbari.affiliate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentAcceptedJobsBinding;
import com.moutamid.chefdarbari.databinding.FragmentAddNewBookingsBinding;
import com.moutamid.chefdarbari.databinding.FragmentCustomerItemsBinding;

public class AddNewBookingsFragment extends Fragment {

    private FragmentAddNewBookingsBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAddNewBookingsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        b.occasionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_occasions,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.occasionTv.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return root;
    }
}