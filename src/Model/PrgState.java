package Model;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.Stmt.IStmt;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private int id;
    private static int manageId;

    //IStmt originalProgram; //optional field, but good to have

    public MyIStack<IStmt> getStk() { return exeStack;}
    public void setMyIStack(MyIStack<IStmt> s) { exeStack = s;}

    public MyIDictionary<String, Value> getSymTable() { return symTable;}
    public void setMyIDictionary(MyIDictionary<String, Value> d) { symTable = d;}

    public MyIList<Value> getOutList() { return out;}
    public void setMyIList(MyIList<Value> l) { out = l;}

    public MyIDictionary<String, BufferedReader> getFileTable() {return fileTable;}

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> fTable, MyIHeap<Integer, Value> h, IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable = fTable;
        heap = h;
        //originalProgram = deepCopy(prg);//recreate the entire original prg
        exeStack.push(prg);
        id = getNewId();
        System.out.println(id);
    }

    public Boolean isNotCompleted() {
        if (!exeStack.isEmpty())
            return true;
        return false;
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty())
            throw new MyException("PrgState stack is empty.");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public synchronized int getNewId() {
        manageId++;
        return manageId;
    }

    public String toString() {
        String s = "";
        s+="Id:\n";
        s+=Integer.toString(id);
        s+="Exe Stack:\n";
        s+=exeStack.toString() + "\n";
        s+="Symb Table:\n";
        s+=symTable.toString() + "\n";
        s+="Out:\n";
        s+=out.toString() + "\n";
        return s;
    }
}
