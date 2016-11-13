package praktikum3.mensaSimulation;

/**
 * Created by talal on 02.11.16.
 */
public class Student extends Thread {

    private KassenBuffer<Kasse> kassenBuffer;

    public Student(String name, KassenBuffer<Kasse> kassenBuffer) {
        setName(name);
        setDaemon(true);
        this.kassenBuffer = kassenBuffer;
    }

    public void bezahlProzess() throws InterruptedException {

        /** Anstellen **/
        Kasse k = kassenBuffer.anstellen(this);
        /** Bezahlen **/
        Student aktualStudent;
        do {
            aktualStudent = kassenBuffer.freigeben(k);
        }while(this != aktualStudent);
    }

    public void run() {
        while(!isInterrupted()) {
            try {
                bezahlProzess();
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
