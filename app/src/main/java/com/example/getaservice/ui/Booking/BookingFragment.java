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

import com.example.getaservice.R;
import com.example.getaservice.adapter.BookingsAdater;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private RecyclerView rv;
    BookingsAdater choiceAdapter = null;
    private GestureDetectorCompat detector = null;

    private static String[] spacecrafts={"JHON MARKER","CHRLES HOOT","MICHAEL","SCOTT","ROGER WLLIAMS","KATE HELLIO"};



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        rv= (RecyclerView) root.findViewById(R.id.interplanettary_RV);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        rv.setAdapter(new BookingsAdater(getActivity(),spacecrafts));

        detector = new GestureDetectorCompat(getActivity(), new BookingFragment.RecyclerViewOnGestureListener());

        rv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });

        bookingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;



    }
    class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        //ontap to list_item remove the item from the list
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = rv.getChildViewHolder(view);
                if (holder instanceof BookingsAdater.RecyclerVH) {
                    int position = holder.getAdapterPosition();

                    // handling single tap.
                    Log.d("click", "clicked on item " + position);
                    // ChoiceModel myModel = ChoiceModel.getSingleton();
                    Toast toast = Toast.makeText(getActivity(),"Clicked on "
                            , Toast.LENGTH_SHORT);

                    toast.show();
                    return true;
                }
            }
            return false;
        }
    }

}