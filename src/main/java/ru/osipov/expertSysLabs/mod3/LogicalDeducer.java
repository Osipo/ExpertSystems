package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.structures.lists.LinkedList;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;

import java.util.*;

public class LogicalDeducer {
    public boolean deduce(PRule formula, LogicSystemWM mem, IDataParser parser){
        Iterator<PRule> r_i = mem.getRules();
        Iterator<String> terms = null;
//        while(r_i.hasNext()){//make copy
//            rules.add(r_i.next());
//        }

        LinkedList<PRule> nRules = new LinkedList<>();
        LinkedList<PRule> activated = new LinkedList<>();
        r_i = null;
        HashMap<String, String> replacements = new HashMap<>();// replace variables with constants
        do {
            nRules.clear();
            activated.clear();
            r_i = mem.getRules();//start new iteration.
            while (r_i.hasNext()) {//check all non-activated rules.
                replacements = new HashMap<>();
                PRule rule = r_i.next();
                System.out.println("Current rule: "+rule);
                System.out.println("Current rule_conclusion: "+rule.getConclusion());
                if (isApplicable(rule, mem, parser, replacements)) {
                    String c = rule.getConclusion();
                    if(c.equalsIgnoreCase("")){// TARGET FOUND.
                        System.out.println("Formula: "+formula+" is deduced.");
                        return true;
                    }
                    if(parser.isFormula(c)){//Conclusion has only predicates.
                        parser.parseFact(c, mem);//Add new Facts.
                    }
                    else {
                        PRule nr = parser.parseRule(rule.getConclusion(), mem, replacements);
                        nRules.add(nr); /* new rule added */
                    }
                    activated.add(rule);/* current applicable rule is marked as activated */
                    //mem.removePRule(rule); /* concurrent exception */
                }
            }//END OF CHECK ALL RULES.

            //DELETE ACTIVATED RULES AND ADD NEW RULES.
            for(PRule nr : nRules){
                mem.addPRule(nr);
            }
            for(PRule ar : activated){
                mem.removePRule(ar);
            }
        } while (mem.rulesCount() > 0);
        activated.clear();
        nRules = null;
        activated = null;
        System.out.println("Cannot deduce formula: "+formula);
        return false;
    }

    private boolean isApplicable(PRule r, LogicSystemWM mem, IDataParser parser, HashMap<String, String> rep)  {
        Iterator<String> terms = r.getFormula();
        Predicate p1 = null;//predicate of rule
        Predicate p2 = null;//predicate of fact.
        String term = null;
        Iterator<Atom> p_a1 = null;
        Iterator<Atom> p_a2 = null;
        LinkedStack<Byte> f_result = new LinkedStack<>();
        while(terms.hasNext()){
            try {
                term = terms.next();
                p1 = parser.parsePredicate(term);
                if(parser.isOperator(term))
                    throw new StringParseException("", -1);
                p2 = mem.getPredicate(p1.getName());
                if(p2 != null && p1.getSize() == p2.getSize()){//names and argc are equal.
                    p_a1 = p1.atomIterator();
                    p_a2 = p2.atomIterator();
                    while(p_a1.hasNext() && p_a2.hasNext()){
                        Atom a1 = p_a1.next();
                        Atom a2 = p_a2.next();
                        if(a1.getCategory() == AtomCategory.CONSTANT && !a1.getVal().equals(a2.getVal())){
                            f_result.push((byte)0);//constant names are not matched
                            break;
                        }
                        //variable => make replacement.
                        else if(a1.getCategory() != AtomCategory.CONSTANT){
                            if(rep.getOrDefault(a1.getVal(), null) == null)
                                rep.put(a1.getVal(), a2.getVal());/* set atom a1 of P to a2. */
                        }
                    }
                    f_result.push((byte)1);
                }
                else //is not a fact.
                    f_result.push((byte)0);
            }
            catch (StringParseException e){//p1 = null. it is not predicate. It may be an operator.
                if(parser.isOperator(term)){//a valid term (operator)
                    //execute an operation and push result to stack (f_result)

                    if(parser.isUnaryOperator(term)){//"NOT" operator
                        byte b = f_result.top();
                        f_result.pop();
                        b = (byte) ((b == (byte)0) ? 1 : 0);
                        f_result.push(b);
                    }
                    else{
                        byte b1 = f_result.top();
                        f_result.pop();
                        byte b2 = f_result.top();
                        f_result.pop();
                        switch (term){
                            case "and": case"&": case"и":
                                f_result.push((byte)(b1 & b2));
                                break;
                            case"or": case "|": case "или":
                                f_result.push((byte)(b1 | b2));
                                break;
                        }
                    }
                }
                else
                    return false;//illegal character sequence => not rule
            }
        }
        return f_result.top() > 0;
    }
}
