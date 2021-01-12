package ru.osipov.expertSysLabs.mod3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleLogicalDataParser implements ILogicalDataParser {

    private long nameId = 1;

    @Override
    public void parseFact(String line, LogicSystemStore store){
        String[] ps = line.split("[|]");//All facts are in form: P1(C1, C2) | P2(C1, C2 ... CN) | ... PN(C1)
        Predicate pr = null;
        for(String p : ps){
            try {
                pr = parsePredicate(p, true);
                store.addPredicate(pr);
            }
            catch (StringParseException e){
                System.out.println(e.getMessage() + " at "+ e.getIdx()+" symbol");
            }
        }
    }

    @Override
    public Predicate parsePredicate(String p, boolean isTrue) throws StringParseException{
        StringBuilder sb = new StringBuilder();
        //System.out.println(p);
        Predicate pr = null;
        char c;
        char pt; // param type;
        for(int i = 0; i < p.length(); i++){
            c = p.charAt(i);
            if(i == 0 && c == '('){
                sb.append("P_").append(nameId);
                nameId++;
                pr = new Predicate(sb.toString());
                pr.setVal(isTrue);
                sb.delete(0,sb.length());//flush sb buffer for new name
            }
            else if(c == '(' && pr == null){
                pr = new Predicate(sb.toString());
                pr.setVal(isTrue);
                sb.delete(0, sb.length());//flush sb buffer for new name
            }
            else if(c == ',' || c == ')'){
                String v = sb.toString();
                pt = v.charAt(0);
                sb.delete(0,sb.length());//flush sb buffer for new name
                if(pr != null){
                    AtomCategory cat = (pt == '@') ? AtomCategory.VARIABLE : AtomCategory.CONSTANT;
                    v = (pt == '@') ? v.substring(1) : v;
                    Atom m = new Atom(v);
                    //System.out.println(p+" : "+v);
                    m.setCategory(cat);
                    pr.addAtom(m);
                }
                else
                    throw new StringParseException("Cannot read predicate at", i);
            }
            else if(c == ' ') {
                //Nothing to do. Skip space symbol and continue the cycle.
            }
            else
                sb.append(c);
        }
        return pr;
    }

    @Override
    public Rule parseRule(String line, LogicSystemStore store) {
        //separate conclusion and premises
        int sep = line.indexOf('>');
        String conclusion = line.substring(sep + 1);
        String premises = line.substring(0, sep);


        Rule rule = new Rule();
        //Find predicates at formula.
        Pattern p = Pattern.compile("([^()&|~, ]+\\([^()&|~]+\\))+");
        Matcher m = p.matcher(premises);

        Predicate rp_i = null;
        while(m.find()){
            String m_i = m.group();
            try {
                rp_i = parsePredicate(m_i, false);
                rule.addPredicate(rp_i);
                //System.out.println(rp_i);
            }
            catch (StringParseException e){
                System.out.println("Illegal string format of predicate: "+m_i);
                System.out.println("Skip this element and continue.");
            }
        }
        try{
            rule.setConclusion(parsePredicate(conclusion, false));
        } catch (StringParseException e) {
            System.out.println("Illegal string format of predicate: "+conclusion);
            System.out.println("Cannot create predicate for conclusion. Set conclusion to null");
            rule.setConclusion(null);
        }
        store.addRule(rule);//save new rule into Working memory
        return rule;
    }
}
