package be.jadoulle.mechanical_gear.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import be.jadoulle.mechanical_gear.Entities.SignalType;

@Dao
public interface SignalTypeDAO {
    @Query("SELECT * FROM signal_type WHERE id = :id")
    SignalType find(int id);

    @Query("SELECT * FROM signal_type")
    List<SignalType> findAll();

    @Insert
    long create(SignalType obj);

    @Update
    int update(SignalType obj);

    @Delete
    int delete(SignalType obj);

}
