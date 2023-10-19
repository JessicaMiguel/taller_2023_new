package util;

public enum Mensajes
{
    USUARIO_REPETIDO("Usuario repetido"), PASS_ERRONEO("Password incorrecto"),
    USUARIO_DESCONOCIDO("Usuario inexistente"), PARAMETROS_NULOS("Algun parametro requerido es null"),
    RUBRO_DESCONOCIDO("Rubro desconocido"), TIPO_PERSONA_DESCONOCIDO("Tipo de Persona desconocida"),
    ERROR_AGENCIA_EN_CONTRATACION("No se pueden modificar Tickets ahora"),AGENCIA_EN_CONTRATACION("La agencia se encuentra en estado de contratacion, se generaron listas de postulantes"),
    AGENCIA_EN_BUSQUEDA("Agencia en estado de busqueda Laboral, se puden crear y eliminar Tickets"), LIMITE_REMUNERACION_NEGATIVO("Limite de remuneracion negativo"), LIMITE_REMUNERACION_INVALIDO("Los limites de remuneracion son inconsistentes "),PASS_NO_COINCIDE("La contrasena y su confirmacion no coinciden"),SIN_TICKET("Sin Ticket Creado");
	

    private String valor;

    private Mensajes(String valor)
    {
	this.valor = valor;
    }

    public String getValor()
    {
	return valor;
    }

    public void setValor(String valor)
    {
	this.valor = valor;
    }

}
