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
    public VenusFile(Venus venus, String fileName, String fileMode) throws RemoteException, IOException, FileNotFoundException {
    	this.setVenus(venus);
    	this.setFileName(fileName);
    	this.setFileMode(fileMode);
    	this.setFilePath(cacheDir + fileName);
    	File fileCache = new File(filePath);
    	if (fileCache.exists())  F = new RandomAccessFile(fileName, fileMode);
    	else { // file not found , proceed to download
    		viceReader = venus.getLookUp().download(fileName, fileMode);
    		int blockSize = venus.getBlockSize();
    		byte [] buffer = new byte[blockSize];
    		/* if file is going to be opened in RW, we can create it and write on it directly,
    		 *  but if not we have to create the file , write on it , and open it only in read mode*/
    		F = new RandomAccessFile(filePath, "rw");
			while((buffer = viceReader.read(blockSize)) != null) {
				this.F.write(buffer);
			}
			if(fileMode.equals("rw")) {
				F.close();
				F = new RandomAccessFile(filePath,fileMode);
			}
			viceReader.close();
			F.seek(0); // get pointer to beginning
    		
    	}
    	
    	
    }
    public int read(byte[] b) throws RemoteException, IOException {
        return this.F.read(b);
    }
    public void write(byte[] b) throws RemoteException, IOException {
    	this.F.write(b);
        return;
    }
    public void seek(long p) throws RemoteException, IOException {
    	this.F.seek(p);
        return;
    }
    public void setLength(long l) throws RemoteException, IOException {
    	this.F.setLength(l);
        return;
    }
    public void close() throws RemoteException, IOException {
    	
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
}

