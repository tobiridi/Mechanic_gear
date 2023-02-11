package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import java.util.List;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.MainActivity;

public class GearRetrieveAsyncTask extends AsyncTask<Integer, Void, GearWithAllObjects> {
    private MainActivity activity;

    public GearRetrieveAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected GearWithAllObjects doInBackground(Integer... integers) {
        GearWithAllObjects gear = null;
        try {
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());
            gear = database.getGearDao().find(integers[0]);
            System.out.println("gear retrieve : " + gear);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return gear;
    }

    @Override
    protected void onPostExecute(GearWithAllObjects gearWithAllObjects) {
        super.onPostExecute(gearWithAllObjects);
        this.activity.addItemGearList(gearWithAllObjects);
    }
}
