package com.example.foodapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.SellActivity;
import com.example.foodapp.databinding.FragmentDashboardBinding;

import java.util.Objects;

public class DashboardFragment extends Fragment {
    private Button sell;
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    String[] titleList = {"Apple", "Orange", "Pasta", "BBT"};
    ListView listView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Example button to lead to sell page
        sell = (Button) binding.SellButton;
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellActivity();
            }
        });

        listView = (ListView) binding.customListView;
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(requireActivity().getApplicationContext(), titleList);
        listView.setAdapter(customBaseAdapter);


        return root;


    }

    // Example function for opening activity
    public void openSellActivity() {
        Intent intent = new Intent(DashboardFragment.this.getActivity(), SellActivity.class);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}