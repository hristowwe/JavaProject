import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class NFAProgram {
    public static void main(String[] args) {

        NFAFileManager nfaFileManager = new NFAFileManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                break;
            }

            String[] parts = command.split("\\s+", 6);
            String operation = parts[0];

            switch (operation) {
                case "open":
                    if (parts.length >= 2) {
                        String filename = parts[1];
                        nfaFileManager.open(filename);
                    } else {
                        System.out.println("Invalid command. Usage: open <filename>");
                    }
                    break;
                    case "create":
          if (parts.length >= 3) {
                String id = parts[1];
                NFA nfa = new NFA();
             nfa.setInitialState(Integer.parseInt(parts[2]));
                nfaFileManager.nfas.put(id, nfa);
                 System.out.println("NFA created with ID: " + id);
              } else {
                  System.out.println("Invalid command. Usage: create <id> <initial state>");
                  }
                 break;
                case "save":
    if (parts.length >= 3) {
        String id = parts[1];
        String filename = parts[2];
        NFA nfa = nfaFileManager.nfas.get(id);
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
    } else {
        System.out.println("Invalid command. Usage: save <id> <filename>");
    }
    break;
                case "list":
                    nfaFileManager.list();
                    break;
                case "print":
                    if (parts.length >= 2) {
                        String id = parts[1];
                        nfaFileManager.print(id);
                    } else {
                        System.out.println("Invalid command. Usage: print <id>");
                    }
                    break;
                case "empty":
                    if (parts.length >= 2) {
                        String id = parts[1];
                        nfaFileManager.empty(id);
                    } else {
                        System.out.println("Invalid command. Usage: empty <id>");
                    }
                    break;
                    case "transition":
    if (parts.length >= 5) {
    
        String id = parts[1];
        NFA nfa = nfaFileManager.nfas.get(id);
       if (nfa != null) {
         int currentState;
         Integer currect = Integer.parseInt(parts[2]);
        if(nfa.firstTransition && nfa.getInitialState() != currect) {
          currentState = nfa.getInitialState();
        }
        else
        {
           currentState = Integer.parseInt(parts[2]);
        }
        char symbol = parts[3].charAt(0);
        int nextState = Integer.parseInt(parts[4]);
  
            nfa.addTransition(currentState, symbol, nextState);
            if(checkState(scanner, nextState))
                nfa.addFinalState(nextState);

          } else {
            System.out.println("NFA with ID " + id + " not found.");
            }
          } else {
            System.out.println("Invalid command. Usage: transition <id> <current state> <symbol> <next state>");
         }
         break;
                default:
                    System.out.println("Invalid command. Type 'help' for a list of available commands.");
                    break;
            }
        }

        scanner.close();
    }

   public static boolean checkState(Scanner scanner, Integer state) {
    while (true) {
        System.out.print("Do you want this (" + state + ") integer to be the final state? (Y/N): ");
        String answer = scanner.next().trim().toLowerCase();
        scanner.nextLine(); 
        if (answer.equals("y")) {
            return true;
        } else if (answer.equals("n")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }
    }
}
}
