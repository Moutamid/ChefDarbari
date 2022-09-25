package com.moutamid.chefdarbarii.chef.ui;

import static android.view.LayoutInflater.from;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.FragmentAcceptedJobsBinding;
import com.moutamid.chefdarbarii.R;

import com.moutamid.chefdarbarii.models.JobsAdminModel2;
import com.moutamid.chefdarbarii.notifications.FcmNotificationsSender;
import com.moutamid.chefdarbarii.utils.Constants;

import java.util.ArrayList;


public class AcceptedJobsFragment extends Fragment {
    private static final String TAG = "AcceptedJobsFragment";
    private FragmentAcceptedJobsBinding b;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAcceptedJobsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        Log.d(TAG, "onCreateView: ");
        if (!isAdded()) return b.getRoot();

        if (Stash.getBoolean(Constants.PAUSE_STATUS, false))
            return b.getRoot();

        Log.d(TAG, "onCreateView: ");
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Constants.databaseReference()
                .child(Constants.USERS)
                .child(Constants.CHEF)
                .child(Constants.auth().getUid())
                .child(Constants.ACCEPTED_JOBS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: ");
                        if (!isAdded()) return;
                        Log.d(TAG, "onDataChange: if (!isAdded()) return;");
                        if (snapshot.exists()) {
                            Log.d(TAG, "onDataChange: if (snapshot.exists()) {");
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel2 adminModel = dataSnapshot.getValue(JobsAdminModel2.class);
                                tasksArrayList.add(adminModel);
                            }
                            progressDialog.dismiss();
                            initRecyclerView();
                        } else {
                            Log.d(TAG, "onDataChange: }else{");
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: ERROR: " + error.toException().getMessage());
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
        conversationRecyclerView = b.acceptedJobsRecyclerview;
        adapter = new RecyclerViewAdapterMessages();
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
            View view = from(parent.getContext()).inflate(R.layout.accepted_jobs_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            JobsAdminModel2 model = tasksArrayList.get(position);

            holder.name.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: " +
                    Constants.BOLD_END + model.name));

            holder.id.setText(Html.fromHtml(Constants.BOLD_START + "Job Id: " + Constants.BOLD_END + model.id));

            holder.staff_required.setText(Html.fromHtml(Constants.BOLD_START + "Staff Required: " + Constants.BOLD_END + model.staff_required));
            holder.payment.setText(Html.fromHtml(Constants.BOLD_START + "Payment: " + Constants.BOLD_END + "₹" + model.payment));
            holder.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: " + Constants.BOLD_END + model.occasion_type));
            holder.party_date.setText(Html.fromHtml(Constants.BOLD_START + "Party Date: " + Constants.BOLD_END + model.date));
            holder.number_of_people.setText(Html.fromHtml(Constants.BOLD_START + "Number of people: " + Constants.BOLD_END + model.number_of_people));
            holder.time.setText(Html.fromHtml(Constants.BOLD_START + "Time: " + Constants.BOLD_END + model.time));
            holder.number_of_dishes.setText(Html.fromHtml(Constants.BOLD_START + "No of dishes: " + Constants.BOLD_END + model.no_of_dishes));
            holder.cuisines.setText(Html.fromHtml(Constants.BOLD_START + "Cuisines: " + Constants.BOLD_END + model.cuisines_list.toString()));
            holder.party_address.setText(Html.fromHtml(Constants.BOLD_START + "Party Address: " + Constants.BOLD_END + model.party_address));

            holder.markAsCompletedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.paymentReceivedCB.isChecked()) {
                        Toast.makeText(requireContext(), "Please mark payment checkbox", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!holder.captureVideoCB.isChecked()) {
                        Toast.makeText(requireContext(), "Please mark capture video checkbox", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!holder.jobCompletedCB.isChecked()) {
                        Toast.makeText(requireContext(), "Please mark job completed checkbox", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // ADD CURRENT MODEL TO COMPLETED BOOKINGS
                    Constants.databaseReference()
                            .child(Constants.USERS)
                            .child(Constants.CHEF)
                            .child(Constants.auth().getUid())
                            .child(Constants.COMPLETED_JOBS)
                            .child(model.push_key)
                            .setValue(model);

                    // REMOVE CURRENT MODEL FROM ACCEPTED BOOKINGS
                    Constants.databaseReference()
                            .child(Constants.USERS)
                            .child(Constants.CHEF)
                            .child(Constants.auth().getUid())
                            .child(Constants.ACCEPTED_JOBS)
                            .child(model.push_key)
                            .removeValue();

                    // REMOVE CURRENT MODEL FROM ADMIN CHILD
                    Constants.databaseReference()
                            .child(Constants.ACCEPTED_JOBS)
                            .child(model.push_key)
                            .removeValue();

                    // ADD CURRENT MODEL TO ADMIN COMPLETED BOOKINGS
                    Constants.databaseReference()
                            .child(Constants.COMPLETED_JOBS)
                            .child(model.push_key)
                            .setValue(model);

                    uploadNotification(model);
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
                }
            });

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
            AppCompatCheckBox paymentReceivedCB, captureVideoCB, jobCompletedCB;
            Button markAsCompletedBtn;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                paymentReceivedCB = v.findViewById(R.id.payment_received_accepted_jobs);
                captureVideoCB = v.findViewById(R.id.capture_video_accepted_jobs);
                jobCompletedCB = v.findViewById(R.id.job_completed_accepted_jobs);
                markAsCompletedBtn = v.findViewById(R.id.mark_as_complete_btn_accepted_jobs);
                name = v.findViewById(R.id.name_accepted_jobs);
                id = v.findViewById(R.id.id_number_accepted_jobs);
                staff_required = v.findViewById(R.id.staff_required_accepted_jobs);
                payment = v.findViewById(R.id.payment_accepted_jobs);
                occasion = v.findViewById(R.id.occasion_accepted_jobs);
                party_date = v.findViewById(R.id.party_date_accepted_jobs);
                number_of_people = v.findViewById(R.id.number_of_people_accepted_jobs);
                time = v.findViewById(R.id.time_accepted_jobs);
                number_of_dishes = v.findViewById(R.id.number_of_dishes_accepted_jobs);
                cuisines = v.findViewById(R.id.cuisines_accepted_jobs);
                party_address = v.findViewById(R.id.party_address_accepted_jobs);
            }
        }

    }

    private void uploadNotification(JobsAdminModel2 model) {
        new FcmNotificationsSender(
                "/topics/" + Constants.ADMIN_NOTIFICATIONS,
                "Job Completed",
                "Chef completed Job Id: " + model.id +
                        " in city " + model.city,
                requireContext().getApplicationContext(),
                requireActivity())
                .SendNotifications();
    }
}