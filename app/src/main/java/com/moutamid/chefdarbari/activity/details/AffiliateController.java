package com.moutamid.chefdarbari.activity.details;

import android.widget.Toast;

import com.moutamid.chefdarbari.databinding.ActivityDetailsBinding;
import com.moutamid.chefdarbari.utils.Constants;

public class AffiliateController {

    public DetailsActivity activity;
    public ActivityDetailsBinding b;

    public AffiliateController(DetailsActivity activity, ActivityDetailsBinding b) {
        this.activity = activity;
        this.b = b;
    }

    public boolean affiliateCheckEntries() {
        if (b.checkboxAffiliate.isChecked()) {
            toast("Please accept T&Cs!");
            return true;
        }
        String name = b.nameEtAffiliate.getText().toString();
        String whatsapp = b.whatsappEtAffiliate.getText().toString();
        String mobileNmbr = b.numberEtAffiliate.getText().toString();
        String email = b.emailEtAffiliate.getText().toString();
        String password = b.passwordEtAffiliate.getText().toString();
        String confirmPassword = b.confirmPasswordEtAffiliate.getText().toString();
        String shopName = b.shopNameEtAffiliate.getText().toString();
        String shopCity = b.shopCityEtAffiliate.getText().toString();
        String shopAddress = b.shopAddressEtAffiliate.getText().toString();
        String shopAreaInSqFt = b.shopAreaInSquareFtEtAffiliate.getText().toString();
        String shopEstdYear = b.shopEstablishedYearEtAffiliate.getText().toString();
        String numberOfOutlets = b.numberOfOutletsEtAffiliate.getText().toString();
        String upiPaymentNameAddress = b.upPaymentNameAddressEtAffiliate.getText().toString();
        String bankAccountNumber = b.bankAccountNumberEtAffiliate.getText().toString();
        String accountHolderName = b.accountHolderNameEtAffiliate.getText().toString();
        String bankBranchName = b.bankBranchNameEtAffiliate.getText().toString();
        String ifscCode = b.ifscCodeEtAffiliate.getText().toString();

        if (name.isEmpty()) {
            toast("Please enter your name");
            return true;
        } else activity.affiliateUserModel.name = name;
        if (whatsapp.isEmpty()) {
            toast("Please enter your whatsapp");
            return true;
        } else activity.affiliateUserModel.whatsapp = whatsapp;
        if (mobileNmbr.isEmpty()) {
            toast("Please enter your mobileNmbr");
            return true;
        } else activity.affiliateUserModel.number = mobileNmbr;
        if (email.isEmpty()) {
            toast("Please enter your email");
            return true;
        } else activity.affiliateUserModel.email = email;
        if (password.isEmpty()) {
            toast("Please enter your password");
            return true;
        } else activity.affiliateUserModel.password = password;

        if (confirmPassword.isEmpty()) {
            toast("Please enter your confirmPassword");
            return true;
        } else if (!confirmPassword.equals(password)) {
            toast("Both passwords do not match!");
            return true;
        }
        if (shopName.isEmpty()) {
            toast("Please enter your shopName");
            return true;
        } else activity.affiliateUserModel.shopName = shopName;
        if (shopCity.isEmpty()) {
            toast("Please enter your shopCity");
            return true;
        } else activity.affiliateUserModel.shopCity = shopCity;
        if (shopAddress.isEmpty()) {
            toast("Please enter your shopAddress");
            return true;
        } else activity.affiliateUserModel.shopAddress = shopAddress;
        if (shopAreaInSqFt.isEmpty()) {
            toast("Please enter your shopAreaInSqFt");
            return true;
        } else activity.affiliateUserModel.shopAreaInSqFoot = shopAreaInSqFt;
        if (shopEstdYear.isEmpty()) {
            toast("Please enter your shopEstdYear");
            return true;
        } else activity.affiliateUserModel.shopEstablishedYear = shopEstdYear;
        if (numberOfOutlets.isEmpty()) {
            toast("Please enter your numberOfOutlets");
            return true;
        } else activity.affiliateUserModel.numberOfOutlets = numberOfOutlets;
        if (upiPaymentNameAddress.isEmpty()) {
            toast("Please enter your upiPaymentNameAddress");
            return true;
        } else activity.affiliateUserModel.upiPaymentNameAddress = upiPaymentNameAddress;
        if (bankAccountNumber.isEmpty()) {
            toast("Please enter your bankAccountNumber");
            return true;
        } else activity.affiliateUserModel.bankAccountNumber = bankAccountNumber;
        if (accountHolderName.isEmpty()) {
            toast("Please enter your accountHolderName");
            return true;
        } else activity.affiliateUserModel.accountHolderName = accountHolderName;
        if (bankBranchName.isEmpty()) {
            toast("Please enter your bankBranchName");
            return true;
        } else activity.affiliateUserModel.bankBranchName = bankBranchName;
        if (ifscCode.isEmpty()) {
            toast("Please enter your ifscCode");
            return true;
        } else activity.affiliateUserModel.ifscCode = ifscCode;
        if (b.accountTypeTv.getText().toString().equals("Acc. Type")) {
            toast("Please select account type!");
            return true;
        }
        // URLs
        if (activity.affiliateUserModel.ownerPhotoUrl.equals(Constants.NULL)){
            toast("Please upload owner photo!");
            return true;
        }
        if (activity.affiliateUserModel.ownerAadhaarCardUrl.equals(Constants.NULL)){
            toast("Please upload owner aadhaar card!");
            return true;
        }
        if (activity.affiliateUserModel.shopPhotoInsideurl.equals(Constants.NULL)){
            toast("Please upload shop inside photo!");
            return true;
        }if (activity.affiliateUserModel.shopPhotoOutsideUrl.equals(Constants.NULL)){
            toast("Please upload shop outside photo!");
            return true;
        }
        if (activity.affiliateUserModel.shopOwnerShipDocUrl.equals(Constants.NULL)){
            toast("Please upload shop ownership document photo!");
            return true;
        }
        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(activity, mcg, Toast.LENGTH_SHORT).show();
    }

}
