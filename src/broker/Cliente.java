package broker;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

public class Cliente {

	public static void main(String[] args){
		Registry registro;
		try {
			registro = LocateRegistry.getRegistry("127.0.0.1",8000);
			Broker broker = (Broker) registro.lookup("Broker");
			
			Vector<Object> v = new Vector<Object>();
			
			v.addElement("Berserk");
			broker.ejecutar_servicio("introducir_libro", v);
			
			System.out.println(broker.ejecutar_servicio("lista_libros", new Vector<Object>()));
			
			
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch(RemoteException e){
			 e.printStackTrace();
		}
	}
}
