package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
EditText emailET,passowrdET;
String emailS,passWordS;
CustomLoadingScreen progressDialog;

DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intializeFields();
    }

    private void intializeFields() {
        emailET=(EditText)findViewById(R.id.loginEmailEditText);
        passowrdET=(EditText)findViewById(R.id.PasswordEditText);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressDialog=new CustomLoadingScreen(this);
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        progressDialog.setTitle("Logging In ");

        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void loginUser(View view) {
        emailS=emailET.getText().toString();
        passWordS=passowrdET.getText().toString();
        if(emailS.isEmpty()){
            emailET.setError("Email cannot be empty");
            return;
        }
         if(passWordS.isEmpty()){
            passowrdET.setError("please enter password");
            return;
        }
         progressDialog.show();

         firebaseAuth.signInWithEmailAndPassword(emailS,passWordS).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
             @Override
             public void onSuccess(AuthResult authResult) {


                 Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                     startActivity(new Intent(LoginActivity.this, WaitingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
                     finish();


             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

                 Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
             }
         });

    }

    
}