package com.example.tuitionbuddy01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class assignmentViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AssignmentAdapterClass assignmentAdapterClass;
    String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view);
        intializeFields();
        FirebaseRecyclerOptions<viewFileModelClass> options =
                new FirebaseRecyclerOptions.Builder<viewFileModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Assignments").child(subjectName), viewFileModelClass.class)
                        .build();
        assignmentAdapterClass=new AssignmentAdapterClass(options);
        recyclerView.setAdapter(assignmentAdapterClass);
        assignmentAdapterClass.startListening();
    }

    private void intializeFields() {
        subjectName=getIntent().getStringExtra("subjectName");
        recyclerView=(RecyclerView)findViewById(R.id.assignmentFragRecyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}