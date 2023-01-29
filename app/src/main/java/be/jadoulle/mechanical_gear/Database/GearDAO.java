package be.jadoulle.mechanical_gear.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import be.jadoulle.mechanical_gear.POJO.Gear;

@Dao
public interface GearDAO extends DAO<Gear> {
    @Override
    @Query("SELECT * FROM gear WHERE id = :id")
    Gear find(int id);

//    @Query("SELECT * FROM gear LEFT JOIN signal_type ON gear.id = signal_type.gear_id ORDER BY gear.id")
    @Override
    @Query("SELECT * FROM gear")
    ArrayList<Gear> findAll();

    @Override
    @Insert
    boolean create(Gear obj);

    @Override
    @Update
    boolean update(Gear obj);

    @Override
    @Delete
    boolean delete(Gear obj);

}
