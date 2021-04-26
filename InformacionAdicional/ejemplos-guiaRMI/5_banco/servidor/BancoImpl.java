import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class BancoImpl extends UnicastRemoteObject implements Banco {
    List<Cuenta> l;
    BancoImpl() throws RemoteException {
        l = new LinkedList<Cuenta>();
    }
    public Cuenta crearCuenta(Titular t) throws RemoteException {
        Cuenta c = new CuentaImpl(t);
        l.add(c);
        return c;
    }
    public List<Cuenta> obtenerCuentas() throws RemoteException {
       return l;
    }
}
