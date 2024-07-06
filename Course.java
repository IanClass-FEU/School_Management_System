public class Course {
    private String code;
    private String name;
    private int units;
    private int yearLevel;

    public Course(String code, String name, int units, int yearLevel) {
        this.code = code;
        this.name = name;
        this.units = units;
        this.yearLevel = yearLevel;
    }

    // Getters and setters (if needed)
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getUnits() {
        return units;
    }

    public int getYearLevel() {
        return yearLevel;
    }
}

