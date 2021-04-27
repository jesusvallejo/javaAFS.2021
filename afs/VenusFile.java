// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*; 
import java.io.*; 

public class VenusFile {
	public static final String cacheDir = "Cache/";
	private Venus venus;
	private String fileName;
	private String fileMode;
	private String filePath;
	private RandomAccessFile F;
	private ViceReader viceReader;
	private boolean modified;
	private long modSize;
	public VenusFile(Venus venus, String fileName, String fileMode) throws RemoteException, IOException, FileNotFoundException {
		this.modified = false;
		this.setVenus(venus);
		this.setFileName(fileName);
		this.setFileMode(fileMode);
		this.setFilePath(cacheDir + fileName);
		File fileCache = new File(filePath);
		if (fileCache.exists()) {
			System.err.println("existe");
			this.F = new RandomAccessFile(filePath, fileMode);}
		else { // file not found , proceed to download
			System.err.println("no existe");
			viceReader = venus.getLookUp().download(fileName, fileMode,venus.getCallBack());
			int blockSize = venus.getBlockSize();
			byte [] buffer = new byte[blockSize];
			/* if file is going to be opened in RW, we can create it and write on it directly,
			 *  but if not we have to create the file , write on it , and open it only in read mode*/
			this.F = new RandomAccessFile(filePath, "rw");
			while((buffer = viceReader.read(blockSize)) != null) {
				this.F.write(buffer);
			}
			if(!fileMode.equals("rw")) {
				F.close();
				this.F = new RandomAccessFile(filePath,fileMode);
			}
			viceReader.close();
		}
		this.F.seek(0); // get pointer to beginning of file
		this.setModSize(F.length());
	}
	public int read(byte[] b) throws RemoteException, IOException {  // DONE
		return this.F.read(b);
	}
	public void write(byte[] b) throws RemoteException, IOException { // DONE
		this.F.write(b);
		modified = true;
		return;
	}
	public void seek(long p) throws RemoteException, IOException { // DONE
		this.F.seek(p);
		return;
	}
	public void setLength(long l) throws RemoteException, IOException { //DONE
		this.F.setLength(l);
		return;
	}
	public void close() throws RemoteException, IOException {
		if(this.modified && this.fileMode.equals("rw")) { // 
			ViceWriter viceWriter = this.venus.getLookUp().upload(this.fileName,this.fileMode,venus.getCallBack());
			int blockSize=this.venus.getBlockSize();
			int bufferSize=0;
			byte[] buffer = new byte[blockSize];
			this.F.seek(0);
			while((bufferSize = this.F.read(buffer))!=-1) {
				if(blockSize>bufferSize) 
					viceWriter.write(shrinkBuffer(buffer,bufferSize));
				else
					viceWriter.write(buffer);
			}
			if(this.getModSize()!=F.length()) viceWriter.setLength(this.F.length());	
			viceWriter.close();
		}

		this.F.close();
		this.modified = false;
		return;
	}
	public Venus getVenus() {
		return venus;
	}
	public void setVenus(Venus venus) {
		this.venus = venus;
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

	private byte [] shrinkBuffer(byte [] buffer, int bufferSize) {
		byte [] smallerBuffer = new byte[bufferSize];
		for(int k=0; k < bufferSize;k++) {
			smallerBuffer[k]=buffer[k];
		}
		return smallerBuffer;
	}
	public long getModSize() {
		return modSize;
	}
	public void setModSize(long modSize) {
		this.modSize = modSize;
	}
}

