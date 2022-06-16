package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mechanic_gear.java_beans.Gear;

public class AddGearActivity extends AppCompatActivity {

    private final static int ADD_GEAR_ACTIVITY_CODE = 2;
    //avoid show many messages
    private static Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        //get intent of previous activity
        Intent intent = getIntent();

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null){
                    toast.cancel();
                }

                //data return
                Intent intent1 = new Intent();
                intent1.putExtra("data_send", "Cancel form");
                setResult(RESULT_OK, intent1);

                //close the activity
                finish();
            }
        });

        findViewById(R.id.btn_validate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null){
                    toast.cancel();
                }
                toast = Toast.makeText(AddGearActivity.this, "Validate form", Toast.LENGTH_SHORT);
                toast.show();

                //TODO : very the data input + save the form (AsyncTask maybe)

                //data return
                //Intent intent = new Intent();
                //setResult(RESULT_OK, intent);

                //close the activity
                finish();
            }
        });
    }

    private static Gear createGearOrNull(){
        //TODO
        Gear gear;
        gear = new Gear();
        return gear;
    }
}
