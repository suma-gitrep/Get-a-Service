package com.example.getaservice.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.ExampleDialog;
import com.example.getaservice.LoginActivity;
import com.example.getaservice.R;
import com.example.getaservice.adapter.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements ExampleDialog.ExampleDialogListener {

    private HomeViewModel homeViewModel;
    private RecyclerView rv;
    MyAdapter choiceAdapter = null;
    private GestureDetectorCompat detector = null;

    private static String[] spacecrafts={"JHON MARKER","CHRLES HOOT","MICHAEL","SCOTT","ROGER WLLIAMS","KATE HELLIO"};





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        final TextView text = root.findViewById(R.id.text);

        ImageView filterImg=root.findViewById(R.id.filter);

        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // String array for alert dialog multi choice items
                String[] colors = new String[]{
                        "Laboratory",
                        "Plumber",
                        "carpentry",
                        "Babysitting",
                        "home services"
                };

                // Boolean array for initial selected items
                final boolean[] checkedColors = new boolean[]{
                        false,
                        true,
                        false,
                        true,
                        false

                };

                // Convert the color array to list
                final List<String> colorsList = Arrays.asList(colors);

                // Set multiple choice items for alert dialog

                builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedColors[which] = isChecked;

                        // Get the current focused item
                        String currentItem = colorsList.get(which);

                        // Notify the current action
                        Toast.makeText(getActivity(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Your preferred colors?");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
        //REFERENCE
        rv= (RecyclerView) root.findViewById(R.id.interplanettary_RV);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        rv.setAdapter(new MyAdapter(getActivity(),spacecrafts));

        detector = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener());

        rv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });


        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    //  finish();
                }
            }
        };




        return root;
    }

    @Override
    public void applyTexts(String username, String password) {

        Toast toast = Toast.makeText(getActivity(),"Clicked on "
                , Toast.LENGTH_SHORT);
    }

    class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        //ontap to list_item remove the item from the list
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = rv.getChildViewHolder(view);
                if (holder instanceof MyAdapter.RecyclerVH) {
                    int position = holder.getAdapterPosition();

                    // handling single tap.
                    Log.d("click", "clicked on item " + position);
                    // ChoiceModel myModel = ChoiceModel.getSingleton();
                    Toast toast = Toast.makeText(getActivity(),"Clicked on "
                            , Toast.LENGTH_SHORT);

                    toast.show();

                    openDialog();


                    return true;
                }
            }
            return false;
        }
    }

    private void openDialog() {


        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getActivity().getSupportFragmentManager(), "example dialog");

    }

}