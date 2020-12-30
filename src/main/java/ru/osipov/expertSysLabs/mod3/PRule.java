package ru.osipov.expertSysLabs.mod3;

import java.util.Arrays;
import java.util.Iterator;

public class PRule {
    private String[] formula;
    private String conclusion;
    public PRule(String[] formula, String conclusion){
        this.formula = formula;
        this.conclusion = conclusion;
    }

    public Iterator<String> getFormula() {
        return Arrays.asList(formula).iterator();
    }

    public String getConclusion(){
        return conclusion;
    }

    @Override
    public int hashCode(){
        StringBuilder sb = new StringBuilder();
        for(String p : formula){
            sb.append(p);
        }
        sb.append(conclusion);
        return sb.toString().hashCode();
    }



    @Override
    public boolean equals(Object o){
        if(o instanceof PRule){
            PRule r2 = (PRule)o;
            boolean f = true;
            Iterator<String> I1 = this.getFormula();
            Iterator<String> I2 = r2.getFormula();
            while(I1.hasNext() && I2.hasNext()){
                f = I1.next().equals(I2.next());
            }
            return f;
        }
        return false;
    }
}
