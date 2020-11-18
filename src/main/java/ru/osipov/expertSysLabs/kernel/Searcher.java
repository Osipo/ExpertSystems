package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;
import ru.osipov.expertSysLabs.structures.lists.LinkedList;
import ru.osipov.expertSysLabs.structures.lists.LinkedQueue;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;
import ru.osipov.expertSysLabs.structures.lists.Store;

import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Searcher {

    private Store<Vertex> O_V;//opened list of facts.
    private Store<Rule> O_R;//opened list of rules.

    private NavigableSet<Vertex> C_V;//closed set of facts.
    private NavigableSet<Rule> C_R;//closed set of rules.

    private NavigableSet<Vertex> target;//target.

    private WorkingMemory wm;
    public Searcher() {
        this(SearchMode.BFS);
    }

    public Searcher(SearchMode m){
        switch (m){
            case BFS:{
                O_V = new LinkedQueue<>();
                O_R = new LinkedQueue<>();
                break;
            }
            case DFS:{
                O_V = new LinkedStack<>();
                O_R = new LinkedStack<>();
                break;
            }
            default:{
                O_V = new LinkedQueue<>();
                O_R = new LinkedQueue<>();
                break;
            }
        }
        C_R = new TreeSet<>(Comparators::compareRules);
        C_V = new TreeSet<>(Comparators::compareVertices);
        this.target = new TreeSet<>(Comparators::compareVertices);
        this.wm = new WorkingMemory();
    }

    public void search(){

    }

    private boolean hasTarget(Base data, String t){
        FactEntry f = data.getFacts().getValue(t);
        if(f != null){
            if(f.getPremise() != null)
                this.target.add(f.getPremise());
            if(f.getConclusion() != null)
                this.target.add(f.getConclusion());
            return true;
        }
        return false;
    }
}
