package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class ClientThread {

		
	private final static int serverPort = 5555;
	public static void main(String argv[]) 
	{
		Scanner lector = new Scanner(System.in);	
		System.out.println("Cuantos clientes simultaneos desea implementar");
		int cantidad =Integer.parseInt(lector.nextLine());
		lector.close();
		for (int i = 0; i < cantidad; i++) 
		{
			ClienteUDP newThread=new ClienteUDP((i+1),serverPort+i);
			newThread.start();
		}
	}

}
