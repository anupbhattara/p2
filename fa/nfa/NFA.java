package fa.nfa;

import java.util.*;

public class NFA implements NFAInterface {
    
    private Set<NFAState> states;
    private NFAState startState;
    private Set<NFAState> finalStates;
    private Set<Character> sigma;
    
    public NFA() {
        this.states = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
        this.sigma = new LinkedHashSet<>();
        this.startState = null;
    }
    
    @Override
    public boolean addState(String name) {
        // Check if state already exists
        if (getState(name) != null) {
            return false;
        }
        NFAState newState = new NFAState(name);
        states.add(newState);
        return true;
    }
    
    @Override
    public boolean setFinal(String name) {
        NFAState state = getState(name);
        if (state == null) {
            return false;
        }
        finalStates.add(state);
        return true;
    }
    
    @Override
    public boolean setStart(String name) {
        NFAState state = getState(name);
        if (state == null) {
            return false;
        }
        startState = state;
        return true;
    }
    
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }
    
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }
    
    @Override
    public NFAState getState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }
    
    @Override
    public boolean isFinal(String name) {
        NFAState state = getState(name);
        return state != null && finalStates.contains(state);
    }
    
    @Override
    public boolean isStart(String name) {
        NFAState state = getState(name);
        return state != null && state.equals(startState);
    }
    
    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState from = getState(fromState);
        
        // Check if fromState exists
        if (from == null) {
            return false;
        }
        
        // Check if symbol is in alphabet (or is epsilon 'e')
        if (onSymb != 'e' && !sigma.contains(onSymb)) {
            return false;
        }
        
        // Check if all toStates exist
        Set<NFAState> toStateSet = new LinkedHashSet<>();
        for (String toStateName : toStates) {
            NFAState toState = getState(toStateName);
            if (toState == null) {
                return false;
            }
            toStateSet.add(toState);
        }
        
        // Add the transition
        from.addTransition(onSymb, toStateSet);
        return true;
    }
    
    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.toStates(onSymb);
    }
    
    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new LinkedHashSet<>();
        Stack<NFAState> stack = new Stack<>();
        
        stack.push(s);
        closure.add(s);
        
        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            Set<NFAState> epsilonTransitions = current.toStates('e');
            
            if (epsilonTransitions != null) {
                for (NFAState next : epsilonTransitions) {
                    if (!closure.contains(next)) {
                        closure.add(next);
                        stack.push(next);
                    }
                }
            }
        }
        
        return closure;
    }
    
    @Override
    public boolean accepts(String s) {
        // Start with epsilon closure of start state
        Set<NFAState> currentStates = eClosure(startState);
        
        // Process each character in the input string
        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);
            Set<NFAState> nextStates = new LinkedHashSet<>();
            
            // For each current state, find all states reachable on symbol
            for (NFAState state : currentStates) {
                Set<NFAState> reachable = state.toStates(symbol);
                if (reachable != null) {
                    // Add epsilon closure of each reachable state
                    for (NFAState reachableState : reachable) {
                        nextStates.addAll(eClosure(reachableState));
                    }
                }
            }
            
            currentStates = nextStates;
            
            // If no states remain, reject
            if (currentStates.isEmpty()) {
                return false;
            }
        }
        
        // Check if any final state is in current states
        for (NFAState state : currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public int maxCopies(String s) {
        // Start with epsilon closure of start state
        Set<NFAState> currentStates = eClosure(startState);
        int maxCopies = currentStates.size();
        
        // Process each character in the input string
        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);
            Set<NFAState> nextStates = new LinkedHashSet<>();
            
            // For each current state, find all states reachable on symbol
            for (NFAState state : currentStates) {
                Set<NFAState> reachable = state.toStates(symbol);
                if (reachable != null) {
                    // Add epsilon closure of each reachable state
                    for (NFAState reachableState : reachable) {
                        nextStates.addAll(eClosure(reachableState));
                    }
                }
            }
            
            currentStates = nextStates;
            
            // Update max copies
            if (currentStates.size() > maxCopies) {
                maxCopies = currentStates.size();
            }
            
            // If no states remain, we still continue to count
            if (currentStates.isEmpty()) {
                break;
            }
        }
        
        return maxCopies;
    }
    
    @Override
    public boolean isDFA() {
        // Check if there are any epsilon transitions
        for (NFAState state : states) {
            Set<NFAState> epsilonTransitions = state.toStates('e');
            if (epsilonTransitions != null && !epsilonTransitions.isEmpty()) {
                return false;
            }
        }
        
        // Check if each state has at most one transition per symbol
        for (NFAState state : states) {
            for (char symbol : sigma) {
                Set<NFAState> transitions = state.toStates(symbol);
                if (transitions != null && transitions.size() > 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
}