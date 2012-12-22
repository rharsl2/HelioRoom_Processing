/**
 * TODO Description
 * Created 10/2012
 * @author Rachel
 */

package helioroom;

import java.util.HashMap;
import java.util.Stack;

import helioroom.util.*;
import processing.core.*;


public class Helio_Color2ColorSketch extends PApplet{
	Color2Color col2color; 
	PFont font, cursiveFont;	
	HashMap<String,Integer> savedObs = new HashMap<String,Integer>();
	int[][] countChart = new int[9][9];

	
	//TABLE CUSTOMIZATIONS
	int beginXval =120; //For Labels
	int beginYval =130; //For Labels
	int xSpaceInBetween= 160; //For Labels
	int ySpaceInBetween= 82; //For Labels
	
	int leftX =100; //For Table Lines
	int topY=80;  //For Table Lines
	int endXVal; //For Table Lines
	int bottomY; //For Table Lines
	
//	public boolean sketchFullScreen() {
//		  return true;
//	}
	
	public void setup(){
		size(displayWidth, displayHeight);
		endXVal=displayWidth-80;
		bottomY=displayHeight-80;
		//System.out.println("width" +displayWidth +"height" +displayHeight);
		//System.out.println("endXval" +endXVal +"bottomY" +bottomY);
		//size(800, 800);
		//background(255);
		
		col2color = new Color2Color(this);
		font = createFont("Arial",16,true);
		cursiveFont = loadFont("Hand_Of_Sean-30.vlw");

		textFont(font,22);              
		fill(255);  //Specify font color 
		printChartLabels();
		drawTable();
		refreshInfo(col2color.initialObs);
	}
	public void draw(){
		background(0);

		refreshInfo(col2color.pcktListener.receivedC2CMessages);
		drawTable();
		printChartLabels();
		printCounts();
	}
	
	//DATA STRUCTURES
	 public enum Colors {
		    RED,
		    ORANGE,
		    YELLOW,
		    GREEN,
		    BLUE,
		    PURPLE,
		    BROWN,
		    GRAY,
		    PINK
	 }
	
	
	//LOGICAL METHODS	
	public void drawTable(){
		stroke(255);
		strokeWeight(10);
		strokeCap(ROUND);
		line(leftX,topY,endXVal,topY); //(Line from point (beginXVal,topY) to (endXVal,topY)
		line(leftX,topY,leftX,bottomY);
		line(leftX,bottomY,endXVal,bottomY);
		line(endXVal,topY,endXVal,bottomY);
		
		//rect(beginXval,topY,700,550);
	}
	public void saveToChart(String frontPlanet, String backPlanet, int newCount){
		//Front planet on y index
		int yIndex= getPlanetIndex(frontPlanet);
		int xIndex = getPlanetIndex(backPlanet);
		
		countChart[xIndex][yIndex]=newCount;
	}
	public int getPlanetIndex(String planet){
		//ORDER FOR x and y Axis (left->right) (top->bottom):
		//red, orange, yellow, green, blue, purple, brown, grey, pink
		
		Colors color = Colors.valueOf(planet.toUpperCase());
		switch(color){
			case RED: return 0;
			case ORANGE: return 1;
			case YELLOW: return 2;
			case GREEN: return 3;
			case BLUE: return 4;
			case PURPLE: return 5;
			case BROWN: return 6;
			case GRAY: return 7;
			case PINK: return 8;
			default: return -100; //ERRROR
		}
	}
	public String indexToColor(int index){
		switch(index){
		case 0: return "Red";
		case 1: return "Orange";
		case 2: return "Yellow";
		case 3: return "Green";
		case 4: return "Blue";
		case 5: return "Purple";
		case 6: return "Brown";
		case 7: return "Gray";
		case 8: return "Pink";
		default: return "ERROR"; //ERRROR
		}
	}
	public int indexToXPixelPos(int index){	
		
		switch(index){
		case 0: return beginXval+10;
		case 1: return beginXval + xSpaceInBetween;
		case 2: return beginXval + xSpaceInBetween*2;
		case 3: return beginXval + xSpaceInBetween*3;
		case 4: return beginXval + xSpaceInBetween*4;
		case 5: return beginXval + xSpaceInBetween*5;
		case 6: return beginXval + xSpaceInBetween*6;
		case 7: return beginXval + xSpaceInBetween*7;
		case 8: return beginXval + xSpaceInBetween*8;
		default: return -100; //ERRROR
		}
	}
	public int indexToYPixelPos(int index){
		switch(index){
		case 0: return beginYval+10;
		case 1: return beginYval + ySpaceInBetween;
		case 2: return beginYval + ySpaceInBetween*2;
		case 3: return beginYval + ySpaceInBetween*3;
		case 4: return beginYval + ySpaceInBetween*4;
		case 5: return beginYval + ySpaceInBetween*5;
		case 6: return beginYval + ySpaceInBetween*6;
		case 7: return beginYval + ySpaceInBetween*7;
		case 8: return beginYval + ySpaceInBetween*8;
		default: return -100; //ERRROR
		}
	}
	public void refreshInfo(Stack<Color2ColorObservation> observations){
		for (Color2ColorObservation obs: observations){
			//Color2ColorObservation obs = observations.firstElement();
			//System.out.println("obsv: " +obs.toString()); 
			
			String key= obs.frontPlanet+"_"+obs.backPlanet;
			if(savedObs.containsKey(key)){
				int val =savedObs.get(key) +1;
				savedObs.put(key,val);
			}else{
				savedObs.put(key, 1);
			}
			Integer newCount=savedObs.get(key);
			saveToChart(obs.frontPlanet, obs.backPlanet, newCount);	
		}

		col2color.pcktListener.receivedC2CMessages.removeAllElements();
		
	}
	public void printCounts(){
		for (int i=0; i<9; i++){ 
			for (int j=0; j<9; j++){ 
				int xpos= indexToXPixelPos(i);
				int ypos= indexToYPixelPos(j);	
				if(i==j){
					fill(0xFFB22222);
					textFont(font,30); 
					text("X",xpos,ypos);
				}else{
					int count =countChart[i][j];
					if(count==0){
						fill(153);
					}else{
						fill(255);
					}
					textFont(font,30); 
					text(count,xpos,ypos);
				}
			}
		}
	}
	public void printChartLabels(){
		fill(255);
		textFont(cursiveFont,30); 
		text("HelioRoom Order Observations",(displayWidth/2 -230),35); 
		//text("Is In Front Of",(displayWidth/2 -40),40); 
		
		//Print X Labels
		for (int i=0; i<9; i++){ 
			int xpos= indexToXPixelPos(i);
			fill(getColor(i));
			textFont(font,22); 
			text(indexToColor(i),xpos,70);
		}
		//Print Y Labels
		for (int j=0; j<9; j++){ 
			int ypos= indexToYPixelPos(j);	
			fill(getColor(j));
			textFont(font,22); 
			text(indexToColor(j),20,ypos);
		}
		
	}
	public int getColor(int i){
		switch(i){
		case 0: return 0xFFcc0000;//RED
		case 1: return 0xFFff6600;//ORANGE
		case 2: return 0xFFffff00;//YELLOW
		case 3: return 0xFF00ee00;//GREEN
		case 4: return 0xFF0000ee;//BLUE
		case 5: return 0xFF9900cc;//PURPLE
		case 6: return 0xFFa0522d;//BROWN
		case 7: return 0xFF3d3d3d;//GRAY
		case 8: return 0xFFffb6c1;//PINK
		default: return 255; //ERRROR
		}
	}
}
