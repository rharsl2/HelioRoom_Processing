package helioroom.util;

public class Color2ColorObservation {
	public String frontPlanet;
	public String backPlanet;
	public String user;
	
	public Color2ColorObservation(String frontPlanet, String backPlanet, String user){
		this.frontPlanet=frontPlanet;
		this.backPlanet=backPlanet;
		this.user=user;		
	}
	
	public String toString(){
		String obsv= "front: " +frontPlanet +" back: " +backPlanet +" user: " +user; 
		return obsv;
	}
	
}
