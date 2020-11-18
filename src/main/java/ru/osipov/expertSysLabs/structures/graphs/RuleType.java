package ru.osipov.expertSysLabs.structures.graphs;

/**
 * @author Osipov O.K.
 * A type of rule (AND, OR, NOT)
 * If all vertices are active => AND == true
 * If at least one vertex is active => OR == true (AND == false, NOT == false)
 * If no one vertex is active => NOT == true (AND == false, OR == false)
 */
public enum RuleType {
    AND,OR,NOT;
}
