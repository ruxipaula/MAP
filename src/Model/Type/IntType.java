package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {
    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    public Value defaultValue() {
        return new IntValue();
    }
    public String toString() { return "int ";}

    public Type deepCopy() {
        return new IntType();
    }
}
