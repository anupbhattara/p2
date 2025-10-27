package fa.nfa;

import java.util.*;
import fa.State;

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
