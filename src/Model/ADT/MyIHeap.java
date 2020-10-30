package Model.ADT;

import Exceptions.MyException;

import java.util.Map;

public interface MyIHeap<K, V> {
    public void put(K key, V value);
    public V lookup(K key) throws MyException;
    public int getNextFree();
    public boolean isDefined(K key);
    public void update(K key, V value);
    public Map<K,V> getContent();
    public void setContent(Map<K, V> map);
}
