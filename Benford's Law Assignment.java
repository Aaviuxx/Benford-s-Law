/**
 * Date: May 5, 2021
 * Teacher: Mr. Ho
 * Name: Ayeh Fartousi & Derek Xu
 * Description: Part Two of Luhn Algorithmn Assignemnt
 */

// import Java packages here
import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException; 
import java.lang.Math;

public class BenfordLaw extends Application{

    public static double ones = 1;
    public static double twos = 1;
    public static double threes = 1;
    public static double fours = 1;
    public static double fives = 1;
    public static double sixes = 1;
    public static double sevens = 1;
    public static double eights = 1;
    public static double nines = 1;

    @Override
    public void start(Stage s){
        s.setTitle("Benford's Law");
        //x axis
        CategoryAxis x    = new CategoryAxis();
        x.setLabel("Digits");
        //y axis
        NumberAxis y = new NumberAxis();
        y.setLabel("Percent");
        //bar chart creation
        BarChart bc = new BarChart(x, y);
        //add values
        XYChart.Series ds = new XYChart.Series();
        ds.setName("Digits");
        ds.getData().add(new XYChart.Data("1", ones));
        ds.getData().add(new XYChart.Data("2"  , twos));
        ds.getData().add(new XYChart.Data("3"  , threes));
        ds.getData().add(new XYChart.Data("4"  , fours));
        ds.getData().add(new XYChart.Data("5"  , fives));
        ds.getData().add(new XYChart.Data("6"  , sixes));
        ds.getData().add(new XYChart.Data("7"  , sevens));
        ds.getData().add(new XYChart.Data("8"  , eights));
        ds.getData().add(new XYChart.Data("9"  , nines));
        bc.getData().add(ds);
        //vertical box
        VBox vbox = new VBox(bc);
        Scene sc = new Scene(vbox, 300, 200);
        s.setScene(sc);
        s.setHeight(500);
        s.setWidth(600);
        s.show();
    }
    public static void main(String [] args) throws FileNotFoundException{
        Scanner user = new Scanner(System.in);
        String cont = "yes";
        do {
            System.out.println("if you would like to read the file containing sales, enter 'read'");            
            System.out.println("if you would like to check the file for possible accounting fraud, enter 'check'");  
            System.out.println("If you would like to export and generate the sales data, enter 'generate'");       
            String ans = user.nextLine();

            if (ans.equalsIgnoreCase("read")){                               // code will play if the user responds with "yes"
                loadSalesData();                                            // reads file
                analyzeSalesData(loadSalesData());                          // calculates percentages
                printSalesData(analyzeSalesData(loadSalesData()));
            } 
            else if (ans.equalsIgnoreCase("check")) {
                checkSalesData(analyzeSalesData(loadSalesData()));
            }
            else if (ans.equalsIgnoreCase("generate")){ //Thing
                exportSalesData();
                Application.launch(args);
                
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
    public static void exportSalesData() {
        String currentNum = "";
        
        File file = new File("sales.csv");
        Scanner myReader = new Scanner(file);

        while (myReader.hasNextLine()){
            for (int i = 0; i < 1621; i++){
                currentNum = myReader.nextLine().subString(4);

                if (currentNum.equals("1")){
                    ones = ones + 1;
                }

                else if (currentNum.equals("2")){
                    twos = twos + 1;
                }

                else if (currentNum.equals("3")){
                    threes = threes + 1;
                }

                else if (currentNum.equals("4")){
                    fours = fours + 1;
                }

                else if (currentNum.equals("5")){
                    fives = fives + 1;
                }

                else if (currentNum.equals("6")){
                    sixes = sixes + 1;
                }

                else if (currentNum.equals("7")){
                    sevens = sevens + 1;
                }

                else if (currentNum.equals("8")){
                    eights = eights + 1;
                }

                else if (currentNum.equals("9")){
                    nines = nines + 1;
                }
            }
        }
        


    }  

    
}