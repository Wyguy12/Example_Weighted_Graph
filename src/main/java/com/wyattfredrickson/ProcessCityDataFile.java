package com.wyattfredrickson;

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
     * This method returns the string representation of the ProcessCityDataFile object
     * Example: Hollywood,San Francisco,1
     */
    @Override
    public String toString() {
        return firstChoice + ", " + secondChoice + ", " + value; 
    }
}