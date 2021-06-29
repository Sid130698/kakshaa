package com.example.tuitionbuddy01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class availableRequestsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    requestAdapter myRequestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_requests);
        intializeFields();
        FirebaseRecyclerOptions<requestModelClass> options =
                new FirebaseRecyclerOptions.Builder<requestModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("requests"), requestModelClass.class)
                        .build();
        myRequestAdapter=new requestAdapter(options);
        recyclerView.setAdapter(myRequestAdapter);
        myRequestAdapter.startListening();
    }

    private void intializeFields() {
        recyclerView=(RecyclerView)findViewById(R.id.requestsRecyclerView);
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}