package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellActivity extends AppCompatActivity implements View.OnClickListener {

    private String seller;
    private Button sellBtn;
    private EditText title, price, quantity, description;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private DatabaseReference mDatabase;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        mAuth = FirebaseAuth.getInstance();
        sellBtn = findViewById(R.id.SellItemBtn);
        sellBtn.setOnClickListener(this);
        title = findViewById(R.id.SellTitleInput);
        price = findViewById(R.id.SellPriceInput);
        quantity = findViewById(R.id.SellQuantityInput);
        description = findViewById(R.id.SellDescriptionInput);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.SellItemBtn:
                sellItem();
        }


    }
    private void sellItem() {
        String seller = mAuth.getCurrentUser().getUid();
        String Title = title.getText().toString().trim();
        String Price = price.getText().toString().trim();
        String Quantity = quantity.getText().toString().trim();
        String Description = description.getText().toString().trim();

        SellItem item = new SellItem(Title, Price, Quantity, Description, seller);
        DatabaseReference Ref = ref.child("SellItems");
        DatabaseReference newRef = Ref.push();
        newRef.setValue(item);




    }
}
