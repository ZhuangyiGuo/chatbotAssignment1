package com.example.chatbotassignment1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecylerviewActivity extends AppCompatActivity implements ArrayCourseAdapter.ItemClickListener {
    RecyclerView recyclerView;
    private  final int number_nubmer_of_rows = 200 ;
    int row_index = 1 ;
    public ArrayList <String> courseArrayList;
    ArrayCourseAdapter arrayCourseAdapter;
    private String temp_course;
    private final String ACTIVITY_NAME = "RecylerviewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        temp_course = "";
        courseArrayList = new ArrayList<>();
            courseArrayList.add("cp100") ;
            courseArrayList.add("cp200") ;
            courseArrayList.add("cp300") ;

        arrayCourseAdapter = new ArrayCourseAdapter(this,courseArrayList);
        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(arrayCourseAdapter);
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
                add_button_function();
                break;
            case R.id.menu_object_5:
                //Start an activity…
                remove_button_function();
                break;
            case R.id.three_dot_menu2:
                remove_all();
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
    public void add_button_function() {
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog2, null);
        customDialog.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box2);
                        String message = edit.getText().toString();
                        add_course(message);
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }

    public void remove_button_function() {
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog3, null);
        customDialog.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box3);
                        String message = edit.getText().toString();
                        int i = remove_aux(message);
                        if (i != -1) {
                            remove_course(i);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }

    private void add_course(String aCourse) {
        courseArrayList.add(0,aCourse);
        arrayCourseAdapter.notifyItemInserted(0);
    }
    private void remove_course(int index) {
        courseArrayList.remove(index);
        arrayCourseAdapter.notifyItemRemoved(index);
    }
    private void remove_all() {
        courseArrayList.clear();
        arrayCourseAdapter.notifyDataSetChanged();
    }
    private int remove_aux(String acourse) {
        int val = 0;
        while (courseArrayList.size()>val){
            if(courseArrayList.get(val).equals(acourse)) {
                return val;
            }
            val++;
        }
        return -1;
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + arrayCourseAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
