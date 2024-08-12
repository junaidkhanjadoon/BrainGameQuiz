package com.example.brainbuzz.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.brainbuzz.R;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.ActivityRegisterBinding;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.UsersModel;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    String name, username, email, bio, password;
    DatabaseHelper databaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set the label for the top text view
        binding.rlTop.tvLabel.setText("Register");

        // Set visibility and click listeners for the back button and bottom layout
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v -> finish());
        binding.llBottom.setOnClickListener(v -> finish());

        // Set a click listener for the register button
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user input and register if valid
                if (isValidated()){
                    UsersModel users = new UsersModel(name, username, email, bio, password, "");
                    boolean registrationSuccessful = databaseHelper.register(users);

                    if (registrationSuccessful) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                   // databaseHelper.register(users);
                  //  showMessage("Successfully Registered");
                    finish();
                }
            }
        });
    }

    // Validate user input for registration
    private Boolean isValidated(){
        name = binding.nameEt.getText().toString().trim();
        username = binding.usernameEt.getText().toString().trim();
        email = binding.emailEt.getText().toString().trim();
        bio = binding.bioEt.getText().toString().trim();
        password = binding.passET.getText().toString().trim();

        if (name.isEmpty()){
            showMessage("Please enter name");
            return false;
        }
        if (username.isEmpty()){
            showMessage("Please enter username");
            return false;
        }
        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
            return false;
        }
        if (password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }
        if (bio.isEmpty()){
            showMessage("Please enter bio");
            return false;
        }

        return true;
    }

    // Show a toast message
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
