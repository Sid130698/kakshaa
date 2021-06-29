package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AnnouncementActivity extends AppCompatActivity {
EditText announcementTextEditText;
String announcementString;
CustomLoadingScreen progressDialog;
FirebaseAuth firebaseAuth;
String returnUserName=null;
DatabaseReference databaseReference;
String currentUserId,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        IntializeFields();
        getName();

    }

    private void getName() {

        progressDialog= new CustomLoadingScreen(AnnouncementActivity.this);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        progressDialog.show();

        databaseReference.child("students").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                   username = snapshot.child("studentName").getValue().toString();
                    progressDialog.dismiss();
                }

                else{
                   username="Teacher";
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

    }

    private void IntializeFields() {
        announcementTextEditText=(EditText)findViewById(R.id.announcementEditText);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference();

    }

    public void postThisAnnouncement(View view) {

        try{

            announcementString=announcementTextEditText.getText().toString();
            if(announcementString.isEmpty()){
                announcementTextEditText.setError("This Field cannpot be Empty");
                return;
            }
            else{

                // databaseReference.child("Announcements").child(currentUserId).setValue("");

                if(username==null)
                {

                    AlertDialog.Builder alertDialog;
                    alertDialog=new AlertDialog.Builder(AnnouncementActivity.this);
                    alertDialog.setTitle("Not Registered!");
                    alertDialog.setMessage("Connection ERROR ");
                    alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getName();

                        }
                    });
                    alertDialog.show();

                }
                else{ progressDialog= new CustomLoadingScreen(AnnouncementActivity.this);

                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
                    progressDialog.show();
                    HashMap<String,String> announcementHashMap= new HashMap<>();
                    announcementHashMap.put("Message",announcementString);
                    announcementHashMap.put("Name",username);
                    databaseReference.child("Announcements").child(databaseReference.child("Announcements").push().getKey()).setValue(announcementHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AnnouncementActivity.this, "announcement success!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(AnnouncementActivity.this,DashBoardActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AnnouncementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                }

            }
        } catch (Exception e) {
            Toast.makeText(this,"Connection Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AnnouncementActivity.this,DashBoardActivity.class));
        }


    }



}