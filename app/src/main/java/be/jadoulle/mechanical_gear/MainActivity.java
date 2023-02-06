package be.jadoulle.mechanical_gear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.jadoulle.mechanical_gear.AsyncTask.GearRetrieveAllAsyncTask;
import be.jadoulle.mechanical_gear.AsyncTask.GearRetrieveAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;
import be.jadoulle.mechanical_gear.Views.GearAdapter;

public class MainActivity extends AppCompatActivity {
    private List<GearWithAllObjects> allGears;
    private RecyclerView recyclerView;

    private View.OnClickListener add_gear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddGearActivity.class);
            //TODO : optimise
            startActivityForResult(intent, ActivityCode.MAIN_ACTIVITY_CODE);
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

        //call async task, retrieve all gears
        new GearRetrieveAllAsyncTask(this).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == ActivityCode.MAIN_ACTIVITY_CODE && resultCode == RESULT_OK) {
            //TODO : maybe change if add gear have all data at the creation,
            // so don't call db because we have the gear
            //get new gear item
            int newIdGear = data.getIntExtra("idNewGear", 0);
            GearWithAllObjects deletedGear = (GearWithAllObjects) data.getSerializableExtra("deletedGear");

            if(newIdGear > 0) {
                //call async task, retrieve the gear
                new GearRetrieveAsyncTask(this).execute(newIdGear);
            }
            else if (deletedGear != null) {
                //refresh Recycler View
                this.deleteItemGearList(deletedGear);
            }
        }
    }

    /**
     * update all items in {@link RecyclerView}
     * @param allGearWithAllObjects  new list of items
     */
    public void refreshAllGearList(List<GearWithAllObjects> allGearWithAllObjects) {
        //update data
        try {
            this.allGears.clear();
            this.allGears.addAll(allGearWithAllObjects);
            this.recyclerView.getAdapter().notifyDataSetChanged();
            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Utils.showToast(this, "list error", Toast.LENGTH_SHORT);
        }
    }

    /**
     * add a new item at the end of the list in the {@link RecyclerView}
     * @param newGearWithAllObjects  the new item
     */
    public void addItemGearList(GearWithAllObjects newGearWithAllObjects) {
        try {
            this.allGears.add(newGearWithAllObjects);
            this.recyclerView.getAdapter().notifyItemInserted(this.allGears.size() -1);
            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void updateItemGearList(GearWithAllObjects updatedGearWithAllObjects) {
        //TODO : not implemented
//        try {
//            int position = this.allGears.indexOf(updatedGearWithAllObjects);
//            this.allGears.set(position, updatedGearWithAllObjects);
//            this.recyclerView.getAdapter().notifyItemChanged(position);
//            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
//        }
//        catch (NullPointerException e) {
//            e.printStackTrace();
//        }
    }

    public void deleteItemGearList(GearWithAllObjects deletedGearWithAllObjects) {
        try {
            int position = this.allGears.indexOf(deletedGearWithAllObjects);
            this.allGears.remove(deletedGearWithAllObjects);
            this.recyclerView.getAdapter().notifyItemRemoved(position);
            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}