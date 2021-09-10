package Sam04;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class 셋테스트 {

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        TreeSet<String> treeSet = new TreeSet<>();

        hashSet.add("4");
        hashSet.add("3");
        hashSet.add("1");
        hashSet.add("2");
        hashSet.add("4");

        linkedHashSet.add("4");
        linkedHashSet.add("3");
        linkedHashSet.add("1");
        linkedHashSet.add("2");
        linkedHashSet.add("4");

        treeSet.add("4");
        treeSet.add("2");
        treeSet.add("3");
        treeSet.add("1");
        treeSet.add("4");


        Iterator<String> hashSetIterator = hashSet.iterator();
        while(hashSetIterator.hasNext()){
            System.out.println(hashSetIterator.next());
        }

        Iterator<String> linkedHashSetIterator = linkedHashSet.iterator();
        while(linkedHashSetIterator.hasNext()){
            System.out.println(linkedHashSetIterator.next());
        }

        Iterator<String> treeSetIterator = treeSet.iterator();
        while(treeSetIterator.hasNext()){
            System.out.println(treeSetIterator.next());
        }
    }
}
