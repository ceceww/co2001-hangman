package hangman;

import java.io.Serializable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 *
 * @author cecew
 */
public class FileHandling implements Serializable {

    private static String fileName;


    public FileHandling(String fName) {
        fileName = fName;

    }

    public static String getFileName(){
        return fileName;
    }

    public static List<String> readFile(String fileName) {

        List<String> list = new ArrayList<>();

        BufferedReader br = null;

        try {
            
            Scanner sc = new Scanner(new FileInputStream(fileName));
            int count=0;
            while(sc.hasNext()){
                sc.next();
                count++;
            }
            System.out.println("Number of words: " + count);
            
            br = new BufferedReader(new FileReader(fileName));

            String word;
            
            List<String> randomInts = new ArrayList<String>();
            
            while (randomInts.size()<50){
                Random random = new Random();
                int n = random.nextInt(count);
                if(!randomInts.contains(Integer.toString(n))){
                    randomInts.add(Integer.toString(n));
                }
            }
            for(int k = 0; k < 50; k++){
                    word = Files.readAllLines(Paths.get(fileName)).get(Integer.valueOf(randomInts.get(k)));
                    list.add(word);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        finally {
            try{

                br.close();

            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

        return list;
    }
  
   public static void main (String[] args){

        List lists = new ArrayList<>();

        long startTime = System.nanoTime();

        List<String> list1 = readFile("file1.txt");
        List<String> list2 = readFile("file2.txt");
        List<String> list3 = readFile("file3.txt");
        List<String> list4 = readFile("file4.txt");

        long threadTime = System.nanoTime() - startTime;

        System.out.println("It took " + threadTime + " nanoseconds to make the selection.");

        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);

        System.out.println(lists);

        try {
            FileOutputStream fo =
                    new FileOutputStream("Lists.ser");
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(lists);
            out.close();
            fo.close();
            System.out.println("Serialized data is saved in Lists.ser");
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}