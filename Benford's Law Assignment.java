/**
 * Date: May 5, 2021
 * Teacher: Mr. Ho
 * Name: Ayeh Fartousi & Derek Xu
 * Description: Part Two of Luhn Algorithmn Assignemnt
 */

// import Java packages here
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException; 
import java.lang.Math;

class BenfordLaw {
    public static void main(String [] args) {
        Scanner user = new Scanner(System.in);
        String cont = "yes";
        do {
            System.out.println("if you would like to read the file containing sales, enter 'read'");            
            System.out.println("if you would like to check the file for possible accounting fraud, enter 'check'");         
            String ans = user.nextLine();

            if (ans.equalsIgnoreCase("read")){                               // code will play if the user responds with "yes"
                loadSalesData();                                            // reads file
                analyzeSalesData(loadSalesData());                          // calculates percentages
                printSalesData(analyzeSalesData(loadSalesData()));
            } 
            else if (ans.equalsIgnoreCase("check")) {
                checkSalesData(analyzeSalesData(loadSalesData()));
            }
            else {
                System.out.println("invalid input.");
            }
            System.out.println("Would you like to try a different option?");
            cont = user.nextLine();
        }
        while (cont.equals("yes"));
        user.close();
    }

    /**
     * @author Ayeh Fartousi
     * @return sales array containing only numbers from the .csv file
     */
    public static String [] loadSalesData () {
        String [] sales = new String [1621];                        // creates array with an index the size of the number of the lines in the file
        try {                                                       // try and catch, in case no file is found
            File file = new File("sales.csv");                      // .csv file is read
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {              
                for (int i = 0; i < 1621; i++) {                    
                    sales [i] = reader.nextLine().substring(4);     // ignores the codes and seperators, and saves only the number values
                    //System.out.println(sales[i]);
                }
            }
            reader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");               // catches errors
            e.printStackTrace();
        }
        return sales;                                               // returns arrays with only numbers from the .csv file
    }

    /**
     * @author Ayeh Fartousi
     * @return frequency array containing the frequencies of each number
     */
    public static double [] analyzeSalesData (String [] sales) {
        double [] frequency = new double [9];                       // new array is creaed to calculate frequency of each number
        for (int i = 0; i < frequency.length; i++) {
            frequency [i] = 0;                                      // each index of the array is preset to zero
        }
        for (int j = 0; j < 1621; j++) {                            // the array containing all the numbers is read line by line
            for (int i = 0; i < frequency.length; i++) {      
                if (sales [j].substring(0, 1).equals(String.valueOf(i+1))) {        // if the number begins with the value of i+1 (which is a number from 1-9 in order)
                    frequency [i] += 1;                                             // one is added to the frequency
                }
            }
        }
                
        double total = 0;                                           // total is preset to zero
        for (int i = 0; i < frequency.length; i++) {
             total = total + frequency [i];                         // all the numbers in the frequency array are added to calc total
        }
        for (int i = 0; i < frequency.length; i++) {
            frequency [i] = Math.round(frequency[i]*100/total);
        }
        return frequency;                               // an array of all the frequencies is returned
    }
    /**
     * @author Ayeh Fartousi
     */
    public static void printSalesData (double [] frequency) {
        double total = 0;                                           // total is preset to zero
        for (int i = 0; i < frequency.length; i++) {
             total = total + frequency [i];                         // all the numbers in the frequency array are added to calc total
        }
        System.out.println("total: " + total);

        System.out.println("frequency of each number:");
        for (int i = 0; i < frequency.length; i++) {    // percentage of frequency of each number is calculated using the number and total
            System.out.println("frequency of " + (i + 1) + ": " + Math.round(frequency[i]*100/total) + "%");      
        }
    }

    /**
     * @author Ayeh Fartousi
     */
    public static void checkSalesData (double [] freq) {
        if (29 < freq [0] && freq [0] < 32) {
            System.out.println("no fraud has occured.");
        }
        else {
            System.out.println("fraud may have occured.");
        }
    }

    /**
     * @author Derek Xu
     */
    public static void exportSalesData (String [] args) {
        
    }  
}