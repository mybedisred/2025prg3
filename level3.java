import java.util.regex.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

//author: Spencer Gilcrest
//date: 9/7/2025
//finds all lowercase letters surrounded by 3 uppercase letters (not part of long uppercase string) then puts them together to make a word
public class level3 {
    public static void main(String[] args){
        String sourcepage = "level3source.txt";

        try {
            String stuff = new String(Files.readAllBytes(Paths.get(sourcepage)));
            Pattern pattern = Pattern.compile("[^A-Z]+[A-Z]{3}([a-z])[A-Z]{3}[^A-Z]+");
            Matcher match = pattern.matcher(stuff);

            String result = "";
            while (match.find()){
                result += match.group(1);
            }

            System.out.println(result);
            
            
        }
        catch (IOException e){
                System.out.println(e.getMessage());
            }
    }
}
