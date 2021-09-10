package e02.c1;

public class BoxTest {

    public static void main(String[] args) {

        Box b1= new Box();
        b1.setBox("홍길동");
        String name = (String) b1.getBox();
        System.out.println(name);

        BoxInteger b2 = new BoxInteger();
        b2.setBox(25);
        Integer age = (Integer) b2.getBox();
        System.out.println(age);

        double weight = 75.12;

        // Integer count = (Integer) b1.getBox();
        // System.out.println(count);
    }
}
