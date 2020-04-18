package com.example.getaservice.ui.Booking;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.BookingModel;
import com.example.getaservice.R;
import com.example.getaservice.Shared;
import com.example.getaservice.Workermodel;
import com.example.getaservice.adapter.BookingsAdater;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private RecyclerView rv;
    BookingsAdater choiceAdapter = null;
    private GestureDetectorCompat detector = null;
    BookingsAdater bookingsAdater;
    Shared shared;
    ArrayList<BookingModel> bookingModelArrayList = new ArrayList<BookingModel>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        rv= (RecyclerView) root.findViewById(R.id.interplanettary_RV);
        shared = new Shared(getActivity());

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        getBookings();

        return root;



    }


    public void getBookings() {
        bookingsAdater=new BookingsAdater(getActivity(), bookingModelArrayList);
        rv.setAdapter(bookingsAdater);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
        bookingModelArrayList.clear();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // A new data item has been added, add it to the list
                // message = dataSnapshot.getValue(Message.class);
                //   messageList.add(message);
                BookingModel bookingModel = dataSnapshot.getValue(BookingModel.class);
                String userDetails = shared.getWorkerModel();
                Workermodel workermodel = new Gson().fromJson(userDetails, Workermodel.class);
                if(bookingModel.getCustomer().equals(workermodel.getName())){
                    bookingModelArrayList.add(bookingModel);
                    bookingsAdater.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A data item has changed
                //  Message message = dataSnapshot.getValue(Message.class);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A data item has been removed
                //Message message = dataSnapshot.getValue(Message.class);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //  Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A data item has changed position
                //    Comment movedComment = dataSnapshot.getValue(Comment.class);
                //    Message message = dataSnapshot.getValue(Message.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        };

        reference.addChildEventListener(childEventListener);
    }

}