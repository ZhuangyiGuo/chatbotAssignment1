package com.example.chatbotassignment1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecylerviewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CourseDatabaseHelper courseDatabaseHelper;
    private ArrayList<String> courseList;
    private SQLiteDatabase db;
    private Cursor cursor;
    private final String ACTIVITY_NAME = "RecylerviewActivity";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        courseList = new ArrayList<>();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        courseDatabaseHelper = new CourseDatabaseHelper(this);
        db = courseDatabaseHelper.getWritableDatabase();
        String str = "SELECT * FROM courses";
        cursor = db.rawQuery(str,null);
        cursor.moveToFirst();

        int i = 0;
        while (!cursor.isAfterLast() ) {

        }
        cursor.close();

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(arrayCourseAdapter);
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar2_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        int objectId = mi.getItemId();

        switch(objectId){
            case R.id.menu_object_4:
                Log.d("Toolbar",getString(R.string.option_1_message));
                addCourcses();
                break;
            case R.id.menu_object_5:
                Log.d("Toolbar",getString(R.string.option_1_message));
                editCourses();
                break;
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(RecylerviewActivity.this);
                builder.setTitle(R.string.return_to_home);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                //Start an activity…
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }



    public void editCourses(){
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog2, null);
        customDialog.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText edit = view.findViewById(R.id.dialog_message_box2);
                String message = edit.getText().toString();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    }
    public void addCourcses(){
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog3, null);
        customDialog.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    }

    private class CourseAdapter extends ArrayAdapter<String>{
        public CourseAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return courseList.size();
        }
        public String getItem(int position){
            return courseList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = RecylerviewActivity.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.course_row,null);
            TextView message = (TextView) result.findViewById(R.id.messageText);
            message.setText(getItem(position));// get the string at position
            return result;
        }
    }
}