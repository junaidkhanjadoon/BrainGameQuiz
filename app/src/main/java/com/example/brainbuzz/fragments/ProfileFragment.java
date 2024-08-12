package com.example.brainbuzz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.brainbuzz.R;
import com.example.brainbuzz.auth.LoginActivity;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.FragmentProfileBinding;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.UsersModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    DatabaseHelper databaseHelper;
    private static final int PICK_IMAGE_REQUEST = 1;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle any arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.main));

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the label for the top bar
        binding.rlTop.tvLabel.setText("Profile");

        // Set user details
        UsersModel currentUser = HelperClass.users;
        binding.tvName.setText(currentUser.getName());
        binding.tvUserName.setText(currentUser.getUserName());
        binding.tvBio.setText(currentUser.getBio());
        binding.tvEmail.setText(currentUser.getEmail());

        // Load and display the user's current image if exists
        if (currentUser.getImg() != null && !currentUser.getImg().isEmpty()) {
            try {
                Glide.with(binding.getRoot())
                        .asBitmap()
                        .load(Uri.parse(currentUser.getImg()))
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                // Now you have the Bitmap, you can set it as the image resource.
                                binding.ivImage.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                // This is called when the Drawable is cleared, for example, when the view is recycled.
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace(); // Log or handle invalid URI
            }
        }

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        // Set up the click listener for selecting an image
        binding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the document gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        binding.tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the document gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Set up click listener for logout button
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout and navigate to LoginActivity
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                binding.ivImage.setImageURI(imageUri);
               HelperClass.users.setImg(imageUri.toString());
               databaseHelper.updateUserImage(HelperClass.users.getId(), imageUri.toString());
            }
        }
    }

}