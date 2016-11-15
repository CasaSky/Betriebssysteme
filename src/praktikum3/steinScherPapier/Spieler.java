package praktikum3.steinScherPapier;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 13.11.16.
 */
public class Spieler extends Thread {
    private TischBuffer<Spieler, Symbol> tisch;
    private Integer wahl;
    private final Symbol[] values = Symbol.values();


    public Spieler(String name, TischBuffer<Spieler, Symbol> tisch) {
        setName(name);
        setDaemon(true);
        this.tisch = tisch;
    }

    public void spielen() {
        int size = Symbol.values().length;
        wahl = ThreadLocalRandom.current().nextInt(size);
        try {
            tisch.enter(this, values[wahl]);
        } catch (InterruptedException e) {
        }
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
