package com.example.tuitionbuddy01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {
    TextView physicsTV,chemistryTV,mathsTV;
    DatabaseReference databaseReference;
    Boolean isPhysics=true,isChem=true,isMaths=true;
    AlertDialog.Builder alertDialog;
    LinearLayout physicsLinearLayout,chemistryLinearLayout,mathsLinearLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
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
        View view= inflater.inflate(R.layout.fragment_assignment, container, false);

        physicsTV=(TextView)view.findViewById(R.id.physicsSubjectTextViewForView);
        chemistryTV=(TextView)view.findViewById(R.id.chemistrySubjectTextViewForView);
        mathsTV=(TextView)view.findViewById(R.id.mathsSubjectTextViewForView);
        physicsLinearLayout=(LinearLayout)view.findViewById(R.id.physicsLinearLayout);
        chemistryLinearLayout=(LinearLayout)view.findViewById(R.id.chemistryLinearLayout);
        mathsLinearLayout=(LinearLayout)view.findViewById(R.id.mathsLinearLayout);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("physics").getValue().equals("No")){
                        isPhysics=false;

                    } if(snapshot.child("chemistry").getValue().equals("No")){
                        isChem=false;

                    } if(snapshot.child("mathematics").getValue().equals("No")){
                        isMaths=false;


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        physicsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPhysics)
                startActivity(new Intent(getActivity(),assignmentViewActivity.class).putExtra("subjectName","physics"));
                else
                   showAlertMessage();

            }
        });
        chemistryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChem)
                startActivity(new Intent(getActivity(),assignmentViewActivity.class).putExtra("subjectName","chemistry"));
                else
                    //Activity(), "You are not enrolled for this Subject", Toast.LENGTH_SHORT).show();
                showAlertMessage();
            }
        });
        mathsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMaths)
                startActivity(new Intent(getActivity(),assignmentViewActivity.class).putExtra("subjectName","mathematics"));
                else
                    //Toast.makeText(getActivity(), "You are not enrolled for this Subject", Toast.LENGTH_SHORT).show();
                showAlertMessage();
            }
        });









        return view;
    }

    private void showAlertMessage() {
        alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Not Registered!");
        alertDialog.setMessage("You are not enrolled for this subject ");
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        //assignmentAdapterClass.stopListening();
    }
}