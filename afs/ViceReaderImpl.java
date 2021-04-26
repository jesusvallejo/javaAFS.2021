// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
	private static final String AFSDir = "AFSDir/";
	private RandomAccessFile F;
	private String fileName;
	private String fileMode;
	private String filePath;

	public ViceReaderImpl(String fileName, String fileMode/* añada los parámetros que requiera */)
			throws RemoteException , FileNotFoundException{ // Comprobar esto creo que se tira para arriba pero no estoy seguro
		this.setFileName(fileName);
		this.setFileMode(fileMode);
		this.setFilePath(AFSDir+fileName);
		F = new RandomAccessFile(filePath,fileMode);
	}
	public byte[] read(int tam) throws RemoteException , IOException {
		byte [] buffer = new byte[tam];
		byte [] smallerBuffer; // in case we read less than expected
		int read = F.read(buffer);
		if (read<0) // nothing to read
			return null;
		if (read<tam) {
			smallerBuffer = new byte [read];
			for(int k=0; k < buffer.length ;k++) {
				smallerBuffer[k]=buffer[k];
			}
			return smallerBuffer;
		}
		return buffer;
	}
	
	public void close() throws RemoteException {
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
	
}       

