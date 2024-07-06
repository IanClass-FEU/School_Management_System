import java.io.*;
import java.util.*;
public class EnrollmentSystem {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Enrollment> enrollments = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        displayMenu();
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
    


    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nEnrollment System Menu:");
            System.out.println("1. Enroll a Student");
            System.out.println("2. View List of Enrollees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

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

        Enrollment enrollment = findEnrollment(student);
        if (enrollment == null) {
            enrollment = new Enrollment(student);
            enrollments.add(enrollment);
        }

        System.out.println("Available courses for Year " + student.getYearLevel() + ":");
        for (Course course : courses) {
            if (student.canEnrollCourse(course)) {
                System.out.println(course.getCode() + " - " + course.getName());
            }
        }

        System.out.print("Enter course code to enroll (or 'q' to quit): ");
        String courseCode;
        while (!(courseCode = scanner.nextLine()).equalsIgnoreCase("q")) {
            Course course = findCourse(courseCode);
            if (course != null) {
                enrollment.enrollCourse(course);
            } else {
                System.out.println("Invalid course code.");
            }
            System.out.print("Enter course code to enroll (or 'q' to quit): ");
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
    

    private static Enrollment findEnrollment(Student student) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)) {
                return enrollment;
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

    private static void viewEnrollees() {
        System.out.println("\nList of Enrollees:");
        for (Enrollment enrollment : enrollments) {
            Student student = enrollment.getStudent();
            System.out.println("Student: " + student.getId() + " - " + student.getName());
            System.out.println("Enrolled Courses:");
            for (Course course : enrollment.getEnrolledCourses()) {
                System.out.println("  " + course.getCode() + " - " + course.getName());
            }
            System.out.println();
        }
    }
}