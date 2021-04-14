package Main;

import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ServerUDP 
{

	 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        int port = 8080;
        Scanner sc = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket outdata = null;
        DatagramPacket indata = null;
        byte[] senddata, receivedata;
        InetAddress address = InetAddress.getLocalHost();

        int size=1024;
        String ruta="data/archivo1.txt";
        File f1=new File(ruta);
        double numPack=Math.ceil(((int)f1.length())/size);
        String hello = String.valueOf(numPack);
        System.out.println(hello);
        senddata = hello.getBytes();
        outdata = new DatagramPacket(senddata, senddata.length, address, port);
        ds.send(outdata);  
        
		FileReader fileReader=new FileReader(f1);
		BufferedReader br=new BufferedReader(fileReader);
		StringBuilder sb1=new StringBuilder();

		String line;
		while((line=br.readLine())!=null)
		{
			sb1.append(line);
			sb1.append("\n");
		}
		System.out.println(sb1.toString());
		
		byte[] senttoserver=sb1.toString().getBytes();
		DatagramPacket p2=new DatagramPacket(senttoserver,senttoserver.length, address, port);
		ds.send(p2);
		ds.close();
        
    }
}