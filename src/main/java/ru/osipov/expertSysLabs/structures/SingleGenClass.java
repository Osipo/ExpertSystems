package ru.osipov.expertSysLabs.structures;

/**
* @author Osipov O.K.
* The marker for classes with single type parameter T1
*/
public class SingleGenClass<T1> {
    private final Class<T1> type;

    public SingleGenClass(Class<T1> clazz){
        this.type = clazz;
    }

    public Class<T1> getGenType(){
        return type;
    }
}
