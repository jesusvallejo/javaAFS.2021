import java.rmi.*;
import java.rmi.server.*;

class BancoImpl extends UnicastRemoteObject implements Banco {
    BancoImpl() throws RemoteException {
    }
    public Cuenta crearCuenta(String nombre) throws RemoteException {
        return new CuentaImpl(nombre);
    }
}
