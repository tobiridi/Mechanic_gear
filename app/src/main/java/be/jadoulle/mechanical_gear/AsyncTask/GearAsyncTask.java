package be.jadoulle.mechanical_gear.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import be.jadoulle.mechanical_gear.AddGearActivity;
import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.MainActivity;

public class GearAsyncTask {
    private ExecutorService executorService;
    private AppCompatActivity activity;

    public GearAsyncTask(AppCompatActivity activity) {
        this.activity = activity;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public List<GearWithAllObjects> getAllGears() {
        /*
         * use "Callable" if return data, else use "Runnable"
         */
        List<GearWithAllObjects> allGears = null;
        //task in background
        Callable<List<GearWithAllObjects>> callable = new Callable<List<GearWithAllObjects>>() {
            @Override
            public List<GearWithAllObjects> call() throws Exception {
                GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
                return database.getGearDao().findAll();
            }
        };

        try {
            //execute task
            Future<List<GearWithAllObjects>> future = this.executorService.submit(callable);
            //get data from callable, block the process until the end of the task
            allGears = future.get();

            //update MainActivity
            if(activity instanceof MainActivity) {
                ((MainActivity) activity).refreshAllGearList(allGears);
            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //close the ended previous task
        this.executorService.shutdown();

        return allGears;
    }

    /**
     * The param order is : denomination, sensorType, basicWorking, role, nbrWire,
     * tests, category, note, composition
     * @param strings Fields of the gear
     * @return The new {@link Gear} object
     */
    public Gear createGear(final String...strings) {
        Gear newGear = null;

        //task
        Callable<Gear> callable = new Callable<Gear>() {
            @Override
            public Gear call() throws Exception {
                //replace empty string to null
                for (int i = 0; i < strings.length; i++) {
                    if (strings[i].isEmpty())
                        strings[i] = null;
                }

                String denomination = strings[0];
                String sensorType = strings[1];
                String basicWorking = strings[2];
                String role = strings[3];
                byte nbrWire = strings[4] != null ? Byte.parseByte(strings[4]) : 0;
                String tests = strings[5];
                String category = strings[6];
                String note = strings[7];
                String composition = strings[8];

                Gear dbNewGear = new Gear(0, denomination, sensorType, basicWorking, role, nbrWire, tests, category, note, composition);

                GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
                int idNewGear = (int) database.getGearDao().create(dbNewGear);

                dbNewGear.setId(idNewGear);
                return dbNewGear;
            }
        };

        try {
            //execute task
            Future<Gear> future = this.executorService.submit(callable);
            //get data from callable
            newGear = future.get();

            System.out.println("new gear : " + newGear);

            //update AddGearActivity
            if(activity instanceof AddGearActivity) {
                ((AddGearActivity) activity).confirmGearCreation(newGear);
            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //close the ended previous task
        this.executorService.shutdown();

        return newGear;
    }

    public int deleteGear(final Gear gear) {
        int deleteItem = 0;

        //task
        Callable<Integer> callable = () -> {
            GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
            return database.getGearDao().delete(gear);
        };

        try {
            //execute task
            Future<Integer> future = this.executorService.submit(callable);
            //get data from callable
            deleteItem = future.get();

            //update DetailsGearActivity
            if(activity instanceof DetailsGearActivity) {
                boolean isDelete = deleteItem == 1;
                ((DetailsGearActivity) activity).confirmGearSuppression(isDelete);
            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //close the ended previous task
        this.executorService.shutdown();

        return deleteItem;
    }

    //TODO : not tested
    public GearWithAllObjects getGear(final int idGear) {
        GearWithAllObjects gearFind = null;

        //task
        Callable<GearWithAllObjects> callable = () -> {
            GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
            return database.getGearDao().find(idGear);
        };

        try {
            //execute task
            Future<GearWithAllObjects> future = this.executorService.submit(callable);
            //get data from callable
            gearFind = future.get();

        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //close the ended previous task
        this.executorService.shutdown();

        return gearFind;
    }
}
