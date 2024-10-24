package com.wyattfredrickson.WeightedGraphExample;
import java.util.Scanner;
import java.io.*;
/**
 * This class processes the city data file cities.txt
 */
public class ProcessCityDataFile {
    String firstChoice; // the first city in the vertex picked
    String secondChoice; // the second city in the vertex picked
    int value; // the weight of the edge between the two cities

    /**
     * Constructor for the ProcessCityDataFile class
     * @param firstChoice the first city in the vertex picked
     * @param secondChoice the second city in the vertex picked
     * @param value the weight of the edge between the two cities
     */
    public ProcessCityDataFile(String firstChoice, String secondChoice, int value) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.value = value; 
    }
    /**
     * Default constructor for the ProcessCityDataFile class
     */
    public ProcessCityDataFile() {

    }
    /**
     * This method returns the first choice of the ProcessCityDataFile object
     * @return the first choice of the ProcessCityDataFile object
     */
    public String getFirstChoice() {
        return firstChoice;
    }
    /**
     * This method returns the second choice of the ProcessCityDataFile object
     * @return the second choice of the ProcessCityDataFile object
     */
    public String getSecondChoice() {
        return secondChoice;
    }
    /**
     * This method returns the value of the ProcessCityDataFile object
     * @return the value of the ProcessCityDataFile object
     */
    public int getValue() {
        return value;
    }
    /**
     * This method loads data from a file and populates the graph and cityToIndexMap objects 
     * @param filename the name of the file to load data from
     * @param graph the graph object to populate using a WeightedGraph data structure
     * @throws FileNotFoundException if the file is not found 
     */
    public void loadDataFromFile(String filename, WeightedGraph<String> graph) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) { // Create a new Scanner object and pass the filename as a parameter
            while(scanner.hasNext()) {
                String line = scanner.nextLine().trim(); // Read the next line from the file and remove any leading or trailing whitespace
                if (line.isEmpty()) continue; // Skip to the next iteration of the loop if the line is empty
                
                String[] tokens = line.split(" - "); // Split the line into an array of tokens using a comma as the delimiter
                if (tokens.length != 3) { // Check if the line contains 3 tokens if not print an error message and skip to the next iteration of the loop
                    System.out.println("Invalid line: " + line); 
                    continue; 
                }
                
                try { 
                    int distance = Integer.parseInt(tokens[2].replace(",", "").replace(" miles","").trim()); // Parse the distance from the tokens array
                    ProcessCityDataFile data = new ProcessCityDataFile(tokens[0].trim(), tokens[1].trim(), distance); // Create a new ProcessCityDataFile object and pass the tokens as parameters and distance as the weight

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
     * This method returns the string representation of the ProcessCityDataFile object
     * @return the string representation of the ProcessCityDataFile object
     * Example: "Minneapolis - St. Paul - 10"
     */
    @Override
    public String toString() {
        return firstChoice + " - " + secondChoice + " - " + value; 
    }
}