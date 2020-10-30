package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    public boolean equals(Object another) {
        if (another instanceof StringType)
            return true;
        else
            return false;
    }

    public Value defaultValue() {
        return new StringValue();
    }

    public String toString() {
        return "String ";
    }

    public Type deepCopy() {
        return new StringType();
    }
}
