package helioroom;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Stack;
import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import helioroom.util.Color2ColorObservation;
import helioroom.util.Color2NameObservation;
import helioroom.xmpp.HR_MessageListener;
import helioroom.xmpp.HR_PacketListener;

import org.json.*;

import org.jivesoftware.smackx.muc.MultiUserChat; 
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;

import processing.core.PApplet;

public class Color2Name {
    public HR_MessageListener msgListener = new HR_MessageListener(); //Will perform processing of an XMPP Chat Message
    public HR_PacketListener pcktListener = new HR_PacketListener();//Will perform processing of an XMPP Group Message
    public PApplet parent; // The parent PApplet that we will render ourselves onto
    public Stack<Color2NameObservation> initialObs = new Stack<Color2NameObservation>();
    //String xmppServer = new String("rharsley-laptop.local");
    //String mongodbServer =new String("localhost");
    
    Color2Name(PApplet p) {
        parent = p;
        loadInitialObs();
        setupChat();
    }
    
	public void setupChat(){
		System.out.println("Setting up Chat Enviornement.");
	     String room = new String("helioRoom@conference.rharsley");
	     String userName= new String("identifydisplay@rharsley");
	     String pass = new String("identifydisplay");
	     
	     // Create a connection to the XMPP server.
	     ConnectionConfiguration config = new ConnectionConfiguration(Demo.xmppServer, 5222);
	     Connection con = new XMPPConnection(config);

	     try {
	       // Connect to the server
	       con.connect();
	       con.login(userName, pass);
	       
	       ChatManager chatmanager = con.getChatManager();
	       chatmanager.addChatListener(new ChatManagerListener() {
	           //@Override
	           public void chatCreated(Chat chat, boolean createdLocally)
	           {
	               if (!createdLocally){
	                   chat.addMessageListener(msgListener);
	               }
	           }
	       });

	       MultiUserChat muc = new MultiUserChat(con,room);
	       muc.join(userName);
	       muc.addMessageListener(pcktListener);
	       
	       // Disconnect from the server
	       // con.disconnect();
	     } catch (XMPPException e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	     }
	     
	}
	public void loadInitialObs(){
		Mongo m;
		try {
			m = new Mongo(Demo.mongodbServer , 27017 );
			DB db = m.getDB( "helio" );
			
			Set<String> colls = db.getCollectionNames();

			for (String s : colls) {
			    System.out.println(s);
			}
			
			DBCollection coll = db.getCollection("identify");
			
			DBCursor cursor = coll.find();
			try {
				while (cursor.hasNext()) {
					BasicDBObject main = (BasicDBObject)cursor.next();
					//System.out.println(main);
					
					JSONObject json = new JSONObject(main.toString());
					for (Object key: json.keySet()){
						//System.out.println("key: "+key);				
						
						if(!key.toString().equals("_id")){
							String user = key.toString();
							
							JSONArray array = (JSONArray)json.get(user);	
							JSONObject obj = array.getJSONObject(0);
							String planetColor = obj.getString("color");
							String planetName = obj.getString("name");
							
							
							Color2NameObservation obs = new Color2NameObservation(planetColor,planetName,user);
							initialObs.add(obs);
							
							System.out.println("new c2n: "+ obs.toString());
						}
//						
					}
								

				}
			} finally {
				cursor.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
