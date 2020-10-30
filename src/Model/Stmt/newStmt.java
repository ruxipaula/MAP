package Model.Stmt;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class newStmt implements IStmt {
    private String varName;
    private Exp exp;

    public newStmt(String name, Exp e) {
        varName = name;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new MyException("Variable name is not defined!");
        Value v = symTable.lookup(varName);
        if (!(v.getType() instanceof RefType))
            throw new MyException("Var is not reference!");
        Value val = exp.eval(symTable, heap);
        RefValue rv = (RefValue)v;
        if (!val.getType().equals(rv.getLocationType()))
            throw new MyException("Types should be equal.");

        int free = heap.getNextFree();
        heap.put(free, val);
        symTable.update(varName, new RefValue(free, ((RefValue) v).getLocationType()));
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("New stmt: diff types.");
    }

    public String toString() {
        return "newStmt(" + varName + ", " + exp.toString() + ")";
    }
}
