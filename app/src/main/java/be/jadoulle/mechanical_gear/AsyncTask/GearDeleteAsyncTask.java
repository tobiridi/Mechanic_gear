package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.Gear;

public class GearDeleteAsyncTask extends AsyncTask<Gear, Void, Integer> {
    private DetailsGearActivity activity;

    public GearDeleteAsyncTask (DetailsGearActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(Gear... gears) {
        try {
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());
            return database.getGearDao().delete(gears[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        System.out.println("gear delete : " + integer);
        boolean isDelete = integer > 0;
        this.activity.confirmGearSuppression(isDelete);
    }
}
