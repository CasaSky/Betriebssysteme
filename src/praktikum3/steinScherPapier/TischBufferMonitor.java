package praktikum3.steinScherPapier;

/**
 * Created by talal on 13.11.16.
 */
public class TischBufferMonitor extends TischBuffer<Spieler, Symbol>{

    public synchronized void enter(Spieler spieler, Symbol symbol) throws InterruptedException {
        // Pro Runde darf jeder Spieler nur einmal spielen und Pro Runde sind maximal zwei Zuege erlaubt
        while (size()>2 || containsKey(spieler)) {
            System.out.println("\n*-->" + spieler + " wartet...<--*");
            wait();
        }

        put(spieler, symbol);
        this.notifyAll();
    }

    public synchronized void purge() throws InterruptedException {
        while (size()==0) { // optional, da purge nie aufgerufen wird, wenn size == 0
            wait();
        }
        clear();
        this.notifyAll();
    }
}
