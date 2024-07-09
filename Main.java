import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int maxChoice = 5;

        displayIntro();

        do {
            clearScreen();
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > maxChoice) {
                    clearScreen();
                    displayMenu();
                    System.out.println("Invalid choice. Please enter a number between 1 and " + maxChoice + ".");
                    pauseScreen();
                } else {
                    handleChoice(choice, scanner);
                }
            } catch (InputMismatchException e) {
                clearScreen();
                displayMenu();
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                pauseScreen();
            }
        } while (true);
    }


    private static void displayIntro() {
System.out.println(" ______________________________________________");
System.out.println("|       ┓ ┏  ┏┓  ┓   ┏┓  ┏┓  ┳┳┓  ┏┓           |");
System.out.println("|       ┃┃┃  ┣   ┃   ┃   ┃┃  ┃┃┃  ┣            |");
System.out.println("|       ┗┻┛  ┗┛  ┗┛  ┗┛  ┗┛  ┛ ┗  ┗┛           |");
System.out.println("|                                              |");
System.out.println("|                 ┏┳┓  ┏┓                      |");
System.out.println("|                  ┃   ┃┃                      |");
System.out.println("|                  ┻   ┗┛                      |");
System.out.println("|                                              |");
System.out.println("| ┏┳  ┏┓  ┓┏  ┏┓  ┓ ┏  ┏┓  ┏┓  ┓┏┓  ┏┓  ┏┓  ┏┓ |");
System.out.println("|  ┃  ┣┫  ┃┃  ┣┫  ┃┃┃  ┃┃  ┃   ┃┫   ┣   ┣   ┏┛ |");
System.out.println("| ┗┛  ┛┗  ┗┛  ┛┗  ┗┻┛  ┗┛  ┗┛  ┛┗┛  ┗┛  ┗┛  ┗┛ |");
System.out.println("|______________________________________________|");
pauseScreen();
}


    private static void displayMenu() {
        System.out.println(" ______________________________________________");
        System.out.println("|                                              |");
        System.out.println("| ┏┳  ┏┓  ┓┏  ┏┓  ┓ ┏  ┏┓  ┏┓  ┓┏┓  ┏┓  ┏┓  ┏┓ |");
        System.out.println("|  ┃  ┣┫  ┃┃  ┣┫  ┃┃┃  ┃┃  ┃   ┃┫   ┣   ┣   ┏┛ |");
        System.out.println("| ┗┛  ┛┗  ┗┛  ┛┗  ┗┻┛  ┗┛  ┗┛  ┛┗┛  ┗┛  ┗┛  ┗┛ |");
        System.out.println("|          School Management System            |");
        System.out.println("|______________________________________________|");
        System.out.println("|                                              |");
        System.out.println("|       [1] Enrolment     [2] Student          |");
        System.out.println("|                                              |");
        System.out.println("|       [3] Schedule      [4] Course           |");
        System.out.println("|                                              |");
        System.out.println("|              [5] Exit Program                |");
        System.out.println("|                                              |");
        System.out.println("|______________________________________________|");
    }

    private static void handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                EnrollmentSystem.displayMenu();
                break;
            case 2:
                clearScreen();
                StudentManagementMenu.studentManagementMenu();
                break;
            case 3:
                clearScreen();
                ScheduleManagementMenu.scheduleManagementMenu();
                break;
            case 4:
                CourseManagementMenu.courseManagementMenu();
                break;
            case 5:
                System.out.println("Are you sure you want to exit the program? (y/n)");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("y")) {
                    System.out.println("Exiting program...");
                    System.exit(0);
                } if (confirmation.equalsIgnoreCase("n")){
                    clearScreen();
                    displayMenu();
                    System.out.println("Exiting cancelled.");
                    pauseScreen();
                }
                else {
                    System.out.println("Invalid Input. Exiting cancelled.");
                    pauseScreen();
                }
                break;
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
