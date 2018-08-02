import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


/**
 * The first value in the key is the number of spaces in the input
 */
public class AddQuestion {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What is the filename?");
        String fileName = in.nextLine();
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