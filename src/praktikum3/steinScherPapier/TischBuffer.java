package praktikum3.steinScherPapier;

import java.util.HashMap;

/**
 * Created by talal on 13.11.16.
 */
public class TischBuffer<Spieler, Symbol> extends HashMap<Spieler, Symbol> {

    public synchronized void enter(Spieler name, Symbol symbol) throws InterruptedException {
        // Pro Runde darf jeder Spieler nur einmal spielen und Pro Runde sind nur zwei Zuege erlaubt
        while (size()>2 || containsKey(name)) {
            System.out.println("\n*-->" + name + " wartet...<--*");
            wait();
        }

        this.put(name, symbol);
        this.notifyAll();
    }

    public synchronized void purge() throws InterruptedException {
        while (size()==0) {
            this.wait();
        }
        this.clear();
        this.notifyAll();
    }
}
