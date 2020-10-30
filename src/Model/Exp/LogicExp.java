package Model.Exp;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1-and, 2-or

    public LogicExp(String operand, Exp exp1, Exp exp2) {
        e1 = exp1;
        e2 = exp2;
        if (operand.equals("and"))
            op = 1;
        else
            op = 2;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value nr1 = e1.eval(tbl, hp);
        if (nr1.getType() instanceof BoolType) {
            Value nr2 = e2.eval(tbl, hp);
            if (nr2.getType() instanceof BoolType) {
                BoolValue bv1, bv2;
                bv1 = (BoolValue)nr1;
                bv2 = (BoolValue)nr2;
                boolean b1, b2;
                b1 = bv1.getVal();
                b2 = bv2.getVal();
                if (op == 1)
                    return new BoolValue(b1 && b2);
                else
                    return new BoolValue(b1 || b2);
            }
            else
                throw new MyException("Second parameter is not BOOLEAN.");
        }
        else throw new MyException("First parameter is not BOOLEAN");
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not boolean.");
        }
        else
            throw new MyException("First operand is not boolean.");
    }

    public String toString() {
        if (op == 1)
            return e1.toString() + " and " + e2.toString();
        else
            return e1.toString() + " or " + e2.toString();
    }
}
