import java.io.*;
import java.util.*;

public class EnrollmentSystem {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> enrolledStudents = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        loadEnrollees();
        displayMenu();
        saveEnrollees();
    }

    private static void loadData() {
        try (BufferedReader studentReader = new BufferedReader(new FileReader("students.txt"));
             BufferedReader courseReader = new BufferedReader(new FileReader("courses.txt"))) {
            loadStudents(studentReader);
            loadCourses(courseReader);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private static void loadCourses(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String code = parts[0].trim();
                String name = parts[1].trim();
                int units = Integer.parseInt(parts[2].trim());
                int yearLevel = Integer.parseInt(parts[3].trim());
                Course course = new Course(code, name, units, yearLevel);
                courses.add(course);
            }
        }
    }

    private static void loadStudents(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                String id = parts[0].trim();
                String name = parts[1].trim() + " " + parts[2].trim();
                int yearLevel = Integer.parseInt(parts[8].trim());
                Student student = new Student(id, name, yearLevel);
                students.add(student);
            }
        }
    }

    public static void displayMenu() {
        loadData();
        loadEnrollees();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            clearScreen();
            System.out.println(" ______________________________________________");
            System.out.println("|    ┏┓  ┳┓  ┳┓  ┏┓  ┓   ┓   ┳┳┓  ┏┓  ┳┓  ┏┳┓  |");
            System.out.println("|    ┣   ┃┃  ┣┫  ┃┃  ┃   ┃   ┃┃┃  ┣   ┃┃   ┃   |");
            System.out.println("|    ┗┛  ┛┗  ┛┗  ┗┛  ┗┛  ┗┛  ┛ ┗  ┗┛  ┛┗   ┻   |");
            System.out.println("|                                              |");
            System.out.println("|           ┏┓  ┓┏  ┏┓  ┏┳┓  ┏┓  ┳┳┓           |");
            System.out.println("|           ┗┓  ┗┫  ┗┓   ┃   ┣   ┃┃┃           |");
            System.out.println("|           ┗┛  ┗┛  ┗┛   ┻   ┗┛  ┛ ┗           |");
            System.out.println("|______________________________________________|");
            System.out.println("|                                              |");
            System.out.println("| [1] Enroll Student      [2] View Enrollees   |");
            System.out.println("|                                              |");
            System.out.println("|                 [3] Exit                     |");
            System.out.println("|______________________________________________|");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                viewUnenrolledStudents();
                    enrollStudent(scanner);
                    break;
                case 2:
                    viewEnrollees();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private static void enrollStudent(Scanner scanner) {
        boolean continueEnrollment = true;
    
        while (continueEnrollment) {
            System.out.print("Enter student ID or name (or 'q' to quit): ");
            String input = scanner.nextLine();
    
            if (input.equalsIgnoreCase("q")) {
                return; // Exit the method and return to the main menu
            }
    
            Student student = findStudent(input);
    
            if (student == null) {
                System.out.println("Student not found.");
                continue; // Skip to the next iteration of the loop
            }
    
            if (enrolledStudents.contains(student)) {
                System.out.println("Student is already enrolled.");
                continue; // Skip to the next iteration of the loop
            }
    
            // Create a List to store unique courses
            List<Course> availableCourses = new ArrayList<>();
    
            for (Course course : courses) {
                if (student.canEnrollCourse(course) && student.getYearLevel() == course.getYearLevel()) {
                    boolean isDuplicate = false;
                    for (Course existingCourse : availableCourses) {
                        if (existingCourse.getCode().equals(course.getCode())) {
                            isDuplicate = true;
                            break;
                        }
                    }
    
                    // Add the course to the list if it's not a duplicate
                    if (!isDuplicate) {
                        availableCourses.add(course);
                    }
                }
            }
    
            System.out.println("Available courses for Year " + student.getYearLevel() + ":");
            System.out.printf("%-10s %-30s %s%n", "Code", "Name", "Units");
            System.out.println("-----------------------------------");
    
            for (Course course : availableCourses) {
                System.out.printf("%-10s %-30s %d%n", course.getCode(), course.getName(), course.getUnits());
            }
    
            System.out.println();
    
            boolean enrollmentComplete = false;
            while (!enrollmentComplete) {
                System.out.print("Enter course code to enroll (or 'q' to quit, 's' to save): ");
                String courseCode = scanner.nextLine();
    
                if (courseCode.equalsIgnoreCase("q")) {
                    continueEnrollment = false; // Exit the outer loop and return to the main menu
                    break;
                } else if (courseCode.equalsIgnoreCase("s")) {
                    enrolledStudents.add(student);
                    System.out.println("Enrollment saved for " + student.getName());
                    saveEnrollees();
                    enrollmentComplete = true;
                } else {
                    Course course = findCourse(courseCode);
                    if (course != null) {
                        student.enrollCourse(course);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                }
            }
        }
    }


    private static Student findStudent(String input) {
        String lowercaseInput = input.toLowerCase();
        for (Student student : students) {
            if (student.getId().toLowerCase().equals(lowercaseInput) ||
                student.getName().toLowerCase().equals(lowercaseInput)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static void loadEnrollees() {
        try (BufferedReader reader = new BufferedReader(new FileReader("enrollees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String studentId = parts[0];
                    String studentName = parts[1];
                    int yearLevel = Integer.parseInt(parts[2]);
                    Student student = findStudent(studentId);
                    if (student == null) {
                        student = new Student(studentId, studentName, yearLevel);
                        students.add(student);
                    }
                    for (int i = 3; i < parts.length; i++) {
                        Course course = findCourse(parts[i]);
                        if (course != null) {
                            student.enrollCourse(course);
                        }
                    }
                    enrolledStudents.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading enrollees: " + e.getMessage());
        }
    }

    private static void viewEnrollees() {
        clearScreen();
        System.out.println("\nList of Enrollees:");
    
        // Create a Set to store unique students
        Set<Student> uniqueStudents = new HashSet<>(enrolledStudents);
    
        for (Student student : uniqueStudents) {
            System.out.println("Student: " + student.getId() + " - " + student.getName());
            System.out.println("Enrolled Courses:");
            for (Course course : student.getEnrolledCourses()) {
                System.out.println("  " + course.getCode() + " - " + course.getName());
            }
            System.out.println();
        }
    
        pauseScreen();
    }

    private static void viewUnenrolledStudents() {
        clearScreen();
        System.out.println("\nList of Unenrolled Students:");
    
        // Create a Set to store unique students
        Set<Student> unenrolledStudents = new HashSet<>(students);
        unenrolledStudents.removeAll(enrolledStudents);
    
        // Remove any duplicate entries from the unenrolledStudents set
        unenrolledStudents = new HashSet<>(unenrolledStudents);
    
        if (unenrolledStudents.isEmpty()) {
            System.out.println("All students are currently enrolled.");
        } else {
            for (Student student : unenrolledStudents) {
                System.out.println("Student: " + student.getId() + " - " + student.getName() + ", Year Level: " + student.getYearLevel());
            }
        }
    
        System.out.println();
    }
    
    
    
    
    

    private static void saveEnrollees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("enrollees.txt"))) {
            for (Student student : enrolledStudents) {
                StringBuilder line = new StringBuilder(student.toString());
                for (Course course : student.getEnrolledCourses()) {
                    line.append(",").append(course.getCode());
                }
                writer.write(line.toString());
                writer.newLine();
            }
            System.out.println("Enrollees saved to enrollees.txt");
        } catch (IOException e) {
            System.err.println("Error saving enrollees: " + e.getMessage());
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
