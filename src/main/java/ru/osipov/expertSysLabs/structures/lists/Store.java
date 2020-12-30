package ru.osipov.expertSysLabs.structures.lists;

import java.util.Iterator;

public interface Store<T> {
    void insert(T item);
    void delete();
    T get();
    int size();
    Iterator<T> iterator();
    boolean contains(T obj);
}
