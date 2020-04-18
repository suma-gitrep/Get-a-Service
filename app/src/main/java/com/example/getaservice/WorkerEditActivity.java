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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WorkerEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button save,cancel;
    Shared shared;

    EditText username,password,email,phonenumber,experience,certification,charges,address;
    Switch availability;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editworkerprofile);
        shared = new Shared(WorkerEditActivity.this);
        save=findViewById(R.id.savebutton);
        cancel=findViewById(R.id.cancel);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        username=(EditText)findViewById(R.id.userName);
        address=(EditText)findViewById(R.id.address);
        phonenumber=(EditText)findViewById(R.id.phone);
        charges=(EditText)findViewById(R.id.charges);

        experience = (EditText) findViewById(R.id.experience);
        certification = (EditText) findViewById(R.id.certifications);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

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
if(workermodel.getStatus().equalsIgnoreCase("yes")){
    availability.setChecked(true);
}
else {
    availability.setChecked(false);

}



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(WorkerEditActivity.this, "Succesfully  saved all user data", Toast.LENGTH_LONG).show();


                Intent in=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        WorkerEditActivity.this,
                        "Cancelled to save the data",
                        Toast.LENGTH_LONG).show();
                Intent in=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        });
    }

<<<<<<< Updated upstream


=======
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
>>>>>>> Stashed changes

    }
}
