package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue() { val = false;}
    public BoolValue(boolean v) { val = v;}

    public boolean getVal() { return val;}

    public String toString() {
        return String.valueOf(val);
    }

    public Type getType() {
        return new BoolType();
    }

    //delete equals method from all Value classes!!
    public boolean equals(Object another) {
        BoolValue v = (BoolValue) another;
        return val == v.getVal();
    }

    public Value deepCopy() {
        return new BoolValue(new Boolean(val));
    }


}