package Model.Type;

import Model.Value.Value;

public interface Type {
    boolean equals(Object another);
    Value defaultValue();
    Type deepCopy();
}
