import java.util.ArrayList;
import java.util.List;

class Student {
    private String id;
    private String name;
    private int yearLevel;
    private List<Course> enrolledCourses;

    public Student(String id, String name, int yearLevel) {
        this.id = id;
        this.name = name;
        this.yearLevel = yearLevel;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public boolean canEnrollCourse(Course course) {
        return course.getYearLevel() == yearLevel && !enrolledCourses.contains(course);
    }

    public void enrollCourse(Course course) {
        if (canEnrollCourse(course)) {
            enrolledCourses.add(course);
            System.out.println("Successfully enrolled in " + course.getName());
        } else {
            System.out.println("Cannot enroll in " + course.getName());
        }
    }

    @Override
    public String toString() {
        return id + "," + name + "," + yearLevel;
    }
}
