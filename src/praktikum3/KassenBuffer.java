package praktikum3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 02.11.16.
 */
public class KassenBuffer<T extends  Thread> {

    private LinkedList<T> buffer = new LinkedList<T>();

    /** Mutex: Locked/Unlocked (Synchronisation des Pufferszugriffs **/
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(0);


    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    // Student geht zur Kasse
    public void enter(T t) {
        reentrantLock.lock(); // Zugriff sperren(P)
        buffer.add(t);
        reentrantLock.unlock(); // Zugriff entsperren(V)
        semaphore.release(); // Weckt einen wartenden Prozess(V)
    }

    // Student bezahlt und verlaesst die Kasse
    public void quit(T t) throws InterruptedException {
        semaphore.acquire(); // Blockieren und warten (Wenn Puffer leer ist) (P)
        reentrantLock.lock();  // Zugriff sperren(P)
        buffer.remove(t);
        reentrantLock.unlock(); // Zugriff entsperren(V)
    }


    public LinkedList<T> getBuffer() {
        return buffer;
    }


}
