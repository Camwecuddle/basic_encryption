import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;

/**
 * The first value in the key is the number of spaces in the input
 */
class Encrypt {


    public Encrypt() {}

    public int[][] generateKey() {
        int[][] key = new int[2][2];
        for(int i = 0; i < key.length; i++) {
            for(int j = 0; j < key[i].length; j++) {
                key[i][j] = (int)(Math.random() * 20 + 1); //key[0][0] will be overwritten
            }
        }
        return key;
    }

    public int[] encryptOne(int[] letters, int[][] key) {
        int[] hold = new int[2];
        System.out.println("Pre: num1: " + letters[0]);
        System.out.println("Pre: num2: " + letters[1]);
        hold[0] = (letters[0] * key[0][0]) + (letters[0] * key[1][0]);
        hold[1] = (letters[1] * key[0][1]) + (letters[1] * key[1][1]);
        return hold;
    }

    public int[] decryptOne(int[] letters, int[][] key) {
        int[] hold = new int[2];
        hold[0] = (letters[0] * key[0][0]) + (letters[0] * key[1][0]);
        hold[1] = (letters[1] * key[0][1]) + (letters[1] * key[1][1]);
        //System.out.println(key[0][0]);
        //System.out.println(key[0][1]);
        //System.out.println(key[1][0]);
        //System.out.println(key[1][1]);
        return hold;
    }

    public int[] encryptString(String entry, int[][] key) {
        int index = 1; //int to get correct substring
        String input = entry;

        if(input.length() % 2 != 0) { //the string length has to be even for the matrix system to work
            input += " ";
        }

        int[] output = new int[input.length()];

        while(index < input.length()) {
            int[] hold = new int[2];
            hold[0] = input.charAt(index-1);
            hold[1] = input.charAt(index);
            hold = encryptOne(hold, key);
            output[index-1] = 33 + (hold[0] % 93);
            System.out.println("Pre: Letter1: " + output[index-1]);
            output[index] = 33 + (hold[1] % 93);
            System.out.println("Pre: Letter2: " + output[index]);
            index += 2;
        }
        return output;
    }

    public int[] encryptFile(String filename, int[][] key) {
        String entry = "";
        int keepReading = 1;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        while(keepReading != -1) {
            try {
                keepReading = reader.read();
                entry += (char) keepReading;
            } catch (IOException e) {
                System.out.println(e);
            }
            
        }
        System.out.println("Original: " + entry);
        return encryptString(entry, key);
    }

    public String decryptString(int[] encrypted, int[][] key) {
        int[][] inverseKey = new int[2][2];
        int determinant = 1/((key[0][0]*key[1][1]) - (key[0][1]*key[1][0]));
        System.out.println(determinant);
        inverseKey[0][0] = key[1][1]*determinant;
        inverseKey[1][1] = key[0][0]*determinant;
        inverseKey[0][1] = key[0][1] * -1 * determinant;
        inverseKey[1][0] = key[1][0] * -1 * determinant;
        int index = 1;
        String unencrypted = ""; 

        while(index < encrypted.length) {
            int[] hold = new int[2];
            hold[0] = encrypted[index-1];
            System.out.println("Post: Letter1: " + encrypted[index-1]);
            hold[1] = encrypted[index];
            System.out.println("Post: Letter2: " + encrypted[index]);
            hold = decryptOne(hold, inverseKey);
            unencrypted += (char) hold[0];
            unencrypted += " ";
            unencrypted += (char) hold[1];
            unencrypted += " ";
            System.out.println("Post: Output: " + unencrypted);
            index += 2;
        }
        
        return unencrypted;
    }




    public static void main(String[] args) {
        Encrypt test = new Encrypt();
        int[][] key = test.generateKey();
        int[] yeet = test.encryptFile("Cambo.txt", key);
        test.decryptString(yeet, key);
    }
}