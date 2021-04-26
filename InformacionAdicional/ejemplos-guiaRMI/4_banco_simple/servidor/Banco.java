
import java.rmi.*;

interface Banco extends Remote {
    Cuenta crearCuenta(String nombre) throws RemoteException;
}
