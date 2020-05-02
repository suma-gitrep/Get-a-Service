package com.example.getaservice.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.getaservice.BookingModel;
import com.example.getaservice.ExampleDialog;
import com.example.getaservice.R;
import com.example.getaservice.Shared;
import com.example.getaservice.Workermodel;
import com.example.getaservice.adapter.BookingsAdater;
import com.example.getaservice.adapter.MyAdapter;
import com.example.getaservice.adapter.MyCustomPagerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements ExampleDialog.ExampleDialogListener {

    private HomeViewModel homeViewModel;
    private RecyclerView rv;
    TextView dailogtext;
    MyAdapter choiceAdapter = null;
    private GestureDetectorCompat detector = null;
    ArrayList<Workermodel> workermodelArrayList=new ArrayList<Workermodel>();
    ArrayList<BookingModel> bookingModelArrayList = new ArrayList<BookingModel>();
    MyAdapter myAdapter;
    BookingsAdater bookingsAdater;
    Shared shared;
    ViewPager viewPager;
    EditText currentlocation;
    int images[] = {R.drawable.getaservice, R.drawable.slider_2 };
    MyCustomPagerAdapter myCustomPagerAdapter;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //   FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        shared = new Shared(getActivity());
        final TextView text = root.findViewById(R.id.text);
        ImageView filterImg = root.findViewById(R.id.filter);
        dailogtext =root.findViewById(R.id.dailogtext);
        currentlocation=root.findViewById(R.id.simpleSearchView);
viewPager=root.findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(getActivity(), images);
        viewPager.setAdapter(myCustomPagerAdapter);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();
        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // String array for alert dialog multi choice items
                final String[] colors = new String[]{
                        "Laboratory",
                        "Plumber",
                        "carpentry",
                        "Babysitting",
                        "home services"
                };

                // Boolean array for initial selected items
                final boolean[] checkedColors = new boolean[]{
                        false,
                        false,
                        false,
                        false,
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


                        if(isChecked){
                            mUserItems.add(which);
                        }else{
                            mUserItems.remove((Integer.valueOf(which)));
                        }
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
                builder.setTitle("Your preferred Categories?");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + colors[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }

                        System.out.println("Selected list:"+item);
                        mUserItems.clear();
                        dailogtext.setText(item);

                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        // Do something when click the negative button
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                        for (int i = 0; i < checkedColors.length; i++) {
                            checkedColors[i] = false;
                            mUserItems.clear();
                           // mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        //REFERENCE
        rv = (RecyclerView) root.findViewById(R.id.interplanettary_RV);
        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));



        Workermodel workermodel = new Gson().fromJson(shared.getWorkerModel(),Workermodel.class);
        if (shared.getIsUserLoggedIn().equalsIgnoreCase("true") && workermodel.getUsertype().equalsIgnoreCase("worker")){
            getBookings();
        }
        else {
            getUsers();
        }
        return root;




    }



    @Override
    public void applyTexts(String username, String password) {

        Toast toast = Toast.makeText(getActivity(), "Clicked on "
                , Toast.LENGTH_SHORT);
    }




    public void getUsers() {
        myAdapter=new MyAdapter(getActivity(), workermodelArrayList);
        rv.setAdapter(myAdapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        workermodelArrayList.clear();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // A new data item has been added, add it to the list
                // message = dataSnapshot.getValue(Message.class);
                //   messageList.add(message);
                Workermodel workerModel = dataSnapshot.getValue(Workermodel.class);
                Log.v("wokermodel", workerModel.getName());
                if(workerModel.getUsertype().equals("worker")){
                    workermodelArrayList.add(workerModel);
    myAdapter.notifyDataSetChanged();
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
                if(bookingModel.getWorker().equals(workermodel.getName())){
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

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
//                                    latTextView.setText(location.getLatitude()+"");
//                                    lonTextView.setText(location.getLongitude()+"");

                                    getAddress(location.getLatitude(),location.getLongitude());

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //String name=mLastLocation.getProvider();
            //System.out.println("sumamma"+name);
//            latTextView.setText(mLastLocation.getLatitude()+"");
//            lonTextView.setText(mLastLocation.getLongitude()+"");
            getAddress(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            Log.v("IGA", "Address  " + add);

            currentlocation.setText(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}