package ru.osipov.expertSysLabs.mod3;

public interface ILogicalDataParser {
    public void parseFact(String line, LogicSystemStore store);
    Predicate parsePredicate(String p) throws StringParseException;
    Rule parseRule(String line, LogicSystemStore store);
}
