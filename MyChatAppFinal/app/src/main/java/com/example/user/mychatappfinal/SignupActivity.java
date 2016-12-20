package com.example.user.mychatappfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    int flag=0;
    Button registerBtn;
    EditText editTextEmail, editTextPassword;
    TextView textViewsignin;

    ///////////
    Button choseBtn;
    ImageView imageView;
    public static final int PIC_IMAGE_REQUEST=111;
    Uri uriFilepath;
    StorageReference storageReference;
    //////////

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        ///////////
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = (ImageView) findViewById(R.id.idImage);
        choseBtn = (Button) findViewById(R.id.idChoseimg);

        choseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        //////////





        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(getApplicationContext(), MainFragmentActivity.class));
        }



        progressDialog = new ProgressDialog(this);

        registerBtn = (Button) findViewById(R.id.idRegisterBtn);

        editTextEmail = (EditText) findViewById(R.id.idEmail);
        editTextPassword = (EditText) findViewById(R.id.idPassword);

        textViewsignin = (TextView) findViewById(R.id.idtxt);



        ///////////////////
        registerBtn = (Button) findViewById(R.id.idRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                registerUser();
                uploadFile();

            }
        });

        /////////////////////////////

        textViewsignin = (TextView) findViewById(R.id.idtxt);
        textViewsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //will open login activity here

                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() !=null){

            uriFilepath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriFilepath);
                imageView.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    //////////////////////////
    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select an image"), PIC_IMAGE_REQUEST);
    }

///////////////////////





    //////////////////////////////////
    private void registerUser(){
        String emailTxt = editTextEmail.getText().toString().trim();
        String passwordTxt = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(emailTxt)){
            // email is empty
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordTxt)){
            // password is empty
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            //stoppin the finction executting further
            return;
        }
        //if validation are okay
        //we will first show a paragraph

        progressDialog.setMessage("registering user");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            //user is successful registered and loged in
                            //we will start the profile activity here

                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));



                            Toast.makeText(SignupActivity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                        else {
                            Toast.makeText(SignupActivity.this, "could not registered, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();

                        }
                    }
                });

    }
///////////////////////////////////////




    /////////////////////////////////////
    private void uploadFile() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        if (uriFilepath != null) {

            File file = new File(String.valueOf(uriFilepath));

            StorageReference reference = storageReference.child("images/"+file.getName()+".jpg");

            reference.putFile(uriFilepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(((int)progress) + "% Uploaded..");
                }
            });
            flag++;

        }
        else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }
    //////////////////////////////////
}
