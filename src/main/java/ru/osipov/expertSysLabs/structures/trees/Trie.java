package ru.osipov.expertSysLabs.structures.trees;

import ru.osipov.expertSysLabs.structures.SingleGenClass;
import ru.osipov.expertSysLabs.structures.lists.LinkedList;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Osipov O.K.
 * @param <Leaf> - the type of the data being stored
 */
public class Trie<Leaf> extends SingleGenClass<Leaf> implements Iterable<Leaf> {

    //Node contains edges to the others which are labeled with character.
    //NEXT node may be either the same (be a map) or be a leaf node which contains the data.
    //Leaf node has type <Leaf> and it can be gained from its parent by edge with label = '$'
    //So '$' symbol is reserved.
    private final Map<Character,Object> _tree;

    public Trie(Class<Leaf> clazz){
        super(clazz);
        this._tree = new TreeMap<>();
    }

    /**
     * @author Osipov O.K.
     * @param wordForm The word which represents path from the root to the leaf node.
     * @param entry a record in datastore.
     */
    public void add(String wordForm, Leaf entry) {
        Map<Character,Object> node = _tree;//current node.
        Map<Character,Object> next = null;//next node (CHILD of current)
        int l = wordForm.length();//the length of the path
        for(int i = 0; i < l; i++){
            char sym = wordForm.charAt(i);
            next = (Map<Character,Object>) node.getOrDefault(sym,null);
            if(next != null){
                node = next;//move to the CHILD.
            }
            else{//add new node, connect it to the current and move to the NEW CHILD.
                Map<Character,Object> c_i = new TreeMap<>();
                node.put(sym,c_i);//current_node['sym'] = c_i
                node = c_i;//current_node = c_i.
            }
        }
        Leaf item = (Leaf) node.getOrDefault('$',null);
        if(item == null){
            item = entry;
            node.put('$',item);
        }
        else{//Override the value
            node.put('$',entry);
        }
    }

    /**
     * @author Osipov O.K.
     * @param word the path in the prefix tree (keyValue)
     * @return Leaf - the actual value in datastore.
     */
    public Leaf getValue(String word){
        if(word == null || word.length() < 1)
            return null;
        Map<Character,Object> node = _tree;//current node.
        Map<Character,Object> next = null;//next node (CHILD of current)
        int l = word.length();//the length of the path
        for(int i = 0; i < l; i++){
            char sym = word.charAt(i);
            next = (Map<Character,Object>) node.getOrDefault(sym,null);
            if(next != null){
                node = next;//move to the CHILD.
            }
            else{
                return null;
            }
        }
        Leaf e = (Leaf) node.getOrDefault('$',null);
        return e;
    }


    /**
     * @author Osipov O.K.
     * @param word the keyValue. WARNING: if it has common prefix with other records
     *             (i.e. we have in store [abc abd bac bad] and we want to remove 'ab')
     *             they shall be deleted too. (i.e. on previos example we will have [bac bad])
     * @return boolean has removed successful?
     */
    public boolean remove(String word){
        if(word == null || word.length() < 1)
            return false;
        Map<Character,Object> node = _tree;//current node.
        Map<Character,Object> next = null;//next node (CHILD of current)
        int l = word.length() - 1;//the length of the path
        for(int i = 0; i < l; i++){
            char sym = word.charAt(i);
            next = (Map<Character,Object>) node.getOrDefault(sym,null);
            if(next != null){
                node = next;//move to the CHILD.
            }
            else{
                return false;
            }
        }
        Map<Character,Object> leaf = (Map<Character,Object>) node.getOrDefault(word.charAt(l),null);
        if(leaf == null)
            return false;
        node.put(word.charAt(l),null);
        return true;
    }

    @Override
    @Nonnull
    /**
     * @author Osipov O.K.
     * Collect only leaf nodes and return iterator of LinkedList
     */
    public Iterator<Leaf> iterator() {

        LinkedList<Leaf> E = new LinkedList<>();//Leaf nodes with data.
        LinkedStack<Object> S = new LinkedStack<>();//STACK of nodes.
        S.push(_tree);
        while(!S.isEmpty()) {
            Object b = S.top();//extract inter-node.
            S.pop();
            Map<Character, Object> children = (Map<Character, Object>) b;
            //add its children to the STACK.
            for (Character k : children.keySet()) {
                if (k == '$')
                    continue;
                S.push(children.get(k));
            }

            //if it has edge with '$' and its value IS NOT NULL.
            if (children.containsKey('$') && children.getOrDefault('$', null) != null){
                E.add((Leaf) children.get('$'));
            }
        }
        return E.iterator();
    }
//
//    @Override
//    @Nonnull
//    public Iterator<Leaf> iterator(){
//        return new TrieIterator();
//    }

    private class TrieIterator implements Iterator<Leaf> {

        private LinkedStack<Object> S;
        TrieIterator(){
            S = new LinkedStack<>();
            S.push(_tree);
        }

        @Override
        public boolean hasNext() {
            return !S.isEmpty();
        }

        //Flush the sequence to the beginning.
        public void flush(){
            this.S = null;
            this.S = new LinkedStack<>();
            S.push(_tree);
        }

        @Override
        public Leaf next() {
            while(!S.isEmpty()) {
                Object b = S.top();//extract inter-node.
                S.pop();
                Map<Character, Object> children = (Map<Character, Object>) b;
                //add its children to the STACK.
                for (Character k : children.keySet()) {
                    if (k == '$')
                        continue;
                    S.push(children.get(k));
                }

                //if it has edge with '$' and its value IS NOT NULL.
                if (children.containsKey('$') && children.getOrDefault('$', null) != null){
                    return (Leaf) children.get('$');
                }
            }
            return null;
        }
    }
}