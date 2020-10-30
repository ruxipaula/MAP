package Model.ADT;

import Exceptions.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyHeap<K,V> implements MyIHeap<K,V> {
    private Map<K, V> dict;
    private int nextFree;

    public MyHeap() {
        dict = new HashMap<K,V>();
        nextFree = 1;
    }

    @Override
    public int getNextFree() { return nextFree;}

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
        nextFree++;
    }

    @Override
    public V lookup(K key) throws MyException {
        V value = dict.get(key);
        if (value == null)
            throw new MyException("Variable is not defined!");
        else
            return value;
    }

    @Override
    public void update(K key, V value) {
        dict.replace(key, value);
    }

    @Override
    public Map<K,V> getContent() {
        return dict;
    }

    @Override
    public void setContent(Map<K,V> map) {
        dict.clear();
        dict.putAll(map);
    }

    public String toString() {
        String s = "";
        Iterator it = dict.keySet().iterator();
        while(it.hasNext()) {
            K key = (K)it.next();
            s += key.toString() + ":";
            V value = dict.get(key);
            s += value.toString() + "\n";
        }
        return s;
    }
}
