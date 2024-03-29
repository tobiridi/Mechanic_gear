package be.jadoulle.mechanical_gear.Entities;

import static androidx.room.ColumnInfo.BLOB;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "representation",
        foreignKeys = {
            @ForeignKey(entity = Gear.class, parentColumns = {"id"}, childColumns = {"gear_id"}, onDelete = CASCADE)
        })
public class Representation implements Serializable {
    //ATTRIBUTES
    @PrimaryKey(autoGenerate = true)
    private int id;
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
    public Representation(int id, byte[] picture, int gearId) {
        this.id = id;
        this.picture = picture;
        this.gearId = gearId;
    }

    //METHODS
    @Override
    public String toString() {
        return "Representation{" +
                "id=" + id +
                ", gearId=" + gearId +
                '}';
    }
}
