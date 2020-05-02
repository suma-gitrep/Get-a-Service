package com.example.getaservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WorkerEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button save, cancel;
    Shared shared;
    String categorystr;

    EditText username, password, email, phonenumber, experience, certification, charges, address;
    Switch availability;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editworkerprofile);
        shared = new Shared(WorkerEditActivity.this);
        save = findViewById(R.id.savebutton);
        cancel = findViewById(R.id.cancel);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.userName);
        address = (EditText) findViewById(R.id.address);
        phonenumber = (EditText) findViewById(R.id.phone);
        charges = (EditText) findViewById(R.id.charges);

        experience = (EditText) findViewById(R.id.experience);
        certification = (EditText) findViewById(R.id.certifications);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        availability = (Switch) findViewById(R.id.switch1);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select A Category");
        categories.add("Plumber");
        categories.add("Carpenter");
        categories.add("Electrician");
        categories.add("cleaner");
        categories.add("food services");
        categories.add("Baby Sitter");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Workermodel workermodel = new Gson().fromJson(shared.getWorkerModel(), Workermodel.class);
        email.setText(workermodel.getEmail());
        password.setText(workermodel.getPasswordstr());
        username.setText(workermodel.getName());
        address.setText(workermodel.getAddressstr());
        phonenumber.setText(workermodel.getPhonestr());
        charges.setText(workermodel.getChargestr());
        experience.setText(workermodel.getExperiencestr());
        certification.setText(workermodel.getCertificationstr());
name=workermodel.getName();
        if (workermodel.getStatus().equalsIgnoreCase("yes")) {
            availability.setChecked(true);
        } else {
            availability.setChecked(false);

        }

        categorystr = workermodel.getCategory();
        System.out.println(categorystr);
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition = myAdap.getPosition(categorystr);
username.setEnabled(false);
//set the default according to value
        spinner.setSelection(spinnerPosition);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //final String usernamestr= username.getText().toString();
                final String passwordstr = password.getText().toString();


                final String emailstr = email.getText().toString();
                final String phonestr=phonenumber.getText().toString();

                final String experiencestr=experience.getText().toString();
                final String certificationstr=certification.getText().toString();
                final String chargestr=charges.getText().toString();

                final String addressstr=address.getText().toString();

                final String category = spinner.getSelectedItem().toString();
                final String status;

                if (availability.isChecked())
                    status = "yes";
                else
                    status ="no";
                if(email.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if( password.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if( username.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if( charges.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter charges password", Toast.LENGTH_SHORT).show();
                }
                else if( phonenumber.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if( certification.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if( experience.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }

                else if( address.getText().length()<=0){
                    Toast.makeText(WorkerEditActivity.this, "Enter address details", Toast.LENGTH_SHORT).show();
                }
else {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(name);
                    Workermodel workermodel = new Workermodel(name, passwordstr,emailstr , phonestr, experiencestr, certificationstr,chargestr, addressstr,category, status, "worker");

                    //updating artist
                    dR.setValue(workermodel);
                    Gson gson = new Gson();
                    shared.setWorkerModel(gson.toJson(workermodel));
                    shared.setIsUserLoggedIn("true");
                    Toast.makeText(WorkerEditActivity.this, "Succesfully  edited all user details", Toast.LENGTH_LONG).show();

                    finish();


//                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(in);
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        WorkerEditActivity.this,
                        "Cancelled to save the data",
                        Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}