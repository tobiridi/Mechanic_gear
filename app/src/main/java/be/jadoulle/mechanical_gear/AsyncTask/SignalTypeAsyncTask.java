package be.jadoulle.mechanical_gear.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;
import be.jadoulle.mechanical_gear.MainActivity;

public class SignalTypeAsyncTask {
    private ExecutorService executorService;
    private AppCompatActivity activity;
    private List<SignalType> signalTypes;

    /**
     * Create a async task with a list of signalTypes, used with {@link SignalTypeAsyncTask#createSignalTypes(Gear)} method,
     * this list is will be saved in Database using multi Threads.
     * @param activity {@link AppCompatActivity}
     * @param signalTypes The gear's signal types
     */
    public SignalTypeAsyncTask(AppCompatActivity activity, List<SignalType> signalTypes) {
        this.signalTypes = signalTypes;
        this.activity = activity;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    /**
     * Create a async task without list of signalTypes, use a single Thread for tasks,
     * can throw {@link NullPointerException} if used with {@link SignalTypeAsyncTask#createSignalTypes(Gear)} method.
     * @param activity {@link AppCompatActivity}
     */
    public SignalTypeAsyncTask(AppCompatActivity activity) {
        this.activity = activity;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public SignalType createSignalTypes(final Gear gear) throws NullPointerException {
        SignalType newSignal = null;

        if(this.signalTypes == null){
            throw new NullPointerException("Can not save signalTypes because signalTypes is Null");
        }
        else {
            //execute tasks on multi Threads
            for (final SignalType signalType : this.signalTypes) {
                //task
                Callable<SignalType> callable = () -> {
                    signalType.setGearId(gear.getId());
                    GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
                    int idNewSignal = (int) database.getSignalTypeDao().create(signalType);

                    signalType.setId(idNewSignal);
                    return signalType;
                };

                try {
                    //execute task
                    Future<SignalType> future = this.executorService.submit(callable);
                    //get data from callable
                    newSignal = future.get();

                    System.out.println("new signalType saved : " + newSignal);

                    //update MainActivity
                    if(activity instanceof MainActivity) {
                        //TODO : update progress bar
                    }
                }
                catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //close all Threads
            this.executorService.shutdown();
        }

        return  newSignal;
    }
}
