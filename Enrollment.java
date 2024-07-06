import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Enrollment {
    private final Student student;
    private final List<Course> enrolledCourses;

    public Enrollment(Student student) {
        this.student = student;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student getStudent() {
        return student;
    }

    public boolean enrollCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            return false; // Course already enrolled
        }
        enrolledCourses.add(course);
        return true;
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
}
