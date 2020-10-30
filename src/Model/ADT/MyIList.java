package Model.ADT;

import java.util.List;

public interface MyIList<T> {
    public void add(T element);
    public int size();
    public T get(int i);
    public List<T> getContent();
}
