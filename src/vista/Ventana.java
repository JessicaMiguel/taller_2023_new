package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modeloDatos.Usuario;
import modeloNegocio.Agencia;
import util.Constantes;


/**
 *  Clase que representa la Ventana en la aplicaicion. En cada sesion, las ventanas aparecen limpias.
 * @author
 *
 */

public class Ventana extends JFrame implements IVista, ActionListener,WindowListener
{

	private JPanel contentPane;
	private PanelLogin panelLogin;
	private PanelRegistro panelRegistro;
	private PanelAdmin panelAdmin;
	private ActionListener actionListener;
	private JPanel panelActual;
	private PanelCliente panelCliente;

	/**
	 * Create the frame.
	 */
	public Ventana(ActionListener actionListener)
	{
		this.actionListener = actionListener;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		this.panelAdmin = new PanelAdmin(actionListener);
		this.panelLogin = new PanelLogin(actionListener);
		this.panelRegistro = new PanelRegistro(actionListener);

		this.panelRegistro.addActionListener(this);
		this.panelLogin.addActionListener(this);
		this.panelAdmin.addActionListener(this); 
		

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.panelActual = this.panelLogin;
		this.contentPane.add(this.panelLogin, BorderLayout.CENTER);
		this.addWindowListener(this);
		this.setVisible(true);
	}

	private void goToLogin()
	{
		this.contentPane.remove(panelActual);
		this.contentPane.add(this.panelLogin, BorderLayout.CENTER);
		this.panelActual = this.panelLogin;

		this.validate();
		this.repaint();
	}

	private void goToRegistro()
	{
		this.contentPane.remove(panelActual);
		this.contentPane.add(this.panelRegistro, BorderLayout.CENTER);
		this.panelActual = this.panelRegistro;

		this.validate();
		this.repaint();
	}

	private void goToCliente(int tipoUsuario, Usuario usuario)
	{
		this.contentPane.remove(panelActual);
		this.panelCliente = new PanelCliente(usuario, actionListener,tipoUsuario,usuario);
		panelCliente.addActionListener(this);
		this.contentPane.add(panelCliente, BorderLayout.CENTER);
		this.panelActual = panelCliente;

		this.validate();
		this.repaint();
	}

	@Override
	public String getPassword()
	{
		// TODO Auto-generated method stub
		return this.panelLogin.getPassword();
	}

	@Override
	public String getUsserName()
	{
		// TODO Auto-generated method stub
		return this.panelLogin.getUsserName();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String comando = e.getActionCommand();
		if (comando.equalsIgnoreCase(Constantes.REGISTRAR))
			this.goToRegistro();
		else if (comando.equalsIgnoreCase(Constantes.REG_BUTTON_CANCELAR) || comando.equalsIgnoreCase(Constantes.CERRARSESION))
			this.goToLogin();
		
		
	}

	@Override
	public void actualizar(int tipoUsuario, Usuario usuario)
	{
		this.setTitle(Agencia.getInstance().getEstado());
	    if (tipoUsuario != 2)
			this.goToCliente(tipoUsuario, usuario);
		else
		    this.goToAdmin(usuario);
		
	}

	private void goToAdmin(Usuario usuario)
	{
	    this.contentPane.remove(panelActual);
		this.panelAdmin.actualizarListas();
		this.contentPane.add(this.panelAdmin, BorderLayout.CENTER);
		this.panelActual = this.panelAdmin;

		this.validate();
		this.repaint(); 
	}

	@Override
	public String getTipoUsuario()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getTipoUsuario();
	}

	@Override
	public String getTipoPersona()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getTipoPersona();
	}

	@Override
	public String getRubro()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getRubro();
	}

	@Override
	public String getNombreReal()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getNombreReal();
	}

	@Override
	public String getApellido()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getApellido();
	}

	@Override
	public String getTelefono()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getTelefono();
	}

	@Override
	public String getConfirmPassword()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getConfirmPassword();
	}

	

	@Override
	public int getEdad()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getEdad();
	}

	@Override
	public String getRegPassword()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getRegPassword();
	}

	@Override
	public String getRegUsserName()
	{
		// TODO Auto-generated method stub
		return this.panelRegistro.getRegUsserName();
	}

	@Override
	public String getLocacion()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getLocacion();
	}

	@Override
	public String getPuesto()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getPuesto();
	}

	@Override
	public String getExperiencia()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getExperiencia();
	}

	@Override
	public String getEstudios()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getEstudios();
	}

	@Override
	public String getJornada()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getJornada();
	}

	@Override
	public int getRemuneracion()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getRemunearcion();
	}

	@Override
	public void actualizaCliente()
	{
	    this.panelCliente.actualizaCliente();
	}

	@Override
	public int getLimiteInferior()
	{
	    // TODO Auto-generated method stub
	    return this.panelAdmin.getLimiteInferior();
	}

	@Override
	public int getLimiteSuperior()
	{
	    // TODO Auto-generated method stub
	    return this.panelAdmin.getLimiteSuperior();
	}

	@Override
	public boolean isPorTicket()
	{
		// TODO Auto-generated method stub
		return this.panelAdmin.isPorTicket();
	}

	@Override
	public Usuario getCandidato()
	{
	    // TODO Auto-generated method stub
	    return this.panelCliente.getCandidato();
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{ActionEvent ev=new ActionEvent(this,0, Constantes.CERRARSESION); 
	this.actionListener.actionPerformed(ev);
	
		}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
