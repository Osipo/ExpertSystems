package ru.osipov.expertSysLabs.jsonParser.jsElements;

public abstract class JsonElement<T> {
    public abstract T getValue();

    @Override
    public String toString(){
        return getValue().toString();
    }

}
