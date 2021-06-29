package com.example.tuitionbuddy01;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterChatForTeacher extends FirebaseRecyclerAdapter<modelTeacherChatMenuClass,AdapterChatForTeacher.teacherViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterChatForTeacher(@NonNull FirebaseRecyclerOptions<modelTeacherChatMenuClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull teacherViewHolder holder, int position, @NonNull modelTeacherChatMenuClass model) {
        holder.teacherNameTextView.setText(model.getTeacherName());
        holder.teacherLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recieversUserId=getRef(position).getKey();
                holder.teacherNameTextView.getContext().startActivity(new Intent(holder.teacherNameTextView.getContext(),ChatActivity.class).putExtra("recieverUserId",recieversUserId));
            }
        });


    }

    @NonNull
    @Override
    public teacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teachercontact_view,parent,false);
        return new teacherViewHolder(view);
    }

    class teacherViewHolder extends RecyclerView.ViewHolder{
        ImageView teacherImageView;
        TextView teacherNameTextView;
        LinearLayout teacherLinearLayout;

        public teacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherLinearLayout=(LinearLayout)itemView.findViewById(R.id.teacherContactViewLinearLayout);
            teacherImageView=(ImageView) itemView.findViewById(R.id.teacherProfilePicChatMenu);
            teacherNameTextView=(TextView) itemView.findViewById(R.id.teacherNameinChatMenu);
        }
    }
}
