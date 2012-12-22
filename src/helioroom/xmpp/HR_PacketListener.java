/**
 * TODO This listener is called whenever a chat message is received.
 * Created 10/2012
 * @author Rachel
 */

package helioroom.xmpp;

import java.util.Collection;
import java.util.Stack;

import helioroom.util.Color2ColorObservation;
import helioroom.util.Color2NameObservation;
import helioroom.util.StringUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class HR_PacketListener implements PacketListener {
	public String frontPlanet;
	public String backPlanet;
	public String color;
	public String name;
	public String user;
	public Stack<Color2ColorObservation> receivedC2CMessages = new Stack<Color2ColorObservation>();
	public Stack<Color2NameObservation> receivedC2NMessages = new Stack<Color2NameObservation>();
	
	@Override
	public void processPacket(Packet packet) {
	      // System.out.println("Processing a packet.");
	        
	        String body =packet.toXML();
	        String formatedBody = StringUtilities.toJava(body);
	        
	      //  System.out.println("Formatted XML: ");
	      //  System.out.println(formatedBody);
	        
	        //TODO Use this to determine if delayed packet?
	        Collection<PacketExtension> pckExts =packet.getExtensions();
	        if (pckExts.size() != 0){
//	        	for (PacketExtension s: pckExts){
//	   	        	System.out.println("Element Name: " +s.getElementName());
//	   	        	System.out.println("Namespace: " +s.getNamespace());
//	   	        	System.out.println(s.toXML());
//	   	        }
	        	System.out.println("Delayed packet. Will skip.");
	        	return; //Don't want delayed packets?
	        }
	     
	        Message msg = (Message) packet;
	        boolean validMsg = parseXML(msg);
	        if(validMsg){
	        	//updateGUI
	        }
	}

	public boolean parseXML(Message msg){
        Element root = null;
		try {
			root = DocumentHelper.parseText(msg.getBody()).getRootElement();
			if (root==null)
				return false;
			// Pick the right XML processor based on the root element name
			if (root.getName().equals("inFrontOf")){			
				frontPlanet=root.attributeValue("front");
				backPlanet=root.attributeValue("back");
				user =root.attributeValue("user");
				System.out.println(user +" says " +frontPlanet + " is in front of " +backPlanet);
				
				Color2ColorObservation obs = new Color2ColorObservation(frontPlanet,backPlanet,user);
				receivedC2CMessages.add(obs);
				return true;
			}else if (root.getName().equals("identify")){			
				color=root.attributeValue("color");
				name=root.attributeValue("name");
				user =root.attributeValue("user");
				System.out.println(user +" says " +color + " is " +name);
				
				Color2NameObservation obs = new Color2NameObservation(color,name,user);
				receivedC2NMessages.add(obs);
				return true;
			}			
		} catch (DocumentException e) {
			// Not XML... skip
		}
		return false;
	}


}
