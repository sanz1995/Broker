package broker;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;




public class BrokerImp extends UnicastRemoteObject implements Broker{

	
	List<DirName> directions;
	List<Service> services;
	
	Registry registro;
	
	public BrokerImp() throws RemoteException{
		directions = new ArrayList<DirName>();
		services = new ArrayList<Service>();
	}
	
	public String ejecutar_servicio(String nom_servicio, 
			Vector<Object> parametros_servicio) throws RemoteException{
		Iterator<Service> i = services.iterator();
		
		boolean seguir = true;
		
		Service s=null;
		while(i.hasNext() & seguir){
			s=i.next();
			if(s.getService().equals(nom_servicio)){
				seguir=false;
			}
		}
		if(!seguir){
			seguir = true;
			
			Iterator<DirName> it = directions.iterator();
			
			DirName d=null;
			while(it.hasNext() & seguir){
				d=it.next();
				if(d.getName().equals(s.getName())){
					seguir=false;
				}
			}
			
			String ip = d.getDir().substring(0, d.getDir().lastIndexOf(":"));
			String port = d.getDir().substring(d.getDir().lastIndexOf(":")+1);
			Registry r= LocateRegistry.getRegistry(ip,Integer.parseInt(port));
			
			try {
				//Class.forName(d.getName());
				//Ejecutable e = (Ejecutable) r.lookup(d.getName());
				Object o =r.lookup(d.getName());
				return (String) o.getClass().getMethod(nom_servicio,s.getParameters()).invoke(o, parametros_servicio.toArray());
			} catch (NotBoundException e) {
				return "El servidor seleccionado no esta activo";
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "El servicio seleccionado no esta activo";
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "Error";
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "Error";
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "Los parametros introducidos no coinciden con los del servicio";
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "Error";
			}
		}else{
			return "El servicio seleccionado no esta registrado";
		}
	}
	
	public void registrar_servidor(String host_remoto_IP_port, String nombre_registrado){
		directions.add(new DirName(host_remoto_IP_port,nombre_registrado));
	}
	
	
	public void registrar_servicio(String nombre_registrado,String nombre_servicio
			,Vector<Object> lista_param,String tipoRetorno){
		Iterator<Service> i= services.iterator();
		boolean existe = false;
		while(i.hasNext() && !existe){
			if(i.next().getService().equals(nombre_servicio)){
				existe = true;
			}
		}
		if(!existe){
			services.add(new Service(nombre_registrado,nombre_servicio,lista_param));
		}
	}
	
	
	
	public static void main(String[] args) throws Exception{
		
		Utils.setCodeBase(BrokerImp.class);
		
		Registry registro = LocateRegistry.createRegistry(8000);
		
		Broker obj = new BrokerImp();
        
        registro.bind("Broker",obj);
		
	}
}
