package e01;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test3 {
    public static void main(String[] args) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime aWeekAgo = currentDate.atStartOfDay().minusDays(7L);
        LocalDate yesterday = currentDate.minusDays(1L);

        System.out.println(aWeekAgo.toLocalDate());

        for(LocalDate date = aWeekAgo.toLocalDate(); date.isBefore(yesterday); date = date.plusDays(1L)) {
            System.out.println(date);
        }

        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");

        System.out.println(map.get("3"));




    }
}
