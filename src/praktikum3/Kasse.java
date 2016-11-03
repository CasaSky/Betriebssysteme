package praktikum3;

import java.util.LinkedList;

/**
 * Created by talal on 02.11.16.
 */
public class Kasse extends Thread implements Comparable<Kasse>{
    private KassenQueue<Kasse> kassenQueue;

    public LinkedList<Student> getStudents() {
        return students;
    }

    private LinkedList<Student> students;

    public Kasse(String name, KassenQueue<Kasse> kassenQueue, LinkedList<Student> students) {
        setName(name);
        this.kassenQueue = kassenQueue;
        this.students = students;
    }

    public void run() {
        while(!isInterrupted()) {
            try {
                if (!kassenQueue.getReentrantLock().isLocked()) {
                    Kasse k = kassenQueue.quitQueue(); // Naechster Student kommt dran um zu bezahlen
                    System.err.println(k.getName() + " verlaesst " + getName());
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public KassenQueue<Kasse> getKassenQueue() {
        return kassenQueue;
    }

    @Override
    public int compareTo(Kasse o) {
        int size1 = students.size();
        int size2 = o.getStudents().size();
        return Integer.compare(size1, size2);
    }
}
