package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Working memory
 * Contains real facts (source_data), and designated rules (dRules) and vertices (dVertices).
 * @author Osipov O.K.
 */
public class WorkingMemory {
    private NavigableSet<Rule> dRules;
    private NavigableSet<Vertex> dVertices;
    private NavigableSet<Vertex> source_data;

    public WorkingMemory(){
        this.dRules = new TreeSet<>(Comparators::compareRules);
        this.dVertices = new TreeSet<>(Comparators::compareVertices);
        this.source_data = new TreeSet<>(Comparators::compareVertices);
    }

    public NavigableSet<Rule> getCRules() {
        return dRules;
    }

    public NavigableSet<Vertex> getVertices() {
        return dVertices;
    }


    public NavigableSet<Vertex> getData() {
        return source_data;
    }
}
