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
import be.jadoulle.mechanical_gear.MainActivity;

public class RepresentationAsyncTask {
    private ExecutorService executorService;
    private AppCompatActivity activity;
    private List<Representation> representations;

    /**
     * Create a async task with a list of representations, used with {@link RepresentationAsyncTask#createRepresentations(Gear)} method,
     * this list is will be saved in Database using multi Threads.
     * @param activity {@link AppCompatActivity}
     * @param representations The gear's representations
     */
    public RepresentationAsyncTask(AppCompatActivity activity, List<Representation> representations) {
        this.representations = representations;
        this.activity = activity;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    /**
     * Create a async task without list of representations, use a single Thread for tasks,
     * can throw {@link NullPointerException} if used with {@link RepresentationAsyncTask#createRepresentations(Gear)} method.
     * @param activity {@link AppCompatActivity}
     */
    public RepresentationAsyncTask(AppCompatActivity activity) {
        this.activity = activity;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * @see RepresentationAsyncTask#RepresentationAsyncTask(AppCompatActivity, List)
     * @return New {@link Representation} saved
     * @throws NullPointerException If no representations are set in the list of representations
     */
    public Representation createRepresentations(final Gear gear) throws NullPointerException {
        Representation newRepresentation = null;

        if(this.representations == null){
            throw new NullPointerException("Can not save representations because representations is Null");
        }
        else {
            //execute tasks on multi Threads
            for (final Representation rep : this.representations) {
                //task
                Callable<Representation> callable = () -> {
                    rep.setGearId(gear.getId());
                    GearDatabase database = GearDatabase.getInstance(activity.getApplicationContext());
                    int idNewRep = (int) database.getRepresentationDao().create(rep);

                    rep.setId(idNewRep);
                    return rep;
                };

                try {
                    //execute task
                    Future<Representation> future = this.executorService.submit(callable);
                    //get data from callable
                    newRepresentation = future.get();

                    //update MainActivity
                    if(activity instanceof MainActivity) {
                        ((MainActivity) activity).updateProgressBarOnView();
                    }
                }
                catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //close all Threads
            this.executorService.shutdown();
        }

        return newRepresentation;
    }

}
