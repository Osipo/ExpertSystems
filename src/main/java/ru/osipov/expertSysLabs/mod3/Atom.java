package ru.osipov.expertSysLabs.mod3;

public class Atom {
    private String val;//name of variable or value of constant.
    private AtomCategory category;
    public Atom(String v){
        this.val = v;
        this.category = AtomCategory.CONSTANT;
    }

    /* if atoms are constant => check them for equality else return true */
    @Override
    public boolean equals(Object ob){
        if(ob instanceof Atom){
            Atom a2 = (Atom)ob;
            if(a2.category == AtomCategory.CONSTANT && this.category == a2.category){
                return this.getVal().equals(a2.getVal());
            }
            else
                return true;
        }
        return false; /* type of ob is not an Atom */
    }
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void setCategory(AtomCategory category) {
        this.category = category;
    }

    public AtomCategory getCategory() {
        return category;
    }

    public String toString(){
        return (category == AtomCategory.CONSTANT ? '@' : "") +
                val;
    }
}
