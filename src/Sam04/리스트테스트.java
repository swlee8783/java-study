package Sam04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class 리스트테스트 {
    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();

        arrayList.add("B");
        arrayList.add("A");
        arrayList.add("D");
        arrayList.add("C");
        arrayList.add("A");

        linkedList.add("B");
        linkedList.add("A");
        linkedList.add("D");
        linkedList.add("C");
        linkedList.add("A");

        for(String s : arrayList){
            System.out.println(s);
        }

        for(String s : linkedList) {
            System.out.println(s);
        }

        Collections.sort(arrayList);


    }
}
