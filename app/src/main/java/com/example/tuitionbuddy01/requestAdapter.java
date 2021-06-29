package com.example.tuitionbuddy01;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class requestAdapter extends FirebaseRecyclerAdapter<requestModelClass,requestAdapter.requestViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public requestAdapter(@NonNull FirebaseRecyclerOptions<requestModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull requestViewHolder holder, int position, @NonNull requestModelClass model) {
            if(model.getIsRejected().equals("Yes") ||model.getIsAccepted().equals("Yes"))
                holder.linearLayout.setVisibility(View.GONE);
            holder.studentName.setText(model.getStudentName());
            holder.physics.setText("physics :"+model.getPhysics());
            holder.chem.setText("chemistry :"+model.getChemistry());
            holder.maths.setText("maths :"+model.getMathematics());
            holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //model.setIsRejected("Yes");
                    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("requests").child(model.getStudentID()).child("isRejected");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue().equals("No")){
                                databaseReference.setValue("Yes");

                            }
                            Toast.makeText(holder.rejectBtn.getContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("requests").child(model.getStudentID()).child("isAccepted");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue().equals("No")){
                                databaseReference.setValue("Yes");
                                addStudentToStudents(model.getStudentID(),model.getStudentName());

                            }
                            Toast.makeText(holder.rejectBtn.getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });



    }

    private void addStudentToStudents(String studentID, String studentName) {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("students").child(studentID).setValue("");
        HashMap<String,String> StudentDetails=new HashMap<>();
        StudentDetails.put("studentName",studentName);
        databaseReference.child("students").child(studentID).setValue(StudentDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(RegisterActivity.this, "dataStored", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    @NonNull
    @Override
    public requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view,parent,false);
        return new requestViewHolder(view);
    }

    class requestViewHolder extends RecyclerView.ViewHolder{
        TextView studentName,physics,chem,maths;
        Button acceptBtn,rejectBtn;
        LinearLayout linearLayout;

        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName=(TextView)itemView.findViewById(R.id.studentNamerequestTV);
            physics=(TextView)itemView.findViewById(R.id.physicsRequestTV);
            chem=(TextView)itemView.findViewById(R.id.chemRequestTV);
            maths=(TextView)itemView.findViewById(R.id.mathRequestTV);
            acceptBtn=(Button)itemView.findViewById(R.id.acceptReqButton);
            rejectBtn=(Button)itemView.findViewById(R.id.rejectReqButton);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.requestViewLinearLayout);
            int getItemPosition=getLayoutPosition();

        }
    }
}
