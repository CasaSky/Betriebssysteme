package praktikum3;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by talal on 02.11.16.
 */
public class Student extends Thread {

    private KassenBuffer<Kasse> kassenBuffer;
    private LinkedList<Kasse> kassen;
    private boolean stehtAn;

    public boolean isStehtAn() {
        return stehtAn;
    }

    public void setStehtAn(boolean stehtAn) {
        this.stehtAn = stehtAn;
    }

    public Student(String name, LinkedList<Kasse> kassen, KassenBuffer<Kasse> kassenBuffer) {
        setName(name);
        this.kassen = kassen;
        this.kassenBuffer = kassenBuffer;
    }
    //
    public void run() {

        //Zur Kasse gehen
        while(!isInterrupted()) {
            if (!kassenBuffer.getReentrantLock().isLocked()) {
                stehtAn = true;
                Collections.sort(kassen);
                System.err.println(kassen.toString());
                Kasse k = kassen.getFirst();
                k.addStudent(this);
                kassenBuffer.enter(k);
                System.err.println(getName()+" stellt sich in Kasse "+k.getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
