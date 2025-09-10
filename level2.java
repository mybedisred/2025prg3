import java.util.regex.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
//author: Spencer Gilcrest
//date: 9/5/2025
//this program searches through the sourcepage.txt file and finds all the letters and puts them into a string to make a word

public class level2 {
    public static void main(String[] args){
        String sourcepage = "level2source.txt";

        try {
            String stuff = new String(Files.readAllBytes(Paths.get(sourcepage)));
            Pattern letters = Pattern.compile("[a-zA-Z]");
            Matcher match = letters.matcher(stuff);

            StringBuilder result = new StringBuilder();
            while (match.find()){
                result.append(match.group());
            }

            System.out.println(result.toString());
            
            
        }
        catch (IOException e){
                System.out.println(e.getMessage());
            }
    }
}
