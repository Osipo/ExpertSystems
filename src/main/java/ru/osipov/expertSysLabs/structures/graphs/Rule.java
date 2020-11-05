package ru.osipov.expertSysLabs.structures.graphs;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The class for the rule (the second type of node in Digraph)
 * Contains premises conclusion and the type of connection of premises (AND,OR,NOT)
 * @author Osipov O.K.
 */
public class Rule implements Node{

    private Vertex conclusion;//result of rule.
    private NavigableSet<Vertex> premises;//conditions.
    private RuleType type;//type of connection between conditions (OR, AND, NOT(c1 AND c2 AND ... AND cn))
    private String name;
    private static long id = -1L;

    public Rule(){
        this.name = "R_" + (++id);
        this.premises = new TreeSet<>(Comparators::compareVertices);
        this.conclusion = null;
        this.type = RuleType.AND;
    }

    public String getName(){
        return name;
    }

    public NavigableSet<Vertex> getPremises(){
        return this.premises;
    }

    public void setConclusion(Vertex c){
        this.conclusion = c;
    }

    public Vertex getConclusion(){
        return this.conclusion;
    }

    public void setType(RuleType t){
        this.type = t;
    }

    public RuleType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Rule) {
            Rule b = (Rule) obj;
            return name.equals(b.name);
        }
        return false;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.RULE;
    }
}
