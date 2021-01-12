package ru.osipov.expertSysLabs.mod3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Rule implements IEquatable<Rule> {
    private Predicate conclusion;
    private List<Predicate> premises;

    public Rule(){
        this.premises = new ArrayList<>();
    }

    public void setConclusion(Predicate conclusion) {
        this.conclusion = conclusion;
    }

    public Predicate getConclusion() {
        return conclusion;
    }

    //add only unique predicates.
    public void addPredicate(Predicate p){
        if(!this.premises.contains(p))
            this.premises.add(p);
    }

    public List<Predicate> getPremises() {
        return premises;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String p_i = null;
        for(Predicate p : premises){
            p_i = p.toString();
            p_i = p_i.substring(0, p_i.length() - 1);
            sb.append(p_i);
            sb.append(' ');
        }
        sb.append(" > ").append(conclusion.toString());
        return sb.toString();
    }
    @Override
    public boolean equals(Object ob){
        if(ob instanceof Rule){
            Rule r = (Rule) ob;
            return equals(this, r);
        }
        else
            return false;
    }

    @Override
    public boolean equals(Rule ob1, Rule ob2) {
        boolean f = true;
        ListIterator<Predicate> I1 = this.premises.listIterator();
        ListIterator<Predicate> I2 = ob2.premises.listIterator();
        if(ob1.premises.size() != ob2.premises.size())
            return false;
        while(I1.hasNext() && I2.hasNext() && f){
            Predicate p1 = I1.next();
            Predicate p2 = I2.next();
            f = p1.equals(p2);

        }
        return f;
    }
}
