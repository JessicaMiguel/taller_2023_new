package excepciones;

public class NewRegisterException extends Exception
{
    private String nombreUsuarioPretendido;

    public NewRegisterException(String arg, String nombreUsuarioPretendido)
    {
	super(arg);
	this.nombreUsuarioPretendido=nombreUsuarioPretendido;
    }

    public String getNombreUsuarioPretendido()
    {
        return nombreUsuarioPretendido;
    }
    
    
}
