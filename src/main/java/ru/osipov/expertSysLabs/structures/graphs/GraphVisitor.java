package ru.osipov.expertSysLabs.structures.graphs;

import ru.osipov.expertSysLabs.structures.trees.Action;

import java.util.Set;

public interface GraphVisitor<T> {
    void visit(T node, Action<T> act);
}
