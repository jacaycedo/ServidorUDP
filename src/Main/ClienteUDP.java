package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClienteUDP extends Thread{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
    	
        int port = 8080;
        byte[] senddata, receivedata;
        DatagramPacket out = null;
        DatagramPacket in = null;
        DatagramSocket socket = new DatagramSocket(port);
        byte[]b=new byte[10000000];
        while (true) {

            receivedata = new byte[1000];
            in = new DatagramPacket(receivedata, receivedata.length);
            socket.receive(in);
            InetAddress srcaddress = in.getAddress();
            int srcport = in.getPort();
            String f = new String(in.getData());
            double numPack=Double.parseDouble(f);
            System.out.println("Paquetes a descargar:"+numPack);
            
            DatagramPacket p=new DatagramPacket(b,b.length);
    		socket.receive(p);
    		System.out.print("Received packets");
    		    		
    		
    		
    		String filedata=new String(p.getData()).trim();
    		System.out.println(filedata);

    	
    		PrintWriter pw = new PrintWriter("ArchivosRecibidos/prueba1.txt");
    		pw.println(filedata);
    		pw.close();
            
        

        }
        
        

        
    }
}
