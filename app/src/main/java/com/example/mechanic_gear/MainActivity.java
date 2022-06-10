package com.example.mechanic_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll_all_gears = findViewById(R.id.ll_all_gears);

        for (int i = 1; i <= 20; i++) {
            TextView textView = new TextView(MainActivity.this);
            View v_divider = new View(MainActivity.this);
            v_divider.setMinimumWidth(ll_all_gears.getWidth() - 10);
            v_divider.setMinimumHeight(2);
            v_divider.setBackgroundColor(getResources().getColor(R.color.black));

            textView.setText("je suis le texte " + i);
            textView.setTextSize(30);
            textView.setPadding(10,10,10,20);
            //textView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

            ll_all_gears.addView(textView);
            ll_all_gears.addView(v_divider);

        }

    }
}