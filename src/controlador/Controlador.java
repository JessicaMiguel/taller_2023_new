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
 * por el usuario en la interfaz gr�fica de la aplicaci�n. Realiza acciones como
 * el inicio de sesi�n, registro, creaci�n de tickets, activaci�n de rondas de
 * contrataciones y otras acciones relacionadas con la l�gica de la aplicaci�n.
 * LOGIN: Cuando se recibe este comando, el m�todo login se llama para manejar
 * el proceso de inicio de sesi�n. La l�gica de inicio de sesi�n se ejecuta en
 * el m�todo login. REG_BUTTON_REGISTRAR: Este comando est� relacionado con el
 * proceso de registro de usuarios en la aplicaci�n. Cuando se recibe este
 * comando, se llama al m�todo registrar para gestionar el registro de usuarios.
 * CERRARSESION: Cuando se recibe este comando, el usuario actual se desconecta
 * y se guarda el estado de la aplicaci�n en un archivo llamado "Agencia.xml".
 * CONFIRMARNUEVOTICKET: Este comando est� relacionado con la confirmaci�n de la
 * creaci�n de un nuevo ticket. Cuando se recibe este comando, se llama al
 * m�todo nuevoTicket para gestionar la creaci�n del ticket. GATILLAR: Cuando se
 * recibe este comando, se activa una ronda de contrataciones en la aplicaci�n
 * llamando al m�todo gatillarRonda del objeto agencia. MODIFICAR_VALORES: Este
 * comando est� relacionado con la modificaci�n de valores V1 y V2 en la
 * aplicaci�n en funci�n del tipo de usuario. APLICAR_PROMO: El c�digo llama a
 * un m�todo aplicaPromo y muestra un mensaje con los detalles de un cliente
 * despu�s de aplicar la promoci�n.
 * 
 */

public class Controlador implements ActionListener
{
	private IVista vista = new Ventana(this);
	private IOptionPane myOptionPane = new MyJOptionPane();
	private Agencia agencia = Agencia.getInstance();
	private Usuario usuario = null;
	private int tipoUsuario = 0;
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
	 * Este m�todo tiene como objetivo eliminar un ticket de la instancia de la
	 * clase Agencia utilizando el m�todo eliminarTicket() de esa instancia. Se
	 * trata la excepci�n ImposibleModificarTicketsException Luego, despu�s de
	 * intentar eliminar el ticket, se llama al m�todo actualizaCliente() de la
	 * vista para actualizar la interfaz de usuario del cliente.
	 * Si el tiecket no se puede elimiar muestra un cuadro emergente con el mensaje correspondiente a la excepcion ImposibleModificarTicketsException 
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
		this.usuario.setCandidato(this.vista.getCandidato());
		System.out.println(this.usuario + "  eligio a " + this.usuario.getCandidato());
	}

	/**
	 * Se encarga de aplicar una promoci�n en la aplicaci�n y luego mostrar
	 * informaci�n sobre el cliente despu�s de aplicar la promoci�n en una ventana emergente
	 * 
	 * 
	 */

	public void aplicarPromo()
	{
		Usuario cl = agencia.aplicaPromo(this.vista.isPorTicket());
		this.myOptionPane.ShowMessage(cl.toString());
	}

	/**
	 * Se utiliza para modificar los valores de las variables limiteInferior y
	 * limiteSuperior en el objeto agencia.
	 * En caso de error se muestra el mensaje correspondiente en una ventana emergente
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
	 * El m�todo se utiliza para iniciar una nueva ronda de contrataciones en el
	 * sistema de la agencia, invocando el m�todo correspondiente en la instancia de
	 * la clase Agencia.
	 */

	public void gatillar()
	{
		agencia.gatillarRonda();
		this.vista.actualizar(tipoUsuario, usuario);
		this.myOptionPane.ShowMessage(Agencia.getInstance().getEstado());
	}

	/**
	 * El m�todo cerrarSesion() se encarga de cerrar la sesi�n del usuario actual y
	 * guardar el estado actual de la agencia en un archivo antes de finalizar la
	 * sesi�n.
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
	 * Permite a los usuarios crear nuevos tickets. El m�todo recopila informaci�n
	 * ingresada por el usuario desde la vista. Estos datos incluyen:
	 * 
	 * jornada: La jornada del trabajo. locacion: La locaci�n o lugar donde se
	 * llevar� a cabo el trabajo. estudios: Informaci�n sobre los estudios
	 * requeridos para el trabajo. puesto: El tipo de puesto de trabajo.
	 * experiencia: La experiencia previa requerida para el trabajo. remuneracion:
	 * La remuneraci�n ofrecida para el trabajo. Si tipoUsuario es Empleado,
	 * intentar� crear un nuevo ticket de empleado utilizando los datos recopilados.
	 * Esto se hace llamando al m�todo crearTicketEmpleado() de la instancia de
	 * agencia. Si ocurre alguna excepci�n del tipo
	 * ImposibleModificarTicketsException, se trata. Si tipoUsuario es Empleador
	 * intentar� crear un nuevo ticket de empleador utilizando los datos
	 * recopilados. Esto se hace llamando al m�todo crearTicketEmpleador() de la
	 * instancia de agencia. Tambi�n maneja excepciones del tipo
	 * ImposibleModificarTicketsException Despu�s de crear el nuevo ticket (ya sea
	 * de empleado o empleador), se llama al m�todo actualizaCliente() de la vista
	 * para actualizar la interfaz de usuario del cliente.
	 * En caso de error se muestra el mensaje correspondiente
	 */

	public void nuevoTicket()
	{
		String jornada = this.vista.getJornada();
		String locacion = this.vista.getLocacion();
		String estudios = this.vista.getEstudios();
		String puesto = this.vista.getPuesto();
		String experiencia = this.vista.getExperiencia();
		int remuneracion = this.vista.getRemuneracion();
		if (this.tipoUsuario == 0)
			try
			{
				agencia.crearTicketEmpleado(locacion, remuneracion, jornada, puesto, experiencia, estudios, usuario);
			} catch (ImposibleModificarTicketsException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}
		else if (this.tipoUsuario == 1)
			try
			{
				agencia.crearTicketEmpleador(locacion, remuneracion, jornada, puesto, experiencia, estudios, usuario);
			} catch (ImposibleModificarTicketsException e)
			{
				this.myOptionPane.ShowMessage(e.getMessage());
			}

		this.vista.actualizaCliente();

	}

	/**
	 * El m�todo registrar() se encarga de registrar un nuevo usuario (empleado o
	 * empleador) en la agencia, realizar su inicio de sesi�n y actualizar la vista
	 * con los detalles del usuario registrado.
	 * 
	 * Si el tipo de usuario seleccionado es "Empleado", se realiza lo siguiente: Se
	 * intenta registrar un empleado en la agencia utilizando los datos
	 * proporcionados (nombre de usuario, contrase�a, nombre real, apellido,
	 * tel�fono y edad).
	 * 
	 * Si el registro no es exitoso porque el usuario ya existe entonces se trata la
	 * excepci�n NewRegistrerException y se muestra el mensaje de error Si el
	 * registro no es exitoso porque las contrase�as no coinciden entonces se trata
	 * la excepci�n ContraNoCoincideException y se muestra el mensaje de error Si el
	 * registro es exitoso,se intenta loguear al empleado en el sistema. Si el
	 * logueo no es exitoso porque la contrase�a es incorrecta entonces se trata la
	 * excepcion ContraException y se muestra el mensaje de error Si el logueo no es
	 * exitoso porque el usuario no es correcto entonces se trata la excepcion
	 * NombreUsuarioException y se muestra el mensaje de error
	 * 
	 * An�logamente para el empleado pero utilizando los datos correspondientes
	 * (nombre de usuario, contrase�a, nombre real, tipo de persona y rubro).
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
	 * Si el logueo no es exitoso porque la contrase�a es erronea se trata la
	 * excepcion ContraException y se muestra el mensaje de error Si el logueo no es
	 * exitoso porque lel usuario no existe se trata la excepcion
	 * NombreUsuarioException y se muestra el mensaje de error
	 * 
	 */

	public void login()
	{
		try
		{
			this.usuario = this.agencia.login(this.vista.getUsserName(), this.vista.getPassword());
			this.tipoUsuario = this.agencia.getTipoUsuario();
			this.vista.actualizar(this.tipoUsuario, usuario);

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
