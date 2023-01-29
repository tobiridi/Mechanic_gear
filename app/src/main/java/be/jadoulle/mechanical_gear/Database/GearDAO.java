package be.jadoulle.mechanical_gear.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.Entities.Gear;

@Dao
public interface GearDAO {
    @Transaction
    @Query("SELECT * FROM gear WHERE id = :id")
    GearWithAllObjects find(int id);

    @Transaction
    @Query("SELECT * FROM gear")
    List<GearWithAllObjects> findAll();

    @Insert
    long create(Gear obj);

    @Update
    int update(Gear obj);

    @Delete
    int delete(Gear obj);

}
