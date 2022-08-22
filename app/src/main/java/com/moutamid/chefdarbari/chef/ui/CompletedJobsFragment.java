package com.moutamid.chefdarbari.chef.ui;

import static android.view.LayoutInflater.from;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentCompletedJobsBinding;
import com.moutamid.chefdarbari.models.JobsAdminModel;
import com.moutamid.chefdarbari.models.JobsAdminModel2;
import com.moutamid.chefdarbari.utils.Constants;

import java.util.ArrayList;

public class CompletedJobsFragment extends Fragment {

    private FragmentCompletedJobsBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCompletedJobsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

        Constants.databaseReference()
                .child(Constants.auth().getUid())
                .child(Constants.COMPLETED_JOBS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel2 adminModel = dataSnapshot.getValue(JobsAdminModel2.class);
                                tasksArrayList.add(adminModel);
                            }
                            initRecyclerView();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return root;
    }

    private ArrayList<JobsAdminModel2> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.completedJobsRecyclerView;
        //conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        //linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

//    if (adapter.getItemCount() != 0) {

//        noChatsLayout.setVisibility(View.GONE);
//        chatsRecyclerView.setVisibility(View.VISIBLE);

//    }

    }

/*public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
    int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
    return noOfColumns;
}*/

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = from(parent.getContext()).inflate(R.layout.completed_jobs_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            JobsAdminModel2 model = tasksArrayList.get(position);

            holder.name.setText("Name: " + model.name);

            holder.id.setText("ID: " + model.id);

            holder.staff_required.setText("Staff Required: " + model.staff_required);
            holder.payment.setText("Payment: " + model.payment + "₹");
            holder.occasion.setText("Occasion Type: " + model.occasion_type);
            holder.party_date.setText("Party Date: " + model.date);
            holder.number_of_people.setText("Number of people: " + model.number_of_people);
            holder.time.setText("Time: " + model.time);
            holder.number_of_dishes.setText("No of dishes: " + model.no_of_dishes);
            holder.cuisines.setText("Cuisines: " + model.cuisines_list.toString());
            holder.party_address.setText("Party Address: " + model.party_address);
        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {
            TextView name, id, staff_required, payment,
                    occasion, party_date, number_of_people, time,
                    number_of_dishes, cuisines, party_address;
            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                name = v.findViewById(R.id.name_completed_jobs);
                id = v.findViewById(R.id.id_number_completed_jobs);
                staff_required = v.findViewById(R.id.staff_required_completed_jobs);
                payment = v.findViewById(R.id.payment_completed_jobs);
                occasion = v.findViewById(R.id.occasion_completed_jobs);
                party_date = v.findViewById(R.id.party_date_completed_jobs);
                number_of_people = v.findViewById(R.id.number_of_people_completed_jobs);
                time = v.findViewById(R.id.time_completed_jobs);
                number_of_dishes = v.findViewById(R.id.number_of_dishes_completed_jobs);
                cuisines = v.findViewById(R.id.cuisines_completed_jobs);
                party_address = v.findViewById(R.id.party_address_completed_jobs);
            }
        }

    }
}