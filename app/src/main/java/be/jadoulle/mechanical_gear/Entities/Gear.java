package be.jadoulle.mechanical_gear.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

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

@Entity(tableName = "gear")
public class Gear implements Serializable {
    //ATTRIBUTES
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String denomination;
    @ColumnInfo(name = "sensor_type")
    private String sensorType;
    @ColumnInfo(name = "basic_working")
    private String basicWorking;
    private String role;
    @ColumnInfo(name = "nbr_wire")
    private byte nbrWire;
    private String tests;
    private String category;
    private String note;
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

    public String getSensorType() {
        return sensorType;
    }
    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
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

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
    public Gear(int id, String denomination, String sensorType, String basicWorking, String role,
                byte nbrWire, String tests, String category, String note, String composition) {
        this.id = id;
        this.denomination = denomination;
        this.sensorType = sensorType;
        this.basicWorking = basicWorking;
        this.role = role;
        this.nbrWire = nbrWire;
        this.tests = tests;
        this.category = category;
        this.note = note;
        this.composition = composition;
    }

    //METHODS
    public boolean create() {
        // TODO : not finished
//        GearDatabase.getInstance(null).getGearDao().insertGear(this);
        return false;
    }

    public boolean update() {
        // TODO : not finished
        return false;
    }

    public static List<Gear> getAll() {
        // TODO : not finished
        return null;
    }

    @Override
    public String toString() {
        return "Gear{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                ", gearSensorType='" + sensorType + '\'' +
                ", basicWorking='" + basicWorking + '\'' +
                ", role='" + role + '\'' +
                ", nbrWire=" + nbrWire +
                ", tests='" + tests + '\'' +
                ", gearCategory='" + category + '\'' +
                ", note='" + note + '\'' +
                ", composition='" + composition + '\'' +
                '}';
    }
}

