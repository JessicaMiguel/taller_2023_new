package modeloNegocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import excepciones.ContraException;
import excepciones.ImposibleCrearEmpleadoException;
import excepciones.ImposibleCrearEmpleadorException;
import excepciones.ImposibleModificarTicketsException;
import excepciones.NewRegisterException;
import excepciones.NombreUsuarioException;
import modeloDatos.Admin;
import modeloDatos.Contratacion;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.Ticket;
import modeloDatos.Usuario;
import util.Mensajes;
import vista.IVista;

public class Agencia
{
    private static Agencia instance;

    private HashMap<String, EmpleadoPretenso> empleados = new HashMap<String, EmpleadoPretenso>();
    private HashMap<String, Empleador> empleadores = new HashMap<String, Empleador>();

    private int v1;
    private int v2;
    private Usuario usuarioLogeado = null;
    private int tipoUsuario = -1;
    private ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
    private boolean estadoContratacion = false;
    private HashMap<Usuario, Double> comisionesUsuarios = new HashMap<Usuario, Double>();

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

    public void setV2(int limiteSuperior)
    {
	this.v2 = limiteSuperior;
    }

    public void setV1(int limiteInferior)
    {
	this.v1 = limiteInferior;
    }

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
	    noElegidos.get(i).setPuntaje(noElegidos.get(i).getPuntaje() - 20);
    }

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

    public void match(Empleador empleador, EmpleadoPretenso empleado)
    {
	System.out.println("HAY MATCH ENTRE " + empleador + "    y    " + empleado);
	this.contrataciones.add(new Contratacion(empleador, empleado));
	empleado.setPuntaje(empleado.getPuntaje() + 10);
	empleador.setPuntaje(empleador.getPuntaje() + 50);
	double sueldo = empleador.getTicket().getRemuneracion();
	this.comisionesUsuarios.put(empleado, empleado.calculaComision(sueldo));
	this.comisionesUsuarios.put(empleador, empleador.calculaComision(sueldo));
	

	empleado.setTicket(null);
	empleador.setTicket(null);

    }

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

    public void guardarAgencia(String nombreArchivo)
    {
	// TODO Auto-generated method stub

    }

    public void crearTicketEmpleado(String locacion, int remuneracion, String jornada, String puesto,
	    String experiencia, String estudios, Usuario usuario) throws ImposibleModificarTicketsException
    {
	if (this.estadoContratacion)
	    throw new ImposibleModificarTicketsException();
	if (usuario.getTicket() != null)
	    this.eliminarTicket();
	usuario.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));
    }

    public void crearTicketEmpleador(String locacion, int remuneracion, String jornada, String puesto,
	    String experiencia, String estudios, Usuario usuario) throws ImposibleModificarTicketsException
    {
	if (this.estadoContratacion)
	    throw new ImposibleModificarTicketsException();
	if (usuario.getTicket() != null)
	    this.eliminarTicket();
	usuario.setTicket(new Ticket(locacion, remuneracion, jornada, puesto, experiencia, estudios));

    }

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

    public Usuario registroEmpleador(String nombreUsuario, String pass, String nombreReal, String telefono,
	    String tipoPersona, String rubro) throws NewRegisterException, ImposibleCrearEmpleadorException
    {
	if (nombreUsuario == null || pass == null || nombreReal == null || telefono == null || tipoPersona == null
		|| rubro == null)
	    throw new ImposibleCrearEmpleadorException(Mensajes.PARAMETROS_NULOS.getValor(), nombreUsuario, pass,
		    nombreReal, telefono, tipoPersona, rubro);

	if (!tipoPersona.equals(IVista.JURIDICA) && !tipoPersona.equals(IVista.FISICA))
	    throw new ImposibleCrearEmpleadorException(Mensajes.TIPO_PERSONA_DESCONOCIDO.getValor(), nombreUsuario,
		    pass, nombreReal, telefono, tipoPersona, rubro);

	if (this.empleadores.containsKey(nombreUsuario))
	    throw new NewRegisterException(Mensajes.USUARIO_REPETIDO.getValor(), nombreUsuario);

	Empleador empleador = new Empleador(nombreUsuario, pass, nombreReal, telefono, tipoPersona, rubro);
	this.empleadores.put(nombreUsuario, empleador);
	return empleador;
    }

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
	    throw new NombreUsuarioException(Mensajes.USUARIO_DESCONOCIDO.getValor(), nombreUsuario);
	return this.usuarioLogeado;

    }

    private void chequeaPass(Usuario u, String pass) throws ContraException
    {
	if (!u.getPassword().equals(pass))
	    throw new ContraException(Mensajes.PASS_ERRONEO.getValor(), u, pass);
    }

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
	// TODO Auto-generated method stub
	return this.comisionesUsuarios.get(usuario);
    }

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

    public int getV1()
    {
	return v1;
    }

    public int getV2()
    {
	return v2;
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

}
