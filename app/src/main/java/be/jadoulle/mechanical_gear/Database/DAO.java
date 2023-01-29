package be.jadoulle.mechanical_gear.Database;

import java.util.ArrayList;

public interface DAO<T> {
    T find(int id);
    ArrayList<T> findAll();
    boolean create(T obj);
    boolean update(T obj);
    boolean delete(T obj);

}
