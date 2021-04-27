// Interfaz de cliente que define los métodos remotos para gestionar
// callbacks
package afs;

import java.io.FileNotFoundException;
import java.rmi.*;

public interface VenusCB extends Remote {
    public void invalidate(String fileName /* añada los parámetros que requiera */)
        throws RemoteException, FileNotFoundException;
    /* añada los métodos remotos que requiera */
}

