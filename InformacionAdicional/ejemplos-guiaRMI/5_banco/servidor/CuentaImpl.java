import java.rmi.*;
import java.rmi.server.*;

class CuentaImpl extends UnicastRemoteObject implements Cuenta {
    private Titular tit;
    private float saldo = 0;
    CuentaImpl(Titular t) throws RemoteException {
        tit = t;
    }
    public Titular obtenerTitular() throws RemoteException {
        return tit;
    }
    public float obtenerSaldo() throws RemoteException {
        return saldo;
    }
    public float operacion(float valor) throws RemoteException {
        saldo += valor;
        return saldo;
    }
}
