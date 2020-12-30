package ru.osipov.expertSysLabs.exe;

import ru.osipov.expertSysLabs.mod3.DataParser;
import ru.osipov.expertSysLabs.mod3.LogicSystemWM;
import ru.osipov.expertSysLabs.structures.lists.LinkedList;

import java.util.ArrayList;
import java.util.Scanner;

public class Lab_3 {
    public static void main(String[] args){
        Scanner inp = new Scanner(System.in);
        LogicSystemWM wm = new LogicSystemWM();
        DataParser f_reader = new DataParser();
        String line;
        System.out.println("Input facts in form: P1(C1, C2 ... CN), P2(C1), ...");
        System.out.println("To finish input type empty string (Just press Enter without typing)");


        /*
        //READ FACTS
        line = inp.nextLine();
        while(line.length() != 0){
            f_reader.parseFact(line, wm);
            line = inp.nextLine();
        }
        System.out.println("Facts were read successful");
        wm.printContent();*/
        String r = "((( F(x, N) | G(M1) ) & O(x,y,z)) | Z(T1)) > P(X)";
        f_reader.parseRule(r, wm);
        System.out.println(r);
    }
}