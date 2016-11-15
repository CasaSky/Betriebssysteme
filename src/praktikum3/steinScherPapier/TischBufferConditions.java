package praktikum3.steinScherPapier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 14.11.16.
 */
public class TischBufferConditions extends TischBuffer<Spieler, Symbol> {

    private Lock lock = new ReentrantLock();
    private Condition notLimit = lock.newCondition();
    private Condition limit = lock.newCondition();

    //final Map<Spieler, Symbol> items = new HashMap<>();

    public void enter(Spieler spieler, Symbol symbol) throws InterruptedException {
        lock.lock();
        while (size() >= 2 || containsKey(spieler)) {
            //System.out.println("\n*-->" + spieler + " wartet...<--*");
            notLimit.await();
        }
        put(spieler, symbol);


        if (size() == 2) {
            limit.signalAll();
        }
        lock.unlock();

    }

    public void purge() throws InterruptedException {
        lock.lock();

        while (size() < 2) {
            limit.await();
        }
        clear();

        notLimit.signalAll();
        lock.unlock();

    }

}
