package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
String joiningPosition;
Button submitBtn;
CustomLoadingScreen registerDialogBox;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
EditText usernameET,userEmailET,passwordET,confPasswordET;
String userNameS,userEmailS,passwordS,confPasswordS;
String physics="No";
String chemistry="No";
String mathematics="No";
CheckBox physicsSubCB,chemSubCB,mathsSubCB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeFields();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameS=usernameET.getText().toString();
                userEmailS=userEmailET.getText().toString();
                passwordS=passwordET.getText().toString();
                if(physicsSubCB.isChecked()){
                    physics="Yes";
                }
                if(chemSubCB.isChecked()){
                    chemistry="Yes";
                }
                if(mathsSubCB.isChecked())
                    mathematics="Yes";
                confPasswordS=confPasswordET.getText().toString();
                if(userNameS.isEmpty()){
                    usernameET.setError("name Cannot be Empty");
                    return;
                }
                if(userEmailS.isEmpty()){
                    userEmailET.setError("Email Cannot be Empty");
                    return;
                }
                if(passwordS.isEmpty()){
                    passwordET.setError("passowrd Cannot be Empty");
                    return;
                }
                if(confPasswordS.isEmpty()){
                    confPasswordET.setError("This Field is empty");
                    return;
                }
                if(!physicsSubCB.isChecked() && !chemSubCB.isChecked() && !mathsSubCB.isChecked()){
                    Toast.makeText(RegisterActivity.this, "Select a subject", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!passwordS.equals(confPasswordS)){
                    confPasswordET.setError("Password doNot match");
                    return;
                }
                else{
                    registerDialogBox.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
                    registerDialogBox.setTitle("Registering User");

                    registerDialogBox.setCanceledOnTouchOutside(true);
                    registerDialogBox.show();
                    firebaseAuth.createUserWithEmailAndPassword(userEmailS,passwordS).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendRequest();
                           // createAccount();
                            registerDialogBox.dismiss();
                            //go to dashboard

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registerDialogBox.dismiss();
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            passwordET.setText(" ");
                            confPasswordET.setText(" ");
                        }
                    });

                }
            }
        });

    }

    private void sendRequest() {
        databaseReference.child("requests").child(firebaseAuth.getCurrentUser().getUid()).setValue("");
        HashMap<String,String> StudentDetails=new HashMap<>();
        StudentDetails.put("studentName",userNameS);
        StudentDetails.put("studentID",firebaseAuth.getCurrentUser().getUid());
        StudentDetails.put("physics",physics);
        StudentDetails.put("chemistry",chemistry);
        StudentDetails.put("mathematics",mathematics);
        StudentDetails.put("isAccepted","No");
        StudentDetails.put("isRejected","No");

        databaseReference.child("requests").child(firebaseAuth.getCurrentUser().getUid()).setValue(StudentDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(RegisterActivity.this, "dataStored", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,WaitingActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount() {
        databaseReference.child("students").child(firebaseAuth.getCurrentUser().getUid()).setValue("");
        HashMap<String,String> StudentDetails=new HashMap<>();
        StudentDetails.put("studentName",userNameS);
        databaseReference.child("students").child(firebaseAuth.getCurrentUser().getUid()).setValue(StudentDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(RegisterActivity.this, "dataStored", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,DashBoardActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void initializeFields() {
//        Spinner joinAsSpinner=(Spinner)findViewById(R.id.spinnerStudTeach);
//        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.joinAs, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        joinAsSpinner.setAdapter(adapter);
//        joinAsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                joiningPosition=adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(RegisterActivity.this, joiningPosition, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//
//            }
//        });
        usernameET=findViewById(R.id.registerEnterNameEditText);
        userEmailET=findViewById(R.id.registerEmailIDEditText);
        passwordET=findViewById(R.id.registerPasswordEditText);
        confPasswordET=findViewById(R.id.registerConfPasswordEditText);
        submitBtn=findViewById(R.id.submitRegisterBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        registerDialogBox=new CustomLoadingScreen(this);
        physicsSubCB=(CheckBox)findViewById(R.id.physicsSubCheckBox);
        chemSubCB=(CheckBox)findViewById(R.id.chemistrySubCheckBox);
        mathsSubCB=(CheckBox)findViewById(R.id.mathsSubCheckBox);
    }
}