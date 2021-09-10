package Sam04;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public class 맵테스트 {
    public static void main(String[] args){

        HashMap<String, Object> hashMap = new HashMap<>();
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();

        hashMap.put("B", "B-Value");
        hashMap.put("A", "A-Value");
        hashMap.put("D", "D-Value");
        hashMap.put("C", "C-Value");
        hashMap.put("B", "BB-Value");


        linkedHashMap.put("B", "B-Value");
        linkedHashMap.put("A", "A-Value");
        linkedHashMap.put("D", "D-Value");
        linkedHashMap.put("C", "C-Value");
        linkedHashMap.put("B", "BB-Value");

        treeMap.put("B", "B-Value");
        treeMap.put("A", "A-Value");
        treeMap.put("D", "D-Value");
        treeMap.put("C", "C-Value");
        treeMap.put("B", "BB-Value");


        System.out.println(hashMap.get("A"));

        for(String key : hashMap.keySet()) {
            System.out.println(key + " : " + hashMap.get(key));
        }

        for(String key : linkedHashMap.keySet()) {
            System.out.println(key + " : " + linkedHashMap.get(key));
        }

        for(String key : treeMap.keySet()) {
            System.out.println(key + " : " + treeMap.get(key));
        }

        HashMap<String, Object> member = new HashMap<>();
        member.put("user_id", "superman");
        member.put("password", "1234");
        member.put("gender", "M");
        member.put("age", 20);
        member.put("using_yn", true);

        System.out.println("회원아이디: " + member.get("user_id"));
    }
}
