package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {
    private String val;

    public StringValue(String s) { val = s; }
    public StringValue() { val = "nothing";}

    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return val;
    }

    public boolean equals(Object another) {
        StringValue s = (StringValue)(another);
        return val.equals(s.getVal());
    }

    public String toString() {
        return val;
    }

    public Value deepCopy() {
        return new StringValue(new String(val));
    }
}
