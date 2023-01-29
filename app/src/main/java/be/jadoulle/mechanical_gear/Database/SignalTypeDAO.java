package be.jadoulle.mechanical_gear.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

import be.jadoulle.mechanical_gear.POJO.SignalType;

@Dao
public interface SignalTypeDAO extends DAO<SignalType> {
    @Override
    @Query("SELECT * FROM signal_type WHERE id = :id")
    SignalType find(int id);

    @Override
    @Query("SELECT * FROM signal_type")
    ArrayList<SignalType> findAll();

    @Override
    @Insert
    boolean create(SignalType obj);

    @Override
    @Update
    boolean update(SignalType obj);

    @Override
    @Delete
    boolean delete(SignalType obj);

}
