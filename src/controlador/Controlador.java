package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import excepciones.ContraException;
import excepciones.ImposibleCrearEmpleadoException;
import excepciones.ImposibleCrearEmpleadorException;
import excepciones.ImposibleModificarTicketsException;
import excepciones.LimiteInferiorRemuneracionInvalidaException;
import excepciones.LimiteSuperiorRemuneracionInvalidaException;
import excepciones.NewRegisterException;
import excepciones.NombreUsuarioException;
import modeloDatos.Cliente;
import modeloDatos.Usuario;
import modeloNegocio.Agencia;
import util.Constantes;
import vista.IOptionPane;
import vista.IVista;
import vista.MyJOptionPane;
import vista.Ventana;

/**
 * 
 * Es el controlador de eventos que responde a diferentes acciones realizadas
 * por el usuario en la interfaz grafica de la aplicacion. Realiza acciones como
 * el inicio de sesion, registro, creacion de tickets, activacion de rondas de
 * contrataciones y otras acciones relacionadas con la logica de la aplicacion.
 * LOGIN: Cuando se recibe este comando, el metodo login se llama para manejar
 * el proceso de inicio de sesion. La logica de inicio de sesion se ejecuta en
 * el metodo login. REG_BUTTON_REGISTRAR: Este comando esta relacionado con el
 * proceso de registro de usuarios en la aplicacion. Cuando se recibe este
 * comando, se llama al metodo registrar para gestionar el registro de usuarios.
 * CERRARSESION: Cuando se recibe este comando, el usuario actual se desconecta
 * y se guarda el estado de la aplicacion en un archivo llamado "Agencia.xml".
 * CONFIRMARNUEVOTICKET: Este comando esta relacionado con la confirmacion de la
 * creacion de un nuevo ticket. Cuando se recibe este comando, se llama al
 * metodo nuevoTicket para gestionar la creacion del ticket. GATILLAR: Cuando se
 * recibe este comando, se activa una ronda de contrataciones en la aplicacion
 * llamando al metodo gatillarRonda del objeto agencia. MODIFICAR_VALORES: Este
 * comando esta relacionado con la modificacion de valores V1 y V2 en la
 * aplicacion en funcion del tipo de usuario. APLICAR_PROMO: El codigo llama a
 * un metodo aplicaPromo y muestra un mensaje con los detalles de un cliente
 * despues de aplicar la promocion.<br>
 * El atributo usuario podria ser null en el caso de que no haya un usuario logueado<br>
 *  <b>Invariante de clase</b>, <br>
 * Los atributos vista y myOptionPane son diferentes de null <br>
 * 
 * 
 */

public class Controlador implements ActionListener
{
	private IVista vista = new Ventana(this);
	private IOptionPane myOptionPane = new MyJOptionPane();
	private Agencia agencia = Agencia.getInstance();
	private Usuario usuario = null;
	private String nombreArchivo = "Agencia.xml";

	public Controlador()
	{

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String comando = e.getActionCommand();
		if (comando.equalsIgnoreCase(Constantes.REGISTRAR))
		{
		} else if (comando.equalsIgnoreCase(Constantes.REG_BUTTON_CANCELAR))
		{
		} else if (comando.equalsIgnoreCase(Constantes.LOGIN))
		{
			this.login();
		} else if (comando.equalsIgnoreCase(Constantes.REG_BUTTON_REGISTRAR))
		{
			this.registrar();
		} else if (comando.equalsIgnoreCase(Constantes.CERRARSESION))
		{
			this.cerrarSesion();
		} else if (comando.equalsIgnoreCase(Constantes.CONFIRMARNUEVOTICKET))
			this.nuevoTicket();
		else if (comando.equalsIgnoreCase(Constantes.GATILLAR))
		{
			this.gatillar();
		} else if (comando.equalsIgnoreCase(Constantes.MODIFICAR_VALORES))
		{
			this.modificarValorsLimitesRemuneracion();

		} else if (comando.equalsIgnoreCase(Constantes.SELECCIONAR_CANDIDATO))
		{
			this.seleccionarCandidato();

		}

		else if (comando.equalsIgnoreCase(Constantes.APLICAR_PROMO))
		{
			this.aplicarPromo();
		} else if (comando.equalsIgnoreCase(Constantes.ELIMINAR_TICKET))
		{
			this.eliminarTicket();
		}

	}

	/**
	 * Este metodo tiene como objetivo eliminar un ticket de la instancia de la
	 * clase Agencia utilizando el metodo eliminarTicket() de esa instancia. Se
	 * trata la excepcion ImposibleModificarTicketsException Luego, despues de
	 * intentar eliminar el ticket, se llama al metodo actualizaCliente() de la
	 * vista para actualizar la interfaz de usuario del cliente. Si el tiecket no se
	 * puede elimiar muestra un cuadro emergente con el mensaje correspondiente al
	 * enumerado Mensajes.ERROR_AGENCIA_EN_CONTRATACION
	 */

	private void eliminarTicket()
	{
		try
		{
			Agencia.getInstance().eliminarTicket();
		} catch (ImposibleModificarTicketsException e)
		{
			// TODO Auto-generated catch block
			this.myOptionPane.ShowMessage(e.getMessage());
		}
		this.vista.actualizaCliente();
	}

	private void seleccionarCandidato()
	{
		Cliente cli=(Cliente) this.usuario;
		cli.setCandidato(this.vista.getCandidato());
		System.out.println(this.usuario + "  eligio a " + cli.getCandidato());
	}

	/**
	 * Se encarga de aplicar una promocion en la aplicacion y luego mostrar
	 * informacion sobre el cliente (metodo toString()) despues de aplicar la
	 * promocion en una ventana emergente
	 * 
	 * 
	 */

	public void aplicarPromo()
	{
		Cliente cl = agencia.aplicaPromo(this.vista.isPorTicket());
		this.myOptionPane.ShowMessage(cl.toString());
	}

	/**
	 * Se utiliza para modificar los valores de las variables limiteInferior y
	 * limiteSuperior en el objeto agencia. En caso de error se muestra el mensaje
	 * correspondiente en una ventana emergente, el cual puede ser:
	 * Mensajes.LIMITE_REMUNERACION_NEGATIVO o bien,
	 * Mensajes.LIMITE_REMUNERACION_INVALIDO
	 * 
	 */

	public void modificarValorsLimitesRemuneracion()
	{

		try
		{
			agencia.setLimitesRemuneracion(this.vista.getLimiteInferior(), this.vista.getLimiteSuperior());

		} catch (LimiteInferiorRemuneracionInvalidaException | LimiteSuperiorRemuneracionInvalidaException e)
		{
			this.myOptionPane.ShowMessage(e.getMessage());

		}

	}

	/**
	 * El metodo se utiliza para iniciar una nueva ronda de contrataciones en el
	 * sistema de la agencia, invocando el metodo correspondiente en la instancia de
	 * la clase Agencia. Muestra en una ventana el estado en el que queda la
	 * agencia, mediante el metdo getEstado()
	 */

	public void gatillar()
	{
		agencia.gatillarRonda();
		this.vista.actualizar(Agencia.getInstance().getTipoUsuario(), usuario);
		this.myOptionPane.ShowMessage(Agencia.getInstance().getEstado());
	}

	/**
	 * El metodo cerrarSesion() se encarga de cerrar la sesion del usuario actual y
	 * guardar el estado actual de la agencia en un archivo antes de finalizar la
	 * sesion.
	 * 
	 */

	public void cerrarSesion()
	{
		this.usuario = null;
		this.agencia.cerrarSesion();
		try
		{
			agencia.guardarAgencia(nombreArchivo);
		} catch (IOException e)
		{
			this.myOptionPane.ShowMessage(e.getMessage());
		}

	}

	/**
	 * Permite a los usuarios crear nuevos tickets. El metodo recopila informacion
	 * ingresada por el usuario desde la vista. Estos datos incluyen:
	 * 
	 * jornada: La jornada del trabajo. locacion: La locacion o lugar donde se
	 * llevara a cabo el trabajo. estudios: Informacion sobre los estudios
	 * requeridos para el trabajo. puesto: El tipo de puesto de trabajo.
	 * experiencia: La experiencia previa requerida para el trabajo. remuneracion:
	 * La remuneracion ofrecida para el trabajo. Si tipoUsuario es Empleado,
	 * intentara crear un nuevo ticket de empleado utilizando los datos recopilados.
	 * Esto se hace llamando al metodo crearTicketEmpleado() de la instancia de
	 * agencia. Si ocurre alguna excepcion del tipo
	 * ImposibleModificarTicketsException, se trata. Si tipoUsuario es Empleador
	 * intentara crear un nuevo ticket de empleador utilizando los datos
	 * recopilados. Esto se hace llamando al metodo crearTicketEmpleador() de la
	 * instancia de agencia. Tambien maneja excepciones del tipo
	 * ImposibleModificarTicketsException Despues de crear el nuevo ticket (ya sea
	 * de empleado o empleador), se llama al metodo actualizaCliente() de la vista
	 * para actualizar la interfaz de usuario del cliente. En caso de error se
	 * muestra el mensaje correspondiente al enumerado
	 * Mensajes.ERROR_AGENCIA_EN_CONTRATACION
	 */

	public void nuevoTicket()
	{
		Cliente cliente=(Cliente) this.usuario;
		String jornada = this.vista.getJornada();
		String locacion = this.vista.getLocacion();
		String estudios = this.vista.getEstudios();
		String puesto = this.vista.getPuesto();
		String experiencia = this.vista.getExperiencia();
		int remuneracion = this.vista.getRemuneracion();
		if (Agencia.getInstance().getTipoUsuario() == 0)
			try
			{
				agencia.crearTicketEmpleado(locacion, remuneracion, jornada, puesto, experiencia, estudios, cliente);
			} catch (ImposibleModificarTicketsException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}
		else if (Agencia.getInstance().getTipoUsuario() == 1)
			try
			{
				agencia.crearTicketEmpleador(locacion, remuneracion, jornada, puesto, experiencia, estudios, cliente);
			} catch (ImposibleModificarTicketsException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}

		this.vista.actualizaCliente();

	}

	/**
	 * El metodo registrar() se encarga de registrar un nuevo usuario (empleado o
	 * empleador) en la agencia, realizar su inicio de sesion y actualizar la vista
	 * con los detalles del usuario registrado.
	 * 
	 * Si el tipo de usuario seleccionado es "Empleado", se realiza lo siguiente: Se
	 * intenta registrar un empleado en la agencia utilizando los datos
	 * proporcionados (nombre de usuario, contrasena, nombre real, apellido,
	 * telefono y edad).
	 * 
	 * Si el registro no es exitoso porque el usuario ya existe entonces se trata la
	 * excepcion NewRegistrerException y se muestra el mensaje de error
	 * correspondiente al enumarado Mensajes.USUARIO_REPETIDO. Si el registro no es
	 * exitoso porque las contrasenas no coinciden entonces se trata la excepcion
	 * ContraNoCoincideException y se muestra el mensaje de error correspondiente al
	 * enumerado Mensajes.PASS_NO_COINCIDE. Si el registro no es exitoso porque
	 * alguno de los parametros requerido es nulo, se trata la excecpcion
	 * ImposibleCrearEmpleadoException, y se muestra el mensaje de error
	 * correspondiente al enumerado Mensajes.PARAMETROS_NULOS Si el registro es
	 * exitoso,se loguea al empleado en el sistema.
	 * 
	 * Analogamente para el empleado pero utilizando los datos correspondientes
	 * (nombre de usuario, contrasena, nombre real, tipo de persona y rubro).
	 * 
	 */
	public void registrar()
	{
		String tipoUsuario = this.vista.getTipoUsuario();
		String nombreUsuario = vista.getRegUsserName();
		String pass = this.vista.getRegPassword();
		int t = 0;
		if (tipoUsuario.equalsIgnoreCase(Constantes.EMPLEADO))
		{

			try
			{
				this.usuario = agencia.registroEmpleado(nombreUsuario, pass, this.vista.getNombreReal(),
						this.vista.getApellido(), this.vista.getTelefono(), this.vista.getEdad());
			} catch (NewRegisterException | ImposibleCrearEmpleadoException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}

		} else
		{
			try
			{
				this.usuario = agencia.registroEmpleador(nombreUsuario, pass, this.vista.getNombreReal(),
						this.vista.getTelefono(), this.vista.getTipoPersona(), this.vista.getRubro());
			} catch (NewRegisterException | ImposibleCrearEmpleadorException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}
			t = 1;
		}
		if (usuario != null)
		{
			try
			{
				this.agencia.login(nombreUsuario, pass);
			} catch (ContraException | NombreUsuarioException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}

			this.vista.actualizar(t, usuario);
		}
	}

	/**
	 * Se encarga de autenticar (loguear) a un usuario en la agencia utilizando las
	 * credenciales ingresadas en la vista
	 * 
	 * Si el logueo no es exitoso porque la contrasena es erronea se trata la
	 * excepcion ContraException y se muestra el mensaje de error correspondiente al enumerado Mensajes.PASS_ERRONEO.  Si el logueo no es
	 * exitoso porque lel usuario no existe se trata la excepcion 
	 * NombreUsuarioException y se muestra el mensaje de error correspondiente al enumerado Mensajes.USUARIO_DESCONOCIDO.
	 * 
	 */

	public void login()
	{
		try
		{
			this.usuario = this.agencia.login(this.vista.getUsserName(), this.vista.getPassword());
			
			this.vista.actualizar(Agencia.getInstance().getTipoUsuario(), usuario);

		} catch (ContraException e)
		{
			this.myOptionPane.ShowMessage(e.getMessage());

		} catch (NombreUsuarioException e)
		{
			this.myOptionPane.ShowMessage(e.getMessage());
		}
	}

	public IOptionPane getMyOptionPane()
	{
		return myOptionPane;
	}

	public void setMyOptionPane(IOptionPane myOptionPane)
	{
		this.myOptionPane = myOptionPane;
	}

	public IVista getVista()
	{
		return vista;
	}

	public void setVista(IVista vista)
	{
		this.vista = vista;
	}

}
