import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class NFAFileManager implements Serializable  {
    // Клас, отговарящ за управление на файловете, свързани с недетерминирани краени автомати.
    protected Map<String, NFA> nfas; // Мап, който съхранява свързването между идентификаторите и автоматите.
    private int nextId; // Следващият свободен идентификатор.

    // Конструктор за инициализиране на мапа и идентификатора.
    public NFAFileManager() {
        nfas = new HashMap<>();
        nextId = 1;
    }

    // Метод за отваряне на автомат от файл.
    public void open(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            NFA nfa = (NFA) inputStream.readObject();
            String id = "NFA-" + nextId++;
            nfas.put(id, nfa);
            System.out.println("Opened NFA with ID: " + id);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error opening NFA: " + e.getMessage());
        }
    }

    // Метод за запис на автомат във файл.
    public void save(String id, String filename) {
        NFA nfa = nfas.get(id);
        if (nfa != null) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                outputStream.writeObject(nfa);
                System.out.println("NFA saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving NFA: " + e.getMessage());
            }
        } else {
            System.out.println("NFA with ID " + id + " not found.");
        }
    }

    // Метод за извеждане на списъка с наличните автомати.
    public void list() {
        System.out.println("Available NFAs:");
        for (String id : nfas.keySet()) {
            System.out.println(id);
        }
    }

    // Метод за извеждане на преходите на даден автомат.
    public void print(String id) {
        NFA nfa = nfas.get(id);
        if (nfa != null) {
            nfa.printTransitions();
        } else {
            System.out.println("NFA with ID " + id + " not found.");
        }
    }

    // Метод за проверка дали езикът на автомата е празен.
    public void empty(String id) {
        NFA nfa = nfas.get(id);
        if (nfa != null) {
            boolean isEmpty = nfa.isEmptyLanguage();
            System.out.println("Language is empty: " + isEmpty);
        } else {
            System.out.println("NFA with ID " + id + " not found.");
        }
    }
}
