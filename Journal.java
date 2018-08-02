import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


/**
 * Driver to write a encrypted journal entry
 */
public class Journal {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What is the filename?");
        String fileName = in.nextLine();
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("gedit " + fileName + ".txt");
            proc.waitFor();
        }
        catch(IOException | InterruptedException e) {
            System.out.println(e);
        }   
        System.out.println("What is the question?");
        String question = in.nextLine();
        System.out.println("What is the answer?");
        String answer = in.nextLine();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("questions.txt"));
            writer.write(fileName + "\n" + question + "\n" + answer);
            writer.close();
        } catch (IOException e) {}
        in.close();
    }
}