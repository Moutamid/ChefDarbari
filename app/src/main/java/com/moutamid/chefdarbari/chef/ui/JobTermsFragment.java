package com.moutamid.chefdarbari.chef.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moutamid.chefdarbari.databinding.FragmentJobTermsBinding;


public class JobTermsFragment extends Fragment {

    private FragmentJobTermsBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentJobTermsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

        return root;
    }


}