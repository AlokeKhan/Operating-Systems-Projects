package src;

import java.util.Random;

public class VirtualNetworkSimulation {

    private Node[] threadPoolOfNodes;  // Array of Nodes representing the network
    private Random rng;                // Random object for generating random values

    // Constructor to initialize the simulation
    public VirtualNetworkSimulation(long seed, int N, int K, int B, long M) {
        rng = new Random(seed);
        threadPoolOfNodes = new Node[N];
    
        // Initialize nodes
        for (int i = 0; i < N; i++) {
            threadPoolOfNodes[i] = new Node("Node-" + i, seed, M / N, K, B);
        }
    
        // make  overlay
        int[][] overlay = generateOverlay(N, K);
    
        // Assign neighbors for each node
        for (int i = 0; i < N; i++) {
            Node[] neighbors = new Node[K];
            for (int j = 0; j < K; j++) {
                neighbors[j] = threadPoolOfNodes[overlay[i][j]];
            }
            threadPoolOfNodes[i].setNeighbors(neighbors);
        }
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java src.VirtualNetworkSimulation <seed> <N> <K> <B> <M>");
            return;
        }

        long seed = Long.parseLong(args[0]);
        int N = Integer.parseInt(args[1]);
        int K = Integer.parseInt(args[2]);
        int B = Integer.parseInt(args[3]);
        long M = Long.parseLong(args[4]);

        try {
            VirtualNetworkSimulation vns = new VirtualNetworkSimulation(seed, N, K, B, M);
            vns.startSimulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private int[][] generateOverlay(int N, int K) {
        int[][] overlay = new int[N][K];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                overlay[i][j] = (i + j + 1) % N;  // Circular pattern
            }
        }
        return overlay;
    }

    // Method to print a report after the simulation
    private void report(Node node) {
        System.out.println("Node " + node.getNodeID() + " report:");
        System.out.println("  Total Messages Sent: " + node.reportTotalSent());
        System.out.println("  Total Messages Received: " + node.reportTotalReceived());
        System.out.println("  Sum of Messages Sent: " + node.reportSumSent());
        System.out.println("  Sum of Messages Received: " + node.reportSumReceived());
        System.out.println();
    }

    private void finalReport(Node[] threadpool){
        long finalsum1 = 0;
        long finalsum2 = 0;
        long finalsent = 0;
        long finalreceived = 0;
        for (Node node : threadpool){
            finalsum1 += node.reportSumReceived();
            finalsum2 += node.reportSumSent();
            finalsent += node.reportTotalSent();
            finalreceived += node.reportTotalReceived();
        }
        System.out.println("Final report:");
        System.out.println("  Total Messages Sent: " + finalsent);
        System.out.println("  Total Messages Received: " + finalreceived);
        System.out.println("  Global Messages Sent: " + finalsum1);
        System.out.println("  Global Messages Received: " + finalsum2);
    }

    public void startSimulation() {
        for (Node node : threadPoolOfNodes) {
            node.start();
        }
    
        for (Node node : threadPoolOfNodes) {
            try {
                node.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        //print output
        for (Node node : threadPoolOfNodes) {
            report(node);
        }
        finalReport(threadPoolOfNodes);
    
        System.out.println("Simulation completed.");
    }
}
