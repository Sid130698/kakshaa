package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingActivity extends AppCompatActivity {
    DatabaseReference databaseReference,teacherRef;
    TextView messageAfterRegistration;
    CustomLoadingScreen progressDialog;
    Button editDetailsAndResendRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        progressDialog= new CustomLoadingScreen(WaitingActivity.this);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        progressDialog.show();
        messageAfterRegistration=(TextView)findViewById(R.id.messageAfterRegistration);
        editDetailsAndResendRequestButton=(Button)findViewById(R.id.editDetailsandRequestButton);
        teacherRef=FirebaseDatabase.getInstance().getReference().child("Teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    finish();


                    startActivity(new Intent(WaitingActivity.this, DashBoardActivity.class));
                    finish();
                    progressDialog.dismiss();




                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if (snapshot.child("isAccepted").getValue().equals("Yes")) {
                                    finish();
                                    startActivity(new Intent(WaitingActivity.this, DashBoardActivity.class));
                                    finish();

                                            progressDialog.dismiss();



                                } else {
                                    if (snapshot.child("isRejected").getValue().equals("Yes")) {
                                        //progressDialog.dismiss();
                                        Toast.makeText(WaitingActivity.this, "yahaaaaaan", Toast.LENGTH_SHORT).show();
                                        editDetailsAndResend();
                                        progressDialog.dismiss();
                                    } else {
                                        messageAfterRegistration.setText("Teacher has Not Accepted Requests");
                                        progressDialog.dismiss();
                                    }
                                }
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
        });}


            private void editDetailsAndResend() {
                editDetailsAndResendRequestButton.setVisibility(View.VISIBLE);
                editDetailsAndResendRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(WaitingActivity.this, ReEnterDetails.class));
                    }
                });

            }


        }