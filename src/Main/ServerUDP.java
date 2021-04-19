package Main;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.*;
public class ServerUDP extends Thread
{



	private int port;

	private int id;

	private String ruta;
	
	private Cifrador cifra;
	private String hash;
	public ServerUDP(int pId,String pRuta,int pPort) {
		port=pPort;
		ruta=pRuta;
		id=pId;
		cifra = new Cifrador();

	}
	public void run() {

		try {
			enviarHash();
			
			Scanner sc = new Scanner(System.in);
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket outdata = null;
			DatagramPacket indata = null;
			byte[] senddata, receivedata;
			InetAddress address = InetAddress.getByName("192.168.1.254");
			int size=1024;
			
			File f1=new File(ruta);
			double numPack= Math.ceil(((int)f1.length())/size)==0?1:Math.ceil(((int)f1.length())/size);
			String hello = String.valueOf(numPack);
			System.out.println(hello);
			senddata = hello.getBytes();
			outdata = new DatagramPacket(senddata, senddata.length, address, port);
			ds.send(outdata);  
			
			FileInputStream fileReader=new FileInputStream(f1);
			BufferedInputStream bis=new BufferedInputStream(fileReader);

			long inicio = System.currentTimeMillis();
			for (double i = 0; i < numPack; i++) {
				byte[]b=new byte[size];
				bis.read(b,0,b.length);
				outdata=new DatagramPacket(b,b.length,address,port);
				ds.send(outdata);

			}
			long fin = System.currentTimeMillis();
			long tiempoTransferencia = fin-inicio;
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"); 
			LocalDateTime now = LocalDateTime.now();  
			String nombreLog="logsServidor/"+dtf.format(now)+"id"+id+"-log.txt";
			PrintWriter writer = new PrintWriter(nombreLog, "UTF-8");
			
			long fileSize=f1.length();//Tamanio archivo en bytes
			String fileName=ruta.substring(5, ruta.length());
			writer.println("Nombre Archivo: "+fileName);
			writer.println("Tamaño Archivo: "+fileSize+"bytes");
			writer.println("Cantidad de Paquetes Transmitidos: "+numPack);
			writer.println("Tiempo Transferencia: "+tiempoTransferencia+"ms");
			writer.println("Id Cliente al que se realizo transferencia: "+id);
			writer.println("Hash enviado: "+hash);
			writer.close();

			bis.close();
			ds.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void enviarHash()
	{
		DataInputStream in;
		DataOutputStream out;
		try 
		{
			hash = cifra.getFileChecksum(ruta);
			ServerSocket s = new ServerSocket(port);
			Socket sc = s.accept();
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
			out.writeUTF(hash);
			s.close();
		}
		catch (Exception e) {
			
		}
	}
}