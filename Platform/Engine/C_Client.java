

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;

class C_Client{
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	String outText="";
	DefaultListModel listModel;
	
	C_Client(String _address, int port){
		System.out.println("Attempting to connect to "+_address+".");
		for(int a=1;a<=5;++a){
			try{
				clientSocket = new Socket(_address, port);
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				listModel = new DefaultListModel();
				System.out.println("Connected!");
				break;
			}catch(IOException e){
				System.out.println("Attempt "+a+" of 5 failed to connect.");
				if (a==5){
					System.out.println("Failed to connect...Exiting");
					System.exit(0);
				}
			}
		}
	}
	
	boolean Step(JTextPane textBox, JScrollPane scrollPane, JList OnlineList){
		try{
			String fromServer;
			while ((fromServer = in.readLine()) != null) {
			    //textBox.setText(textBox.getText()+fromServer+"\n");
			    if (fromServer.length()>=4 && fromServer.substring(fromServer.length()-4).equals("-end"))
			        break;
			    else if (fromServer.length()>=4 && fromServer.substring(0,4).equals("-sdc")){
			    	return false;
			    }else if (fromServer.length()>=4 && fromServer.substring(0,3).equals("-un")){
			    	listModel.addElement(fromServer.substring(3));
			    	OnlineList.setModel(listModel);
			    	//textBox.setText(textBox.getText()+fromServer+"\n");
			    }else if(fromServer.length()>=4 && fromServer.substring(0,3).equals("-du")){
			    	listModel.remove(listModel.indexOf(fromServer.substring(3)));
			    	OnlineList.setModel(listModel);
			    }
			    else{
			    	textBox.setText(textBox.getText()+fromServer+"\n");
			    	textBox.selectAll();
			    }
			    
			}
		}catch(IOException e){
			System.out.println(e);
			Cleanup();
			return false;
		}
		return true;
	}
	
	void SendMessage(String msg){
		outText=msg;
		out.println(msg);
	}
	
	void Cleanup(){
		try{
			in.close();
			out.close();
			clientSocket.close();
			System.out.println("Client Shutdown");
		}catch(IOException e){
			System.out.println(e);
		}
	}
};