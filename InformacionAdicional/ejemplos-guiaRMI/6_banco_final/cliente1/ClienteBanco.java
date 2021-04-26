import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class ClienteBanco {
    static public void main (String args[]) {
        if (args.length!=5) {
            System.err.println("Uso: ClienteBanco hostregistro numPuertoRegistro nombreTitular IDTitular nombreTutor");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Banco srv = (Banco) Naming.lookup("//" + args[0] + ":" + args[1] + "/Banco");
            Titular tit = new TitularMenor(args[2], args[3], args[4]);
            Cuenta c = srv.crearCuenta(tit);
            c.operacion(30);

            List <Cuenta> l;
            l = srv.obtenerCuentas();
            for (Cuenta i: l) {
                Titular t = i.obtenerTitular();
                System.out.println(t + ": " +i.obtenerSaldo());
            }

            c.operacion(-5);

            l = srv.obtenerCuentas();
            for (Cuenta i: l)
                System.out.println(i.obtenerTitular() + ": " +i.obtenerSaldo());
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
