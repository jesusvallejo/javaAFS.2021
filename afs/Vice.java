// Interfaz de servidor que define los métodos remotos para iniciar
// la carga y descarga de ficheros
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*;

public interface Vice extends Remote {
    public ViceReader download(String fileName,String fileMode, VenusCB callback /* añada los parámetros que requiera */)
          throws RemoteException, FileNotFoundException, IOException;
    public ViceWriter upload(String fileName, String fileMode, VenusCB callback /* añada los parámetros que requiera */)
          throws RemoteException, FileNotFoundException, IOException;

    /* añada los métodos remotos que requiera */
}
       

