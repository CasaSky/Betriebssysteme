package praktikum3.steinScherPapier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 14.11.16.
 */
public class TischBufferConditions extends TischBuffer<Spieler, Symbol>  {

    private Lock lock = new ReentrantLock();
    private Condition notLimit  = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    //final Map<Spieler, Symbol> items = new HashMap<>();

    public void enter(Spieler spieler, Symbol symbol) throws InterruptedException {
        lock.lock();
        try {
            while (size()>2 || containsKey(spieler)) {
                System.out.println("\n*-->" + spieler + " wartet...<--*");
                notLimit.await();
            }
            put(spieler, symbol );

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void purge() throws InterruptedException {
        lock.lock();
        try {
            while (size() == 0) {
                notEmpty.await();
            }
            clear();
            notLimit.signal();
        } finally {
            lock.unlock();
        }
    }

}
