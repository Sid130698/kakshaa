package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class addAssignment extends AppCompatActivity {
Button addAssignmentBtn,uploadButton;
EditText assignmentnameEditText;
Uri filePath;
String subjectName;
StorageReference storageReference;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        initializeFields();
        addAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent=new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Assignment"),101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                    }
                }).check();

            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = assignmentnameEditText.getText().toString();
                if(filename.isEmpty()){
                    assignmentnameEditText.setError("Please Enter A File Name");
                    return;
                }
                processUpload(filePath,filename);
            }
        });
    }

    private void processUpload(Uri filePath, String filename) {

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading File");
        progressDialog.setMessage("Please wait ! while file is Being uplaoded");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        StorageReference reference=storageReference.child("uploadedAssignments/"+filename+System.currentTimeMillis()+".pdf");
        reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        fileInfoModelClass infoModelClass=new fileInfoModelClass(filename,uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(infoModelClass);
                        assignmentnameEditText.setVisibility(View.INVISIBLE);
                        uploadButton.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(addAssignment.this, "File Uplaoded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent =(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)percent+"%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void initializeFields() {
        addAssignmentBtn=(Button)findViewById(R.id.addAssignmentButton);
        uploadButton=(Button)findViewById(R.id.uploadAssignmentButton);
        assignmentnameEditText=(EditText)findViewById(R.id.fileNameEditText);
        storageReference= FirebaseStorage.getInstance().getReference();
        subjectName=getIntent().getStringExtra("subjectName");
        if(!subjectName.isEmpty())
        databaseReference= FirebaseDatabase.getInstance().getReference("Assignments").child(subjectName);
        else
            subjectName=getIntent().getStringExtra("subjectName");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101&&resultCode==RESULT_OK){
            filePath=data.getData();
            assignmentnameEditText.setVisibility(View.VISIBLE);
            uploadButton.setVisibility(View.VISIBLE);
            uploadButton.setEnabled(true);
        }
    }
}