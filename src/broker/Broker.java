package broker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Broker extends Remote{

	public String ejecutar_servicio(String nom_servicio, 
			Vector<Object> parametros_servicio) throws RemoteException;
	
	public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado) throws RemoteException;
	
	public void registrar_servicio(String nombre_registrado,String nombre_servicio
			,Vector<Object> parametros_servicio,String tipoRetorno) throws RemoteException;
}
