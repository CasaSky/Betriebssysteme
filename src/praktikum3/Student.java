package praktikum3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by talal on 02.11.16.
 */
public class Student extends Thread {

    private LinkedList<Kasse> kassen = new LinkedList<>();
    private KassenQueue<Kasse> kassenQueue;

    public Student(String name, LinkedList<Kasse> kassen, KassenQueue<Kasse> kassenQueue) {
        setName(name);
           if (kassen == null) {
               throw new IllegalArgumentException();
           }
           this.kassen = kassen;
        this.kassenQueue = kassenQueue;
    }
    //
    public void run() {

        //Zur Kasse gehen
        while(!isInterrupted()) {
            if (!kassenQueue.getQueue().contains(this) && !kassenQueue.getReentrantLock().isLocked()) {
                Collections.sort(kassen);
                System.err.println(kassen.toString());
                kassenQueue.enterToQueue(kassen.getFirst());
                System.err.println(getName()+" stellt sich in Kasse "+kassen.get(0).getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
