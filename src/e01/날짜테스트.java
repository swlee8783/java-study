package e01;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class 날짜테스트 {
    public static void main(String[] args) {

        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(simpleDateFormat.format(today));

        int year = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(year);
    }
}
