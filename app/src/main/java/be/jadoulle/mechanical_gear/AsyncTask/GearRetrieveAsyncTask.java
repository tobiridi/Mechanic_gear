package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import java.util.List;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.MainActivity;

public class GearRetrieveAsyncTask extends AsyncTask<Void, Void, List<GearWithAllObjects>> {
    private MainActivity activity;

    public GearRetrieveAsyncTask(MainActivity activity) {
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
        System.out.println("nombre d objet gear : " + allGears.size());
        for (GearWithAllObjects gear : allGears) {
            System.out.println("gear entier : " + gear);
        }
        this.activity.refreshGearList(allGears);
    }
}
