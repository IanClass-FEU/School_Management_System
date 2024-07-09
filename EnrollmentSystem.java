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
        clearScreen();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
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
        System.out.print("Enter student ID or name: ");
        String input = scanner.nextLine();
        Student student = findStudent(input);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (enrolledStudents.contains(student)) {
            System.out.println("Student is already enrolled.");
            return;
        }

        System.out.println("Available courses for Year " + student.getYearLevel() + ":");
        for (Course course : courses) {
            if (student.canEnrollCourse(course)) {
                System.out.println(course.getCode() + " - " + course.getName());
            }
        }

        boolean enrollmentComplete = false;
        while (!enrollmentComplete) {
            System.out.print("Enter course code to enroll (or 'q' to quit, 's' to save): ");
            String courseCode = scanner.nextLine();

            if (courseCode.equalsIgnoreCase("q")) {
                return;
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
        System.out.println("\nList of Enrollees:");
        for (Student student : enrolledStudents) {
            System.out.println("Student: " + student.getId() + " - " + student.getName());
            System.out.println("Enrolled Courses:");
            for (Course course : student.getEnrolledCourses()) {
                System.out.println("  " + course.getCode() + " - " + course.getName());
            }
            System.out.println();
        }
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

}
