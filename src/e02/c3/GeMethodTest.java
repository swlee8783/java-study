package e02.c3;

public class GeMethodTest {

    public static void main(String[] args) {

        String[] names = {"자바", "파이썬", "씨쁠쁠", "씨샵", "자바스크립트"};
        Double[] scores = {100D, 98D, 95D, 90D, 85D};

        GeMethod.print(names);
        GeMethod.print(scores);

        GeMethod.firstStringPrint("자");
    }
}
