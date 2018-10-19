import javax.crypto.SealedObject;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.Locale;
import java.util.Random;
import java.io.*;

public class Client {

    public static void main (String args[]){

        System.out.println("Hello, world!");

        //userID for the uid param in cleint_request.java
        int userID = 34206809;
        //password for the pass. auth. in client_request.java
        long password = 1522030216;
        //Random number generator for the NONSE
        Random rand = new Random();
        int randInt = rand.nextInt(100) + 1;
        //Intantiate new client request obj
        Client_request cr =  new Client_request(userID, randInt, password);
        //Initialise server interface
        CW_server_interface serverInfc;

        //Instantiate String variable for filename
        String filename = "/Users/kobi/IdeaProjects/Dist_Sys_CW_1/src/spec.doc";

        File file = new File(filename);

        //Instantiate fileoutputstream
        FileOutputStream ois = null;


        //Intialise server response
        Server_response servResp;
        Server_response contents;


        try{
            //Get ref to remote object through the registry
            serverInfc = (CW_server_interface) Naming.lookup("rmi://scc311-server.lancs.ac.uk/CW_server");
            //use ref to call remote methods
            servResp = serverInfc.getSpec(userID, cr);
            //System.out.println(servResp);

            int currentStatus = serverInfc.getStatus(userID);
            System.out.println(currentStatus);

            //Create fileoutputstream to pass to write_to method
            ois = new FileOutputStream(filename);
            servResp.write_to(ois);



            /**
             ALL CODE BELOW IS FOR READING THE SERIALISED FILE
             **/


        } catch (RemoteException re){

            System.out.println("RemoteException: " + re);
        } catch (NotBoundException e) {
            System.out.println("NotBoundException: ");
            e.printStackTrace();
        } catch(IOException ex) {
            System.out.println("IOException is caught");
        }


    }

}
