package ru.osipov.expertSysLabs.kernel;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonArray;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonElement;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonObject;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonString;
import ru.osipov.expertSysLabs.structures.graphs.Comparators;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.RuleType;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;
import ru.osipov.expertSysLabs.structures.trees.Trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Knowledge base.
 */
public class Base {

    private Trie<FactEntry> facts;
    private NavigableSet<Rule> rules;
    /**
     * Build an istance of Base from JsonObject.
     *
     * @param data read JsonObject from read json-file.
     * @see JsonObject
     * @author Osipov O.K.
     * @throws InvalidBaseFormatException Exception: invalid description of base from json-file.
     */
    public Base(JsonObject data) throws InvalidBaseFormatException{
        this.facts = new Trie<>(FactEntry.class);
        this.rules = new TreeSet<>(Comparators::compareRules);
        JsonElement rules = data.getElement("rules");
        if(rules instanceof JsonArray){
            ArrayList<JsonElement> rl = ((JsonArray) rules).getElements();
            for(JsonElement r : rl){
                if(r instanceof JsonObject){
                    //optional PARTS OF RULE.
                    String conclusion = null;
                    String type = null;

                    //CREATE AND ADD NEW RULE.
                    Rule v_r = new Rule();
                    this.rules.add(v_r);

                    //EXTRACT premises.
                    JsonElement p = ((JsonObject) r).getElement("premises");
                    if(p instanceof JsonArray){
                        ArrayList<JsonElement> ps = ((JsonArray) p).getElements();
                        for(JsonElement prem : ps){
                            if(prem instanceof JsonString){
                                String premise = ((JsonString) prem).getValue();
                                checkPremise(v_r, premise);
                            }
                            else
                                throw new InvalidBaseFormatException("Premise must be a string!",null);
                        }
                    }
                    else if(p instanceof JsonString){
                        String premise = ((JsonString) p).getValue();
                        checkPremise(v_r, premise);
                    }
                    else
                        throw new InvalidBaseFormatException("Value of property \"premises\" must be a string or array!",null);


                    //Extract conclusion AND type.
                    JsonElement c = ((JsonObject) r).getElement("conclusion");
                    if(c instanceof JsonString){
                        conclusion = ((JsonString) c).getValue();

                        FactEntry entry = this.facts.getValue(conclusion);
                        if(entry != null){
                            if(entry.getConclusion() != null){
                                v_r.setConclusion(entry.getConclusion());
                            }
                            else{
                                Vertex v_c = new Vertex(conclusion);
                                entry.setConclusion(v_c);//fact is not only a premise. tell base that it is also a conclusion of some rule.
                                v_r.setConclusion(v_c);
                            }
                        }
                        else{
                            Vertex v_c = new Vertex(conclusion);
                            FactEntry record = new FactEntry(null,v_c);
                            this.facts.add(conclusion,record);
                            v_r.setConclusion(v_c);
                        }
                    }
                    else if(c != null){
                        throw new InvalidBaseFormatException("Value of property \"conclusion\" must be a string or not be specified!",null);
                    }

                    JsonElement t = ((JsonObject) r).getElement("type");
                    if(t instanceof JsonString){
                        type  = ((JsonString) t).getValue();
                        v_r.setType(RuleType.valueOf(type));
                    }
                    else if(t != null){
                        throw new InvalidBaseFormatException("Value of property \"type\" must be a string or not be specified!",null);
                    }
                }
                else
                    throw new InvalidBaseFormatException("Each rule in \"rules\" array must be an object!",null);
            }
        }
        else
            throw new InvalidBaseFormatException("Value of property \"rules\" must be an array!",null);
    }

    private void checkPremise(Rule v_r, String premise){

        FactEntry entry = this.facts.getValue(premise);
        //If it is a well known fact and it is a premise and conclusion of some rule
        if(entry != null && entry.getPremise() != null && entry.getConclusion() != null){
            entry.getPremise().addRule(v_r);
            entry.getConclusion().addRule(v_r);
            v_r.getPremises().add(entry.getPremise());
            v_r.getPremises().add(entry.getConclusion());
        }
        //If it is a well known fact and it is a premise of some rule.
        else if(entry != null && entry.getPremise() != null){
            entry.getPremise().addRule(v_r);//connect new rule to that premise.
            v_r.getPremises().add(entry.getPremise());
        }
        //If it is a well known fact and it is the conclusion of some rule.
        else if(entry != null && entry.getConclusion() != null){
            entry.getConclusion().addRule(v_r);
            entry.setPremise(entry.getConclusion());//mark it as a premise.
            v_r.getPremises().add(entry.getConclusion());
        }
        //new fact in base and it is a premise.
        else{
            Vertex v_i = new Vertex(premise);
            FactEntry record = new FactEntry(v_i,null);
            this.facts.add(premise,record);
            v_r.getPremises().add(v_i);
            v_i.addRule(v_r);
        }
    }

    public Trie<FactEntry> getFacts(){
        return facts;
    }

    public NavigableSet<Rule> getRules(){return rules;}

    /**
     * Creates a png image of content of the Base (digraph image)
     * @param fname the name of the new file.
     */
    public void makePngFile(String fname) throws IOException {
        File f = new File(fname);
        if(f.lastModified() != 0){
            System.out.println("Cannot write to existing file!");
            return;
        }
        try (FileWriter fw = new FileWriter(f,true);){
            fw.write("digraph g1 "+" {\n");
            for(Rule r : this.rules){
                //write premises nodes
                for(Vertex p : r.getPremises()){
                    fw.write(p.getName());
                    fw.write("[ label=\"");
                    fw.write(p.getName()+"\\n"+p.getValue()+"\"");
                    fw.write(",color=\"blue\", shape=\"circle\"];\n");
                }
                if(r.getConclusion() != null){
                    //write conclusion node
                    fw.write(r.getConclusion().getName());
                    fw.write("[ label=\"");
                    fw.write(r.getConclusion().getName()+"\\n"+r.getConclusion().getValue()+"\"");
                    fw.write(",color=\"blue\", shape=\"circle\"];\n");
                }

                //write rule node
                fw.write(r.getName());
                fw.write("[ label=\"");
                fw.write(r.getName()+"\\n"+r.getType()+"\"");
                fw.write(",color=\"black\", shape=\"square\"");
                fw.write("];\n");

                //write connections of premises
                for(Vertex p : r.getPremises()){
                    fw.write(p.getName());
                    fw.write(" -> ");
                    fw.write(r.getName());
                    fw.write(";\n");
                }

                //write conclusion connection.
                if(r.getConclusion() != null) {
                    fw.write(r.getName());
                    fw.write(" -> ");
                    fw.write(r.getConclusion().getName());
                    fw.write(";\n");
                }
            }
            fw.write("}");
        }
        catch (FileNotFoundException e){
            System.out.println("Cannot open file to write.");
        } catch (IOException e) {
            System.out.println("Cannot write to file");
        }
        //render dot file to png (image)
        Graphviz.fromFile(f).render(Format.PNG).toFile(new File(fname+"_img.png"));
    }
}