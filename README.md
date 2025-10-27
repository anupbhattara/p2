PROJECT 2: Nondeterministic Finite Automata (NFA)
=================================

Authors:
Anup Bhattara
Daniel Aguilar Carranza

---------------------------------
1. PROJECT OVERVIEW
---------------------------------

This project implements a Nondeterministic Finite Automaton (NFA) in Java. The implementation consists of two main classes: fa.nfa.NFA, which models the automaton itself and its behavior, and fa.nfa.NFAState, which models individual states.

The core functionalities implemented are:
1.  NFA construction (adding states, transitions, alphabet).
2.  The epsilon-closure function (eClosure) using **Depth-First Search (DFS)** with a stack.
3.  The NFA acceptance algorithm (accepts) using multi-copy simulation/traversal.
4.  Determining the maximum number of active copies during a trace (maxCopies).
5.  Checking if the NFA is, in fact, a Deterministic Finite Automaton (isDFA).

---------------------------------
3. COMPILATION INSTRUCTIONS
---------------------------------

The project must be compiled from the top directory (the directory containing the 'fa' and 'test' folders) on the 'onyx' server.

***NOTE: JUnit is required for compilation and testing.***

To compile the provided JUnit test file:

$ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java

---------------------------------
4. EXECUTION INSTRUCTIONS
---------------------------------

To run the JUnit test cases, execute the following command (on a single line) from the top directory:

$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.nfa.NFATest

The output will show the results of all test cases (e.g., NFATest1_1, NFATest1_2, etc.), indicating which ones passed and failed. All implemented methods must pass these tests to satisfy the project requirements.


