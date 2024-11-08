package src; 

public class Message {
    private String src;        // Source node ID
    private String dst;        // Destination node ID
    private long messageValue; // Value of the message

    public Message(String src, String dst, long messageValue) {
        this.src = src;
        this.dst = dst;
        this.messageValue = messageValue;
    }

    public long getMessageValue() {
        return messageValue;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
