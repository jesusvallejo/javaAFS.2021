// Interfaz de servidor que define los métodos remotos para iniciar
// la carga y descarga de ficheros
package afs;
import java.io.FileNotFoundException;
import java.rmi.*;

public interface Vice extends Remote {
    public ViceReader download(String fileName,String fileMode /* añada los parámetros que requiera */)
          throws RemoteException, FileNotFoundException;
    public ViceWriter upload(String fileName /* añada los parámetros que requiera */)
          throws RemoteException;

    /* añada los métodos remotos que requiera */
}
       

