

import java.io.*;
import java.net.*;
import java.util.Vector;

class C_Server{
	ServerSocket serverSocket;
	Vector Threads = new Vector();
	Vector Usernames = new Vector();
	Vector BannedIPs =  new Vector();

	
	C_Server(int port){
		try {
    		serverSocket = new ServerSocket(port);
    		System.out.println("Server Started");
    		//clientSocket= serverSocket.accept();
		} catch (IOException e) {
    		System.out.println("Could not listen on port: "+port);
		}
	}
	
	boolean Step(){
		try{
			C_Thread temp = new C_Thread(serverSocket.accept(), this);
			Thread t=new Thread(temp);
			Threads.add(temp);
			t.start();
		}catch(IOException e){
			System.out.println(e);	
		}
		return true;
	}
	
	void Cleanup(){
		try{
			
			//clientSocket.close();
			serverSocket.close();
			System.out.println("Server Shutdown");
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
};