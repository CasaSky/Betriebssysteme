package praktikum3.steinScherPapier;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 13.11.16.
 */
public class Spieler extends Thread {
    private TischBuffer<Spieler, Symbol> tisch;
    private Integer wahl;

    public Spieler(String name, TischBuffer<Spieler, Symbol> tisch) {
        setName(name);
        setDaemon(true);
        this.tisch = tisch;
    }

    public synchronized void spielen() {
        int size = Symbol.values().length;
        wahl = ThreadLocalRandom.current().nextInt(size);
        Symbol[] values = Symbol.values();
        try {
            tisch.enter(this, values[wahl]);
        } catch (InterruptedException e) {

        }
        System.out.println("\n*-->" + getName() + " " +values[wahl] + "<--*");
    }

    public void run() {
        while(!isInterrupted()) {
            spielen();
            //sleep(1);
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
