import java.rmi.*;
import java.rmi.server.*;

class ServidorBanco  {
    static public void main (String args[]) {
       if (args.length!=1) {
            System.err.println("Uso: ServidorBanco numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            BancoImpl srv = new BancoImpl();
            Naming.rebind("rmi://localhost:" + args[0] + "/Banco", srv);
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorBanco:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
