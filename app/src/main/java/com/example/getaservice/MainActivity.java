package com.example.getaservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    // FirebaseAuth auth;
    // FirebaseUser user;
    ProgressDialog PD;
    private Context an = this;
    SharedPreferences.Editor edit;

    SharedPreferences pref, workpref;
    String usernametext, usertypestr;
    String wemail, wusername, wutpe;
    // String name, email, pass, phone, address, confirm, certification, cate, experience, charges, status, usertype;
    boolean isUserLoggedIn = false;
    Workermodel workermodel = new Workermodel();
    Shared shared;
    private Gson gson;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shared = new Shared(MainActivity.this);
        gson = new Gson();
        //  FloatingActionButton fab = findViewById(R.id.fab);
        // auth = FirebaseAuth.getInstance();
        //  user = auth.getCurrentUser();
        View header;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        ImageView nav_head_image = header.findViewById(R.id.imageView);
        TextView headerTitle = header.findViewById(R.id.headertitle);
        TextView headerSubtitle = header.findViewById(R.id.headersubtitle);

        String isUserExists = shared.getIsUserLoggedIn();
        if (isUserExists.equalsIgnoreCase("true")) {
            isUserLoggedIn = true;
            String userDetails = shared.getWorkerModel();
            workermodel = gson.fromJson(userDetails, Workermodel.class);
            nav_head_image.setImageResource(R.drawable.editprofile);
            headerTitle.setText("Welcome " + workermodel.getName());
            headerSubtitle.setText(workermodel.getEmail());
        } else {
            nav_head_image.setImageResource(R.drawable.userheader);
            headerTitle.setText(R.string.nav_header_title);
            headerSubtitle.setText(R.string.nav_header_subtitle);
        }


        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        /*try {
            Intent intent = getIntent();
            usertype = intent.getStringExtra("usertype");
            if (usertype.equals("worker")) {
                name = intent.getStringExtra("name");
                email = intent.getStringExtra("email");
                pass = intent.getStringExtra("password");
                phone = intent.getStringExtra("phone");
                certification = intent.getStringExtra("certification");
                address = intent.getStringExtra("address");
                cate = intent.getStringExtra("category");
                experience = intent.getStringExtra("experience");
                charges = intent.getStringExtra("charges");
                status = intent.getStringExtra("status");
            } else {
                name = intent.getStringExtra("name");
                email = intent.getStringExtra("email");
                pass = intent.getStringExtra("password");
                phone = intent.getStringExtra("phone");
                confirm = intent.getStringExtra("confirm");
                address = intent.getStringExtra("address");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/



       /* if (email != null) {
            nav_head_image.setImageResource(R.drawable.editprofile);
            headerTitle.setText("Welcome " + name);
            headerSubtitle.setText(email);
        }*/

        nav_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserLoggedIn == false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (workermodel != null && workermodel.getUsertype().equalsIgnoreCase("worker")) {
                    Intent intent = new Intent(MainActivity.this, WorkerEditActivity.class);
                    startActivity(intent);
                } else if (workermodel != null && workermodel.getUsertype().equalsIgnoreCase("customer")) {
                    Intent intent = new Intent(MainActivity.this, CustomerEditActivity.class);
                    startActivity(intent);

                }
            }
        });


//           if (wutpe!=null) {
//                nav_head_image.setImageResource(R.drawable.editprofile);
//                headerTitle.setText("Welcome " + wusername);
//                headerSubtitle.setText(wemail);
//                nav_head_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent intent = new Intent(MainActivity.this, WorkerEditActivity.class);
//                        startActivity(intent);
//
//
//                    }
//                });
//
//            }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,
                R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*NavInflater inflater = navController.getNavInflater();
        NavGraph graph;
        if (isUserLoggedIn) {
            graph = inflater.inflate(R.navigation.mobile_navigation);
        } else {
            graph = inflater.inflate(R.navigation.mobile_navigation_duplicate);
        }*/
        //navController.setGraph(graph);
        if (isUserLoggedIn && workermodel.getUsertype().equalsIgnoreCase("customer")) {
            navigationView.getMenu().findItem(R.id.nav_gallery).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_gallery).setVisible(false);
        }
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_logout:
                shared.setIsUserLoggedIn("false");
                shared.setWorkerModel("");
                Toast.makeText(MainActivity.this, "Successfully logged out of the app", Toast.LENGTH_LONG).show();
                recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void applyTexts(String username, String password) {

    }

}