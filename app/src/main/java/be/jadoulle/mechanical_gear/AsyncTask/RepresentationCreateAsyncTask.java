package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.Representation;

public class RepresentationCreateAsyncTask extends AsyncTask<Representation, Void, Boolean>  {
    private DetailsGearActivity activity;

    public RepresentationCreateAsyncTask(DetailsGearActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Representation... representations) {
        try{
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());
            long idNewRep = database.getRepresentationDao().create(representations[0]);
            System.out.println("newRep id : " + idNewRep);
            return idNewRep > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        this.activity.confirmRepresentationCreation(aBoolean);
    }
}
