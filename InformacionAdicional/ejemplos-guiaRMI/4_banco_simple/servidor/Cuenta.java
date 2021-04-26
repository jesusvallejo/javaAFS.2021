
import java.rmi.*;

interface Cuenta extends Remote {
    String obtenerNombre() throws RemoteException;
    float obtenerSaldo() throws RemoteException;
    float operacion(float valor) throws RemoteException;
}
