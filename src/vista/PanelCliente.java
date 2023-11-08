package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.ClientePuntaje;
import modeloDatos.Cliente;
import modeloNegocio.Agencia;
import util.Constantes;
import util.Mensajes;

public class PanelCliente extends PanelAgencia
{
	private Cliente cliente;
	private JPanel panelCentral;
	private JPanel panelSur;
	private JButton btnCerrarSesion;

	private JPanel panelTicket;
	private JList<ClientePuntaje> list;
	private DefaultListModel<ClientePuntaje> modeloLista = new DefaultListModel<ClientePuntaje>();
	private JScrollPane scrollPane_1;
	private JTextArea textAreaResultado;
	private JPanel panel_Resultado;
	private JPanel panelCandidatos;
	private JScrollPane scrollPane;

	private JPanel panel_3;
	private JButton btnSeleccionarCandidato;
	private JPanel panel_4;
	private JScrollPane scrollPane_2;
	private JTextArea textAreaTicket;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JButton btnNuevoTicket;
	private JButton btnAceptar;
	private JPanel panel_8;
	private JRadioButton rdbtnPresencial;
	private JRadioButton rdbtnHomeOffice;
	private JRadioButton rdbtnIndistinto;
	private JPanel panel_9;
	private JRadioButton rdbtnJunior;
	private JRadioButton rdbtnSenior;
	private JRadioButton rdbtnManagment;
	private JPanel panel_10;
	private JRadioButton rdbtnCargaMedia;
	private JRadioButton rdbtnCompleta;
	private JRadioButton rdbtnExtendida;
	private JPanel panel_11;
	private JRadioButton rdbtnNada;
	private JRadioButton rdbtnExperienciaMedia;
	private JRadioButton rdbtnMucha;
	private JPanel panel_12;
	private JRadioButton rdbtnPrimario;
	private JRadioButton rdbtnSecundario;
	private JRadioButton rdbtnTerciario;
	private JPanel panel_13;
	private JTextField textFieldRemuneracion;
	private JPanel panelMitad;
	private final ButtonGroup buttonGroupEstudios = new ButtonGroup();
	private final ButtonGroup buttonGroupExperiencia = new ButtonGroup();
	private final ButtonGroup buttonGroupJornada = new ButtonGroup();
	private final ButtonGroup buttonGroupPuesto = new ButtonGroup();
	private final ButtonGroup buttonGroupLocacion = new ButtonGroup();
	private JScrollPane scrollPane_3;
	private int remuneracion = 0;
	private int tipoUsuario;
	private Cliente usuario;
	private JPanel panel;
	private JButton btnEliminarTicket;

	/**
	 * Create the panel.
	 * 
	 * @param tipoUsuario
	 * @param usuario
	 */
	public PanelCliente(Cliente cliente, ActionListener actionListener, int tipoUsuario, Cliente usuario)
	{
		super(actionListener);
		this.usuario = usuario;
		this.tipoUsuario = tipoUsuario;
		String titulo = null;
		if (tipoUsuario == 0)
			titulo = "Empleado: " + cliente.getUsserName();
		else
			titulo = "Empleador o Empresa: " + cliente.getUsserName();
		setBorder(new TitledBorder(null, titulo, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.cliente = cliente;
		setLayout(new BorderLayout(0, 0));

		this.panelSur = new JPanel();
		add(this.panelSur, BorderLayout.SOUTH);

		this.btnCerrarSesion = new JButton("Cerrar Sesion");

		this.panelSur.add(this.btnCerrarSesion);

		this.panelCentral = new JPanel();
		add(this.panelCentral, BorderLayout.CENTER);
		this.panelCentral.setLayout(new GridLayout(0, 1, 0, 0));

		panelMitad = new JPanel();
		panelCentral.add(panelMitad);
		panelMitad.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel_Resultado = new JPanel();
		panel_Resultado.setPreferredSize(new Dimension(10, 150));
		this.panel_Resultado.setBorder(new TitledBorder(null, "Resultado de la busqueda:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		this.panel_Resultado.setLayout(new BorderLayout(0, 0));

		this.scrollPane_1 = new JScrollPane();
		this.panel_Resultado.add(this.scrollPane_1);

		this.textAreaResultado = new JTextArea();
		this.scrollPane_1.setViewportView(this.textAreaResultado);

		this.panelCandidatos = new JPanel();
		this.panelCandidatos.setBorder(
				new TitledBorder(null, "Lista de Candidatos", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.panelMitad.add(this.panel_Resultado);
		this.panelMitad.add(this.panelCandidatos);

		this.panelCandidatos.setLayout(new BorderLayout(0, 0));

		this.list = new JList<ClientePuntaje>();
		this.list.setBorder(null);

		this.list.setModel(modeloLista);

		this.scrollPane = new JScrollPane();
		this.panelCandidatos.add(this.scrollPane);

		this.scrollPane.setViewportView(this.list);

		this.panel_3 = new JPanel();
		this.panelCandidatos.add(this.panel_3, BorderLayout.SOUTH);

		this.btnSeleccionarCandidato = new JButton("Selecionar Candidato");
		this.panel_3.add(this.btnSeleccionarCandidato);

		this.panelTicket = new JPanel();
		this.panelTicket
				.setBorder(new TitledBorder(null, "Ticket:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panelCentral.add(this.panelTicket);
		this.panelTicket.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel_4 = new JPanel();
		this.panel_4.setBorder(new TitledBorder(null, "Actual:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panelTicket.add(this.panel_4);
		this.panel_4.setLayout(new BorderLayout(0, 0));

		this.scrollPane_2 = new JScrollPane();
		this.panel_4.add(this.scrollPane_2, BorderLayout.CENTER);

		this.textAreaTicket = new JTextArea();
		this.scrollPane_2.setViewportView(this.textAreaTicket);

		this.panel = new JPanel();
		this.panel_4.add(this.panel, BorderLayout.SOUTH);

		this.btnEliminarTicket = new JButton("Eliminar Ticket");
		this.panel.add(this.btnEliminarTicket);

		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Nuevo Ticket", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTicket.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		this.scrollPane_3 = new JScrollPane();
		this.panel_5.add(this.scrollPane_3, BorderLayout.CENTER);
		panel_6 = new JPanel();
		this.scrollPane_3.setViewportView(this.panel_6);

		// panel_5.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		panel_13 = new JPanel();
		panel_13.setEnabled(false);
		panel_13.setBorder(
				new TitledBorder(null, "Remuneracion Pretendida", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_13);

		textFieldRemuneracion = new JTextField();
		this.textFieldRemuneracion.addKeyListener(this);
		textFieldRemuneracion.setEnabled(false);
		panel_13.add(textFieldRemuneracion);
		textFieldRemuneracion.setColumns(20);
		textFieldRemuneracion.setName(Constantes.TEXTFIELD_REMUNERACION);
		
		
		panel_8 = new JPanel();
		panel_8.setEnabled(false);
		panel_8.setBorder(new TitledBorder(null, "Locacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnPresencial = new JRadioButton("Presencial");
		rdbtnPresencial.setEnabled(false);
		rdbtnPresencial.setSelected(true);
		buttonGroupLocacion.add(rdbtnPresencial);
		panel_8.add(rdbtnPresencial);

		rdbtnHomeOffice = new JRadioButton("HomeOffice");
		rdbtnHomeOffice.setEnabled(false);
		buttonGroupLocacion.add(rdbtnHomeOffice);
		panel_8.add(rdbtnHomeOffice);

		rdbtnIndistinto = new JRadioButton("Indistinto");
		rdbtnIndistinto.setEnabled(false);
		buttonGroupLocacion.add(rdbtnIndistinto);
		panel_8.add(rdbtnIndistinto);

		panel_9 = new JPanel();
		panel_9.setEnabled(false);
		panel_9.setBorder(new TitledBorder(null, "Tipo de Puesto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnJunior = new JRadioButton("Junior");
		rdbtnJunior.setEnabled(false);
		rdbtnJunior.setSelected(true);
		buttonGroupPuesto.add(rdbtnJunior);
		panel_9.add(rdbtnJunior);

		rdbtnSenior = new JRadioButton("Senior");
		rdbtnSenior.setEnabled(false);
		buttonGroupPuesto.add(rdbtnSenior);
		panel_9.add(rdbtnSenior);

		rdbtnManagment = new JRadioButton("Managment");
		rdbtnManagment.setEnabled(false);
		buttonGroupPuesto.add(rdbtnManagment);
		panel_9.add(rdbtnManagment);

		panel_10 = new JPanel();
		panel_10.setEnabled(false);
		panel_10.setBorder(new TitledBorder(null, "Carga Horaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnCargaMedia = new JRadioButton("Carga Media");
		rdbtnCargaMedia.setEnabled(false);
		rdbtnCargaMedia.setSelected(true);
		buttonGroupJornada.add(rdbtnCargaMedia);
		panel_10.add(rdbtnCargaMedia);

		rdbtnCompleta = new JRadioButton("Completa");
		rdbtnCompleta.setEnabled(false);
		buttonGroupJornada.add(rdbtnCompleta);
		panel_10.add(rdbtnCompleta);

		rdbtnExtendida = new JRadioButton("Extendida");
		rdbtnExtendida.setEnabled(false);
		buttonGroupJornada.add(rdbtnExtendida);
		panel_10.add(rdbtnExtendida);

		panel_11 = new JPanel();
		panel_11.setEnabled(false);
		panel_11.setBorder(
				new TitledBorder(null, "Experiencia Previa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_11);
		panel_11.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnNada = new JRadioButton("Nada");
		rdbtnNada.setEnabled(false);
		rdbtnNada.setSelected(true);
		buttonGroupExperiencia.add(rdbtnNada);
		panel_11.add(rdbtnNada);

		rdbtnExperienciaMedia = new JRadioButton("Media");
		rdbtnExperienciaMedia.setEnabled(false);
		buttonGroupExperiencia.add(rdbtnExperienciaMedia);
		panel_11.add(rdbtnExperienciaMedia);

		rdbtnMucha = new JRadioButton("Mucha");
		rdbtnMucha.setEnabled(false);
		buttonGroupExperiencia.add(rdbtnMucha);
		panel_11.add(rdbtnMucha);

		panel_12 = new JPanel();
		panel_12.setEnabled(false);
		panel_12.setBorder(
				new TitledBorder(null, "Estudios Cursados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_12);
		panel_12.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnPrimario = new JRadioButton("Primario");
		rdbtnPrimario.setEnabled(false);
		rdbtnPrimario.setSelected(true);
		buttonGroupEstudios.add(rdbtnPrimario);
		panel_12.add(rdbtnPrimario);

		rdbtnSecundario = new JRadioButton("Secundario");
		rdbtnSecundario.setEnabled(false);
		buttonGroupEstudios.add(rdbtnSecundario);
		panel_12.add(rdbtnSecundario);

		rdbtnTerciario = new JRadioButton("Terciario");
		rdbtnTerciario.setEnabled(false);
		buttonGroupEstudios.add(rdbtnTerciario);
		panel_12.add(rdbtnTerciario);

		

		panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.SOUTH);

		btnNuevoTicket = new JButton("Nuevo Ticket");

		panel_7.add(btnNuevoTicket);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		panel_7.add(btnAceptar);
		this.actualizaCliente();
		this.setActionAndName(this.btnCerrarSesion, Constantes.CERRARSESION);

		this.setActionAndName(this.btnSeleccionarCandidato, Constantes.SELECCIONAR_CANDIDATO);

		this.setActionAndName(this.btnAceptar, Constantes.CONFIRMARNUEVOTICKET);

		this.setActionAndName(this.btnNuevoTicket, Constantes.NUEVOTICKET);
		this.setActionAndName(this.btnEliminarTicket, Constantes.ELIMINAR_TICKET);

		this.setActionAndName(this.rdbtnCargaMedia, Constantes.JORNADA_MEDIA);
		this.setActionAndName(this.rdbtnCompleta, Constantes.JORNADA_COMPLETA);
		this.setActionAndName(this.rdbtnExtendida, Constantes.JORNADA_EXTENDIDA);

		this.setActionAndName(this.rdbtnNada, Constantes.EXP_NADA);
		this.setActionAndName(this.rdbtnExperienciaMedia, Constantes.EXP_MEDIA);
		this.setActionAndName(this.rdbtnMucha, Constantes.EXP_MUCHA);

		this.setActionAndName(this.rdbtnTerciario, Constantes.TERCIARIOS);
		this.setActionAndName(this.rdbtnSecundario, Constantes.SECUNDARIOS);
		this.setActionAndName(this.rdbtnPrimario, Constantes.PRIMARIOS);

		this.setActionAndName(this.rdbtnJunior, Constantes.JUNIOR);
		this.setActionAndName(this.rdbtnSenior, Constantes.SENIOR);
		this.setActionAndName(this.rdbtnManagment, Constantes.MANAGMENT);

		this.setActionAndName(this.rdbtnPresencial, Constantes.PRESENCIAL);
		this.setActionAndName(this.rdbtnHomeOffice, Constantes.HOME_OFFICE);
		this.setActionAndName(this.rdbtnIndistinto, Constantes.INDISTINTO);
		this.textAreaResultado.setName(Constantes.TEXT_AREA_RESULTADOS);
		this.textAreaTicket.setName(Constantes.TEXT_AREA_TICKET);
		this.list.setName(Constantes.LISTA_CANDIDATOS);
		this.addActionListener(actionListener);
		this.addActionListener(this);
		this.actualizaLista();

	}

	private void actualizaLista()
	{
		if (cliente.getListaDePostulantes() != null)
		{
			this.modeloLista.clear();
			Iterator<ClientePuntaje> it = cliente.getListaDePostulantes().iterator();
			while (it.hasNext())
				this.modeloLista.addElement(it.next());
			this.repaint();
		}
	}

	public void addActionListener(ActionListener actionListener)
	{
		this.btnCerrarSesion.addActionListener(actionListener);
		this.btnSeleccionarCandidato.addActionListener(actionListener);
		this.btnNuevoTicket.addActionListener(actionListener);
		this.btnAceptar.addActionListener(actionListener);
		this.btnEliminarTicket.addActionListener(actionListener);

	}

	private void enabledTicket(boolean condicion)
	{
		this.panel_8.setEnabled(condicion);
		this.panel_9.setEnabled(condicion);
		this.panel_10.setEnabled(condicion);
		this.panel_11.setEnabled(condicion);
		this.panel_12.setEnabled(condicion);
		this.panel_13.setEnabled(condicion);

		rdbtnPresencial.setEnabled(condicion);
		rdbtnHomeOffice.setEnabled(condicion);
		rdbtnIndistinto.setEnabled(condicion);

		rdbtnJunior.setEnabled(condicion);
		rdbtnSenior.setEnabled(condicion);
		rdbtnManagment.setEnabled(condicion);

		rdbtnCargaMedia.setEnabled(condicion);
		rdbtnCompleta.setEnabled(condicion);
		rdbtnExtendida.setEnabled(condicion);

		rdbtnNada.setEnabled(condicion);
		rdbtnExperienciaMedia.setEnabled(condicion);
		rdbtnMucha.setEnabled(condicion);

		rdbtnPrimario.setEnabled(condicion);
		rdbtnSecundario.setEnabled(condicion);
		rdbtnTerciario.setEnabled(condicion);

		this.textFieldRemuneracion.setEnabled(condicion);
		this.btnNuevoTicket.setEnabled(!condicion);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equalsIgnoreCase(Constantes.NUEVOTICKET))
		{
			this.enabledTicket(true);

		} else if (e.getActionCommand().equalsIgnoreCase(Constantes.CONFIRMARNUEVOTICKET))
		{
			this.enabledTicket(false);
			this.btnAceptar.setEnabled(false);
			this.textFieldRemuneracion.setText("");
			this.btnEliminarTicket.setEnabled(true);

		}

		else if (e.getActionCommand().equalsIgnoreCase(Constantes.ELIMINAR_TICKET))
		{

			this.btnEliminarTicket.setEnabled(false);

		}

	}

	public String getLocacion()
	{
		// TODO Auto-generated method stub
		return this.buttonGroupLocacion.getSelection().getActionCommand();
	}

	public String getPuesto()
	{
		// TODO Auto-generated method stub
		return this.buttonGroupPuesto.getSelection().getActionCommand();
	}

	public String getExperiencia()
	{
		// TODO Auto-generated method stub
		return this.buttonGroupExperiencia.getSelection().getActionCommand();
	}

	public String getEstudios()
	{
		// TODO Auto-generated method stub
		return this.buttonGroupEstudios.getSelection().getActionCommand();
	}

	public String getJornada()
	{
		// TODO Auto-generated method stub
		return this.buttonGroupJornada.getSelection().getActionCommand();
	}

	public void keyPressed(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
		if (e.getSource() == this.textFieldRemuneracion)
		{
			do_textFieldRemuneracion_keyReleased(e);
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	protected void do_textFieldRemuneracion_keyReleased(KeyEvent e)
	{
		this.remuneracion = -1;
		try
		{
			this.remuneracion = Integer.parseInt(this.textFieldRemuneracion.getText());
		} catch (NumberFormatException ex)
		{
		}
		this.btnAceptar.setEnabled(this.remuneracion > 0);
	}

	public int getRemunearcion()
	{
		// TODO Auto-generated method stub
		return this.remuneracion;
	}

	public void actualizaCliente()
	{
		if (this.cliente.getTicket() != null)
			this.textAreaTicket.setText(this.cliente.getTicket().toString());
		else
		{
			this.textAreaTicket.setText(Mensajes.SIN_TICKET.getValor());
		}
		this.btnEliminarTicket.setEnabled(this.cliente.getTicket() != null);
		Cliente emparejamiento = null;
		double comision = 0;
		if (this.tipoUsuario == 0)
		{
			EmpleadoPretenso ep = (EmpleadoPretenso) this.cliente;
			emparejamiento = Agencia.getInstance().getContratacionEmpleadoPretenso(ep);
			if (emparejamiento != null)
				comision = Agencia.getInstance().getComisionUsuario(this.usuario);

		} else
		{
			Empleador empleador = (Empleador) this.cliente;
			emparejamiento = Agencia.getInstance().getContratacionEmpleador(empleador);
			if (emparejamiento != null)
				comision = Agencia.getInstance().getComisionUsuario(this.usuario);

		}
		if (emparejamiento != null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Hay una nueva Coincidencia:\n");
			sb.append("Comision a pagar: ");
			sb.append(comision);
			sb.append("\nCoincidencia con el cliente:\n");
			sb.append(emparejamiento.toString());
			this.textAreaResultado.setText(sb.toString());
		} else
			this.textAreaResultado.setText("Sin coincidencias");
		this.actualizaLista();
	}

	public Cliente getCandidato()
	{
		Cliente u = null;
		if (this.list.getSelectedValue() != null)
			u = this.list.getSelectedValue().getCliente();
		return u;
	}
}
