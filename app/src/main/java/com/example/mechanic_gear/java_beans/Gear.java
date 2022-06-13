package com.example.mechanic_gear.java_beans;

import java.io.Serializable;

public class Gear implements Serializable {

    private String denomination;
    private String representation;      //image (can be null)
    private String gearSensorType;
    private String basicWorking;
    private String role;
    private byte nbrWire;               //-127 -> 127
    private String tests;
    private String signalType;          //image + text (can be null)
    private GearCategory gearCategory;
    private String note;                //(can be null)
    private String composition;

    public String getDenomination() {
        return denomination;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getRepresentation() {
        return representation;
    }
    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public String getGearSensorType() {
        return gearSensorType;
    }
    public void setGearSensorType(String gearSensorType) {
        this.gearSensorType = gearSensorType;
    }

    public String getBasicWorking() {
        return basicWorking;
    }
    public void setBasicWorking(String basicWorking) {
        this.basicWorking = basicWorking;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public byte getNbrWire() {
        return nbrWire;
    }
    public void setNbrWire(byte nbrWire) {
        this.nbrWire = nbrWire;
    }

    public String getTests() {
        return tests;
    }
    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getSignalType() {
        return signalType;
    }
    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public GearCategory getGearCategory() {
        return gearCategory;
    }
    public void setGearCategory(GearCategory gearCategory) {
        this.gearCategory = gearCategory;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public String getComposition() {
        return composition;
    }
    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Gear(String denomination, String representation, String gearSensorType,
                String basicWorking, String role, byte nbrWire, String tests, String signalType,
                GearCategory gearCategory, String note, String composition) {

        this.denomination = denomination;
        this.representation = representation;
        this.gearSensorType = gearSensorType;
        this.basicWorking = basicWorking;
        this.role = role;
        this.nbrWire = nbrWire;
        this.tests = tests;
        this.signalType = signalType;
        this.gearCategory = gearCategory;
        this.note = note;
        this.composition = composition;
    }

    public Gear(){

    }

    @Override
    public String toString() {
        return "Gear{" +
                "denomination='" + denomination + '\'' +
                ", representation='" + representation + '\'' +
                ", gearSensorType='" + gearSensorType + '\'' +
                ", basicWorking='" + basicWorking + '\'' +
                ", role='" + role + '\'' +
                ", nbrWire=" + nbrWire +
                ", tests='" + tests + '\'' +
                ", signalType='" + signalType + '\'' +
                ", gearCategory=" + gearCategory +
                ", note='" + note + '\'' +
                ", composition='" + composition + '\'' +
                '}';
    }
}
