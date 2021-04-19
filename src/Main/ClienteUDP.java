package Main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Timer;

public class ClienteUDP extends Thread{


	private int id;

	private int port;
	DatagramSocket  socket;
	Cifrador cifra ;

	public ClienteUDP(int pid,int pServerPort) {
		this.id=pid;
		this.port=pServerPort;
		cifra = new Cifrador();
	}
	/**
	 * @param args the command line arguments
	 */
	public void run() {
		try {
			String hashRecibido = pedirHash();
			byte[] senddata, receivedata;
			DatagramPacket out = null;
			DatagramPacket in = null;
			socket = new DatagramSocket(port);
			Timer timer = new Timer();
			double numPack = 0;
			long startTime = System.currentTimeMillis();
			while (true && !socket.isClosed()) {
				receivedata = new byte[1000];
				in = new DatagramPacket(receivedata, receivedata.length);
				try 
				{
					socket.receive(in);
					timer.schedule(new TimeOutTask(timer, this), 10000);
				}
				catch (Exception e) 
				{
					System.out.println("El socket se cerro debido a inactividad");
				}
				String f = new String(in.getData());
				numPack=Double.parseDouble(f);
				startTime = System.currentTimeMillis();
				FileOutputStream  fo=new FileOutputStream("ArchivosRecibidos/prueba1+"+id+".txt");   
				for (double i = 0; i < numPack; i++) {
					timer.cancel();
					timer = new Timer();
					timer.schedule(new TimeOutTask(timer, this), 10000);
					byte[]b=new byte[1024];
					DatagramPacket inData = new DatagramPacket(b, b.length);
					try 
					{
						socket.receive(inData);
					}
					catch (Exception e) 
					{
						System.out.println("El socket se cerro debido a inactividad");
						break;
					}
					byte [] data = new byte[inData.getLength()];
					System.arraycopy(inData.getData(), inData.getOffset(), data, 0, inData.getLength());
					fo.write(data);
				}
				
				socket.close();
				fo.close();

			}
			File f = new File("ArchivosRecibidos/prueba1+\"+id+\".txt");
			String hash = cifra.getFileChecksum("ArchivosRecibidos/prueba1+"+id+".txt");
			System.out.println("Recibido " + hashRecibido);
			System.out.println("Generado "+ hash);
			long endTime = System.currentTimeMillis();
			long tiempoTransferencia = endTime - startTime;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now();  
			
			String nombreLog="logsCliente/"+dtf.format(now)+"-c"+(id)+"-log.txt";  
			PrintWriter writer = new PrintWriter(nombreLog, "UTF-8");
			writer.println("Nombre Archivo: "+ "prueba1"+id+".txt");
			writer.println("Tamaño Archivo: "+f.length()+"bytes");
			writer.println("Cantidad de Paquetes: "+numPack);
			writer.println("Tiempo Transferencia: "+tiempoTransferencia+"ms");
			writer.println("Id Cliente al que se realizo transferencia: "+id);
			writer.println("Estado de transferencia: " + 200);
			writer.println("Hash recibido: " + hash);
			System.out.println("Client "+ (id)+ " Received File");
			writer.close(); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void apagar()
	{
		socket.close();
		this.interrupt();
	}
	
	public String pedirHash()
	{
		DataInputStream in;
		DataOutputStream out;
		String hash = "";
		try 
		{
			Socket s = new Socket("192.168.1.255", port);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			hash = in.readUTF();
			s.close();
			in.close();
			out.close();
		}
		catch (Exception e) {
			
		}
		return hash;
	}
}
