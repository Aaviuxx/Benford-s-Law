import java.util.*; //Importing various things such as utilities or javafx components
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

public class BenfordsLaw extends Application { //Main class extends Application in order to run javafx

   public static double ones = 1; //Global variables used to store frequency values
   public static double twos = 1;
   public static double threes = 1;
   public static double fours = 1;
   public static double fives = 1;
   public static double sixes = 1;
   public static double sevens = 1;
   public static double eights = 1;
   public static double nines = 1;

/*
 * Author: Derek Xu
 * Takes in data for digit frequencies
 * Generates a bar graph corresponding to the data
*/

@Override
public void start(Stage s) { //Method to generate bar graph
    s.setTitle("Benford's Law"); //Sets the title of the graph on the window
    //x axis
    CategoryAxis x = new CategoryAxis(); //Creates new X Axis
    x.setLabel("Digits"); //Labels the X Axis
    //y axis
    NumberAxis y = new NumberAxis(); //Creates new Y Axis
    y.setLabel("Percent"); //Labels the Y Axis
    //bar chart creation
    BarChart barChart = new BarChart(x, y); //Creates new bar chart 
    //add values
    XYChart.Series ds = new XYChart.Series(); //initializes to set data
    ds.setName("Digits"); //Sets name of bars
    ds.getData().add(new XYChart.Data("1", ones)); //Sets the data for each bar
    ds.getData().add(new XYChart.Data("2"  , twos));
    ds.getData().add(new XYChart.Data("3"  , threes));
    ds.getData().add(new XYChart.Data("4"  , fours));
    ds.getData().add(new XYChart.Data("5"  , fives));
    ds.getData().add(new XYChart.Data("6"  , sixes));
    ds.getData().add(new XYChart.Data("7"  , sevens));
    ds.getData().add(new XYChart.Data("8"  , eights));
    ds.getData().add(new XYChart.Data("9"  , nines));
    barChart.getData().add(ds); //Adds data to chart
    VBox vbox = new VBox(barChart); 
    Scene sc = new Scene(vbox, 300, 200); //Creates new scene
    s.setScene(sc); //Inserts graph into window, sets dimensions of the window
    s.setHeight(500); //Sets width and height of window
    s.setWidth(600);
    s.show(); //Shows the window
}


public static void main(String[] args) throws IOException {
    Scanner user = new Scanner(System.in);
    String cont = "yes";
    do {
        System.out.println("*Please do not generate sales data more than once*")
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
        else if (ans.equalsIgnoreCase("generate")){ //If user inputs "generate", create graph, and exports data to a csv file
            exportSalesData(); //Calls on exportSalesData method
            Application.launch(args); //Launches graph (Cannot be invoked more than once)
        } 
        
        else { //If the user inputs something invalid, print string
            System.out.println("invalid input.");
        }
        System.out.println("Would you like to try a different option?");
        cont = user.nextLine();

    }
    while (cont.equals("yes"));
    user.close();
    

}

/*
 * Author: Derek Xu 
 * Reads the sales.csv file in order to gather data
 * returns digit frequencies and exports the data into a csv file
*/

public static void exportSalesData() throws IOException { //exportSalesData method
    
   String currentNum = ""; //Initialize variable
   
   File file = new File("sales.csv"); //Creates file to read
   Scanner myReader = new Scanner(file); //Creates new scanner to read the file

   while (myReader.hasNextLine()){ //While loop to check if the file still has data to read
       for (int i = 0; i < 1621; i++){ //loops to read each of the 1621 lines in the csv file
           currentNum = myReader.nextLine().substring(4, 5); //Current Number reads the first digit of the sales number

           if (currentNum.equals("1")){ //If currentNum is *digit*, add one to the counter for that digit
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
      ones = ones / 1621 * 100; //Turns each digit set into a percentage to a single decimal
      twos = twos / 1621 * 100;
      threes = threes / 1621 * 100;
      fours = fours / 1621 * 100;
      fives = fives / 1621 * 100;
      sixes = sixes / 1621 * 100;
      sevens = sevens / 1621 * 100;
      eights = eights / 1621 * 100;
      nines = nines / 1621 * 100;

    File results = new File("results.csv"); //Creates new csv file

    FileWriter fw = new FileWriter(results); //Creates a new file writer
    fw.write("Digit Frequencies\n"); //Writes data to the file
    fw.write("1. (" + (int)(ones * 10)/10.0 + "%)\n");
    fw.write("2. (" + (int)(twos * 10)/10.0 + "%)\n");
    fw.write("3. (" + (int)(threes * 10)/10.0 + "%)\n");
    fw.write("4. (" + (int)(fours * 10)/10.0 + "%)\n");
    fw.write("5. (" + (int)(fives * 10)/10.0 + "%)\n");
    fw.write("6. (" + (int)(sixes * 10)/10.0 + "%)\n");
    fw.write("7. (" + (int)(sevens * 10)/10.0 + "%)\n");
    fw.write("8. (" + (int)(eights * 10)/10.0 + "%)\n");
    fw.write("9. (" + (int)(nines * 10)/10.0 + "%)\n");
    fw.flush(); //Flushes and closes the file writer
    fw.close();
   }
   
}  

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
 * uses array frequency to store the amount of times that numbers from 1-9 appear in the .csv file.
 * calculates percentage of each number's frequency using total amount of numbers found.
 * @author Ayeh Fartousi
 * @param sales is the array containing all numbers from .csv file
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




}