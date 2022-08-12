package com.moutamid.chefdarbari.activity.details;

import android.widget.Toast;

import com.moutamid.chefdarbari.databinding.ActivityDetailsBinding;
import com.moutamid.chefdarbari.utils.Constants;

import java.util.ArrayList;

public class ChefController {

    public DetailsActivity activity;
    public ActivityDetailsBinding b;

    public ChefController(DetailsActivity activity, ActivityDetailsBinding b) {
        this.activity = activity;
        this.b = b;
    }

    public boolean checkEntries() {
        if (b.checkboxChef.isChecked()) {
            toast("Please accept T&Cs!");
            return true;
        }
        String name = b.nameEtChef.getText().toString();
        String mobileNmbr = b.numberEtChef.getText().toString();
        String whatsapp = b.whatsappEtChef.getText().toString();
        String email = b.emailEtChef.getText().toString();
        String password = b.passwordEtChef.getText().toString();
        String confirmPassword = b.confirmPasswordEtChef.getText().toString();
        String homeTown = b.homeTownEtChef.getText().toString();
        activity.chefUserModel.expertInList.clear();

        if (b.indianExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.indianExpertCheff.getText().toString());
        }
        if (b.tandoorExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.tandoorExpertCheff.getText().toString());
        }
        if (b.mughlaiExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.mughlaiExpertCheff.getText().toString());
        }
        if (b.continentalExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.continentalExpertCheff.getText().toString());
        }
        if (b.chaatExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.chaatExpertCheff.getText().toString());
        }
        if (b.asianExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.asianExpertCheff.getText().toString());
        }
        if (b.pantryExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.pantryExpertCheff.getText().toString());
        }
        if (b.chineseExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.chineseExpertCheff.getText().toString());
        }
        if (b.italianExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.italianExpertCheff.getText().toString());
        }
        if (b.mexicanExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.mexicanExpertCheff.getText().toString());
        }
        if (b.thaiExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.thaiExpertCheff.getText().toString());
        }
        if (b.japaneseExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.japaneseExpertCheff.getText().toString());
        }
        if (b.lebaneseExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.lebaneseExpertCheff.getText().toString());
        }
        if (b.pastryExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.pastryExpertCheff.getText().toString());
        }
        if (b.bakeryExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.bakeryExpertCheff.getText().toString());
        }
        if (b.drinksBartenderExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.drinksBartenderExpertCheff.getText().toString());
        }
        if (b.servingWaiterExpertCheff.isChecked()) {
            activity.chefUserModel.expertInList.add(b.servingWaiterExpertCheff.getText().toString());
        }
        String monthlySalary = b.monthlySalaryEtChef.getText().toString();
        String work1HotelName = b.work1ExhotelNameEtChef.getText().toString();
        String work1post = b.workEx1PostEtCheff.getText().toString();
        String work1city = b.workEx1CityEtChef.getText().toString();
        String work2HotelName = b.workEx2HotelNameEtChef.getText().toString();
        String work2post = b.workEx2PostEtChef.getText().toString();
        String work2city = b.workEx2CityEtChef.getText().toString();
        if (name.isEmpty()) {
            toast("Please enter your name");
            return true;
        } else activity.chefUserModel.name = name;
        if (mobileNmbr.isEmpty()) {
            toast("Please enter your mobile number");
            return true;
        } else activity.chefUserModel.number = mobileNmbr;
        if (whatsapp.isEmpty()) {
            toast("Please enter your WhatsApp number");
            return true;
        } else activity.chefUserModel.whatsapp = whatsapp;
        if (email.isEmpty()) {
            toast("Please enter your email");
            return true;
        } else activity.chefUserModel.email = email;
        if (password.isEmpty()) {
            toast("Please enter your password");
            return true;
        } else activity.chefUserModel.password = password;
        if (confirmPassword.isEmpty()) {
            toast("Please enter your confirm password");
            return true;
        } else if (!confirmPassword.equals(password)){
            toast("Your password do not match!");
            return true;
        }
        if (homeTown.isEmpty()) {
            toast("Please enter your home town");
            return true;
        } else activity.chefUserModel.home_town = homeTown;
        if (monthlySalary.isEmpty()) {
            toast("Please enter your monthly salary");
            return true;
        } else activity.chefUserModel.monthly_salary = monthlySalary;
        if (work1HotelName.isEmpty()) {
            toast("Please enter your work 1 hotel name");
            return true;
        } else activity.chefUserModel.work_1_hotel_name = work1HotelName;
        if (work1post.isEmpty()) {
            toast("Please enter your work 1 post");
            return true;
        } else activity.chefUserModel.work_1_post = work1post;
        if (work1city.isEmpty()) {
            toast("Please enter your work 1 city");
            return true;
        } else activity.chefUserModel.work_1_city = work1city;
        if (work2HotelName.isEmpty()) {
            toast("Please enter your work 2 hotel name");
            return true;
        } else activity.chefUserModel.work_2_hotel_name = work2HotelName;
        if (work2post.isEmpty()) {
            toast("Please enter your work 2 post");
            return true;
        } else activity.chefUserModel.work_2_post = work2post;
        if (work2city.isEmpty()) {
            toast("Please enter your work 2 city");
            return true;
        } else activity.chefUserModel.work_2_city = work2city;

        // URLs
        if (activity.affiliateUserModel.ownerPhotoUrl.equals(Constants.NULL)) {
            toast("Please upload owner photo!");
            return true;
        }
        if (activity.affiliateUserModel.ownerAadhaarCardUrl.equals(Constants.NULL)) {
            toast("Please upload owner aadhaar card!");
            return true;
        }
        if (activity.affiliateUserModel.shopPhotoInsideurl.equals(Constants.NULL)) {
            toast("Please upload shop inside photo!");
            return true;
        }
        if (activity.affiliateUserModel.shopPhotoOutsideUrl.equals(Constants.NULL)) {
            toast("Please upload shop outside photo!");
            return true;
        }
        if (activity.affiliateUserModel.shopOwnerShipDocUrl.equals(Constants.NULL)) {
            toast("Please upload shop ownership document photo!");
            return true;
        }

        return false;
    }


    public void toast(String mcg) {
        Toast.makeText(activity, mcg, Toast.LENGTH_SHORT).show();
    }

}
