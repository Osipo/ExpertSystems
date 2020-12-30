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

    /**
     * Check that nodes with values from realFacts are exists in knowledge base.
     * And add these found nodes to memory.
     * Also compute all applicable rules from these
     * @param realFacts initial source data
     * @param data The knowledge base.
     * @param mem The working memory
     */
    public void initMem(Iterator<String> realFacts, Base data, WorkingMemory mem){
        while(realFacts.hasNext()){//Search these facts at Base and set nodes active (n.active => true)
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
        getApplicableRules(data, mem);
    }
    /**Get set of rules which can be applied from current state.
     * @param mem The working memory.
     * @param data The knowledge base with facts and rules.
    */
    public void getApplicableRules(Base data, WorkingMemory mem){
        //NavigableSet<Rule> result = new TreeSet<>(Comparators::compareRules);

        //check all rules. And save all applicable rules to working memory.
        for(Rule r : data.getRules()){
            if(r.isApplicable()) {
                //result.add(r);
                mem.getCRules().add(r);
            }
        }
        //return result; //return a list of current applicable rules.
    }

    //set active on vertices with content = val.
    private void checkVertex(Vertex v, String val, WorkingMemory mem){
        boolean f = v.getValue().equals(val);
        if(f) {
            v.setActive(true);
            mem.getVertices().add(v);
        }
    }

    private void checkTarget(Vertex v, String val, WorkingMemory mem){
        boolean f = v.getValue().equals(val);
        if(f) {
            v.setActive(true);
            mem.getTargets().add(v);
        }
    }

    public void setTargets(Iterator<String> targets, Base db, WorkingMemory mem){
        while(targets.hasNext()) {
            String val_i = targets.next();
            FactEntry f = db.getFacts().getValue(val_i);
            if (f != null) {
                if (f.getPremise() != null) {//check if premise_node contains value
                    checkTarget(f.getPremise(), val_i, mem);
                }
                if (f.getConclusion() != null) {//check if conclusion_node contains value
                    checkTarget(f.getConclusion(), val_i, mem);
                }
            }
        }
    }
}
