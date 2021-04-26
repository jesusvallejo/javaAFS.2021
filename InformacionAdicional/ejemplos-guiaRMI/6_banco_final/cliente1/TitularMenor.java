import java.io.*;

class TitularMenor extends Titular {
     private String nombreTutor;
     TitularMenor(String n, String i, String t) {
         super(n, i);
         nombreTutor = t;
     }
     public String obtenerTutor() {
         return nombreTutor;
     }
     public String toString() {
         return super.toString() + " | " + nombreTutor;
     }
}
