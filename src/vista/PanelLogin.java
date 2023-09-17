package vista;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PanelLogin extends JPanel implements KeyListener
{
	
	private JPanel panel_6;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JTextField textFieldUsser;
	private JPanel panel_2;
	private JLabel lblNewLabel_1;
	private JPanel panel_3;
	private JTextField textFieldPassword;
	private JPanel panel_4;
	private JPanel panel_5;
	private JButton btnLogin;
	private JPanel panel_7;
	private JButton btnRegistro;

	/**
	 * Create the panel.
	 */
	public PanelLogin(ActionListener actionListener)
	{
		setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_6 = new JPanel();
		this.panel_6.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(this.panel_6);
		this.panel_6.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel = new JPanel();
		this.panel_6.add(this.panel);

		this.lblNewLabel = new JLabel("Nombre de Usuario");
		this.panel.add(this.lblNewLabel);

		this.panel_1 = new JPanel();
		this.panel_6.add(this.panel_1);

		this.textFieldUsser = new JTextField();
		this.textFieldUsser.addKeyListener(this);
		
		this.textFieldUsser.setColumns(20);
		this.panel_1.add(this.textFieldUsser);

		this.panel_2 = new JPanel();
		this.panel_6.add(this.panel_2);

		this.lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		this.panel_2.add(this.lblNewLabel_1);

		this.panel_3 = new JPanel();
		this.panel_6.add(this.panel_3);

		this.textFieldPassword = new JPasswordField();
		this.textFieldPassword.addKeyListener(this);
		
		this.textFieldPassword.setColumns(20);
		this.panel_3.add(this.textFieldPassword);

		this.panel_4 = new JPanel();
		this.panel_6.add(this.panel_4);

		this.panel_5 = new JPanel();
		this.panel_6.add(this.panel_5);

		this.btnLogin = new JButton("Login");
		this.btnLogin.setEnabled(false);
		this.panel_5.add(this.btnLogin);

		this.panel_7 = new JPanel();
		this.panel_7
				.setBorder(new TitledBorder(null, "Nuevo Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(this.panel_7);

		this.btnRegistro = new JButton("Registrar");
		this.panel_7.add(this.btnRegistro);
	
		this.addActionListener(actionListener);
		this.textFieldUsser.setName(IVista.NOMBRE_USUARIO);
		this.textFieldPassword.setName(IVista.PASSWORD);
		this.btnLogin.setName(IVista.LOGIN);
		this.btnLogin.setActionCommand(IVista.LOGIN);
		this.btnRegistro.setName(IVista.REGISTRAR);
		this.btnRegistro.setActionCommand(IVista.REGISTRAR);
		
		
	}

	public void keyPressed(KeyEvent e)
	{

	}

	public void keyReleased(KeyEvent e)
	{
		this.verificaBoton();

	}

	public void keyTyped(KeyEvent e)
	{
	}

	private void verificaBoton()
	{
		boolean condicion = !this.textFieldPassword.getText().isEmpty() && !this.textFieldUsser.getText().isEmpty();
		this.btnLogin.setEnabled(condicion);
	}
	public String getPassword()
	{
		// TODO Auto-generated method stub
		return this.textFieldPassword.getText();
	}


	
	public String getUsserName()
	{
		// TODO Auto-generated method stub
		return this.textFieldUsser.getText();
	}
	
	public void addActionListener(ActionListener actionListener) 
	{
		this.btnLogin.addActionListener(actionListener);
		this.btnRegistro.addActionListener(actionListener);
	}

}
