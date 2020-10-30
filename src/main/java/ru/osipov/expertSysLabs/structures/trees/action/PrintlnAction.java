package ru.osipov.expertSysLabs.structures.trees.action;

import ru.osipov.expertSysLabs.structures.trees.Action;
import ru.osipov.expertSysLabs.structures.trees.Node;

public class PrintlnAction<T> implements Action<Node<T>> {
    @Override
    public void perform(Node<T> arg) {
        System.out.println(arg.getValue().toString());
    }
}
