package com.moutamid.chefdarbarii.affiliate.ui;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.FragmentAddNewBookingsBinding;
import com.moutamid.chefdarbarii.models.AffiliateAddBookingModel;
import com.moutamid.chefdarbarii.utils.Constants;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddNewBookingsFragment extends Fragment {

    private FragmentAddNewBookingsBinding b;
    AffiliateAddBookingModel affiliateAddBookingModel = new AffiliateAddBookingModel();
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAddNewBookingsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded())
            return b.getRoot();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        affiliateAddBookingModel.time = Constants.NULL;
        affiliateAddBookingModel.date_of_party = Constants.NULL;

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

        b.dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long today;
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.clear();
                today = MaterialDatePicker.todayInUtcMilliseconds();
                final MaterialDatePicker.Builder singleDateBuilder = MaterialDatePicker.Builder.datePicker();
                singleDateBuilder.setTitleText("Select Date");
                singleDateBuilder.setSelection(today);
                final MaterialDatePicker materialDatePicker = singleDateBuilder.build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        affiliateAddBookingModel.date_of_party = materialDatePicker.getHeaderText();
                        b.dateEt.setText(materialDatePicker.getHeaderText());
                    }
                });
                materialDatePicker.show(getFragmentManager(), "DATE");
            }
        });

        b.timeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay, hour, mMinute;
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String current_hour = hourOfDay + "";
                                if (current_hour.length() == 1)
                                    current_hour = "0" + current_hour;

                                String current_minute = minute + "";
                                if (current_minute.length() == 1)
                                    current_minute = "0" + current_minute;

                                String am_pm = "";

                                Calendar datetime = Calendar.getInstance();
                                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                datetime.set(Calendar.MINUTE, minute);

                                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                    am_pm = " AM";
                                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                    am_pm = " PM";

                                String time = current_hour + ":" + current_minute;

                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                    Date dateObj;
                                    dateObj = sdf.parse(time);
                                    time = new SimpleDateFormat("K:mm").format(dateObj);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                affiliateAddBookingModel.time = time + am_pm;
                                b.timeEt.setText(affiliateAddBookingModel.time);
                            }

                        }, hour, mMinute, true);
                timePickerDialog.show();
            }
        });
        return root;
    }

    private boolean checkEntries() {
        String name = b.name.getText().toString();
        String number = b.numberEt.getText().toString();
        String partyVenueAddress = b.partyVenueAddressEt.getText().toString();
//        String dateOfParty = b.dateEt.getText().toString();
//        String time = b.timeEt.getText().toString();
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
        if (affiliateAddBookingModel.date_of_party.equals(Constants.NULL)) {
            toast("Please enter date of party");
            return true;
        }
        if (affiliateAddBookingModel.time.equals(Constants.NULL)) {
            toast("Please enter time of party");
            return true;
        }
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