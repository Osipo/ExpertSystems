package ru.osipov.expertSysLabs.exe;

import ru.osipov.expertSysLabs.jsonParser.SimpleJsonParser;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonObject;
import ru.osipov.expertSysLabs.kernel.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lab1_2 {
    public static void main(String[] args) {

        //Current working directory
        String e_dir = System.getProperty("user.dir");

        System.out.println("Current working directory: "+e_dir);
        //Path to examples of Labs 1 & 2 from PROJECT DIRECTORY!
        //Working directory MUST BE THE PROJECT DIR!
        e_dir = e_dir + "\\src\\main\\java\\ru\\osipov\\expertSysLabs\\examples\\";
        System.out.println("Examples directory: "+e_dir);
        String e1 = e_dir + "example2.json";
        SimpleJsonParser js = new SimpleJsonParser();
        JsonObject data = js.parse(e1);
        if(data != null){
            System.out.println("Json was read successfull");
            try {
                Base dataBase = new Base(data);
                System.out.println("Data was read.");

                //Make png of built graph.
                //dataBase.makePngFile(e_dir+"example2_data");

                //Resolves the set of applicable rules
                Resolver resolver = new Resolver();
                WorkingMemory wm = new WorkingMemory();

                List<String> WM1 = Arrays.asList("5","6","7","8");
                resolver.initMem(WM1.iterator(), dataBase, wm);
                resolver.setTargets(Collections.singletonList("3").iterator(), dataBase, wm);
                System.out.println("Real mem: "+wm.toString());
                Searcher S = new Searcher();
                SearchResult result = S.search(dataBase, wm);
                System.out.println("Total Result: "+result);

            } catch (InvalidBaseFormatException ex){
                System.out.println("Invalid json data!");
            }
        }
    }
}
