package be.jadoulle.mechanical_gear.Entities.DataClasses;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import be.jadoulle.mechanical_gear.Entities.Gear;
import be.jadoulle.mechanical_gear.Entities.Representation;
import be.jadoulle.mechanical_gear.Entities.SignalType;

public class GearWithAllObjects implements Serializable {
    @Embedded
    private Gear gear;
    @Relation(parentColumn = "id" , entityColumn = "gear_id")
    private List<Representation> representations;
    @Relation(parentColumn = "id" , entityColumn = "gear_id")
    private List<SignalType> signalTypes;

    public Gear getGear() {
        return gear;
    }
    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public List<Representation> getRepresentations() {
        return representations;
    }
    public void setRepresentations(List<Representation> representations) {
        this.representations = representations;
    }

    public List<SignalType> getSignalTypes() {
        return signalTypes;
    }
    public void setSignalTypes(List<SignalType> signalTypes) {
        this.signalTypes = signalTypes;
    }

    //METHODS
    @Override
    public String toString() {
        return "GearWithAllObjects{" +
                "gear=" + gear +
                ", representations=" + representations +
                ", signalTypes=" + signalTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        GearWithAllObjects other = (GearWithAllObjects) o;
        return this.gear.equals(other.gear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gear, representations, signalTypes);
    }

    public boolean addRepresentation(Representation newRepresentation) {
        return this.representations.add(newRepresentation);
    }

    public boolean addSignalType(SignalType newSignalType) {
        return this.signalTypes.add(newSignalType);
    }
}
