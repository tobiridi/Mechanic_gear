package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import java.util.List;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.MainActivity;

public class GearRetrieveAllAsyncTask extends AsyncTask<Void, Void, List<GearWithAllObjects>> {
    private MainActivity activity;

    public GearRetrieveAllAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<GearWithAllObjects> doInBackground(Void... voids) {
        List<GearWithAllObjects> allGears = null;
        try {
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());
            allGears = database.getGearDao().findAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return allGears;
    }

    @Override
    protected void onPostExecute(List<GearWithAllObjects> allGears) {
        super.onPostExecute(allGears);
        System.out.println("nbr gear in database : " + allGears.size());
        this.activity.refreshAllGearList(allGears);
    }
}
