package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReEnterDetails extends AppCompatActivity {
    CheckBox physicsSubCB,chemSubCB,mathsSubCB;
    EditText nameEditText;
    Button submitButton;
    String physics="No";
    String chemistry="No";
    String mathematics="No";
    DatabaseReference databaseReference,putDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_enter_details);
        intializeFields();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEditText.getText().toString();

                if(physicsSubCB.isChecked()){
                    physics="Yes";
                }
                if(chemSubCB.isChecked()){
                    chemistry="Yes";
                }
                if(mathsSubCB.isChecked()){
                    mathematics="Yes";
                }
                putDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            putDataRef.child("studentName").setValue(name);
                            putDataRef.child("physics").setValue(physics);
                            putDataRef.child("chemistry").setValue(chemistry);
                            putDataRef.child("isRejected").setValue("No");
                            putDataRef.child("mathematics").setValue(mathematics);
                            Toast.makeText(ReEnterDetails.this, "Data Changed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ReEnterDetails.this,WaitingActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    private void intializeFields() {
        physicsSubCB=(CheckBox)findViewById(R.id.physicsSubCheckBoxReEnter);
        chemSubCB=(CheckBox)findViewById(R.id.chemistrySubCheckBoxReEnter);
        mathsSubCB=(CheckBox)findViewById(R.id.mathsSubCheckBoxReEnter);
        submitButton=(Button)findViewById(R.id.submitRegisterBtnReEnter);
        nameEditText=(EditText)findViewById(R.id.reEnterDetailsName);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nameEditText.setText(snapshot.child("studentName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        putDataRef=databaseReference;
    }
}