package com.moutamid.chefdarbari.activity.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.chefdarbari.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbari.chef.ui.JobDetailsActivity;
import com.moutamid.chefdarbari.databinding.ActivityDetailsBinding;
import com.moutamid.chefdarbari.models.AffiliateUserModel;
import com.moutamid.chefdarbari.models.ChefUserModel;
import com.moutamid.chefdarbari.notifications.FcmNotificationsSender;
import com.moutamid.chefdarbari.utils.Constants;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.chef.ChefNavigationActivity;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private AffiliateController affiliateController;
    private ChefController chefController;
    private ActivityDetailsBinding b;
    String user_type;
    AffiliateUserModel affiliateUserModel = new AffiliateUserModel();
    ChefUserModel chefUserModel = new ChefUserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        affiliateController = new AffiliateController(this, b);
        chefController = new ChefController(this, b);
        user_type = getIntent().getStringExtra(Constants.PARAMS);

        if (user_type.equals(Constants.AFFILIATE)) {
            b.topText.setText("Affiliate Partner Information Form");
            b.affiliateCardView.setVisibility(View.VISIBLE);
            b.chefCardView.setVisibility(View.GONE);

        } else {
            b.topText.setText("Candidate Information Form");
            b.chefCardView.setVisibility(View.VISIBLE);
            b.affiliateCardView.setVisibility(View.GONE);

        }

        affiliateUserModel.ownerPhotoUrl = Constants.NULL;
        affiliateUserModel.ownerAadhaarCardUrl = Constants.NULL;
        affiliateUserModel.shopPhotoInsideurl = Constants.NULL;
        affiliateUserModel.shopPhotoOutsideUrl = Constants.NULL;
        affiliateUserModel.shopOwnerShipDocUrl = Constants.NULL;

        b.accountTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
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

        b.submitBtnChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chefController.checkEntries()) {
                    return;
                }
                progressDialog.show();
                Constants.auth().createUserWithEmailAndPassword(
                                chefUserModel.email, chefUserModel.password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    uploadChefData();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(DetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        b.submitBtn1.setOnClickListener(v -> {
            if (affiliateController.affiliateCheckEntries()) {
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
                                progressDialog.dismiss();
                                Toast.makeText(DetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                /*startActivity(new Intent(DetailsActivity.this,
                        AffiliateNavigationActivity.class)
                        .putExtra(Constants.PARAMS, Constants.AFFILIATE))*/
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

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

        // CHEF DOCUMENTS
        b.workEx1ExperienceCertificateLayoutUrlChef.setOnClickListener(v -> {
            openGallery(CHEF_EXPERIENCE_1_PHOTO_CODE);
        });
        b.workEx2ExperienceCertificateLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_EXPERIENCE_2_PHOTO_CODE);
        });
        b.workEx2ProfessionalPhotoLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_PROFESSIONAL_PHOTO_CODE);
        });
        b.workEx2AadhaarPhotoLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_AADHAAR_CODE);
        });
        b.workEx2EducationCertificateLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_EDUCATION_CERTIFICATE_CODE);
        });
        b.workEx2ResumeLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_RESUME_CODE);
        });
        b.workEx2CovidCertificateLayoutChef.setOnClickListener(v -> {
            openGallery(CHEF_COVID_CERTIFICATE_CODE);
        });

        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private void uploadChefData() {
        Constants.databaseReference()
                .child(Constants.USERS)
                .child(Constants.CHEF)
                .child(Constants.auth().getCurrentUser().getUid())
                .setValue(chefUserModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            uploadNotification();
                            Stash.put(Constants.CURRENT_CHEF_MODEL, chefUserModel);
                            Toast.makeText(DetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailsActivity.this, ChefNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(DetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadAffiliateData() {
        Constants.databaseReference()
                .child(Constants.USERS)
                .child(Constants.AFFILIATE)
                .child(Constants.auth().getCurrentUser().getUid())
                .setValue(affiliateUserModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            uploadNotification();
                            Stash.put(Constants.CURRENT_AFFILIATE_MODEL, affiliateUserModel);
                            Toast.makeText(DetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailsActivity.this, AffiliateNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(DetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    private int CHEF_EXPERIENCE_1_PHOTO_CODE = 2231;
    private int CHEF_EXPERIENCE_2_PHOTO_CODE = 2232;
    private int CHEF_PROFESSIONAL_PHOTO_CODE = 2233;
    private int CHEF_AADHAAR_CODE = 2234;
    private int CHEF_EDUCATION_CERTIFICATE_CODE = 2235;
    private int CHEF_RESUME_CODE = 2236;
    private int CHEF_COVID_CERTIFICATE_CODE = 2237;


    private void openGallery(int CODE) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AFFILIATE_OWNER_PHOTO_CODE ||
                    requestCode == AFFILIATE_SHOP_OUTSIDE_CODE ||
                    requestCode == AFFILIATE_SHOP_INSIDE_CODE ||
                    requestCode == AFFILIATE_SHOP_OWNERSHIP_DOC_CODE ||
                    requestCode == AFFILIATE_AADHAAR_CARD_CODE ||

                    requestCode == CHEF_EXPERIENCE_1_PHOTO_CODE ||
                    requestCode == CHEF_EXPERIENCE_2_PHOTO_CODE ||
                    requestCode == CHEF_PROFESSIONAL_PHOTO_CODE ||
                    requestCode == CHEF_AADHAAR_CODE ||
                    requestCode == CHEF_EDUCATION_CERTIFICATE_CODE ||
                    requestCode == CHEF_RESUME_CODE ||
                    requestCode == CHEF_COVID_CERTIFICATE_CODE) {

                Uri imageUri = data.getData();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImages");

                progressDialog.show();

                final StorageReference filePath = storageReference
                        .child(System.currentTimeMillis() + imageUri.getLastPathSegment());
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
                                if (requestCode == CHEF_EXPERIENCE_1_PHOTO_CODE) {
                                    chefUserModel.work_1_certificate = photoUrl.toString();
                                    b.workEx1ExperienceCertificateTextviewUrlChef.setText(imageUri.getLastPathSegment());
                                }
                                if (requestCode == CHEF_EXPERIENCE_2_PHOTO_CODE) {
                                    chefUserModel.work_2_certificate = photoUrl.toString();
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
                                }
                                progressDialog.dismiss();
                                Toast.makeText(DetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        affiliateController.toast(e.toString());
                    }
                });
            }
        }
    }

    private void uploadNotification() {
        String body;
        String title;
        if (user_type.equals(Constants.AFFILIATE)) {
            title = "New Affiliate SignUp";
            body = affiliateUserModel.name + " has signed up from " + affiliateUserModel.shopCity;
        } else {
            title = "New Chef SignUp";
            body = chefUserModel.name +" has signed up from " + chefUserModel.city;
        }
        new FcmNotificationsSender(
                "/topics/" + Constants.ADMIN_NOTIFICATIONS,
                title,
                body,
                getApplicationContext(),
                DetailsActivity.this)
                .SendNotifications();
    }

}