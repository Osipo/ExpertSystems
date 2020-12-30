package ru.osipov.expertSysLabs.mod3;

import java.util.Iterator;
import java.util.LinkedList;

public class Predicate {
    private String name;
    private LinkedList<Atom> atoms;

    public Predicate(String name){
        this.name = name;
        this.atoms = new LinkedList<>();
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
        if(!atoms.contains(a))
            atoms.add(a);
    }

    public Iterator<Atom> atomIterator(){
        return atoms.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [ ");
        Iterator<Atom> ps = this.atoms.iterator();
        Atom a = null;
        while(ps.hasNext()){
            a = ps.next();
            sb.append(a.getVal()).append(":").append(a.getCategory()).append(' ');
        }

        sb.append("]\n");
        ps = null;
        a = null;
        return sb.toString();
    }
}
