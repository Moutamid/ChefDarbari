package com.moutamid.chefdarbari.chef.ui;

import static com.bumptech.glide.Glide.with;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.DATA;
import static com.moutamid.chefdarbari.R.color.lighterGrey;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentShowIdBinding;
import com.moutamid.chefdarbari.models.ChefUserModel;
import com.moutamid.chefdarbari.utils.Constants;


public class ShowIDFragment extends Fragment {

    private FragmentShowIdBinding b;
    ChefUserModel chefUserModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentShowIdBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();
        chefUserModel = (ChefUserModel) Stash.getObject(Constants.CURRENT_CHEF_MODEL, ChefUserModel.class);

        b.nameId.setText(chefUserModel.name);
        b.numberId.setText(chefUserModel.number);
        b.emailId.setText(chefUserModel.email);
        b.expertInId.setText(chefUserModel.expertInList.toString());
        b.addressId.setText(chefUserModel.home_town);

        with(requireContext().getApplicationContext())
                .asBitmap()
                .load(chefUserModel.professional_photo_url)
                .apply(new RequestOptions()
                        .placeholder(lighterGrey)
                        .error(lighterGrey)
                )
                .diskCacheStrategy(DATA)
                .into(b.profileId);

        return root;
    }


}