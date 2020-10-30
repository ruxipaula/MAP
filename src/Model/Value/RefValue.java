package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int addr, Type locType) {
        address = addr;
        locationType = locType;
    }

    public int getAddress() { return address;}

    public Type getLocationType() {
        return locationType;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public String toString() {
        return "(" + String.valueOf(address) + ", " + locationType.toString() + ")";
    }

    public Value deepCopy() {
        return new RefValue(new Integer(address), locationType.deepCopy());
    }
}
