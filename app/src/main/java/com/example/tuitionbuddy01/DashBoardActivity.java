package com.example.tuitionbuddy01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initializeFields();
        bottomNavigationView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.dashBoardFramelayout,new AnnouncementFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFragment=null;
                if(item.getItemId()==R.id.announcementsMenuItem){
                    currentFragment=new AnnouncementFragment();
                }
                else if(item.getItemId()==R.id.addpostMenuItem){
                    currentFragment=new AddPostFragment();

                }
                else if(item.getItemId()==R.id.assignmentsMenuItem){
                    currentFragment=new AssignmentFragment();

                }
                else if(item.getItemId()==R.id.chatMenuItem){
                    currentFragment=new chatFragment();
                }
                else if(item.getItemId()==R.id.accountMenuItem){
                    currentFragment= new accountFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoardFramelayout,currentFragment).commit();

                return true;
            }
        });
    }

    private void initializeFields() {
        bottomNavigationView=findViewById(R.id.bottomNavigationBar);
        ;
    }
}