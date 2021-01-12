package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;

import java.util.*;

/* Stores Input Data */
public class LogicSystemStore {
    private Map<String, Predicate> facts;
    private Set<Rule> rules;
    private Predicate target;
    public LogicSystemStore(){
        this.facts = new TreeMap<>(Comparators::compareStrings);
        this.rules = new HashSet<>();
        this.target = null;
    }

    public void setTarget(Predicate target) {
        this.target = target;
    }

    public Predicate getTarget() {
        return target;
    }

    public void addPredicate(Predicate p){
        facts.put(p.getName() + p.getSize(), p);
    }
    public Predicate getPredicate(String name){
        return facts.getOrDefault(name, null);
    }


    public boolean addRule(Rule r){
        return this.rules.add(r);
    }

    public boolean removeRule(Rule r){
        return this.rules.remove(r);
    }

    public Iterator<Predicate> getFacts(){
        return new PredicateIterator(this.facts.entrySet().iterator());
    }


    public Iterator<Rule> getRules(){
        return rules.iterator();
    }

    public int rulesCount(){
        return rules.size();
    }

    public int factsCount(){
        return facts.size();
    }

    public void printContent(){
        System.out.println("=== FACTS ===");
        for(Map.Entry<String,Predicate> e : facts.entrySet()){
            System.out.print(e.getValue().toString());
        }
        System.out.println("=== RULES ===");
        for(Rule r : rules){
            System.out.println(r);
        }
        System.out.println("TARGET: "+target);
    }

    private static class PredicateIterator implements Iterator<Predicate> {

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
