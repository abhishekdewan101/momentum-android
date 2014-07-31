package com.example.momentum;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.momentum.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Daily extends Activity {

    EditText goal;
    EditText name;
    Button saveButton;

    private static final String PREF_NAME = "momentum_file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        goal = (EditText) findViewById(R.id.goal);
        saveButton = (Button) findViewById(R.id.save);
        name = (EditText) findViewById(R.id.username);

        SharedPreferences settings = getSharedPreferences(PREF_NAME,0);
        String userName = settings.getString("name",null);
        if(userName  != null){
            name.setText(userName);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(PREF_NAME,0);
                Log.e("UsernAme",name.getText().toString());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("goal",goal.getText().toString());
                editor.putString("name",name.getText().toString());
                editor.commit();
                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                Toast.makeText(getApplicationContext(),"The status will be updated shortly",Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
