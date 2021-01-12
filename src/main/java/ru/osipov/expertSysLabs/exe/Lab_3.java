package ru.osipov.expertSysLabs.exe;

import ru.osipov.expertSysLabs.mod3.*;

public class Lab_3 {
    public static void main(String[] args){

        String e_dir = System.getProperty("user.dir");
        e_dir = e_dir + "\\src\\main\\java\\ru\\osipov\\expertSysLabs\\mod3\\examples\\";

        String e1 = e_dir + "example1.txt";

        InputReader reader = new InputReader();
        LogicSystemStore store = new LogicSystemStore();

        if(!reader.read(e1, store)){
            System.out.println("Illegal file format!");
            System.exit(-1);
        }

        store.printContent();

        System.out.println("\n=== DEDUCE TARGET ===");
        System.out.println("Target is " + store.getTarget());

        DirectDeducer deducer = new DirectDeducer();
        deducer.canDeduce(store);
        //S = [t, >, t] R = [{t > t}]
        //S = [A, W, S, H, >, tv, =, t, >, t] R = [{t > t}, {A W S H > tv}] R_c = [A W S H > tv]
        //S = [W, S, H, >, tv, =, t, >, t] R = [{t > t}, {A W S H > tv}] R_c = [A W S H > tv]
        //S = [M, >, w, =, W, S, H, >, tv, =, t, >, t] R = [{t > t}, {A W S H > tv}, {M > w}] R_c = [M > w]
        //S = [>, w, =, W, S, H, >, tv, =, t, >, t] R = [{t > t}, {A W S H > tv}, {M > w}] R_c = [M > w]
        //S = [S, H, >, tv, =, t, >, t] R = [{t > t}, {A W S H > tv}] R_c = [A W S H > tv]
    }
}