package assignment;

import java.util.*;

/**
 * Created by 이시원 on 2021-08-28
 */
public class AssignmentWeek03 {

    public static void main(String[] args){

        List<Integer[]> coordinates = new ArrayList<>();
        List<String> cString = new ArrayList<>();
        HashMap<Integer[], Double> keyValue = new HashMap<>();
        double result = 0.0;
        Integer[] c = new Integer[2];
        Scanner in = new Scanner(System.in);

        System.out.println("x, y값을 입력해주세요. ex) 1 1");
        c[0] = in.nextInt();
        c[1] = in.nextInt();

        System.out.println(Arrays.toString(c));

        while(coordinates.size() < 10) {
            System.out.println("x, y값을 입력해주세요. ex) 1 1");

            Integer[] temp = new Integer[2];
            temp[0] = in.nextInt();
            temp[1] = in.nextInt();

            cString.add(Integer.toString(temp[0]).concat(Integer.toString(temp[1])));

            Set<String> set = new HashSet<>(cString);

            if(set.size() != cString.size()) {
                cString = new ArrayList<>(set);

                System.out.println("중복된 값이 입력되었습니다.");
            }
            else {
                coordinates.add(temp);
            }

            System.out.println("(" + coordinates.size() + " / 10)");

        }

        for(Integer[] j : coordinates) {
            double temp_result = calcDistance(c[0], c[1], j[0], j[1]);

            keyValue.put(j, temp_result);

            System.out.println(Arrays.toString(j) + " : " + keyValue.get(j));

            if (keyValue.size() != 1) {
                result = Math.min(result, temp_result);
            }
            else  {
                result = temp_result;
            }
        }

        System.out.println("최소값\n" + Arrays.toString(getKey(keyValue, result)) + " : " + result);
    }

    private static <V, K> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if(value.equals(map.get(key))) {
                return key;
            }
        }
            return null;
    }

    private static double calcDistance(int standardX, int standardY, int targetX, int targetY) {
        return Math.sqrt(Math.pow(Math.abs(standardX - targetX), 2) + Math.pow(Math.abs(standardY - targetY), 2));
    }
}
