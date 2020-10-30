package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    public Value defaultValue() {
        return new BoolValue();
    }

    public String toString() {
        return "boolean ";
    }

    public Type deepCopy() {
        return new BoolType();
    }
}
