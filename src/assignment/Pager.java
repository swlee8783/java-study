package assignment;

/**
 * Created by 이시원 on 2021-09-05
 */
public class Pager {

    private static long pagesPerBlock = 10;
    private long totalCount;
    private long totalIndex;

    Pager(long totalCount){
        this.totalCount = totalCount;
    }

    public String html(long pageIndex){
        long startIndex;
        long endIndex;

        totalIndex = setTotalIndex(totalCount, pagesPerBlock);
        if(validatePageIndex(totalIndex, pageIndex)) {
            startIndex = setStartBlock(pageIndex);
            endIndex = setEndBlock(pageIndex);

            return makeHtml(startIndex, endIndex, pageIndex);
        }

        return "pageIndex 값이 totalCount 값을 초과했습니다.";
    }

    private String makeHtml(long startIndex, long endIndex, long pageIndex) {
        if(startIndex == pageIndex && endIndex == pageIndex) {
            return makeHtmlByPageIndex(pageIndex);
        }

        return makeHtmlByAllParams(startIndex, endIndex, pageIndex);
    }

    private String makeHtmlByAllParams(long startIndex, long endIndex, long pageIndex) {
        StringBuilder sb = new StringBuilder();

        sb.append("<a href='#'>[처음]</a>\n");
        sb.append("<a href='#'>[이전]</a>\n\n");

        for(long i = startIndex; i <= endIndex; i++) {
            if(i == pageIndex) {
                sb.append("<a href='#' class='on'>")
                        .append(pageIndex)
                        .append("</a>\n");
            } else {
                sb.append("<a href='#'>")
                        .append(i)
                        .append("</a>\n");
            }
        }
        sb.append("\n");
        sb.append("<a href='#'>[다음]</a>\n");
        sb.append("<a href='#'>[마지막]</a>\n");

        return sb.toString();
    }

    private String makeHtmlByPageIndex(long pageIndex) {
        StringBuilder sb = new StringBuilder();

        sb.append("<a href='#'>[처음]</a>\n");
        sb.append("<a href='#'>[이전]</a>\n\n");
        sb.append("<a href='#' class='on'>")
                .append(pageIndex)
                .append("</a>\n\n");
        sb.append("<a href='#'>[다음]</a>\n");
        sb.append("<a href='#'>[마지막]</a>\n");

        return sb.toString();
    }

    private boolean validatePageIndex(long totalIndex, long pageIndex) {
        return totalIndex >= pageIndex;
    }

    private long setEndBlock(long pageIndex) {
        String temp = (pageIndex % pagesPerBlock) == 0 ?
                (Long.toString(pageIndex / pagesPerBlock) + "0") :
                (Long.toString((pageIndex / pagesPerBlock) + 1L) + "0");
        return Math.min(Long.parseLong(temp), totalIndex);
    }

    private long setStartBlock(long pageIndex) {
        long overTen = (pageIndex / pagesPerBlock);
        String temp = (pageIndex % pagesPerBlock) == 0 ?
                Long.toString(overTen - 1L) + "1" :
                Long.toString(overTen) + "1";
        return Long.parseLong(temp);
    }

    private long setTotalIndex(long totalCount, long pagesPerBlock) {
        if(totalCount % pagesPerBlock == 0) {
            totalIndex = totalCount / pagesPerBlock;
        }
        else {
            totalIndex = (totalCount / pagesPerBlock) + 1;
        }
        return totalIndex;
    }

}
