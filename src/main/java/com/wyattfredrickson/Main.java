package com.wyattfredrickson;
import java.util.Scanner;
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
     * This method loads data from a file and populates the graph and cityToIndexMap objects 
     * @param filename the name of the file to load data from
     * @param graph the graph object to populate using a WeightedGraph data structure
     * @throws FileNotFoundException if the file is not found 
     */
    void loadDataFromFile(String filename, WeightedGraph<String> graph) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) { // Create a new Scanner object and pass the filename as a parameter
            while(scanner.hasNext()) {
                String line = scanner.nextLine().trim(); // Read the next line from the file and remove any leading or trailing whitespace
                if (line.isEmpty()) continue; // Skip to the next iteration of the loop if the line is empty
                
                String[] tokens = line.split(","); // Split the line into an array of tokens using a comma as the delimiter
                if (tokens.length != 3) { // Check if the line contains 3 tokens if not print an error message and skip to the next iteration of the loop
                    System.out.println("Invalid line: " + line); 
                    continue; 
                }
                
                try { 
                    ProcessCityDataFile data = new ProcessCityDataFile(tokens[0].trim(), tokens[1].trim(), Integer.parseInt(tokens[2].trim())); // Create a new ProcessCityDataFile object and pass the tokens as parameters

                // Add vertices to the graph if they don't already exist
                if (!graph.containsVertex(data.getFirstChoice())) { // Check if the graph contains the first choice
                    graph.addVertex(data.getFirstChoice()); // Add the first choice as a vertex to the graph
                }
                if (!graph.containsVertex(data.getSecondChoice())) { // Check if the graph contains the second choice
                    graph.addVertex(data.getSecondChoice()); // Add the second choice as a vertex to the graph
                }

                int u = graph.getVertexIndex(data.getFirstChoice()); // Get the index of the first choice
                int v = graph.getVertexIndex(data.getSecondChoice()); // Get the index of the second choice
                graph.addEdge(u, v, data.getValue()); // Add an edge between the first and second choices with the value as the weight
                } catch (NumberFormatException e) {
                    System.out.println("Invalid distance: " + line); // Print an error message if the distance is invalid
                }
            }  
        } 
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
        double theTicketToRide = 0.0; // Initialize the ticket to ride variable to 0
        for (int i = 0; i < path.size(); i++) {
            System.out.print(cities.get(path.get(i))); // get the city name from the path list and print it using the city index as the key
            if (i < path.size() - 1) { // If the index is less than the size of the path - 1
                theTicketToRide += graph.getEdgeWeight(cities.get(path.get(i)), cities.get(path.get(i + 1))); // Add the edge weight to the ticket to ride variable
                System.out.print(" >> "); 
            }
        }
        System.out.println(" (cost " + theTicketToRide + ")"); 
    }



    public static void main( String[] args ) throws FileNotFoundException {
        Main main = new Main(); // Create an instance of the Main class
        main.printDeveloperInfo(); // Call the printDeveloperInfo method

        WeightedGraph<String> graph = new WeightedGraph<>(); // Create a new WeightedGraph object with a String type
        main.loadDataFromFile("cities.txt", graph); // Call the loadDataFromFile method and pass the filename and a new WeightedGraph object as parameters
        Scanner scanner = new Scanner(System.in); // Create a new Scanner object
        main.userMenu(scanner, graph); // Call the userMenu method and pass the scanner and graph objects as parameters
        scanner.close(); // Close the scanner



    }

}