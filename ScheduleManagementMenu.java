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
        loadCoursesFromFile();
        loadSchedulesFromFile();
        boolean exit = false;
        while (!exit) {
            System.out.println("\nSchedule Management Menu");
            System.out.println("1. Add Schedule");
            System.out.println("2. View Schedules");
            System.out.println("3. Edit Schedule");
            System.out.println("4. Delete Schedule");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                    viewSchedules();
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
        System.out.print("Enter course code (3 letters followed by 3 digits): ");
        String courseCode = getCourseCodeInput();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        if (isDuplicateSchedule(course)) {
            System.out.println("Schedule for course " + courseCode + " already exists.");
            return;
        }

        System.out.print("Enter section: ");
        String section = getInput();

        System.out.print("Enter day (e.g., M T W TH F S): ");
        String day = getInput();

        System.out.print("Enter time (24 Hours Format): ");
        String time = getInput();

        System.out.print("Enter room: ");
        String room = getInput();

        Schedule schedule = new Schedule(course, section, day, time, room);
        schedules.add(schedule);
        writeScheduleToFile(schedule);

        System.out.println("Schedule added successfully.");
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
        System.out.println("No schedules found.");
    } else {
        System.out.println("\nSchedule List:");
        for (Schedule schedule : schedules) {
            System.out.println(formatScheduleString(schedule));
        }
    }
}

private static String formatScheduleString(Schedule schedule) {
    return schedule.getCourse().getCourseCode() + " | " +
           schedule.getSection() + " | " +
           schedule.getDay() + " | " +
           schedule.getTime() + " | " +
           schedule.getRoom();
}


private static void editSchedule() {
    System.out.print("Enter course code to edit schedule: ");
    String courseCode = getCourseCodeInput();

    Course course = findCourseByCode(courseCode);
    if (course == null) {
        System.out.println("Course with code " + courseCode + " not found.");
        return;
    }

    Schedule schedule = findScheduleByCourse(course);
    if (schedule == null) {
        System.out.println("Schedule for course " + courseCode + " not found.");
        return;
    }

    System.out.print("Enter new section (" + schedule.getSection() + "): ");
    String section = getInput();
    if (!section.isEmpty()) {
        schedule.setSection(section);
    }

    System.out.print("Enter new day (" + schedule.getDay() + "): ");
    String day = getInput();
    if (!day.isEmpty()) {
        schedule.setDay(day);
    }

    System.out.print("Enter new time (" + schedule.getTime() + "): ");
    String time = getInput();
    if (!time.isEmpty()) {
        schedule.setTime(time);
    }

    System.out.print("Enter new room (" + schedule.getRoom() + "): ");
    String room = getInput();
    if (!room.isEmpty()) {
        schedule.setRoom(room);
    }

    writeScheduleToFile(schedule);
    System.out.println("Schedule information updated successfully.");
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
        System.out.print("Enter course code to delete schedule: ");
        String courseCode = getCourseCodeInput();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        Schedule schedule = findScheduleByCourse(course);
        if (schedule == null) {
            System.out.println("Schedule for course " + courseCode + " not found.");
            return;
        }

        schedules.remove(schedule);
        writeSchedulesToFile();
        System.out.println("Schedule deleted successfully.");
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
