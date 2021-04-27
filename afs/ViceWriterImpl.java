// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
	private RandomAccessFile F;
	private String fileName;
	private String fileMode;
	private String filePath;
	
	ReentrantReadWriteLock lock;
	VenusCB callback;
	ViceImpl vice;

    
	public ViceWriterImpl(String fileName , String fileMode,ReentrantReadWriteLock lock,VenusCB callback,ViceImpl vice/* añada los parámetros que requiera */)
		    throws RemoteException, FileNotFoundException, IOException{
		
    	this.setFileName(fileName);
    	this.setFileMode(fileMode);
    	this.setFilePath(AFSDir+fileName);
    	this.setCallback(callback);
    	this.setLock(lock);
    	this.setVice(vice);
    	this.F = new RandomAccessFile(filePath,fileMode);
    	this.F.seek(0);
    }

	public void write(byte [] b) throws RemoteException , IOException{
		lock.writeLock().lock();
    	this.F.write(b);
        return;
    }
    public void close() throws RemoteException , IOException{
    	vice.invalidate(fileName, callback);					// invalidar
    	lock.writeLock().unlock();
    	vice.getLockManager().unbind(fileName);
    	this.F.close();
        return;
    }
    
    public void setLength(long len) throws RemoteException, IOException{
        this.F.setLength(len);
        return;
    }
	public String getFileMode() {
		return fileMode;
	}
	public void setFileMode(String fileMode) {
		this.fileMode = fileMode;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public ReentrantReadWriteLock getLock() {
		return lock;
	}
	public void setLock(ReentrantReadWriteLock lock) {
		this.lock = lock;
	}
	public VenusCB getCallback() {
		return callback;
	}
	public void setCallback(VenusCB callback) {
		this.callback = callback;
	}
    public ViceImpl getVice() {
		return vice;
	}
	public void setVice(ViceImpl vice) {
		this.vice = vice;
	}
}       

