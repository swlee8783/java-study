package e02.c2;


public class BoxTest {

    public static void main(String[] args) {

        Box<String> b1= new Box<>();
        b1.setBox("홍길동");
        String name = (String) b1.getBox();
        System.out.println(name);

        Box<Integer> b2 = new Box<>();
        b2.setBox(25);
        Integer age = (Integer) b2.getBox();
        System.out.println(age);

    }
}
