package com.wyattfredrickson.WeightedGraphExample;
import java.util.*;


/**
 * Provides a custom weighted graph implementation 
 * Also a SearchTree class 
 */
public class WeightedGraph<V> implements IGraph<V> {
    // Represents the adjacency list for the graph
    // Each outer list index corresponds to a vertex in the 'vertices' list
    // The inner list at each index contains the edges associated with that vertex
    // Each edge represents a connection from the source vertex (outer index) to the destination vertex
    // Named 'neighbors' because neighbors in a graph are the vertices that are connected to a given vertex
    private List<List<Edge>> neighbors = new ArrayList<>(); // Store the neighbors in a list as a list of edge objects
    private Map<V, Integer> vertexIndexMap = new HashMap<>(); // Store the index of each vertex in a map
    private List<V> vertices = new ArrayList<>(); // Store the vertices in a list 
    
    /**
     * No argument constructor for WeightedGraph
     */
    public WeightedGraph() {
    }
    /**
     * A constructor for WeightedGraph that takes vertices and edges as parameters
     * @param vertices an array of vertices
     * @param edges a 2D array of edges
     */
    public WeightedGraph(V[] vertices, int[][] edges) {
        for (V vertex : vertices) { // For each vertex in the vertices array
            addVertex(vertex); // Add the vertex to the graph
        }
        for (int[] edge : edges) { // For each edge in the edges array
            if (edge.length == 3) { // If the edge has 3 elements
                int u = edge[0]; // Then the first element is the source vertex
                int v = edge[1]; // The second element is the destination vertex
                double weight = edge[2]; // The third element is the weight of the edge
                addEdge(u, v, weight); // Add the edge to the graph
            } else { // Else if the edge does not have 3 elements then print an error message
                throw new IllegalArgumentException("The Edge array must have 3 elements: [source, destination, weighted distance]"); // Throw an IllegalArgumentException
            }
        }
    }





    /**
     * Methods for adding and removing vertices and edges for constructing a graph
     */
    /**
     * A method that adds a vertex to the graph 
     * A vertex is a node in a graph that can be connected to other vertices by edges
     * 
     */
    @Override
    public void addVertex(V vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertices.size()); 
            vertices.add(vertex);
            neighbors.add(new ArrayList<>()); 
        }
    }
    /**
     * A method that adds an edge to the graph
     * A weighted edge is a connection between two vertices in a graph with a weight assigned to it
     */
    @Override
    public void addEdge(int u, int v, double weight) {
        if (u < 0 || u >= vertices.size() || v < 0 || v >= vertices.size()) {
            throw new IllegalArgumentException("Invalid vertex index!");
        }
        neighbors.get(u).add(new Edge(u, v, weight)); // Add the edge to the neighbors list
        neighbors.get(u).add(new Edge(u, v, weight)); 
    }
    /**
     * A method that removes a vertex from the graph
     */
    @Override
    public boolean removeVertex(V vertex) {
        Integer index = vertexIndexMap.get(vertex);
        if (index == null) return false;
        vertices.remove((int) index); 
        neighbors.remove((int) index);
        for (int i = index; i < vertices.size(); i++) {
            vertexIndexMap.put(vertices.get(i), i);
        }
        return true;
    }
    /**
     * A method that removes an edge from the graph
     */
    @Override
    public boolean removeEdge(V source, V destination) {
        Integer u = vertexIndexMap.get(source);
        Integer v = vertexIndexMap.get(destination);
        if (u == null || v == null) return false;
        return neighbors.get(u).removeIf(edge -> edge.v == v) && neighbors.get(v).removeIf(edge -> edge.v == u);
    }





    /**
     * Methods for getting the number of vertices and edges in the graph (Query methods)
     */
    /**
     * Method that gets the vertex count of the graph
     * @return the total number of vertices in the graph
     */
    @Override
    public int getVertexCount() {
        return vertices.size(); 
    }
    /**
     * Method that gets the vertices of the graph 
     * @param vertex the vertex to get the index of
     * @return the result of the vertex index map
     */
    @Override
    public int getVertexIndex(V vertex) {
        Integer index = vertexIndexMap.get(vertex);
        if (index == null) {
            throw new IllegalArgumentException("Vertex not found in the graph!");
        }
        return index; 
    }
    /**
     * Method that gets the edge count of the graph
     * @return the total number of edges in the graph
     */
    @Override
    public int getEdgeCount() {
        int count = 0; // Initialize the count to 0
        for (List<Edge> edgeList : neighbors) { // For each edge list in the neighbors list
            count += edgeList.size(); // Add the size of the edge list to the count variable
        }
        return count / 2; 
    }
    /**
     * Method that gets the vertices of the graph
     * @return a new ArrayList of the vertices in the graph
     */
    @Override
    public List<V> getVertices() {
        return new ArrayList<>(vertices); // Return a new ArrayList of the vertices
    }
    /**
     * Method for grabbing the index of a vertex in the graph
     */
    @Override
    public boolean containsVertex(V vertex) {
        return vertexIndexMap.containsKey(vertex); // Using the containsKey() method to check for the vertex in the vertex index map
    }
    /**
     * Method for checking if an edge exists between two vertices in the graph
     */
    @Override
    public boolean containsEdge(V source, V destination) {
        Integer u = vertexIndexMap.get(source); // Get the index of the source vertex
        Integer v = vertexIndexMap.get(destination); // Get the index of the destination vertex
        if (u == null || v == null) return false; // If the source or destination vertex is not found return false
        return neighbors.get(u).stream().anyMatch(edge -> edge.v == v); // This statement returns true if the edge is found in the neighbors list
        // Using the stream() method to iterate over the neighbors list and the anyMatch() method to check if the edge is found
    }





    /**
     * Methods for finding the shortest path and minimum spanning tree in the graph
     */
    
    /**
     * Method for finding the shortest path using Dijkstra's algorithm
     */
    @Override
    public SearchTree findShortestPath(V source) {
        Integer sourceIndex = vertexIndexMap.get(source);
        if (sourceIndex == null) {
            throw new IllegalArgumentException("Vertex is not found in the graph!");
        }
        double[] distances = new double[vertices.size()]; // Create a cost array to store the cost of each vertex
        Arrays.fill(distances, Double.POSITIVE_INFINITY); // Fill the cost array with positive infinity values
        distances[sourceIndex] = 0; // Set the distance of the source vertex to 0

        int[] parent = new int[vertices.size()]; // Create a parent array to store the parent of each vertex
        Arrays.fill(parent, -1); // Fill the parent array with -1 values

        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight)); // Create a priority queue to store the edges
        queue.add(new Edge(sourceIndex, sourceIndex, 0)); // Add the source vertex to the queue

        boolean[] visited = new boolean[vertices.size()]; // Create a visited array to store the visited vertices
        List<Integer> searchOrder = new ArrayList<>(); // Create a search order list to store the search order

        while (!queue.isEmpty()) {
            Edge edge = queue.poll(); // Remove the edge with the smallest weight from the queue
            int u = edge.v; // Get the destination vertex of the edge

            if (visited[u]) continue; // If the vertex is already visited then continue
            visited[u] = true; // Mark the vertex as visited
            searchOrder.add(u); // Add the vertex to the search order list

            for (Edge neighbor : neighbors.get(u)) { // For each neighbor of the vertex in the neighbors list
                int v = neighbor.v; // Get the destination vertex of the neighbor edge
                double weight = neighbor.weight; // Get the weight of the neighbor edge

                if (!visited[v] && distances[u] + weight < distances[v]) { // If the vertex is not visited and the distance is less than the current distance
                    distances[v] = distances[u] + weight; // Update the distance of the destination vertex
                    parent[v] = u; // Set the parent of the destination vertex to the source vertex
                    queue.add(new Edge(u, v, distances[v])); // This statement adds the edge to the queue if the vertex is not visited and the distance is less than the current distance
                }
            }
        }
        return new SearchTree(sourceIndex, parent, searchOrder); // Return the search tree with the source vertex, parent array, and search order
    }

    /**
     * Method for obtaining a minimum spanning tree using Prim's algorithm
     */
    @Override
    public SearchTree obtainMinSpanSearchTree(V startingVertex) {
        Integer startingIndex = vertexIndexMap.get(startingVertex); 
        if (startingIndex == null) {
            throw new IllegalArgumentException("Vertex cannot be found in the graph!");
        }
        boolean[] insideMST = new boolean[vertices.size()]; // Create an inside MST array to store the vertices inside the minimum spanning tree
        int[] parent = new int[vertices.size()]; // Create a parent array to store the parent of each vertex
        Arrays.fill(parent, -1); // Fill the parent array with -1 values

        double[] key = new double[vertices.size()]; // Create a key array to store the key of each vertex
        Arrays.fill(key, Double.POSITIVE_INFINITY); // Fill the key array with positive infinity values
        key[startingIndex] = 0; // Set the key of the starting vertex to 0

        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight)); // Create a priority queue to store the edges
        queue.add(new Edge(startingIndex, startingIndex, 0)); // Add the starting vertex to the queue

        List<Integer> searchOrder = new ArrayList<>(); // Create a search order list to store the search order

        while (!queue.isEmpty()) { // While the queue is not empty
            Edge edge = queue.poll(); // Remove the edge with the smallest weight from the queue
            int u = edge.v; // Get the destination vertex of the edge

            if (insideMST[u]) continue; // If the vertex is already inside the MST then continue
            insideMST[u] = true; // Mark the vertex as inside the MST
            searchOrder.add(u); // Add the vertex to the search order list

            for (Edge neighbor : neighbors.get(u)) { // For each neighbor of the vertex in the neighbors list
                int v = neighbor.v; // assign the destination vertex of the neighbor edge to v
                double weight = neighbor.weight; // assign the weight of the neighbor edge to weight

                if (!insideMST[v] && weight < key[v]) { // If the vertex is not inside the MST and the weight is less than the key
                    key[v] = weight; // Then update the key of the vertex to the weight
                    parent[v] = u; // Set the parent of the vertex to the source vertex
                    queue.add(new Edge(u, v, key[v])); // Add the edge to the queue 
                }
            }
        }
        return new SearchTree(startingIndex, parent, searchOrder); // Return the search tree with the starting vertex, parent array, and search order
    }





    /**
     * Methods for returning the neighbors of a vertex, the weight of an edge
     */
    /**
     * A method that returns the index of a vertex in the graph 
     * @param vertex the vertex to get the index of
     * @return the result of the vertex index map
     */
    @Override
    public List<V> getNeighbors(V vertex) {
        Integer index = vertexIndexMap.get(vertex); // Get the index of the vertex
        if (index == null) return Collections.emptyList(); // If the index is not found return an empty list using the Collections.emptyList() method
        List<V> result = new ArrayList<>(); // Create a new ArrayList to store the neighbors of the vertex
        for (Edge edge : neighbors.get(index)) { // For each edge in the neighbors list
            result.add(vertices.get(edge.v)); // Get the destination vertex of the edge and add it to the result list
        }
        return result; // Return the result list
    }
    @Override
    public double getEdgeWeight(V source, V destination) {
        Integer u = vertexIndexMap.get(source); // Get the index of the source vertex
        Integer v = vertexIndexMap.get(destination); // Get the index of the destination vertex
        if (u == null || v == null) return Double.POSITIVE_INFINITY; // If the source or destination vertex is not found return positive infinity value
        return neighbors.get(u).stream().filter(edge -> edge.v == v).findFirst().map(edge -> edge.weight).orElse(Double.POSITIVE_INFINITY); // This statement returns the weight of the edge if it is found in the neighbors list
        // Using the stream() method to iterate over the neighbors list, the filter() method to filter the edge, the findFirst() method to find the first edge, the map() method to get the weight of the edge, and the orElse() method to return positive infinity if the edge is not found
    } 







    /**
     * SearchTree is a specialized class designed for describing the parent–child relationship of the nodes..
     */
    public class SearchTree {
        private int root; // The root of the tree
        private int[] parent; // Store the parent of each vertex 
        private List<Integer> searchOrder; // Store the search order in a list
        /**
         * Constructor for SearchTree
         * @param root the root of the tree
         * @param parent the parent of each vertex
         * @param searchOrder the search order in a list 
         */
        public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }  
        /**
         * Get the root of the tree
         * @return the root of the tree
         */
        public int getRoot() {
            return root;
        }
        /**
         * Get the search order in a list
         * @return the search order in a list
         */
        public List<Integer> getSearchOrder() {
            return searchOrder;
        }
        /**
         * Get the parent of each vertex stored in an array
         * @return the parent of each vertex
         */
        public int[] getParent() {
            return parent;
        }
        /**
         * Get the number of vertices found
         * @param numberOfVerticesFound the number of vertices found
         * @return the number of vertices found
         */
        public int getNumberOfVerticesFound(int numberOfVerticesFound) {
            return numberOfVerticesFound;
        }
        /**
         * Method that gets the path of the search tree from the root to the specified index 
         * And stores the path in an ArrayList, then returns the ArrayList with the specified index
         * @param index the index of the path
         * @return the path in an ArrayList
         */
        public List<Integer> getPath(int index) {
            List<Integer> rootPath = new ArrayList<>(); // Create a new ArrayList to store the path of the search tree 
            while (index != -1) { // While the index is not -1 
                rootPath.add(index); // Add the index to the path ArrayList
                index = parent[index]; // Assign the parent of the current index to the index
            }
            Collections.reverse(rootPath); // Reverse the list to show the path from the root to the specified index
            return rootPath; // Return the path ArrayList 
        }
        /**
         * Method that prints the path of the search tree from the root to the specified index
         * @param index the index of the path 
         */
        public void printPath(int index) {
            List<Integer> rootPath = getPath(index); // Get the path of the search tree from the root to the specified index

            for (int i = 0; i < rootPath.size(); i++) { 
                if (i == rootPath.size() - 1) {
                    System.out.print(rootPath.get(i)); // Print the path of the search tree from the root to the specified index
                } else { 
                    System.out.print(rootPath.get(i) + " -> "); // Print the path of the search tree from the root to the specified index 
                }
            }
        }
        /**
         * Method that prints the tree structure of the search tree
         */
        public void printTree() {
            System.out.println("Tree Structure: ");
            for (int i = 0; i < parent.length; i++) {
                if (i == root) {
                    System.out.println("Root: " + i + " (no parent)"); // Print the root of the tree
                } else {
                    System.out.println("Vertex: " + i + " -> Parent: " + parent[i]); // Print the parent of each vertex
                }
            }
        }
    }


















































































































































}