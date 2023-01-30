package be.jadoulle.mechanical_gear;

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
import be.jadoulle.mechanical_gear.Entities.SignalType;

@RunWith(AndroidJUnit4.class)
public class SignalTypeTest {
    private GearDatabase database;
    //Data for test
    private static SignalType signalTypeTest = new SignalType(1,"signal de test",null,1);

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
    public void insertSignalType() {
        this.database.getSignalTypeDao().create(signalTypeTest);

        SignalType signalType = this.database.getSignalTypeDao().find(1);
        System.out.println("signalType details : " + signalType);
    }

    @After
    public void closeDatabase() throws Exception {
        this.database.close();
    }

}
