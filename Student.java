class Student {
    private String id;
    private String name;
    private int yearLevel;

    public Student(String id, String name, int yearLevel) {
        this.id = id;
        this.name = name;
        this.yearLevel = yearLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    // Method to check if a course can be enrolled based on year level
    public boolean canEnrollCourse(Course course) {
        return course.getYearLevel() == yearLevel;
    }
}

