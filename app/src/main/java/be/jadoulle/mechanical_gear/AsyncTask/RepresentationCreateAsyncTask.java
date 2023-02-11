package be.jadoulle.mechanical_gear.AsyncTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;

import be.jadoulle.mechanical_gear.AddGearActivity;
import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;

public class RepresentationCreateAsyncTask extends AsyncTask<Bitmap, Void, GearWithAllObjects> {
    private AddGearActivity activity;
    private GearWithAllObjects gearWithAllObjects;

    public RepresentationCreateAsyncTask(AddGearActivity activity, GearWithAllObjects gearWithAllObjects) {
        this.activity = activity;
        this.gearWithAllObjects = gearWithAllObjects;
    }

    @Override
    protected GearWithAllObjects doInBackground(Bitmap... bitmaps) {
        try {
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());

            for (Bitmap b : bitmaps) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                int idGear = this.gearWithAllObjects.getGear().getId();

                Representation newRepresentation = new Representation(0, outputStream.toByteArray(), idGear);

                int idNewRep = (int) database.getRepresentationDao().create(newRepresentation);
                newRepresentation.setId(idNewRep);
                System.out.println("newRep id : " + idNewRep);

                this.gearWithAllObjects.addRepresentation(newRepresentation);
            }

            return this.gearWithAllObjects;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(GearWithAllObjects newGearWithAllObjects) {
        super.onPostExecute(newGearWithAllObjects);
        this.activity.confirmGearCreation(newGearWithAllObjects);
    }
}
