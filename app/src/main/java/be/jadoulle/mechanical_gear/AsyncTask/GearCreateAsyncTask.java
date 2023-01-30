package be.jadoulle.mechanical_gear.AsyncTask;

import android.os.AsyncTask;

import be.jadoulle.mechanical_gear.AddGearActivity;
import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.Gear;

public class GearCreateAsyncTask extends AsyncTask<String, Void, Boolean> {
    private AddGearActivity activity;

    public GearCreateAsyncTask(AddGearActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            GearDatabase database = GearDatabase.getInstance(this.activity.getApplicationContext());

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

            //check nbrWire is valid
            if(strings[4] != null && !strings[4].isEmpty()) {
                nbrWire = Byte.parseByte(strings[4]);
                if(nbrWire < 0 || nbrWire > 127) {
                    return false;
                }
            }

            Gear newGear = new Gear(0, denomination, sensorType, basicWorking, role, nbrWire, tests, category, note, composition);
            System.out.println("newGear : " + newGear);
            long idNewGear = database.getGearDao().create(newGear);
            System.out.println("newGear id : " + idNewGear);
            //if true, newGear is in the DB
            return idNewGear > 0;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        this.activity.confirmGearCreation(aBoolean);
    }
}
