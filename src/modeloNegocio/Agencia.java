package modeloNegocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import excepciones.ContraException;
import excepciones.ImposibleCrearEmpleadoException;
import excepciones.ImposibleCrearEmpleadorException;
import excepciones.ImposibleModificarTicketsException;
import excepciones.LimiteInferiorRemuneracionInvalidaException;
import excepciones.LimiteSuperiorRemuneracionInvalidaException;
import excepciones.NewRegisterException;
import excepciones.NombreUsuarioException;
import modeloDatos.Admin;
import modeloDatos.Cliente;
import modeloDatos.Contratacion;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.Ticket;
import modeloDatos.Usuario;
import modeloDatos.ClientePuntaje;
import persistencia.AgenciaDTO;
import persistencia.IPersistencia;
import persistencia.UtilPersistencia;
import util.Constantes;
import util.Mensajes;

/**
 * Clase que representa la Agencia, aplica el patron Singleton<br> 
 * El atributo persistencia puede ser null, en cuyo caso los metodos cargarAgencia y guardarAgencia devolveran <b>false</b><br>
 * <b>Invariantes de clases </b><br>
 * Los atributos: <b>empleados, empleadores, contrataciones, comisionesUsuarios</b> son diferentes de null.<br>
 * Los atributos <b>limiteInferior y limiteSuperior</b> son positivos, y limiteInferior es menor a limiteSuperior.<br>
 * El atributo tipoUsuario, representa el tipo de usuario logeado, siempre sera igual a:<br>
 * -1 Si no hay usuario logueado<br>
 * 0  Si el usuario logueado es Empleado Pretenso<br>
 * 1  Si el usuario logueado es Empleador<br>
 * 2  Si el usuario logueado es el Administrador<br>
 * 
 * 
 * 
 */
public class Agencia
{
	private static Agencia instance;
	private HashMap<String, EmpleadoPretenso> empleados = new HashMap<String, EmpleadoPretenso>();
	private HashMap<String, Empleador> empleadores = new HashMap<String, Empleador>();
	private int limiteInferior=1000;
	private int limiteSuperior=2000;
	private Usuario usuarioLogeado = null;
	private int tipoUsuario = -1;
	private ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
	private boolean estadoContratacion = false;
	private HashMap<Cliente, Double> comisionesUsuarios = new HashMap<Cliente, Double>();
	private IPersistencia persistencia;

	private Agencia()
	{
	}

	public static Agencia getInstance()
	{
		if (instance == null)
			instance = new Agencia();
		return instance;
	}

	public Iterator<EmpleadoPretenso> getIteratorEmpleadosPretensos()
	{

		return this.empleados.values().iterator();
	}

	public Iterator<Empleador> getIterartorEmpleadores()
	{

		return this.empleadores.values().iterator();
	}

	/**
	 * Especifica los limites de remuneracion pretendida para determinar los tres
	 * rangos posibles <br>
	 * Rango 1 si remuneracion pretendida menor a limite Inferior <br>
	 * Rango 2 si limite inferior menor o igual a remuneracion pretendida menor a
	 * limite superior <br>
	 * Rango 3 si limite superior menor a remuneracion pretendida
	 * 
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @throws LimiteSuperiorRemuneracionInvalidaException Se lanza la excepcion si
	 *                                                     limite superior es menor
	 *                                                     que limite superior
	 * @throws LimiteInferiorRemuneracionInvalidaException Se lanza la excepcion si
	 *                                                     limite inferior es menor
	 *                                                     a cero
	 */
	public void setLimitesRemuneracion(int limiteInferior, int limiteSuperior)
			throws LimiteSuperiorRemuneracionInvalidaException, LimiteInferiorRemuneracionInvalidaException
	{
		if (limiteInferior < 0)
			throw new LimiteInferiorRemuneracionInvalidaException(Mensajes.LIMITE_REMUNERACION_NEGATIVO.getValor(),
					this.limiteInferior, this.limiteSuperior, limiteInferior);
		if (limiteSuperior <= this.limiteInferior)
			throw new LimiteSuperiorRemuneracionInvalidaException(Mensajes.LIMITE_REMUNERACION_INVALIDO.getValor(),
					this.limiteInferior, this.limiteSuperior, limiteSuperior);

		this.limiteSuperior = limiteSuperior;
		this.limiteInferior = limiteInferior;
	}

	/**
	 * Dependiendo del valor del atributo estadoContratacion, este metodo realiza
	 * diferentes acciones: Si estadoContratacion es verdadero, se buscan matcheos
	 * entre empleadores y empleados para trabajos disponibles. Tambien, se penaliza
	 * a los empleadores que tienen un tocket asociado pero no fueron elegidos en
	 * una ronda de contratacion, y "limpia" o elimina todas las conexiones entre
	 * candidatos y puestos de trabajo despues de una ronda de contratacion. Esto
	 * prepara el sistema para una nueva ronda de contratacion donde los candidatos
	 * pueden postularse nuevamente a nuevos puestos. Si estadoContratacion es
	 * falso, se generan nuevos postulantes para los trabajos disponibles y se
	 * calculan los premios o castigos basados en el resultado de la ronda anterior.
	 * Al final del metodo, se invierte el valor de estadoContratacion, lo que
	 * significa que se alternara entre rondas de contratacion y generacion de
	 * candidatos en llamadas sucesivas a este metodo. Dependiendo del
	 * estadoContratacion se controla el flujo de una ronda de contratacion y la
	 * generacion de candidatos <br>
	 * 
	 */

	public void gatillarRonda()
	{
		if (this.estadoContratacion)
		{
			this.buscaMatcheos();
			this.penalizaEmpleadores();
			this.limpiaPostulantes();

		} else
		{
			this.generaPostulantes();
			this.calculaPremiosCastigosAsignaciones();

		}
		this.estadoContratacion = !this.estadoContratacion;
	}

	private void limpiaPostulantes()
	{
		Iterator<Empleador> it = this.empleadores.values().iterator();
		while (it.hasNext())
			it.next().setListaDePostulantes(null);

		Iterator<EmpleadoPretenso> it2 = this.empleados.values().iterator();
		while (it2.hasNext())
			it2.next().setListaDePostulantes(null);

	}

	/**
	 * Este metodo penaliza a los empleadores que tienen un ticket asociado pero no
	 * fueron elegidos en una ronda de contratacion.
	 * 
	 * <b>Pre: </b> Debe haber objetos validos y registrados en las colecciones<br>
	 * empleadores y empleados. <br>
	 */

	private void penalizaEmpleadores()
	{
		ArrayList<Empleador> noElegidos = new ArrayList<Empleador>();
		Iterator<Empleador> itEmpleadores = this.empleadores.values().iterator();
		while (itEmpleadores.hasNext())
		{
			Empleador empleador = itEmpleadores.next();
			if (empleador.getTicket() != null)
				noElegidos.add(empleador);
		}
		Iterator<EmpleadoPretenso> itEmpleados = this.empleados.values().iterator();
		while (itEmpleados.hasNext())
		{
			EmpleadoPretenso empleado = itEmpleados.next();
			if (empleado.getCandidato() != null)
				noElegidos.remove(empleado.getCandidato());
		}

		for (int i = 0; i < noElegidos.size(); i++)
			noElegidos.get(i).setPuntaje(noElegidos.get(i).getPuntaje() + 20);
	}

	/**
	 * Este metodo otorga premios y castigos a los clientes que participan en el
	 * proceso de seleccion. <b>Pre: </b>Las listas de postulantes estan ordenadas
	 * 
	 */

	public void calculaPremiosCastigosAsignaciones()
	{
		Iterator<Empleador> itEmpleadores = this.empleadores.values().iterator();

		while (itEmpleadores.hasNext())
		{
			Empleador empleador = itEmpleadores.next();
			if (empleador.getListaDePostulantes() != null && !empleador.getListaDePostulantes().isEmpty())
			{
				ArrayList<ClientePuntaje> ups = empleador.getListaDePostulantes();
				ups.get(0).getCliente().setPuntaje(ups.get(0).getCliente().getPuntaje() + 5);
				ups.get(ups.size() - 1).getCliente().setPuntaje(ups.get(0).getCliente().getPuntaje() - 5);

			}
		}

		Iterator<EmpleadoPretenso> itEmpleados = this.empleados.values().iterator();

		while (itEmpleados.hasNext())
		{
			EmpleadoPretenso empleado = itEmpleados.next();
			if (empleado.getListaDePostulantes() != null && !empleado.getListaDePostulantes().isEmpty())
			{
				ArrayList<ClientePuntaje> ups = empleado.getListaDePostulantes();
				ups.get(0).getCliente().setPuntaje(ups.get(0).getCliente().getPuntaje() + 10);
			}
		}

	}

	private void buscaMatcheos()
	{
		Iterator<Empleador> itEmpleadores = this.empleadores.values().iterator();

		while (itEmpleadores.hasNext())
		{
			Empleador empleador = itEmpleadores.next();

			if (empleador.getCandidato() != null)
			{

				Cliente empleado = empleador.getCandidato();

				if (empleado.getCandidato() != null)
				{
					if (empleado.getCandidato() == empleador)
					{
						this.match(empleador, (EmpleadoPretenso) empleado);

					}
				}
			}
		}
	}

	/**
	 * Esta relacionado con la ocurrencia de un "match" entre un empleador y un
	 * empleado pretenso, lo que implica que un empleado potencial ha sido
	 * seleccionado por un empleador. Realiza ajustes en los puntajes y las
	 * comisiones, y marca a ambos clientes como no disponibles para futuros matches
	 * o contrataciones. <b>Pre: </b> Empleado y empleador deben ser validos y estar
	 * registrados en el sistema <br>
	 * Crea una nueva instancia de Contratacion que representa la contratacion o
	 * emparejamiento entre el empleador y el empleado. Incrementa el puntaje del
	 * empleado en 10 puntos Incrementa el puntaje del empleador en 50 puntos
	 * Calcula las comisiones para el empleado y el empleador en funcion del Ticket
	 * asociado al empleador. Empleado y empleador ya no estan disponibles para
	 * futuros emparejamientos o contrataciones.<br>
	 * 
	 * @param empleador: Objeto empleador con quien el empleado pretenso hace match
	 * @param empleado:  Objeto empleado con quien el empleador hace match <br>
	 *                   <b>Post:</b> Los tickets del Empleado y del emepleador se
	 *                   eliminan sin generar penalizaciones.
	 */

	public void match(Empleador empleador, EmpleadoPretenso empleado)
	{
		System.out.println("HAY MATCH ENTRE " + empleador + "    y    " + empleado);
		this.contrataciones.add(new Contratacion(empleador, empleado));
		empleado.setPuntaje(empleado.getPuntaje() + 10);
		empleador.setPuntaje(empleador.getPuntaje() + 50);
		this.comisionesUsuarios.put(empleado, empleado.calculaComision(empleador.getTicket()));
		this.comisionesUsuarios.put(empleador, empleador.calculaComision(empleador.getTicket()));

		empleado.setTicket(null);
		empleador.setTicket(null);

	}

	/**
	 * Este metodo se encarga de calcular y asignar listas de postulantes a los
	 * Empleadores y EmpleadoPretenso en funcion de sus Ticket y las comparaciones
	 * de puntajes entre ellos. <b>Pre: </b> Listas de empleados y empleadores con
	 * elementos validos <br>
	 * Establece la lista de EmpleadoPretenso ordenados de mayor a menor como la
	 * lista de postulantes para el Empleador Establece una lista de Empleadores
	 * ordenada por puntaje de mayor a menor de compatibilidad y la establece como
	 * la lista de postulantes para ese EmpleadoPretenso.<br>
	 * 
	 */

	public void generaPostulantes()
	{
		Iterator<EmpleadoPretenso> itEmpleados;
		Iterator<Empleador> itEmpleadores;

		itEmpleadores = this.empleadores.values().iterator();
		while (itEmpleadores.hasNext())
		{
			Empleador empleador = itEmpleadores.next();
			if (empleador.getTicket() != null)
			{
				itEmpleados = this.empleados.values().iterator();
				ArrayList<ClientePuntaje> asignaciones = new ArrayList<ClientePuntaje>();
				while (itEmpleados.hasNext())
				{
					EmpleadoPretenso empleado = itEmpleados.next();
					if (empleado.getTicket() != null)
					{
						double puntaje = empleador.getTicket().getComparacionTotal(empleado.getTicket());
						ClientePuntaje up = new ClientePuntaje(puntaje, empleado);
						asignaciones.add(up);

					}

				}
				Collections.sort(asignaciones);
				empleador.setListaDePostulantes(asignaciones);
			}
		}

		itEmpleados = this.empleados.values().iterator();
		while (itEmpleados.hasNext())
		{
			EmpleadoPretenso empleado = itEmpleados.next();
			if (empleado.getTicket() != null)
			{
				itEmpleadores = this.empleadores.values().iterator();
				ArrayList<ClientePuntaje> asignaciones = new ArrayList<ClientePuntaje>();
				while (itEmpleadores.hasNext())
				{
					Empleador empleador = itEmpleadores.next();
					if (empleador.getTicket() != null)
					{
						double puntaje = empleador.getTicket().getComparacionTotal(empleado.getTicket());
						ClientePuntaje up = new ClientePuntaje(puntaje, empleador);
						asignaciones.add(up);

					}

				}
				Collections.sort(asignaciones);
				empleado.setListaDePostulantes(asignaciones);
			}
		}

	}

	/**
	 * Tiene como objetivo guardar la informacion de una agencia en un archivo
	 * utilizando un mecanismo de persistencia. Este archivo contendra la
	 * informacion necesaria para restaurar la agencia en un estado similar en el
	 * futuro si es necesario. <b>Pre: </b> Nombre de archivo valido <br>
	 * Se guarda el estado de la agencia en el archivo <br>
	 * 
	 * @param nombreArchivo String, con el nombre del archivo donde se almacenara la agencia
	 * @return retorna true o false en caso de exito o fracaso en el guardado
	 * @throws IOException Se lanza en caso de error de entrada/salida
	 */

	public boolean guardarAgencia(String nombreArchivo) throws IOException
	{
		boolean respuesta = false;
		if (this.persistencia != null)
		{
			persistencia.abrirOutput(nombreArchivo);
			persistencia.escribir(UtilPersistencia.AgenciaDtoFromAgencia());
			persistencia.cerrarOutput();
			respuesta = true;
		}
		return respuesta;
	}

	/**
	 * Se encarga de crear un nuevo ticket para un empleado con los datos enviados
	 * por parametros. Si el Empleado tiene un ticket activo, este sera eliminado.
	 * 
	 * <b>Pre:</b> El cliente debe ser un empleado <br>
	 * 
	 * @param locacion     String con la locacion del empleado
	 * @param remuneracion int con la remuneracion pretendida por el empleado
	 * @param jornada      String con la jornada de preferencia del empleado
	 * @param puesto       String tipo de puesto al que aspira el empleado
	 * @param experiencia  String experiencia del empleado
	 * @param estudios     String estudios que posee el empleado
	 * @param cliente      objeto cliente del ticket
	 * @throws ImposibleModificarTicketsException lanza una excepcion
	 *                                            ImposibleModificarTicketsException
	 *                                            si el estado de contratacion no
	 *                                            esta en un estado valido <br>
	 * 
	 * 
	 */

	public void crearTicketEmpleado(String locacion, int remuneracion, String jornada, String puesto,
			String experiencia, String estudios, Cliente cliente) throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		if (cliente.getTicket() != null)
			this.eliminarTicket();
		cliente.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));
	}

	/**
	 * Se encarga de crear un nuevo ticket para un empleador. Si el Empleador tiene
	 * un ticket activo, este sera eliminado. <b>pre: </b> El cliente debe ser un
	 * empleador (no un empleado) <br>
	 * 
	 * @param locacion     String locacion de la oferta del empleador
	 * @param remuneracion int remuneracion ofrecida por el empleador
	 * @param jornada      String jornada del puesto de trabajo que abre el
	 *                     empleador
	 * @param puesto       String tipo de puesto de la posicion
	 * @param experiencia  String experiencia requerida para el puesto
	 * @param estudios     String estudios requeridos para el puesto
	 * @param cliente      objeto Usuario a quien se le creara el ticket
	 * @throws ImposibleModificarTicketsException lanza una excepcion
	 *                                            ImposibleModificarTicketsException
	 *                                            si el estado de contratacion no
	 *                                            esta en un estado valido
	 *
	 */

	public void crearTicketEmpleador(String locacion, int remuneracion, String jornada, String puesto,
			String experiencia, String estudios, Cliente cliente) throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		if (cliente.getTicket() != null)
			this.eliminarTicket();
		cliente.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));

	}

	/**
	 * Se encarga de registrar un nuevo empleado
	 * 
	 * @param nombreUsuario String con el nombre de usuario del empleado
	 * @param pass          String password generada para el empleado
	 * @param nombreReal    String Nombre real del empleado que se esta registrando
	 * @param apellido      String con el apellido del empleado que esta registrando
	 * @param telefono      String con el telefono del empleado
	 * @param edad          int con la edad del empleado que se esta registrando
	 * @return objeto Cliente que es el empleado registrado
	 * @throws NewRegisterException            se lanza para indicar que el nombre
	 *                                         de usuario ya esta en uso.
	 * @throws ImposibleCrearEmpleadoException Se lanza en caso de que
	 *                                         nombreUsuario, pass, nombreReal,
	 *                                         apellido, telefono sean nulos
	 * 
	 */

	public Cliente registroEmpleado(String nombreUsuario, String pass, String nombreReal, String apellido,
			String telefono, int edad) throws NewRegisterException, ImposibleCrearEmpleadoException
	{
		if (nombreUsuario == null || pass == null || nombreReal == null || telefono == null || apellido == null)
			throw new ImposibleCrearEmpleadoException(Mensajes.PARAMETROS_NULOS.getValor(), nombreUsuario, pass,
					nombreReal, apellido, telefono, edad);

		if (this.empleados.containsKey(nombreUsuario))
			throw new NewRegisterException(Mensajes.USUARIO_REPETIDO.getValor(), nombreUsuario);
		EmpleadoPretenso empleado = new EmpleadoPretenso(nombreUsuario, pass, nombreReal, apellido, telefono, edad);
		this.empleados.put(nombreUsuario, empleado);
		System.out.println(empleado + " registrado");
		return empleado;
	}

	/**
	 * Se encarga de registrar un nuevo empleador.
	 * 
	 * @param nombreUsuario String nombre de usuario para el empleador que se quiere
	 *                      registrar
	 * @param pass          String password para el empleador que se esta
	 *                      registrando
	 * @param nombreReal    String nombre real del empleador que se esta registrando
	 * @param telefono      String telefono del empleador que se esta registrando
	 * @param tipoPersona   String tipo de persona: Fisica o Juridica para el
	 *                      empleador que se esta registrando
	 * @param rubro         String rubro del empleador que se esta registrando
	 * @return Objeto Cliente con el empleador registrado
	 * @throws NewRegisterException             Se lanza en caso de que ya exista un
	 *                                          empleador registrado con el mismo
	 *                                          nombreUsuario.
	 * @throws ImposibleCrearEmpleadorException lanza una excepcion
	 *                                          ImposibleCrearEmpleadorException en
	 *                                          caso de que nombreUsuario, pass,
	 *                                          nombreReal, telefono, tipoPersona, o
	 *                                          rubro sean nulos o en caso de que el
	 *                                          tipo de persona no sea "JURIDICA" ni
	 *                                          "FISICA"o en caso de que rubro no
	 *                                          sea "SALUD" "COMERCIO LOCAL"
	 *                                          "COMERCIO INTERNACIONAL" (ver clase
	 *                                          Constantes) <br>
	 */

	public Cliente registroEmpleador(String nombreUsuario, String pass, String nombreReal, String telefono,
			String tipoPersona, String rubro) throws NewRegisterException, ImposibleCrearEmpleadorException
	{
		if (nombreUsuario == null || pass == null || nombreReal == null || telefono == null || tipoPersona == null
				|| rubro == null)
			throw new ImposibleCrearEmpleadorException(Mensajes.PARAMETROS_NULOS.getValor(), nombreUsuario, pass,
					nombreReal, telefono, tipoPersona, rubro);

		if (!tipoPersona.equals(Constantes.JURIDICA) && !tipoPersona.equals(Constantes.FISICA))
			throw new ImposibleCrearEmpleadorException(Mensajes.TIPO_PERSONA_DESCONOCIDO.getValor(), nombreUsuario,
					pass, nombreReal, telefono, tipoPersona, rubro);

		Empleador empleador = new Empleador(nombreUsuario, pass, nombreReal, telefono, tipoPersona, rubro);
		this.empleadores.put(nombreUsuario, empleador);
		return empleador;
	}

	/**
	 * Se encarga de realizar el inicio de sesion de un usuario en la aplicacion, ya
	 * sean empleados, empleadores o administradores Si el inicio de sesion es
	 * exitoso, el usuario quedara logeado en la aplicacion y el atributo tipo de
	 * usuario sera seteado: 0 para Empleado, 1 para Empleador, 2 para Administrador
	 * 
	 * @param nombreUsuario String: nombre de usuario de quien se quiere loguear
	 * @param pass          String: contrasena del usuario que se quiere loguear
	 * @return Se retorna un objeto Usuario con el objeto usuario
	 * @throws ContraException        si la contrasena no es correcta se lanza una
	 *                                excepcion del tipo ContraException
	 * @throws NombreUsuarioException si no se encontro un empleado, un empleador o
	 *                                un administrador con el nombreUsuario
	 *                                proporcionado, se lanza una excepcion
	 *                                NombreUsuarioException <b>Pre: </b> nombre de
	 *                                usuario y contrasena distintos de null <br>
	 * 
	 */

	public Usuario login(String nombreUsuario, String pass) throws ContraException, NombreUsuarioException
	{
		Usuario u = null;
		if (nombreUsuario.equals("admin") && pass.equals("admin"))
		{
			Usuario adm = new Admin();
			this.usuarioLogeado = adm;
			this.tipoUsuario = 2;

		}

		else if (this.empleados.containsKey(nombreUsuario))
		{
			u = this.empleados.get(nombreUsuario);

			this.chequeaPass(u, pass);
			this.usuarioLogeado = u;
			this.tipoUsuario = 0;

		} else if (this.empleadores.containsKey(nombreUsuario))
		{
			u = this.empleadores.get(nombreUsuario);

			this.chequeaPass(u, pass);
			this.usuarioLogeado = u;
			this.tipoUsuario = 1;
		} else
			throw new NombreUsuarioException(Mensajes.USUARIO_DESCONOCIDO.getValor(), null);
		return this.usuarioLogeado;

	}

	private void chequeaPass(Usuario u, String pass) throws ContraException
	{
		if (!u.getPassword().equals(pass))
			throw new ContraException(Mensajes.PASS_ERRONEO.getValor(), u, pass);
	}

	/**
	 * Busca la contratacion asociada a un Empleador especifico en la lista de
	 * contrataciones de la agencia.
	 * 
	 * @param empleador objeto Empleador de quien se quiere obtener la contratacion.
	 * @return objeto Usuario con la contratacion. <b>Pre: </b> empleador distinto
	 *         de null y registrado en el sistema<br>
	 * 
	 */

	public Cliente getContratacionEmpleador(Empleador empleador)
	{
		EmpleadoPretenso respuesta = null;
		int i = this.contrataciones.size() - 1;
		while (i >= 0 && this.contrataciones.get(i).getEmpleador() != empleador)
			i--;
		if (i >= 0)
			respuesta = this.contrataciones.get(i).getEmpleado();
		return respuesta;
	}

	public double getComisionUsuario(Cliente usuario)
	{
		return this.comisionesUsuarios.get(usuario);
	}

	/**
	 * Se encarga de cargar una instancia de la clase Agencia desde un archivo
	 * serializado
	 * 
	 * @param nombreArchivo String con el nombre del archivo
	 * @return retorna true o false en caso de exito o fracaso en la carga
	 * @throws IOException            Esta excepcion se lanza cuando ocurren
	 *                                problemas durante la operacion de lectura del
	 *                                archivo
	 * @throws ClassNotFoundException Esta excepcion se lanza si la clase AgenciaDTO
	 *                                no se encuentra en el part durante la
	 *                                deserializacion del objeto. <b>Pre: </b> El
	 *                                metodo debe recibir el nombre de un archivo
	 *                                distinto de null<br>
	 * 
	 */

	public boolean cargarAgencia(String nombreArchivo) throws IOException, ClassNotFoundException
	{
		boolean respuesta = false;
		if (this.persistencia != null)
		{
			this.persistencia.abrirInput(nombreArchivo);
			AgenciaDTO agenciaDTO = (AgenciaDTO) this.persistencia.leer();
			UtilPersistencia.agenciaFromAgenciaDTO(agenciaDTO);
			respuesta=true;
		}
		return respuesta;
	}

	/**
	 * Busca la contratacion asociada a un EmpleadoPretenso especifico en la lista
	 * de contrataciones de la agencia.
	 * 
	 * @param ep el Objeto EmpleadoPretenso de quien se quiere obtener la
	 *           contratacion
	 * @return objeto Cliente con el Empleador encontrado si no se encontro ninguna
	 *         contratacion que involucre al EmpleadoPretenso especificado. <b>Pre:
	 *         </b> empleado distinto de null y registrado en el sistema<br>
	 */

	public Cliente getContratacionEmpleadoPretenso(EmpleadoPretenso ep)
	{
		Empleador respuesta = null;
		int i = this.contrataciones.size() - 1;
		while (i >= 0 && this.contrataciones.get(i).getEmpleado() != ep)
			i--;
		if (i >= 0)
			respuesta = this.contrataciones.get(i).getEmpleador();
		return respuesta;
	}

	public int getTipoUsuario()
	{

		return this.tipoUsuario;
	}

	/**
	 * El metodo aplicaPromo se utiliza para seleccionar y beneficiar a un cliente
	 * especifico (ya sea un Empleador o un EmpleadoPretenso) Si
	 * promoPorListaDePostulantes es true, la promocion se basara en el tamano de
	 * las listas de postulantes de los Empleadores y EmpleadoPretenso. Si
	 * promoPorListaDePostulantes es false, se basara simplemente en la cantidad
	 * total de Empleadores y EmpleadoPretenso sin considerar las listas de
	 * postulantes
	 * 
	 * @param promoPorListaDePostulantes Este parametro es un booleano que controla
	 *                                   el tipo de promocion que se aplicara.
	 * @return objeto Cliente En resumen, el metodo aplicaPromo se utiliza para
	 *         seleccionar y beneficiar a un cliente especifico (ya sea un Empleador
	 *         o un EmpleadoPretenso) <b>Pre: </b> promoPorListaDePostulantes es
	 *         true o false <br>
	 * 
	 */

	public Cliente aplicaPromo(boolean promoPorListaDePostulantes)
	{
		Iterator clientes = null;
		int contadorEmpleador = 0;
		int contadorEmpleadoPretenso = 0;
		Cliente clienteBeneficiado = null;

		if (promoPorListaDePostulantes)
		{

			Iterator<Empleador> itEmpleadores = this.empleadores.values().iterator();
			while (itEmpleadores.hasNext())

			{
				Empleador empleador = itEmpleadores.next();
				if (empleador.getListaDePostulantes() != null)
					contadorEmpleador += empleador.getListaDePostulantes().size();
			}
			Iterator<EmpleadoPretenso> itEmpleados = this.empleados.values().iterator();
			while (itEmpleados.hasNext())
			{
				EmpleadoPretenso empleadoPretenso = itEmpleados.next();
				if (empleadoPretenso.getListaDePostulantes() != null)
					contadorEmpleadoPretenso += empleadoPretenso.getListaDePostulantes().size();
			}
		} else
		{

			contadorEmpleador = this.empleadores.size();
			contadorEmpleadoPretenso = this.empleados.size();
		}
		if (contadorEmpleador > contadorEmpleadoPretenso)
			clientes = this.empleadores.values().iterator();
		else
			clientes = this.empleados.values().iterator();

		int puntajeMaximo = Integer.MIN_VALUE;
		while (clientes.hasNext())
		{
			Cliente cl = (Cliente) clientes.next();
			if (cl.getPuntaje() > puntajeMaximo)
			{
				puntajeMaximo = cl.getPuntaje();
				clienteBeneficiado = cl;
			}
		}
		return clienteBeneficiado;

	}

	/**
	 * Se utiliza para cerrar la sesion de un usuario en la aplicacion. Despues de
	 * ejecutar el metodo, no habra un usuario logeado en la aplicacion <br>
	 * 
	 */

	public void cerrarSesion()
	{
		this.usuarioLogeado = null;
		this.tipoUsuario = -1;

	}

	public HashMap<String, EmpleadoPretenso> getEmpleados()
	{
		return empleados;
	}

	public void setEmpleados(HashMap<String, EmpleadoPretenso> empleados)
	{
		this.empleados = empleados;
	}

	public HashMap<String, Empleador> getEmpleadores()
	{
		return empleadores;
	}

	public void setEmpleadores(HashMap<String, Empleador> empleadores)
	{
		this.empleadores = empleadores;
	}

	public ArrayList<Contratacion> getCoincidencias()
	{
		return contrataciones;
	}

	public void setCoincidencias(ArrayList<Contratacion> coincidencias)
	{
		this.contrataciones = coincidencias;
	}

	public int getLimiteInferior()
	{
		return limiteInferior;
	}

	public int getLimiteSuperior()
	{
		return limiteSuperior;
	}

	public boolean isEstadoContratacion()
	{
		return estadoContratacion;
	}

	/**
	 * Elimina el ticlet del usuario logueado en <br>
	 * <b>pre:</b> Hay un cliente logueado en la aplicacion <br>
	 * 
	 * @throws ImposibleModificarTicketsException lanza una excepcion
	 *                                            ImposibleModificarTicketsException
	 *                                            si el estado de contratacion no
	 *                                            esta en un estado valido
	 */
	public void eliminarTicket() throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		Cliente cli = (Cliente) this.usuarioLogeado;
		cli.setTicket(null);
		cli.setPuntaje(cli.getPuntaje() - 1);
	}

	/**
	 * @return un String dependiendo del estado en que se encuentre la Agencia, estos pueden ser:<br>
	 *  Mensajes.AGENCIA_EN_CONTRATACION.getValor()<br> 
	 *  Mensajes.AGENCIA_EN_BUSQUEDA.getValor() 
	 */
	public String getEstado()
	{
		String cartel = null;
		if (this.estadoContratacion)
			cartel = Mensajes.AGENCIA_EN_CONTRATACION.getValor();
		else
			cartel = Mensajes.AGENCIA_EN_BUSQUEDA.getValor();
		return cartel;
	}

	public ArrayList<Contratacion> getContrataciones()
	{
		return contrataciones;
	}

	public void setContrataciones(ArrayList<Contratacion> contrataciones)
	{
		this.contrataciones = contrataciones;
	}

	public HashMap<Cliente, Double> getComisionesUsuarios()
	{
		return comisionesUsuarios;
	}

	public void setComisionesUsuarios(HashMap<Cliente, Double> comisionesUsuarios)
	{
		this.comisionesUsuarios = comisionesUsuarios;
	}

	public void setEstadoContratacion(boolean estadoContratacion)
	{
		this.estadoContratacion = estadoContratacion;
	}

	public IPersistencia getPersistencia()
	{
		return persistencia;
	}

	public void setPersistencia(IPersistencia persistencia)
	{
		this.persistencia = persistencia;
	}

}
