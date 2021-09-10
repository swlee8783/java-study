package e01;

public class 스트링버퍼테스트 {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("홍길동")
                .append(System.lineSeparator())
                .append("학생")
                .append(System.lineSeparator())
                .append(20);

        System.out.println(sb);

        String name = "홍길동";
        String dept = "학생";
        int age = 20;

        System.out.println(age);
        System.out.println(age);

        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.format("이름: %s", name))
                .append(System.lineSeparator())
                .append(String.format("직업: %s", dept))
                .append(System.lineSeparator())
                .append(String.format("나이: %d", age));

        System.out.println(sb2);

        //sb2.appendLine();
        //sb2.appendFormat();

        StringPlus sp = new StringPlus();
        sp.append(String.format("이름: %s", name));
        sp.appendFormat("이름: %s", name)
                .line()
                .appendFormat("직업: %s", dept)
                .line()
                .appendFormat("나이: %d", age);

        System.out.println(sp.toString());
    }
}
