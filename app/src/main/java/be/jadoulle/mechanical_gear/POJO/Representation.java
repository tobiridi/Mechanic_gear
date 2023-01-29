package be.jadoulle.mechanical_gear.POJO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "representation")
public class Representation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private byte[] picture;
    
    private Gear gearRepresentation;
}
