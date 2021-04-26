import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

class ServicioLogImpl extends UnicastRemoteObject implements ServicioLog {
    PrintWriter fd;
    ServicioLogImpl(String f) throws RemoteException {
        try {
             fd = new PrintWriter(f);
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
    //public synchronized void log(String m) throws RemoteException {
    public void log(String m) throws RemoteException {
        System.out.println(m);
        fd.println(m);
        fd.flush();
    }
}
