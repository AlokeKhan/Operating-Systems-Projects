package src;

public class Consumer extends Thread {
    private final Node src;

    public Consumer(Node src) {
        this.src = src;
    }

    private void logInput(Message message) {
        System.out.println("Consumer at Node " + src.getNodeID() + 
                         " received message: " + message.getMessageValue() + 
                         " from Node " + message.getSrc());
    }

    public void run() {
        while (!src.isDone() || !src.getMessageBuffer().isEmpty()) {
            try {
                Message message = src.getMessageBuffer().pollMessage();
                if (message != null) {
                    src.updateReceivedMessages(message);
                    logInput(message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}