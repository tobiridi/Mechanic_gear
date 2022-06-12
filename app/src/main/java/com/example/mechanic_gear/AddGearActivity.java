package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddGearActivity extends AppCompatActivity {

    private final static int ADD_GEAR_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        //get intent of previous activity
        Intent intent = getIntent();

        Intent intent1 = new Intent();
        intent1.putExtra("data_send", "en création");
        setResult(RESULT_OK, intent1);

        //close the activity
        finish();
    }
}