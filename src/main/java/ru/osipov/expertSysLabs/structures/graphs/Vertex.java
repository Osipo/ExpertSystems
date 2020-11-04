package ru.osipov.expertSysLabs.structures.graphs;

import java.util.*;

/**
 * The main class which represents a graph node.
 * As the graph is Digraph, the one has two different type of nodes.
 * The type for this node is fact.
 * @author Osipov O.K.
 */
public class Vertex implements Node {
    private static long id = -1L;
    private String name;
    private String val;//Content of node.

    private NavigableSet<Rule> rules;//Rules for which this node is premise.

    private int compareRules(Rule r1, Rule r2){
        long c1 = Long.parseLong(r1.getName().substring(2));
        long c2 = Long.parseLong(r2.getName().substring(2));
        return Long.compare(c1,c2);
    }

    public Vertex(){
        this.name = "V_" + (++id);
        this.val = null;
        this.rules = new TreeSet<>(this::compareRules);
    }

    public Vertex(String val){
        this.name = "V_" + (++id);
        this.val = val;
        this.rules = new TreeSet<>(this::compareRules);
    }

    public String getName(){
        return name;
    }

    public void setValue(String v){
        this.val = v;
    }

    public String getValue(){
        return val;
    }

    public NavigableSet<Rule> getRules(){
        return this.rules;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Vertex) {
            Vertex b = (Vertex) obj;
            return name.equals(b.name);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }

    @Override
    public String toString(){
        return this.name;
    }


    public void addRule(Rule r){
        this.rules.add(r);
    }

    public boolean removeRule(Rule r){
        if(r == null || !this.rules.contains(r))
            return false;
        this.rules.remove(r);
        return true;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FACT;
    }
}
