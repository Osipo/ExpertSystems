package ru.osipov.expertSysLabs.structures.trees;

import java.util.ArrayList;
import java.util.List;

public class LinkedNode<T> extends Node<T> {
    private List<LinkedNode<T>> children;
    private LinkedNode<T> parent;
    public LinkedNode(){
        children = new ArrayList<>();
        idx = -1;
    }

    public void setParent(LinkedNode<T> parent) {
        this.parent = parent;
    }

    public void setChildren(List<LinkedNode<T>> children) {
        this.children = children;
    }

    public LinkedNode<T> getParent() {
        return parent;
    }

    public List<LinkedNode<T>> getChildren() {
        return children;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
