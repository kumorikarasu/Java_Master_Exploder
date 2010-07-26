

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class C_Chat extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private static JButton SendButton = null;

	private static JTextField Nickname = null;

	private JScrollPane OnlineList_Scroll = null;

	private static JList OnlineList = null;

	private static JScrollPane MessageWindow_Scroll = null;

	private static JTextPane MessageWindow = null;

	private JScrollPane ChatWindow_Scroll = null;

	private static JTextPane ChatWindow = null;
	
	private static C_Client Client = null;

	/**
	 * This method initializes SendButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private static JButton getSendButton() {
		if (SendButton == null) {
			SendButton = new JButton();
			SendButton.setBounds(new Rectangle(498, 385, 120, 52));
			SendButton.setText("Send");
			SendButton.setMnemonic(KeyEvent.VK_ENTER);
			SendButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (getOnlineList().getSelectedIndex() == -1)
						Client.SendMessage(getNickname().getText()+": "+getMessageWindow().getText());
					else{
						Client.SendMessage("-wm"+getOnlineList().getModel().getElementAt(getOnlineList().getSelectedIndex())
								+"|"+getNickname().getText()+": "+getMessageWindow().getText());
						getOnlineList().setSelectedIndex(-1);
						getOnlineList().clearSelection();
					}
					getMessageWindow().setText("");
					getMessageWindow().grabFocus();	
				}
			});
		}
		return SendButton;
	}

	/**
	 * This method initializes Nickname	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private static JTextField getNickname() {
		if (Nickname == null) {
			Nickname = new JTextField(10);
			Nickname.setBounds(new Rectangle(495, 13, 124, 31));
			Nickname.setText("Guest");
		}
		return Nickname;
	}

	/**
	 * This method initializes OnlineList_Scroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getOnlineList_Scroll() {
		if (OnlineList_Scroll == null) {
			OnlineList_Scroll = new JScrollPane();
			OnlineList_Scroll.setBounds(new Rectangle(496, 55, 121, 323));
			OnlineList_Scroll.setViewportView(getOnlineList());
		}
		return OnlineList_Scroll;
	}

	/**
	 * This method initializes OnlineList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private static JList getOnlineList() {
		if (OnlineList == null) {
			OnlineList = new JList();
			OnlineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return OnlineList;
	}

	/**
	 * This method initializes MessageWindow_Scroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private static JScrollPane getMessageWindow_Scroll() {
		if (MessageWindow_Scroll == null) {
			MessageWindow_Scroll = new JScrollPane();
			MessageWindow_Scroll.setBounds(new Rectangle(9, 386, 477, 53));
			MessageWindow_Scroll.setViewportView(getMessageWindow());
		}
		return MessageWindow_Scroll;
	}

	/**
	 * This method initializes MessageWindow	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private static JTextPane getMessageWindow() {
		if (MessageWindow == null) {
			MessageWindow = new JTextPane();
			MessageWindow.setText("");
			MessageWindow.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {
					int key=e.getKeyCode();
					String modifiers = KeyEvent.getKeyModifiersText(e.getModifiers());
					if (key==KeyEvent.VK_ENTER)
						if (modifiers.equals("Shift"))
							MessageWindow.setText(MessageWindow.getText()+"\n");
						else
							getSendButton().doClick();
				}

				public void keyReleased(KeyEvent e) {
					int key=e.getKeyCode();
					String modifiers = KeyEvent.getKeyModifiersText(e.getModifiers());
					if (key==KeyEvent.VK_ENTER && !modifiers.equals("Shift"))
						MessageWindow.setText("");
				}

				public void keyTyped(KeyEvent e) {
				}
			});
		}
		return MessageWindow;
	}

	/**
	 * This method initializes ChatWindow_Scroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getChatWindow_Scroll() {
		if (ChatWindow_Scroll == null) {
			ChatWindow_Scroll = new JScrollPane();
			ChatWindow_Scroll.setBounds(new Rectangle(15, 14, 466, 362));
			ChatWindow_Scroll.setViewportView(getChatWindow());
			ChatWindow_Scroll.setFocusable(false);
		}
		return ChatWindow_Scroll;
	}

	/**
	 * This method initializes ChatWindow	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private static JTextPane getChatWindow() {
		if (ChatWindow == null) {
			ChatWindow = new JTextPane();
			ChatWindow.setEditable(false);
		}
		return ChatWindow;
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Client = new C_Client("74.109.242.233", 1000);
		Client = new C_Client("5.61.204.41", 1000);
		//Client.getRootPane().setDefaultButton(SendButton);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				C_Chat thisClass = new C_Chat();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
		while(true){
			if (!Client.Step(getChatWindow(), getMessageWindow_Scroll(), getOnlineList())){
				break;
			}
		}
		Client.Cleanup();
		System.exit(0);
	}
	
	
	/**
	 * This is the default constructor
	 */
	public C_Chat() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(640, 480);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
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
			jContentPane.add(getSendButton(), null);
			jContentPane.add(getNickname(), null);
			jContentPane.add(getOnlineList_Scroll(), null);
			jContentPane.add(getMessageWindow_Scroll(), null);
			jContentPane.add(getChatWindow_Scroll(), null);
		}
		return jContentPane;
	}

}