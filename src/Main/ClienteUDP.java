package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ClienteUDP extends Thread{
	private  DataOutputStream dataOutputStream = null;
	private  DataInputStream dataInputStream = null;
	private  int id;
	private  int cantidadClientes;	
	private int puerto;	
	//private Cifrador cifrador;
	public ClienteUDP(int id, int puerto) {
		//cifrador=new Cifrador();
		this.puerto = puerto;
		this.id = id;
		
	}

	private  void receiveFile(String fileName) throws Exception{
		
		
	}


	@Override
	public void run() 
	{
		
	}

}
