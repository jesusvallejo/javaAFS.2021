import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class ClienteBanco {
    static public void main (String args[]) {
        if (args.length!=3) {
            System.err.println("Uso: ClienteBanco hostregistro numPuertoRegistro nombreTitular");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Banco srv = (Banco) Naming.lookup("//" + args[0] + ":" + args[1] + "/Banco");
            Cuenta c = srv.crearCuenta(args[2]);
            c.operacion(30);
            System.out.println(c.obtenerNombre() + ": " + c.obtenerSaldo());
            c.operacion(-5);
            System.out.println(c.obtenerNombre() + ": " + c.obtenerSaldo());
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteBanco:");
            e.printStackTrace();
        }
    }
}
