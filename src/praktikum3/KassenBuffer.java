package praktikum3;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 02.11.16.
 */
public class KassenBuffer<T extends Kasse> extends LinkedList<T> {

    /** Mutex: Locked/Unlocked (Synchronisation des Pufferszugriffs **/
    private ReentrantLock reentrantLock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1);

    public KassenBuffer() {
    }
    public KassenBuffer(LinkedList<T> buffer) {
        super(buffer);
    }

    // Student bezahlt und verlaesst die Kasse
    public void freigeben(T t) {
        reentrantLock.lock(); // Zugriff sperren(P)
        //add(t); da nicht gelöscht ist
        reentrantLock.unlock(); // Zugriff entsperren(V)
        semaphore.release(size()); // Weckt einen wartenden Prozess(V)
    }

    // Student geht zur Kasse
    public Kasse benutzen(T t) throws InterruptedException {
        Kasse kasse = null;
        semaphore.tryAcquire(size()); // Blockieren und warten (Wenn Puffer leer ist) (P)
        reentrantLock.lock();  // Zugriff sperren(P)
        if (t == null) {
            kasse = peek();//poll(); // Hier die Warteschlange von Kassen also nicht löschen (Kein Verbraucher?)
        } else {
            //remove(t);
        }
        reentrantLock.unlock(); // Zugriff entsperren(V)
        return kasse;
    }

    // Sucht nach warteschlangen
    public boolean warteschlangeVorhanden() {
        boolean result = this.stream().anyMatch(k -> !k.getStudents().isEmpty());
        return result;
    }

}
