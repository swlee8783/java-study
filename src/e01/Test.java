package e01;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();

        Set<String> keySet = new HashSet<>();
        map = null;
        if(map != null) {
            keySet = map.keySet();
        }

        int size = keySet.size();
        System.out.println(size);
        if(keySet == null||keySet.isEmpty()){
            System.out.println("null");
        }
        else if (keySet.isEmpty()) {
            System.out.println("empty");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("str1");
        sb.append("str2");
        System.out.println(sb.toString());


        List<Long> rspTimeList = new ArrayList<>(Arrays.asList(11L, 27L, 33L));
        double rspTotal = rspTimeList.stream().mapToLong(i -> i).sum();
        double rspAvg = 0;
        double rspStDev = 0;

        if (!rspTimeList.isEmpty()) {

            // rspTime 평균값 계산
            rspAvg = Math.round((rspTotal / rspTimeList.size())*1000)/1000.0;
            double sum = 0;

            for (Long rspTime : rspTimeList) {
                sum += Math.pow(rspTime - rspAvg, 2);
            }

            // rspTime 표준편차 계산
            rspStDev = Math.round(Math.sqrt(sum / rspAvg)*1000)/1000.0;
        }

        System.out.println(rspTotal + "," + rspAvg + "," + rspStDev);

    }
}
