package com.moutamid.chefdarbarii.chef.ui;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
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

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.activity.SplashActivity;
import com.moutamid.chefdarbarii.activity.details.DetailsActivity;
import com.moutamid.chefdarbarii.chef.ChefNavigationActivity;
import com.moutamid.chefdarbarii.databinding.FragmentProfileBinding;
import com.moutamid.chefdarbarii.models.ChefUserModel;
import com.moutamid.chefdarbarii.utils.Constants;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding b;
    ChefUserModel chefUserModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

        chefUserModel = (ChefUserModel) Stash.getObject(Constants.CURRENT_CHEF_MODEL, ChefUserModel.class);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        if (chefUserModel == null)
            getDetails();
        else
            initOnCreateMethod();

        b.logoutBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Are you sure?")
                    .setMessage("Do you really want to logout?")
                    .setPositiveButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setNegativeButton("Yes", (dialog, which) -> {
                        Constants.auth().signOut();
                        Stash.clearAll();
                        Intent intent = new Intent(requireContext(), SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        requireActivity().finish();
                        startActivity(intent);
                    })
                    .show();
        });

        return root;
    }

    private void initOnCreateMethod() {
        b.nameEtChef.setText(chefUserModel.name);
        b.numberEtChef.setText(chefUserModel.number);
        b.whatsappEtChef.setText(chefUserModel.whatsapp);
        b.emailEtChef.setText(chefUserModel.email);
        b.passwordEtChef.setText(chefUserModel.password);
        b.confirmPasswordEtChef.setText(chefUserModel.password);
        b.cityTextviewChef.setText(chefUserModel.city);
        b.homeTownEtChef.setText(chefUserModel.home_town);
        b.languageTextviewChef.setText(chefUserModel.language_speak);
        b.ageTextviewChef.setText(chefUserModel.age);
        b.educationTextviewChef.setText(chefUserModel.highest_education);
        b.postTextviewCheff.setText(chefUserModel.post);
        b.experienceTextviewChef.setText(chefUserModel.total_experience);
        b.vehicleTextviewChef.setText(chefUserModel.has_vehicle);
        b.monthlySalaryEtChef.setText(chefUserModel.monthly_salary);
        b.physicalTextviewChef.setText(chefUserModel.physical_status);
        b.work1ExhotelNameEtChef.setText(chefUserModel.work_1_hotel_name);
        b.workEx1PostEtCheff.setText(chefUserModel.work_1_post);
        b.workEx1CityEtChef.setText(chefUserModel.work_1_city);
        b.workEx1WorkedYearTextviewChef.setText(chefUserModel.work_1_worked_years);
        b.workEx2HotelNameEtChef.setText(chefUserModel.work_2_hotel_name);
        b.workEx2PostEtChef.setText(chefUserModel.work_2_post);
        b.workEx2CityEtChef.setText(chefUserModel.work_2_city);
        b.workEx2WorkedYearTextviewChef.setText(chefUserModel.work_2_worked_years);

        b.cityPopupLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_cities,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.cityTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.city = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.languageLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_languages,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        chefUserModel.language_speak = menuItem.getTitle().toString();
                        b.languageTextviewChef.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.ageLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_age,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        chefUserModel.age = menuItem.getTitle().toString();
                        b.ageTextviewChef.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.educationLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_education,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.educationTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.highest_education = menuItem.getTitle().toString();

                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.postLayoutCheff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_post,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.postTextviewCheff.setText(menuItem.getTitle().toString());
                        chefUserModel.post = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.experienceLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_experience,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.experienceTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.total_experience = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.vehicleLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_vehicle,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.vehicleTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.has_vehicle = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.physicalLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_physical,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.physicalTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.physical_status = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.workEx1WorkedYearLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_workyear,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.workEx1WorkedYearTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.work_1_worked_years = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.workEx2WorkedYearLayoutChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_workyear,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.workEx2WorkedYearTextviewChef.setText(menuItem.getTitle().toString());
                        chefUserModel.work_2_worked_years = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.submitBtnChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries()) {
                    return;
                }
                progressDialog.show();
                /*Constants.auth().createUserWithEmailAndPassword(
                                chefUserModel.email, chefUserModel.password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {*/
                uploadChefData();
                       /*         } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
            }
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
    }

    private ProgressDialog progressDialog;
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

    private void uploadChefData() {
        Constants.databaseReference().child(Constants.USERS)
                .child(Constants.auth().getCurrentUser().getUid())
                .setValue(chefUserModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Stash.put(Constants.CURRENT_CHEF_MODEL, chefUserModel);
                            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireContext(), ChefNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            requireActivity().finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHEF_EXPERIENCE_1_PHOTO_CODE ||
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
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
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

    public boolean checkEntries() {
        if (!b.checkboxChef.isChecked()) {
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
        chefUserModel.expertInList.clear();

        if (b.indianExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.indianExpertCheff.getText().toString());
        }
        if (b.tandoorExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.tandoorExpertCheff.getText().toString());
        }
        if (b.mughlaiExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.mughlaiExpertCheff.getText().toString());
        }
        if (b.continentalExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.continentalExpertCheff.getText().toString());
        }
        if (b.chaatExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.chaatExpertCheff.getText().toString());
        }
        if (b.asianExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.asianExpertCheff.getText().toString());
        }
        if (b.pantryExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.pantryExpertCheff.getText().toString());
        }
        if (b.chineseExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.chineseExpertCheff.getText().toString());
        }
        if (b.italianExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.italianExpertCheff.getText().toString());
        }
        if (b.mexicanExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.mexicanExpertCheff.getText().toString());
        }
        if (b.thaiExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.thaiExpertCheff.getText().toString());
        }
        if (b.japaneseExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.japaneseExpertCheff.getText().toString());
        }
        if (b.lebaneseExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.lebaneseExpertCheff.getText().toString());
        }
        if (b.pastryExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.pastryExpertCheff.getText().toString());
        }
        if (b.bakeryExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.bakeryExpertCheff.getText().toString());
        }
        if (b.drinksBartenderExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.drinksBartenderExpertCheff.getText().toString());
        }
        if (b.servingWaiterExpertCheff.isChecked()) {
            chefUserModel.expertInList.add(b.servingWaiterExpertCheff.getText().toString());
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
        } else chefUserModel.name = name;
        if (mobileNmbr.isEmpty()) {
            toast("Please enter your mobile number");
            return true;
        } else chefUserModel.number = mobileNmbr;
        if (whatsapp.isEmpty()) {
            toast("Please enter your WhatsApp number");
            return true;
        } else chefUserModel.whatsapp = whatsapp;
        if (email.isEmpty()) {
            toast("Please enter your email");
            return true;
        } else chefUserModel.email = email;
        if (password.isEmpty()) {
            toast("Please enter your password");
            return true;
        } else chefUserModel.password = password;
        if (confirmPassword.isEmpty()) {
            toast("Please enter your confirm password");
            return true;
        } else if (!confirmPassword.equals(password)) {
            toast("Your password do not match!");
            return true;
        }
        if (homeTown.isEmpty()) {
            toast("Please enter your home town");
            return true;
        } else chefUserModel.home_town = homeTown;
        if (monthlySalary.isEmpty()) {
            toast("Please enter your monthly salary");
            return true;
        } else chefUserModel.monthly_salary = monthlySalary;
        if (work1HotelName.isEmpty()) {
            toast("Please enter your work 1 hotel name");
            return true;
        } else chefUserModel.work_1_hotel_name = work1HotelName;
        if (work1post.isEmpty()) {
            toast("Please enter your work 1 post");
            return true;
        } else chefUserModel.work_1_post = work1post;
        if (work1city.isEmpty()) {
            toast("Please enter your work 1 city");
            return true;
        } else chefUserModel.work_1_city = work1city;
        if (work2HotelName.isEmpty()) {
            toast("Please enter your work 2 hotel name");
            return true;
        } else chefUserModel.work_2_hotel_name = work2HotelName;
        if (work2post.isEmpty()) {
            toast("Please enter your work 2 post");
            return true;
        } else chefUserModel.work_2_post = work2post;
        if (work2city.isEmpty()) {
            toast("Please enter your work 2 city");
            return true;
        } else chefUserModel.work_2_city = work2city;

        // URLs
        if (chefUserModel.work_1_certificate.equals(Constants.NULL)) {
            toast("Please upload work 1 certificate!");
            return true;
        }
        if (chefUserModel.work_2_certificate.equals(Constants.NULL)) {
            toast("Please upload work 2 certificate!");
            return true;
        }
        if (chefUserModel.aadhaar_card_url.equals(Constants.NULL)) {
            toast("Please upload aadhaar card!");
            return true;
        }
        if (chefUserModel.education_certificate_url.equals(Constants.NULL)) {
            toast("Please upload education certificate!");
            return true;
        }
        if (chefUserModel.resume_url.equals(Constants.NULL)) {
            toast("Please upload resume!");
            return true;
        }
        if (chefUserModel.covid_vaccination_url.equals(Constants.NULL)) {
            toast("Please upload covid vaccination doc!");
            return true;
        }

        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(requireContext(), mcg, Toast.LENGTH_SHORT).show();
    }

    private void getDetails() {
        progressDialog.show();
        Constants.databaseReference().child(Constants.USERS)
                .child(Constants.auth().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Stash.put(Constants.CURRENT_CHEF_MODEL,
                                    snapshot.getValue(ChefUserModel.class));
                            chefUserModel = snapshot.getValue(ChefUserModel.class);
                            progressDialog.dismiss();
                            initOnCreateMethod();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}