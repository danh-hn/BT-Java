public class Student {
    private int studentID;
    private String name;
    private int age;
    private String email;
    private double gpa;

    public Student(int studentID, String name, int age, String email, double gpa) {
        this.studentID = studentID;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gpa = gpa;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public double getGpa() { return gpa; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setGpa(double gpa) { this.gpa = gpa; }
}
