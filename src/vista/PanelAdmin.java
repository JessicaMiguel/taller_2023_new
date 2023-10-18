package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloNegocio.Agencia;
import util.Constantes;

public class PanelAdmin extends PanelAgencia
{
	private JPanel panelSur;
	private JButton btnCerrarSesion;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JList<EmpleadoPretenso> listEmpleados;
	private JScrollPane scrollPane_1;
	private JList<Empleador> listEmpleadores;
	private JPanel panelEste;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblNewLabel;
	private JPanel panel_5;
	private JTextField textFieldInferior;
	private JPanel panel_6;
	private JLabel lblSuperior;
	private JPanel panel_7;
	private JTextField textFieldSuperior;
	private JPanel panel_9;
	private JButton btnGatillar;
	private JButton btnCambiar;
	private JPanel panel_10;
	private JPanel panel_11;
	private DefaultListModel<EmpleadoPretenso> listModelEmpleados = new DefaultListModel<EmpleadoPretenso>();
	private DefaultListModel<Empleador> listModelEmpleadores = new DefaultListModel<Empleador>();
	private JPanel panel_8;
	private JCheckBox chckbxPorPostulantes;
	private JButton btnPromo;

	/**
	 * Create the panel.
	 */
	public PanelAdmin(ActionListener actionListener)
	{
		super(actionListener);
		setLayout(new BorderLayout(0, 0));

		this.panelSur = new JPanel();
		add(this.panelSur, BorderLayout.SOUTH);

		this.btnCerrarSesion = new JButton("Cerrar Sesion");
		this.panelSur.add(this.btnCerrarSesion);

		this.panelEste = new JPanel();
		add(this.panelEste, BorderLayout.EAST);
		this.panelEste.setLayout(new GridLayout(0, 1, 0, 0));

		this.panel_3 = new JPanel();
		this.panel_3.setBorder(new TitledBorder(null, "Modificar limites de remuneracion", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		this.panelEste.add(this.panel_3);
		this.panel_3.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel_4 = new JPanel();
		this.panel_3.add(this.panel_4);

		this.lblNewLabel = new JLabel("Inferior:");
		this.panel_4.add(this.lblNewLabel);

		this.panel_5 = new JPanel();
		this.panel_3.add(this.panel_5);

		this.textFieldInferior = new JTextField();
		this.textFieldInferior.setColumns(10);
		this.panel_5.add(this.textFieldInferior);

		this.panel_6 = new JPanel();
		this.panel_3.add(this.panel_6);

		this.lblSuperior = new JLabel("Superior:");
		this.panel_6.add(this.lblSuperior);

		this.panel_7 = new JPanel();
		this.panel_3.add(this.panel_7);

		this.textFieldSuperior = new JTextField();
		this.textFieldSuperior.setColumns(10);
		this.panel_7.add(this.textFieldSuperior);

		this.panel_11 = new JPanel();
		this.panel_3.add(this.panel_11);

		this.panel_10 = new JPanel();
		this.panel_3.add(this.panel_10);

		this.btnCambiar = new JButton("Cambiar");
		this.btnCambiar.setEnabled(false);
		this.panel_10.add(this.btnCambiar);

		this.panel_9 = new JPanel();
		this.panelEste.add(this.panel_9);

		this.btnGatillar = new JButton("Gatillar Ronda");
		this.panel_9.add(this.btnGatillar);

		panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Aplicar Promo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEste.add(panel_8);

		chckbxPorPostulantes = new JCheckBox("Por Lista de Postulantes");
		panel_8.add(chckbxPorPostulantes);

		btnPromo = new JButton("Aplicar Promo");
		panel_8.add(btnPromo);

		this.panel = new JPanel();
		add(this.panel, BorderLayout.CENTER);
		this.panel.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel_1 = new JPanel();
		this.panel_1.setBorder(
				new TitledBorder(null, "Lista de Empleados:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panel.add(this.panel_1);
		this.panel_1.setLayout(new BorderLayout(0, 0));

		this.scrollPane = new JScrollPane();
		this.panel_1.add(this.scrollPane);

		this.listEmpleados = new JList<EmpleadoPretenso>();
		this.scrollPane.setViewportView(this.listEmpleados);

		this.panel_2 = new JPanel();
		this.panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Lista de Empleadores:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.panel.add(this.panel_2);
		this.panel_2.setLayout(new BorderLayout(0, 0));

		this.scrollPane_1 = new JScrollPane();
		this.panel_2.add(this.scrollPane_1);

		this.listEmpleadores = new JList<Empleador>();
		this.scrollPane_1.setViewportView(this.listEmpleadores);
		this.addActionListener(this.actionListener);
		this.addActionListener(this);
		this.listEmpleadores.setModel(listModelEmpleadores);
		this.listEmpleados.setModel(listModelEmpleados);
		this.setActionAndName(btnCerrarSesion, Constantes.CERRARSESION);
		this.setActionAndName(this.btnGatillar, Constantes.GATILLAR);
		this.setActionAndName(this.btnPromo, Constantes.APLICAR_PROMO);
		this.setActionAndName(this.chckbxPorPostulantes, Constantes.CHECK_BOX_LISTA_POSTULANTES);
		this.setActionAndName(btnCambiar, Constantes.MODIFICAR_VALORES);
		this.textFieldInferior.setName(Constantes.TEXTO_INFERIOR);
		this.textFieldSuperior.setName(Constantes.TEXTO_SUPERIOR);
		this.listEmpleadores.setName(Constantes.LISTA_EMPLEADORES);
		this.listEmpleados.setName(Constantes.LISTA_EMPLEADOS);
		this.textFieldInferior.addKeyListener(this);
		this.textFieldSuperior.addKeyListener(this);
		this.actualizarListas();

	}

	public void addActionListener(ActionListener actionListener)
	{
		this.btnCambiar.addActionListener(actionListener);
		this.btnCerrarSesion.addActionListener(actionListener);
		this.btnGatillar.addActionListener(actionListener);
		this.btnPromo.addActionListener(actionListener);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int inferior = -1;
		int superior = -1;

		try
		{
			inferior = Integer.parseInt(this.textFieldInferior.getText());
			superior = Integer.parseInt(this.textFieldSuperior.getText());
		} catch (NumberFormatException ex)
		{
		}

		this.btnCambiar.setEnabled(inferior < superior && inferior > 0);
	}

	public void actualizarListas()
	{
		this.listModelEmpleados.clear();
		this.listModelEmpleadores.clear();
		Iterator<EmpleadoPretenso> it_e = Agencia.getInstance().getIteratorEmpleadosPretensos();
		while (it_e.hasNext())
			this.listModelEmpleados.addElement(it_e.next());

		Iterator<Empleador> it_er = Agencia.getInstance().getIterartorEmpleadores();
		while (it_er.hasNext())
			this.listModelEmpleadores.addElement(it_er.next());

	}

	public int getLimiteInferior()
	{
		// TODO Auto-generated method stub
		return Integer.parseInt(this.textFieldInferior.getText());
	}

	public int getLimiteSuperior()
	{
		// TODO Auto-generated method stub
		return Integer.parseInt(this.textFieldSuperior.getText());
	}

	public boolean isPorTicket()
	{
		// TODO Auto-generated method stub
		return this.chckbxPorPostulantes.isSelected();
	}
}
