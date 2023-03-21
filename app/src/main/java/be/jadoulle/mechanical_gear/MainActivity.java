package be.jadoulle.mechanical_gear;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.jadoulle.mechanical_gear.AsyncTask.GearAsyncTask;
import be.jadoulle.mechanical_gear.AsyncTask.RepresentationAsyncTask;
import be.jadoulle.mechanical_gear.AsyncTask.SignalTypeAsyncTask;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Utils.Utils;
import be.jadoulle.mechanical_gear.Views.GearAdapter;
import be.jadoulle.mechanical_gear.Views.GearViewHolder;

public class MainActivity extends AppCompatActivity {
    private List<GearWithAllObjects> allGears;
    private RecyclerView recyclerView;
    private Thread progressBarThread;

    /*
        "ActivityResultLauncher", used for launch an other activity, specify an input type
        "registerForActivityResult", used for register the data receive from an other activity
        "ActivityResultContract", define needed input type to produce an output type
        "ActivityResultCallback", implement activityResult() method, used to get the output type needed for the "ActivityResultContract"
    */
    private ActivityResultLauncher<Intent> recyclerViewNewGearLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (data != null && result.getResultCode() == RESULT_OK) {
                        GearWithAllObjects newGear = (GearWithAllObjects) data.getSerializableExtra("newGear");
                        if (newGear != null) {
                            //refresh Recycler View
                            addItemGearList(newGear);
                        }
                    }
                }
            });

    private ActivityResultLauncher<Intent> recyclerViewLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                Intent data = result.getData();
                if (data != null && result.getResultCode() == RESULT_OK) {
                    GearWithAllObjects deletedGear = (GearWithAllObjects) data.getSerializableExtra("deletedGear");
                    GearWithAllObjects updatedGear = (GearWithAllObjects) data.getSerializableExtra("updatedGear");
                    //refresh Recycler View
                    if (deletedGear != null) {
                        deleteItemGearList(deletedGear);
                    } else if(updatedGear != null) {
                        updateItemGearList(updatedGear);
                    }
                }
            });

    private View.OnClickListener addGearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(MainActivity.this, AddGearActivity.class);
                recyclerViewNewGearLauncher.launch(intent);
            }
            catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    //ACCESSOR
    public ActivityResultLauncher<Intent> getRecyclerViewLauncher() {
        return recyclerViewLauncher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ibtn_add_new_item).setOnClickListener(addGearListener);

        //configure recyclerView
        this.allGears = new ArrayList<>();
        this.recyclerView = (RecyclerView) findViewById(R.id.rv_all_gears);
        this.recyclerView.setAdapter(new GearAdapter(this.allGears));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        //call async task, retrieve all gears
        new GearAsyncTask(this).getAllGears();
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
        }
    }

    /**
     * add a new item at the end of the list in the {@link RecyclerView}
     * @param newGearWithAllObjects  the new item
     */
    public void addItemGearList(GearWithAllObjects newGearWithAllObjects) {
        try {
            this.allGears.add(newGearWithAllObjects);
            this.recyclerView.getAdapter().notifyItemInserted(this.allGears.size() - 1);

            //wait view holder creation then save representation and signalType in DB + update progress bar
            //the view holder is created from "main" thread
            this.progressBarThread = new Thread(this::waitViewHolderCreation, "progressBarThread");
            this.progressBarThread.start();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void updateItemGearList(GearWithAllObjects updatedGearWithAllObjects) {
        try {
            int position = this.allGears.indexOf(updatedGearWithAllObjects);
            this.allGears.set(position, updatedGearWithAllObjects);
            this.recyclerView.getAdapter().notifyItemChanged(position);
            Utils.showToast(this, this.getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    private synchronized void waitViewHolderCreation() {
        GearWithAllObjects newGearWithAllObjects = this.allGears.get(this.allGears.size() - 1);

        try {
            while (this.recyclerView.getChildCount() < this.allGears.size()) {
                wait(50L);
            }

            //call async task, create representation
            new RepresentationAsyncTask(this, newGearWithAllObjects.getRepresentations())
                    .createRepresentations(newGearWithAllObjects.getGear());

            //call async task, create signalType
            new SignalTypeAsyncTask(this, newGearWithAllObjects.getSignalTypes())
                    .createSignalTypes(newGearWithAllObjects.getGear());

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBarOnView() {
        //main thread update progress bar for the new item in recycler view
        this.runOnUiThread(() -> {
            int index = this.recyclerView.getChildCount() - 1;
            GearViewHolder gearViewHolder = (GearViewHolder) this.recyclerView.getChildViewHolder(this.recyclerView.getChildAt(index));
            gearViewHolder.updateProgressBar();
        });
    }

}