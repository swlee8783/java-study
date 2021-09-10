package Sam04;

import java.util.TreeMap;
import java.util.Vector;

public class VectorTest {

    public static void main(String[] args) {
        Vector vector = new Vector();

        vector.add("홍길동");
        vector.add("남자");
        vector.add(25);
        vector.add(80.15);

        String name = (String) vector.get(0);
        int age = (int) vector.get(2);

        for(Object x : vector) {
            System.out.println(x);
        }

    }
}
