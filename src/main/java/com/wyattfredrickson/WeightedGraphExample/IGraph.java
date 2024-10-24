package com.wyattfredrickson.WeightedGraphExample;
import java.util.List;


/**
 * Provides an interface for a custom weighted graph implementation 
 * 
 */
public interface IGraph<V> {
    /**
     * Methods for adding and removing vertices and edges for constructing a graph
     */
    public void addVertex(V vertex); // Adds a new vertex to the graph taking the vertex object as a parameter
    public void addEdge( int u, int v, double weight); // Adds a new edge to the graph taking the source and destination vertices and the weight of the edge as parameters
    public boolean removeVertex(V vertex); // Removes a vertex from the graph taking the vertex object as a parameter
    public boolean removeEdge(V source, V destination); // Removes an edge from the graph taking the source and destination vertices as parameters


    /**
     * Methods for getting the number of vertices and edges in the graph (Query methods)
     */
    public int getVertexCount(); // Return the number of vertices in the graph
    public int getVertexIndex(V vertex); // Return the index of the specified vertex taking the vertex object as a parameter
    public int getEdgeCount(); // Return the number of edges in the graph
    public List<V> getVertices(); // Return a list of ALL! vertices in the graph 
    public boolean containsVertex(V vertex); // Return true if the graph contains the specified vertex taking the vertex object as a parameter
    public boolean containsEdge(V source, V destination); // Return true if the graph contains the specified edge taking the source and target vertices as parameters


    /**
     * Methods for finding the shortest path and minimum spanning tree in the graph
     */
    public WeightedGraph<V>.SearchTree findShortestPath(V source); // Find single source shortest path using Dijkstra's algorithm
    public WeightedGraph<V>.SearchTree obtainMinSpanSearchTree(V startingVertex); // Obtain a minimum spanning tree using Prim's algorithm 


    /**
     * Methods for returning the neighbors of a vertex, the weight of an edge
     */
    List<V> getNeighbors(V vertex); // Return the neighbors of the specified vertex taking the vertex object as a parameter
    double getEdgeWeight(V source, V destination); // Return the edge weight between two vertices taking the source and destination vertices as parameters
}