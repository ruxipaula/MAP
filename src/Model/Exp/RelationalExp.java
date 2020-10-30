package Model.Exp;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExp implements Exp {
    Exp exp1;
    Exp exp2;
    String operand;

    public RelationalExp(Exp e1, Exp e2, String op) {
        exp1 = e1;
        exp2 = e2;
        operand = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(symTable, hp);
        v2 = exp2.eval(symTable, hp);
        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            IntValue vl1 = (IntValue)v1;
            IntValue vl2 = (IntValue)v2;
            int val1, val2;
            val1 = vl1.getVal();
            val2 = vl2.getVal();
            if (operand.equals("<"))
                return new BoolValue(val1<val2);
            else if (operand.equals("<="))
                return new BoolValue(val1<=val2);
            else if (operand.equals("=="))
                return new BoolValue(val1==val2);
            else if (operand.equals("!="))
                return new BoolValue(val1!=val2);
            else if (operand.equals(">"))
                return new BoolValue(val1>val2);
            else if (operand.equals(">="))
                return new BoolValue(val1>=val2);
            else
                throw new MyException("Invalid operand.");
        }
        else throw new MyException("Expression has to be an integer");
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not integer.");
        }
        else
            throw new MyException("First operand is not integer.");
    }

    public String toString() {
        return exp1.toString() + " " + operand + " " + exp2.toString();
    }
}
