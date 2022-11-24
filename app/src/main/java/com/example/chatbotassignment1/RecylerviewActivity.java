package com.example.chatbotassignment1;

import static android.content.ContentValues.TAG;

import static java.sql.Types.ROWID;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
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

    private CourseDatabaseHelper courseDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    private boolean frameLoaded;
    private CourseFragment courseFragment;
    private final int LAUNCH_ACTIVITY = 10;

    @SuppressLint({"SuspiciousIndentation", "Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.frame_layout) == null) {
            frameLoaded = false;
        } else {
            frameLoaded = true;
        }

        temp_course = "";
        courseArrayList = new ArrayList<>();
        arrayCourseAdapter = new ArrayCourseAdapter(this,courseArrayList);
        arrayCourseAdapter.setClickListener(this::onItemClick);
        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(arrayCourseAdapter);

        courseDatabaseHelper = new CourseDatabaseHelper(this);
        sqLiteDatabase = courseDatabaseHelper.getWritableDatabase();
        String query = "SELECT * FROM courses";
        cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            courseArrayList.add(cursor.getString(cursor.getColumnIndex(courseDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        cursor.close();
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
                            updateDatabase();
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
        courseArrayList.add(aCourse);
        arrayCourseAdapter.notifyItemInserted(courseArrayList.size());
        sqLiteDatabase.execSQL("INSERT INTO " + CourseDatabaseHelper.TABLE_NAME + " (" + CourseDatabaseHelper.KEY_MESSAGE + ") VALUES ('" + aCourse + "')");
    }
    private void remove_course(int index) {
        courseArrayList.remove(index);
        arrayCourseAdapter.notifyItemRemoved(index);
    }
    private void remove_all() {
        courseArrayList.clear();
        arrayCourseAdapter.notifyDataSetChanged();
        sqLiteDatabase.execSQL("DELETE FROM " + CourseDatabaseHelper.TABLE_NAME);
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
    private void updateDatabase() {
        sqLiteDatabase.execSQL("DELETE FROM " + CourseDatabaseHelper.TABLE_NAME);
        //if (courseArrayList.size()>0) {
            for (int i = 0; i < courseArrayList.size(); i++) {

                sqLiteDatabase.execSQL("INSERT INTO " + CourseDatabaseHelper.TABLE_NAME + " (" + CourseDatabaseHelper.KEY_MESSAGE + ") VALUES ('" + arrayCourseAdapter.getItem(i) + "')");
            }
        //}
    }
    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + arrayCourseAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        //finish();

        Bundle bundle = new Bundle();
        bundle.putString(CourseDatabaseHelper.KEY_MESSAGE,arrayCourseAdapter.getItem(position));
        courseFragment = new CourseFragment();

        if (frameLoaded) {
            Log.i("Tablet Mode","is on");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            courseFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame_layout, courseFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.i("Tablet Mode","is off");
            Intent intent = new Intent(RecylerviewActivity.this,CourseDetails.class);
            intent.putExtra("bundle",bundle);
            startActivity(intent);
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();
    }


}
