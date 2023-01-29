package be.jadoulle.mechanical_gear;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import be.jadoulle.mechanical_gear.Database.GearDatabase;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;

public class RepresentationTest {
    private GearDatabase database;
    //Data for test
    private static Representation representationTest = new Representation(1,null,1);

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
        this.database.getRepresentationDao().create(representationTest);

        Representation representation = this.database.getRepresentationDao().find(1);
        System.out.println("representation details : " + representation);
    }

    @After
    public void closeDatabase() throws Exception {
        this.database.close();
    }
}
