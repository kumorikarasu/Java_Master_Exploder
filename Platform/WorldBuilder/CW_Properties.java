
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CW_Properties extends JDialog {

	private static final long serialVersionUID = 1L;
	private final CW_Properties thisClass = this;
	static CW_Worldbuilder Parent;
	static int gridx=2,gridy=2,width=4,height=4;
	private JPanel jPanel = null;
	private JTextField GridY = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField GridX = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel111 = null;
	private JLabel mapwidth_text = null;
	private JTextField mapheight = null;
	private JTextField mapwidth = null;
	/**
	 * This is the default constructor
	 */
	public CW_Properties() {
		super();
		initialize();
	}
	
	public CW_Properties(CW_Worldbuilder _parent) {
		super();
		initialize();
		Parent = _parent;
		gridx=(short) CW_Worldbuilder.gridx;
		gridy=(short) CW_Worldbuilder.gridy;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 500);
		this.setContentPane(getJPanel());
		this.setTitle("Properties");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (this.jPanel == null) {
			this.mapwidth_text = new JLabel();
			this.mapwidth_text.setBounds(new Rectangle(120, 258, 66, 14));
			this.mapwidth_text.setText("Map Width:");
			this.jLabel111 = new JLabel();
			this.jLabel111.setBounds(new Rectangle(119, 283, 71, 14));
			this.jLabel111.setText("Map Height:");
			this.jLabel11 = new JLabel();
			this.jLabel11.setBounds(new Rectangle(146, 316, 45, 17));
			this.jLabel11.setText("Grid X:");
			this.jLabel1 = new JLabel();
			this.jLabel1.setBounds(new Rectangle(147, 342, 45, 17));
			this.jLabel1.setText("Grid Y:");
			this.jPanel = new JPanel();
			this.jPanel.setLayout(null);
			this.jPanel.add(getGridY(), null);
			this.jPanel.add(getJTextField1(), null);
			this.jPanel.add(getJButton(), null);
			this.jPanel.add(getJButton1(), null);
			this.jPanel.add(this.jLabel1, null);
			this.jPanel.add(this.jLabel11, null);
			this.jPanel.add(this.jLabel111, null);
			this.jPanel.add(this.mapwidth_text, null);
			this.jPanel.add(getMapheight(), null);
			this.jPanel.add(getMapWidth(), null);
		}
		return this.jPanel;
	}

	/**
	 * This method initializes GridY	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getGridY() {
		if (this.GridY == null) {
			this.GridY = new JTextField(""+CW_Worldbuilder.gridy);
			this.GridY.setBounds(new Rectangle(200, 340, 70, 20));
		}
		return this.GridY;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (this.jButton == null) {
			this.jButton = new JButton();
			this.jButton.setBounds(new Rectangle(119, 424, 75, 30));
			this.jButton.setText("Ok");
			this.jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gridx = Integer.valueOf( CW_Properties.this.GridX.getText() ).intValue();
					gridy = Integer.valueOf( CW_Properties.this.GridY.getText() +"").intValue();
					width = Integer.valueOf( CW_Properties.this.mapwidth.getText() ).intValue();
					height = Integer.valueOf( CW_Properties.this.mapheight.getText() +"").intValue();
					if (gridx!=CW_Worldbuilder.gridx)
						CW_Worldbuilder.gridx=gridx;
					if (gridy!=CW_Worldbuilder.gridy)
						CW_Worldbuilder.gridy=gridy;
					if (width!=CW_Worldbuilder.width)
						CW_Worldbuilder.width=width;
					if (height!=CW_Worldbuilder.height)
						CW_Worldbuilder.height=height;
					if (CW_Worldbuilder.grid){
						CW_Worldbuilder.grid=false;
						CW_Worldbuilder.grid=true;
					}
					CW_Worldbuilder.Editor.ChangeSize(width,height);
					CW_Properties.this.thisClass.dispose();
				}
			});
		}
		return this.jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (this.jButton1 == null) {
			this.jButton1 = new JButton();
			this.jButton1.setBounds(new Rectangle(199, 424, 75, 30));
			this.jButton1.setText("Cancel");
			this.jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (CW_Worldbuilder.grid){
						CW_Worldbuilder.grid=false;
						CW_Worldbuilder.grid=true;
					}
					CW_Properties.this.thisClass.dispose();
				}
			});
		}
		return this.jButton1;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (this.GridX == null) {
			this.GridX = new JTextField(""+CW_Worldbuilder.gridx);
			this.GridX.setBounds(new Rectangle(200, 314, 70, 20));
		}
		return this.GridX;
	}

	/**
	 * This method initializes mapheight	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMapheight() {
		if (this.mapheight == null) {
			this.mapheight = new JTextField(""+CW_Worldbuilder.height);
			this.mapheight.setBounds(new Rectangle(200, 282, 70, 20));
		}
		return this.mapheight;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMapWidth() {
		if (this.mapwidth == null) {
			this.mapwidth = new JTextField(""+CW_Worldbuilder.width);
			this.mapwidth.setBounds(new Rectangle(200, 257, 70, 20));
		}
		return this.mapwidth;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */

}  //  @jve:decl-index=0:visual-constraint="74,25"
