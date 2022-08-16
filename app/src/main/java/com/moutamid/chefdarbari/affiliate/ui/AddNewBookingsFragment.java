package com.moutamid.chefdarbari.affiliate.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentAddNewBookingsBinding;
import com.moutamid.chefdarbari.models.AffiliateAddBookingModel;
import com.moutamid.chefdarbari.utils.Constants;

public class AddNewBookingsFragment extends Fragment {

    private FragmentAddNewBookingsBinding b;
    AffiliateAddBookingModel affiliateAddBookingModel = new AffiliateAddBookingModel();
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAddNewBookingsBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

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
                        affiliateAddBookingModel.occasion_type = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries())
                    return;

                progressDialog.show();

                Constants.databaseReference().child(Constants.NEW_PARTY_BOOKINGS)
                        .push()
                        .setValue(affiliateAddBookingModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Constants.databaseReference()
                                        .child(Constants.auth().getUid())
                                        .child(Constants.NEW_PARTY_BOOKINGS)
                                        .push()
                                        .setValue(affiliateAddBookingModel);
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        return root;
    }

    private boolean checkEntries() {
        String name = b.name.getText().toString();
        String number = b.numberEt.getText().toString();
        String partyVenueAddress = b.partyVenueAddressEt.getText().toString();
        String dateOfParty = b.dateEt.getText().toString();
        String time = b.timeEt.getText().toString();
        String numberOfPeople = b.numberOfPeople.getText().toString();
        String numberOfDishes = b.numberOfDishesEt.getText().toString();

        if (name.isEmpty()) {
            toast("Please enter name");
            return true;
        } else affiliateAddBookingModel.name = name;
        if (number.isEmpty()) {
            toast("Please enter number");
            return true;
        } else affiliateAddBookingModel.number = number;
        if (partyVenueAddress.isEmpty()) {
            toast("Please enter party venue address");
            return true;
        } else affiliateAddBookingModel.party_venue_address = partyVenueAddress;
        if (dateOfParty.isEmpty()) {
            toast("Please enter date of party");
            return true;
        } else affiliateAddBookingModel.date_of_party = dateOfParty;
        if (time.isEmpty()) {
            toast("Please enter time of party");
            return true;
        } else affiliateAddBookingModel.time = time;
        if (numberOfPeople.isEmpty()) {
            toast("Please enter number of people");
            return true;
        } else affiliateAddBookingModel.number_of_people = numberOfPeople;
        if (numberOfDishes.isEmpty()) {
            toast("Please enter number of dishes");
            return true;
        } else affiliateAddBookingModel.number_of_dishes = numberOfDishes;

        affiliateAddBookingModel.cuisinesList.clear();

        if (b.northIndianCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.northIndianCheckBox.getText().toString());
        }
        if (b.southIndianCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.southIndianCheckBox.getText().toString());
        }
        if (b.chineseCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.chineseCheckBox.getText().toString());
        }
        if (b.mexicanCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.mexicanCheckBox.getText().toString());
        }
        if (b.italianCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.italianCheckBox.getText().toString());
        }
        if (b.continentalCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.continentalCheckBox.getText().toString());
        }
        if (b.thaiCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.thaiCheckBox.getText().toString());
        }
        if (b.barbecueCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.barbecueCheckBox.getText().toString());
        }
        if (b.homeFoodCheckBox.isChecked()) {
            affiliateAddBookingModel.cuisinesList.add(b.homeFoodCheckBox.getText().toString());
        }

        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(requireContext(), mcg, Toast.LENGTH_SHORT).show();
    }

}