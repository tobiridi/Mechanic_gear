package be.jadoulle.mechanical_gear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.jadoulle.mechanical_gear.AsyncTask.GearRetrieveAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Utils.Utils;
import be.jadoulle.mechanical_gear.Views.GearAdapter;

public class MainActivity extends AppCompatActivity {
    private List<GearWithAllObjects> allGears;
    private RecyclerView recyclerView;

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

        //configure recyclerView
        this.allGears = new ArrayList<>();
        this.recyclerView = (RecyclerView) findViewById(R.id.rv_all_gears);
        this.recyclerView.setAdapter(new GearAdapter(this.allGears));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

    }

    @Override
    protected void onResume() {
        super.onResume();

        //call async task, retrieve all gears
        new GearRetrieveAsyncTask(this).execute();
    }

    public void refreshGearList(List<GearWithAllObjects> allGears) {
        //TODO : optimise, refresh only if the list change
        // update gear, delete gear, add gear
        try {
            if(!this.allGears.isEmpty()) {
                this.allGears.clear();
            }
            this.allGears.addAll(allGears);
            this.recyclerView.getAdapter().notifyDataSetChanged();
            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Utils.showToast(this, "list update failed", Toast.LENGTH_SHORT);
        }

    }
}