// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
	private static final String AFSDir = "AFSDir/";
	private RandomAccessFile F;
	private String fileName;
	private String fileMode;
	private String filePath;
	
	
	private ReentrantReadWriteLock lock;
	private ViceImpl vice;
	
	public ViceReaderImpl(String fileName, String fileMode, ReentrantReadWriteLock lock, ViceImpl vice/* añada los parámetros que requiera */)
			throws RemoteException , FileNotFoundException{ // Comprobar esto creo que se tira para arriba pero no estoy seguro
		this.setFileName(fileName);
		this.setFileMode(fileMode);
		this.setFilePath(AFSDir+fileName);
		this.setLock(lock);
		this.setVice(vice);
		this.F = new RandomAccessFile(filePath,fileMode);
	}
	public byte[] read(int tam) throws RemoteException , IOException {
		byte [] buffer = new byte[tam];
		//this.lock.readLock().lock();
		int read = this.F.read(buffer);
		if (read<0) { // nothing to read
			//this.lock.readLock().unlock();
			return null;
		}
		if (read<tam) {
		return shrinkBuffer(buffer,read);
		}
		return buffer;
	}
	
	public void close() throws RemoteException,IOException {
		this.lock.readLock().unlock();
		this.vice.getLockManager().unbind(fileName);
		this.F.close();
		return;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public ReentrantReadWriteLock getLock() {
		return lock;
	}
	public void setLock(ReentrantReadWriteLock lock) {
		this.lock = lock;
	}
	

	public ViceImpl getVice() {
		return vice;
	}
	public void setVice(ViceImpl vice) {
		this.vice = vice;
	}
	
	private byte [] shrinkBuffer(byte [] buffer, int bufferSize) {
		byte [] smallerBuffer = new byte[bufferSize];
		for(int k=0; k < bufferSize;k++) {
			smallerBuffer[k]=buffer[k];
		}
		return smallerBuffer;
	}
	
}       

