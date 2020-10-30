package ru.osipov.expertSysLabs.structures.trees;

public interface PositionalTree<T> extends Tree<T> {
    void addTo(Node<T> n, T item);
    Node<T> rightMostChild(Node<T> n);
    PositionalTree<T> getSubTree(Node<T> n);
}
