package com.moutamid.chefdarbarii.affiliate.ui;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.chefdarbarii.R;
import com.moutamid.chefdarbarii.databinding.FragmentMyBookingsBinding;
import com.moutamid.chefdarbarii.models.AffiliateAddBookingModel;
import com.moutamid.chefdarbarii.utils.Constants;

import java.util.ArrayList;


public class MyBookingsFragment extends Fragment {

    private FragmentMyBookingsBinding b;
    private ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentMyBookingsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Constants.databaseReference()
                .child(Constants.auth().getUid())
                .child(Constants.NEW_PARTY_BOOKINGS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                AffiliateAddBookingModel model = dataSnapshot.getValue(AffiliateAddBookingModel.class);
                                tasksArrayList.add(model);

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

    private ArrayList<AffiliateAddBookingModel> tasksArrayList = new ArrayList<AffiliateAddBookingModel>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.myBookingsRecyclerview;
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
            View view = from(parent.getContext()).inflate(R.layout.my_bookings_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            AffiliateAddBookingModel model = tasksArrayList.get(position);
            if (model.booking_confirmed) {
                holder.bookingConfirmed.setText("Confirmed");
                holder.bookingConfirmed.setBackgroundColor(getResources().getColor(R.color.lightgreen));
            } else {
                holder.bookingConfirmed.setText("Not Confirmed");
                holder.bookingConfirmed.setBackgroundColor(getResources().getColor(R.color.orange));
            }
            holder.id.setText(Html.fromHtml(Constants.BOLD_START + "Booking ID: " + Constants.BOLD_END + model.id));
            holder.name.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: " + Constants.BOLD_END + model.name));
            holder.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: " + Constants.BOLD_END + model.occasion_type));
            holder.party_date.setText(Html.fromHtml(Constants.BOLD_START + "Party Date: " + Constants.BOLD_END + model.date_of_party));
            holder.number_of_people.setText(Html.fromHtml(Constants.BOLD_START + "Number of people: " + Constants.BOLD_END + model.number_of_people));
            holder.time.setText(Html.fromHtml(Constants.BOLD_START + "Time: " + Constants.BOLD_END + model.time));
            holder.no_of_dishes.setText(Html.fromHtml(Constants.BOLD_START + "No of dishes: " + Constants.BOLD_END + model.number_of_dishes));
            holder.cuisines.setText(Html.fromHtml(Constants.BOLD_START + "Cuisines: " + Constants.BOLD_END + model.cuisinesList.toString()));
            holder.party_adress.setText(Html.fromHtml(Constants.BOLD_START + "Party Address: " + Constants.BOLD_END + model.party_venue_address));
            holder.number.setText(Html.fromHtml(Constants.BOLD_START + "Number: " + Constants.BOLD_END + model.number));

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView name, bookingConfirmed, id, payment, occasion, party_date,
                    number_of_people, time, no_of_dishes, cuisines, party_adress,
            number;

            //staffRequired
            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                bookingConfirmed = v.findViewById(R.id.booking_confirmed_mybookings_item);
                number = v.findViewById(R.id.number_my_bookings_item);
                id = v.findViewById(R.id.id_my_bookings_item);
                name = v.findViewById(R.id.name_my_bookings_item);
//                staffRequired = v.findViewById(R.id.staff_required_my_bookings_item);
                payment = v.findViewById(R.id.payment_my_bookings_item);
                occasion = v.findViewById(R.id.occasion_my_bookings_item);
                party_date = v.findViewById(R.id.party_date_my_bookings_item);
                number_of_people = v.findViewById(R.id.number_of_people_my_bookings_item);
                time = v.findViewById(R.id.time_my_bookings_item);
                no_of_dishes = v.findViewById(R.id.no_of_dishes_my_bookings_item);
                cuisines = v.findViewById(R.id.cuisines_my_bookings_item);
                party_adress = v.findViewById(R.id.party_adress_my_bookings_item);

            }
        }

    }
}