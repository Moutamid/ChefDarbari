package com.moutamid.chefdarbarii.affiliate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.FragmentCustomerItemsBinding;


public class CustomerItemsFragment extends Fragment {

    private FragmentCustomerItemsBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCustomerItemsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

        return root;
    }
}