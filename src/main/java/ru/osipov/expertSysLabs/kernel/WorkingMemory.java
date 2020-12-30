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
    private NavigableSet<Rule> Rules;
    private NavigableSet<Vertex> Vertices;
    private NavigableSet<Vertex> target_Data;

    public WorkingMemory(){
        this.Rules = new TreeSet<>(Comparators::compareRules);
        this.Vertices = new TreeSet<>(Comparators::compareVertices);
        this.target_Data = new TreeSet<>(Comparators::compareVertices);
    }

    public NavigableSet<Rule> getCRules() {
        return Rules;
    }

    public NavigableSet<Vertex> getVertices() {
        return Vertices;
    }


    public NavigableSet<Vertex> getTargets() {
        return target_Data;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\tSource: [ ");
        Iterator<Vertex> i = Vertices.iterator();
        while(i.hasNext()){
            sb.append(i.next().getName()).append(' ');
        }
        sb.append("]\n\t");
        sb.append("Targets: [ ");
        i = target_Data.iterator();
        while(i.hasNext()){
            sb.append(i.next().getName()).append(' ');
        }
        sb.append("]\n\t");
        Iterator<Rule> r = Rules.iterator();
        sb.append("Applicable Rules: [");
        while(r.hasNext()){
            sb.append(r.next().getName()).append(' ');
        }
        sb.append("]\n");
        sb.append("}");
        return sb.toString();
    }
}
