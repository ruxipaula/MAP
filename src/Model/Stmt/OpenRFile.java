package Model.Stmt;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    Exp exp;

    public OpenRFile(Exp e) {
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value val = exp.eval(state.getSymTable(), heap);
        Type valType = val.getType();
        if (valType.equals(new StringType())) {
            StringValue sval = (StringValue)val;
            if (!fileTable.isDefined(sval.getVal())) {
                try {
                    BufferedReader myFile = new BufferedReader(new FileReader(sval.getVal()));
                    fileTable.put(sval.getVal(), myFile);
                }
                catch(IOException e) {
                    throw new MyException(e.getMessage());
                }
            }
            else {
                throw new MyException("Already opened.");
            }
        }
        else {
            throw new MyException("Invalid type.");
        }

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if (expType.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new MyException("OpenRFile exp is not string.");
    }

    public String toString() {
        return "OpenRFile("+ exp.toString() + ")";
    }

}
