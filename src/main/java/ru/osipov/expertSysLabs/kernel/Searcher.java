package ru.osipov.expertSysLabs.kernel;

import ru.osipov.expertSysLabs.structures.graphs.Comparators;
import ru.osipov.expertSysLabs.structures.graphs.Rule;
import ru.osipov.expertSysLabs.structures.graphs.Vertex;
import ru.osipov.expertSysLabs.structures.lists.LinkedQueue;
import ru.osipov.expertSysLabs.structures.lists.LinkedStack;
import ru.osipov.expertSysLabs.structures.lists.Store;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Searcher {

    private Store<Vertex> O_V;//opened list of facts.
    private Store<Rule> O_R;//opened list of rules.

    private NavigableSet<Vertex> C_V;//closed set of facts.
    private NavigableSet<Rule> C_R;//closed set of rules.


    private NavigableSet<Vertex> target;//target.

    private SearchResult state;

    private Resolver rslv;

    public Searcher() {
        this(SearchMode.BFS);
        this.state = SearchResult.INITIAL;
        this.rslv = new Resolver();
    }

    private Searcher(SearchMode m){
        switch (m){
            case BFS:{
                O_V = new LinkedQueue<>();
                O_R = new LinkedQueue<>();
                break;
            }
            case DFS:{
                O_V = new LinkedStack<>();
                O_R = new LinkedStack<>();
                break;
            }
            default:{ //by default Breadth First Search
                O_V = new LinkedQueue<>();
                O_R = new LinkedQueue<>();
                break;
            }
        }
        C_R = new TreeSet<>(Comparators::compareRules);
        C_V = new TreeSet<>(Comparators::compareVertices);
        this.target = new TreeSet<>(Comparators::compareVertices);
    }

    //Search by data.
    public SearchResult search(Base data, WorkingMemory mem){
        if(state != SearchResult.INITIAL){
            System.out.println("Cannot execute operation. You must flush data of previous search!");
            return SearchResult.ERR;
        }

        state = SearchResult.NOT_FOUND;
        long i = 1;//iteration.
        while(true){
            rslv.getApplicableRules(data, mem);//put applicable rules from nodes of wm.
            //put only not executed rules.
            System.out.println(mem);
            for(Rule r : mem.getCRules()){
                if(!C_R.contains(r) && !O_R.contains(r))
                     O_R.insert(r);
            }
            //and puts only those nodes that are not visited.
            for(Vertex v : mem.getVertices()){
                if(!C_V.contains(v) && !O_V.contains(v))
                    O_V.insert(v);
            }
            if(O_R.size() == 0) {
                state = SearchResult.NOT_EXISTED;
                return SearchResult.NOT_EXISTED;
            }
            Rule c_r = O_R.get();//get element from store (if STACK => top() else if QUEUE => front())
            O_R.delete();//delete executed rule.
            C_R.add(c_r);//and backtrack for that rule.
            Vertex exit = c_r.getConclusion();
            if(mem.getTargets().contains(exit)) {
                state = SearchResult.FOUND;
                System.out.println(i+": "+this.toString());
                return SearchResult.FOUND;
            }
            if(!C_V.contains(exit)) {//IF not visited
                exit.setActive(true);
                mem.getVertices().add(exit);//add to memory
                C_V.add(exit);//and mark it as visited.
            }
            System.out.println(i+": "+this.toString());
            i++;
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Opened_V\t\tClosed_V\tOpened_R\tClosed_R\n");
        Iterator<Vertex> i = O_V.iterator();
        sb.append("[ ");
        while (i.hasNext()){
            sb.append(i.next().getName()+" ");
        }
        sb.append("]");
        sb.append('\t');
        i = C_V.iterator();
        sb.append("[ ");
        while (i.hasNext()){
            sb.append(i.next().getName()).append(" ");
        }
        sb.append("]");
        sb.append('\t');
        Iterator<Rule> r = O_R.iterator();
        sb.append("[ ");
        while (r.hasNext()){
            sb.append(r.next().getName()).append(" ");
        }
        sb.append("]");
        sb.append('\t');
        r = C_R.iterator();
        sb.append("[ ");
        while (r.hasNext()){
            sb.append(r.next().getName()).append(" ");
        }
        sb.append("]");
        sb.append('\t');
        i = null;
        r = null;
        return sb.toString();
    }
}
