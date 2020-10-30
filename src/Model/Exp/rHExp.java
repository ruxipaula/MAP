package Model.Exp;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class rHExp implements Exp {
    private Exp exp;

    public rHExp(Exp e) {
        exp = e;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v = exp.eval(tbl, hp);
        if (!(v.getType() instanceof RefType))
            throw new MyException("Value should be of type Ref");
        int addr = ((RefValue) v).getAddress();
        if (!hp.isDefined(addr))
            throw new MyException("Address is not defined.");
        Value val = hp.lookup(addr);
        return val;
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if (type instanceof RefType) {
            RefType t = (RefType) type;
            return t.getInner();
        } else
            throw new MyException("Argument is not Reference Type");
    }

    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
