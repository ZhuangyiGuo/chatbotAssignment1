package com.example.chatbotassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseFragment extends Fragment {

    private TextView id;
    private TextView time;
    private TextView classroom;
    private TextView credit;
    private TextView instructor;
    private TextView description;


    static final int resultCode = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment,container,false);

        boolean check = (getArguments() == null);

        id = view.findViewById(R.id.courseID);
        time = view.findViewById(R.id.coursetime);
        classroom = view.findViewById(R.id.classroom);
        credit = view.findViewById(R.id.credit);
        instructor = view.findViewById(R.id.instructor);
        description = view.findViewById(R.id.description);

        Log.i("OnCreateView","Bundle failure check= " + String.valueOf(check));
        id.append(": " + getArguments().getString(CourseDatabaseHelper.KEY_MESSAGE));
        time.append("\n\n    TBA");
        classroom.append("\n\n    TBA");
        credit.append("\n\n    0.5 credit.");
        instructor.append("\n\n    TBA");
        description.append("\n\n    This is a example course created bu us.");
        Log.i("activity check", (String) getActivity().getLocalClassName());

        return view;
    }
}
