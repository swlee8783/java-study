package e02.c3;

public class StudentTest extends Person{

    public static void main(String[] args) {
        Person p1 = new Person();
        Student s1 = new Student();
        Teacher t1 = new Teacher();

        GeMethod.displayAge(p1);
        GeMethod.displayAge(s1);
        // GeMethod.displayAge(t1);


    }
}
