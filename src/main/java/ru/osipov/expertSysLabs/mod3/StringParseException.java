package ru.osipov.expertSysLabs.mod3;

public class StringParseException extends Exception {
    private int idx;

    public StringParseException(String message, Throwable throwable, int idx){
        super(message, throwable);
        this.idx = idx;
    }

    public StringParseException(String message, int idx){
       this(message, null, idx);
    }

    public int getIdx(){
        return idx;
    }
}
