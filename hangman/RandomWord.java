package hangman;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author cecew
 */
public class RandomWord {
    
    public static String randomWord(int wordLength, String fileName) {
        ArrayList<String> list;
        ArrayList<String> list2 = new ArrayList<>();
        Random random = new Random();
        int randomNum = random.nextInt(50);
        String randomWord = "";
        try{
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (ArrayList) in.readObject();
            in.close();
            fileIn.close();
            String s = list.toString();
            String[] words = s.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
            }
            
            for (int i=0; i<words.length; i++){
                if (words[i].length()==wordLength){
                    list2.add(words[i]);
                }
            }
            randomWord = list2.get(randomNum);
            
        }
        catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return randomWord;
    }
    
    public static void main(String [] args) {
        System.out.println(randomWord(5, "Lists.ser"));
        

    }

}
