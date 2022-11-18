package com.example.chatbotassignment1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static final int LAUNCH_ACTIVITY = 10;
    protected static final String ACTIVITY_NAME = "MainActivity";
    private String snackbarMsg;
    Calendar currentDateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button chatButton = findViewById(R.id.chatButton);
        Button courseButton = findViewById(R.id.courseButton);
        Button weatherButton = findViewById(R.id.weatherButton);
        snackbarMsg = getResources().getString(R.string.snackbarMsg1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                startActivity(new Intent(MainActivity.this, ChatWindow.class));
            }
        });
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked course button");
                startActivity(new Intent(MainActivity.this,RecylerviewActivity.class));
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.fabMsg, Snackbar.LENGTH_LONG).setAction(getString(R.string.close), v1 -> {
                    Log.d("TAG", "close pressed");
                    // snackbar.dismiss();
                }).show();
            }
        });
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked weather button");
                startActivity(new Intent(MainActivity.this,WeatherForecast.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        int objectId = mi.getItemId();

        switch(objectId){
            case R.id.menu_object_2:
                DatePickerDialog.OnDateSetListener dListener =
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int yr, int mth, int dy) {
                                currentDateAndTime.set(Calendar.YEAR, yr);
                                currentDateAndTime.set(Calendar.MONTH, mth);
                                currentDateAndTime.set(Calendar.DAY_OF_MONTH, dy);
                                //setTimeAndDateText();
                            }
                        };
                new DatePickerDialog(this, dListener,
                        currentDateAndTime.get(Calendar.YEAR),
                        currentDateAndTime.get(Calendar.MONTH),
                        currentDateAndTime.get(Calendar.DAY_OF_MONTH)). show();

                break;
            case R.id.menu_object_1:
                Log.d("Toolbar",getString(R.string.option_1_message));
                Snackbar.make(findViewById(R.id.menu_object_1),snackbarMsg,Snackbar.LENGTH_LONG).show();
                break;
            case R.id.menu_object_3:
                //Start an activity…
                case3();
                break;
            case R.id.three_dot_menu:
                //Start an activity…
                Toast.makeText(MainActivity.this, R.string.setting_menu_toast, Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.return_to_main);
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
    public void case3(){
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        customDialog.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box);
                        String wrote = getResources().getString(R.string.you_wrote);
                        String message = wrote + edit.getText().toString();
                        snackbarMsg = message;
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

}