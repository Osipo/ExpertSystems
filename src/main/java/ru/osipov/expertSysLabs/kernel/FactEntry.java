package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.structures.graphs.Node;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;

/**
 * A record in base
 * It specify whether the content is presented at digraph as premise or conclusion.
 * @author Osipov O.K.
 */
public class FactEntry {
    private Vertex premise;//A node of digraph which is premise
    private Vertex conclusion;//A node of digraph which is conclusion.

    public FactEntry(Vertex p, Vertex c){
        this.premise = p;
        this.conclusion = c;
    }

    public Vertex getConclusion() {
        return conclusion;
    }

    public Vertex getPremise() {
        return premise;
    }

    public void setPremise(Vertex premise) {
        this.premise = premise;
    }

    public void setConclusion(Vertex conclusion) {
        this.conclusion = conclusion;
    }
}
