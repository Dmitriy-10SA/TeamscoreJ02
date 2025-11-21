package dublicates;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;
            DuplicatesCheck duplicatesCheck = new DuplicatesCheck();
            do {
                System.out.print("Введите текст (или stop, если хотите закончить): ");
                userInput = scanner.nextLine();
                try {
                    duplicatesCheck.addOrThrow(userInput);
                } catch (AlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            } while (!userInput.equals("stop"));
        }
    }
}
