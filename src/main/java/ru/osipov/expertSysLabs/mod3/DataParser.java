package ru.osipov.expertSysLabs.mod3;

import ru.osipov.expertSysLabs.boolParser.BoolSyntaxParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParser implements IDataParser{
    private long nameId = 1;
    private BoolSyntaxParser parser = new BoolSyntaxParser();

    public BoolSyntaxParser getParser(){
        return parser;
    }

    @Override
    public boolean isOperator(String c){
        return parser.isOperator(c);
    }

    @Override
    public boolean isUnaryOperator(String c) {
        return parser.isUnaryOperator(c);
    }

    @Override
    public void parseFact(String line, LogicSystemWM wm){
        String[] ps = line.split("[|]");//All facts are in form: P1(C1, C2) | P2(C1, C2 ... CN) | ... PN(C1)
        Predicate pr = null;
        for(String p : ps){
            try {
                pr = parsePredicate(p);
                wm.addPredicate(pr);
            }
            catch (StringParseException e){
                System.out.println(e.getMessage() + " at "+ e.getIdx()+" symbol");
            }
        }
    }

    @Override
    public boolean isFormula(String line){
        int sep = line.indexOf('>');
        if(sep == -1)
            return true;
        else return line.substring(sep + 1).length() > 0;
    }

    @Override
    public PRule parseFormula(String line, LogicSystemWM wm, HashMap<String, String> replacements){
        //Find predicates at formula.
        Pattern p = Pattern.compile("([^()&|~, ]+\\([^()&|~]+\\))+");
        Matcher m = p.matcher(line);

        //Replace predicates with names (for RPN computation)
        HashMap<String,String> vars = new HashMap<>();//Predicate names in boolean formula.
        int i = 0;
        String k = null;
        while(m.find()){
            k = "P_" + i;
            vars.put(k, m.group());
            //System.out.println(k+" : "+m.group());
            line = line.replace(m.group(), k);
            i++;
        }

        //System.out.println(premises);

        //Compute Reverse Polish Notation of formula which represents a premises of the rule.
        ArrayList<String> terms = new ArrayList<>();//formula terms
        p = Pattern.compile("(P_[0-9]+)|([()|&])");//split on boolean terms (pseudo_names, and operators)
        m = p.matcher(line);
        while(m.find())
            terms.add(m.group());
        String f_rpn = this.parser.GetInput(terms.iterator()).toString();

        String[] f_comps = f_rpn.split("\\s+");
        String[] formula = new String[f_comps.length - 2];

        //Return original predicates and truncate [ and ] symbols from f_comps
        for(int fi = 1; fi < f_comps.length - 1; fi++){
            formula[fi - 1] = vars.getOrDefault(f_comps[fi],f_comps[fi]);

            //REPLACE VARIABLES WITH ITS REPLACEMENTS
            if(replacements != null) {
                for (String var : replacements.keySet()) {
                    formula[fi - 1] = formula[fi - 1].replace(var, replacements.getOrDefault(var, var));
                }
            }
        }
        f_comps = null;

        //Now we have well-formed formula
        PRule rule = new PRule(formula, "");
        wm.addPRule(rule);//save new rule into Working memory
        return rule;
    }
    @Override
    public PRule parseRule(String line, LogicSystemWM wm, HashMap<String, String> replacements){

        //separate conclusion and premises
        int sep = line.indexOf('>');
        String conclusion = line.substring(sep + 1);
        String premises = line.substring(0, sep);


        //Find predicates at formula.
        Pattern p = Pattern.compile("([^()&|~, ]+\\([^()&|~]+\\))+");
        Matcher m = p.matcher(premises);

        //Replace predicates with names (for RPN computation)
        HashMap<String,String> vars = new HashMap<>();//Predicate names in boolean formula.
        int i = 0;
        String k = null;
        while(m.find()){
            k = "P_" + i;
            vars.put(k, m.group());
            //System.out.println(k+" : "+m.group());
            premises = premises.replace(m.group(), k);
            i++;
        }

        //System.out.println(premises);

        //Compute Reverse Polish Notation of formula which represents a premises of the rule.
        ArrayList<String> terms = new ArrayList<>();//formula terms
        p = Pattern.compile("(P_[0-9]+)|([()|&])");//split on boolean terms
        m = p.matcher(premises);
        while(m.find())
            terms.add(m.group());
        String f_rpn = this.parser.GetInput(terms.iterator()).toString();

        String[] f_comps = f_rpn.split("\\s+");
        String[] formula = new String[f_comps.length - 2];

        //Return original predicates and truncate [ and ] symbols from f_comps
        for(int fi = 1; fi < f_comps.length - 1; fi++){
            formula[fi - 1] = vars.getOrDefault(f_comps[fi],f_comps[fi]);

            //REPLACE VARIABLES WITH ITS REPLACEMENTS
            if(replacements != null) {
                for (String var : replacements.keySet()) {
                    formula[fi - 1] = formula[fi - 1].replace(var, replacements.getOrDefault(var, var));
                }
            }
        }
        f_comps = null;

        //Now we have well-formed formula
        PRule rule = new PRule(formula, conclusion);
        wm.addPRule(rule);//save new rule into Working memory
        return rule;
    }

    @Override
    public Predicate parsePredicate(String p) throws StringParseException{
        StringBuilder sb = new StringBuilder();
        System.out.println(p);
        Predicate pr = null;
        char c;
        char pt; // param type;
        for(int i = 0; i < p.length(); i++){
            c = p.charAt(i);
            if(i == 0 && c == '('){
                sb.append("P_").append(nameId);
                nameId++;
                pr = new Predicate(sb.toString());
                sb.delete(0,sb.length());//flush sb buffer for new name
            }
            else if(c == '(' && pr == null){
                pr = new Predicate(sb.toString());
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

}
