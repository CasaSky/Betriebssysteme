package praktikum3.steinScherPapier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by talal on 13.11.16.
 */
public class Schieri extends Thread{
    private TischBuffer<Spieler, Symbol> tisch;
    private int maxRunden;
    private Spieler spieler1;
    private Spieler spieler2;
    private int runden;
    private int unentschiede;
    private Map<Spieler, Integer> gewinne = new HashMap<>();
    private final Integer[][] spielregeln = {{0, 1, 2}, {2, 0, 1}, {1, 2, 0}};

    public Schieri(TischBuffer<Spieler, Symbol> tisch, int maxRunden, Spieler spieler1, Spieler spieler2) {
        this.tisch = tisch;
        this.maxRunden = maxRunden;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }

    private synchronized void spielauswerten() throws InterruptedException {
        if (tisch.size() == 2 && tisch.containsKey(spieler1) && tisch.containsKey(spieler2)) {
            Integer s1 = tisch.get(spieler1).index();
            Integer s2 = tisch.get(spieler2).index();
            int ergebniss = spielregeln[s2][s1];
            System.out.println("\n**Schieri bewertet Runde " + runden+"...**");

            switch (ergebniss) {
                case 1: // bei 1 Spieler1 gewinnt.
                    Integer value1 = gewinne.get(spieler1);
                    System.out.println("\n**Runde " + runden + " geht an " + spieler1.getName() + "!**");
                    gewinne.put(spieler1, value1 == null ? 1 : Integer.sum(value1, 1));
                    break;

                case 2: // bei 2 Spieler 2 gewinnt.
                    Integer value2 = gewinne.get(spieler2);
                    System.out.println("\n**Runde " + runden + " geht an " + spieler2.getName() + "!**");
                    gewinne.put(spieler2, value2 == null ? 1 : Integer.sum(value2, 1));
                    break;

                default: // bei 0 ist ein unentschieden.
                    System.out.println("\n**Runde "+runden+" war unentschieden!**");
                    unentschiede++;
                    break;
            }
            tisch.purge(); // tisch leeren.
            runden++;
        }
        notifyAll();
    }
    public void run() {
        spieler1.start();
        spieler2.start();
        while (runden<maxRunden) {
            try {
                spielauswerten();
                //Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        spieler1.interrupt();
        spieler2.interrupt();
        System.out.println("\n=*******=\nAnzahl Runden " + runden + "\nAnzahl Unterschiede " + unentschiede + "\nAnzahl Gewinne " + gewinne + "\n=*******=");
    }

    public static void main(String[] args) throws InterruptedException {
        TischBuffer<Spieler, Symbol> tisch = new TischBuffer<>();
        Spieler spieler1 = new Spieler("Talal", tisch);
        Spieler spieler2 = new Spieler("Cookie", tisch);
        Schieri cherie = new Schieri(tisch, 10, spieler1, spieler2);
        cherie.start();
        cherie.join();
        System.out.println("\nSpiel ist beendet.");
    }
}
