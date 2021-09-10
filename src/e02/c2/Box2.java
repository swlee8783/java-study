package e02.c2;

public class Box2<T> {

    private T name;
    private T gender;

    public void setData(T data, T lastData) {

        this.name = data;
        this.gender = lastData;
    }

    public T getName() {
        return name;
    }

    public T getGender() { return gender; }
}
