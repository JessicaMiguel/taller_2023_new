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
import modeloDatos.Contratacion;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.Ticket;
import modeloDatos.Usuario;
import persistencia.AgenciaDTO;
import persistencia.IPersistencia;
import persistencia.UtilPersistencia;
import util.Constantes;
import util.Mensajes;

public class Agencia
{
	private static Agencia instance;
	private HashMap<String, EmpleadoPretenso> empleados = new HashMap<String, EmpleadoPretenso>();
	private HashMap<String, Empleador> empleadores = new HashMap<String, Empleador>();
	private int limiteInferior;
	private int limiteSuperior;
	private Usuario usuarioLogeado = null;
	private int tipoUsuario = -1;
	private ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
	private boolean estadoContratacion = false;
	private HashMap<Usuario, Double> comisionesUsuarios = new HashMap<Usuario, Double>();
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
	 * Dependiendo del valor del atributo estadoContratacion, este método realiza
	 * diferentes acciones: Si estadoContratacion es verdadero, se buscan matcheos
	 * entre empleadores y empleados para trabajos disponibles. También, se penaliza
	 * a los empleadores que tienen un tocket asociado pero no fueron elegidos en
	 * una ronda de contratación, y "limpia" o elimina todas las conexiones entre
	 * candidatos y puestos de trabajo después de una ronda de contratación. Esto
	 * prepara el sistema para una nueva ronda de contratación donde los candidatos
	 * pueden postularse nuevamente a nuevos puestos. Si estadoContratacion es
	 * falso, se generan nuevos postulantes para los trabajos disponibles y se
	 * calculan los premios o castigos basados en el resultado de la ronda anterior.
	 * Al final del método, se invierte el valor de estadoContratacion, lo que
	 * significa que se alternará entre rondas de contratación y generación de
	 * candidatos en llamadas sucesivas a este método.
	 * 
	 * <b>Post: </b> Dependiendo del estadoContratacion se controla el flujo de una
	 * ronda de contratación y la generación de candidatos <br>
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

	/**
	 * Tiene como objetivo eliminar las listas de postulantes de los empleadores y
	 * empleados. El propósito de este método es "limpiar" las listas de postulantes
	 * de los empleadores y empleados pretensos, lo que implica eliminar todas las
	 * conexiones entre candidatos y puestos de trabajo después de una ronda de
	 * contratación 
	 * <b>Pre: </b> Debe haber objetos válidos y registrados en las colecciones<br>
	 * <b>Post: </b> Prepara el sistema para una nueva ronda de
	 * contratación donde los candidatos pueden postularse nuevamente a nuevos
	 * puestos.<br>
	 * 
	 */

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
	 * Este método penaliza a los empleadores que tienen un ticket asociado pero no
	 * fueron elegidos en una ronda de contratación.
	 * 
	 * <b>Pre: </b> Debe haber objetos válidos y registrados en las colecciones<br>
	 * empleadores y empleados. <br>
	 * <b>Post: </b> Se penaliza al empleador reduciendo su puntaje en 20
	 * unidades<br>
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
	 * Este método otorga premios y castigos a los usuarios que participan en el
	 * proceso de selección. <b>Pre: </b>Las listas de postulantes están ordenadas
	 * <br>
	 * <b>Post: </b> Los empleadores que tienen candidatos en su lista de
	 * postulantes reciben un premio de 5 puntos para su candidato principal y un
	 * castigo de -5 puntos para el último candidato de la lista. Los empleados
	 * pretensos que tienen postulantes en su lista reciben un premio de 10 puntos
	 * para el candidato en el puesto 1.<br>
	 */

	public void calculaPremiosCastigosAsignaciones()
	{
		Iterator<Empleador> itEmpleadores = this.empleadores.values().iterator();

		while (itEmpleadores.hasNext())
		{
			Empleador empleador = itEmpleadores.next();
			if (empleador.getListaDePostulantes() != null && !empleador.getListaDePostulantes().isEmpty())
			{
				ArrayList<UsuarioPuntaje> ups = empleador.getListaDePostulantes();
				ups.get(0).getUsuario().setPuntaje(ups.get(0).getUsuario().getPuntaje() + 5);
				ups.get(ups.size() - 1).getUsuario().setPuntaje(ups.get(0).getUsuario().getPuntaje() - 5);

			}
		}

		Iterator<EmpleadoPretenso> itEmpleados = this.empleados.values().iterator();

		while (itEmpleados.hasNext())
		{
			EmpleadoPretenso empleado = itEmpleados.next();
			if (empleado.getListaDePostulantes() != null && !empleado.getListaDePostulantes().isEmpty())
			{
				ArrayList<UsuarioPuntaje> ups = empleado.getListaDePostulantes();
				ups.get(0).getUsuario().setPuntaje(ups.get(0).getUsuario().getPuntaje() + 10);
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

				Usuario empleado = empleador.getCandidato();

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
	 * Está relacionado con la ocurrencia de un "match" entre un empleador y un
	 * empleado pretenso, lo que implica que un empleado potencial ha sido
	 * seleccionado por un empleador. Realiza ajustes en los puntajes y las
	 * comisiones, y marca a ambos usuarios como no disponibles para futuros matches
	 * o contrataciones. <b>Pre: </b> Empleado y empleador deben ser válidos y estar
	 * registrados y logueados en el sistema <br>
	 * <b>Post: </b> Crea una nueva instancia de Contratacion que representa la
	 * contratación o emparejamiento entre el empleador y el empleado. Incrementa el
	 * puntaje del empleado en 10 puntos Incrementa el puntaje del empleador en 50
	 * puntos Calcula las comisiones para el empleado y el empleador en función del
	 * Ticket asociado al empleador. Empleado y empleador ya no están disponibles
	 * para futuros emparejamientos o contrataciones.<br>
	 * 
	 * @param empleador: Objeto empleador con quien el empleado pretenso hace match
	 * @param empleado:  Objeto empleado con quien el empleador hace match
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
	 * Este método se encarga de calcular y asignar listas de postulantes a los
	 * Empleadores y EmpleadoPretenso en función de sus Ticket y las comparaciones
	 * de puntajes entre ellos. <b>Pre: </b> Listas de empleados y empleadores con
	 * elementos válidos <br>
	 * <b>Post: </b>Establece la lista de EmpleadoPretenso ordenados de mayor a
	 * menor como la lista de postulantes para el Empleador Establece una lista de
	 * Empleadores ordenada por puntaje de mayor a menor de compatibilidad y la
	 * establece como la lista de postulantes para ese EmpleadoPretenso.<br>
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
				ArrayList<UsuarioPuntaje> asignaciones = new ArrayList<UsuarioPuntaje>();
				while (itEmpleados.hasNext())
				{
					EmpleadoPretenso empleado = itEmpleados.next();
					if (empleado.getTicket() != null)
					{
						double puntaje = empleador.getTicket().getComparacionTotal(empleado.getTicket());
						UsuarioPuntaje up = new UsuarioPuntaje(puntaje, empleado);
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
				ArrayList<UsuarioPuntaje> asignaciones = new ArrayList<UsuarioPuntaje>();
				while (itEmpleadores.hasNext())
				{
					Empleador empleador = itEmpleadores.next();
					if (empleador.getTicket() != null)
					{
						double puntaje = empleador.getTicket().getComparacionTotal(empleado.getTicket());
						UsuarioPuntaje up = new UsuarioPuntaje(puntaje, empleador);
						asignaciones.add(up);

					}

				}
				Collections.sort(asignaciones);
				empleado.setListaDePostulantes(asignaciones);
			}
		}

	}

	/**
	 * Tiene como objetivo guardar la información de una agencia en un archivo
	 * utilizando un mecanismo de persistencia. Este archivo contendrá la
	 * información necesaria para restaurar la agencia en un estado similar en el
	 * futuro si es necesario. <b>Pre: </b> Nombre de archivo válido <br>
	 * <b>Post: </b> Se guarda el estado de la agencia en el archivo <br>
	 * 
	 * @param nombreArchivo String, con el nombre del archivo donde se almacenará la
	 *                      agencia
	 * @throws IOException Se lanza en caso de error de entrada/salida
	 */

	public void guardarAgencia(String nombreArchivo) throws IOException
	{
		if (this.persistencia != null)
		{
			persistencia.abrirOutput(nombreArchivo);
			persistencia.escribir(UtilPersistencia.AgenciaDtoFromAgencia());
			persistencia.cerrarOutput();
		}
	}

	/**
	 * Se encarga de crear un nuevo ticket para un empleado con los datos enviados
	 * por parámetros
	 * 
	 * @param locacion     String con la locacion del empleado
	 * @param remuneracion int con la remuneracion pretendida por el empleado
	 * @param jornada      String con la jornada de preferencia del empleado
	 * @param puesto       String tipo de puesto al que aspira el empleado
	 * @param experiencia  String experiencia del empleado
	 * @param estudios     String estudios que posee el empleado
	 * @param usuario      objeto Usuario del ticket
	 * @throws ImposibleModificarTicketsException lanza una excepción
	 *                                            ImposibleModificarTicketsException
	 *                                            si el estado de contratación no
	 *                                            está en un estado válido <b>Pre:
	 *                                            </b> La agencia debe estar en un
	 *                                            estado en el que se permita la
	 *                                            creación de tickets El usuario
	 *                                            debe ser un empleado (no un
	 *                                            empleador) y no tener un ticket
	 *                                            activo en ese momento. Parámetros
	 *                                            válidos <br>
	 *                                            <b>Post: </b> El usuario tendrá un
	 *                                            nuevo ticket asociado con la
	 *                                            información proporcionada. <br>
	 *
	 */

	public void crearTicketEmpleado(String locacion, int remuneracion, String jornada, String puesto,
			String experiencia, String estudios, Usuario usuario) throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		if (usuario.getTicket() != null)
			this.eliminarTicket();
		usuario.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));
	}

	/**
	 * Se encarga de crear un nuevo ticket para un empleador
	 * 
	 * @param locacion     String locacion de la oferta del empleador
	 * @param remuneracion int remuneración ofrecida por el empleador
	 * @param jornada      String jornada del puesto de trabajo que abre el
	 *                     empleador
	 * @param puesto       String tipo de puesto de la posición
	 * @param experiencia  String experiencia requerida para el puesto
	 * @param estudios     String estudios requeridos para el puesto
	 * @param usuario      objeto Usuario a quien se le creará el ticket
	 * @throws ImposibleModificarTicketsException lanza una excepción
	 *                                            ImposibleModificarTicketsException
	 *                                            si el estado de contratación no
	 *                                            está en un estado válido <b>Pre:
	 *                                            </b> La agencia debe estar en un
	 *                                            estado en el que se permita la
	 *                                            creación de tickets El usuario
	 *                                            debe ser un empleador (no un
	 *                                            empleado) y no tener un ticket
	 *                                            activo en ese momento. Parámetros
	 *                                            válidos <br>
	 *                                            <b>Post: </b> El usuario
	 *                                            (empleador) tendrá un nuevo ticket
	 *                                            asociado con la información
	 *                                            proporcionada.
	 *
	 */

	public void crearTicketEmpleador(String locacion, int remuneracion, String jornada, String puesto,
			String experiencia, String estudios, Usuario usuario) throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		if (usuario.getTicket() != null)
			this.eliminarTicket();
		usuario.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));

	}

	/**
	 * Se encarga de registrar un nuevo empleado
	 * 
	 * @param nombreUsuario String con el nombre de usuario del empleado
	 * @param pass          String password generada para el empleado
	 * @param nombreReal    String Nombre real del empleado que se está registrando
	 * @param apellido      String con el apellido del empleado que está registrando
	 * @param telefono      String con el telefono del empleado
	 * @param edad          int con la edad del empleado que se está registrando
	 * @return objeto Usuario que es el empleado registrado
	 * @throws NewRegisterException            se lanza para indicar que el nombre
	 *                                         de usuario ya está en uso.
	 * @throws ImposibleCrearEmpleadoException Se lanza en caso de que
	 *                                         nombreUsuario, pass, nombreReal,
	 *                                         apellido, telefono sean nulos
	 *                                         <b>Post: </b> Se registrará un nuevo
	 *                                         empleado en el sistema <br>
	 */

	public Usuario registroEmpleado(String nombreUsuario, String pass, String nombreReal, String apellido,
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
	 * @param pass          String password para el empleador que se está
	 *                      registrando
	 * @param nombreReal    String nombre real del empleador que se está registrando
	 * @param telefono      String telefono del empleador que se está registrando
	 * @param tipoPersona   String tipo de persona: Fisica o Juridica para el
	 *                      empleador que se está registrando
	 * @param rubro         String rubro del empleador que se está registrando
	 * @return Objeto usuario con el empleador registrado
	 * @throws NewRegisterException             Se lanza en caso de que ya exista un
	 *                                          empleador registrado con el mismo
	 *                                          nombreUsuario.
	 * @throws ImposibleCrearEmpleadorException lanza una excepción
	 *                                          ImposibleCrearEmpleadorException en
	 *                                          caso de que nombreUsuario, pass,
	 *                                          nombreReal, telefono, tipoPersona, o
	 *                                          rubro sean nulos o en caso de que el
	 *                                          tipo de persona no sea "JURIDICA" ni
	 *                                          "FISICA" <b>Post: </b> Se registrará
	 *                                          un nuevo empleador en el sistema
	 *                                          <br>
	 */

	public Usuario registroEmpleador(String nombreUsuario, String pass, String nombreReal, String telefono,
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
	 * Se encarga de realizar el inicio de sesión de un usuario en la aplicación, ya sean empleados, empleadores o administradores
	 * @param nombreUsuario String: nombre de usuario de quien se quiere loguear
	 * @param pass String: contraseña del usuario que se quiere loguear
	 * @return Se retorna un objeto Usuario con el objeto usuario
	 * @throws ContraException si la contraseña no es correcta se lanza una excepción del tipo ContraException
	 * @throws NombreUsuarioException si no se encontró un empleado, un empleador o un administrador con el nombreUsuario proporcionado, 
	 * se lanza una excepción NombreUsuarioException
	 * <b>Pre: </b> nombre de usuario y contraseña distintos de null <br>
	 * <b>Post: </b> Si el inicio de sesión es exitoso, el usuario quedará logeado en la aplicación  <br>
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
	 * Busca la contratación asociada a un Empleador específico en la lista de contrataciones de la agencia.
	 * 
	 * @param empleador objeto Empleador de quien se quiere obtener la contratación.
	 * @return objeto Usuario con la contratación.
	 * <b>Pre: </b> empleador distinto de null <br>
	 * <b>Post: </b> Se obtiene la contratación  <br>
	 */

	public Usuario getContratacionEmpleador(Empleador empleador)
	{
		EmpleadoPretenso respuesta = null;
		int i = this.contrataciones.size() - 1;
		while (i >= 0 && this.contrataciones.get(i).getEmpleador() != empleador)
			i--;
		if (i >= 0)
			respuesta = this.contrataciones.get(i).getEmpleado();
		return respuesta;
	}

	public double getComisionUsuario(Usuario usuario)
	{
		return this.comisionesUsuarios.get(usuario);
	}
	
	/**
	 * Se encarga de cargar una instancia de la clase Agencia desde un archivo serializado 
	 * @param nombreArchivo String con el nombre del archivo
	 * @throws IOException  Esta excepción se lanza cuando ocurren problemas durante la operación de lectura del archivo
	 * @throws ClassNotFoundException  Esta excepción se lanza si la clase AgenciaDTO no se encuentra en el parh durante la deserialización del objeto.
	 * <b>Pre: </b> El método debe recibir el nombre de un archivo válido  <br>
	 * <b>Post: </b>La instancia de la clase Agencia se actualizará con los datos cargados desde el archivo especificado  <br>
	 */

	public void cargarAgencia(String nombreArchivo) throws IOException, ClassNotFoundException
	{
		this.persistencia.abrirInput(nombreArchivo);
		AgenciaDTO agenciaDTO = (AgenciaDTO) this.persistencia.leer();
		UtilPersistencia.agenciaFromAgenciaDTO(agenciaDTO);
	}
	/**
	 * Busca la contratación asociada a un EmpleadoPretenso específico en la lista de contrataciones de la agencia.
	 * @param ep Objeto EmpleadoPretenso de quien se quiere obtener la contratación
	 * @return objeto usuario con el Empleador encontrado si no se encontró ninguna contratación que 
	 * involucre al EmpleadoPretenso especificado.
	 * <b>Pre: </b> empleado distinto de null <br>
	 * <b>Post: </b> Se obtiene la contratación  <br>
	 */

	public Usuario getContratacionEmpleadoPretenso(EmpleadoPretenso ep)
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
	 * El método aplicaPromo se utiliza para seleccionar y beneficiar a un cliente específico (ya sea un Empleador o un EmpleadoPretenso)
	 * @param promoPorListaDePostulantes Este parámetro es un booleano que controla el tipo de promoción que se aplicará.
	 * @return objeto Usuario En resumen, el método aplicaPromo se utiliza para seleccionar y beneficiar a un cliente específico 
	 * (ya sea un Empleador o un EmpleadoPretenso)
	 * <b>Pre: </b> promoPorListaDePostulantes es true o false <br>
	 * <b>Post: </b>Si promoPorListaDePostulantes es true, la promoción se basará en el tamaño de las listas de postulantes de los Empleadores y EmpleadoPretenso.  
	 * Si promoPorListaDePostulantes es false, se basará simplemente en la cantidad total de Empleadores y EmpleadoPretenso sin considerar las listas de postulantes
	 * <br>
	 */

	public Usuario aplicaPromo(boolean promoPorListaDePostulantes)
	{
		Iterator clientes = null;
		int contadorEmpleador = 0;
		int contadorEmpleadoPretenso = 0;
		Usuario clienteBeneficiado = null;

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
			Usuario cl = (Usuario) clientes.next();
			if (cl.getPuntaje() > puntajeMaximo)
			{
				puntajeMaximo = cl.getPuntaje();
				clienteBeneficiado = cl;
			}
		}
		return clienteBeneficiado;

	}
	/**
	 * Se utiliza para cerrar la sesión de un usuario en la aplicación.
	 * <b>Post: </b> Después de ejecutar el método, no habrá un usuario logeado en la aplicación  <br>
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

	public void eliminarTicket() throws ImposibleModificarTicketsException
	{
		if (this.estadoContratacion)
			throw new ImposibleModificarTicketsException();
		this.usuarioLogeado.setTicket(null);
		this.usuarioLogeado.setPuntaje(this.usuarioLogeado.getPuntaje() - 1);
	}

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

	public HashMap<Usuario, Double> getComisionesUsuarios()
	{
		return comisionesUsuarios;
	}

	public void setComisionesUsuarios(HashMap<Usuario, Double> comisionesUsuarios)
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
