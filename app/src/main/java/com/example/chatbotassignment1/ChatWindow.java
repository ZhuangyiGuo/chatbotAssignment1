package com.example.chatbotassignment1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatWindow extends AppCompatActivity {

    private ListView chatList;
    private EditText chatEditText;
    private Button sendButton;
    private ArrayList<String> chatMessages;
    private ChatDatabaseHelper chatDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private final String ACTIVITY_NAME = "ChatWindow";

    ChatAdapter messageAdapter;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        chatList = findViewById(R.id.chatList);
        chatEditText = findViewById(R.id.chatInput);
        sendButton = findViewById(R.id.sendButton);
        chatMessages = new ArrayList<>();
        chatMessages.clear();
        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        chatList.setAdapter (messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chatText = chatEditText.getText().toString().trim();
                chatMessages.add(chatText);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                db.execSQL("INSERT INTO " + ChatDatabaseHelper.TABLE_NAME + " (" + ChatDatabaseHelper.KEY_MESSAGE + ") VALUES ('" + chatEditText.getText() + "')");
                chatEditText.setText("");
            }
        });

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        db = chatDatabaseHelper.getWritableDatabase();
        String str = "SELECT * FROM messages";
        cursor = db.rawQuery(str,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());
            chatMessages.add(cursor.getString(cursor.getColumnIndex(chatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Column's name: " + cursor.getColumnName(i));
        }
        cursor.close();

    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return chatMessages.size();
        }
        public String getItem(int position){
            return chatMessages.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            TextView message;
            if(position % 2 == 0) result = inflater.inflate(R.layout.chat_row_incoming,null);
            else result = inflater.inflate(R.layout.chat_row_outgoing,null);
            message = (TextView) result.findViewById(R.id.messageText);
            message.setText(getItem(position));// get the string at position
            return result;
        }
    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    public void clearChat() {
        db.execSQL("delete from "+ ChatDatabaseHelper.TABLE_NAME);
        messageAdapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar3_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        int objectId = mi.getItemId();

        switch(objectId){
            case R.id.three_dot_menu3:
                //Start an activity…
                clearChat();
                break;
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatWindow.this);
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
}