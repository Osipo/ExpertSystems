package ru.osipov.expertSysLabs.structures.graphs;

import java.util.Set;

public class Rule extends Vertex {

    private Vertex conclusion;//result of rule.
    private Set<Vertex> premises;//conditions.
    private RuleType type;//type of connection between conditions (OR, AND, NOT(c1 AND c2 AND ... AND cn))

    public Rule(Set<Vertex> premises,Vertex conclusion, RuleType type){
       super();
       this.name = "R_" + id;
       this.premises = premises;
       this.conclusion = conclusion;
       this.type = type;
    }

    public Set<Vertex> getPremises(){
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
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Rule) {
            Rule b = (Rule) obj;
            return premises.equals(b.premises) && conclusion.equals(b.conclusion) && type.equals(b.type);
        }
        return false;
    }
}
