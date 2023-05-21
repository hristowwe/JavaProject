import java.io.Serializable;

class Transition implements Serializable  {
    // Клас, представляващ преход в автомата.
    private char symbol; // Символът, с който се осъществява преходът.
    private int nextState; // Състоянието, до което се стига след прехода.

    // Конструктор за инициализиране на символа и следващото състояние.
    public Transition(char symbol, int nextState) {
        this.symbol = symbol;
        this.nextState = nextState;
    }

    // Метод за връщане на символа.
    public char getSymbol() {
        return symbol;
    }

    // Метод за връщане на следващото състояние.
    public int getNextState() {
        return nextState;
    }
}