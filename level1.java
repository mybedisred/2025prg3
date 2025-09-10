
//Author: Spencer Gilcrest
//Date: 9/3/25
//The cipher class has a method that takes a string input and moves every character forward 2 letters in the alphabet to decode the input

public class level1{
    public static String decode(String input){
        String output = "";
        for (int i = 0; i < input.length(); i++){
            char currChar = input.charAt(i);

            if (currChar >= 'a' && currChar <= 'z'){
                char shiftedChar = (char)(currChar + 2);
                if (shiftedChar > 'z'){
                    shiftedChar -= 26;
                }
                output += shiftedChar;
            }
            else {
                output += currChar;
            }
        }
        return output;
    }

public static void main(String[] args){
    String problem1 = "g fmnc wms bgblr rpylqjyrc gr zw fylb. rfyrq ufyr amknsrcpq ypc dmp. bmgle gr gl zw fylb gq glcddgagclr ylb rfyr'q ufw rfgq rcvr gq qm jmle. sqgle qrpgle.kyicrpylq() gq pcamkkclbcb. lmu ynnjw ml rfc spj.";
    String answer = decode(problem1);
    System.out.println(answer);
}
}


 
