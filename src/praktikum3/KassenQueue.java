package praktikum3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 02.11.16.
 */
public class KassenQueue<T extends  Thread> {

    private Queue<T> queue = new LinkedList<T>();

    /** Mutex: Locked/Unlocked (Synchronisation des Pufferszugriffs **/
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(0);

    // Student geht zur Kasse
    public void enterToQueue(T t) {
        reentrantLock.lock(); // Zugriff sperren(P)
        queue.add(t);
        reentrantLock.unlock(); // Zugriff entsperren(V)
        semaphore.release(); // Weckt einen wartenden Prozess(V)
    }

    // Student bezahlt und verlaesst die Kasse
    public T quitQueue() throws InterruptedException {
        semaphore.acquire(); // Blockieren und warten (Wenn Puffer leer ist) (P)
        reentrantLock.lock();  // Zugriff sperren(P)
        T t = queue.remove();
        reentrantLock.unlock(); // Zugriff entsperren(V)
        t.join(2); // essen
        return t;
    }


    public Queue<T> getQueue() {
        return queue;
    }


}
