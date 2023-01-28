package be.jadoulle.mechanical_gear.POJO;

import java.io.Serializable;

public class SignalType implements Serializable {
    //ATTRIBUTES
    private int id;
    private String text;
    private byte[] picture;
    private Gear gear;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Gear getGear() {
        return gear;
    }
    public void setGear(Gear gear) {
        this.gear = gear;
    }

    //CONSTRUCTOR
    public SignalType(int id, String text, byte[] picture, Gear gear) {
        this.id = id;
        this.text = text;
        this.picture = picture;
        this.gear = gear;
        this.gear.addSignalType(this);
    }

    //METHODS
    @Override
    public String toString() {
        return "SignalType{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
