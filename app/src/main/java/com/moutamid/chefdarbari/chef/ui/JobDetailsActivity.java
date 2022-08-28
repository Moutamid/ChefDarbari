package com.moutamid.chefdarbari.chef.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.ActivityJobDetailsBinding;
import com.moutamid.chefdarbari.models.ChefUserModel;
import com.moutamid.chefdarbari.models.JobsAdminModel;
import com.moutamid.chefdarbari.models.JobsAdminModel2;
import com.moutamid.chefdarbari.notifications.FcmNotificationsSender;
import com.moutamid.chefdarbari.utils.Constants;

import java.util.Objects;

public class JobDetailsActivity extends AppCompatActivity {

    private ActivityJobDetailsBinding b;
    private JobsAdminModel model = (JobsAdminModel) Stash.getObject(Constants.CURRENT_JOB_MODEL, JobsAdminModel.class);
    ChefUserModel chefUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();
        chefUserModel = (ChefUserModel) Stash.getObject(Constants.CURRENT_CHEF_MODEL, ChefUserModel.class);

        if (model.job_open) {
            b.jobStatusJobDetails.setText("Open Job");
            b.jobStatusJobDetails.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        } else {
            b.jobStatusJobDetails.setText("Closed");
            b.jobStatusJobDetails.setBackgroundColor(getResources().getColor(R.color.red));
            b.acceptJobBtn.setVisibility(View.GONE);
        }

        b.idJobDetails.setText(Html.fromHtml(Constants.BOLD_START + "Job Id: " + Constants.BOLD_END + model.id));
        b.nameJobDetails.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: " + Constants.BOLD_END + model.name));
        b.staff.setText(Html.fromHtml(Constants.BOLD_START + "Staff Required: " + Constants.BOLD_END + model.staff_required));
        b.payment.setText(Html.fromHtml(Constants.BOLD_START + "Payment: " + Constants.BOLD_END + "₹"+model.payment));
        b.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: " + Constants.BOLD_END + model.occasion_type));
        b.partydate.setText(Html.fromHtml(Constants.BOLD_START + "Date: " + Constants.BOLD_END + model.date));
        b.numberofpeople.setText(Html.fromHtml(Constants.BOLD_START + "Number of people: " + Constants.BOLD_END + model.number_of_people));
        b.time.setText(Html.fromHtml(Constants.BOLD_START + "Time: " + Constants.BOLD_END + model.time));
        b.numberofdishes.setText(Html.fromHtml(Constants.BOLD_START + "No of dishes: " + Constants.BOLD_END + model.no_of_dishes));
        b.cuisinesList.setText(model.cuisines_list.toString());
        b.dishtype.setText(Html.fromHtml(Constants.BOLD_START + "Dish Type: " + Constants.BOLD_END + model.dish_type));
        b.partyaddress.setText(Html.fromHtml(Constants.BOLD_START + "Party Address: " + Constants.BOLD_END + model.party_address));
        b.number.setText(Html.fromHtml(Constants.BOLD_START + "Number: " + Constants.BOLD_END + model.number));
        b.location.setText(Html.fromHtml(Constants.BOLD_START + "Location: " + Constants.BOLD_END + model.location));
        b.city.setText(Html.fromHtml(Constants.BOLD_START + "City: " + Constants.BOLD_END + model.city));
        b.numberofgasburners.setText(Html.fromHtml(Constants.BOLD_START + "No of gas burners: " + Constants.BOLD_END + model.number_of_gas_burners));
        b.kitchenTools.setText(model.kitchen_tools_list.toString());
        b.breakfastmenu.setText(model.breakfast_items);
        b.lunchmenu.setText(model.lunch_items);
        b.dinnermenu.setText(model.dinner_items);
        b.snacksmenu.setText(model.snack_items);

        b.acceptJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(JobDetailsActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to accept this job?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploadJobToAcceptedBooking();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        progressDialog = new ProgressDialog(JobDetailsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private ProgressDialog progressDialog;

    private void uploadJobToAcceptedBooking() {
        progressDialog.show();

        JobsAdminModel2 jobsAdminModel2 = new JobsAdminModel2();

        jobsAdminModel2.id = model.id;
        jobsAdminModel2.occasion_type = model.occasion_type;
        jobsAdminModel2.name = model.name;
        jobsAdminModel2.number = model.number;
        jobsAdminModel2.party_address = model.party_address;
        jobsAdminModel2.location = model.location;
        jobsAdminModel2.city = model.city;
        jobsAdminModel2.number_of_people = model.number_of_people;
        jobsAdminModel2.date = model.date;
        jobsAdminModel2.time = model.time;
        jobsAdminModel2.no_of_dishes = model.no_of_dishes;
        jobsAdminModel2.dish_type = model.dish_type;
        jobsAdminModel2.staff_required = model.staff_required;
        jobsAdminModel2.payment = model.payment;
        jobsAdminModel2.number_of_gas_burners = model.number_of_gas_burners;
        jobsAdminModel2.breakfast_items = model.breakfast_items;
        jobsAdminModel2.lunch_items = model.lunch_items;
        jobsAdminModel2.dinner_items = model.dinner_items;
        jobsAdminModel2.snack_items = model.snack_items;
        jobsAdminModel2.push_key = model.push_key;
        jobsAdminModel2.nameChef = chefUserModel.name;
        jobsAdminModel2.uidChef = Constants.auth().getUid();
        jobsAdminModel2.post = chefUserModel.post;
        jobsAdminModel2.numberChef = chefUserModel.number;
        jobsAdminModel2.expertInChef = chefUserModel.expertInList.toString();
        jobsAdminModel2.highestQualificationChef = chefUserModel.highest_education;
        jobsAdminModel2.experienceYearsChef = chefUserModel.total_experience;
        jobsAdminModel2.job_open = model.job_open;
        jobsAdminModel2.kitchen_tools_list = model.kitchen_tools_list;
        jobsAdminModel2.cuisines_list = model.cuisines_list;

        // ADDING JOB DATA TO ADMIN CHILD
        Constants.databaseReference()
                .child(Constants.ACCEPTED_JOBS)
                .child(model.push_key)
                .setValue(jobsAdminModel2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // MAKING JOB OPEN STATUS FALSE
                            Constants.databaseReference()
                                    .child(Constants.ADMIN_BOOKINGS)
                                    .child(model.push_key)
                                    .child("job_open")
                                    .setValue(false);

                            // ADDING JOB DATA TO OUR OWN CHILD
                            Constants.databaseReference()
                                    .child(Constants.auth().getUid())
                                    .child(Constants.ACCEPTED_JOBS)
                                    .child(model.push_key)
                                    .setValue(jobsAdminModel2);

                            b.jobStatusJobDetails.setText("Closed");
                            b.jobStatusJobDetails.setBackgroundColor(getResources().getColor(R.color.red));
                            b.acceptJobBtn.setVisibility(View.GONE);

                            progressDialog.dismiss();
                            Toast.makeText(JobDetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            uploadNotification();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(JobDetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadNotification() {
        new FcmNotificationsSender(
                "/topics/" + Constants.ADMIN_NOTIFICATIONS,
                "Job Accepted",
                "Chef" + chefUserModel.name +
                        " has accepted a job in " + model.city,
                getApplicationContext(),
                JobDetailsActivity.this)
                .SendNotifications();
    }

}