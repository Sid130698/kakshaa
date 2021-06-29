package com.example.tuitionbuddy01;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AssignmentAdapterClass extends FirebaseRecyclerAdapter<viewFileModelClass,AssignmentAdapterClass.assignmentViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AssignmentAdapterClass(@NonNull FirebaseRecyclerOptions<viewFileModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull assignmentViewHolder holder, int position, @NonNull viewFileModelClass model) {
        holder.fileNameTxtView.setText(model.getFileName());
        holder.pdfImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.pdfImage.getContext(),ViewPDF.class);
                intent.putExtra("filename",model.getFileName());
                intent.putExtra("fileURL",model.getFileURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdfImage.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public assignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assiignment_view,parent,false);
        return new assignmentViewHolder(view);
    }

    class assignmentViewHolder extends RecyclerView.ViewHolder{
        TextView fileNameTxtView;
        ImageView pdfImage;

        public assignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTxtView=(TextView)itemView.findViewById(R.id.fileNameTextView);
            pdfImage=(ImageView)itemView.findViewById(R.id.pdfImageView);
        }
    }
}
