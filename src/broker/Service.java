package broker;

import java.util.Vector;

public class Service {
	private String name;
	private String service;
	private Class<?>[] parameters;
	
	public Service(String name,String service,Vector<Object> parameters){
		this.name=name;
		this.service=service;
		if(parameters!=null){
			this.parameters = new Class[parameters.size()];
			for(int i=0; i<parameters.size();i++){
				this.parameters[i] = (Class<?>) parameters.get(i);
			}
		}
		//this.parameters=parameters;
	}
	
	public String getName(){
		return name;
	}
	
	public String getService(){
		return service;
	}
	
	public Class<?>[] getParameters(){
		return parameters;
	}
}
