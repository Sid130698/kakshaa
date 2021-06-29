 package com.example.tuitionbuddy01;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 public class AdapteChatClassForStudents extends FirebaseRecyclerAdapter<chatStudentModelClass,AdapteChatClassForStudents.studentchatMenuViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapteChatClassForStudents(@NonNull FirebaseRecyclerOptions<chatStudentModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull studentchatMenuViewHolder holder, int position, @NonNull chatStudentModelClass model) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getRef(position).getKey());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.studentName.setText(model.getStudentName());
                    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String recieversUserId=getRef(position).getKey();
                            holder.studentName.getContext().startActivity(new Intent(holder.studentName.getContext(),ChatActivity.class).putExtra("recieverUserId",recieversUserId));

                        }
                    });
                    holder.deleteButtonImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //final DatabaseReference deleteDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getRef(position).getKey());
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(holder.deleteButtonImage.getContext(), "MessageDeleted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(holder.deleteButtonImage.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else{
                    holder.linearLayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @NonNull
    @Override
    public studentchatMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_contact_view,parent,false);
        return new studentchatMenuViewHolder(view);
    }

    class studentchatMenuViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView studentPhoto;
        TextView studentName;
        ImageView deleteButtonImage;

        public studentchatMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName=(TextView)itemView.findViewById(R.id.studentNameinChatMenu);
            studentPhoto=(ImageView)itemView.findViewById(R.id.studentProfilePicChatMenu);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.studentContactViewLinearLayout);
            deleteButtonImage=(ImageView)itemView.findViewById(R.id.deleteButtonImageView);
        }
    }
}
