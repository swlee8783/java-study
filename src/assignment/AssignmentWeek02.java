package assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 이시원 on 2021-08-20
 */
public class AssignmentWeek02 {

    public static void main(String[] args) {
        try {
            File file = new File("property.html");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("<!doctype html>");
            bufferedWriter.write("<html>");
            bufferedWriter.write("<head>");
            bufferedWriter.write("<meta charset=\"UTF-8\">");
            bufferedWriter.write("<style>");
            bufferedWriter.write("table { border-collapse: collapse; width: 100% }");
            bufferedWriter.write("th, td { border: solid 1px #000; }");
            bufferedWriter.write("</style>");
            bufferedWriter.write("</head>");
            bufferedWriter.write("<body>");
            bufferedWriter.write("<p style = \"font-size:200%\"><b>자바 환경정보</b></p>");
            bufferedWriter.write("<table>");
            bufferedWriter.write("<tr><th>키</th><th>밸류</th></tr>");

            for (Object k : System.getProperties().keySet()) {
                String key = k.toString();
                String value = System.getProperty(key);

                bufferedWriter.write("<tr><th>" + key + "</th><th>" + value +"</th></tr>");

            }

            bufferedWriter.write("</table>");
            bufferedWriter.write("</body>");
            bufferedWriter.write("</html>");

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
