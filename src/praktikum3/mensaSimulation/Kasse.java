package praktikum3.mensaSimulation;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by talal on 02.11.16.
 */
public class Kasse {
    private String name;
    private Queue<Student> students; //Warteschlange von Studenten

    public Kasse(String name) {
        setName(name);
        this.students = new LinkedList<>();
    }

    // Student in der Warteschlange anstellen
    public void add(Student s) {
        this.students.add(s);
    }

    public Student remove()  {
        Student actualStudent = students.remove();
        return actualStudent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kasse)) return false;
        Kasse kasse = (Kasse) o;
        return name.equals(kasse.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

}
