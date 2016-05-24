package broker;

public class DirName {

	private String name;
	private String dir;
	
	public DirName(String dir, String name){
		this.name=name;
		this.dir=dir;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDir(){
		return dir;
	}
	
	//public boolean equals(DirName d){
		//return d.getName().equals(name);
	//}
}
