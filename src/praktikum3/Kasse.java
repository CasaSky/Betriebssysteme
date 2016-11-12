package praktikum3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by talal on 02.11.16.
 */
public class Kasse implements Comparable<Kasse>{
    private String name;
    private Queue<Student> students; //Warteschlange von Studenten

    public Kasse(String name) {
        setName(name);
        this.students = new LinkedList<>();
    }

    // Student in der Warteschlange anstellen
    public void anstellen(Student s) {
        this.students.add(s);
    }

    public void bezahlen()  {
        students.remove();
    }

    @Override
    public int compareTo(Kasse o) {
        int size1 = students.size();
        int size2 = o.getStudents().size();
        return Integer.compare(size1, size2);
    }

    @Override
    public String toString() {
        return getName()+"("+students.size()+")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<Student> getStudents() {
        return students;
    }

}
