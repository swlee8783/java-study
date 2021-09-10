package assignment;

/**
 * Created by 이시원 on 2021-09-05
 */
public class AssignmentWeek04 {
    public static void main(String[] args) {
        long totalCount = 127;
        long pageIndex = 13;

        Pager pager = new Pager(totalCount);
        System.out.println(pager.html(pageIndex));

    }
}
