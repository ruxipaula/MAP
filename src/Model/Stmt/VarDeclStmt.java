package Model.Stmt;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type type;

    public VarDeclStmt(String n, Type t) {
        name = n;
        type = t;
    }

    public String toString() {
        return type + name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new MyException("Variable is already defined.");
        else {
            if (type.equals(new IntType())) {
                Type t = new IntType();
                Value v = t.defaultValue();
                symTable.put(name, v);
            } else if(type.equals(new BoolType())) {
                Type t = new BoolType();
                Value v = t.defaultValue();
                symTable.put(name, v);
            } else if (type.equals(new StringType())) {
                Type t = new StringType();
                Value v = t.defaultValue();
                symTable.put(name, v);
            }
            else {
                Value v = type.defaultValue();
                symTable.put(name, type.defaultValue());
            }
        }
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }
}
