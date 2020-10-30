package ru.osipov.expertSysLabs.kernel;

public class InvalidBaseFormatException extends Exception{
    public InvalidBaseFormatException(String msg, Throwable t){
        super(msg,t);
    }
}
