package be.jadoulle.mechanical_gear.POJO;

import java.io.Serializable;
import java.util.ArrayList;

/*
    sensor = capteur
    actuator = actionneur
 */

/*
    denomination;       titre/nom (saisie utilisateur)
    representation;     plusieurs images de l'objet (saisie utilisateur)
    gearSensorType;     le type de l'objet (saisie utilisateur)
    basicWorking;       le texte qui decrit sont fonctionnement de base (saisie utilisateur)
    role;               à quoi il sert (saisie utilisateur)
    nbrWire;            nombre de fils (saisie utilisateur)
    tests;              comment on le test, avec quelle outils (saisie utilisateur)
    signalType;         le type de signal (saisie utilisateur + plusieurs photo en plus)
    gearCategory;       actionneur ou capteur (choix utilisateur)
    note;               note de l'utilisateur (saisie utilisateur)
    composition;        la matière de l'objet (saisie utilisateur)
 */

public class Gear implements Serializable {
    //ATTRIBUTES
    private int id;
    private String denomination;
    private ArrayList<byte[]> representations;      //images (can have 0)
    private String gearSensorType;
    private String basicWorking;
    private String role;
    private byte nbrWire;
    private String tests;
    private ArrayList<SignalType> signalTypes;      //images + text (can have 0)
    private String gearCategory;
    private String note;                //(can be null)
    private String composition;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public ArrayList<byte[]> getRepresentations() {
        return representations;
    }
    public void setRepresentations(ArrayList<byte[]> representations) {
        this.representations = representations;
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

    public ArrayList<SignalType> getSignalTypes() {
        return signalTypes;
    }
    public void setSignalTypes(ArrayList<SignalType> signalTypes) {
        this.signalTypes = signalTypes;
    }

    public String getGearCategory() {
        return gearCategory;
    }
    public void setGearCategory(String gearCategory) {
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

    //CONSTRUCTOR
    public Gear(int id, String denomination, String gearSensorType, String basicWorking, String role,
                byte nbrWire, String tests, String gearCategory, String note, String composition) {
        this.id = id;
        this.denomination = denomination;
        this.gearSensorType = gearSensorType;
        this.basicWorking = basicWorking;
        this.role = role;
        this.nbrWire = nbrWire;
        this.tests = tests;
        this.gearCategory = gearCategory;
        this.note = note;
        this.composition = composition;
        this.representations = new ArrayList<>();
        this.signalTypes = new ArrayList<>();
    }

    //METHODS
    public boolean addRepresentation(byte[] newRepresentation) {
        return this.representations.add(newRepresentation);
    }

    public boolean addSignalType(SignalType newSignalType) {
        return this.signalTypes.add(newSignalType);
    }

    @Override
    public String toString() {
        return "Gear{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                ", gearSensorType='" + gearSensorType + '\'' +
                ", basicWorking='" + basicWorking + '\'' +
                ", role='" + role + '\'' +
                ", nbrWire=" + nbrWire +
                ", tests='" + tests + '\'' +
                ", gearCategory='" + gearCategory + '\'' +
                ", note='" + note + '\'' +
                ", composition='" + composition + '\'' +
                '}';
    }
}

