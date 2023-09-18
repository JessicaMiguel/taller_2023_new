package vista;

import modeloDatos.Usuario;

public interface IVista
{
   
    public String getPassword();

    public String getUsserName();

    public String getRegPassword();

    public String getRegUsserName();

    public void actualizar(int tipoUsuario, Usuario usuario);

    public String getTipoUsuario();

    public String getTipoPersona();

    public String getRubro();

    public String getNombreReal();

    public String getApellido();

    public String getTelefono();

    public String getConfirmPassword();

    public int getEdad();

    public String getLocacion();

    public String getPuesto();

    public String getExperiencia();

    public String getEstudios();

    public String getJornada();

    public int getRemuneracion();

    public void actualizaCliente();

    public int getLimiteInferior();

    public int getLimiteSuperior();

    public boolean isPorTicket();

    public Usuario getCandidato();

}
