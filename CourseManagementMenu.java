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
            clearScreen();
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
        System.out.println("Enter your choice: ");
        int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    pauseScreen();
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
        clearScreen();
        System.out.print("Enter course code (3 letters followed by 3 digits): ");
        String courseCode = getCourseCodeInput();
    
        if (isDuplicateCourse(courseCode)) {
            clearScreen();
            System.out.println("Course with code " + courseCode + " already exists.");
            pauseScreen();
            return;
        }
        clearScreen();
        System.out.println("Course code: " + courseCode);
        System.out.print("Enter course title: ");
        String courseTitle = getInput();
    
        clearScreen();
        System.out.println("Course code: " + courseCode);
        System.out.println("Course title: " + courseTitle);
        System.out.print("Enter number of units: ");
        int units = getIntInput();
    
        clearScreen();
        System.out.println("Course code: " + courseCode);
        System.out.println("Course title: " + courseTitle);
        System.out.println("Course units: " + units);
        System.out.print("Enter year level: ");
        int yearLevel = getIntInput();
    
        clearScreen();
        System.out.println("\nConfirm Course Details:");
        System.out.println("Course Code: " + courseCode);
        System.out.println("Course Title: " + courseTitle);
        System.out.println("Number of Units: " + units);
        System.out.println("Year Level: " + yearLevel);
    
        System.out.print("Do you want to save this course? (Y/N): ");
        String choice = getInput().toUpperCase();
    
        if (choice.equals("Y")) {
            clearScreen();
            Course course = new Course(courseCode, courseTitle, units, yearLevel);
            courses.add(course);
            writeCourseToFile(course);
            System.out.println("Course Code: " + courseCode);
            System.out.println("Course Title: " + courseTitle);
            System.out.println("Number of Units: " + units);
            System.out.println("Year Level: " + yearLevel);
            System.out.println("Course added successfully.");
            pauseScreen();
            
        } else {
            clearScreen();
            System.out.println("Course not added.");
            pauseScreen();
        }
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
        clearScreen();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\nCourse List:");
            System.out.println(" ");
    
            for (Course course : courses) {
                System.out.println(course.getCourseTitle());
                System.out.println(course.getCourseCode());
                System.out.println("Units: " + course.getUnits());
                System.out.println("Year Level: " + course.getYearLevel());
                System.out.println();
            }
        }
    }
    
    
    

    private static void editCourse() {
        viewCourses();
        System.out.print("Enter course code to edit: ");
        String courseCode = getCourseCodeInput();
    
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            clearScreen();
            System.out.println("Course with code " + courseCode + " not found.");
            pauseScreen();
            return;
        }
    
        String originalCourseTitle = course.getCourseTitle();
        int originalUnits = course.getUnits();
        int originalYearLevel = course.getYearLevel();
    
        String courseTitle;
        do {
            clearScreen();
            System.out.println( courseCode );
            System.out.println("Original Course Title: " + originalCourseTitle);
            System.out.print("Enter new course title: ");
            courseTitle = getInput();
            if (courseTitle.isEmpty()) {
                System.out.println("Course title cannot be blank. Please try again.");
            }
        } while (courseTitle.isEmpty());
    
        int units;
        do {
            clearScreen();
            System.out.println( courseCode );
            System.out.println("New Course Title: " + courseTitle);
            System.out.println("Original Units: " + originalUnits);
            System.out.print("Enter new number of units: ");
            units = getIntInput();
            if (units == 0) {
                System.out.println("Number of units cannot be zero. Please try again.");
            }
        } while (units == 0);
    
        int yearLevel;
        do {
            clearScreen();
            System.out.println( courseCode );
            System.out.println("New Course Title: " + courseTitle);
            System.out.println("New Units: " + units);
            System.out.println("Original Year Level: " + originalYearLevel);
            System.out.print("Enter new year level: ");
            yearLevel = getIntInput();
            if (yearLevel == 0) {
                System.out.println("Year level cannot be zero. Please try again.");
            }
        } while (yearLevel == 0);
    

        clearScreen();
        System.out.println( courseCode );
        System.out.println("New Course Title: " + courseTitle);
        System.out.println("New Units: " + units);
        System.out.println("New Year Level: " + yearLevel);
    
        System.out.print("\nDo you want to save these changes? (Y/N): ");
        String confirmation = getInput().toUpperCase();
        if (confirmation.equals("Y")) {
            course.setCourseTitle(courseTitle);
            course.setUnits(units);
            course.setYearLevel(yearLevel);
            writeCoursesToFile();
            System.out.println("Course information updated successfully.");
            pauseScreen();
        } else {
            System.out.println("Changes discarded.");
            pauseScreen();
        }
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
        clearScreen();
        viewCourses();
        System.out.print("Enter course code to delete: ");
        String courseCode = getCourseCodeInput();
    
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            pauseScreen();
            deleteCourse();
        }
    
        clearScreen();
        System.out.println("\nCourse to be deleted:");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.println("Units: " + course.getUnits());
        System.out.println("Year Level: " + course.getYearLevel());
    
        System.out.print("\nAre you sure you want to delete this course? (Y/N): ");
        String confirmation = getInput().toUpperCase();
        if (confirmation.equals("Y")) {
            courses.remove(course);
            writeCoursesToFile();
            System.out.println("Course deleted successfully.");
        } else {
            clearScreen();
            System.out.println("Course deletion canceled.");
        }
        pauseScreen();
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

    private static void pauseScreen() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {

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
            return courseCode + "," + courseTitle + "," + units + "," + yearLevel;
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
