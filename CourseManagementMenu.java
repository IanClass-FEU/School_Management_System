import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class CourseManagementMenu {
    private static List<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String COURSES_FILE = "courses.txt";
    private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{3}\\d{3}$");

    public static void courseManagementMenu() {
        loadCoursesFromFile();
        clearScreen();
        boolean exit = false;
        while (!exit) {
        System.out.println(" ______________________________________________");
        System.out.println("|            ┏┓  ┏┓  ┳┳  ┳┓  ┏┓  ┏┓            |");
        System.out.println("|            ┃   ┃┃  ┃┃  ┣┫  ┗┓  ┣             |");
        System.out.println("|            ┗┛  ┗┛  ┗┛  ┛┗  ┗┛  ┗┛            |");
        System.out.println("|                                              |");
        System.out.println("|   ┳┳┓  ┏┓  ┳┓  ┏┓  ┏┓  ┏┓  ┳┳┓  ┏┓  ┳┓  ┏┳┓  |");
        System.out.println("|   ┃┃┃  ┣┫  ┃┃  ┣┫  ┃┓  ┣   ┃┃┃  ┣   ┃┃   ┃   |");
        System.out.println("|   ┛ ┗  ┛┗  ┛┗  ┛┗  ┗┛  ┗┛  ┛ ┗  ┗┛  ┛┗   ┻   |");
        System.out.println("|______________________________________________|");
        System.out.println("|                                              |");
        System.out.println("|    [1] Add Course       [2] View Course      |");
        System.out.println("|                                              |");
        System.out.println("|    [3] Edit Course      [4] Delete Course    |");
        System.out.println("|                                              |");
        System.out.println("|                  [5] Exit                    |");
        System.out.println("|______________________________________________|");
        int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    editCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadCoursesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String courseCode = parts[0];
                    String courseTitle = parts[1];
                    int units = Integer.parseInt(parts[2]);
                    int yearLevel = Integer.parseInt(parts[3]);

                    Course course = new Course(courseCode, courseTitle, units, yearLevel);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from courses file: " + e.getMessage());
        }
    }

    private static void addCourse() {
        System.out.print("Enter course code (3 letters followed by 3 digits): ");
        String courseCode = getCourseCodeInput();

        if (isDuplicateCourse(courseCode)) {
            System.out.println("Course with code " + courseCode + " already exists.");
            return;
        }

        System.out.print("Enter course title: ");
        String courseTitle = getInput();

        System.out.print("Enter number of units: ");
        int units = getIntInput();

        System.out.print("Enter year level: ");
        int yearLevel = getIntInput();

        Course course = new Course(courseCode, courseTitle, units, yearLevel);
        courses.add(course);
        writeCourseToFile(course);

        System.out.println("Course added successfully.");
    }

    private static boolean isDuplicateCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    private static void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\nCourse List:");
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    private static void editCourse() {
        System.out.print("Enter course code to edit: ");
        String courseCode = getCourseCodeInput();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        System.out.print("Enter new course title (" + course.getCourseTitle() + "): ");
        String courseTitle = getInput();
        if (!courseTitle.isEmpty()) {
            course.setCourseTitle(courseTitle);
        }

        System.out.print("Enter new number of units (" + course.getUnits() + "): ");
        int units = getIntInput();
        if (units != 0) {
            course.setUnits(units);
        }

        System.out.print("Enter new year level (" + course.getYearLevel() + "): ");
        int yearLevel = getIntInput();
        if (yearLevel != 0) {
            course.setYearLevel(yearLevel);
        }

        writeCoursesToFile();
        System.out.println("Course information updated successfully.");
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static void deleteCourse() {
        System.out.print("Enter course code to delete: ");
        String courseCode = getCourseCodeInput();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        courses.remove(course);
        writeCoursesToFile();
        System.out.println("Course deleted successfully.");
    }

    private static String getInput() {
        String input = scanner.nextLine().trim();
        return input;
    }

    private static String getCourseCodeInput() {
        String input;
        do {
            input = getInput();
            if (!COURSE_CODE_PATTERN.matcher(input).matches()) {
                System.out.println("Invalid course code format. Please enter 3 letters followed by 3 digits.");
            }
        } while (!COURSE_CODE_PATTERN.matcher(input).matches());
        return input;
    }

    private static int getIntInput() {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(getInput());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return input;
    }

    private static void writeCourseToFile(Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE, true))) {
            writer.write(course.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to courses file: " + e.getMessage());
        }
    }

    private static void writeCoursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : courses) {
                writer.write(course.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to courses file: " + e.getMessage());
        }
    }

    static class Course {
        private String courseCode;
        private String courseTitle;
        private int units;
        private int yearLevel;
    
        public Course(String courseCode, String courseTitle, int units, int yearLevel) {
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.units = units;
            this.yearLevel = yearLevel;
        }
    
        public String getCourseCode() {
            return courseCode;
        }
    
        public String getCourseTitle() {
            return courseTitle;
        }
    
        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }
    
        public int getUnits() {
            return units;
        }
    
        public void setUnits(int units) {
            this.units = units;
        }
    
        public int getYearLevel() {
            return yearLevel;
        }
    
        public void setYearLevel(int yearLevel) {
            this.yearLevel = yearLevel;
        }
    
        @Override
        public String toString() {
            return courseCode + " - " + courseTitle + " - " + units + " - " + yearLevel;
        }
    }
    

    public static void main(String[] args) {
        courseManagementMenu();
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {

        }
    }
}
