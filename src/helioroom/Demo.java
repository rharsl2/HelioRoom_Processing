/**
 * TODO Description
 * Created 10/2012
 * @author Rachel
 */

package helioroom;

import processing.core.PApplet;

public class Demo {
//    public static String xmppServer = new String("131.193.79.212");
//    public static String mongodbServer =new String("131.193.79.212");
//    
    public static String xmppServer = new String("169.254.225.196");
    public static String mongodbServer =new String("169.254.225.196");
    
	public static void main(String args[]) {
	    //PApplet.main(new String[] { "--present", "helioroom.Helio_Color2ColorSketch" });
	    PApplet.main(new String[] { "--present", "helioroom.Helio_Color2NameSketch" });
	 }
}
