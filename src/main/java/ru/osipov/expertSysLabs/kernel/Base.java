package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonArray;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonElement;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonObject;
import ru.osipov.expertSysLabs.jsonParser.jsElements.JsonString;

import java.util.ArrayList;

/**
 * База знаний. Содержит информацию о всех продукциях и условиях.
 */
public class Base {

    /**
     * Строит экземпляр базы знаний на основе JSON-данных.
     * @param data JSON-документ, содержащий информацию о правилах (продукциях)
     * @see JsonObject
     * @author Osipov Oleg Konstantinovich.
     * @throws InvalidBaseFormatException Исключение: неправильное описание данных базы.
     */
    public Base(JsonObject data) throws InvalidBaseFormatException{
        JsonElement rules = data.getElement("rules");
        if(rules instanceof JsonArray){
            ArrayList<JsonElement> rl = ((JsonArray) rules).getElements();
            for(JsonElement r : rl){
                if(r instanceof JsonObject){
                    //PARTS OF RULE.
                    ArrayList<String> premises = new ArrayList<>();//premises of rule.
                    String conclusion = null;
                    String type = null;

                    //EXTRACT premises.
                    JsonElement p = ((JsonObject) r).getElement("premises");
                    if(p instanceof JsonArray){
                        ArrayList<JsonElement> ps = ((JsonArray) p).getElements();
                        for(JsonElement prem : ps){
                            if(prem instanceof JsonString){
                                String premise = ((JsonString) prem).getValue();
                                premises.add(premise);
                            }
                            else
                                throw new InvalidBaseFormatException("Premise must be a string!",null);
                        }
                    }
                    else if(p instanceof JsonString){
                        String prem = ((JsonString) p).getValue();
                        premises.add(prem);
                    }
                    else
                        throw new InvalidBaseFormatException("Value of property \"premises\" must be a string or array!",null);

                    //BUILD premises vertices.

                    //Extract conclusion AND type.
                    JsonElement c = ((JsonObject) r).getElement("conclusion");
                    if(c instanceof JsonString){
                        conclusion = ((JsonString) c).getValue();
                    }
                    else if(c != null){
                        throw new InvalidBaseFormatException("Value of property \"conclusion\" must be a string or null!",null);
                    }

                    JsonElement t = ((JsonObject) r).getElement("type");
                    if(t instanceof JsonString){
                        type  = ((JsonString) t).getValue();
                    }
                    else if(t != null){
                        throw new InvalidBaseFormatException("Value of property \"type\" must be a string or null!",null);
                    }


                }
                else
                    throw new InvalidBaseFormatException("Each rule in \"rules\" array must be an object!",null);
            }
        }
        else
            throw new InvalidBaseFormatException("Value of property \"rules\" must be an array!",null);
    }
}
