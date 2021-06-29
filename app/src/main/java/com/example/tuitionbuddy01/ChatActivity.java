package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    RecyclerView messageRecyclerView;
    ImageView sendImageButton;
    EditText enteredMessageEditText;
    String messageString;
    String recieverUserId,currentUserId;
    DatabaseReference databaseReference,rootref;
    List<chatModelClass> messageList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterChat adapterChat;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        intializeFields();
        bringOlderMessage();
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageString=enteredMessageEditText.getText().toString();
                if(messageString.isEmpty()){
                    enteredMessageEditText.setError("No message entered");
                    return;
                }
                progressDialog.show();
                String messageSenderRef,msgRecRef;
                messageSenderRef="Messages/"+currentUserId+"/"+recieverUserId;
                msgRecRef="Messages/"+recieverUserId+"/"+currentUserId;
                DatabaseReference userMessageRef=databaseReference.child("Messages").child(currentUserId).child(recieverUserId).push();
                String stringUserMsgRef=userMessageRef.getKey();
                Map messageTextBody=new HashMap();
                messageTextBody.put("From",currentUserId);
                messageTextBody.put("To",recieverUserId);
                messageTextBody.put("Message",messageString);
                messageTextBody.put("MessageId",stringUserMsgRef);
                Map messageBodyDetails=new HashMap();
                messageBodyDetails.put(messageSenderRef+"/"+stringUserMsgRef,messageTextBody);
                messageBodyDetails.put(msgRecRef+"/"+stringUserMsgRef,messageTextBody);
                databaseReference.updateChildren(messageBodyDetails).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                       // Toast.makeText(ChatActivity.this, "SuccessFully Message Sent", Toast.LENGTH_SHORT).show();
                        enteredMessageEditText.setText("");
                        progressDialog.dismiss();
                        messageRecyclerView.smoothScrollToPosition(adapterChat.getItemCount());
                        try {
                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ChatActivity.this, "Big Failure"+e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });



            }
        });
    }

    private void bringOlderMessage() {
        rootref.child("Messages").child(currentUserId).child(recieverUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    chatModelClass objChatModelClass = snapshot.getValue(chatModelClass.class);
                    messageList.add(objChatModelClass);
                    adapterChat.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void intializeFields() {
        messageRecyclerView=(RecyclerView)findViewById(R.id.chatMessagesRecyclerView);
        sendImageButton=(ImageView)findViewById(R.id.sendMessageImageView);
        enteredMessageEditText=(EditText)findViewById(R.id.chatMessageEditText);
        currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        recieverUserId=getIntent().getExtras().getString("recieverUserId");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        adapterChat=new AdapterChat(messageList);
        rootref=FirebaseDatabase.getInstance().getReference();
        linearLayoutManager=new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(adapterChat);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Sending Message");
        progressDialog.setMessage("Please wait!!!");
    }
}