package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonArray;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonElement;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonObject;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonString;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.RuleType;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * База знаний. Содержит информацию о всех продукциях и условиях.
 */
public class Base {

    private Set<Vertex> facts;

    private Set<Rule> rules;

    /**
     * Строит экземпляр базы знаний на основе JSON-данных.
     * @param data JSON-документ, содержащий информацию о правилах (продукциях)
     * @see JsonObject
     * @author Osipov Oleg Konstantinovich.
     * @throws InvalidBaseFormatException Исключение: неправильное описание данных базы.
     */
    public Base(JsonObject data) throws InvalidBaseFormatException{
        this.facts = new HashSet<>();
        this.rules = new HashSet<>();
        JsonElement rules = data.getElement("rules");
        if(rules instanceof JsonArray){
            ArrayList<JsonElement> rl = ((JsonArray) rules).getElements();
            for(JsonElement r : rl){
                if(r instanceof JsonObject){
                    //PARTS OF RULE.
                    Set<Vertex> v_premises = new HashSet<>();//premises of rule.
                    String conclusion = null;
                    String type = null;

                    Rule v_r = new Rule(v_premises,null,RuleType.AND);

                    //EXTRACT premises.
                    JsonElement p = ((JsonObject) r).getElement("premises");
                    if(p instanceof JsonArray){
                        ArrayList<JsonElement> ps = ((JsonArray) p).getElements();
                        for(JsonElement prem : ps){
                            if(prem instanceof JsonString){
                                String premise = ((JsonString) prem).getValue();
                                Vertex v_i = new Vertex(premise);
                                v_i.setNext(v_r);
                                v_premises.add(v_i);
                                this.facts.add(v_i);
                            }
                            else
                                throw new InvalidBaseFormatException("Premise must be a string!",null);
                        }
                    }
                    else if(p instanceof JsonString){
                        String prem = ((JsonString) p).getValue();
                        Vertex v_i = new Vertex(prem);
                        v_i.setNext(v_r);
                        v_premises.add(v_i);
                        this.facts.add(v_i);
                    }
                    else
                        throw new InvalidBaseFormatException("Value of property \"premises\" must be a string or array!",null);


                    //Extract conclusion AND type.
                    JsonElement c = ((JsonObject) r).getElement("conclusion");
                    if(c instanceof JsonString){
                        conclusion = ((JsonString) c).getValue();
                        Vertex v_c = new Vertex(conclusion);
                        v_r.setConclusion(v_c);
                        v_c.setPrev(v_r);
                        this.facts.add(v_c);
                    }
                    else if(c != null){
                        throw new InvalidBaseFormatException("Value of property \"conclusion\" must be a string or null!",null);
                    }

                    JsonElement t = ((JsonObject) r).getElement("type");
                    if(t instanceof JsonString){
                        type  = ((JsonString) t).getValue();
                        v_r.setType(RuleType.valueOf(type));
                    }
                    else if(t != null){
                        throw new InvalidBaseFormatException("Value of property \"type\" must be a string or null!",null);
                    }
                    this.rules.add(v_r);
                }
                else
                    throw new InvalidBaseFormatException("Each rule in \"rules\" array must be an object!",null);
            }
        }
        else
            throw new InvalidBaseFormatException("Value of property \"rules\" must be an array!",null);
    }
}
