package ru.osipov.expertSysLabs.jsonParser;

public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable throwable){
        super(message,throwable);
    }
}
