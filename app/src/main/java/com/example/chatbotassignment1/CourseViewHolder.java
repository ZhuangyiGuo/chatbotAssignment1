package com.example.chatbotassignment1;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // class to initialize the views of rows

    public TextView title;
    public ImageView coverImage;
    public TextView course_code;
    public TextView instructor;

    public CourseViewHolder(View courseView) {
        super(courseView);
        coverImage = courseView.findViewById(R.id.cover_page_imgeID);
        title = courseView.findViewById(R.id.row_course_title);
        course_code = courseView.findViewById(R.id.row_course_code);
        instructor = courseView.findViewById(R.id.row_course_instructor);
        courseView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("onclick", "onClick "
                + getLayoutPosition() + " " + title.getText());
    }
}
