package com.example.mechanic_gear;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static int MAIN_ACTIVITY_CODE = 1;
    private static Toast toast= null;

//    private ActivityResultLauncher<Intent> temp = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result != null){
//                        Toast.makeText(MainActivity.this, result.getResultCode(), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(MainActivity.this, "retour false", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

    private View.OnClickListener add_new_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddGearActivity.class);
            //start new activity with callback
            //TODO : depreciate
            startActivityForResult(intent, MAIN_ACTIVITY_CODE);

            //new method not depreciate
            //temp.launch(intent);

        }
    };

    private View.OnClickListener edit_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(MainActivity.this, "click : " + view.getClass(), Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private View.OnLongClickListener modify_gear_listener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(MainActivity.this, "long click : " + view.getClass(), Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll_all_gears = findViewById(R.id.ll_all_gears);
        View v_divider = findViewById(R.id.v_divider);

        //TODO : Async task to load all gears
        for (int i = 1; i <= 50; i++) {
            //TODO : Async task put image
            Drawable imageView = ResourcesCompat.getDrawable(getResources(), R.drawable.capteur_de_pression, null);
            //resize drawable
            //dimensions : 120 or 200
            imageView.setBounds(0,0,200,200);
            //imageView.setPadding(10,10,10,10);
            //imageView.setImageResource(R.drawable.add_new_item);
            //imageView.setLayoutParams(ll_all_gears.getLayoutParams());

            Button btn = new Button(MainActivity.this);
            //TODO : Async task put text
            btn.setText(getResources().getString(R.string.gear_denomination));
            btn.setCompoundDrawables(imageView, null, null, null);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_gradient_corner_background, null));
            btn.setOnClickListener(edit_gear_listener);
            btn.setOnLongClickListener(modify_gear_listener);

            View v_multiple_divider = new View(MainActivity.this);
            //v_multiple_divider.setBackground(v_divider.getBackground());
            v_multiple_divider.setLayoutParams(v_divider.getLayoutParams());
            v_multiple_divider.setVisibility(View.INVISIBLE);

            ll_all_gears.addView(btn);
            ll_all_gears.addView(v_multiple_divider);

        }

        //add gear
        findViewById(R.id.ibtn_add_new_item).setOnClickListener(add_new_gear_listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_ACTIVITY_CODE && resultCode == RESULT_OK && data != null){
            Toast.makeText(MainActivity.this, data.getStringExtra("data_send"), Toast.LENGTH_SHORT).show();
        }
    }
}


/**
 * event types
 * onTouchListener() -> while the finger is on the view / widget, return true if the event has been apply
 * onCheckedChangeListener() -> to check what radio button is checked
 */
