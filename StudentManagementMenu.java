import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StudentManagementMenu {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String STUDENTS_FILE = "students.txt";
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'.-]+$");

    public static void studentManagementMenu() {
        loadStudentsFromFile();
        boolean exit = false;
        while (!exit) {
            System.out.println(" ______________________________________________");
            System.out.println("|         ┏┓  ┏┳┓  ┳┳  ┳┓  ┏┓  ┳┓  ┏┳┓         |");
            System.out.println("|         ┗┓   ┃   ┃┃  ┃┃  ┣   ┃┃   ┃          |");
            System.out.println("|         ┗┛   ┻   ┗┛  ┻┛  ┗┛  ┛┗   ┻          |");
            System.out.println("|   ┳┳┓  ┏┓  ┳┓  ┏┓  ┏┓  ┏┓  ┳┳┓  ┏┓  ┳┓  ┏┳┓  |");
            System.out.println("|   ┃┃┃  ┣┫  ┃┃  ┣┫  ┃┓  ┣   ┃┃┃  ┣   ┃┃   ┃   |");
            System.out.println("|   ┛ ┗  ┛┗  ┛┗  ┛┗  ┗┛  ┗┛  ┛ ┗  ┗┛  ┛┗   ┻   |");
            System.out.println("|______________________________________________|");
            System.out.println("|                                              |");
            System.out.println("|   [1] Add Student       [2] View Student     |");
            System.out.println("|                                              |");
            System.out.println("|   [3] Edit Student      [4] Delete Student   |");
            System.out.println("|                                              |");
            System.out.println("|                 [5] Exit                     |");
            System.out.println("|______________________________________________|");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudentList();
                    break;
                case 3:
                    editStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    String id = parts[0];
                    String lastName = parts[1];
                    String firstName = parts[2];
                    String middleName = parts[3];
                    String gender = parts[4];
                    Date birthday = parseDate(parts[5]);
                    String address = parts[6];
                    String degreeProgram = parts[7];
                    int yearLevel = Integer.parseInt(parts[8]);

                    Student student = new Student(id, lastName, firstName, middleName, gender, birthday, address, degreeProgram, yearLevel);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from students file: " + e.getMessage());
        }
    }

    private static void addStudent() {
        clearScreen();
        System.out.print("Enter student ID: ");
        String id = getInput();
    
        if (!isValidStudentId(id)) {
            System.out.println("Invalid student ID. The ID must be a 6-digit number.");
            return;
        }
    
        if (isDuplicateStudent(id)) {
            System.out.println("Student with ID " + id + " already exists.");
            return;
        }
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.print("Enter last name: ");
        String lastName = getNameInput();
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.print("Enter first name: ");
        String firstName = getNameInput();
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.print("Enter middle name: ");
        String middleName = getNameInput();
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.print("Enter gender (M/F): ");
        String gender = getGenderInput();
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.println(" Gender          " + gender);
        System.out.print("Enter birthday (yyyy-MM-dd): ");
        String birthdayStr = getInput();
        Date birthday = parseDate(birthdayStr);
        if (birthday == null) {
            return;
        }
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.println(" Gender          " + gender);
        System.out.println(" Birthdate       " + birthdayStr);
        System.out.print("Enter address: ");
        String address = getInput();
    
        clearScreen();
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.println(" Gender          " + gender);
        System.out.println(" Birthdate       " + birthdayStr);
        System.out.println(" Address         " + address);
        System.out.print("Enter degree program: ");
        String degreeProgram = getInput();
        
        clearScreen();
        System.out.println("\nPlease confirm the following details:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.println(" Gender          " + gender);
        System.out.println(" Birthdate       " + birthdayStr);
        System.out.println(" Address         " + address);
        System.out.println(" Degree Program  " + degreeProgram);
        System.out.print("Enter year level: ");
        int yearLevel = getIntInput();
    
        clearScreen();
        System.out.println("\nPlease confirm the following details:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + id);
        System.out.println(" Last Name       " + lastName);
        System.out.println(" First Name      " + firstName);
        System.out.println(" Middle Name     " + middleName);
        System.out.println(" Gender          " + gender);
        System.out.println(" Birthdate       " + birthdayStr);
        System.out.println(" Address         " + address);
        System.out.println(" Degree Program  " + degreeProgram);
        System.out.println(" Year Level      " + yearLevel);
        
        System.out.println(" ");
        System.out.println("Confirm adding student? (Y/N): ");
        
        String confirm = getInput().toUpperCase();
    
        if (confirm.equals("Y")) {
            Student student = new Student(id, lastName, firstName, middleName, gender, birthday, address, degreeProgram, yearLevel);
            students.add(student);
            writeStudentToFile(student);
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student addition canceled.");
        }
    
        pauseScreen();
    }
    

    private static boolean isDuplicateStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidStudentId(String id) {
        return id.matches("\\d{6}");
    }

    private static void viewStudentList() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\nStudent List:");
            for (Student student : students) {
                System.out.println("ID Number: " + student.getId() + ", Full Name: " + student.getFullName());
            }
        }
    }

    private static void editStudent() {
        clearScreen();
        System.out.print("Enter student ID to edit: ");
        String id = getInput();
    
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        clearScreen();
        System.out.println("\nCurrent student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());
        System.out.println(" Birthdate       " + student.getBirthday().toString());
        System.out.println(" Address         " + student.getAddress());
        System.out.println(" Degree Program  " + student.getDegreeProgram());
        System.out.println(" Year Level      " + student.getYearLevel());
    
        System.out.print("\nConfirm editing student information? (Y/N): ");
        String confirm = getInput().toUpperCase();
    
        if (!confirm.equals("Y")) {
            clearScreen();
            System.out.println("Student information edit canceled.");
            pauseScreen();
            return;
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());

        System.out.print("Enter new last name (" + student.getLastName() + "): ");
        String lastName = getNameInput();
        if (!lastName.isEmpty()) {
            student.setLastName(lastName);
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());

        System.out.print("Enter new first name (" + student.getFirstName() + "): ");
        String firstName = getNameInput();
        if (!firstName.isEmpty()) {
            student.setFirstName(firstName);
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());

        System.out.print("Enter new middle name (" + student.getMiddleName() + "): ");
        String middleName = getNameInput();
        if (!middleName.isEmpty()) {
            student.setMiddleName(middleName);
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());

        System.out.print("Enter new gender (" + student.getGender() + ") (M/F): ");
        String gender = getGenderInput();
        if (!gender.isEmpty()) {
            student.setGender(gender);
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());

        System.out.println("\nEnter the student's birth date (YYYY-MM-DD) or leave blank to keep the current value:");
        String birthdayStr = getInput();
        if (!birthdayStr.isEmpty()) {
            Date birthday = parseDate(birthdayStr);
            if (birthday != null) {
                student.setBirthday(birthday);
            }
        }
    

        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());
        System.out.println(" Birthdate       " + student.getBirthday().toString());

        System.out.print("Enter new address (" + student.getAddress() + "): ");
        String address = getInput();
        if (!address.isEmpty()) {
            student.setAddress(address);
        }
    

        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());
        System.out.println(" Birthdate       " + student.getBirthday().toString());
        System.out.println(" Address         " + student.getAddress());

        System.out.print("Enter new degree program (" + student.getDegreeProgram() + "): ");
        String degreeProgram = getInput();
        if (!degreeProgram.isEmpty()) {
            student.setDegreeProgram(degreeProgram);
        }
    
        clearScreen();
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());
        System.out.println(" Birthdate       " + student.getBirthday().toString());
        System.out.println(" Address         " + student.getAddress());
        System.out.println(" Degree Program  " + student.getDegreeProgram());

        System.out.print("Enter new year level (" + student.getYearLevel() + "): ");
        int yearLevel = getIntInput();
        if (yearLevel != 0) {
            student.setYearLevel(yearLevel);
        }
    
        
        System.out.println("\nUpdated student information:");
        System.out.println("Field           Value ");
        System.out.println(" ID              " + student.getId());
        System.out.println(" Last Name       " + student.getLastName());
        System.out.println(" First Name      " + student.getFirstName());
        System.out.println(" Middle Name     " + student.getMiddleName());
        System.out.println(" Gender          " + student.getGender());
        System.out.println(" Birthdate       " + student.getBirthday().toString());
        System.out.println(" Address         " + student.getAddress());
        System.out.println(" Degree Program  " + student.getDegreeProgram());
        System.out.println(" Year Level      " + student.getYearLevel());
    
        System.out.print("Confirm updating student information? (Y/N): ");
        String confirm2 = getInput().toUpperCase();
    
        if (confirm2.equals("Y")) {
            clearScreen();
            writeStudentsToFile();
            System.out.println("Student information updated successfully.");
            pauseScreen();
        } else {
            clearScreen();
            System.out.println("Student information update canceled.");
            pauseScreen();
        }
    }
    
    

    private static Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = getInput();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        students.remove(student);
        writeStudentsToFile();
        System.out.println("Student record deleted successfully.");
    }

    private static String getInput() {
        String input = scanner.nextLine().trim();
        return input;
    }

    private static String getNameInput() {
        String input;
        do {
            input = getInput();
            if (!NAME_PATTERN.matcher(input).matches()) {
                System.out.println("Invalid name format. Please enter a valid name.");
            }
        } while (!NAME_PATTERN.matcher(input).matches());
        return input;
    }

    private static String getGenderInput() {
        String input;
        do {
            input = getInput().toUpperCase();
            if (!input.equals("M") && !input.equals("F")) {
                System.out.println("Invalid gender. Please enter M or F.");
            }
        } while (!input.equals("M") && !input.equals("F"));
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

    private static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    private static void writeStudentToFile(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE, true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to students file: " + e.getMessage());
        }
    }

    private static void writeStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to students file: " + e.getMessage());
        }
    }

    private static void pauseScreen() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {

        }
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {

        }
    }

    static class Student {
        private String id;
        private String lastName;
        private String firstName;
        private String middleName;
        private String gender;
        private Date birthday;
        private String address;
        private String degreeProgram;
        private int yearLevel;

        public Student(String id, String lastName, String firstName, String middleName, String gender, Date birthday, String address, String degreeProgram, int yearLevel) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.gender = gender;
            this.birthday = birthday;
            this.address = address;
            this.degreeProgram = degreeProgram;
            this.yearLevel = yearLevel;
        }

        public String getId() {
            return id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDegreeProgram() {
            return degreeProgram;
        }

        public void setDegreeProgram(String degreeProgram) {
            this.degreeProgram = degreeProgram;
        }

        public int getYearLevel() {
            return yearLevel;
        }

        public void setYearLevel(int yearLevel) {
            this.yearLevel = yearLevel;
        }

        public String getFullName() {
            return lastName + ", " + firstName + " " + middleName;
        }

        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return id + "," + lastName + "," + firstName + "," + middleName + "," + gender + "," + formatter.format(birthday) + "," + address + "," + degreeProgram + "," + yearLevel;
        }
    }

    public static void main(String[] args) {
        studentManagementMenu();
    }
}
