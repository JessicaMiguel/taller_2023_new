package persistencia;

import excepciones.LimiteInferiorRemuneracionInvalidaException;
import excepciones.LimiteSuperiorRemuneracionInvalidaException;
import modeloNegocio.Agencia;

public class UtilPersistencia
{
public static AgenciaDTO AgenciaDtoFromAgencia() 
{
AgenciaDTO agenciaDTO=new AgenciaDTO();
agenciaDTO.setComisionesUsuarios(Agencia.getInstance().getComisionesUsuarios());
agenciaDTO.setContrataciones(Agencia.getInstance().getContrataciones());
agenciaDTO.setEmpleadores(Agencia.getInstance().getEmpleadores());
agenciaDTO.setEmpleados(Agencia.getInstance().getEmpleados());
agenciaDTO.setEstadoContratacion(Agencia.getInstance().isEstadoContratacion());
agenciaDTO.setLimiteInferior(Agencia.getInstance().getLimiteInferior());
agenciaDTO.setLimiteSuperior(Agencia.getInstance().getLimiteSuperior());

return agenciaDTO;
}

public static void agenciaFromAgenciaDTO(AgenciaDTO agenciaDTO) 
{
	Agencia.getInstance().setComisionesUsuarios(agenciaDTO.getComisionesUsuarios());
	Agencia.getInstance().setContrataciones(agenciaDTO.getContrataciones());
	Agencia.getInstance().setEmpleadores(agenciaDTO.getEmpleadores());
	Agencia.getInstance().setEmpleados(agenciaDTO.getEmpleados());
	Agencia.getInstance().setEstadoContratacion(agenciaDTO.isEstadoContratacion());
	try
	{
		Agencia.getInstance().setLimitesRemuneracion(agenciaDTO.getLimiteInferior(),agenciaDTO.getLimiteSuperior());
	} catch (LimiteSuperiorRemuneracionInvalidaException e)
	{
		// TODO Auto-generated catch block
		System.out.println(e.getLimiteInferiorActual());
		System.out.println(e.getLimiteSuperiorActual());
		System.out.println(e.getLimiteSuperiorPretendido());
		
		e.printStackTrace();
	} catch (LimiteInferiorRemuneracionInvalidaException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
