package e02.c2;

public class Box3<K, V> {

    private K name;
    private V age;

    public void setData(K name, V age){
        this.name = name;
        this.age = age;
    }

    public K getName() {
        return name;
    }

    public V getAge(){
        return age;
    }

}
