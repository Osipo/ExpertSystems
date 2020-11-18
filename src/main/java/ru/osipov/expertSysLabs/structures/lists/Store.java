package ru.osipov.expertSysLabs.structures.lists;

public interface Store<T> {
    void insert(T item);
    void delete();
    T get();
}
