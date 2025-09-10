import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Author: Spencer Gilcrest
//Date: 9/10/25
//This program searches through a link, finds the number clue for the next link, goes to the new link and repeats the process until the puzzle is over
public class level4 {
     public static void main(String[] args) throws IOException {
          boolean done = false;
          String result = "12345";
          
          while(!done){
               
               URL url = new URL("http://www.pythonchallenge.com/pc/def/linkedlist.php?nothing=" + result);
               BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
               String line = reader.readLine();
               String line2 = reader.readLine();
               if (line2 != null){
                    line += line2;
               }
               System.out.println(line);
         
               Pattern pattern = Pattern.compile("nothing is (\\d+)");
               Matcher match = pattern.matcher(line);

               while (match.find()){
                    result = match.group(1);
               }
               if (line.equals("Yes. Divide by two and keep going.")){
                    int linenumber = Integer.parseInt(result);
                    linenumber/=2;
                    result = linenumber + "";
               }
               if (line.equals("peak.html")){
                    done = true;
               }

               System.out.println(result);
             
          }
        
          }
}
