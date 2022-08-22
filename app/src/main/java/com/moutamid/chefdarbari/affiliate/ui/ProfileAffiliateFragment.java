package com.moutamid.chefdarbari.affiliate.ui;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentProfileAffiliateBinding;
import com.moutamid.chefdarbari.models.AffiliateUserModel;
import com.moutamid.chefdarbari.utils.Constants;

public class ProfileAffiliateFragment extends Fragment {

    private FragmentProfileAffiliateBinding b;
    AffiliateUserModel affiliateUserModel = new AffiliateUserModel();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentProfileAffiliateBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

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
                            affiliateUserModel.accountType = "Savings";
                        }
                        if (menuItem.getItemId() == R.id.current) {
                            b.accountTypeTv.setText("Current");
                            affiliateUserModel.accountType = "Current";
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        b.submitBtn1.setOnClickListener(v -> {
            if (affiliateCheckEntries()) {
                return;
            }
            progressDialog.show();
            Constants.auth().createUserWithEmailAndPassword(
                            affiliateUserModel.email, affiliateUserModel.password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                uploadAffiliateData();
                            } else {
                                Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                /*startActivity(new Intent(requireContext(),
                        AffiliateNavigationActivity.class)
                        .putExtra(Constants.PARAMS, Constants.AFFILIATE))*/
        });


        // AFFILIATE DOCUMENTS
        b.ownerPhotoLayout.setOnClickListener(v -> {
            openGallery(AFFILIATE_OWNER_PHOTO_CODE);
        });
        b.aadhaarCardLayout.setOnClickListener(v -> {
            openGallery(AFFILIATE_AADHAAR_CARD_CODE);
        });
        b.shopPhotoInsideLayout.setOnClickListener(v -> {
            openGallery(AFFILIATE_SHOP_INSIDE_CODE);
        });
        b.shopOutsideLayout.setOnClickListener(v -> {
            openGallery(AFFILIATE_SHOP_OUTSIDE_CODE);
        });
        b.shopOwnershipDocLayout.setOnClickListener(v -> {
            openGallery(AFFILIATE_SHOP_OWNERSHIP_DOC_CODE);
        });


        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        return root;
    }


    private void uploadAffiliateData() {
        Constants.databaseReference().child(Constants.USERS)
                .child(Constants.auth().getCurrentUser().getUid())
                .setValue(affiliateUserModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private ProgressDialog progressDialog;

    private int AFFILIATE_OWNER_PHOTO_CODE = 1234;
    private int AFFILIATE_AADHAAR_CARD_CODE = 1235;
    private int AFFILIATE_SHOP_INSIDE_CODE = 1236;
    private int AFFILIATE_SHOP_OUTSIDE_CODE = 1237;
    private int AFFILIATE_SHOP_OWNERSHIP_DOC_CODE = 1238;


    private void openGallery(int CODE) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AFFILIATE_OWNER_PHOTO_CODE ||
                    requestCode == AFFILIATE_SHOP_OUTSIDE_CODE ||
                    requestCode == AFFILIATE_SHOP_INSIDE_CODE ||
                    requestCode == AFFILIATE_SHOP_OWNERSHIP_DOC_CODE ||
                    requestCode == AFFILIATE_AADHAAR_CARD_CODE

                    /*requestCode == CHEF_EXPERIENCE_1_PHOTO_CODE ||
                    requestCode == CHEF_EXPERIENCE_2_PHOTO_CODE ||
                    requestCode == CHEF_PROFESSIONAL_PHOTO_CODE ||
                    requestCode == CHEF_AADHAAR_CODE ||
                    requestCode == CHEF_EDUCATION_CERTIFICATE_CODE ||
                    requestCode == CHEF_RESUME_CODE*/
            ) {

                Uri imageUri = data.getData();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImages");

                progressDialog.show();

                final StorageReference filePath = storageReference
                        .child(Constants.auth().getCurrentUser().getUid() + imageUri.getLastPathSegment());
                filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri photoUrl) {
                                if (requestCode == AFFILIATE_OWNER_PHOTO_CODE) {
                                    affiliateUserModel.ownerPhotoUrl = photoUrl.toString();
                                    b.uploadOwnerPhotoTextAffiliate.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == AFFILIATE_AADHAAR_CARD_CODE) {
                                    affiliateUserModel.ownerAadhaarCardUrl = photoUrl.toString();
                                    b.ownerAadhaarCardPhotoTextAffiliate.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == AFFILIATE_SHOP_OWNERSHIP_DOC_CODE) {
                                    affiliateUserModel.shopOwnerShipDocUrl = photoUrl.toString();
                                    b.shopOwnershipDocsPhotoTextAffiliate.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == AFFILIATE_SHOP_INSIDE_CODE) {
                                    affiliateUserModel.shopPhotoInsideurl = photoUrl.toString();
                                    b.shopInsidePhotoTextAffiliate.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == AFFILIATE_SHOP_OUTSIDE_CODE) {
                                    affiliateUserModel.shopPhotoOutsideUrl = photoUrl.toString();
                                    b.shopPhotoOutsideTextAffiliate.setText(imageUri.getLastPathSegment());
                                }
                                /*if (requestCode == CHEF_EXPERIENCE_1_PHOTO_CODE) {
                                    chefUserModel.work_1_worked_years = photoUrl.toString();
                                    b.workEx1ExperienceCertificateTextviewUrlChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_EXPERIENCE_2_PHOTO_CODE) {
                                    chefUserModel.work_2_worked_years = photoUrl.toString();
                                    b.workEx2ExperienceCertificateTextviewChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_PROFESSIONAL_PHOTO_CODE) {
                                    chefUserModel.professional_photo_url = photoUrl.toString();
                                    b.workEx2ProfessionalPhotoTextviewChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_AADHAAR_CODE) {
                                    chefUserModel.aadhaar_card_url = photoUrl.toString();
                                    b.workEx2AadhaarPhotoTextviewChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_EDUCATION_CERTIFICATE_CODE) {
                                    chefUserModel.education_certificate_url = photoUrl.toString();
                                    b.workEx2EducationCertificateTextviewChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_RESUME_CODE) {
                                    chefUserModel.resume_url = photoUrl.toString();
                                    b.workEx2ResumeTextviewChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_COVID_CERTIFICATE_CODE) {
                                    chefUserModel.covid_vaccination_url = photoUrl.toString();
                                    b.workEx2CovidCertificateTextviewChef.setText(imageUri.getLastPathSegment());
                                }*/
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        toast(e.toString());
                    }
                });
            }
        }

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }*/

    public boolean affiliateCheckEntries() {
        if (b.checkboxAffiliate.isChecked()) {
            toast("Please accept T&Cs!");
            return true;
        }
        String name = b.profileNameEtAffiliate.getText().toString();
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
        } else affiliateUserModel.name = name;
        if (whatsapp.isEmpty()) {
            toast("Please enter your whatsapp");
            return true;
        } else affiliateUserModel.whatsapp = whatsapp;
        if (mobileNmbr.isEmpty()) {
            toast("Please enter your mobileNmbr");
            return true;
        } else affiliateUserModel.number = mobileNmbr;
        if (email.isEmpty()) {
            toast("Please enter your email");
            return true;
        } else affiliateUserModel.email = email;
        if (password.isEmpty()) {
            toast("Please enter your password");
            return true;
        } else affiliateUserModel.password = password;

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
        } else affiliateUserModel.shopName = shopName;
        if (shopCity.isEmpty()) {
            toast("Please enter your shopCity");
            return true;
        } else affiliateUserModel.shopCity = shopCity;
        if (shopAddress.isEmpty()) {
            toast("Please enter your shopAddress");
            return true;
        } else affiliateUserModel.shopAddress = shopAddress;
        if (shopAreaInSqFt.isEmpty()) {
            toast("Please enter your shopAreaInSqFt");
            return true;
        } else affiliateUserModel.shopAreaInSqFoot = shopAreaInSqFt;
        if (shopEstdYear.isEmpty()) {
            toast("Please enter your shopEstdYear");
            return true;
        } else affiliateUserModel.shopEstablishedYear = shopEstdYear;
        if (numberOfOutlets.isEmpty()) {
            toast("Please enter your numberOfOutlets");
            return true;
        } else affiliateUserModel.numberOfOutlets = numberOfOutlets;
        if (upiPaymentNameAddress.isEmpty()) {
            toast("Please enter your upiPaymentNameAddress");
            return true;
        } else affiliateUserModel.upiPaymentNameAddress = upiPaymentNameAddress;
        if (bankAccountNumber.isEmpty()) {
            toast("Please enter your bankAccountNumber");
            return true;
        } else affiliateUserModel.bankAccountNumber = bankAccountNumber;
        if (accountHolderName.isEmpty()) {
            toast("Please enter your accountHolderName");
            return true;
        } else affiliateUserModel.accountHolderName = accountHolderName;
        if (bankBranchName.isEmpty()) {
            toast("Please enter your bankBranchName");
            return true;
        } else affiliateUserModel.bankBranchName = bankBranchName;
        if (ifscCode.isEmpty()) {
            toast("Please enter your ifscCode");
            return true;
        } else affiliateUserModel.ifscCode = ifscCode;
        if (b.accountTypeTv.getText().toString().equals("Acc. Type")) {
            toast("Please select account type!");
            return true;
        }
        // URLs
        if (affiliateUserModel.ownerPhotoUrl.equals(Constants.NULL)) {
            toast("Please upload owner photo!");
            return true;
        }
        if (affiliateUserModel.ownerAadhaarCardUrl.equals(Constants.NULL)) {
            toast("Please upload owner aadhaar card!");
            return true;
        }
        if (affiliateUserModel.shopPhotoInsideurl.equals(Constants.NULL)) {
            toast("Please upload shop inside photo!");
            return true;
        }
        if (affiliateUserModel.shopPhotoOutsideUrl.equals(Constants.NULL)) {
            toast("Please upload shop outside photo!");
            return true;
        }
        if (affiliateUserModel.shopOwnerShipDocUrl.equals(Constants.NULL)) {
            toast("Please upload shop ownership document photo!");
            return true;
        }
        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(requireContext(), mcg, Toast.LENGTH_SHORT).show();
    }


}