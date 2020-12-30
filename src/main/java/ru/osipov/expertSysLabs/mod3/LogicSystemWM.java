package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;

import java.util.*;

public class LogicSystemWM {
    private Map<String, Predicate> facts;
    private Set<PRule> rules;
    public LogicSystemWM(){
        this.facts = new TreeMap<>(Comparators::compareStrings);
        this.rules = new HashSet<>();
    }
    public void addPredicate(Predicate p){
        facts.put(p.getName(), p);
    }
    public Predicate getPredicate(String name){
        return facts.getOrDefault(name, null);
    }

    public boolean addPRule(PRule r){
        return this.rules.add(r);
    }

    public boolean removePRule(PRule r){
        return this.rules.remove(r);
    }

    public Iterator<Predicate> getFacts(){
        return new PredicateIterator(this.facts.entrySet().iterator());
    }

    public Iterator<PRule> getRules(){
        return rules.iterator();
    }

    public void printContent(){
        for(Map.Entry<String,Predicate> e : facts.entrySet()){
            System.out.print(e.getValue().toString());
        }
    }

    private class PredicateIterator implements Iterator<Predicate> {

        private Iterator<Map.Entry<String, Predicate>> current;

        private PredicateIterator(Iterator<Map.Entry<String, Predicate>> I){
            this.current = I;
        }
        @Override
        public boolean hasNext() {
            return current.hasNext();
        }

        @Override
        public Predicate next() {
            return current.next().getValue();
        }
    }
}
