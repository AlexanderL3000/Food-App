package com.example.foodapp;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.media.Image;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

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
import android.content.ContentResolver;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImage;

import java.net.URL;


public class SellActivity extends AppCompatActivity implements View.OnClickListener {

    private String seller;
    private Button sellBtn;
    private EditText title, price, quantity, description;
    Uri imageUri;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseRef;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    String imageUrl;

    private String hi;
    private StorageTask mUploadTask;

    ImageButton uploadPic;
    private ProgressBar mProgressBar;





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
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");



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
        imageUri = uri;
        uploadFile();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri)


                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            Toast.makeText(SellActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl =
                                            uri.toString();
                                    Upload upload = new Upload(title.getText().toString().trim(),downloadUrl);

                                    imageUrl = downloadUrl;

                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);
                                }
                            });
                            /**Upload upload = new Upload(title.getText().toString().trim(),
                                    taskSnapshot.getDownloadURL().toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);*/
                        }
                    });
        }
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




        SellItem item = new SellItem(Title, Price, Quantity, Description, seller, imageUrl);

        DatabaseReference Ref = ref.child("SellItems");
        String itemID = Ref.push().getKey();
        //Ref.child(itemID).setValue(item);
        /*DatabaseReference newRef = Ref.push();
        newRef.setValue(item);*/

        DatabaseReference check = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String item1;
        String item2;
        String item3;

        /*item1 = getDataValue(check.child("Item1"));
        item2 = getDataValue(check.child("Item2"));
        item3 = getDataValue(check.child("Item3"));

        if (!item1.equals("-1")) {
            check.child("Item1").setValue(itemID);
        }
        else if (!item2.equals("-1")) {
            check.child("Item2").setValue(itemID);
        }
        else if (!item3.equals("-1")) {
            check.child("Item3").setValue(itemID);
        }
        else {
            Toast.makeText(SellActivity.this, "Can't have more than 3 listings", Toast.LENGTH_LONG).show();
            return;
        }

        */

        check.child("Item1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String item1 = snapshot.getValue(String.class);
                if (item1.equals("-1")) {
                    check.child("Item1").setValue(itemID);
                    Ref.child(itemID).setValue(item);
                    startActivity(new Intent(SellActivity.this, MainActivity.class));
                }
                else {
                    check.child("Item2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String item2 = snapshot.getValue(String.class);
                            if (item2.equals("-1")) {
                                check.child("Item2").setValue(itemID);
                                Ref.child(itemID).setValue(item);
                                startActivity(new Intent(SellActivity.this, MainActivity.class));
                            }
                            else {
                                check.child("Item3").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        String item3 = snapshot.getValue(String.class);
                                        if (item3.equals("-1")) {
                                            check.child("Item3").setValue(itemID);
                                            Ref.child(itemID).setValue(item);
                                            startActivity(new Intent(SellActivity.this, MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(SellActivity.this, "Can't have more than 3 listings", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













    }
/*
    private String getDataValue(DatabaseReference path) {

        String Item;

        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Item = snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        return Item;
    }

 */


}
