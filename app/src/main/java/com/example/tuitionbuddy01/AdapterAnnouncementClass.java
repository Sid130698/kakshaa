package com.example.tuitionbuddy01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterAnnouncementClass extends FirebaseRecyclerAdapter<ModelAnnouncementClass,AdapterAnnouncementClass.announcementViewHolder > {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterAnnouncementClass(@NonNull FirebaseRecyclerOptions<ModelAnnouncementClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull announcementViewHolder holder, int position, @NonNull ModelAnnouncementClass model) {
        holder.nameTV.setText(model.getName());
        holder.messageTV.setText(model.getMessage());
    }

    @NonNull
    @Override
    public announcementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_view,parent,false);
        return  new announcementViewHolder(view);
    }

    class announcementViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV,messageTV;
        public announcementViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=(TextView)itemView.findViewById(R.id.announcementUserNameTV);
            messageTV=(TextView)itemView.findViewById(R.id.announcementMessageTV);
        }
    }



}
