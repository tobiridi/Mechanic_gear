package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechanic_gear.asynchronous_task.SaveNewGearTaskAsync;
import com.example.mechanic_gear.java_beans.Gear;
import com.example.mechanic_gear.java_beans.GearCategory;

import java.util.ArrayList;

public class AddGearActivity extends AppCompatActivity {

    public final static int ADD_GEAR_ACTIVITY_CODE = 2;
    //avoid show many messages
    private static Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        //get intent of previous activity
        Intent intent = getIntent();

        TextView tv_title = findViewById(R.id.tv_title);
        //add an underline below the title
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.add_gear_title) + " :");
        spannableString.setSpan(new UnderlineSpan(),0, spannableString.length(), 0);
        tv_title.setText(spannableString);

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null){
                    toast.cancel();
                }

                //data return
                Intent intent1 = new Intent();
                intent1.putExtra("data_send", "Cancel form");
                setResult(RESULT_CANCELED, intent1);

                //close the activity
                finish();
            }
        });

        findViewById(R.id.btn_validate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //async task
                Gear g = new Gear("denomination","base64",
                        "capteur","role1",(byte)3,"multimètre",
                        GearCategory.ACTIONNEUR,"composer de fer");
                new SaveNewGearTaskAsync(AddGearActivity.this).execute(g);

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
