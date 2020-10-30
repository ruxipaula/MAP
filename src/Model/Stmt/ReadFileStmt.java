package Model.Stmt;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    Exp exp;
    String varName;

    public ReadFileStmt(Exp e, String vName) {
        exp = e;
        varName = vName;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(varName)) {
            if(symTable.lookup(varName).getType().equals(new IntType())) {
                Value v = exp.eval(symTable, heap);
                Type t = v.getType();
                if (t.equals(new StringType())) {
                    MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
                    StringValue sv = (StringValue)v;
                    String str = sv.getVal();
                    if (fileTable.isDefined(str)) {
                        BufferedReader breader = fileTable.lookup(str);
                        //reads a line from the file
                        int nr;
                        try {
                            String line = breader.readLine();
                            if (line == null)
                                nr = 0;
                            else
                                nr = Integer.parseInt(line);
                        }
                        catch(IOException e) {
                            throw new MyException("Cannot read from file.");
                        }
                        IntValue nrCast = new IntValue(nr);
                        symTable.update(varName, nrCast);
                    }
                    else {
                        throw new MyException("String is not defined.");
                    }
                }
                else {
                    throw new MyException("Type is not String.");
                }
            }
            else {
                throw new MyException("Variable must be int.");
            }
        }
        else {
            throw new MyException("Undefined varible.");
        }

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(varName);
        Type expType = exp.typecheck(typeEnv);
        if (varType.equals(new IntType())) {
            if (expType.equals(new StringType()))
                return typeEnv;
            else
                throw new MyException("Read file exc.");
        }
        else
            throw new MyException("File name is not string.");
    }

    public String toString() {
        return "readRFile(" + exp.toString() + ", " + varName + ")";
    }
}
