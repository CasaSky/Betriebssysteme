package praktikum3.mensaSimulation;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by talal on 02.11.16.
 */
public class KassenBuffer<T extends Kasse> extends LinkedList<T> {

    public LinkedList<T> buffer;

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    /** Mutex: Locked/Unlocked (Synchronisation des Pufferszugriffs **/
    private ReentrantLock reentrantLock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1);

    public KassenBuffer() {
    }
    public KassenBuffer(LinkedList<T> buffer) {
        super(buffer);
    }

    public Kasse anstellen(Student s) {

        Kasse k;
        /****************************** Anstellen **************************************/
        reentrantLock.lock(); // Zugriff sperren(P)
        // Sortieren
        if (warteschlangeVorhanden()) {
            // Student bekommt die Information, welche Kasse mit der kleinsten Warteschlange..
            //printer("Warteschlange "+this);
            sort((o1, o2) -> Integer.compare(o1.getStudents().size(), o2.getStudents().size()));
            k = getFirst();
            //printer("Warteschlange "+this);
            k.add(s);
            printer(s.getName() + " benutzt die " + k.getName() + "...");
        }

        // keine Sortierung
        else {
            // Student besitzt die Kasse um dort zu bezahlen.
            //printer("Keine Warteschlange "+this);
            k = getFirst();
            k.add(s);
            printer(s.getName() + " benutzt die " + k.getName() + "...");
        }
        reentrantLock.unlock(); // Zugriff entsperren(V)
        semaphore.release(size()); // Weckt einen wartenden Prozess(V)
        return k;
    }

    public Student freigeben(Kasse k) throws InterruptedException {
        semaphore.acquire(k.getStudents().size()); // Blockieren und warten (Wenn Puffer leer ist) (P)
        reentrantLock.lock();  // Zugriff sperren(P)
        Optional<Kasse> value = ((Optional<Kasse>) this.stream().filter(e -> e.equals(k)).findFirst());
        Kasse actualKasse = value.get();

        Student s = null;
        if (!actualKasse.isEmpty()) {
            s = actualKasse.remove();
            printer(s.getName()+" bezahlt an der "+k.getName());
        }
        this.remove(actualKasse);
        this.add((T) actualKasse);
        reentrantLock.unlock(); // Zugriff entsperren(V)
        return s;
    }

    // Sucht nach warteschlangen
    public boolean warteschlangeVorhanden() {
        boolean result = this.stream().anyMatch(k -> !k.getStudents().isEmpty());
        return result;
    }

    public void printer(String message) {
        System.out.println(message+"\n");
    }
}
