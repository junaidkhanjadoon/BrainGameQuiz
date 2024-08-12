package com.example.brainbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.brainbuzz.auth.LoginActivity;
import com.example.brainbuzz.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    Animation fromTop, fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the layout using View Binding
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        // Load animations for the logo and app name
        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        // Apply animations to the logo and app name
        binding.tvAppName.setAnimation(fromTop);
        binding.cvLogo.setAnimation(fromTop);
        binding.tvLoading.setAnimation(fromBottom);

        // Start a thread to display the splash screen for a specific duration
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // Sleep for 3000 milliseconds (3 seconds)
                    sleep(3000);

                    // Start the LoginActivity and finish the SplashActivity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    // Handle any interruption exceptions and show a toast message
                    Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        // Start the thread
        thread.start();
    }
}
