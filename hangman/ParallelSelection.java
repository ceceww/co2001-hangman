package hangman;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 *
 * @author cecew
 */
public class ParallelSelection extends FileHandling implements Callable<List> {

    public ParallelSelection(String fileName) {
        super(fileName);
    }

    public List<String> call() throws IOException {

        return ParallelSelection.readFile(getFileName());

    }

    public static void main (String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(4);

        Future<List>[] results = new Future[4];

        List<String> lists = new ArrayList<>();

        long startTime = System.nanoTime();
        ParallelSelection list1 = new ParallelSelection("file1.txt");
        results[0] = es.submit(list1);
        try {
            results[0].get();
        }
        catch(Exception e){}

        ParallelSelection list2 = new ParallelSelection("file2.txt");
        results[1] = es.submit(list2);
        try {
            results[1].get();
        }
        catch(Exception e){}

        ParallelSelection list3 = new ParallelSelection("file3.txt");
        results[2] = es.submit(list3);
        try {
            results[2].get();
        }
        catch(Exception e){}

        ParallelSelection list4 = new ParallelSelection("file4.txt");
        results[3] = es.submit(list4);
        try {
            results[3].get();
        }
        catch(Exception e){}

        long endTime = System.nanoTime();

        es.shutdown();

        try{
            lists.add(results[0].get().toString());
            lists.add(results[1].get().toString());
            lists.add(results[2].get().toString());
            lists.add(results[3].get().toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        long threadTime = endTime - startTime;

        System.out.println("It took " + threadTime + " nanoseconds to make the selection");

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
