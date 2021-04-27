// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.net.MalformedURLException;
import java.rmi.*; 

public class Venus {
	private String envRH= "REGISTRY_HOST";
	private String envRP= "REGISTRY_PORT";
	private String envB=  "BLOCKSIZE";
	private Remote lookUpPath;
	
	private String registryHost;
	private int registryPort;
	private int blockSize;
	private Vice lookUp;
	
	private VenusCB callback;
	public Venus()throws MalformedURLException, RemoteException, NotBoundException{
		this.setRegistryHost(System.getenv(envRH)); // get environmental REGISTRY_HOST
		this.setRegistryPort(Integer.parseInt(System.getenv(envRP)));// get environmental REGISTRY_HOST and cat to Integer
		this.setBlockSize(Integer.parseInt(System.getenv(envB)));// get environmental REGISTRY_HOST and cast to Integer
		this.lookUpPath = Naming.lookup("//"+this.registryHost+":"+this.registryPort + "/AFS"); // example: //192.168.1.3:3333/AFS
		this.setLookUp((Vice) lookUpPath);
		this.callback = new VenusCBImpl();
	}
	

	public String getRegistryHost() {
		return registryHost;
	}
    
	public void setRegistryHost(String registryHost) {
		this.registryHost = registryHost;
	}
    
	public int getRegistryPort() {
		return registryPort;
	}
	
	public void setRegistryPort(int registryPort) {
		this.registryPort = registryPort;
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}


	public Vice getLookUp() {
		return lookUp;
	}


	public void setLookUp(Vice lookUp) {
		this.lookUp = lookUp;
	}


	public Remote getLookUpPath() {
		return lookUpPath;
	}


	public void setLookUpPath(Remote lookUpPath) {
		this.lookUpPath = lookUpPath;
	}
	
	public VenusCB getCallBack() {
        return this.callback;
    } 
	
}

