package be.jadoulle.mechanical_gear.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import be.jadoulle.mechanical_gear.POJO.Gear;
import be.jadoulle.mechanical_gear.POJO.Representation;
import be.jadoulle.mechanical_gear.POJO.SignalType;

@Database(entities = {
        Gear.class,
        SignalType.class,
        Representation.class
        },
        version = 1, exportSchema = true)
public abstract class GearDatabase extends RoomDatabase {
    private static GearDatabase INSTANCE;

    public static GearDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GearDatabase.class,"gearDatabase.db").build();
        }
        return INSTANCE;
    }

    //get DAO class
    public abstract GearDAO getGearDao();
    public abstract SignalTypeDAO getSignalTypeDao();
}
