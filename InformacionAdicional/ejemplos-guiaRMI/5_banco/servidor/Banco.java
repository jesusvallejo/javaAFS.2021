
import java.rmi.*;
import java.util.*;

interface Banco extends Remote {
    Cuenta crearCuenta(Titular t) throws RemoteException;
    List<Cuenta> obtenerCuentas() throws RemoteException;
}
