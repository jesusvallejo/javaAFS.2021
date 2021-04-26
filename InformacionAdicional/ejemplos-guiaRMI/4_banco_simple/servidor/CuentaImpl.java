import java.rmi.*;
import java.rmi.server.*;

class CuentaImpl extends UnicastRemoteObject implements Cuenta {
    private String nombre;
    private float saldo = 0;
    CuentaImpl(String n) throws RemoteException {
        nombre = n;
    }
    public String obtenerNombre() throws RemoteException {
        return nombre;
    }
    public float obtenerSaldo() throws RemoteException {
        return saldo;
    }
    public float operacion(float valor) throws RemoteException {
        saldo += valor;
        return saldo;
    }
}
