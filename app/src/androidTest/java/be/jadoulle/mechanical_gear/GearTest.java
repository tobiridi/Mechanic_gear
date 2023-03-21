package be.jadoulle.mechanical_gear;

import static junit.framework.TestCase.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Utils.Utils;

@RunWith(AndroidJUnit4.class)
public class GearTest {
    private GearDatabase database;
    //Data for test
    private static Gear gearTest = new Gear(1,"denomination test","acuateur test",
            "rien de spéciale", "role de test",(byte) 5,
            "aucun test effecutée","category test",null,null);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void openDatabase() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(), GearDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void insertGear() {
        this.database.getGearDao().create(gearTest);

        GearWithAllObjects gear = this.database.getGearDao().find(1);
        System.out.println("gear all details : " + gear);
    }

    @After
    public void closeDatabase() throws Exception {
        this.database.close();
    }
}
