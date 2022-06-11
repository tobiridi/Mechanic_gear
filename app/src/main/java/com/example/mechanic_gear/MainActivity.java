package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll_all_gears = findViewById(R.id.ll_all_gears);
        View v_divider = findViewById(R.id.v_divider);

        for (int i = 1; i <= 20; i++) {

            Drawable imageView = ResourcesCompat.getDrawable(getResources(), R.drawable.add_a_photo, null);
            //resize drawable
            imageView.setBounds(0,0,120,120);
            //imageView.setPadding(10,10,10,10);
            //imageView.setImageResource(R.drawable.add_new_item);
            //imageView.setLayoutParams(ll_all_gears.getLayoutParams());

            Button btn = new Button(MainActivity.this);
            btn.setText("sonde de température");
            btn.setCompoundDrawables(imageView, null, null, null);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_custom_background, null));


            View v_multiple_divider = new View(MainActivity.this);
            //v_multiple_divider.setBackground(v_divider.getBackground());
            v_multiple_divider.setLayoutParams(v_divider.getLayoutParams());
            v_multiple_divider.setVisibility(View.INVISIBLE);

            //ll_all_gears.addView(imageView);
            //ll_all_gears.addView(v_multiple_divider);

            ll_all_gears.addView(btn);
            ll_all_gears.addView(v_multiple_divider);
        }
    }
}