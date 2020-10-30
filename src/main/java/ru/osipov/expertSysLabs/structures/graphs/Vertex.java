package ru.osipov.expertSysLabs.structures.graphs;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private static long id;
    private String name;
    private String val;//Content of node.



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
            return val.equals(b.val) && name.equals(b.name);
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
}
