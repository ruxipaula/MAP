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
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    Exp exp;

    public CloseRFileStmt(Exp e) {
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value v = exp.eval(symTable, heap);
        Type t = v.getType();
        if (!t.equals(new StringType()))
            throw new MyException("Name of file should be a string.");
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        StringValue sv = (StringValue)v;
        if (!fileTable.isDefined(sv.getVal()))
            throw new MyException("Couldn't close file because it was not opened before.");
        BufferedReader breader = fileTable.lookup(sv.getVal());
        try{
            breader.close();
        }
        catch(IOException e) {
            throw new MyException("Couldn't close file.");
        }
        fileTable.delete(sv.getVal());
        return null;    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if (expType.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("CloseRFile exp is not string.");
    }

    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}
