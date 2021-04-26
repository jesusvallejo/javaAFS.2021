
import java.rmi.*;

interface ServicioLog extends Remote {
	void log (String m) throws RemoteException;
}
