package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import java.util.Arrays;

import be.jadoulle.mechanical_gear.AddGearActivity;
import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.Gear;

public class GearCreateAsyncTask extends AsyncTask<String, Void, Integer> {
    private AddGearActivity activity;

    public GearCreateAsyncTask(AddGearActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            //replace empty string to null
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].isEmpty())
                    strings[i] = null;
            }

            String denomination = strings[0];
            String sensorType = strings[1];
            String basicWorking = strings[2];
            String role = strings[3];
            byte nbrWire = 0;
            String tests = strings[5];
            String category = strings[6];
            String composition = strings[7];
            String note = strings[8];

            //TODO : optimise, refactor
            //check if nbrWire is valid
            if(strings[4] != null) {
                nbrWire = Byte.parseByte(strings[4]);
                if(nbrWire < 0) {
                    return -1;
                }
            }

            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());
            Gear newGear = new Gear(0, denomination, sensorType, basicWorking, role, nbrWire, tests, category, note, composition);

            return (int) database.getGearDao().create(newGear);

        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        this.activity.confirmGearCreation(integer);
    }
}
