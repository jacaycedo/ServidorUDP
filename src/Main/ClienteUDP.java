package Main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClienteUDP extends Thread{

	
	private int id;
	
	private int port;
	
	public ClienteUDP(int pid,int pServerPort) {
		this.id=pid;
		this.port=pServerPort;
	}
    /**
     * @param args the command line arguments
     */
   public void run() {
    	

	  
	   try {
        byte[] senddata, receivedata;
        DatagramPacket out = null;
        DatagramPacket in = null;
        DatagramSocket socket = new DatagramSocket(port);
        while (true) {

            receivedata = new byte[1000];
            in = new DatagramPacket(receivedata, receivedata.length);
            socket.receive(in);
      
            String f = new String(in.getData());
            double numPack=Double.parseDouble(f);
            System.out.println("Paquetes a descargar:"+numPack);
	
    		FileOutputStream  fo=new FileOutputStream("ArchivosRecibidos/prueba1+"+id+".txt");   
    		for (double i = 0; i < numPack; i++) {
    			byte[]b=new byte[1024];
    			DatagramPacket inData=new DatagramPacket(b, b.length);
    			socket.receive(inData);
    			fo.write(inData.getData());
    			
    		}
            
    		socket.close();
    		fo.close();

        }
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
        

        
    }
}
