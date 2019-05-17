import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * The first value in the key is the number of spaces in the input
 */
class Encrypt {


    public Encrypt() {}

    public double[][] generateKey() {
        double[][] key = new double[2][2];
        for(int i = 0; i < key.length; i++) {
            for(int j = 0; j < key[i].length; j++) {
                key[i][j] = Math.random() * 20 + 1; //key[0][0] will be overwritten
            }
        }
        return key;
    }

    public double[] encryptOne(double[] letters, double[][] key) {
        double[] hold = new double[2];
        //System.out.println("Pre: num1: " + letters[0]);
        //System.out.println("Pre: num2: " + letters[1]);
        hold[0] = (letters[0] * key[0][0]) + (letters[0] * key[1][0]);
        hold[1] = (letters[1] * key[0][1]) + (letters[1] * key[1][1]);
        return hold;
    }

    public double[] decryptOne(double[] letters, double[][] inverseKey) {
        double[] hold = new double[2];
        hold[0] = (letters[0] * inverseKey[0][0]) + (letters[0] * inverseKey[1][0]);
        hold[1] = (letters[1] * inverseKey[0][1]) + (letters[1] * inverseKey[1][1]);
        //System.out.println(key[0][0]);
        //System.out.println(key[0][1]);
        //System.out.println(key[1][0]);
        //System.out.println(key[1][1]);
        return hold;
    }

    public double[] encryptString(String entry, double[][] key) {
        int index = 1; //int to get correct substring
        String input = entry;

        if(input.length() % 2 != 0) { //the string length has to be even for the matrix system to work
            input += " ";
        }

        double[] output = new double[input.length()];

        while(index < input.length()) {
            double[] hold = new double[2];
            hold[0] = input.charAt(index-1);
            hold[1] = input.charAt(index);
            hold = encryptOne(hold, key);
            output[index-1] = 33 + (hold[0] % 93);
            //System.out.println("Pre: Letter1: " + output[index-1]);
            output[index] = 33 + (hold[1] % 93);
            //System.out.println("Pre: Letter2: " + output[index]);
            index += 2;
        }
        return output;
    }

    public double[] encryptFile(String filename, double[][] key) {
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
                //System.out.println(keepReading);
                entry += (char) keepReading;
            } catch (IOException e) {
                System.out.println(e);
            }
            
        }
        System.out.println("Original: " + entry);
        return encryptString(entry, key);
    }

    public ArrayList decryptString(double[] encrypted, double[][] key) {
        double[][] inverseKey = new double[2][2];
        double determinant = 1/((key[0][0]*key[1][1]) - (key[0][1]*key[1][0]));
        System.out.println("determinant = " + determinant);
        inverseKey[0][0] = key[1][1]*determinant;
        inverseKey[1][1] = key[0][0]*determinant;
        inverseKey[0][1] = key[0][1] * -1 * determinant;
        inverseKey[1][0] = key[1][0] * -1 * determinant;
        printKey(key);
        printKey(inverseKey);
        int index = 1;
        ArrayList unencrypted = new ArrayList(); 

        while(index < encrypted.length) {
            double[] hold = new double[2];
            hold[0] = encrypted[index-1];
            //System.out.println("Post: Letter1: " + encrypted[index-1]);
            hold[1] = encrypted[index];
            //System.out.println("Post: Letter2: " + encrypted[index]);
            hold = decryptOne(hold, inverseKey);
            unencrypted.add(hold[0]);
            unencrypted.add(hold[1]);
            //System.out.println("Post: Output: " + unencrypted);
            index += 2;
        }
        
        return unencrypted;
    }

    public void printKey(int[][] key) {
        System.out.println(key[0][0] + " , " + key[0][1]);
        System.out.println(key[1][0] + " , " + key[1][1]);
    }

    public void printKey(double[][] key) {
        System.out.println(key[0][0] + " , " + key[0][1]);
        System.out.println(key[1][0] + " , " + key[1][1]);
    }

    public void printEncryptedArray(double[] encryptedLetters) {
        System.out.println("Encrypted:");
        for(int i = 0; i < encryptedLetters.length; i++) {
            System.out.print(encryptedLetters[i]);
        }
        System.out.println("");
    }

    public void printUnencryptedArray(ArrayList unencryptedLetters) {
        System.out.println("Unencrypted:");
        for (int i = 0; i < unencryptedLetters.size(); i++) {
            System.out.println(unencryptedLetters.get(i));
          }
        System.out.println("");
    }



    public static void main(String[] args) {
        Encrypt test = new Encrypt();
        double[][] key = test.generateKey();

        double[] yeet = test.encryptFile("Cambo.txt", key);
        test.printEncryptedArray(yeet);
        ArrayList unencrypted = test.decryptString(yeet, key);
        test.printUnencryptedArray(unencrypted);

    }
}