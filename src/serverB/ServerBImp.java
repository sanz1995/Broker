package serverB;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import broker.Broker;


public class ServerBImp extends UnicastRemoteObject implements ServerB{

	List<String> libros;
	
	public ServerBImp() throws RemoteException{
		libros = new ArrayList<String>();
	}
	
	public void introducir_libro(String titulo){
		libros.add(titulo);
	}

	
	public String lista_libros(){
		String lista="";
		for(String i : libros){
			lista += i +"\n";
		}
		return lista;
	}
	public String ejecutar_servicio(String nom_servicio, Vector<Object> parametros_servicio) {
		if(nom_servicio.equals("introducir_libro")){
			introducir_libro((String)parametros_servicio.get(0));
			return "";
		}else if(nom_servicio.equals("lista_libros")){
			return lista_libros();
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		//Utils.setCodeBase(Broker.class);
		//System.setProperty("java.security.policy", "AllPermission.policy");
		//RMISecurityManager sec= new RMISecurityManager();
		//sec.checkAccept("127.0.0.1", 8000);
		//System.setSecurityManager(new RMISecurityManager());
		Registry registro = LocateRegistry.createRegistry(8002);
		ServerBImp obj = new ServerBImp();
		
        registro.bind("ServerB",obj);
        
        Registry r = LocateRegistry.getRegistry("127.0.0.1", 8000);
        
        Broker b = (Broker) r.lookup("Broker");
		
        b.registrar_servidor("127.0.0.1:8002", "ServerB");
        
        Vector<Object> v = new Vector<Object>();
        v.addElement(String.class);
        b.registrar_servicio("ServerB", "introducir_libro", v, null);
        b.registrar_servicio("ServerB", "lista_libros", new Vector<Object>(), null);
	}

	
}
