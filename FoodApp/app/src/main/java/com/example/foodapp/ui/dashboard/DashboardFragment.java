package com.example.foodapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodapp.Login;
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.SellActivity;
import com.example.foodapp.SellItem;
import com.example.foodapp.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {
    private Button sell;
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    /**String[] titleList*/;
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

        String test;
        final ArrayList<String> titleList = new ArrayList<>();
        final ArrayList<String> titleList2 = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("server/saving-data/fireblog/SellItems");




        listView = (ListView) binding.customListView;
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(requireActivity().getApplicationContext(), titleList, titleList2);
        listView.setAdapter(customBaseAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SellItem item = dataSnapshot.getValue(SellItem.class);
                    if ((counter % 2) == 0) {
                        titleList.add(item.title);
                        counter = counter + 1;
                    }
                    else{
                        titleList2.add(item.title);
                        counter = counter + 1;
                    }
                }
                customBaseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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