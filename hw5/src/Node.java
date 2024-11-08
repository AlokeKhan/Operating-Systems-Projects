package src;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Node extends Thread {
    // Fields
    private volatile boolean done = false; 
    private String nodeId;
    private Random rng;
    private long qtyMessagesToSend;
    private Node[] neighbors;
    private Producer[] producers;
    private Consumer[] consumers;
    public MessageBuffer MessageBuffer;
    private AtomicLong totalMessagesSent = new AtomicLong(0);
    private AtomicLong totalMessagesReceived = new AtomicLong(0);
    private AtomicLong sumOfMessagesSent = new AtomicLong(0);
    private AtomicLong sumOfMessagesReceived = new AtomicLong(0);
    private int producersDone = 0;

    // Constructor
    public Node(String nodeId, long seed, long qtyMessagesToSend, int numNeighbors, int bufferSize) {
        this.nodeId = nodeId;
        this.rng = new Random(seed + nodeId.hashCode());
        this.qtyMessagesToSend = qtyMessagesToSend;
        this.neighbors = new Node[numNeighbors];
        this.producers = new Producer[numNeighbors];
        this.consumers = new Consumer[numNeighbors];
        this.MessageBuffer = new MessageBuffer(bufferSize);

        // Initialize producers and consumers for each neighbor
        for (int i = 0; i < numNeighbors; i++) {
            this.producers[i] = new Producer(this, qtyMessagesToSend / numNeighbors);
            this.consumers[i] = new Consumer(this);
        }
    }
    public MessageBuffer getMessageBuffer() {
        return MessageBuffer;
    }
    
    // Get Node ID
    public String getNodeID() {
        return nodeId;
    }
    public int getNumNeighbors() {
        return neighbors.length;
    }
    // Set neighbors for the node
    public void setNeighbors(Node[] neighbors) {
        this.neighbors = neighbors;
    }
    public Node getNeighbor(int index) {
        return neighbors[index % neighbors.length];
    }

    // Generate a random message
    public long generateMessage() {
        return 1 + (long) (rng.nextDouble() * (1024 - 1));
    }

    // Select a destination node based on an index
    public Node selectDestination(int index) {
        return neighbors[index % neighbors.length];
    }

    public void updateSentMessages(Message message) {
        totalMessagesSent.incrementAndGet();
        sumOfMessagesSent.addAndGet(message.getMessageValue());
    }

    public void updateReceivedMessages(Message message) {
        totalMessagesReceived.incrementAndGet();
        sumOfMessagesReceived.addAndGet(message.getMessageValue());
    }

    public long reportTotalSent() {
        return totalMessagesSent.get();
    }

    public long reportTotalReceived() {
        return totalMessagesReceived.get();
    }

    public long reportSumSent() {
        return sumOfMessagesSent.get();
    }

    public long reportSumReceived() {
        return sumOfMessagesReceived.get();
    }

    public boolean checkDone() {
        return done;
    }

    public synchronized void markDone() {
        producersDone++;
        if (producersDone == producers.length) {
            done = true;
        }
    }
    
    // Method to check if this node is done
    public boolean isDone() {
        return done && getMessageBuffer().isEmpty();
    }

    public void run() {
        for (Producer producer : producers) {
            producer.start();
        }
        for (Consumer consumer : consumers) {
            consumer.start();
        }
        
        try {
            for (Producer producer : producers) {
                producer.join();
            }
            for (Consumer consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}

