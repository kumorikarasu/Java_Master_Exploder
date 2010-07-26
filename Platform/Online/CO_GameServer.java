
/*
 * Sending/Receiving msgs
 * Syntax - "Server ID"+"MSG ID"+"Params"
 * 
 * Server ID - 10
 * Client ID's - 11-MaxClients
 * 
 * MSG ID - MSGS - Params
 * 00 - Say - "Text"
 * 01 - Team Red Say - "Text"
 * 02 - Team Blue Say - "Text"
 * 03 - Life - "Client ID"+"Value"
 * 04 - Spawn "Client ID"+"x"+"y"+"dir"+"Gun ID"+"Color ID"+"Nickname"
 * 05 - Update - "x"+"y"+"dir"+"isShooting"+"shootdir"
 * 06 - New Client Connected "Client Name"
 * 07 - Kick Client "Client ID"+"Kick Msg"
 * 08 <- DOESNT WORK CUZ ITS OCT NOT INT OR W/E...OUT OF RANGE
 * 10 - Ban Client "Client ID"
 * 11 - Dmg - "Client ID"+"Value"
 * 12 - Dead - "Killer ID"
 * 13 - Update Score "Client ID"+"Kills"+"Deaths"
 * 14 - Change Level - "Level Name"
 * 15 - Global Varible - "Variable ID"+"Value"
 * 		1 - Gravity - "0-999" - Default is 50
 * 		2 - Team Game - "0/1" - Default is 0
 * 		3 - CTF - "0/1" - Default is 0
 * 16 - Team Swap - "Client ID"
 * 17 - Picked up Flag - "Client ID"+"Flag ID" - FlagID: 1 - Red, 2 - Blue
 * 18 - Flag Cap - "Client ID"
 * 19 - Flag Dropped "Client ID"+"Flag ID"
 * 20 - Round Start
 * 21 - Round Over "Winner" - TeamID if team, ClientID if FFA
 * 22 - Create Grenade - "Client ID"+"x"+"y"+"dirx"+"diry"
 *
 * Server Window Commands
 * Command - Param - Info
 * echo - "Text" - displays text to server screen
 * say - "Text" - Displays text to all clients
 * kick - "Client ID" - kicks client from server
 * ban - "Client ID" "time" - Bans Clients IP from
 * 		joining server for "time" hours, 0 = Permaban
 * swap - "Client ID" - Switches Client to opposite Team
 * sv_gravity - "New Value" - Changes Gravity to new value
 * sv_teams - "0/1" - 0 - FFA, 1 - Teams
 * kill - "Client ID" - Kills clients player
 * info - N/A - shows all current clients connected and their IDs
 * 
 */
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Vector;

import javax.swing.JScrollPane;

public class CO_GameServer extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTextField ServerInput = null;

	private JScrollPane ServerWindow = null;

	protected static CO_ServerSend server;

	private static JTextArea Server = null;
	
	static int maxClients = 11+24;
	
	static String[] clientInfo = new String[maxClients];
	
	static Vector banned = new Vector();
	
	static CO_Player[] Clients = new CO_Player[maxClients];
	
	static String LevelName = "level1";
	
	static int gravity=50;
	
	static int Team=0;
	
	static int ctf=0;
	
	static int RoundType = 0; //0 - TimeLimit, >0 First to that number of kills/caps wins
	
	static int RoundTime = 5*60; // in Seconds
	
	static int RoundsPerLevel = 10;
	
	private final static String newline = "\n";  //  @jve:decl-index=0:

	/**
	 * This method initializes ServerInput	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getServerInput() {
		if (ServerInput == null) {
			ServerInput = new JTextField();
			ServerInput.setBounds(new Rectangle(0, 242, 490, 25));
			ServerInput.addActionListener(this);
		}
		return ServerInput;
	}

	/**
	 * This method initializes ServerWindow	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getServerWindow() {
		if (ServerWindow == null) {
			ServerWindow = new JScrollPane();
			ServerWindow.setBounds(new Rectangle(0, 0, 492, 240));
			ServerWindow.setViewportView(getServer());
		}
		return ServerWindow;
	}

	/**
	 * This method initializes Server	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextArea getServer() {
		if (Server == null) {
			Server = new JTextArea();
			Server.setEditable(false);
		}
		return Server;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args){
		CO_GameServer thisClass = new CO_GameServer();
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true);
		for (int a=0;a<CO_GameServer.maxClients;a++)
			clientInfo[a]="-1";
		
		try {
			server = new CO_ServerSend(Server, new DatagramSocket());
			Thread t = new Thread(new CO_ServerReceive(Server, new DatagramSocket(4445)));
			t.start();			
			t = new Thread(server);
			t.start();
			thisClass.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

			}
		});*/
	}

	/**
	 * This is the default constructor
	 */
	public CO_GameServer() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("Master Exploder Dedicated Server");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getServerInput(), null);
			jContentPane.add(getServerWindow(), null);
		}
		return jContentPane;
	}

	public void actionPerformed(ActionEvent arg0) {
        String text = ServerInput.getText();
        Server.append(text+newline);
        Server.append(" "+server.Command(text)+newline);
        ServerInput.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        Server.setCaretPosition(Server.getDocument().getLength());
        
	}

}
