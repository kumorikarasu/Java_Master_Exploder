
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 */

/**
 * @author Sean and Kyle
 *
 */
public class CW_Worldbuilder extends JFrame implements MouseListener, MouseMotionListener {


	private static final long serialVersionUID = 1L;

	private JMenuBar Main_Menu = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTree Objectlist = null;

	private JMenu File = null;

	private JMenuItem Properties = null;

	private JMenuItem New = null;

	private JMenuItem Exit = null;

	private JMenu Edit = null;

	private JCheckBoxMenuItem Grid = null;

	private JCheckBoxMenuItem Snaptogrid = null;

	public static WC_Editor Editor = null;

	CW_Worldbuilder thisClass = this;

	protected String CurrentSelection;

	protected boolean SnaptoGrid;

	static int gridx=32,gridy=32;
	static CW_Level CurrentLevel;
	public static boolean grid;
	public static int width=800,height=600;
	public static boolean isSaved=true;
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}	

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes Main_Menu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMain_Menu() {
		if (this.Main_Menu == null) {
			this.Main_Menu = new JMenuBar();
			this.Main_Menu.add(getFile());
			this.Main_Menu.add(getEdit());
		}
		return this.Main_Menu;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (this.jSplitPane == null) {
			this.jSplitPane = new JSplitPane();
			this.jSplitPane.setDividerLocation(150);
			this.jSplitPane.setLeftComponent(getJScrollPane());
			this.jSplitPane.setRightComponent(getJScrollPane1());

		}
		return this.jSplitPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (this.jScrollPane == null) {
			this.jScrollPane = new JScrollPane();
			this.jScrollPane.setViewportView(getObjectlist());
		}
		return this.jScrollPane;
	}

	private JScrollPane getJScrollPane1() {
		if (this.jScrollPane1 == null) {
			this.jScrollPane1 = new JScrollPane();
			this.jScrollPane1.setViewportView(getEditor());

		}
		return this.jScrollPane1;
	}

	/**
	 * This method initializes Objectlist	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getObjectlist() {
		if (this.Objectlist == null) {
			DefaultMutableTreeNode everything = new DefaultMutableTreeNode("Entites");
			DefaultMutableTreeNode category = null;
			DefaultMutableTreeNode object = null;

			category = new DefaultMutableTreeNode("Blocks");
			object = new DefaultMutableTreeNode("32x");
			category.add(object);
			object = new DefaultMutableTreeNode("64x32");
			category.add(object);
			object = new DefaultMutableTreeNode("128x32");
			category.add(object);
			object = new DefaultMutableTreeNode("256x32");
			category.add(object);
			object = new DefaultMutableTreeNode("512x32");
			category.add(object);
			object = new DefaultMutableTreeNode("32x64");
			category.add(object);
			object = new DefaultMutableTreeNode("32x128");
			category.add(object);
			object = new DefaultMutableTreeNode("32x256");
			category.add(object);
			object = new DefaultMutableTreeNode("32x512");
			category.add(object);
			everything.add(category);

			/*
			category = new DefaultMutableTreeNode("Enemies");
			object = new DefaultMutableTreeNode("AI");
			category.add(object);
			object = new DefaultMutableTreeNode("Patrol Node");
			category.add(object);
			object = new DefaultMutableTreeNode("Reverse Node");
			category.add(object);
			object = new DefaultMutableTreeNode("Jump Left Patrol");
			category.add(object);
			object = new DefaultMutableTreeNode("Jump Right Patrol");
			category.add(object);
			object = new DefaultMutableTreeNode("Jump Left Always");
			category.add(object);
			object = new DefaultMutableTreeNode("Jump Right Always");
			category.add(object);
			//C_Node_MultiplayerSpawn
			everything.add(category);
			*/

			category = new DefaultMutableTreeNode("Multiplayer");
			object = new DefaultMutableTreeNode("Spawn Point");
			category.add(object);
			object = new DefaultMutableTreeNode("Health Pack Spawn");
			category.add(object);
			everything.add(category);

			category = new DefaultMutableTreeNode("Red Flag");
			everything.add(category);
			
			category = new DefaultMutableTreeNode("Blue Flag");
			everything.add(category);

			category = new DefaultMutableTreeNode("Delete");
			everything.add(category);

			this.Objectlist = new JTree(everything);
			this.Objectlist.setRootVisible(false);
			this.Objectlist
			.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					CW_Worldbuilder.this.CurrentSelection=(CW_Worldbuilder.this.Objectlist.getLastSelectedPathComponent().toString()); 
					Editor.CurrentSelection=CW_Worldbuilder.this.CurrentSelection; 
					Editor.getDraw(CW_Worldbuilder.this.CurrentSelection);
				}
			});
			this.Objectlist.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (CW_Worldbuilder.this.Objectlist.getLastSelectedPathComponent()!=null){
						CW_Worldbuilder.this.CurrentSelection=(CW_Worldbuilder.this.Objectlist.getLastSelectedPathComponent().toString()); 
						Editor.CurrentSelection=CW_Worldbuilder.this.CurrentSelection; 
						Editor.getDraw(CW_Worldbuilder.this.CurrentSelection);
					}
				}
			});
		}
		return this.Objectlist;
	}

	/**
	 * This method initializes File	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFile() {
		if (this.File == null) {
			this.File = new JMenu();
			this.File.setText("File");
			this.File.add(getNew());
			this.File.add(getOpen());
			this.File.addSeparator();
			this.File.add(getSave());
			this.File.add(getSaveAs());
			this.File.addSeparator();
			this.File.add(getExit());


		}
		return this.File;
	}

	/**
	 * This method initializes Properties	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getProperties() {
		if (this.Properties == null) {
			this.Properties = new JMenuItem();
			this.Properties.setText("Properties");
			this.Properties.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_T, ActionEvent.CTRL_MASK));
			this.Properties.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CW_Properties Options = new CW_Properties(CW_Worldbuilder.this.thisClass);
					Options.setVisible(true);
				}//properties
			});
		}
		return this.Properties;
	}

	/**
	 * This method initializes New	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNew() {
		if (this.New == null) {
			this.New = new JMenuItem();
			this.New.setText("New");
			this.New.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_N, ActionEvent.CTRL_MASK));
			this.New.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!isSaved){
						int n = JOptionPane.showOptionDialog(
								CW_Worldbuilder.this.thisClass,
								"OMFG SAVE",
								"Save?",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null);
						System.out.println(n);
						switch (n){
						case 0:
							C_FileIO.SaveFile(CurrentLevel.Levellist, CW_Worldbuilder.this);
							break;

						case 1:
							isSaved=true;
							CurrentLevel = new CW_Level();
							Editor.repaint();
							break;
						}
					}else{
						isSaved=true;
						CurrentLevel = new CW_Level();
						Editor.repaint();
						CW_Worldbuilder.Editor.ChangeSize(800,600);	
					}
				}
			});
		}
		return this.New;
	}

	/**
	 * This method initializes Exit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExit() {
		if (this.Exit == null) {
			this.Exit = new JMenuItem();
			this.Exit.setText("Exit");
			this.Exit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(getDefaultCloseOperation());
				}
			});
		}
		return this.Exit;
	}

	/**
	 * This method initializes Edit	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEdit() {
		if (this.Edit == null) {
			this.Edit = new JMenu();
			this.Edit.setText("Edit");
			Edit.add(getUndo());
			this.Edit.add(getProperties());
			this.Edit.addSeparator();
			this.Edit.add(getGrid());
			this.Edit.add(getSnaptogrid());
		}
		return this.Edit;
	}

	/**
	 * This method initializes Grid	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getGrid() {
		if (this.Grid == null) {
			this.Grid = new JCheckBoxMenuItem();
			this.Grid.setText("Grid");
			this.Grid.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_G, ActionEvent.CTRL_MASK));
			this.Grid.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					if (grid)
						grid=false;
					else
						grid=true;
					CW_Worldbuilder.this.thisClass.repaint();
				}
			});
		}
		return this.Grid;
	}

	/**
	 * This method initializes Snaptogrid	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getSnaptogrid() {
		if (this.Snaptogrid == null) {
			this.Snaptogrid = new JCheckBoxMenuItem();
			this.Snaptogrid.setText("Snap to grid");
			this.Snaptogrid.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK));
			this.Snaptogrid.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (CW_Worldbuilder.this.SnaptoGrid)
						CW_Worldbuilder.this.SnaptoGrid=false;
					else
						CW_Worldbuilder.this.SnaptoGrid=true;
				}
			});
		}
		return this.Snaptogrid;
	}

	/**
	 * This method initializes Editor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private WC_Editor getEditor() {
		if (Editor == null) {
			Editor = new WC_Editor();
			Editor.setLayout(new GridBagLayout());
			Editor.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseExited(java.awt.event.MouseEvent e) {    
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					Editor.Click(e.getButton());
				}

			});
			Editor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseMoved(java.awt.event.MouseEvent e) {
					if (CW_Worldbuilder.this.SnaptoGrid){
						Editor.mouse_x = e.getX()/gridx * gridx;
						Editor.mouse_y = e.getY()/gridy * gridy;	
					}else{
						Editor.mouse_x = e.getX();
						Editor.mouse_y = e.getY();
					}
					Editor.repaint();
				}
				public void mouseDragged(java.awt.event.MouseEvent e) {
					if (CW_Worldbuilder.this.SnaptoGrid){
						Editor.mouse_x = e.getX()/gridx * gridx;
						Editor.mouse_y = e.getY()/gridy * gridy;	
					}else{
						Editor.mouse_x = e.getX();
						Editor.mouse_y = e.getY();
					}
					Editor.repaint();
				}
			});
		}
		return Editor;
	}

	/**
	 * This method initializes ObjectList	
	 * 	
	 * @return javax.swing.JTree	
	 */
	/**
	 * @param args

	 */


	private JMenuItem Save = null;

	private JMenuItem SaveAs = null;

	private JMenuItem Open = null;

	private JMenuItem Undo = null;

	/**
	 * This method initializes Save	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSave() {
		if (this.Save == null) {
			this.Save = new JMenuItem();
			this.Save.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			this.Save.setText("Save");
			this.Save.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isSaved=true;
					C_FileIO.SaveFile(CurrentLevel.Levellist, CW_Worldbuilder.this);
				}
			});
		}
		return this.Save;
	}

	/**
	 * This method initializes SaveAs	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveAs() {
		if (this.SaveAs == null) {
			this.SaveAs = new JMenuItem();
			this.SaveAs.setText("Save As...");
			this.SaveAs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isSaved=true;
					C_FileIO.SaveFileAs(CurrentLevel.Levellist, CW_Worldbuilder.this);
				}
			});
		}
		return this.SaveAs;
	}

	/**
	 * This method initializes Open	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOpen() {
		if (this.Open == null) {
			this.Open = new JMenuItem();
			this.Open.setText("Open Level");
			this.Open.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			this.Open.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					C_FileIO.OpenFile(CurrentLevel.Levellist, "", CW_Worldbuilder.this);
				}
			});
		}
		return this.Open;
	}

	/**
	 * This method initializes Undo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getUndo() {
		if (Undo == null) {
			Undo = new JMenuItem();
			Undo.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
			Undo.setText("Delete Last Object");
			Undo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					undo();
				}
			});
		}
		return Undo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				CW_Worldbuilder thisClass = new CW_Worldbuilder();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
				CurrentLevel = new CW_Level();
			}

			public void paintComponent (Graphics g){


			}

		});
	}
	public static Iterator getLevel(){
		return CurrentLevel.Levellist.iterator();
	}
	/**
	 * This is the default constructor
	 */
	public CW_Worldbuilder() {
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
		this.setContentPane(getJSplitPane());
		this.setJMenuBar(getMain_Menu());
		this.setTitle("World Builder");
	}

	public void undo(){
		try {
			C_Entity last = (C_Entity)( CurrentLevel.Levellist).get(CurrentLevel.Levellist.size()-1);
			last.isDead=true;
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
		}
		this.repaint();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
