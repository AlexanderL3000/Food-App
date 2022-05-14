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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        DatabaseReference newRef = Ref.push();
        newRef.setValue(item);




    }
}
