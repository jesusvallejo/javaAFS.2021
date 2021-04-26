import java.rmi.*;
import java.rmi.server.*;

class ServidorChat  {
    static public void main (String args[]) {
       if (args.length!=1) {
            System.err.println("Uso: ServidorChat numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            ServicioChatImpl srv = new ServicioChatImpl();
            Naming.rebind("rmi://localhost:" + args[0] + "/Chat", srv);
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorChat:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
