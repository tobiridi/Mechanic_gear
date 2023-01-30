package be.jadoulle.mechanical_gear.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.jadoulle.mechanical_gear.Entities.Representation;

@Dao
public interface RepresentationDAO {
    @Query("SELECT * FROM representation WHERE id = :id")
    Representation find(int id);

    @Query("SELECT * FROM representation")
    List<Representation> findAll();

    @Insert
    long create(Representation obj);

    @Update
    int update(Representation obj);

    @Delete
    int delete(Representation obj);
}
