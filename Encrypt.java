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

    public double[][] generateKey() {
        double[][] key = new double[2][2];
        for(int i = 0; i < key.length; i++) {
            for(int j = 0; j < key[i].length; j++) {
                key[i][j] = (double)(Math.random() * 20 + 1); //key[0][0] will be overwritten
            }
        }
        return key;
    }

    public double[] encryptOne(double[] letters, double[][] key) {
        double[] hold = new double[2];
        hold[0] = (letters[0] * key[0][0]) + (letters[0] * key[1][0]);
        hold[1] = (letters[1] * key[0][1]) + (letters[1] * key[1][1]);
        return hold;
    }

    public double[] decryptOne(double[] letters, double[][] key) {
        double[] hold = new double[2];
        hold[0] = (letters[0] * key[0][0]) + (letters[0] * key[1][0]);
        hold[1] = (letters[1] * key[0][1]) + (letters[1] * key[1][1]);
        //System.out.println(key[0][0]);
        //System.out.println(key[0][1]);
        //System.out.println(key[1][0]);
        //System.out.println(key[1][1]);
        return hold;
    }

    public String encryptString(String entry, double[][] key) {
        int index = 1; //int to get correct substring
        String input = entry;
        String output = "";

        if(input.length() % 2 != 0) { //the string length has to be even for the matrix system to work
            input += " ";
        }

        while(index < input.length()) {
            double[] hold = new double[2];
            hold[0] = input.charAt(index-1);
            hold[1] = input.charAt(index);
            hold = encryptOne(hold, key);
            output += (char)(33 + (hold[0] % 93));
            output += (char)(33 + (hold[1] % 93));
            index += 2;
        }


        return output;
    }

    public String encryptFile(String filename, double[][] key) {
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

    public String decryptString(String encrypted, double[][] key) {
        double[][] inverseKey = new double[2][2];
        double determinant = 1/((key[0][0]*key[1][1]) - (key[0][1]*key[1][0]));
        System.out.println(determinant);
        inverseKey[0][0] = key[1][1]*determinant;
        inverseKey[1][1] = key[0][0]*determinant;
        inverseKey[0][1] = key[0][1] * -1 * determinant;
        inverseKey[1][0] = key[1][0] * -1 * determinant;
        int index = 1;
        String unencrypted = ""; 

        while(index < encrypted.length()) {
            double[] hold = new double[2];
            hold[0] = encrypted.charAt(index-1);
            hold[1] = encrypted.charAt(index);
            hold = decryptOne(hold, inverseKey);
            unencrypted += (char)(33 + (hold[0] % 93));
            unencrypted += (char)(33 + (hold[1] % 93));
            index += 2;
        }
        
        return unencrypted;
    }




    public static void main(String[] args) {
        Encrypt test = new Encrypt();
        double[][] key = test.generateKey();
        String yeet = test.encryptFile("Cambo.txt", key);
        System.out.println("encypted: " + yeet);
        System.out.println("unencrypted: " + test.decryptString(yeet, key));
    }
}