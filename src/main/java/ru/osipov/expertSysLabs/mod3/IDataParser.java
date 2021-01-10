package ru.osipov.expertSysLabs.mod3;

import java.util.HashMap;

public interface IDataParser {

    public void parseFact(String line, LogicSystemWM wm);

    PRule parseFormula(String line, LogicSystemWM wm, HashMap<String, String> replacements);
    Predicate parsePredicate(String p) throws StringParseException;

    PRule parseRule(String line, LogicSystemWM wm, HashMap<String, String> replacements);

    boolean isFormula(String line);

    public boolean isOperator(String c);
    public boolean isUnaryOperator(String c);
}
