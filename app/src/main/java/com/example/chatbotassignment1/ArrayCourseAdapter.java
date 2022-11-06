package com.example.chatbotassignment1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Abdul Yunis on 2020-02-25.
 * Adapter class needs getItemCount, onCreateViewHolder,
 * and onBindViewHolder methods
 */

public class ArrayCourseAdapter extends RecyclerView.Adapter <CourseViewHolder> {

    private int course_row_layout;
    private ArrayList <Course> courseList;
    private Context cxt;

    // Constructor of the class
    public ArrayCourseAdapter(int book_row_layout_as_id,
                            ArrayList <Course> bookList, Context context) {
        course_row_layout = book_row_layout_as_id;
        this.courseList = bookList;
        this.cxt = context;
    }

    // return the size of the list
    @Override
    public int getItemCount() {
        return courseList == null ? 0 : courseList.size();
    }

    //   turning the layout for each row in the list to View object
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   cxt = parent.getContext() ;
        View myCourseview = LayoutInflater.from(parent.getContext()).
                inflate(course_row_layout, parent, false);

        // create GUI object equivalent to the Book object
        CourseViewHolder myViewHolder = new CourseViewHolder(myCourseview);
        return myViewHolder;
    }

    // load data to each row in the list
    // for simplicity we used one image for all book cover images.
    // in real application, image id will be used to retrieve
    // individual book cover im-age

    @Override
    public void onBindViewHolder(final CourseViewHolder holder, final int listPosition) {
        TextView aCourse = holder.title;
        TextView code = holder.course_code;
        TextView author = holder.instructor;

        // showing book on screen

        author.setText(courseList.get(listPosition).getInstructor());
        aCourse.setText(courseList.get(listPosition).getTitle());
        code.setText(courseList.get(listPosition).getCourse_code());

        // icon is initiazlied  here not when ArrayBook initialized.
        // this is demonstrates the Book Object and GUI book object can be different.
        // presenting Book as TextView on the Android device Screen is very much
        // like creating toString() method for printing,
        // you can add or remove proporties based on the need.
        ImageView coverImage = holder.coverImage;

        Bitmap icon = BitmapFactory.decodeResource(cxt.getResources(),
                R.drawable.instagram);
        coverImage.setImageBitmap(icon);
    }

    public Course getItem(String course_code) {
        for (int i = 0; i < courseList.size(); i++){
            if (courseList.get(i).getCourse_code().equals(course_code)){
                return courseList.get(i);
            }
        }
        return null;
    }
    public Course getItemByIndex(int index) {
        return courseList.get(index);
    }
    public void removeItem(String course_code) {
        for (int i = 0; i < courseList.size(); i++){
            if (courseList.get(i).getCourse_code().equals(course_code)){
                courseList.remove(i);
            }
        }
    }
    public void addItem(Course course) {
        courseList.add(course);
    }
}