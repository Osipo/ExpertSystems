package ru.osipov.expertSysLabs.exe;

import ru.osipov.expertSysLabs.jsonParser.SimpleJsonParser;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonObject;
import ru.osipov.expertSysLabs.kernel.Base;
import ru.osipov.expertSysLabs.kernel.InvalidBaseFormatException;

public class App {
    public static void main(String[] args) {
        String e_dir = System.getProperty("user.dir");
        e_dir = e_dir + "\\src\\main\\java\\ru\\osipov\\expertSysLabs\\examples\\";
        System.out.println("Examples directory: "+e_dir);
        String e1 = e_dir + "example1.json";
        SimpleJsonParser js = new SimpleJsonParser();
        JsonObject data = js.parse(e1);
        if(data != null){
            System.out.println("Json was read successfull");
            try {
                Base dataBase = new Base(data);
                System.out.println("Data was read.");
                dataBase.makePngFile(e_dir+"example1_data");
            } catch (InvalidBaseFormatException ex){
                System.out.println("Invalid json data!");
            }

        }
    }
}
