package serverB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ServerB extends Remote{

	public void introducir_libro(String titulo)  throws RemoteException;
	
	public String lista_libros()  throws RemoteException;
	
}
