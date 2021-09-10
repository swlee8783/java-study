package e01;

public class 메스테스트 {

    public static void main(String[] args) {
        double d1 = 12.426;

        double x1 = 1;
        double y1 = 1;
        double x2 = 2;
        double y2 = 2;

        System.out.println(SmartMath.distance(x1, y1, x2, y2));

        System.out.println(d1);
        System.out.println(Math.round(d1));
        System.out.println(Math.ceil(d1));
        System.out.println(Math.floor(d1));

        System.out.println("------------");
        System.out.println(SmartMath.round(d1, 2)); //12.43
        System.out.println(SmartMath.ceil(d1, 2)); //12.43
        System.out.println(SmartMath.floor(d1, 2)); //12.42
    }
}
