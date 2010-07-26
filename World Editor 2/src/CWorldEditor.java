import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.ImageFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class CWorldEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int width=800,height=608,gridx=32,gridy=32;
	static boolean grid=true,snaptogrid=true;
	static int mousebutton[] = new int[4];
	//public CWorldEditorDisplay EntityLoader = new CWorldEditorDisplay();  //  @jve:decl-index=0:

	private CWorldEditor thisframe = this;
	
	private JPanel jContentPane = null;

	private JMenuBar jJMenuBar1 = null;

	private JMenu jMenu = null;

	private JSplitPane jSplitPane = null;

	private JMenuItem jMenuItem2 = null;

	private JMenu jMenu1 = null;

	private JMenuItem jMenuItem3 = null;

	private JMenuItem jMenuItem4 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField11 = null;

	private JTextField jTextField2 = null;

	private JTree jTree = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane21 = null;

	private CWorldEditorDisplay WorldEditorDisplay;

	private JTabbedPane jTabbedPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane1 = null;

	private JButton jButton = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel61 = null;

	private JLabel jLabel611 = null;

	private JLabel jLabel6111 = null;

	private JLabel jLabel6112 = null;

	private JLabel jLabel61121 = null;

	private JMenuItem jMenuItem5 = null;

	private JMenuItem jMenuItem6 = null;

	private JMenuItem jMenuItem8 = null;

	private JMenuItem jMenuItem9 = null;

	private JMenuItem jMenuItem10 = null;

	private JMenuItem jMenuItem11 = null;

	private JMenuItem jMenuItem12 = null;

	private JMenu jMenu2 = null;

	private JMenuItem jMenuItem13 = null;

	private JCheckBoxMenuItem jCheckBoxMenuItem = null;

	private JCheckBoxMenuItem jCheckBoxMenuItem1 = null;

	private JTextField jTextField3 = null;
	private JTextField jTextField31 = null;
	private JTextField jTextField311 = null;
	private JTextField jTextField3111 = null;
	private JTextField jTextField31111 = null;
	private JTextField jTextField311111 = null;
	private JLabel jLabel7 = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel91 = null;
	private JLabel jLabel92 = null;
	private JCheckBox jCheckBox1 = null;
	private JCheckBox jCheckBox2 = null;
	private JCheckBox jCheckBox3 = null;
	ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:
	
	boolean l1=true,l2=false,l3=false;
	
	static CSprite Tileset;
	static CTileset tileset = null;
	private JButton loadbackgroundimage = null;
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Settings", null, getJPanel(), null);
			jTabbedPane.addTab("Entities", null, getJScrollPane(), null);
			jTabbedPane.addTab("Tiles", null, getJTabbedPane1(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					WorldEditorDisplay.Draw = null;
					WorldEditorDisplay.repaint();
					if (jTabbedPane.getSelectedIndex() == 1){
						if (jTree.getLastSelectedPathComponent() != null)
							getEditor().getDraw(jTree.getLastSelectedPathComponent().toString());
					}
				}
			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {

			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(14, 105, 93, 16));
			jLabel5.setText("Grid Height:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(14, 73, 93, 16));
			jLabel4.setText("Grid Width:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(14, 42, 93, 16));
			jLabel3.setText("Level Height:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(14, 13, 93, 16));
			jLabel2.setText("Level Width:");
			jPanel = new JPanel();

			jPanel.setLayout(null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField11(), null);
			jPanel.add(getJTextField2(), null);

			jPanel.add(getLoadbackgroundimage(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	void SetLevelSize(int _x, int _y){
		width=_x;
		height=_y;
	}
	void SetGridSize(int _x, int _y){
		gridx=_x;
		gridy=_y;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(127, 11, 42, 20));
			jTextField.setText("800");
			jTextField.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					String checkstring = jTextField.getText();
					boolean valid=true;
					if (checkstring.length()>0){
						for (int a=0;a<checkstring.length();a++){
							if (!(checkstring.charAt(a)>=48 && checkstring.charAt(a)<=57 )){
								valid=false;
							}
						}
						if (valid){
							jTextField.setBackground(Color.white);
							Integer a = new Integer(checkstring);
							int b = a.intValue();
							getEditor().ChangeSize(b, height);
							SetLevelSize(b,height);
							getEditor().repaint();
						}else{
							jTextField.setBackground(Color.red);
						}
					}
				}
			});
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(127, 42, 42, 20));
			jTextField1.setText("608");
			jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					String checkstring = jTextField1.getText();
					boolean valid=true;
					if (checkstring.length()>0){
						if (checkstring.length()>0){
							for (int a=0;a<checkstring.length();a++){
								if (!(checkstring.charAt(a)>=48 && checkstring.charAt(a)<=57 )){
									valid=false;
								}
							}
							if (valid){
								jTextField1.setBackground(Color.white);
								Integer a = new Integer(checkstring);
								int b = a.intValue();
								getEditor().ChangeSize(width, b);
								SetLevelSize(width,b);
								getEditor().repaint();
							}else{
								jTextField1.setBackground(Color.red);
							}
						}
					}
				}
			});
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField11	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setBounds(new Rectangle(128, 71, 42, 20));
			jTextField11.setText("32");
			jTextField11.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					String checkstring = jTextField11.getText();
					boolean valid=true;
					if (checkstring.length()>0){
						for (int a=0;a<checkstring.length();a++){
							if (!(checkstring.charAt(a)>=48 && checkstring.charAt(a)<=57 )){
								valid=false;
							}
						}
						if (valid){
							jTextField11.setBackground(Color.white);
							Integer a = new Integer(checkstring);
							int b = a.intValue();
							SetGridSize(b,gridy);
							getEditor().repaint();
						}else{
							jTextField11.setBackground(Color.red);
						}
					}
				}
			});
		}
		return jTextField11;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(127, 103, 42, 20));
			jTextField2.setText("32");
			jTextField2.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					String checkstring = jTextField2.getText();
					if (checkstring.length()>0){
						boolean valid=true;
						for (int a=0;a<checkstring.length();a++){
							if (!(checkstring.charAt(a)>=48 && checkstring.charAt(a)<=57 )){
								valid=false;
							}
						}
						if (valid){
							jTextField2.setBackground(Color.white);
							Integer a = new Integer(checkstring);
							int b = a.intValue();
							SetGridSize(gridx,b);
							getEditor().repaint();
						}else{
							jTextField2.setBackground(Color.red);
						}
					}
				}
			});
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = getEditor().CreateTree();
			jTree.setRootVisible(false);
			this.jTree
			.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					getEditor().getDraw(jTree.getLastSelectedPathComponent().toString());
					getEditor().repaint();
				}
			});
			this.jTree.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					getEditor().repaint();
				}
			});
		}
		return jTree;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane21(), gridBagConstraints);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane21	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane21() {
		if (jScrollPane21 == null) {
			jScrollPane21 = new JScrollPane();
			jScrollPane21.setViewportView(getEditor());
			jScrollPane21.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane21;
	}

	private CWorldEditorDisplay getEditor() {
		if (WorldEditorDisplay == null){
			WorldEditorDisplay = new CWorldEditorDisplay();
			WorldEditorDisplay.addMouseListener(new java.awt.event.MouseAdapter() {   

				public void mouseExited(java.awt.event.MouseEvent e) {    
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//WorldEditorDisplay.Click(e.getButton());
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					WorldEditorDisplay.onEnt=false;
					WorldEditorDisplay.onTile=false;
					if (jTabbedPane.getSelectedIndex()==1)
						WorldEditorDisplay.onEnt=true;
					else if (jTabbedPane.getSelectedIndex()==2)
						WorldEditorDisplay.onTile=true;
					
					//if (jTabbedPane.getSelectedIndex()==1){
						mousebutton[e.getButton()]=1;
						WorldEditorDisplay.Press(e.getButton());
					//}
				}
				public void mouseReleased(java.awt.event.MouseEvent e){
					mousebutton[e.getButton()]=0;
				}

			});
			WorldEditorDisplay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseMoved(java.awt.event.MouseEvent e) {				
					WorldEditorDisplay.onEnt=false;
					WorldEditorDisplay.onTile=false;
					if (jTabbedPane.getSelectedIndex()==1)
						WorldEditorDisplay.onEnt=true;
					if (jTabbedPane.getSelectedIndex()==2)
						WorldEditorDisplay.onTile=true;
					
					if (snaptogrid){
						WorldEditorDisplay.mouse_x = e.getX()/gridx * gridx;
						WorldEditorDisplay.mouse_y = e.getY()/gridy * gridy;	
					}else{
						WorldEditorDisplay.mouse_x = e.getX();
						WorldEditorDisplay.mouse_y = e.getY();
					}
					WorldEditorDisplay.repaint();
				}
				public void mouseDragged(java.awt.event.MouseEvent e) {
					WorldEditorDisplay.onEnt=false;
					WorldEditorDisplay.onTile=false;
					if (jTabbedPane.getSelectedIndex()==1)
						WorldEditorDisplay.onEnt=true;
					if (jTabbedPane.getSelectedIndex()==2)
						WorldEditorDisplay.onTile=true;
					
					if (snaptogrid){
						WorldEditorDisplay.mouse_x = e.getX()/gridx * gridx;
						WorldEditorDisplay.mouse_y = e.getY()/gridy * gridy;	
					}else{
						WorldEditorDisplay.mouse_x = e.getX();
						WorldEditorDisplay.mouse_y = e.getY();
					}
					
					System.out.println(mousebutton[1]);
					if (mousebutton[1]==1)
						WorldEditorDisplay.Press(1);
					if (mousebutton[2]==1)
						WorldEditorDisplay.Press(2);
					if (mousebutton[3]==1)
						WorldEditorDisplay.Press(3);
					
					WorldEditorDisplay.repaint();
					/*WorldEditorDisplay.onEnt=false;
					WorldEditorDisplay.onTile=false;
					if (jTabbedPane.getSelectedIndex()==1)
						WorldEditorDisplay.onEnt=true;
					if (jTabbedPane.getSelectedIndex()==2)
						WorldEditorDisplay.onTile=true;
					
					if (snaptogrid){
						//if (jTabbedPane.getSelectedIndex()==1){
							WorldEditorDisplay.mouse_x = e.getX()/gridx * gridx;
							WorldEditorDisplay.mouse_y = e.getY()/gridy * gridy;	
						}else{
						//	WorldEditorDisplay.mouse_x = e.getX();
						//	WorldEditorDisplay.mouse_y = e.getY();
					//	}
					}
					if (mousebutton[1]==1)
						WorldEditorDisplay.Press(1);
					if (mousebutton[2]==1)
						WorldEditorDisplay.Press(2);
					if (mousebutton[3]==1)
						WorldEditorDisplay.Press(3);

					WorldEditorDisplay.repaint();
					*/
				}
			});
		}
		return WorldEditorDisplay;
	}

	/**
	 * This method initializes jTabbedPane1	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("Settings", null, getJPanel3(), null);
			jTabbedPane1.addTab("Tileset", null, getJPanel2(), null);

			jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					tileset.repaint();
				}
			});
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJScrollPane1(), gridBagConstraints1);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel92 = new JLabel();
			jLabel92.setBounds(new Rectangle(92, 247, 10, 16));
			jLabel92.setText("3");
			jLabel91 = new JLabel();
			jLabel91.setBounds(new Rectangle(53, 247, 16, 16));
			jLabel91.setText("2");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(16, 247, 12, 16));
			jLabel9.setText("1");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(8, 223, 38, 16));
			jLabel8.setText("Layer:");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(7, 201, 91, 16));
			jLabel7.setText("Show Grid:");
			jLabel61121 = new JLabel();
			jLabel61121.setBounds(new Rectangle(7, 177, 91, 16));
			jLabel61121.setText("Tileset Y Space:");
			jLabel6112 = new JLabel();
			jLabel6112.setBounds(new Rectangle(7, 151, 91, 16));
			jLabel6112.setText("Tileset X Space:");
			jLabel6111 = new JLabel();
			jLabel6111.setBounds(new Rectangle(7, 125, 90, 16));
			jLabel6111.setText("Tileset Y Offset:");
			jLabel611 = new JLabel();
			jLabel611.setBounds(new Rectangle(7, 98, 90, 16));
			jLabel611.setText("Tileset X Offset:");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(7, 67, 78, 16));
			jLabel61.setText("Tileset Y Grid:");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(7, 45, 78, 16));
			jLabel6.setText("Tileset X Grid:");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getJButton(), null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(jLabel61, null);
			jPanel3.add(jLabel611, null);
			jPanel3.add(jLabel6111, null);
			jPanel3.add(jLabel6112, null);
			jPanel3.add(jLabel61121, null);
			jPanel3.add(getJTextField3(), null);
			jPanel3.add(getJTextField31(), null);
			jPanel3.add(getJTextField311(), null);
			jPanel3.add(getJTextField3111(), null);
			jPanel3.add(getJTextField31111(), null);
			jPanel3.add(getJTextField311111(), null);
			jPanel3.add(jLabel7, null);
			jPanel3.add(getJCheckBox(), null);
			jPanel3.add(jLabel8, null);
			jPanel3.add(jLabel9, null);
			jPanel3.add(jLabel91, null);
			jPanel3.add(jLabel92, null);
			jPanel3.add(getJCheckBox1(), null);
			jPanel3.add(getJCheckBox2(), null);
			jPanel3.add(getJCheckBox3(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTileset());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(7, 8, 134, 30));
			jButton.setText("New Tileset");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//Create a file chooser
					final JFileChooser fc = new JFileChooser(new File("."));

					fc.addChoosableFileFilter(new Filter());

					int returnVal = fc.showOpenDialog(thisframe);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getName() + ".");
			            Tileset = new CSprite(file.getPath(),Color.magenta);
			            tileset.setSize(Tileset.width, Tileset.height);
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }

				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(101, 42, 41, 20));
			jTextField3.setText("32");
		}
		return jTextField3;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("File");
			jMenu.add(getJMenuItem2());
			jMenu.add(getJMenuItem3());
			jMenu.addSeparator();
			jMenu.add(getJMenuItem5());
			jMenu.add(getJMenuItem6());
			jMenu.addSeparator();
			//jMenu.add(getJMenuItem8());
			//jMenu.add(getJMenuItem9());
			//jMenu.add(getJMenuItem10());
			//jMenu.add(getJMenuItem11());
			//jMenu.addSeparator();
			jMenu.add(getJMenuItem12());




		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem2	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem("New");
			jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_N, ActionEvent.CTRL_MASK)
			);
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getEditor().Level.clear();
				}
			});
		}
		return jMenuItem2;
	}

	/**
	 * This method initializes jMenuItem3	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3 = new JMenuItem("Open");
			jMenuItem3.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_O, ActionEvent.CTRL_MASK)
			);
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					File dataDirectory = new File(".");
				//	File defaultFile = new File(dataDirectory, "level");
					
					final JFileChooser fc = new JFileChooser(dataDirectory);
				
					fc.addChoosableFileFilter(new MelFilter());

					int returnVal = fc.showOpenDialog(thisframe);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	
			            File file = fc.getSelectedFile();
			            WorldEditorDisplay.SaveFile = file;
			           	try {
							BufferedReader br = new BufferedReader(new FileReader(file));
							width=  Integer.valueOf(br.readLine()).intValue();
							height= Integer.valueOf(br.readLine()).intValue();
							gridx=  Integer.valueOf(br.readLine()).intValue();
							gridy=  Integer.valueOf(br.readLine()).intValue();
							WorldEditorDisplay.Background = new CSprite(br.readLine(),Color.MAGENTA);
							Tileset= new CSprite(br.readLine(),Color.MAGENTA);
							String line;
							WorldEditorDisplay.Level.clear();
							br.readLine();
							while((line = br.readLine())!=null){
								if (line.compareTo("")<=0)
									break;
								
								String temp = line.substring(0, 3);
								
								int num,size;
								
								int entityID = Integer.valueOf(temp).intValue();
								line=line.substring(4, line.length());
								num = line.indexOf("|");
								size = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								
								line = line.substring(num+1, line.length());
								int Arguments[] = new int[size];
								for (int a=2;a<size;a++){
									num = line.indexOf("|");
									Arguments[a] = (int)Double.valueOf(line.substring(0, num)).doubleValue();
									line = line.substring(num+1, line.length());
								}
								
								CWorldEntity A = new CWorldEntity(4+size);
								A.Arguments=Arguments;
								A.Arguments[0]=entityID;
								
								//Find Entity and Set Sprite
								Iterator nameitt = WorldEditorDisplay.Entities.iterator();
								while (nameitt.hasNext()){
									CWorldEntity ent = (CWorldEntity) nameitt.next();
									if (A.Arguments[0] == ent.Arguments[0]){
										A.id = ent.id;
										A.Sprite = ent.Sprite;
									}
								}
								
								WorldEditorDisplay.Level.add(A);
							}
							
							//Load Tilesets
							while((line = br.readLine())!=null){
								if (line.compareTo("")<=0)
									break;
								
								Vector4 Pos = new Vector4();
								
								int num = line.indexOf("|");
								Pos.x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.x2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								
								WorldEditorDisplay.l1.add(Pos);
							}
							
							while((line = br.readLine())!=null){
								if (line.compareTo("")<=0)
									break;
								
								Vector4 Pos = new Vector4();
								
								int num = line.indexOf("|");
								Pos.x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.x2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								
								WorldEditorDisplay.l2.add(Pos);
							}
							
							while((line = br.readLine())!=null){
								if (line.compareTo("")<=0)
									break;
								
								Vector4 Pos = new Vector4();
								
								int num = line.indexOf("|");
								Pos.x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.x2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								num = line.indexOf("|");
								Pos.y2 = (int)Double.valueOf(line.substring(0, num)).doubleValue();
								line = line.substring(num+1, line.length());
								
								WorldEditorDisplay.l2.add(Pos);
							}
							
							
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			            //This is where a real application would open the file.
			          //  System.out.println("Opening: " + file.getName() + ".");
			          //  Tileset = new CSprite(file.getPath());
			        } else {
			        //	System.out.println("Open command cancelled by user.");
			        }
				}
			});
		}
		return jMenuItem3;
	}

	/**
	 * This method initializes jMenuItem5	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem("Save");
			jMenuItem5.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK)
			);
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getEditor().Save();
				}
			});
		}
		return jMenuItem5;
	}

	/**
	 * This method initializes jMenuItem6	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem("Save As...");
			jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File dataDirectory = new File(".");
					final JFileChooser fc = new JFileChooser(dataDirectory);
					fc.addChoosableFileFilter(new MelFilter());
					
					int returnVal = fc.showSaveDialog(thisframe);

			        if (returnVal == JFileChooser.APPROVE_OPTION)
			       		CWorldEditorDisplay.SaveFile = fc.getSelectedFile();
					
					getEditor().Save();
				}
			});
		}
		return jMenuItem6;
	}

	/**
	 * This method initializes jMenuItem8	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem8() {
		if (jMenuItem8 == null) {
			jMenuItem8 = new JMenuItem("1 ");
		}
		return jMenuItem8;
	}

	/**
	 * This method initializes jMenuItem9	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem9() {
		if (jMenuItem9 == null) {
			jMenuItem9 = new JMenuItem();
		}
		return jMenuItem9;
	}

	/**
	 * This method initializes jMenuItem10	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem10() {
		if (jMenuItem10 == null) {
			jMenuItem10 = new JMenuItem();
		}
		return jMenuItem10;
	}

	/**
	 * This method initializes jMenuItem11	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem11() {
		if (jMenuItem11 == null) {
			jMenuItem11 = new JMenuItem();
		}
		return jMenuItem11;
	}

	/**
	 * This method initializes jMenuItem12	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem12() {
		if (jMenuItem12 == null) {
			jMenuItem12 = new JMenuItem("Exit");
		}
		return jMenuItem12;
	}

	/**
	 * This method initializes jMenu2	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu2() {
		if (jMenu2 == null) {
			jMenu2 = new JMenu();
			jMenu2.setText("Edit");
			//jMenu2.add(getJMenuItem13());
			//jMenu2.addSeparator();
			jMenu2.add(getJCheckBoxMenuItem());
			jMenu2.add(getJCheckBoxMenuItem1());
		}
		return jMenu2;
	}

	/**
	 * This method initializes jMenuItem13	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem13() {
		if (jMenuItem13 == null) {
			jMenuItem13 = new JMenuItem("Undo");
			jMenuItem13.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_Z, ActionEvent.CTRL_MASK)
			);
		}
		return jMenuItem13;
	}

	/**
	 * This method initializes jCheckBoxMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getJCheckBoxMenuItem() {
		if (jCheckBoxMenuItem == null) {
			jCheckBoxMenuItem = new JCheckBoxMenuItem("Grid");
			jCheckBoxMenuItem.setState(true);
			jCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_G, ActionEvent.CTRL_MASK)
			);
			jCheckBoxMenuItem.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					if (grid)
						grid=false;
					else
						grid=true;
					getEditor().repaint();
				}
			});
		}
		return jCheckBoxMenuItem;
	}

	/**
	 * This method initializes jCheckBoxMenuItem1	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getJCheckBoxMenuItem1() {
		if (jCheckBoxMenuItem1 == null) {
			jCheckBoxMenuItem1 = new JCheckBoxMenuItem("Tileset Visible");
			jCheckBoxMenuItem1.setState(true);
			jCheckBoxMenuItem1.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK)
			);
			jCheckBoxMenuItem1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

				//	if (snaptogrid)
					//	snaptogrid=false;
				//	else
					//	snaptogrid=true;
					getEditor().repaint();
				}
			});
		}
		return jCheckBoxMenuItem1;
	}

	/**
	 * This method initializes jTextField31	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField31() {
		if (jTextField31 == null) {
			jTextField31 = new JTextField();
			jTextField31.setBounds(new Rectangle(101, 67, 41, 20));
			jTextField31.setText("32");
		}
		return jTextField31;
	}

	/**
	 * This method initializes jTextField311	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField311() {
		if (jTextField311 == null) {
			jTextField311 = new JTextField();
			jTextField311.setBounds(new Rectangle(101, 96, 41, 20));
			jTextField311.setText("0");
		}
		return jTextField311;
	}

	/**
	 * This method initializes jTextField3111	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField3111() {
		if (jTextField3111 == null) {
			jTextField3111 = new JTextField();
			jTextField3111.setBounds(new Rectangle(101, 123, 41, 20));
			jTextField3111.setText("0");
		}
		return jTextField3111;
	}

	/**
	 * This method initializes jTextField31111	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField31111() {
		if (jTextField31111 == null) {
			jTextField31111 = new JTextField();
			jTextField31111.setBounds(new Rectangle(101, 149, 41, 20));
			jTextField31111.setText("0");
		}
		return jTextField31111;
	}

	/**
	 * This method initializes jTextField311111	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField311111() {
		if (jTextField311111 == null) {
			jTextField311111 = new JTextField();
			jTextField311111.setBounds(new Rectangle(101, 175, 41, 20));
			jTextField311111.setText("0");
		}
		return jTextField311111;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(101, 199, 21, 21));
			jCheckBox.setSelected(true);
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */


	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {

			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(24, 245, 21, 21));
			jCheckBox1.setSelected(true);
			jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					   Object source = e.getItemSelectable();
					    if (source == jCheckBox1) {
					    	if (l1)
					    		l1=false;
					    	else{
					    		l1=true;
					    		WorldEditorDisplay.layer=1;	
					    	}
					    }
				}
			});
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jCheckBox2	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setBounds(new Rectangle(60, 245, 21, 21));
			jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					   Object source = e.getItemSelectable();
					    if (source == jCheckBox2) {
					    	if (l2)
					    		l2=false;
					    	else{
					    		l2=true;
					    		WorldEditorDisplay.layer=2;	
					    	}
					    }
				}
			});
		}
		return jCheckBox2;
	}

	/**
	 * This method initializes jCheckBox3	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setBounds(new Rectangle(99, 245, 21, 21));
			jCheckBox3.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					   Object source = e.getItemSelectable();
					    if (source == jCheckBox3) {
					    	if (l3)
					    		l3=false;
					    	else{
					    		l3=true;
					    		WorldEditorDisplay.layer=3;	
					    	}
					    }
					    
				}
			});
		}
		return jCheckBox3;
	}

	/**
	 * This method initializes tileset	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private CTileset getTileset() {
		if (tileset == null) {
			tileset = new CTileset();
			tileset.setLayout(new GridBagLayout());
			tileset.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseMoved(java.awt.event.MouseEvent e) {
					tileset.mouse_x = e.getX();
					tileset.mouse_y = e.getY();
					tileset.repaint();
				}
				public void mouseDragged(java.awt.event.MouseEvent e) {
					tileset.mouse_x = e.getX();
					tileset.mouse_y = e.getY();
					tileset.repaint();
				}
			});
			tileset.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					tileset.mouse_button = e.getButton();
					tileset.repaint();
				}
			});
		}
		return tileset;
	}

	/**
	 * This method initializes loadbackgroundimage	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLoadbackgroundimage() {
		if (loadbackgroundimage == null) {
			loadbackgroundimage = new JButton();
			loadbackgroundimage.setBounds(new Rectangle(20, 140, 141, 41));
			loadbackgroundimage.setText("Background Image");
			loadbackgroundimage.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					final JFileChooser fc = new JFileChooser(new File("."));

					fc.addChoosableFileFilter(new Filter());

					int returnVal = fc.showOpenDialog(thisframe);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getName() + ".");
			            WorldEditorDisplay.Background = new CSprite(file.getPath(),Color.magenta);
			            WorldEditorDisplay.repaint();
			        }
				}
			});
		}
		return loadbackgroundimage;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CWorldEditor thisClass = new CWorldEditor();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public CWorldEditor() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		getEditor().LoadEntities(new File("Data/WorldEditor.dat"));

		this.setSize(803, 647);
		this.setJMenuBar(getJJMenuBar1());
		this.setContentPane(getJContentPane());
		this.setTitle("World Editor");
		
		group.add(jCheckBox1);
		group.add(jCheckBox2);
		group.add(jCheckBox3);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar1	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar1() {
		if (jJMenuBar1 == null) {
			jJMenuBar1 = new JMenuBar();
			jJMenuBar1.add(getJMenu());
			jJMenuBar1.add(getJMenu2());
			jJMenuBar1.add(getJMenu1());
		}
		return jJMenuBar1;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(8);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJTabbedPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jMenu1	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("Help");
			jMenu1.add(getJMenuItem4());
		}
		return jMenu1;
	}

	/**
	 * This method initializes jMenuItem4	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem("About");
		}
		return jMenuItem4;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
