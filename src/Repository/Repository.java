package Repository;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> programs;
    int current;
    String logFilePath;

    public Repository(PrgState program, String filePath) {
        current = 0;
        programs = new ArrayList<PrgState>();
        logFilePath = filePath;
        programs.add(program);
    }

    public void add(PrgState p) {
        programs.add(p);
    }

    public PrgState getCrtPrg() { return programs.get(current);}

    public List<PrgState> getPrgList() { return programs;}

    public void setPrgList(List<PrgState> prgs) {
        programs = prgs;
    }

    @Override
    public PrgState getPrgState(int id) {
        for(PrgState program : programs)
            if(program.getId() == id)
                return program;
        return null;

    }
    public void logPrgStateExec(PrgState state) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }
        catch(IOException ex) {
            throw new MyException("File can't be opened or inexistent file.");
        }

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIList<Value> out = state.getOutList();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        logFile.print("PrgState ID:\n");
        logFile.print(state.getId());
        logFile.print("\n");
        logFile.print("Exe stack:\n");
        logFile.print(stk.toString());
        logFile.print("\n");
        logFile.print("Sym Table:\n");
        logFile.print(symTable.toString());
        logFile.print("\n");
        logFile.print("Out:\n");
        logFile.print(out.toString());
        logFile.print("\n");
        logFile.print("\n");
        logFile.print("FileTable:\n");
        logFile.print(fileTable.toString());
        logFile.print("\n");
        logFile.print("\n");
        logFile.print("Heap:\n");
        logFile.print(heap.toString());
        logFile.print("\n");
        logFile.print("\n");


        logFile.close();
    }
}
