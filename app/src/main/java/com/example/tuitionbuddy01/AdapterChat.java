package com.example.tuitionbuddy01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.chatViewHolder> {
    List<chatModelClass> messageList;

    public AdapterChat(List<chatModelClass> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_message_layout,parent,false);
        return new chatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        String messageSenderID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        chatModelClass varChatModelClass=messageList.get(position);
        String fromUserId=varChatModelClass.getFrom();
        if(fromUserId.equals(messageSenderID)){
            holder.sender.setVisibility(View.VISIBLE);
            holder.reciever.setVisibility(View.GONE);
            holder.sender.setBackgroundResource(R.drawable.sender_message_background);
            holder.sender.setText(varChatModelClass.getMessage());
        }
        else{
            holder.sender.setVisibility(View.GONE);
            holder.reciever.setVisibility(View.VISIBLE);
            holder.reciever.setBackgroundResource(R.drawable.reciever_message_background);
            holder.reciever.setText(varChatModelClass.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    class chatViewHolder extends RecyclerView.ViewHolder{
        TextView sender,reciever;
        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            sender=itemView.findViewById(R.id.senderMessageTextView);
            reciever=itemView.findViewById(R.id.recieverMessageTextView);
        }
    }

}
