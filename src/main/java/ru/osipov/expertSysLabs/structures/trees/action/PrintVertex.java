package ru.osipov.expertSysLabs.structures.trees.action;

import ru.osipov.expertSysLabs.structures.graphs.Vertex;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;
import ru.osipov.expertSysLabs.structures.trees.Action;

//PRINT path from current vertex to initial vertex.
public class PrintVertex implements Action<Vertex> {
    @Override
    public void perform(Vertex arg) {
        Vertex t = arg;
        while(t != null){
            System.out.print(t);
            t = t.getPrev();
            if(t != null)
                System.out.print(" > ");
        }
        System.out.print("\n");
    }
}
