package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AddGearActivity extends AppCompatActivity {

    private final static int ADD_GEAR_ACTIVITY_CODE = 2;

    private View.OnClickListener image_view_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(AddGearActivity.this, "click : " + view.getClass(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        //get intent of previous activity
        Intent intent = getIntent();

        Drawable imageView = ResourcesCompat.getDrawable(getResources(), R.drawable.test_image_supp2, null);

        ImageView iv_gear = findViewById(R.id.iv_gear);
        iv_gear.setOnClickListener(image_view_listener);


//        Intent intent1 = new Intent();
//        intent1.putExtra("data_send", "en création");
//        setResult(RESULT_OK, intent1);
//
//        //close the activity
//        finish();
    }
}