package com.example.foodapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodapp.SellItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.foodapp.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button logout;
    private AccountViewModel accountViewModel;
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Insta");
        DatabaseReference reference2 = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("WeChat");



        logout = binding.SignOut;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        EditText editInsta = binding.InstaInput;
        EditText editWeChat = binding.WeChatInput;
        Button saveButton = binding.AccountSaveButton;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String item = snapshot.getValue(String.class);

                editInsta.setHint(item);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String item = snapshot.getValue(String.class);

                editWeChat.setHint(item);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String InstaInput = editInsta.getText().toString().trim();
                String WeChatInput = editWeChat.getText().toString().trim();

                if (!InstaInput.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Insta").setValue(InstaInput);

                }

                if (!WeChatInput.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("WeChat").setValue(WeChatInput);
                }





            }
        });

        return root;



    }

    private void saveSocials() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}