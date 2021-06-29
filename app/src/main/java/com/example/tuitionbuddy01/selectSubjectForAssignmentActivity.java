package com.example.tuitionbuddy01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class selectSubjectForAssignmentActivity extends AppCompatActivity {
TextView physicsTV,chemistryTV,MathsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject_for_assignment);
        initializefields();
        physicsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectSubjectForAssignmentActivity.this,addAssignment.class).putExtra("subjectName","physics"));

            }
        });
        chemistryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectSubjectForAssignmentActivity.this,addAssignment.class).putExtra("subjectName","chemistry"));
            }
        });
        MathsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectSubjectForAssignmentActivity.this,addAssignment.class).putExtra("subjectName","mathematics"));
            }
        });
        
    }

    private void initializefields() {
        physicsTV=(TextView)findViewById(R.id.physicsSubjectTextView);
        chemistryTV=(TextView)findViewById(R.id.chemistrySubjectTextView);
        MathsTV=(TextView)findViewById(R.id.mathsSubjectTextView);
    }

}