package e01;

import java.text.SimpleDateFormat;
import java.util.*;

public class Test2 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("00","01");
        for(String i: list){
            System.out.println(i);
        }

        Map<String, String> map = new HashMap<>();
        map = null;
        String a = "a";
        if(map == null ||map.get(a).isEmpty()){
            System.out.println("Empty");
        }
    }
}
