// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;
import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
    public ViceImpl() throws RemoteException {
    }
    public ViceReader download(String fileName,String fileMode /* añada los parámetros que requiera */)
          throws RemoteException, FileNotFoundException{
        return new ViceReaderImpl(fileName,fileMode);
    }
    public ViceWriter upload(String fileName /* añada los parámetros que requiera */)
          throws RemoteException {
        return null;
    }
}
