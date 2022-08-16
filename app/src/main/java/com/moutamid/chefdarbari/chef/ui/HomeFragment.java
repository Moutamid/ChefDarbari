package com.moutamid.chefdarbari.chef.ui;

import android.content.Intent;
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

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbari.R;
import com.moutamid.chefdarbari.databinding.FragmentHomeBinding;
import com.moutamid.chefdarbari.models.JobsAdminModel;
import com.moutamid.chefdarbari.utils.Constants;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentHomeBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        Constants.databaseReference()
                .child(Constants.ADMIN_BOOKINGS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel adminModel = dataSnapshot.getValue(JobsAdminModel.class);
                                adminModel.push_key = dataSnapshot.getKey();
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

    private ArrayList<JobsAdminModel> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.newJobsRecyclerview;
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


    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_jobs_items, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            JobsAdminModel model = tasksArrayList.get(position);

            holder.name.setText(model.name);
            holder.id.setText("");
            holder.staff_required.setText(model.staff_required);
            holder.payment.setText(model.payment);
            holder.occasion.setText(model.occasion_type);
            holder.party_date.setText(model.party_address);
            holder.number_of_people.setText(model.number_of_people);
            holder.time.setText(model.time);
            holder.number_of_dishes.setText(model.no_of_dishes);
            holder.cuisines.setText(model.cuisines_list.toString());
            holder.party_address.setText(model.party_address);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Stash.put(Constants.CURRENT_JOB_MODEL, model);
                    startActivity(new Intent(requireContext(), JobDetailsActivity.class));
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
            CardView cardView;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                name = v.findViewById(R.id.name_new_jobs_item);
                id = v.findViewById(R.id.id_number_new_jobs_item);
                staff_required = v.findViewById(R.id.staff_required_new_jobs_item);
                payment = v.findViewById(R.id.payment_new_jobs_item);
                occasion = v.findViewById(R.id.occasion_new_jobs_item);
                party_date = v.findViewById(R.id.party_date_new_jobs_item);
                number_of_people = v.findViewById(R.id.number_of_people_new_jobs_item);
                time = v.findViewById(R.id.time_new_jobs_item);
                number_of_dishes = v.findViewById(R.id.number_of_dishes_new_jobs_item);
                cuisines = v.findViewById(R.id.cuisines_new_jobs_item);
                party_address = v.findViewById(R.id.party_address_new_jobs_item);

                cardView = v.findViewById(R.id.newjobscardview);

            }
        }

    }
}