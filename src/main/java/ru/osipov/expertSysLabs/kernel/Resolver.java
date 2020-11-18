package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * By the current state (the set of real content i.e. realFacts)
 * computes the set of rules which can be applied.
 * @see Rule
 * @see Base
 * @author Osipov O.K.
 */
public class Resolver {

    /**Get set of rules which can be applied from current state.
     * @param realFacts The current state => content of the working memory.
     * @param data The knowledge base with facts and rules.
    */
    public NavigableSet<Rule> getApplicableRules(Iterator<String> realFacts, Base data, WorkingMemory mem){
        NavigableSet<Rule> result = new TreeSet<>(Comparators::compareRules);
        while(realFacts.hasNext()){
            String val_i = realFacts.next();
            FactEntry f = data.getFacts().getValue(val_i);
            if(f != null){
                if(f.getPremise() != null){
                    checkVertex(f.getPremise(),val_i,mem);
                }
                if(f.getConclusion() != null){
                    checkVertex(f.getConclusion(),val_i,mem);
                }
            }
        }
        for(Rule r : data.getRules()){
            if(r.isApplicable()) {
                result.add(r);
                mem.getCRules().add(r);
            }
        }
        return result;
    }

    //set active on vertices with content = val.
    private void checkVertex(Vertex v, String val, WorkingMemory mem){
        boolean f = v.getValue().equals(val);
        if(f) {
            v.setActive(v.getValue().equals(val));
            mem.getVertices().add(v);
        }
    }
}
