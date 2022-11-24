package com.example.chatbotassignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class CourseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CourseFragment courseFragment = new CourseFragment();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Log.i("course details","bundle-check " + (bundle == null));
        courseFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.details_frame,courseFragment);
        fragmentTransaction.commit();

    }
}