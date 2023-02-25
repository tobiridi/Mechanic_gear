package be.jadoulle.mechanical_gear.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;

@Database(entities = {
        Gear.class,
        SignalType.class,
        Representation.class
        },
        version = 2, exportSchema = true)
public abstract class GearDatabase extends RoomDatabase {
    private static GearDatabase INSTANCE;

    public static GearDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GearDatabase.class,"gearDatabase.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    //DAO class
    public abstract GearDAO getGearDao();
    public abstract SignalTypeDAO getSignalTypeDao();
    public abstract RepresentationDAO getRepresentationDao();
}
