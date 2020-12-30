package ru.osipov.expertSysLabs.mod3;

public class Atom {
    private String val;
    private AtomCategory category;
    public Atom(String v){
        this.val = v;
        this.category = AtomCategory.CONSTANT;
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
}
