package com.example.getaservice.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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