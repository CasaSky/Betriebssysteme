package praktikum3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 02.11.16.
 */
public class Kasse extends Thread implements Comparable<Kasse>{
    private KassenQueue<Student> kassenQueue;

    public Kasse(String name, KassenQueue<Student> kassenQueue) {
        setName(name);
        this.kassenQueue = kassenQueue;
    }

    public void run() {
        while(!isInterrupted()) {
            try {
                Student s = kassenQueue.quitQueue(); // Naechster Student kommt dran um zu bezahlen
                System.err.println(s.getName()+" verlaesst "+getName());
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public KassenQueue<Student> getKassenQueue() {
        return kassenQueue;
    }

    @Override
    public int compareTo(Kasse o) {
        return Integer.compare(kassenQueue.getQueue().size(), o.getKassenQueue().getQueue().size());
    }
}
