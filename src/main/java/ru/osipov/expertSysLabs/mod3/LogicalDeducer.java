package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.structures.lists.LinkedList;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;

import java.util.*;

public class LogicalDeducer {
    public boolean deduce(PRule formula, LogicSystemWM mem, IDataParser parser){
        LinkedList<PRule> rules = new LinkedList<>();
        Iterator<PRule> r_i = mem.getRules();
        Iterator<String> terms = null;
        while(r_i.hasNext()){//make copy
            rules.add(r_i.next());
        }


        boolean isAdded;
        r_i = rules.iterator();
        HashMap<String, String> replacements = new HashMap<>();//replace variables with constants
        do {
            isAdded = false;
            while (r_i.hasNext()) {//check all rules.
                replacements = new HashMap<>();
                PRule rule = r_i.next();
                if (isApplicable(rule, mem, parser, replacements)) {
                    isAdded = true;
                    parser.parseRule(rule.getConclusion(), mem /* , replacements */);
                }
            }//END OF CHECK ALL RULES.

        } while (isAdded);
        return false;
    }

    private boolean isApplicable(PRule r, LogicSystemWM mem, IDataParser parser, HashMap<String, String> rep)  {
        Iterator<String> terms = r.getFormula();
        Predicate p1 = null;
        Predicate p2 = null;
        String term = null;
        Iterator<Atom> p_a1 = null;
        Iterator<Atom> p_a2 = null;
        LinkedStack<Byte> f_result = new LinkedStack<>();
        while(terms.hasNext()){
            try {
                term = terms.next();
                p1 = parser.parsePredicate(term);
                p2 = mem.getPredicate(p1.getName());
                if(p2 != null && p1.getSize() == p2.getSize()){//names and argc are equal.
                    p_a1 = p1.atomIterator();
                    p_a2 = p2.atomIterator();
                    while(p_a1.hasNext() && p_a2.hasNext()){
                        Atom a1 = p_a1.next();
                        Atom a2 = p_a2.next();
                        if(a1.getCategory() == AtomCategory.CONSTANT && !a1.getVal().equals(a2.getVal())){
                            f_result.push((byte)0);
                            break;
                        }
                        else if(a1.getCategory() != AtomCategory.CONSTANT){
                            rep.put(a1.getVal(),a2.getVal());
                        }
                    }
                    f_result.push((byte)1);
                }
                else
                    f_result.push((byte)0);
            }
            catch (StringParseException e){//p1 = null.
                if(parser.isOperator(term)){//a valid term
                    //execute a operation and push result to stack (f_result)

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
