package com.wyattfredrickson.WeightedGraphExample;
import java.util.Scanner;

import com.wyattfredrickson.WeightedGraphExample.WeightedGraph.SearchTree;

import java.io.*; 
import java.util.*; 


/**
 * 
 * This class is the main class for the project
 */
public class Main {

    /**
     * This method prints the developer information
     */
    void printDeveloperInfo() {
        System.out.println("Submitted by Wyatt Fredrickson - fredricw@csp.edu");
        System.out.println("I certify that this is my own work");
        System.out.println("Sources include: Introduction to Java Programming and Data Structures, Comprehensive Version, 12th Edition - Y. Daniel Liang");
    }
    /**
     * This method displays the user menu for selecting a starting and destination city via the console 
     */
    void userMenu(Scanner scanner, WeightedGraph<String> graph) {
        System.out.println("Please select a starting city: <Enter the city number>");
        List<String> cities = graph.getVertices(); // Get the list of cities from the graph
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(i + " > " + cities.get(i)); 
        }
        System.out.println("Please enter starting city:");
        int startingCity = scanner.nextInt(); // Read the user input for the starting city
        System.out.println("Please enter destination city:");
        int destinationCity = scanner.nextInt(); // Read the user input for the destination city
        // If the starting city or destination city is invalid
        if (startingCity < 0 || startingCity >= cities.size() || destinationCity < 0 || destinationCity >= cities.size()) {
            System.out.println("Invalid city number, please try again!"); 
            return; // Return from the method if the city number is invalid
        }
        String startCityName = cities.get(startingCity); // Get the name of the starting city
        String endDestinationName = cities.get(destinationCity); // Get the name of the destination city

        WeightedGraph<String>.SearchTree searchTree = graph.findShortestPath(startCityName); // Find the shortest path from the starting city
        List<Integer> path = searchTree.getPath(destinationCity); // Get the path from the starting city to the destination city
        if (path.size() <= 1) {
            System.out.println("No path found between " + startCityName + " and " + endDestinationName); // Print a message if no path is found between the starting and destination cities
            return; // Return from the method if no path is found
        }

        System.out.println("The shortest path from " + startCityName + " to " + endDestinationName + " is: ");
        double theTotalMilesDistance = 0.0; // Initialize the ticket to ride variable to 0
        for (int i = 0; i < path.size(); i++) {
            System.out.print(cities.get(path.get(i))); // get the city name from the path list and print it using the city index as the key
            if (i < path.size() - 1) { // If the index is less than the size of the path - 1
                double storeDistanceBetween = graph.getEdgeWeight(cities.get(path.get(i)), cities.get(path.get(i + 1))); // Get the edge weight between the current city and the next city (miles distance) 
                theTotalMilesDistance += storeDistanceBetween; // Add the segment distance to the total miles distance segment distance means
                System.out.print("(" + (int)storeDistanceBetween + " miles) >> ");
            }
        }
        System.out.println(" (Total distance: " + (int)theTotalMilesDistance + " miles)");
    }

    
    public static void main( String[] args ) throws FileNotFoundException {
        Main main = new Main(); // Create an instance of the Main class
        main.printDeveloperInfo(); // Call the printDeveloperInfo method

        WeightedGraph<String> graph = new WeightedGraph<>(); // Create a new WeightedGraph object with a String type
        ProcessCityDataFile processCityDataFile = new ProcessCityDataFile(); // Create a new ProcessCityDataFile object
        processCityDataFile.loadDataFromFile("City-City-Miles.txt", graph); // Call the loadDataFromFile method and pass the filename and graph objects as parameters
        Scanner scanner = new Scanner(System.in); // Create a new Scanner object
        main.userMenu(scanner, graph); // Call the userMenu method and pass the scanner and graph objects as parameters
        scanner.close(); // Close the scanner

    }

}