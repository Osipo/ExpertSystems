package ru.osipov.expertSysLabs.structures.graphs;

public class Comparators {
    public static int compareRules(Rule r1, Rule r2){
        long c1 = Long.parseLong(r1.getName().substring(2));
        long c2 = Long.parseLong(r2.getName().substring(2));
        return Long.compare(c1,c2);
    }

    public static int compareVertices(Vertex v1, Vertex v2){
        long c1 = Long.parseLong(v1.getName().substring(2));
        long c2 = Long.parseLong(v2.getName().substring(2));
        return Long.compare(c1,c2);
    }

    public static int compareStrings(String s1, String s2){
        return s1.compareTo(s2);
    }
}
