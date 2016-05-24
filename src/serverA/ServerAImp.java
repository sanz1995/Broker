package serverA;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import broker.Broker;


public class ServerAImp extends UnicastRemoteObject implements ServerA{

	//M3tax
	Date date;
	
	
	public ServerAImp() throws RemoteException{
		date = new Date();
	}
	
	
	
	public String dar_fecha(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		return dateFormat.format(date);
	}

	
	public String dar_hora(){
		
		DateFormat dateFormat = new SimpleDateFormat("h:mm a");
		
		return dateFormat.format(date);
	}
	
	
	public String ejecutar_servicio(String nom_servicio, Vector<Object> parametros_servicio) {
		if(nom_servicio.equals("dar_hora")){
			return dar_hora();
		}else if(nom_servicio.equals("dar_fecha")){
			return dar_fecha();
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		//Utils.setCodeBase(ServerA.class);
		
		Registry registro = LocateRegistry.createRegistry(8001);
		
		ServerAImp obj = new ServerAImp();
		
        registro.bind("ServerA",obj);
		
        
        Registry r = LocateRegistry.getRegistry(8000);
        
        Broker b = (Broker) r.lookup("Broker");
		
        b.registrar_servidor("127.0.0.1:8001", "ServerA");
        
        b.registrar_servicio("ServerA", "dar_hora",  new Vector<Object>(), null);
        b.registrar_servicio("ServerA", "dar_fecha",  new Vector<Object>(), null);
	}


	
}
