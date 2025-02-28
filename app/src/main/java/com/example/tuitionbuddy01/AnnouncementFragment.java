package com.example.tuitionbuddy01;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterAnnouncementClass adapterAnnouncementClass;
    ProgressDialog announcementDialogBox;
    LinearLayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementFragment newInstance(String param1, String param2) {
        AnnouncementFragment fragment = new AnnouncementFragment();
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
        View view=inflater.inflate(R.layout.fragment_announcement, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.announcementsFragRecyclerView);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        announcementDialogBox=new ProgressDialog(this.getContext());
        announcementDialogBox.setTitle("Fetching Announcemt");
        announcementDialogBox.setMessage("Please wait while we fetch the latest announcement");
        announcementDialogBox.show();
        FirebaseRecyclerOptions<ModelAnnouncementClass> options =
                new FirebaseRecyclerOptions.Builder<ModelAnnouncementClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Announcements"), ModelAnnouncementClass.class)
                        .build();
        adapterAnnouncementClass=new AdapterAnnouncementClass(options);
        recyclerView.setAdapter(adapterAnnouncementClass);

        adapterAnnouncementClass.startListening();
        announcementDialogBox.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterAnnouncementClass.stopListening();
    }
}