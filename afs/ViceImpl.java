// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceImpl extends UnicastRemoteObject implements Vice {
	private LockManager lockManager;
	private HashMap<String,ArrayList<VenusCB>> CList;

	public ViceImpl() throws RemoteException {
		setLockManager(new LockManager());
		setCoherencia(new HashMap<String, ArrayList<VenusCB>>());

	}
	public synchronized ViceReader download(String fileName,String fileMode,VenusCB callback/* añada los parámetros que requiera */)
			throws RemoteException, FileNotFoundException{
		ReentrantReadWriteLock lock = lockManager.bind(fileName);
		//lock.readLock().lock();
		ViceReader down = new ViceReaderImpl(fileName,fileMode,lock,this);
		this.addClist(fileName, callback);
		return down;
	}
	public ViceWriter upload(String fileName,String fileMode, VenusCB callback/* añada los parámetros que requiera */)
			throws RemoteException , FileNotFoundException, IOException {
		ReentrantReadWriteLock lock = lockManager.bind(fileName);
		//lock.writeLock().lock();
		ViceWriter up = new ViceWriterImpl(fileName, fileMode,lock,callback,this);
		return up;
	}
	public synchronized HashMap<String,ArrayList<VenusCB>> getCoherencia() throws RemoteException{
		return CList;
	}
	public void setCoherencia(HashMap<String,ArrayList<VenusCB>> CList) {
		this.CList = CList;
	}
	public LockManager getLockManager() {
		return lockManager;
	}
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}
	public void invalidate(String fileName, VenusCB callback) throws RemoteException, FileNotFoundException{
		for (HashMap.Entry<String,ArrayList<VenusCB>> entry : CList.entrySet()) {
			if(entry.getKey().equals(fileName)){
				int i=0;
				while(entry.getValue().size()<1){
					if(!entry.getValue().get(i).equals(callback)){
						entry.getValue().get(i).invalidate(fileName);
						entry.getValue().remove(i);
						i--;
					}
				}
			}
		}
	}
	public void addClist(String fileName,VenusCB callback) {
		if(CList.get(fileName)== null) CList.put(fileName, new ArrayList<VenusCB>()); // if not found create a new entry
		CList.get(fileName).add(callback);
	}


}
