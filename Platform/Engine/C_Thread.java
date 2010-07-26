
import java.io.*;
import java.net.*;
import java.util.Iterator;

class C_Thread extends Thread{
	Socket clientSocket;
	C_Server Server;
	PrintWriter out;
	BufferedReader in;
	String Username="-null";
	String oldUN="";
	boolean ChangeUser;
	String Whisper="";
	boolean admin;
	boolean toAll;
	InetAddress address;
	
	C_Thread(Socket socket, C_Server server){
		super("C_Thread");
		clientSocket=socket;
		Server=server;
	}
	
	public void run(){
		try{
			address = clientSocket.getInetAddress();
			System.out.println(address.getHostAddress() + " Connected");
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine;
			out.println("Connected to "+InetAddress.getLocalHost()+"\n");
			Iterator it = Server.BannedIPs.iterator();
			while(it.hasNext()){
				String ip = (String)it.next();
				if (ip==address.getHostName()){
					//kick
					out.println("Failed to Connect: You Are Banned");
					out.println("-sdc");
					out.println("-end");
					Cleanup();
					break;
				}
			}
			it = Server.Usernames.iterator();
			while(it.hasNext()){
				out.println("-un"+(String)it.next());
			}
			
			while ((inputLine = in.readLine()) != null) {	

	    		if (inputLine.length()>=4 && inputLine.substring(0, 3).equals("-wm")){
	    			Whisper=inputLine.substring(3, inputLine.indexOf("|"));
	    			inputLine=inputLine.substring(inputLine.indexOf(":"));
	    			System.out.println(inputLine);
	    		}
	    		System.out.println(new String(address.getHostName())+" - "+inputLine);
	    		
	    		int colon = inputLine.indexOf(":");
	    		
	    		
	    		if (colon!=-1 && !Username.equals(inputLine.substring(0, inputLine.indexOf(":")))){
	    			
	    			if (inputLine.indexOf(":")!=-1 && inputLine.substring(0, inputLine.indexOf(":")).equals(""))
	    				inputLine=Username+inputLine;
	    			
	    			if (!Username.equals("-null"))
	    				Server.Usernames.remove(Username);
	    			oldUN=""+Username;//.toString();
	    			Username=inputLine.substring(0, inputLine.indexOf(":"));
	    			Server.Usernames.add(Username);
	    			ChangeUser=true;
	    		}

	    		//  /command
	    		if (colon!=-1 && inputLine.indexOf("/")-2==colon){
	    			if (inputLine.indexOf(" ", inputLine.indexOf("/"))!=-1){
	    				String command=inputLine.substring(inputLine.indexOf("/")+1, inputLine.indexOf(" ", inputLine.indexOf("/")));
	    				String param=inputLine.substring(inputLine.indexOf(" ", inputLine.indexOf(" ")+1)+1);
	    				param.trim();
	    				System.out.println(command+"\n"+param);
	    				if (command.equals("admin")){
	    					if (param.equals("omfgtoalhax")){
	    						out.println("You are now an Admin");
	    						admin=true;
	    					}else
	    						out.println("Wrong Password");
	    				}else if (command.equals("w") || command.equals("whisper")){
	    					Whisper=param.substring(0,param.indexOf(" "));
	    					System.out.println(Whisper);
	    					inputLine=": "+param.substring(param.indexOf(" ")+1);
	    					toAll=true;
	    				}else if (command.equals("kick") || command.equals("adminsay") || command.equals("adminw") || command.equals("ban")){
	    					if (admin){
	    						if (command.equals("ban")){
	    							it=Server.Threads.iterator();
	    							while(it.hasNext()){
	    								C_Thread client=(C_Thread)it.next();
	    								if (client.Username.equals(param)){
	    									Server.BannedIPs.add(client.address.getHostName());//.getAddress());
	    									client.out.println("-sdc");
	    									client.out.println("-end");
	    									client.Cleanup();
	    									System.out.println(Username + " Was Banned");
	    									out.println(param+" was banned from the server");
	    									break;
	    								}
	    							}
	    						}
	    						if (command.equals("kick")){
	    							it=Server.Threads.iterator();
	    							while(it.hasNext()){
	    								C_Thread client=(C_Thread)it.next();
	    								if (client.Username.equals(param)){
	    									client.out.println("-sdc");
	    									client.out.println("-end");
	    									System.out.println(client.Username + " Was Kicked");
	    									client.Cleanup();
	    									out.println(param+" was kicked from the server");
	    									break;
	    								}
	    							}
	    						}
	    					}else{
	    						out.println("You are not an Admin");
	    					}
	    				}else
	    					out.println("Invalid Command/Usage of Command");
	    			}else
	    				out.println("Invalid Command/Usage of Command");
	    			
	    			out.println("-end");
	    		}else
	    			toAll=true;
	    		if (toAll){
		    		Iterator iter = Server.Threads.iterator();
		    		while(iter.hasNext()){
		    			C_Thread client= (C_Thread)iter.next();
	
		    			if (ChangeUser){
		    				if (!oldUN.equals("-null")){
		    					client.out.println("-du"+oldUN);
		    				}
		    				client.out.println("-un"+Username);
		    			}
		    			if (Whisper.equals("") || Whisper.equals(Username)){
		    				if (inputLine.indexOf(":")==0)
		    					client.out.println(Username+inputLine);
		    				else
		    					client.out.println(inputLine);
		    			}else if (Whisper.equals(client.Username)){
		    				client.out.println("Whisper from "+Username+inputLine.substring(inputLine.indexOf(":")));
		    				out.println("Whisper to "+Whisper+inputLine.substring(inputLine.indexOf(":")));
		    			}
		    			client.out.println("-end");
		    		}
				}
	    		ChangeUser=false;
				oldUN=Username;
				Whisper="";
				toAll=false;
			}
		}catch(IOException e){
			System.out.println(Username + " Disconnected");
			Cleanup();
		}
	}
	
	public void Cleanup(){
		try{
			Server.Threads.remove(this);
			if (!Username.equals("-null")){
				Server.Usernames.remove(Username);
				Iterator iter = Server.Threads.iterator();
				while(iter.hasNext()){
					C_Thread client =(C_Thread)iter.next();
					client.out.println("-du"+Username);
					client.out.println("-end");
				}
			}
			in.close();
			out.close();
			clientSocket.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}
};