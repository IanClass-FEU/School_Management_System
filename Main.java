import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int maxChoice = 5;

        do {
            clearScreen();
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > maxChoice) {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + maxChoice + ".");
                    pauseScreen();
                } else {
                    handleChoice(choice, scanner);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                pauseScreen();
            }
        } while (true);
    }

    private static void displayMenu() {
        System.out.println("\nWelcome to FEU!");
        System.out.println("1. Enrolment");
        System.out.println("2. Student Management");
        System.out.println("3. Schedule Management");
        System.out.println("4. Course Management");
        System.out.println("5. Exit Program");
    }

    private static void handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                EnrollmentSystem.displayMenu(); // Call the EnrollmentSystem menu
                break;
            case 2:
                StudentManagementMenu.studentManagementMenu();
                break;
            case 3:
                ScheduleManagementMenu.scheduleManagementMenu();
                break;
            case 4:
                CourseManagementMenu.courseManagementMenu();
                break;
            case 5:
                System.out.println("Exiting program...");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {

        }
    }

    private static void pauseScreen() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {

        }
    }
}
