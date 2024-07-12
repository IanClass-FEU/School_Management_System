import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return id.equals(other.id) && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    }

