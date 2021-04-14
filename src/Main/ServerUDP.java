package Main;

import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ServerUDP extends Thread
{



	private int port;

	private int id;

	private String ruta;

	public ServerUDP(int pId,String pRuta,int pPort) {
		port=pPort;
		ruta=pRuta;
		id=pId;

	}
	public void run() {

		try {
			Scanner sc = new Scanner(System.in);
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket outdata = null;
			DatagramPacket indata = null;
			byte[] senddata, receivedata;
			InetAddress address = InetAddress.getLocalHost();
			int size=1024;
			File f1=new File(ruta);
			double numPack=Math.ceil(((int)f1.length())/size);
			String hello = String.valueOf(numPack);
			System.out.println(hello);
			senddata = hello.getBytes();
			outdata = new DatagramPacket(senddata, senddata.length, address, port);
			ds.send(outdata);  

			FileInputStream fileReader=new FileInputStream(f1);
			BufferedInputStream bis=new BufferedInputStream(fileReader);


			for (double i = 0; i < numPack; i++) {
				byte[]b=new byte[size];
				bis.read(b,0,b.length);
				outdata=new DatagramPacket(b,b.length,address,port);
				ds.send(outdata);

			}
			bis.close();
			ds.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
}