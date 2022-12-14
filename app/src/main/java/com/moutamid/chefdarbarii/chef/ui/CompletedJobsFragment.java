package com.moutamid.chefdarbarii.chef.ui;

import static android.view.LayoutInflater.from;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.FragmentCompletedJobsBinding;
import com.moutamid.chefdarbarii.R;

import com.moutamid.chefdarbarii.models.JobsAdminModel2;
import com.moutamid.chefdarbarii.utils.Constants;

import java.util.ArrayList;

public class CompletedJobsFragment extends Fragment {

    private FragmentCompletedJobsBinding b;
    private ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCompletedJobsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();
        if (Stash.getBoolean(Constants.PAUSE_STATUS, false))
            return b.getRoot();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Constants.databaseReference()
                .child(Constants.USERS)
                .child(Constants.CHEF)
                .child(Constants.auth().getUid())
                .child(Constants.COMPLETED_JOBS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!isAdded()) return;
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel2 adminModel = dataSnapshot.getValue(JobsAdminModel2.class);
                                tasksArrayList.add(adminModel);
                            }
                            progressDialog.dismiss();

                            initRecyclerView();
                        }else progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

//    if (adapter.getItemCount() != 0) {

//        noChatsLayout.setVisibility(View.GONE);
//        chatsRecyclerView.setVisibility(View.VISIBLE);

//    }

    }


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

            holder.name.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: " + Constants.BOLD_END + model.name));

            holder.id.setText(Html.fromHtml(Constants.BOLD_START + "Job Id: " + Constants.BOLD_END + model.id));

            holder.staff_required.setText(Html.fromHtml(Constants.BOLD_START + "Staff Required: " + Constants.BOLD_END + model.staff_required));
            holder.payment.setText(Html.fromHtml(Constants.BOLD_START + "Payment: " + Constants.BOLD_END + "???"+model.payment));
            holder.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: " + Constants.BOLD_END + model.occasion_type));
            holder.party_date.setText(Html.fromHtml(Constants.BOLD_START + "Party Date: " + Constants.BOLD_END + model.date));
            holder.number_of_people.setText(Html.fromHtml(Constants.BOLD_START + "Number of people: " + Constants.BOLD_END + model.number_of_people));
            holder.time.setText(Html.fromHtml(Constants.BOLD_START + "Time: " + Constants.BOLD_END + model.time));
            holder.number_of_dishes.setText(Html.fromHtml(Constants.BOLD_START + "No of dishes: " + Constants.BOLD_END + model.no_of_dishes));
            holder.cuisines.setText(Html.fromHtml(Constants.BOLD_START + "Cuisines: " + Constants.BOLD_END + model.cuisines_list.toString()));
            holder.party_address.setText(Html.fromHtml(Constants.BOLD_START + "Party Address: " + Constants.BOLD_END + model.party_address));
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