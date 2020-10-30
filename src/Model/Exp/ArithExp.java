package Model.Exp;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char operand, Exp exp1, Exp exp2) {
        e1 = exp1;
        e2 = exp2;

        if (operand == '+') {
            op = 1;
        } else if (operand == '-') {
            op = 2;
        } else if (operand == '*') {
            op = 3;
        } else if (operand == '/')
            op = 4;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4) {
                    if (n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
                }
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");

        return null;
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new MyException("Second operand is not integer.");
        }
        else
            throw new MyException("First operand is not integer.");
    }

    public String toString() {
        if (op == 1) {
            return e1 + "+" + e2;
        } else if (op == 2) {
            return e1 + "-" + e2;
        } else if (op == 3) {
            return e1 + "*" + e2;
        } else
            return e1 + "/" + e2;
    }
}
