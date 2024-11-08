package src;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBuffer {
    private Message[] internalMessageBuffer;
    private int bufferCapacity;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public MessageBuffer(int bufferCapacity) {
        this.bufferCapacity = bufferCapacity;
        this.internalMessageBuffer = new Message[bufferCapacity];
    }

    public boolean emplaceMessage(Message message) {
        lock.lock();
        try {
            if (count == bufferCapacity) {
                return false; // Buffer is full, message not added
            }
            internalMessageBuffer[tail] = message;
            tail = (tail + 1) % bufferCapacity;
            count++;
            notEmpty.signal();
            return true; // Message successfully added
            
        } finally {
            lock.unlock();
        }
    }
    

    public void put(Message message) throws InterruptedException {
        lock.lock();
        try {
            while (count == bufferCapacity) {
                if (!notFull.await(1, TimeUnit.SECONDS)) {
                    // Timeout reached, handle the situation
                    throw new InterruptedException("Put operation timed out");
                }
            }
            internalMessageBuffer[tail] = message;
            tail = (tail + 1) % bufferCapacity;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Message pollMessage() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                if (!notEmpty.await(1, TimeUnit.SECONDS)) {
                    throw new InterruptedException("Poll operation timed out");
                }
            }
            Message message = internalMessageBuffer[head];
            internalMessageBuffer[head] = null; 
            head = (head + 1) % bufferCapacity;
            count--;
            notFull.signal();
            return message;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return count == 0;
        } finally {
            lock.unlock();
        }
    }
}