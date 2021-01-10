package ru.osipov.expertSysLabs.exe;

import ru.osipov.expertSysLabs.mod3.*;
import ru.osipov.expertSysLabs.structures.lists.LinkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Lab_3 {
    public static void main(String[] args){

        String e_dir = System.getProperty("user.dir");
        e_dir = e_dir + "\\src\\main\\java\\ru\\osipov\\expertSysLabs\\mod3\\examples\\";

        String e1 = e_dir + "example1.txt";

        LogicSystemWM wmem = new LogicSystemWM();
        InputReader reader = new InputReader();
        LogicalDeducer deducer = new LogicalDeducer();

        if(!reader.read(e1, wmem)){
            System.out.println("Illegal file format!");
            System.exit(-1);
        }

        wmem.printContent();
        boolean answer = deducer.deduce(reader.getTarget(), wmem, reader.getParser());

        /*
        Scanner inp = new Scanner(System.in);
        LogicSystemWM wm = new LogicSystemWM();
        DataParser f_reader = new DataParser();
        String line;
        System.out.println("Input facts in form: P1(C1, C2 ... CN) | P2(C1) | ...");
        System.out.println("To finish input type empty string (Just press Enter without typing)");



        //READ FACTS
        line = inp.nextLine();
        while(line.length() != 0){
            f_reader.parseFact(line, wm);
            line = inp.nextLine();
        }
        System.out.println("Facts were read successful");
        wm.printContent();
        System.out.println("Input rules in form: P(@x, @y) or P2(D) and P(@x) > S(@x, C2)");
        System.out.println("To finish input type <Enter>");

        line = inp.nextLine();
        HashMap<String ,String> replacements = new HashMap<>();
        while(line.length() != 0){
            f_reader.parseRule(line, wm, replacements);
            line = inp.nextLine();
        }

        System.out.println("Rules were read successful.");
        wm.printContent();

        LogicalDeducer deducer = new LogicalDeducer();
        System.out.println("Input formula to proof: ");
        line = inp.nextLine();

        boolean answer = deducer.deduce(f_reader.parseRule(line, wm, replacements), wm, f_reader);*/
    }
}