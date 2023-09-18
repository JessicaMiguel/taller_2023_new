package main;

import java.io.IOException;
import java.util.Iterator;

import controlador.Controlador;
import excepciones.ImposibleCrearEmpleadoException;
import excepciones.ImposibleCrearEmpleadorException;
import excepciones.ImposibleModificarTicketsException;
import excepciones.NewRegisterException;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Ticket;
import modeloNegocio.Agencia;
import persistencia.PersistenciaXML;
import util.Constantes;
import vista.IVista;

public class Main
{

	public static void main(String[] args)
	{
		Controlador controlador = new Controlador();// TODO Auto-generated method stub
		Agencia.getInstance().setPersistencia(new PersistenciaXML());
		try
		{
			Agencia.getInstance().cargarAgencia("Agencia.xml");
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void mainOLD(String[] args)
	{
		Controlador controlador = new Controlador();// TODO Auto-generated method stub

		Ticket t1 = new Ticket(Constantes.HOME_OFFICE, 30000, Constantes.JORNADA_COMPLETA, Constantes.JUNIOR, Constantes.EXP_NADA,
				Constantes.SECUNDARIOS);
		Ticket t2 = new Ticket(Constantes.HOME_OFFICE, 30000, Constantes.JORNADA_COMPLETA, Constantes.JUNIOR, Constantes.EXP_NADA,
				Constantes.SECUNDARIOS);
		System.out.println(t1.getComparacionTotal(t2));

		try
		{
			Agencia.getInstance().registroEmpleado("carlos", "qwerty", "Juan Carlos", "Gomez", "223-457678", 34);
			Agencia.getInstance().registroEmpleado("pepe", "qwerty", "Jose", "Garcia", "223-23443278", 54);
			Agencia.getInstance().registroEmpleado("pipo", "qwerty", "pipin", "pipo", "109280101", 54);

			Agencia.getInstance().registroEmpleador("toledo", "toledo", "Supermercado Toledo", "223-2434234",
					Constantes.JURIDICA, Constantes.COMERCIO_LOCAL);
			Agencia.getInstance().registroEmpleador("hpc", "hpc", "Hospital Privado de Comunidad", "11-123123131",
					Constantes.JURIDICA, Constantes.SALUD);
			Agencia.getInstance().getEmpleados().get("carlos");
			EmpleadoPretenso e = Agencia.getInstance().getEmpleados().get("carlos");

			Agencia.getInstance().crearTicketEmpleado(Constantes.HOME_OFFICE, 30000, Constantes.JORNADA_COMPLETA, Constantes.JUNIOR,
					Constantes.EXP_NADA, Constantes.SECUNDARIOS, e);
			e = Agencia.getInstance().getEmpleados().get("pepe");
			Agencia.getInstance().crearTicketEmpleado(Constantes.HOME_OFFICE, 30000, Constantes.JORNADA_COMPLETA, Constantes.SENIOR,
					Constantes.EXP_MUCHA, Constantes.TERCIARIOS, e);
			e = Agencia.getInstance().getEmpleados().get("pipo");
			Agencia.getInstance().crearTicketEmpleado(Constantes.INDISTINTO, 30000, Constantes.JORNADA_MEDIA, Constantes.SENIOR,
					Constantes.EXP_MUCHA, Constantes.TERCIARIOS, e);

		} catch (NewRegisterException | ImposibleCrearEmpleadoException | ImposibleCrearEmpleadorException
				| ImposibleModificarTicketsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator it = Agencia.getInstance().getIteratorEmpleadosPretensos();
		while (it.hasNext())
			System.out.println(it.next());
	}

}
