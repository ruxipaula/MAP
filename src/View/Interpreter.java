package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Exp.*;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;

public class Interpreter {

    private static Controller newController(IStmt e) {
        String filePath = "B:\\LabMAP\\A3\\repoLog.txt";
        MyIStack<IStmt> exeStack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symbTable= new MyDictionary<String, Value>();
        MyIList<Value> outList = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<String, BufferedReader>();
        MyIHeap<Integer, Value> heap = new MyHeap<>();
        PrgState progr = new PrgState(exeStack, symbTable, outList, fileTable, heap, e);
        IRepository repo = new Repository(progr, filePath);
        Controller ctr = new Controller(repo);
        return ctr;
    }

    public static void main(String[] args) {
        //  int v; v=2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // int a; int b; a=2+3*5 ; b=a+1 ; Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                    new CompStmt(new VarDeclStmt("b",new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),
                    new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                    new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                    new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                    new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                    new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.txt"))),
                    new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                    new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"),
                    new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                    new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                    new CompStmt(new newStmt("a", new VarExp("v")),
                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new PrintStmt(new rHExp(new VarExp("v"))),
                    new CompStmt(new wHStmt("v", new ValueExp(new IntValue(30))),
                    new PrintStmt(new ArithExp('+',new rHExp(new VarExp("v")),new ValueExp(new IntValue(5))))))));

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                    new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(0)), ">"),
                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                    new PrintStmt(new VarExp("v")))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                    new CompStmt(new newStmt("a", new VarExp("v")),
                    new CompStmt(new newStmt("v", new ValueExp(new IntValue(30))),
                    new PrintStmt(new rHExp(new rHExp(new VarExp("a")))))))));

        //int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                    new CompStmt(new newStmt("a", new ValueExp(new IntValue(22))),
                    new CompStmt(new ForkStmt(new CompStmt(new wHStmt("a", new ValueExp(new IntValue(30))),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rHExp(new VarExp("a"))))))),
                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rHExp(new VarExp("a")))))))));

        Controller ctr1 = newController(ex1);
        Controller ctr2 = newController(ex2);
        Controller ctr3 = newController(ex3);
        Controller ctr4 = newController(ex4);
        Controller ctr5 = newController(ex5);
        Controller ctr6 = newController(ex6);
        Controller ctr7 = newController(ex7);
        Controller ctr8 = newController(ex8);
        Controller ctr9 = newController(ex9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctr5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctr6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctr7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctr8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctr9));
        menu.show();

    }
}
