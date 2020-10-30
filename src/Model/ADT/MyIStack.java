package Model.ADT;

import java.util.Stack;

public interface MyIStack<T> {
    // .....
    public T pop();
    public void push(T v);
    public boolean isEmpty();
    public Stack<T> getStack();
}
