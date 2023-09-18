package persistencia;

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
agenciaDTO.setV1(Agencia.getInstance().getV1());
agenciaDTO.setV2(Agencia.getInstance().getV2());

return agenciaDTO;
}

public static void agenciaFromAgenciaDTO(AgenciaDTO agenciaDTO) 
{
	Agencia.getInstance().setComisionesUsuarios(agenciaDTO.getComisionesUsuarios());
	Agencia.getInstance().setContrataciones(agenciaDTO.getContrataciones());
	Agencia.getInstance().setEmpleadores(agenciaDTO.getEmpleadores());
	Agencia.getInstance().setEmpleados(agenciaDTO.getEmpleados());
	Agencia.getInstance().setEstadoContratacion(agenciaDTO.isEstadoContratacion());
	Agencia.getInstance().setV1(agenciaDTO.getV1());
	Agencia.getInstance().setV2(agenciaDTO.getV2());

}
}
