package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ServerThread extends Thread {
	private int id;
	private DatagramSocket socket;
	private String ruta="";
	private DataOutputStream dataOutputStream = null;
	private DataInputStream dataInputStream = null;
	private int archivo;
	private int cantidadClientes;

	//private Cifrador cifrador;

	public ServerThread(int identificacion,String path,int opcion, int puerto,int cantidad) 
	{
		try
		{
			//cifrador=new Cifrador();
			cantidadClientes=cantidad;
			this.socket = new DatagramSocket(puerto);	
			this.id=identificacion;
			ruta=path;
			archivo=opcion;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void enviar()
	{

		try
		{
			System.out.println("Server "+id+" esperando solicitudes en puerto: " + socket.getLocalPort());
			sendFile(ruta);
		} 
		catch (Exception e) 
		{

		}
	}

	private void sendFile(String path) throws Exception
	{
		int bytes = 0;
		File file = new File(path);
		byte[] buffer = Files.readAllBytes(file.toPath());
		System.out.println(buffer);
		InetAddress dir  = InetAddress.getByName("192.168.1.1");
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length, dir , socket.getLocalPort());
		System.out.println("Sending package from server " + id);
		socket.send(pack);
	}


	@Override
	public void run() 
	{
		enviar();
	}

}
