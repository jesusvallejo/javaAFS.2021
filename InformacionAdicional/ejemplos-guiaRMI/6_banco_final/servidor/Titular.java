import java.io.*;

public class Titular implements Serializable {
     private String nombre;
     private String iD;
     Titular(String n, String i) {
         nombre = n;
         iD = i;
     }
     public String obtenerNombre() {
         return nombre;
     }
     public String obtenerID() {
         return iD;
     }
     public String toString() {
         return nombre + " | " + iD;
     }
}
