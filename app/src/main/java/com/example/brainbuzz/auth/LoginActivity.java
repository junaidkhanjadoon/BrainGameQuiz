package com.example.brainbuzz.auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.brainbuzz.MainActivity;
import com.example.brainbuzz.R;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.ActivityLoginBinding;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.UsersModel;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String email, password;
    DatabaseHelper databaseHelper;
    Boolean checkDetails = false;
    List<UsersModel> list = new ArrayList<>();
    int request_code = 123;
    private static final int PERMISSION_REQUEST_CODE = 1;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Check and request storage permission if needed
        if (!fnCheckReadStoragePermission()) {
            fnRequestStoragePermission(request_code);
        }

        // Set the label for the top text view
        binding.rlTop.tvLabel.setText("Login");

        // Set a click listener to navigate to the RegisterActivity when the register layout is clicked
        binding.llRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Set a click listener for the login button
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user input
                if (isValidated()) {
                    // Retrieve all users from the database
                    list = databaseHelper.getAllUsers();
                    for (UsersModel users : list) {
                        // Check if the entered email and password match any user
                        if (email.equals(users.getEmail()) && password.equals(users.getPassword())) {
                            hideKeyboard();
                            checkDetails = true;
                            showMessage("Successfully Login");
                            // Set the logged-in user in the HelperClass
                            HelperClass.users = users;
                            // Start the MainActivity for the regular user
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                            break;

                        }
                    }
                    if (!checkDetails) {
                        showMessage("Wrong Credentials...\nPlease check email or password");
                    }
                }
            }
        });
    }

    // Validate user input for email and password
    private Boolean isValidated() {
        email = binding.emailEt.getText().toString().trim();
        password = binding.passET.getText().toString().trim();

        if (email.isEmpty()) {
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
            return false;
        }
        if (password.isEmpty()) {
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    // Show a toast message
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Request storage permission using the appropriate method based on Android version
    public void fnRequestStoragePermission(int resultCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, resultCode);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, resultCode);
        }
    }

    // Check if read storage permission is granted
    public boolean fnCheckReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
