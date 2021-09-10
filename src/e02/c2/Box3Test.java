package e02.c2;

public class Box3Test {
    public static void main(String[] args){

        Box3<String, Integer> b1= new Box3<>();
        b1.setData("홍길동", 25);

        String name = b1.getName();
        Integer age = b1.getAge();

        System.out.println("이름:"+ name + ", 나이:" + age);
    }
}
