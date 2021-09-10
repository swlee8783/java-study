package e01;

import java.util.Locale;

public class 문자열테스트 {

    public static void p (Object obj) {
        System.out.println(obj);
    }

    public static void main(String[] args) {

        System.out.println("TEST");

        String url = "https://FastCampus.co.kr/";

        final String FS = "http://fastcampus.co.kr/";

        //length()
        int length = url.length();
        System.out.println("문자열 길이: " + length);

        //charAt()
        for(int  i = 0; i < url.length(); i++) {
            p(url.charAt(i));
        }

        //toCharArray()
        for(char c1: url.toCharArray()){
            p(c1);
        }

        // contains(), indexOf(), lastIndexOf()
        p(url.contains("http"));
        p(url.indexOf(".co"));
        p(url.lastIndexOf(".co"));

        p("[" + url + "]");
        p("[" + url.trim() + "]");
        p(url.toLowerCase());
        p(url.toUpperCase());

        //equals()
        p(url.equals(FS));
        p(url.equalsIgnoreCase(FS));

        String url2 = "http://www.naver.com/news/tv/sbs";
        // news,  tv,  sbs
        // 분류,   매체,  방송국
        // new.   radio, kbs
        p(url2);
        p(url2.replace("http://www.naver.com", ""));
        String[] url2List = url2.replace("http://www.naver.com", "").split("/");
        p("분류: " + url2List[0]);
        p("매체: " + url2List[1]);
        p("방송국: " + url2List[2]);

        //카테고리 상품
        //http://www.coupang.com/tv/item001
        //http://www.naver.com/news/tv/sbs

        // concat()
        p(url2.concat("/index.html"));
        p(url2+"/index.html");

        p(url.substring(8));

    }
}
