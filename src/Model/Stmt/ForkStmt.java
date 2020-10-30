package Model.Stmt;

import Exceptions.MyException;
import Model.ADT.*;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt {
    IStmt s;

    public ForkStmt(IStmt stmt) {
        s = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> dict = state.getSymTable();
        MyIList<Value> out = state.getOutList();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> newSymTable = new MyDictionary<String, Value>();
        MyIStack<IStmt> stk = new MyStack<>();

        for (Map.Entry<String, Value> entry: dict.getContent().entrySet())
            newSymTable.put(new String(entry.getKey()), entry.getValue().deepCopy());

        PrgState newPrgState = new PrgState(stk, newSymTable, out, fileTable, heap, s);
        return newPrgState;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        s.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "fork(" + s.toString() + ")";
    }
}
