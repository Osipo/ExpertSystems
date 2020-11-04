package ru.osipov.expertSysLabs.structures.graphs;

/**
 * @author Osipov O.K.
 * A wrapper class for specific type T
 * @param <T> the name of the type to be wrapped.
 */
public class Elem<T> {
    private T v1;

    public Elem(T v){
        this.v1 = v;
    }

    public void setV1(T v1) {
        this.v1 = v1;
    }

    public T getV1() {
        return v1;
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        Elem<T> p = null;
        try{
            p = (Elem<T>)o;
        }
        catch (ClassCastException e){
            return false;
        }
        return p.getV1().equals(v1);
    }

    @Override
    public int hashCode(){
        return v1.toString().hashCode();
    }
}
