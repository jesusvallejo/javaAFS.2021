
import java.rmi.*;

interface Cuenta extends Remote {
    Titular obtenerTitular() throws RemoteException;
    float obtenerSaldo() throws RemoteException;
    float operacion(float valor) throws RemoteException;
}
