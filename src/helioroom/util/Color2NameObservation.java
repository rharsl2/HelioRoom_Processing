package helioroom.util;

public class Color2NameObservation {
	public String planetColor;
	public String planetName;
	public String user;
	
	public Color2NameObservation(String planetColor, String planetName, String user){
		this.planetColor=planetColor;
		this.planetName=planetName;
		this.user=user;		
	}
	
	public String toString(){
		String obsv= "front: " +planetColor +" back: " +planetName +" user: " +user; 
		return obsv;
	}
	
}
