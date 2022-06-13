package com.example.mechanic_gear.java_beans;

import java.io.Serializable;

public class Gear implements Serializable {

    private String denomination;
    private GearSensorType gearSensorType;
    private String basicWorking;
    private String role;
    private byte nbrWire;               //-127 -> 127
    private String tests;
    private String signal;              //image
    private String electricalSymbol;    //image
    private GearCategory gearCategory;

    public String getDenomination() {
        return denomination;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public GearSensorType getGearSensorType() {
        return gearSensorType;
    }
    public void setGearSensorType(GearSensorType gearSensorType) {
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

    public String getSignal() {
        return signal;
    }
    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getElectricalSymbol() {
        return electricalSymbol;
    }
    public void setElectricalSymbol(String electricalSymbol) {
        this.electricalSymbol = electricalSymbol;
    }

    public GearCategory getGearCategory() {
        return gearCategory;
    }
    public void setGearCategory(GearCategory gearCategory) {
        this.gearCategory = gearCategory;
    }

    public Gear(String denomination, GearSensorType gearSensorType, String basicWorking,
                String role, byte nbrWire, String tests, String signal, String electricalSymbol,
                GearCategory gearCategory) {

        this.denomination = denomination;
        this.gearSensorType = gearSensorType;
        this.basicWorking = basicWorking;
        this.role = role;
        this.nbrWire = nbrWire;
        this.tests = tests;
        this.signal = signal;
        this.electricalSymbol = electricalSymbol;
        this.gearCategory = gearCategory;
    }

    public Gear(){

    }

    @Override
    public String toString() {
        return "Gear{" +
                "denomination='" + denomination + '\'' +
                ", gearSensorType=" + gearSensorType +
                ", basicWorking='" + basicWorking + '\'' +
                ", role='" + role + '\'' +
                ", nbrWire=" + nbrWire +
                ", tests='" + tests + '\'' +
                ", signal='" + signal + '\'' +
                ", electricalSymbol='" + electricalSymbol + '\'' +
                ", gearCategory=" + gearCategory +
                '}';
    }

}
