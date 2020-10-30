package Model.ADT;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
   private Stack<T> stk;

   public MyStack() {
       stk = new Stack<T>();
   }

   public T pop() {
       return stk.pop();
   }

   public void push(T value) {
       stk.push(value);
   }

   public boolean isEmpty() {
       return stk.empty();
   }

   @Override
   public Stack<T> getStack() {return stk;}

   public String toString() {
       String s = "";
       Stack<T> stk2 = (Stack<T>)stk.clone();
       while(!stk2.isEmpty()) {
           T val = stk2.pop();
           s += val.toString() + "\n";
       }

       return s;

       //return stack.toString();
   }
}
