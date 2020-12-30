package ru.osipov.expertSysLabs.mod3;

public interface IDataParser {
    Predicate parsePredicate(String p) throws StringParseException;
    void parseRule(String line, LogicSystemWM wm);
    public boolean isOperator(String c);
    public boolean isUnaryOperator(String c);
}
