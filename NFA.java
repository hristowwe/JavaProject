import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class NFA implements Serializable  {
    // Клас, представляващ недетерминиран краен автомат.
    private Map<Integer, Set<Transition>> transitions; // Мап, който съхранява преходите на автомата.
    private int initialState; // Началното състояние на автомата.
    private Set<Integer> finalStates; // Множеството от финални състояния на автомата.
    public boolean firstTransition = true;

    // Конструктор за инициализиране на преходите, началното и финалните състояния.
    public NFA() {
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
    }

    // Метод за добавяне на преход към автомата.
    public void addTransition(int currentState, char symbol, int nextState) {
        Transition transition = new Transition(symbol, nextState);

        Set<Transition> currentTransitions = transitions.computeIfAbsent(currentState, k -> new HashSet<>());

        boolean isDuplicate = currentTransitions.stream()
            .anyMatch(t -> t.getSymbol() == symbol && t.getNextState() == nextState);

        if (isDuplicate) {
            System.out.println("Transition already exists: " + currentState + " -> " + nextState + " (" + symbol + ")");
        } else {
            currentTransitions.add(transition);
            firstTransition = false;
            System.out.println("Transition added to NFA");
        }
    }

    // Метод за задаване на началното състояние.
    public void setInitialState(int state) {
        initialState = state;
    }

    // Метод за връщане на началното състояние.
    public int getInitialState() {
        return initialState;
    }

    // Метод за добавяне на финално състояние.
    public void addFinalState(int state) {
        finalStates.add(state);
    }

    // Метод за проверка дали езикът на автомата е празен.
    public boolean isEmptyLanguage() {
        Set<Integer> reachableStates = new HashSet<>();
        exploreStates(initialState, reachableStates);
        return Collections.disjoint(reachableStates, finalStates);
    }

    // Рекурсивен метод за разглеждане на достижимите състояния от дадено състояние.
    private void exploreStates(int currentState, Set<Integer> reachableStates) {
        reachableStates.add(currentState);
        Set<Transition> currentTransitions = transitions.get(currentState);
        if (currentTransitions != null) {
            for (Transition transition : currentTransitions) {
                if (!reachableStates.contains(transition.getNextState())) {
                    exploreStates(transition.getNextState(), reachableStates);
                }
            }
        }
    }

    // Метод за извеждане на преходите в автомата.
    public void printTransitions() {
        for (Map.Entry<Integer, Set<Transition>> entry : transitions.entrySet()) {
            int currentState = entry.getKey();
            Set<Transition> currentTransitions = entry.getValue();
            for (Transition transition : currentTransitions) {
                int nextState = transition.getNextState();
                char symbol = transition.getSymbol();
                System.out.println("Transition: " + currentState + " -> " + nextState + " (" + symbol + ")");
            }
        }
    }
}