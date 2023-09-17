package main;

import java.util.Iterator;

import controlador.Controlador;
import excepciones.ImposibleCrearEmpleadoException;
import excepciones.ImposibleCrearEmpleadorException;
import excepciones.ImposibleModificarTicketsException;
import excepciones.NewRegisterException;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Ticket;
import modeloNegocio.Agencia;
import vista.IVista;

public class Main
{

	public static void main(String[] args)
	{
		Controlador controlador = new Controlador();// TODO Auto-generated method stub

		Ticket t1=new Ticket(IVista.HOME_OFFICE,30000,IVista.JORNADA_COMPLETA,IVista.JUNIOR,IVista.EXP_NADA,IVista.SECUNDARIOS);
		Ticket t2=new Ticket(IVista.HOME_OFFICE,30000,IVista.JORNADA_COMPLETA,IVista.JUNIOR,IVista.EXP_NADA,IVista.SECUNDARIOS);
		System.out.println(t1.getComparacionTotal(t2));

		try
		{
			Agencia.getInstance().registroEmpleado("carlos", "qwerty", "Juan Carlos", "Gomez", "223-457678", 34);
			Agencia.getInstance().registroEmpleado("pepe", "qwerty", "Jose", "Garcia", "223-23443278", 54);
			Agencia.getInstance().registroEmpleado("pipo", "qwerty", "pipin", "pipo", "109280101", 54);

			Agencia.getInstance().registroEmpleador("toledo","toledo" ,"Supermercado Toledo" , "223-2434234", IVista.JURIDICA, IVista.COMERCIO_LOCAL);
			Agencia.getInstance().registroEmpleador("hpc","hpc" ,"Hospital Privado de Comunidad" , "11-123123131", IVista.JURIDICA, IVista.SALUD);
			Agencia.getInstance().getEmpleados().get("carlos");
			EmpleadoPretenso e=Agencia.getInstance().getEmpleados().get("carlos");
			
			Agencia.getInstance().crearTicketEmpleado(IVista.HOME_OFFICE,30000,IVista.JORNADA_COMPLETA,IVista.JUNIOR,IVista.EXP_NADA,IVista.SECUNDARIOS,e);
			e=Agencia.getInstance().getEmpleados().get("pepe");
			Agencia.getInstance().crearTicketEmpleado(IVista.HOME_OFFICE,30000,IVista.JORNADA_COMPLETA,IVista.SENIOR,IVista.EXP_MUCHA,IVista.TERCIARIOS,e);
			e=Agencia.getInstance().getEmpleados().get("pipo");
			Agencia.getInstance().crearTicketEmpleado(IVista.INDISTINTO,30000,IVista.JORNADA_MEDIA,IVista.SENIOR,IVista.EXP_MUCHA,IVista.TERCIARIOS,e);
			
			
			
		} catch (NewRegisterException | ImposibleCrearEmpleadoException | ImposibleCrearEmpleadorException | ImposibleModificarTicketsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Iterator it=Agencia.getInstance().getIteratorEmpleadosPretensos();
		while(it.hasNext())
			System.out.println(it.next());
	}

}
