package com.example.brainbuzz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brainbuzz.R;
import com.example.brainbuzz.adapter.HistoryAdapter;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.FragmentHistoryBinding;
import com.example.brainbuzz.databinding.FragmentHomeBinding;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.HistoryModel;

import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private HistoryAdapter historyAdapter;
    private DatabaseHelper dbHelper;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rlTop.tvLabel.setText("History");
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new DatabaseHelper(getContext());
        List<HistoryModel> historyList = dbHelper.getHistoryByUserId(HelperClass.users.getId());
        if (historyList.isEmpty()){
            binding.rvHistory.setVisibility(View.GONE);
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
        }else{
            binding.rvHistory.setVisibility(View.VISIBLE);
            binding.tvNoDataFound.setVisibility(View.GONE);
        }
        historyAdapter = new HistoryAdapter(getContext(), historyList);
        binding.rvHistory.setAdapter(historyAdapter);

    }
}