package com.example.foodapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.canhub.cropper.CropImageActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImage;



public class SellActivity extends AppCompatActivity implements View.OnClickListener {

    private String seller;
    private Button sellBtn;
    private EditText title, price, quantity, description;
    Uri URI;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private DatabaseReference mDatabase;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    ImageButton uploadPic;




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


        uploadPic = findViewById(R.id.picUpload);


        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(SellActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .crop(150f, 161f)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        uploadPic.setImageURI(uri);
        URI = uri;
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
        String uri = URI.toString();


        SellItem item = new SellItem(Title, Price, Quantity, Description, seller, uri);
        DatabaseReference Ref = ref.child("SellItems");
        DatabaseReference newRef = Ref.push();
        newRef.setValue(item);




    }
}
