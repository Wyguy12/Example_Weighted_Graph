# Weighted Graph Implementation

This project provides a custom implementation of a weighted graph in Java, using adjacency lists to represent the graph's structure. The graph is used to find the shortest path and minimum spanning tree between cities based on a given list of connections.

## Project Structure

- **`Edge` class**: Represents the connections between cities (edges), including the weight (distance).
- **`IGraph` interface**: Defines the methods for manipulating the graph, such as adding vertices and edges, finding paths, and querying neighbors.
- **`WeightedGraph` class**: Implements the `IGraph` interface. It uses an adjacency list to store edges and provides methods for graph manipulation, shortest path finding (Dijkstra's algorithm), and minimum spanning tree generation (Prim's algorithm).
- **`ProcessCityDataFile` class**: Handles the parsing of city data from a file.
- **`Main` class**: Contains the main entry point, loads data from a file, and interacts with the user to find paths between cities.

## Classes Overview

### 1. `Edge`
The `Edge` class represents a connection between two vertices in the graph. Each edge has:
- **Source vertex (u)**: The starting point of the edge.
- **Destination vertex (v)**: The ending point of the edge.
- **Weight**: The distance between the two vertices.

### 2. `IGraph<V>`
An interface that defines the graph operations, including:
- Adding and removing vertices and edges.
- Checking for the existence of vertices and edges.
- Finding shortest paths and minimum spanning trees.
- Querying neighbors of a vertex and getting edge weights.

### 3. `WeightedGraph<V>`
Implements the `IGraph<V>` interface and provides:
- **Adjacency list representation**: Stores each vertex's list of edges.
- **Map for vertex indexing**: Maps each vertex to an index for quick access.
- **Graph algorithms**:
  - **Dijkstra's Algorithm**: Finds the shortest path between two cities.
  - **Prim's Algorithm**: Finds the minimum spanning tree.

### 4. `ProcessCityDataFile`
Parses the city data from a CSV file. Each line in the file should contain three values:
- **Source city name**
- **Destination city name**
- **Distance between the cities**

### 5. `Main`
- **Main class for running the program**.
- **Loads data from a file** and populates the graph.
- **Displays a user menu** to allow users to select cities and find the shortest path.

## Features

### 1. Graph Manipulation
- Add and remove vertices.
- Add and remove weighted edges.

### 2. Graph Query Methods
- Retrieve the total number of vertices and edges.
- Get a list of all vertices.
- Check if specific vertices or edges exist.

### 3. Graph Algorithms
- **Dijkstra's Algorithm** for finding the shortest path between two cities.
- **Prim's Algorithm** for finding the minimum spanning tree.