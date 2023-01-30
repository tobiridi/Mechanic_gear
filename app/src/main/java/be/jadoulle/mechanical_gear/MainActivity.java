package be.jadoulle.mechanical_gear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import be.jadoulle.mechanical_gear.Database.GearDatabase;

public class MainActivity extends AppCompatActivity {
    /*
        choisir par défaut la première image pour la représentation sur la page d'accueil
        pour chacun des items "gear"
     */

    private View.OnClickListener add_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddGearActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ibtn_add_new_item).setOnClickListener(add_gear_listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GearDatabase.getInstance(this.getApplicationContext()).close();
        System.out.println("close db");
    }
}