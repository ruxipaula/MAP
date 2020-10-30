package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> myList;

    public MyList() {
        myList = new ArrayList<T>();
    }

    @Override
    public void add(T element) {
        myList.add(element);
    }

    @Override
    public int size() {
        return myList.size();
    }

    @Override
    public T get(int i) {
        return myList.get(i);
    }

    @Override
    public List<T> getContent() {return myList;}

    public String toString() {
        return myList.toString();
    }

}
