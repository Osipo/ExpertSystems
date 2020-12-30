package ru.osipov.expertSysLabs.boolParser;

import ru.osipov.expertSysLabs.structures.lists.LinkedStack;

import java.util.Iterator;

public class BoolSyntaxParser {


    public boolean isUnaryOperator(String c){
        switch (c){
            case "~":return true;
            case "not":case "не": return true;
            default: return false;
        }
    }

    public boolean isOperator(String c)
    {
        switch (c)
        {
            case "and":case "и": return true;
            case "&": return true;
            case "|":case "или": return true;
            case "or":return true;
            case "(": return true;
            case ")": return true;
            case "~":return true;
            case "not":case "не": return true;
            default: return false;
        }
    }

    /*Priority of operator.*/
    private int getPriority(String c)
    {
        switch (c)
        {
            case "~": case "not": case "не": return 3;
            case "&": case "and":case "и": return 2;
            case "|": case "or":case "или": return 1;
            case "(": case ")": return 0;
            default: return -1;
        }
    }

    public LinkedStack<String> GetInput(Iterator<String> s){
        LinkedStack<String> ops = new LinkedStack<String>();
        LinkedStack<String> rpn = new LinkedStack<String>();
        while(s.hasNext()){
            String tok = s.next();
            if(tok.equals("(")){
                ops.push(tok);
            }
            else if(tok.equals(")")){
                while(!ops.isEmpty() && !ops.top().equals("(")){
                    rpn.push(ops.top());
                    ops.pop();
                }
                ops.pop();
            }
            else if(!isOperator(tok)){//is operand
                rpn.push(tok);
            }
            else if(isOperator(tok)){
                while(!ops.isEmpty() && isOperator(ops.top()) && getPriority(tok) <= getPriority(ops.top()) ){
                    rpn.push(ops.top());
                    ops.pop();
                }
                ops.push(tok);
            }
        }
        while(!ops.isEmpty()){
            rpn.push(ops.top());
            ops.pop();
        }
        LinkedStack<String> result = new LinkedStack<String>();
        while(!rpn.isEmpty()){
            result.push(rpn.top());
            rpn.pop();
        }
        return result;
    }
}
