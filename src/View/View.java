package View;
/*
import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.*;
import Model.Exp.ArithExp;
import Model.Exp.ValueExp;
import Model.Exp.VarExp;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.util.Scanner;

import static java.lang.System.exit;

public class View {
    private static void printMenu() {
        System.out.println("0 - Exit\n");
        System.out.println("1 - Program 1\n");
        System.out.println("2 - Program 2\n");
        System.out.println("3 - Program 3\n");
    }

    private static void stepsMenu() {
        System.out.println("1 - One step\n");
        System.out.println("2 - All steps\n");
        System.out.println("3 - Exit\n");
    }

    private static void executeProgram(IStmt e) {
        MyIStack<IStmt> exeStack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symbTable= new MyDictionary<String, Value>();
        MyIList<Value> outList = new MyList<Value>();
        PrgState progr = new PrgState(exeStack, symbTable, outList, e);

        IRepository repository = new Repository(progr);
        Controller controller = new Controller(repository);

        Scanner scanner2 = new Scanner(System.in);
        int choice;
        while(!exeStack.isEmpty()) {
            stepsMenu();
            choice = scanner2.nextInt();

            if (choice == 1) {
                try {
                    progr = controller.oneStep(progr);
                    System.out.println(progr);
                } catch(MyException exc) {
                    System.out.println(exc.getMessage());
                }
            } else if (choice == 2) {
                try {
                    controller.allSteps();
                } catch(MyException exc) {
                    System.out.println(exc.getMessage());
                }
            } else if (choice == 3) {
                break;
            } else
                System.out.println("Invalid command.");
        }
    }

    public static void main(String[] args) {
        //  int v; v=2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        // int a; int b; a=2+3*5 ; b=a+1 ; Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        while(true) {
            printMenu();
            int option;
            Scanner scanner1 = new Scanner(System.in);
            option = scanner1.nextInt();
            if (option == 0) {
                exit(0);
            } else if(option == 1) {
                executeProgram(ex1);
            } else if(option == 2) {
                executeProgram(ex2);
            } else if(option == 3) {
                executeProgram(ex3);
            } else {
                System.out.println("Invalid input.");
            }
        }
    }
}
*/