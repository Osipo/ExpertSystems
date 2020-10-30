package ru.osipov.expertSysLabs.jsonParser.jsElements;

public class JsonNull extends JsonElement<String> {
    @Override
    public String getValue() {
        return "null";
    }
}
