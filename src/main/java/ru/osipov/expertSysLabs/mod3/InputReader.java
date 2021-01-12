package ru.osipov.expertSysLabs.mod3;

import java.io.*;

/* Read data from file and save them to the LogicalSystemStore */
public class InputReader {
    private ILogicalDataParser parser;

    public InputReader(){
        this.parser = new SimpleLogicalDataParser();
    }
    
    public boolean read(String fname, LogicSystemStore store){
        try(BufferedReader fr = new BufferedReader(new FileReader(fname))){
            String line = fr.readLine();
            while(line != null){
                //System.out.println("line = "+line);
                if(line.equalsIgnoreCase("facts"))
                    line = parseFacts(fr, store);
                else if(line.equalsIgnoreCase("rules"))
                    line = parseRules(fr, store);
                else if(line.equalsIgnoreCase("target"))
                    line = parseTarget(fr, store);
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

    private String parseTarget(BufferedReader fr, LogicSystemStore store) {
        String res = null;
        try{
            res = fr.readLine();
            Predicate p = parser.parsePredicate(res);
            store.setTarget(p);
        } catch (IOException | StringParseException e) {
            System.out.println("Cannot process target");
            res = "IOERR";
        }
        return res;
    }

    private String parseFacts(BufferedReader fr, LogicSystemStore store)  {
        String res = null;
        try {
            String line = fr.readLine();
            while (line != null && line.length() > 0) {
                res = line;
                parser.parseFact(line, store);
                line = fr.readLine();
            }
        }catch (IOException e){
            System.out.println("Cannot process fact");
            res = "IOERR";
        }
        return res;
    }

    private String parseRules(BufferedReader fr, LogicSystemStore store)  {
        String res = null;
        try {
            String line = fr.readLine();
            while (line != null && line.length() > 0) {
                res = line;
                parser.parseRule(line, store);
                line = fr.readLine();
            }
        }catch (IOException e){
            System.out.println("Cannot process rule");
            res = "IOERR";
        }
        return res;
    }
}
