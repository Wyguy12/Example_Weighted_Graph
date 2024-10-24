package com.wyattfredrickson.WeightedGraphExample;


/**
 * Public class Edge that is to define edges as objects and store the edges in a graph
 */
public class Edge {
    int u; // First vertex
    int v; // Second vertex
    double weight; // Weight of the edge

    /**
     * Constructor for Edge class
     * 
     */
    public Edge(int u, int v, double weight) {
        this.u = u; // Set the first vertex
        this.v = v; // Set the second vertex
        this.weight = weight; // Set the weight of the edge
    }

    /**
     * This method returns the first vertex of the edge object
     * @return the first vertex of the edge object
     */
    public int getU() {
        return u; // Return the first vertex
    }

    /**
     * This method returns the second vertex of the edge object
     * @return the second vertex of the edge object
     */
    public int getV() {
        return v; // Return the second vertex
    }
    
    /**
     * This method returns the weight of the edge object
     * @return the weight of the edge object
     */
    public double getWeight() {
        return weight; // Return the weight of the edge
    }
    
    /**
     * Equals method checks if two edges are equal by comparing their vertices
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the object is the same
        if (o == null || getClass() != o.getClass()) return false; // Check if the object is null or the class is not the same
        Edge edge = (Edge) o; // Cast the object to an Edge object
        return u == edge.u && v == edge.v; // Return true if the vertices are the same
    }

    /**
     * To string method returns the string representation of the Edge object
     * 
     */
    @Override
    public String toString() {
        return "Edge{" + "u=" + u + ", v=" + v + ", weight=" + weight +'}';
    }
}