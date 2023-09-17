package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Cursor;

public class PanelRegistro extends PanelAgencia 
{
    private JPanel panel_11;
    private JPanel panelEnComun;
    private JPanel panel_1;
    private JLabel lblNewLabel;
    private JPanel panel_2;
    private JTextField textFieldNombreUsuario;
    private JPanel panel_3;
    private JLabel lblNewLabel_1;
    private JPanel panel_4;
    private JTextField textFieldPassword;
    private JPanel panel_5;
    private JLabel lblNewLabel_2;
    private JPanel panel_6;
    private JTextField textFieldConfirmPassword;
    private JPanel panel_9;
    private JRadioButton rdbtnEmpleado;
    private JPanel panel_10;
    private JRadioButton rdbtnEmpleador;
    private JPanel panel_12;
    private JPanel panelEmpleado;
    private JPanel panelEmpleador;
    private JPanel panel_15;
    private JLabel lblEdad;
    private JPanel panel_16;
    private JTextField textFieldEdad;
    private JPanel panel_19;
    private JLabel lblApellido;
    private JPanel panel_20;
    private JTextField textFieldApellido;
    private JPanel panelRubro;
    private JPanel panel_17;
    private JLabel lblNombre;
    private JPanel panel_18;
    private JTextField textFieldNombreReal;
    private JPanel panel_21;
    private JLabel lblTelefono;
    private JPanel panel_22;
    private JTextField textFieldTelefono;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rdbtnSalud;
    private JRadioButton rdbtnComercioLocal;
    private JRadioButton rdbtnComercioInternacional;
    private JPanel panelTipo;
    private JRadioButton rdbtnFisica;
    private JRadioButton rdbtnJuridica;
    private final ButtonGroup buttonGroup_1 = new ButtonGroup();
    private final ButtonGroup buttonGroup_2 = new ButtonGroup();
    private JPanel panel_25;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    /**
     * Create the panel.
     */
    public PanelRegistro(ActionListener actionListener)
    {
	super(actionListener);
	setLayout(new BorderLayout(0, 0));

	this.panel_11 = new JPanel();
	add(this.panel_11, BorderLayout.CENTER);
	this.panel_11.setLayout(new GridLayout(0, 2, 0, 0));

	this.panelEnComun = new JPanel();
	this.panel_11.add(this.panelEnComun);
	this.panelEnComun.setLayout(new GridLayout(0, 2, 0, 0));

	this.panel_1 = new JPanel();
	this.panelEnComun.add(this.panel_1);

	this.lblNewLabel = new JLabel("Nombre de Usuario");
	this.panel_1.add(this.lblNewLabel);

	this.panel_2 = new JPanel();
	this.panelEnComun.add(this.panel_2);

	this.textFieldNombreUsuario = new JTextField();

	this.textFieldNombreUsuario.setColumns(20);
	this.panel_2.add(this.textFieldNombreUsuario);

	this.panel_3 = new JPanel();
	this.panelEnComun.add(this.panel_3);

	this.lblNewLabel_1 = new JLabel("Contrase\u00F1a");
	this.panel_3.add(this.lblNewLabel_1);

	this.panel_4 = new JPanel();
	this.panelEnComun.add(this.panel_4);

	this.textFieldPassword = new JPasswordField();

	this.textFieldPassword.setColumns(20);
	this.panel_4.add(this.textFieldPassword);

	this.panel_5 = new JPanel();
	this.panelEnComun.add(this.panel_5);

	this.lblNewLabel_2 = new JLabel("Repetir Contrase\u00F1a");
	this.panel_5.add(this.lblNewLabel_2);

	this.panel_6 = new JPanel();
	this.panelEnComun.add(this.panel_6);

	this.textFieldConfirmPassword = new JPasswordField();

	this.textFieldConfirmPassword.setColumns(20);
	this.panel_6.add(this.textFieldConfirmPassword);

	this.panel_17 = new JPanel();
	this.panelEnComun.add(this.panel_17);

	this.lblNombre = new JLabel("Nombre Real:");
	this.panel_17.add(this.lblNombre);

	this.panel_18 = new JPanel();
	this.panelEnComun.add(this.panel_18);

	this.textFieldNombreReal = new JTextField();

	this.textFieldNombreReal.setColumns(20);
	this.panel_18.add(this.textFieldNombreReal);

	this.panel_21 = new JPanel();
	this.panelEnComun.add(this.panel_21);

	this.lblTelefono = new JLabel("Telefono:");
	this.panel_21.add(this.lblTelefono);

	this.panel_22 = new JPanel();
	this.panelEnComun.add(this.panel_22);

	this.textFieldTelefono = new JTextField();

	this.textFieldTelefono.setColumns(20);
	this.panel_22.add(this.textFieldTelefono);

	this.panel_9 = new JPanel();
	this.panelEnComun.add(this.panel_9);

	this.rdbtnEmpleado = new JRadioButton("Empleado");
	this.rdbtnEmpleado.addActionListener(this);
	buttonGroup.add(this.rdbtnEmpleado);
	this.rdbtnEmpleado.setSelected(true);
	this.panel_9.add(this.rdbtnEmpleado);

	this.panel_10 = new JPanel();
	this.panelEnComun.add(this.panel_10);

	this.rdbtnEmpleador = new JRadioButton("Empleador");
	this.rdbtnEmpleador.addActionListener(this);
	buttonGroup.add(this.rdbtnEmpleador);
	this.panel_10.add(this.rdbtnEmpleador);

	this.panel_12 = new JPanel();
	this.panel_11.add(this.panel_12);
	this.panel_12.setLayout(new GridLayout(2, 0, 0, 0));

	this.panelEmpleado = new JPanel();
	this.panelEmpleado.setBorder(
		new TitledBorder(null, "Datos de Empleado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	this.panel_12.add(this.panelEmpleado);
	this.panelEmpleado.setLayout(new GridLayout(0, 2, 0, 0));

	this.panel_19 = new JPanel();
	this.panelEmpleado.add(this.panel_19);

	this.lblApellido = new JLabel("Apellido:");
	this.panel_19.add(this.lblApellido);

	this.panel_20 = new JPanel();
	this.panelEmpleado.add(this.panel_20);

	this.textFieldApellido = new JTextField();

	this.textFieldApellido.setColumns(20);
	this.panel_20.add(this.textFieldApellido);

	this.panel_15 = new JPanel();
	this.panelEmpleado.add(this.panel_15);

	this.lblEdad = new JLabel("Edad:");
	this.panel_15.add(this.lblEdad);

	this.panel_16 = new JPanel();
	this.panelEmpleado.add(this.panel_16);

	this.textFieldEdad = new JTextField();

	this.textFieldEdad.setColumns(20);
	this.panel_16.add(this.textFieldEdad);

	this.panelEmpleador = new JPanel();
	this.panelEmpleador.setEnabled(false);
	this.panelEmpleador.setBorder(new TitledBorder(
		new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
		"Datos de Empleador", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	this.panel_12.add(this.panelEmpleador);
	this.panelEmpleador.setLayout(new GridLayout(0, 2, 0, 0));

	this.panelRubro = new JPanel();
	this.panelRubro.setEnabled(false);
	this.panelRubro.setBorder(new TitledBorder(null, "Rubro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	this.panelEmpleador.add(this.panelRubro);
	this.panelRubro.setLayout(new GridLayout(0, 1, 0, 0));

	this.rdbtnSalud = new JRadioButton("Salud");
	this.rdbtnSalud.setEnabled(false);
	buttonGroup_1.add(this.rdbtnSalud);
	this.rdbtnSalud.setSelected(true);
	this.panelRubro.add(this.rdbtnSalud);

	this.rdbtnComercioInternacional = new JRadioButton("Comercio Internacional");
	this.rdbtnComercioInternacional.setEnabled(false);
	buttonGroup_1.add(this.rdbtnComercioInternacional);
	this.panelRubro.add(this.rdbtnComercioInternacional);

	this.rdbtnComercioLocal = new JRadioButton("Comercio Local");
	this.rdbtnComercioLocal.setEnabled(false);
	buttonGroup_1.add(this.rdbtnComercioLocal);
	this.panelRubro.add(this.rdbtnComercioLocal);

	this.panelTipo = new JPanel();
	this.panelTipo.setEnabled(false);
	this.panelTipo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	this.panelTipo.setBorder(new TitledBorder(
		new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
		"Tipo de Persona", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	this.panelEmpleador.add(this.panelTipo);
	this.panelTipo.setLayout(new GridLayout(0, 1, 0, 0));

	this.rdbtnFisica = new JRadioButton("Fisica");
	this.rdbtnFisica.setEnabled(false);
	buttonGroup_2.add(this.rdbtnFisica);
	this.rdbtnFisica.setSelected(true);
	this.panelTipo.add(this.rdbtnFisica);

	this.rdbtnJuridica = new JRadioButton("Juridica");
	this.rdbtnJuridica.setEnabled(false);
	buttonGroup_2.add(this.rdbtnJuridica);
	this.panelTipo.add(this.rdbtnJuridica);

	this.panel_25 = new JPanel();
	add(this.panel_25, BorderLayout.SOUTH);

	this.btnRegistrar = new JButton("Registrar");
	this.btnRegistrar.setEnabled(false);
	this.panel_25.add(this.btnRegistrar);
	this.textFieldTelefono.addKeyListener(this);
	this.textFieldNombreReal.addKeyListener(this);
	this.textFieldApellido.addKeyListener(this);
	this.textFieldConfirmPassword.addKeyListener(this);
	this.textFieldEdad.addKeyListener(this);
	this.textFieldNombreUsuario.addKeyListener(this);
	this.textFieldPassword.addKeyListener(this);

	this.btnCancelar = new JButton("Cancelar");

	this.panel_25.add(this.btnCancelar);

	this.textFieldApellido.setName(IVista.REG_APELLIDO);
	this.textFieldTelefono.setName(IVista.REG_TELEFONO);
	this.textFieldNombreReal.setName(IVista.REG_REAL_NAME);
	this.textFieldConfirmPassword.setName(IVista.REG_CONFIRM_PASSWORD);
	this.textFieldPassword.setName(IVista.REG_PASSWORD);
	this.textFieldNombreUsuario.setName(IVista.REG_USSER_NAME);
	this.textFieldEdad.setName(IVista.REG_EDAD);
	
	this.setActionAndName(this.btnRegistrar, IVista.REG_BUTTON_REGISTRAR);
	this.setActionAndName(this.btnCancelar, IVista.REG_BUTTON_CANCELAR);
	
	this.setActionAndName(this.rdbtnComercioInternacional, IVista.REG_RADIO_COMERCIO_INTERNACIONAL);
	this.setActionAndName(this.rdbtnComercioLocal, IVista.REG_RADIO_COMERCIO_LOCAL);
	this.setActionAndName(this.rdbtnSalud, IVista.REG_RADIO_SALUD);
	
	
	this.setActionAndName(this.rdbtnFisica, IVista.REG_RADIO_FISICA);
	this.setActionAndName(this.rdbtnJuridica, IVista.REG_RADIO_JURIDICA);
	
	
	this.addActionListener(actionListener);

    }

    public void keyPressed(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    	this.habilitaContinuar();
    }

    private void habilitaContinuar()
    {
	boolean condicionFinal = false;
	boolean condicion1 = !this.textFieldNombreUsuario.getText().isEmpty()
		&& !this.textFieldConfirmPassword.getText().isEmpty() && !this.textFieldPassword.getText().isEmpty()
		&& !this.textFieldNombreReal.getText().isEmpty()
		&& !this.textFieldTelefono.getText().isEmpty();

	if (this.rdbtnEmpleado.isSelected())
	{
	    int edad = -1;
	    try
	    {
		edad = Integer.parseInt(this.textFieldEdad.getText());
	    } catch (NumberFormatException e)
	    {
	    }
	    condicionFinal = condicion1 && edad > 0 && !this.textFieldApellido.getText().isEmpty();

	} else
	    condicionFinal = condicion1;

	this.btnRegistrar.setEnabled(condicionFinal);
    }

    public void keyTyped(KeyEvent e)
    {

    }

    protected void do_textFieldTelefono_keyTyped(KeyEvent e)
    {
    }

    private void modoEmpleado(boolean condicion)
    {
	this.lblApellido.setEnabled(condicion);
	this.lblEdad.setEnabled(condicion);
	this.textFieldApellido.setEnabled(condicion);
	this.textFieldEdad.setEnabled(condicion);
	this.panelEmpleado.setEnabled(condicion);
	this.panelEmpleador.setEnabled(!condicion);

	this.rdbtnComercioInternacional.setEnabled(!condicion);
	this.rdbtnComercioLocal.setEnabled(!condicion);
	this.rdbtnSalud.setEnabled(!condicion);
	this.rdbtnFisica.setEnabled(!condicion);
	this.rdbtnJuridica.setEnabled(!condicion);
	this.habilitaContinuar();

    }

    

    public void addActionListener(ActionListener actionListener)
    {
	this.btnCancelar.addActionListener(actionListener);
	this.btnRegistrar.addActionListener(actionListener);
	this.rdbtnEmpleado.addActionListener(actionListener);
	this.rdbtnEmpleador.addActionListener(actionListener);
    }

    public String getTipoUsuario()
    {
	String respuesta = null;
	if (this.rdbtnEmpleado.isSelected())
	    respuesta = IVista.EMPLEADO;
	else
	    respuesta = IVista.EMPLEADOR;

	return respuesta;
    }

    public String getTipoPersona()
    {
	String respuesta = null;
	if (this.rdbtnFisica.isSelected())
	    respuesta = IVista.FISICA;
	else
	    respuesta = IVista.JURIDICA;

	return respuesta;
    }

    public String getRubro()
    {

	String respuesta = null;
	if (this.rdbtnSalud.isSelected())
	    respuesta = IVista.SALUD;
	else if (this.rdbtnComercioInternacional.isSelected())
	    respuesta = IVista.COMERCIO_INTERNACIONAL;
	else
	    respuesta = IVista.COMERCIO_LOCAL;
	return respuesta;
    }

    public String getNombreReal()
    {
	// TODO Auto-generated method stub
	return this.textFieldNombreReal.getText();
    }

    public String getApellido()
    {
	// TODO Auto-generated method stub
	return this.textFieldApellido.getText();
    }

    public String getTelefono()
    {
	// TODO Auto-generated method stub
	return this.textFieldTelefono.getText();
    }

    public String getConfirmPassword()
    {
	// TODO Auto-generated method stub
	return this.textFieldConfirmPassword.getText();
    }

    public int getEdad()
    {
	// TODO Auto-generated method stub
	return Integer.parseInt(this.textFieldEdad.getText());
    }

   

    public String getRegPassword()
    {
	// TODO Auto-generated method stub
	return this.textFieldPassword.getText();
    }

    public String getRegUsserName()
    {
	// TODO Auto-generated method stub
	return this.textFieldNombreUsuario.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	this.modoEmpleado(this.rdbtnEmpleado.isSelected());
    }

}
