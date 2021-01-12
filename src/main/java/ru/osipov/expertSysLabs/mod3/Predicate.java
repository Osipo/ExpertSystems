package ru.osipov.expertSysLabs.mod3;

import java.util.Iterator;
import java.util.LinkedList;

public class Predicate {
    private String name;
    private LinkedList<Atom> atoms;
    private boolean val;

    public Predicate(String name){
        this.name = name;
        this.atoms = new LinkedList<>();
        this.val = false;
    }

    public void setVal(boolean f) {
        val = f;
    }

    public boolean getVal() {
        return val;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return atoms.size();
    }

    public void addAtom(Atom a){
        atoms.add(a);
    }

    /* check by name and size (count of variables) */
    @Override
    public boolean equals(Object ob){
        boolean f = true;
        if(ob instanceof Predicate) {
            Predicate p2 = (Predicate) ob;
            if(!this.getName().equals(p2.name) || this.getSize() != p2.getSize())
                return false;

            Iterator<Atom> I1 = atoms.iterator();
            Iterator<Atom> I2 = atoms.iterator();
            Atom a1 = null, a2 = null;
            /* check constants */
            while(I1.hasNext() && I2.hasNext() && f){
                a1 = I1.next();
                a2 = I2.next();
                f = a1.equals(a2);
            }
            if(!f)
                System.out.println(a1 + "is not matched to "+a2);
        }
        else
            f = false;
        return f;
    }

    public Iterator<Atom> atomIterator(){
        return atoms.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("( ");
        Iterator<Atom> ps = this.atoms.iterator();
        Atom a = null;
        while(ps.hasNext()){
            a = ps.next();
            sb.append(a.getVal()).append(":").append(a.getCategory()).append(' ');
        }

        sb.append(")\n");
        ps = null;
        a = null;
        return sb.toString();
    }
}
