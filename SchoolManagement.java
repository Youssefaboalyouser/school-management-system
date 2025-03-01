
// Import necessary packages
import java.util.*;

// Financial Management Class
class FinancialManagement {
    private double totalRevenue = 0;
    private double totalExpenses = 0;

    // Method to add revenue
    public void addRevenue(double amount) {
        totalRevenue += amount;
    }

    // Method to add expense
    public void addExpense(double amount) {
        totalExpenses += amount;
    }

    // Method to get profit
    public double getProfit() {
        return totalRevenue - totalExpenses;
    }

    // Method to get total revenue
    public double getTotalRevenue() {
        return totalRevenue;
    }

    // Method to get total expenses
    public double getTotalExpenses() {
        return totalExpenses;
    }
}

// Student Class
class Student {
    private String name, password;
    private int age;
    private static int idCounter = 1;
    private final String id;
    private List<String> registeredCourses = new ArrayList<>();
    private double annualPayment = 5000;
    private boolean paymentConfirmed = false;
    private Map<String, Double> grades = new HashMap<>();

    // Constructor for Student class
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.id = "S" + idCounter++;
        this.password = "00000";
    }

    // Getter for student ID
    public String getId() {
        return id;
    }
    // Getter for student ID
    public int getage() {
        return age;
    }

    // Getter for student name
    public String getName() {
        return name;
    }

    // Getter for student password
    public String getPassword() {
        return password;
    }

    // Setter for student password
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Method to register a course
    public void registerCourse(String course, Map<String, List<Student>> courseStudents) {
        registeredCourses.add(course);

        // Add student to the course's list in courseStudents
        courseStudents.putIfAbsent(course, new ArrayList<>());
        courseStudents.get(course).add(this);
    }

    // Method to confirm payment
    public void confirmPayment(FinancialManagement fm) {
        if (!paymentConfirmed) {
            fm.addRevenue(annualPayment);
            paymentConfirmed = true;
        }
    }

    // Getter for registered courses
    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    // Getter for grades
    public Map<String, Double> getGrades() {
        return grades;
    }

    // Method to change password
    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect old password. Password change failed.");
        }
    }

    // Method to set grade for a course
    public void setGrade(String course, double grade) {
        grades.put(course, grade);
    }
}

// Teacher Class
class Teacher {
    private String name, password;
    private int age;
    private double salary;
    private static int idCounter = 1;
    private final String id;
    private List<String> assignedCourses = new ArrayList<>();
    private boolean salaryTaken = false;

    // Constructor for Teacher class
    public Teacher(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.id = "T" + idCounter++;
        this.password = "00000";
    }

    // Getter for teacher ID
    public String getId() {
        return id;
    }

    // Getter for teacher name
    public String getName() {
        return name;
    }

    // Getter for teacher password
    public String getPassword() {
        return password;
    }

    // Setter for teacher password
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Getter for teacher salary
    public double getSalary() {
        return salary;
    }

    // Method to assign a course to the teacher
    public void assignCourse(String course) {
        assignedCourses.add(course);
    }

    // Getter for assigned courses
    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    // Method to display personal information of the teacher
    public void displayPersonalInfo() {
        System.out.println("Teacher Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("ID: " + id);
        System.out.println("Salary: " + salary);
    }

    // Method to list registered courses and students
    public void listRegisteredCourses(Map<String, List<Student>> courseStudents) {
        System.out.println("Your Assigned Courses: " + assignedCourses);

        for (String course : assignedCourses) {
            System.out.println("\nCourse: " + course);

            if (courseStudents.containsKey(course)) {
                List<Student> students = courseStudents.get(course);
                System.out.println("Students Registered:");
                for (Student s : students) {
                    System.out.println("Name: " + s.getName() + " | ID: " + s.getId() + " | Grade: "
                            + s.getGrades().getOrDefault(course, 0.0));
                }
            } else {
                System.out.println("No students registered in this course yet.");
            }
        }
    }

    // Method to take salary
    public void takeSalary(FinancialManagement fm) {
        if (salaryTaken) {
            System.out.println("You have already taken your salary.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Confirm taking salary (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            fm.addExpense(salary);
            salaryTaken = true;
            System.out.println("Salary taken successfully. Expense updated.");
        } else {
            System.out.println("Salary request canceled.");
        }
    }

    // Method to modify student grades
    public void modifyStudentGrades(Map<String, List<Student>> courseStudents) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course name: ");
        String course = scanner.nextLine();

        if (!assignedCourses.contains(course)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        if (!courseStudents.containsKey(course)) {
            System.out.println("No students registered in this course.");
            return;
        }

        System.out.println("Students in " + course + ":");
        for (Student s : courseStudents.get(course)) {
            System.out.println("ID: " + s.getId() + " | Name: " + s.getName() + " | Current Grade: "
                    + s.getGrades().getOrDefault(course, 0.0));
        }

        System.out.print("Enter Student ID to modify grade: ");
        String studentId = scanner.nextLine();

        for (Student s : courseStudents.get(course)) {
            if (s.getId().equals(studentId)) {
                System.out.print("Enter new grade: ");
                double newGrade = scanner.nextDouble();
                s.setGrade(course, newGrade);
                System.out.println("Grade updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }
}

// Main Class
public class SchoolManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    private static List<String> courses = new ArrayList<>();
    private static Map<String, String> courseAssignments = new HashMap<>();
    private static List<String> buildings = new ArrayList<>(
            Arrays.asList("Building A", "Building B", "Building C", "Building D"));
    private static int numClasses = 50;
    private static FinancialManagement fm = new FinancialManagement();
    private static String headPassword = "12345Youssef";
    private static boolean salaryTaken = false;
    private static final double HEAD_SALARY = 3000;
    private static Map<String, List<Student>> courseStudents = new HashMap<>();

    // Method to view financial information
    private static void viewFinancialInfo() {
        System.out.println("Total Revenue: " + fm.getTotalRevenue());
        System.out.println("Total Expenses: " + fm.getTotalExpenses());
        System.out.println("Total Profit: " + fm.getProfit());
    }

    // Method to take salary for head of school
    private static void takeSalary() {
        if (salaryTaken) {
            System.out.println("Salary has already been taken.");
            return;
        }
        System.out.print("Confirm taking salary (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            fm.addExpense(HEAD_SALARY);
            salaryTaken = true;
            System.out.println("Salary taken successfully. Expense updated.");
        } else {
            System.out.println("Salary request canceled.");
        }
    }

    // Method for teacher login
    private static void teacherLogin() {
        System.out.print("Enter Teacher Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Teacher ID: ");
        String id = scanner.nextLine();

        for (Teacher t : teachers) {
            if (t.getPassword().equals(password) && t.getId().equals(id)) {
                System.out.println("Welcome, " + t.getName());

                while (true) {
                    System.out.println("\n--- Teacher Menu ---");
                    System.out.println("1. View Personal Information");
                    System.out.println("2. List Assigned Courses");
                    System.out.println("3. View and Modify Student Grades");
                    System.out.println("4. Take Salary");
                    System.out.println("5. Logout");
                    System.out.print("Choose an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            t.displayPersonalInfo();
                            break;
                        case 2:
                            t.listRegisteredCourses(courseStudents);
                            break;
                        case 3:
                            t.listRegisteredCourses(courseStudents); // View students
                            t.modifyStudentGrades(courseStudents); // Modify grades
                            break;
                        case 4:
                            t.takeSalary(fm);
                            break;
                        case 5:
                            System.out.println("Logging out...\n");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            }
        }
        System.out.println("Teacher not found.");
    }

    // Method for student login
    private static void studentLogin() {
        System.out.print("Enter Student password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (Student s : students) {
            if (s.getPassword().equals(password) && s.getId().equals(id)) {
                System.out.println("Welcome, " + s.getName());

                while (true) {
                    System.out.println("\n--- Student Menu ---");
                    System.out.println("1. Display Personal Information");
                    System.out.println("2. Register a New Course");
                    System.out.println("3. View Registered Courses and Grades");
                    System.out.println("4. Pay Fees");
                    System.out.println("5. change password");
                    System.out.println("6. Logedout.....\n");
                    System.out.print("Choose an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.println("Name: " + s.getName());
                            System.out.println("ID: " + s.getId());
                            System.out.println("age: " + s.getage());
                            break;
                        case 2:
                            System.out.print("Enter course name to register: ");
                            String course = scanner.nextLine();
                            s.registerCourse(course, courseStudents); // Updated to include the map
                            System.out.println("Course registered successfully.");
                            break;
                        case 3:
                            System.out.println("Registered Courses: " + s.getRegisteredCourses());
                            System.out.println("Grades: " + s.getGrades());
                            break;
                        case 4:
                            if (!s.getRegisteredCourses().isEmpty()) {
                                System.out.print("Confirm payment? (yes/no): ");
                                String confirm = scanner.nextLine();
                                if (confirm.equalsIgnoreCase("yes")) {
                                    s.confirmPayment(fm);
                                    System.out.println("Payment confirmed.");
                                } else {
                                    System.out.println("Payment canceled.");
                                }
                            } else {
                                System.out.println("No courses registered yet.");
                            }
                            break;
                        case 5:
                            System.out.print("Enter old password: ");
                            String oldPass = scanner.nextLine();
                            System.out.print("Enter new password: ");
                            String newPass = scanner.nextLine();
                            s.changePassword(oldPass, newPass);
                            break;
                        case 6:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            }
        }
        System.out.println("Student not found.");
    }

    // Method to add a new student
    private static void addStudent() {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Student student = new Student(name, age);
        students.add(student);
        System.out.println("Student added successfully with ID: " + student.getId());
    }

    // Method to add a new teacher
    private static void addTeacher() {
        System.out.print("Enter Teacher Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        Teacher teacher = new Teacher(name, age, salary);
        teachers.add(teacher);
        System.out.println("Teacher added successfully with ID: " + teacher.getId());
    }

    // Method to delete a student
    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        boolean removed = students.removeIf(s -> s.getId().equals(id));
        if (removed) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student ID not found.");
        }
    }

    // Method to delete a teacher
    private static void deleteTeacher() {
        System.out.print("Enter Teacher ID to delete: ");
        String id = scanner.nextLine();
        boolean removed = teachers.removeIf(t -> t.getId().equals(id));
        if (removed) {
            System.out.println("Teacher deleted successfully.");
        } else {
            System.out.println("Teacher ID not found.");
        }
    }

    // Main method
    public static void main(String[] args) {
        System.out.println("Welcome to the School Management System");
        while (true) {
            System.out.println("1. Head of School Login\n2. Teacher Login\n3. Student Login\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1)
                headLogin();
            else if (choice == 2)
                teacherLogin();
            else if (choice == 3)
                studentLogin();
            else if (choice == 4)
                System.exit(0);
            else
                System.out.println("Invalid option");
        }
    }

    // Method for head of school login
    private static void headLogin() {
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (password.equals(headPassword))
            headMenu();
        else
            System.out.println("Invalid credentials");
    }

    // Method for head of school menu
    private static void headMenu() {
        while (true) {
            System.out.println(
                    "1. List Teachers\t\t2. List Students\n3. View Head Information\t4. Change Password\n5. Manage Courses\t\t6. Assign Courses\n7. Manage Buildings & Classes\t8. Add new student\n9. Add new teacher\t\t10. delete student\n11. delete teacher\t\t12. View financial info\n13. Take Salary\t\t\t14. logout\nChoose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    listTeachers();
                    System.out.println("=========================================================================\n");
                    break;
                case 2:
                    listStudents();
                    System.out.println("=========================================================================\n");
                    break;
                case 3:
                    viewHeadInfo();
                    System.out.println("=========================================================================\n");
                    break;
                case 4:
                    changeHeadPassword();
                    System.out.println("=========================================================================\n");
                    break;
                case 5:
                    manageCourses();
                    System.out.println("=========================================================================\n");
                    break;
                case 6:
                    assignCourseToTeacher();
                    System.out.println("=========================================================================\n");
                    break;
                case 7:
                    manageBuildings();
                    System.out.println("=========================================================================\n");
                    break;
                case 8:
                    addStudent();
                    System.out.println("=========================================================================\n");
                    break;
                case 9:
                    addTeacher();
                    System.out.println("=========================================================================\n");
                    break;
                case 10:
                    deleteStudent();
                    System.out.println("=========================================================================\n");
                    break;
                case 11:
                    deleteTeacher();
                    System.out.println("=========================================================================\n");
                    break;
                case 12:
                    viewFinancialInfo();
                    System.out.println("=========================================================================\n");
                    break;
                case 13:
                    takeSalary();
                    System.out.println("=========================================================================\n");
                    break;
                case 14:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // Method to list all teachers
    private static void listTeachers() {
        for (Teacher t : teachers)
            System.out.println(t.getName() + " (ID: " + t.getId() + ")");
    }

    // Method to list all students
    private static void listStudents() {
        for (Student s : students)
            System.out.println(s.getName() + " (ID: " + s.getId() + ")");
    }

    // Method to view head of school information
    private static void viewHeadInfo() {
        System.out.println("Head Name: Youssef_Aboalyouser\nSalary: 3000");
    }

    // Method to change head of school password
    private static void changeHeadPassword() {
        System.out.print("Enter New Password: ");
        headPassword = scanner.nextLine();
        System.out.println("Password Changed Successfully");
    }

    // Method to manage courses
    private static void manageCourses() {
        System.out.println("Available courses: " + courses);
        System.out.println("1. Add Course\n2. Delete Course");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            System.out.print("Enter Course Name: ");
            courses.add(scanner.nextLine());
        } else if (choice == 2) {
            System.out.print("Enter Course Name: ");
            courses.remove(scanner.nextLine());
        }
    }

    // Method to assign a course to a teacher
    private static void assignCourseToTeacher() {
        System.out.print("Enter Course Name: ");
        String course = scanner.nextLine();
        System.out.print("Enter Teacher Name: ");
        String teachername = scanner.nextLine();
        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(teachername)) {
                teacher.assignCourse(course);
                courseAssignments.put(course, teachername);
                courseStudents.putIfAbsent(course, new ArrayList<>());
                System.out.println("Course assigned successfully.");
                return;
            }
        }
        System.out.println("Teacher not found.");
    }

    // Method to manage buildings and classes
    private static void manageBuildings() {
        System.out.println("Buildings: " + buildings + "\nClasses: " + numClasses);
        System.out.println("1. Add Building\n2. Remove Building\n3. Add Class\n4. Remove Class");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1)
            buildings.add("Building " + (buildings.size() + 1));
        else if (choice == 2 && !buildings.isEmpty())
            buildings.remove(buildings.size() - 1);
        else if (choice == 3)
            numClasses++;
        else if (choice == 4 && numClasses > 0)
            numClasses--;
    }
}
