package e02.c2;


public class Box2Test {

    public static void main(String[] args) {

        Box2<String> b1 = new Box2<String>();
        b1.setData("홍길동", "남자");
        String name = b1.getName();
        String gender = b1.getGender();
        System.out.println("이름:" + name + ", 성별:" + gender);


    }
}
