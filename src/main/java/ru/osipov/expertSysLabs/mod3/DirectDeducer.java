package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.structures.lists.LinkedList;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;

import java.util.*;

public class DirectDeducer {
    public boolean canDeduce(LogicSystemStore store){
        Predicate t = store.getTarget();

        Set<Predicate> deduced = new HashSet<>();

        boolean result = false;

        LinkedStack<Predicate> S = new LinkedStack<>();
        LinkedStack<Rule> R = new LinkedStack<>();

        //pseudo-rule: target > target
        Rule empty = new Rule();
        empty.addPredicate(t);
        empty.setConclusion(t);
        R.push(empty);
        S.push(t);
        S.push(new Predicate(">"));
        S.push(t);
        Rule c_r = null;
        while(!S.isEmpty()){
            Predicate subT = S.top();
            c_r = R.top();
            System.out.println("Current subTarget: "+subT);
            System.out.println("Current STACK: "+S.toString());
            System.out.println("Current rule: "+c_r);
            System.out.println("Deduced: "+ deduced);
            S.pop();
            result = inFact(store, subT, c_r, S, R, deduced);
            if(!result){
                c_r = R.top();
                System.out.println("Sub_target: "+subT+" cannot be deduced!");
                System.out.println("From rule: "+c_r);

                System.out.println("Try to find another rule with conclusion.");
                while(!S.isEmpty() && !S.top().getName().equalsIgnoreCase("=")){
                    S.pop();
                }
                if(!S.isEmpty()){
                    S.pop();
                    R.pop();
                    store.removeRule(c_r);
                }
                else{
                    System.out.println("There are no rules to prove sub_target: "+subT);
                    return false;
                }
                return false;
            }
        }

        return result;
    }


    private boolean inFact(LogicSystemStore store, Predicate p, Rule r, LinkedStack<Predicate> S, LinkedStack<Rule> R, Set<Predicate> deduced){
        //has reached conclusion.
        if(p.getName().equalsIgnoreCase(">")){
            Predicate c = S.top();
            S.pop();
            c.setVal(true); //deduced.
            deduced.add(c);
            System.out.println("Predicate " + c + " was deduced from rule.");
            R.pop();//exit from rule.
            //It is subtarget
            if(!S.isEmpty() && S.top().getName().equalsIgnoreCase("=")){
                S.pop();
                Predicate t = S.top();
                S.pop();
                System.out.println("All subtargets were deduced for predicate " + t);
                HashMap<String, String> rep = replacements(t, c);
                r = R.top();//goto to the connected rule.
                replaceVars(r, rep);
                t.setVal(true);
                deduced.add(t);
            }
            return true;
        }

        //check facts first.
        else if(store.getPredicate(p.getName() + p.getSize()) != null){
            Predicate p2 = store.getPredicate(p.getName() + p.getSize());
            if(p.equals(p2)) {//predicates are equal when they have same names, argc, and they constants are equal.
                System.out.println("Predicate " + p + "is in facts => It is TRUE");
                deduced.add(p);
                //COMPUTE AND MAKE REPLACEMENTS AT RULE.
                HashMap<String, String> rep = replacements(p, p2);
                replaceVars(r, rep);
                return true;
            }
            else {
                Iterator<Rule> rules = store.getRules();
                System.out.println("Predicate " + p +" is not a fact. Searching in rules");
                boolean f = false;
                while(rules.hasNext() && !f) {
                    r = rules.next();
                    f = checkRule(store, r, p, S, R);
                }
                return f;
            }
        }
        else{
            Iterator<Rule> rules = store.getRules();
            System.out.println("Predicate " + p +" is not a fact. Searching in rules");
            boolean f = false;
            while(rules.hasNext() && !f) {
                r = rules.next();
                f = checkRule(store, r, p, S, R);
            }
            return f;
        }
    }

    private boolean checkRule(LogicSystemStore store, Rule r, Predicate target, LinkedStack<Predicate> S, LinkedStack<Rule> R){
        Predicate conc = r.getConclusion();
        if(target.equals(conc)){ /* found at rule */
            System.out.println("Found at rule " + r + "as conclusion");
            S.push(target);
            S.push(new Predicate("="));
            S.push(conc);
            S.push(new Predicate(">"));
            for(int i = r.getPremises().size() - 1; i >= 0; i--)
                S.push(r.getPremises().get(i));
            R.push(r);
            return true;
        }
        return false;
    }

    /* Compute new replacements */
    private HashMap<String, String> replacements(Predicate p1, Predicate p2){
        Iterator<Atom> I1 = p1.atomIterator();
        Iterator<Atom> I2 = p2.atomIterator();
        HashMap<String, String> replacemnets = new HashMap<>();
        Atom a_i1 = null, a_i2 = null;
        while(I1.hasNext() && I2.hasNext()){
            a_i1 = I1.next();
            a_i2 = I2.next();
            if(a_i2.getCategory() == AtomCategory.CONSTANT && a_i1.getCategory() == AtomCategory.VARIABLE)
                replacemnets.put(a_i1.getVal(), '@'+a_i2.getVal());
            else if(a_i2.getCategory() == a_i1.getCategory() && a_i1.getCategory() == AtomCategory.VARIABLE){
                System.out.println(a_i1.getVal() + "is connected with "+a_i2.getVal());
            }
        }
        return replacemnets;
    }

    /* make replacements at rule */
    private void replaceVars(Rule r, HashMap<String, String> replacements){
        for(Predicate p : r.getPremises()){
            Iterator<Atom> I = p.atomIterator();
            while(I.hasNext()){
                Atom a_i = I.next();
                String nv = replacements.getOrDefault(a_i.getVal(), a_i.getVal());
                if(nv.charAt(0) == '@') {
                    a_i.setCategory(AtomCategory.CONSTANT);
                    a_i.setVal(nv.substring(1));
                }
                else
                    a_i.setVal(nv);
            }
        }
        Iterator<Atom> I = r.getConclusion().atomIterator();
        while(I.hasNext()){
            Atom a_i = I.next();
            String nv = replacements.getOrDefault(a_i.getVal(), a_i.getVal());
            if(nv.charAt(0) == '@') {
                a_i.setCategory(AtomCategory.CONSTANT);
                a_i.setVal(nv.substring(1));
            }
            else
                a_i.setVal(nv);
        }
    }
}
