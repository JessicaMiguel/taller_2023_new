package vista;

import modeloDatos.Usuario;

public interface IVista
{
    // Declaracion de constantes strings
    static final String LOGIN = "LOGIN";
    static final String REGISTRAR = "REGISTRAR";
    static final String NOMBRE_USUARIO = "NOMBRE_USUARIO";
    static final String PASSWORD = "PASSWORD";
    static final String CONFIRM_PASSWORD = "CONFIRM_PASSWORD";

    static final String REG_BUTTON_REGISTRAR = "REG_BUTTON_REGISTRAR";
    static final String REG_BUTTON_CANCELAR = "REG_BUTTON_CANCELAR";

    static final String REG_REAL_NAME = "REG_REAL_NAME";
    static final String REG_USSER_NAME = "REG_USSER_NAME";
    static final String REG_APELLIDO = "REG_APELLIDO";
    static final String REG_TELEFONO = "REG_TELEFONO";
    static final String REG_EDAD = "REG_EDAD";
    static final String REG_EMAIL = "REG_EMAIL";
    static final String REG_PASSWORD = "REG_PASSWORD";
    static final String REG_CONFIRM_PASSWORD = "REG_CONFIRM_PASSWORD";
    static final String REG_RADIO_JURIDICA = "REG_RADIO_JURIDICA";
    static final String REG_RADIO_FISICA = "REG_RADIO_FISICA";
    static final String REG_RADIO_SALUD = "REG_RADIO_SALUD";
    static final String REG_RADIO_COMERCIO_INTERNACIONAL = "REG_RADIO_COMERCIO_INTERNACIONAL";
    static final String REG_RADIO_COMERCIO_LOCAL = "REG_RADIO_COMERCIO_LOCAL";

    static final String EMPLEADOR = "EMPLEADOR";
    static final String EMPLEADO = "EMPLEADO";
    static final String ADMINISTRADOR = "ADMINISTRADOR";
    static final String NO_LOGEADO = "NO_LOGEADO";

    static final String FISICA = "FISICA";
    static final String JURIDICA = "JURIDICA";
    static final String COMERCIO_INTERNACIONAL = "COMERCIO INTERNACIONAL";
    static final String COMERCIO_LOCAL = "COMERCIO LOCAL";
    static final String SALUD = "SALUD";

    static final String SELECCIONAR_CANDIDATO = "SELECCIONAR_CANDIDATO";
    static final String NUEVOTICKET = "NUEVOTICKET";
    static final String CONFIRMARNUEVOTICKET = "CONFIRMARNUEVOTICKET";

    static final String PRESENCIAL = "Presencial";
    static final String HOME_OFFICE = "Home Office";
    static final String INDISTINTO = "Indistinto";

    static final String PRIMARIOS = "Primario";
    static final String SECUNDARIOS = "Secundario";
    static final String TERCIARIOS = "Terciario";

    static final String EXP_NADA = "ExperienciaNada";
    static final String EXP_MEDIA = "ExperienciaMedia";
    static final String EXP_MUCHA = "ExperienciaMucha";

    static final String JORNADA_MEDIA = "CargaMedia";
    static final String JORNADA_COMPLETA = "CargaCompleta";
    static final String JORNADA_EXTENDIDA = "CargaExtendida";

    static final String JUNIOR = "Junior";
    static final String SENIOR = "Senior";
    static final String MANAGMENT = "Managment";

    static final String MODIFICAR_VALORES = "MODIFICAR_VALORES";
    static final String GATILLAR = "GATILLAR";
    static final String APLICAR_PROMO = "APLICAR_PROMO";
    static final String CHECK_BOX_LISTA_POSTULANTES = "CHECK_BOX_LISTA_POSTULANTES";
    static final String ELIMINAR_TICKET="ELIMINAR_TICKET";

    static final String CERRARSESION = "CERRARSESION";

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
