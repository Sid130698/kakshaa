package com.example.tuitionbuddy01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountFragment extends Fragment {
    TextView userNameTextView;
    AlertDialog.Builder alertDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser  firebaseUser;
    Button logOutButton,requestButton,DeleteAccount;
    DatabaseReference namedatabaseReference,databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static accountFragment newInstance(String param1, String param2) {
        accountFragment fragment = new accountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        userNameTextView=view.findViewById(R.id.userNameTextViewLogOut);
        findName();
        alertDialog=new AlertDialog.Builder(getContext());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        logOutButton=view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logoutUser();
            }
        });
        requestButton=(Button)view.findViewById(R.id.requestButton);
        DeleteAccount=(Button)view.findViewById(R.id.deleteAccountButton);
        DeleteAccount.setEnabled(true);
        DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAccount();

            }
        });
        isTeacher();
        return  view;
    }

    private void deleteAccount() {

        alertDialog.setTitle("Delete Account!");
        alertDialog.setMessage("Are you Sure you want to delete the account permanently? ");

        alertDialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getActivity(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        Toast.makeText(getContext(), "Account Deleted ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    private void isTeacher() {


        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Toast.makeText(getContext(), "it Exist", Toast.LENGTH_LONG).show();
                    String userId;
                    if (snapshot.child("Teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userId").getValue() != null) {
                        userId = snapshot.child("Teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userId").getValue().toString();
                        //Toast.makeText(getContext(), userId, Toast.LENGTH_LONG).show();
                        if (!userId.isEmpty()) {

                            // Toast.makeText(getContext(),isTeacherResult.toString(),Toast.LENGTH_LONG).show();
                                requestButton.setVisibility(View.VISIBLE);
                                DeleteAccount.setVisibility(View.GONE);
                            requestButton.setEnabled(true);
                            requestButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //  startActivity(new Intent(getActivity()),);
                                    startActivity(new Intent(getActivity(),availableRequestsActivity.class));

                                }
                            });
                        } else {
                            requestButton.setVisibility(View.GONE);
                            requestButton.setEnabled(false);
                        }
                        //Toast.makeText(getContext(),isTeacherResult.toString(),Toast.LENGTH_LONG).show();

                    }

                }

                else {
                    requestButton.setVisibility(View.INVISIBLE);
                    requestButton.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void findName() {
        namedatabaseReference= FirebaseDatabase.getInstance().getReference().child("students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        namedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String studentName;
                    if(snapshot.child("studentName").getValue().toString()!=null){
                        studentName=snapshot.child("studentName").getValue().toString();
                        userNameTextView.setText("Hello "+studentName);
                    }


                }
                else userNameTextView.setText("Hello Teacher");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
        getActivity().finish();

    }
}