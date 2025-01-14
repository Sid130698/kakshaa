package com.example.tuitionbuddy01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    String userID;
    boolean isTeacher;
    boolean isStudent;
    LinearLayoutManager linearLayoutManager;
    AdapterChatForTeacher adapterChatForTeacher;
    DatabaseReference databaseReference;
    AdapteChatClassForStudents adapteChatClassForStudents;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public chatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chatFragment newInstance(String param1, String param2) {
        chatFragment fragment = new chatFragment();
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
        View view=inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.chatRecyclerView);


        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth=FirebaseAuth.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();
        fillRecyclerView(userID);
        try {
            if (isStudent) {

                adapterChatForTeacher.startListening();
            } else if (isTeacher) {

                adapteChatClassForStudents.startListening();

            }
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//       try {
//           if (isTeacher)
//               adapteChatClassForStudents.stopListening();
//           else if (isStudent)
//               adapterChatForTeacher.stopListening();
//       }
//       catch (Exception e) {
//           Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//       }

    }

    private void fillRecyclerView(String userID) {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Teacher").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        isTeacher=true;
                        Toast.makeText(getActivity(), "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
                        FirebaseRecyclerOptions<chatStudentModelClass> options =
                                new FirebaseRecyclerOptions.Builder<chatStudentModelClass>()
                                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), chatStudentModelClass.class)
                                        .build();
                        adapteChatClassForStudents = new AdapteChatClassForStudents(options);
                        recyclerView.setAdapter(adapteChatClassForStudents);
                        adapteChatClassForStudents.startListening();

                    }
                    else{
                        FirebaseRecyclerOptions<modelTeacherChatMenuClass> options =
                                new FirebaseRecyclerOptions.Builder<modelTeacherChatMenuClass>()
                                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Teacher"), modelTeacherChatMenuClass.class)
                                        .build();
                        adapterChatForTeacher = new AdapterChatForTeacher(options);
                        recyclerView.setAdapter(adapterChatForTeacher);
                        adapterChatForTeacher.startListening();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void fillAsTeacher(String userID) {
//        isTeacher=true;
//
//    }
//
//    private void fillAsStudent(String userID) {
//        isStudent=true;
//    }
}