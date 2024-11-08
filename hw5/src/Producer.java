package src;

public class Producer extends Thread {
    private Node src;      
    private long messagesToProduce;  

    
    public Producer(Node src, long messagesToProduce) {
        this.src = src;
        this.messagesToProduce = messagesToProduce;
    }

    private Message produceMessage() {
        long messageValue = 1 + (long)(Math.random() * (1024 - 1));
        return new Message(src.getNodeID(), null, messageValue); // Destination to be set in sendMessage
    }

    // Method to send a message to a neighboring node
    private void sendMessage(Node destination, Message message) {
        try {
            message.setDst(destination.getNodeID());
            destination.getMessageBuffer().put(message);
            src.updateSentMessages(message);
            logOutput(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    

    // Method to log the output of the message sent
    private void logOutput(Message message) {
        System.out.println("Producer from Node " + message.getSrc() + " sent message: " +
                   message.getMessageValue() + " to Node " + message.getDst());
    }

    public void run() {
        for (int i = 0; i < messagesToProduce; i++) {
            Message message = produceMessage();
            Node destination = src.getNeighbor((int) (message.getMessageValue() % src.getNumNeighbors()));
            sendMessage(destination, message);
        }
        src.markDone(); // Mark this producer as done
    }
}

