package ru.osipov.expertSysLabs.structures.graphs;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    protected static long id = -1L;
    protected String name;
    private String val;//Content of node.

    private Vertex prev;
    private Vertex next;

    public Vertex(){
        this.name = "V_" + (++id);
        this.val = null;
    }

    public Vertex(String val){
        this.name = "V_" + (++id);
        this.val = val;
    }
    public Vertex(String name, String val){
        this.name = name;
        this.val = val;
    }


    public String getName(){
        return name;
    }

    public void setValue(String v){
        this.val = v;
    }

    public String getValue(){
        return val;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Vertex) {
            Vertex b = (Vertex) obj;
            return val.equals(b.val);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }
    @Override
    public String toString(){
        return this.name;
    }

    public void setPrev(Vertex v){
        this.prev = v;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setNext(Vertex next) {
        this.next = next;
    }

    public Vertex getNext() {
        return next;
    }
}
