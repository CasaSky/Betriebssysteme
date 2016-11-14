package praktikum3.steinScherPapier;

import java.util.HashMap;

/**
 * Created by talal on 14.11.16.
 */
public abstract class TischBuffer<Spieler, Symbol> extends HashMap<Spieler, Symbol> {
    public abstract void enter(Spieler spieler, Symbol symbol) throws InterruptedException;
    public abstract void purge() throws InterruptedException;
}
