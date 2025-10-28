package fa.nfa;

import java.util.*;
import fa.State;

/**
 * Represents a state in an NFA.
 * Stores transitions to other states on symbols including epsilon.
 * 
 * @author Anup Bhattarai and Daniel Aguilar Carranza
 */
public class NFAState extends State {
    
    private Map<Character, Set<NFAState>> transitions;
    
    public NFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
    }
    
    public void addTransition(char onSymb, Set<NFAState> toStates) {
        if (!transitions.containsKey(onSymb)) {
            transitions.put(onSymb, new LinkedHashSet<>());
        }
        transitions.get(onSymb).addAll(toStates);
    }
    

    public Set<NFAState> toStates(char symb) {
        return transitions.get(symb);
    }
}
