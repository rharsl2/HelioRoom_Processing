/**
 * TODO Description
 * Created 10/2012
 * @author Rachel
 */

package helioroom.xmpp;

import helioroom.*;
import helioroom.util.*;

import java.util.Collection;
import java.util.Stack;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class HR_MessageListener implements MessageListener{

	public String frontPlanet;
	public String backPlanet;
	
	@Override
	public void processMessage(Chat chat, Message message) {
        System.out.println("Processing a message.");
        
        String body =message.toXML();
        String formatedBody = StringUtilities.toJava(body);
        
        System.out.println("Formatted XML: ");
        System.out.println(formatedBody);
        
//        String updateType = (String)message.getProperty("updateType");
//        
//        if(updateType == "inFrontOf"){
//          String submitter = (String)message.getProperty("name");
//          frontPlanet = (String)message.getProperty("front");
//          backPlanet = (String)message.getProperty("back");
//          
//          System.out.println(submitter +" says " +frontPlanet +" is in Front of " 
//              +backPlanet);
//        }else{
//          String body =message.toXML();
//          System.out.println("I'm not responsible for this type of packet: "
//          +body);
//          
//          Collection<Message.Body> bodies = message.getBodies();
//          System.out.println("Bodies size: "+bodies.size());
//          for(Message.Body s: bodies)
//           System.out.println("body message " +s.getMessage());
//          
//          Message.Type typeM= message.getType();
//          System.out.println("Type " +typeM);
//           
////          Collection<String> props = message.getPropertyNames();
////          for(String s: props)
////            System.out.println("Prop " +s);
//        }
        		
	}

}
