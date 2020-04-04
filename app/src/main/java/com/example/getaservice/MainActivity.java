package com.example.getaservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog PD;
    private Context an=this;
    SharedPreferences.Editor edit;

    SharedPreferences pref;
    String email,usernametext,usertypestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  FloatingActionButton fab = findViewById(R.id.fab);
        auth = FirebaseAuth.getInstance();
      //  user = auth.getCurrentUser();

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        View header;

        pref = getSharedPreferences("Login", 0);
        // retrieving value from Registration
         email = pref.getString("emaillogin", null);
         usernametext=pref.getString("usernameheader",null);
         usertypestr=pref.getString("usertypelogin",null);
       // Log.d("emai",email);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        ImageView nav_head_image=header.findViewById(R.id.imageView);
        TextView headerTitle=header.findViewById(R.id.headertitle);
        TextView headerSubtitle=header.findViewById(R.id.headersubtitle);

if(email!=null) {
    nav_head_image.setImageResource(R.drawable.editprofile);
    headerTitle.setText("Welcome "+usernametext);
    headerSubtitle.setText(email);
    nav_head_image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(usertypestr!=null) {

                Intent intent = new Intent(MainActivity.this, CustomerEditActivity.class);
                startActivity(intent);
            }
            else{

            }

        }
    });


}
else {
    nav_head_image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);


        }
    });

}
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                 R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.action_logout:
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("emaillogin");
                editor.commit();
                finish();

                Toast.makeText(MainActivity.this,"Successfully you logged out of the app", Toast.LENGTH_LONG).show();
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
