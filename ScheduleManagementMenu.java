import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class ScheduleManagementMenu {
    private static List<Schedule> schedules = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String SCHEDULES_FILE = "schedules.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{3}\\d{3}$");

    public static void scheduleManagementMenu() {
        clearScreen();
        loadCoursesFromFile();
        loadSchedulesFromFile();
        boolean exit = false;
        while (!exit) {
            System.out.println(" ______________________________________________");
            System.out.println("|       ┏┓  ┏┓  ┓┏  ┏┓  ┳┓  ┳┳  ┓   ┏┓         |");
            System.out.println("|       ┗┓  ┃   ┣┫  ┣   ┃┃  ┃┃  ┃   ┣          |");
            System.out.println("|       ┗┛  ┗┛  ┛┗  ┗┛  ┻┛  ┗┛  ┗┛  ┗┛         |");
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
                    addSchedule();
                    break;
                case 2:
                    viewSchedules();
                    pauseScreen();
                    clearScreen();
                    break;
                case 3:
                    editSchedule();
                    break;
                case 4:
                    deleteSchedule();
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

    private static void loadSchedulesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String courseCode = parts[0];
                    String section = parts[1];
                    String day = parts[2];
                    String time = parts[3];
                    String room = parts[4];

                    Course course = findCourseByCode(courseCode);
                    if (course != null) {

                        Schedule schedule = new Schedule(course, section, day, time, room);
                        schedules.add(schedule);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from schedules file: " + e.getMessage());
        }
    }

    private static void addSchedule() {
        clearScreen();
        viewCoursesWithSchedules();
        System.out.print("Enter course code (3 letters followed by 3 digits): ");
        String courseCode = getCourseCodeInput();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            clearScreen();
            viewCoursesWithSchedules();
            System.out.println("Course with code " + courseCode + " not found. Try Again");
            return;
        }

        if (isDuplicateSchedule(course)) {
            clearScreen();
            viewCoursesWithSchedules();
            System.out.println("Schedule for course " + courseCode + " already exists.");
            return;
        }
        clearScreen();
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.print("Enter section: ");
        String section = getInput();

        clearScreen();
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.println("Section: " + section);
        System.out.print("Enter day (e.g., M T W TH F S): ");
        String day = getInput();

        clearScreen();
        System.out.println("\nNew Schedule:");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.println("Section: " + section);
        System.out.println("Day: " + day);
        System.out.print("Enter time (24 Hours Format): ");
        String time = getInput();

        clearScreen();
        System.out.println("\nNew Schedule:");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.println("Section: " + section);
        System.out.println("Day: " + day);
        System.out.println("Time: " + time);
        System.out.print("Enter room: ");
        String room = getInput();

        clearScreen();
        System.out.println("\nNew Schedule:");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Title: " + course.getCourseTitle());
        System.out.println("Section: " + section);
        System.out.println("Day: " + day);
        System.out.println("Time: " + time);
        System.out.println("Room: " + room);

        System.out.print("\nDo you want to add this schedule? (Y/N): ");
String confirmation = getInput().toUpperCase();
if (confirmation.equals("Y")) {
    Schedule schedule = new Schedule(course, section, day, time, room);
    schedules.add(schedule);
    writeScheduleToFile(schedule);
    System.out.println("Schedule added successfully.");
    pauseScreen();
    clearScreen();
} else {
    System.out.println("Schedule addition canceled.");
    pauseScreen();
}
    }

    private static boolean isDuplicateSchedule(Course course) {
        for (Schedule schedule : schedules) {
            if (schedule.getCourse().equals(course)) {
                return true;
            }
        }
        return false;
    }

    private static void viewSchedules() {
    if (schedules.isEmpty()) {
        clearScreen();
        System.out.println("No schedules found.");
        pauseScreen();
    } else {
        clearScreen();
        System.out.println("\nSchedule List:");
        System.out.println(" ");
        for (Schedule schedule : schedules) {
            System.out.println(schedule.getCourse().getCourseTitle() + " - " + schedule.getCourse().getCourseCode());
            System.out.println(schedule.getSection());
            System.out.println(schedule.getRoom());
            System.out.println(schedule.getDay());
            System.out.println(schedule.getTime());
            System.out.println(" ");
        }
    }
}

private static String formatScheduleString(Schedule schedule) {
    return schedule.getCourse().getCourseTitle() + " (" +
           schedule.getCourse().getCourseCode() + ") - " +
           schedule.getSection() + " - " +
           schedule.getDay() + " - " +
           schedule.getTime() + " - " +
           schedule.getRoom();
}



private static void editSchedule() {
    clearScreen();
    viewSchedules();

    String courseCode;
    Course course;
    do {
        System.out.print("Enter course code to edit schedule: ");
        courseCode = getCourseCodeInput();

        course = findCourseByCode(courseCode);
        if (course == null) {
            clearScreen();
            System.out.println("Course with code " + courseCode + " not found.");
            pauseScreen();
            continue;
        }
        clearScreen();
        System.out.println("Course: " + course.getCourseTitle() + " (" + course.getCourseCode() + ")");
        System.out.print("Is this the correct course? (Y/N): ");
        String confirmation = getInput().toUpperCase();
        if (confirmation.equals("Y")) {
            break;
        }
    } while (true);

    Schedule schedule = findScheduleByCourse(course);
    if (schedule == null) {
        clearScreen();
        System.out.println("Schedule for course " + courseCode + " not found.");
        pauseScreen();
        return;
    }

    System.out.print("Enter new section (" + schedule.getSection() + "): ");
    String newSection = getInput();

    System.out.print("Enter new day (" + schedule.getDay() + "): ");
    String newDay = getInput();

    System.out.print("Enter new time (" + schedule.getTime() + "): ");
    String newTime = getInput();

    System.out.print("Enter new room (" + schedule.getRoom() + "): ");
    String newRoom = getInput();

    System.out.println("\nNew Schedule:");
    System.out.println("Course: " + course.getCourseTitle() + " (" + course.getCourseCode() + ")");
    System.out.println("Section: " + (newSection.isEmpty() ? schedule.getSection() : newSection));
    System.out.println("Day: " + (newDay.isEmpty() ? schedule.getDay() : newDay));
    System.out.println("Time: " + (newTime.isEmpty() ? schedule.getTime() : newTime));
    System.out.println("Room: " + (newRoom.isEmpty() ? schedule.getRoom() : newRoom));

    System.out.print("\nDo you want to update the schedule? (Y/N): ");
    String confirmation = getInput().toUpperCase();
    if (confirmation.equals("Y")) {
        if (!newSection.isEmpty()) {
            schedule.setSection(newSection);
        }
        if (!newDay.isEmpty()) {
            schedule.setDay(newDay);
        }
        if (!newTime.isEmpty()) {
            schedule.setTime(newTime);
        }
        if (!newRoom.isEmpty()) {
            schedule.setRoom(newRoom);
        }

        writeScheduleToFile(schedule);
        System.out.println("Schedule information updated successfully.");
        pauseScreen();
        clearScreen();
    } else {
        System.out.println("Schedule update canceled.");
        pauseScreen();
    }
}





    private static Schedule findScheduleByCourse(Course course) {
        for (Schedule schedule : schedules) {
            if (schedule.getCourse().equals(course)) {
                return schedule;
            }
        }
        return null;
    }

    private static void deleteSchedule() {
        clearScreen();
        viewSchedules();
        System.out.print("Enter course code to delete schedule: ");
        String courseCode = getCourseCodeInput();
    
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            clearScreen();
            System.out.println("Course with code " + courseCode + " not found.");
            pauseScreen();
            return;
        }
    
        Schedule schedule = findScheduleByCourse(course);
        if (schedule == null) {
            clearScreen();
            System.out.println("Schedule for course " + courseCode + " not found.");
            pauseScreen();
            return;
        }
    

        clearScreen();
        System.out.println("\nSchedule to be deleted:");
        System.out.println("Course: " + course.getCourseTitle() + " (" + course.getCourseCode() + ")");
        System.out.println("Section: " + schedule.getSection());
        System.out.println("Day: " + schedule.getDay());
        System.out.println("Time: " + schedule.getTime());
        System.out.println("Room: " + schedule.getRoom());
    
        System.out.print("\nAre you sure you want to delete this schedule? (Y/N): ");
        String confirmation = getInput().toUpperCase();
    
        if (confirmation.equals("Y")) {
            schedules.remove(schedule);
            writeSchedulesToFile();
            System.out.println("Schedule deleted successfully.");
            pauseScreen();
        } else {
            clearScreen();
            System.out.println("Schedule deletion canceled.");
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

    private static void writeScheduleToFile(Schedule schedule) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULES_FILE, true))) {
            String scheduleRecord = schedule.getCourse().getCourseCode() + "|" +
                                     schedule.getSection() + " | " +
                                     schedule.getDay() + " | " +
                                     schedule.getTime() + " | " +
                                     schedule.getRoom();
            writer.write(scheduleRecord);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to schedules file: " + e.getMessage());
        }
    }    


    private static void writeSchedulesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULES_FILE))) {
            for (Schedule schedule : schedules) {
                writer.write(schedule.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to schedules file: " + e.getMessage());
        }
    }

    private static void viewCoursesWithSchedules() {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\nCourses and Schedules:");
            for (Course course : courses) {
                Schedule schedule = findScheduleByCourse(course);
                String scheduleInfo = (schedule != null) ? formatScheduleString(schedule) : "No Added Schedule Yet";

                System.out.println(course.getCourseTitle());
                System.out.println(course.getCourseCode());
                System.out.println("Units: " + course.getUnits());
                System.out.println("Year Level: " + course.getYearLevel());
                System.out.println("Schedule: " + scheduleInfo);
                System.out.println();
            }
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

        public int getUnits() {
            return units;
        }

        public int getYearLevel() {
            return yearLevel;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Course other = (Course) obj;
            return courseCode.equals(other.courseCode);
        }
    }

    static class Schedule {
        private Course course;
        private String section;
        private String day;
        private String time;
        private String room;

        public Schedule(Course course, String section, String day, String time, String room) {
            this.course = course;
            this.section = section;
            this.day = day;
            this.time = time;
            this.room = room;
        }

        public Course getCourse() {
            return course;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        @Override
        public String toString() {
            return course.getCourseCode() + " - " + section + " - " + day + " - " + time + " - " + room;
        }
    }

    public static void main(String[] args) {
        scheduleManagementMenu();
    }
}
