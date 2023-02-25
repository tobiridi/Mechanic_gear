package be.jadoulle.mechanical_gear.AsyncTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.ArrayList;

import be.jadoulle.mechanical_gear.AddGearActivity;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;

//TODO : change to Executor
public class SignalTypeCreateAsyncTask extends AsyncTask<Void, Void, GearWithAllObjects>  {
    private AddGearActivity activity;
    private GearWithAllObjects gearWithAllObjects;
    private ArrayList<Bitmap> signalTypePictures;
    private ArrayList<String> signalTypeNames;

    public SignalTypeCreateAsyncTask(AddGearActivity activity, GearWithAllObjects gearWithAllObjects, ArrayList<Bitmap> pictures, ArrayList<String> names) {
        this.activity = activity;
        this.gearWithAllObjects = gearWithAllObjects;
        this.signalTypePictures = pictures;
        this.signalTypeNames = names;
    }

    @Override
    protected GearWithAllObjects doInBackground(Void... voids) {

        return null;
    }

    @Override
    protected void onPostExecute(GearWithAllObjects gearWithAllObjects) {
        super.onPostExecute(gearWithAllObjects);
        this.activity.confirmGearCreation(gearWithAllObjects);
    }
}
