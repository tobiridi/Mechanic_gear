package be.jadoulle.mechanical_gear.Entities;

import static androidx.room.ColumnInfo.BLOB;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "signal_type",
        foreignKeys = {
            @ForeignKey(entity = Gear.class, parentColumns = {"id"}, childColumns = {"gear_id"}, onDelete = CASCADE)
        })
public class SignalType implements Serializable {
    //ATTRIBUTES
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(typeAffinity = BLOB)
    private byte[] picture;
    @ColumnInfo(name = "gear_id")
    private int gearId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getGearId() {
        return gearId;
    }
    public void setGearId(int gearId) {
        this.gearId = gearId;
    }

    //CONSTRUCTOR
    public SignalType(int id, String name, byte[] picture, int gearId) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.gearId = gearId;
    }

    //METHODS
    @Override
    public String toString() {
        return "SignalType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gearId='" + gearId + '\'' +
                '}';
    }
}
