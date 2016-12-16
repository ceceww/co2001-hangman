package hangman;

import java.io.Serializable;
import java.io.*;
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
            br = new BufferedReader(new FileReader(fileName));

            String words;

            for (int i = 0; i < 50; i++) {
                words = br.readLine();
                list.add(words);
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