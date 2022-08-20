package com.moutamid.chefdarbari.chef.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.ActivityJobDetailsBinding;
import com.moutamid.chefdarbari.models.JobsAdminModel;
import com.moutamid.chefdarbari.utils.Constants;

import java.util.Objects;

public class JobDetailsActivity extends AppCompatActivity {

    private ActivityJobDetailsBinding b;
    private JobsAdminModel model = (JobsAdminModel) Stash.getObject(Constants.CURRENT_JOB_MODEL, JobsAdminModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        b.idJobDetails.setText("ID: "+model.id);
        b.nameJobDetails.setText("Name: "+model.name);
        b.staff.setText("Staff Required: "+model.staff_required);
        b.payment.setText("Payment: "+model.payment + "₹");
        b.occasion.setText("Occasion Type: "+model.occasion_type);
        b.partydate.setText("Date: "+model.date);
        b.numberofpeople.setText("Number of people: "+model.number_of_people);
        b.time.setText("Time: "+model.time);
        b.numberofdishes.setText("No of dishes: "+model.no_of_dishes);
        b.cuisinesList.setText("Cuisines: "+model.cuisines_list.toString());
        b.dishtype.setText("Dish Type: "+model.dish_type);
        b.partyaddress.setText("Party Address: "+model.party_address);
        b.number.setText("Number: "+model.number);
        b.location.setText("Location: "+model.location);
        b.city.setText("City: "+model.city);
        b.numberofgasburners.setText("No of gas burners: "+model.number_of_gas_burners);
        b.kitchenTools.setText(model.kitchen_tools_list.toString());
        b.breakfastmenu.setText(model.breakfast_items);
        b.lunchmenu.setText(model.lunch_items);
        b.dinnermenu.setText(model.dinner_items);
        b.snacksmenu.setText(model.snack_items);

        b.acceptJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobDetailsActivity.this, "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}