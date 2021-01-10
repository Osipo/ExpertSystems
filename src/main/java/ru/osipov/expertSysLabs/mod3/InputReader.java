package ru.osipov.expertSysLabs.mod3;

import java.io.*;

public class InputReader {
    private IDataParser parser;
    private PRule target;

    public InputReader(){
        this.parser = new DataParser();
        this.target = null;
    }
    public boolean read(String fname, LogicSystemWM wm){
        try(BufferedReader fr = new BufferedReader(new FileReader(fname))){
            String line = fr.readLine();
            while(line != null){
                if(line.equalsIgnoreCase("facts"))
                    line = parseFacts(fr, wm);
                else if(line.equalsIgnoreCase("rules"))
                    line = parseRules(fr, wm);
                else if(line.equalsIgnoreCase("target"))
                    line = parseTarget(fr, wm);
                else
                    line = fr.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file with name: "+fname);
            return false;
        } catch (IOException e) {
            System.out.println("Cannot read from file.");
            return false;
        }
        return true;
    }

    private String parseTarget(BufferedReader fr, LogicSystemWM wm) {
        String res = null;
        try{
            res = fr.readLine();
            this.target = parser.parseFormula(res, wm, null);
        } catch (IOException e) {
            System.out.println("Cannot process target");
            res = "IOERR";
        }
        return res;
    }

    public PRule getTarget(){
        return target;
    }

    public IDataParser getParser(){
        return parser;
    }


    private String parseFacts(BufferedReader fr, LogicSystemWM wm)  {
        String res = null;
        try {
            String line = fr.readLine();
            res = line;
            while (line != null && line.length() > 0) {
                parser.parseFact(line, wm);
                line = fr.readLine();
            }
        }catch (IOException e){
            System.out.println("Cannot process fact");
            res = "IOERR";
        }
        return res;
    }

    private String parseRules(BufferedReader fr, LogicSystemWM wm)  {
        String res = null;
        try {
            String line = fr.readLine();
            while (line != null && line.length() > 0) {
                parser.parseRule(line, wm, null);
                line = fr.readLine();
            }
        }catch (IOException e){
            System.out.println("Cannot process rule");
            res = "IOERR";
        }
        return res;
    }
}
