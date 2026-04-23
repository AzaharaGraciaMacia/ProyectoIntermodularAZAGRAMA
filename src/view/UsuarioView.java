package view;

import dao.UsuarioDAO;
import model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Vista de consola para la gestión de usuarios.
 */
public class UsuarioView {

    /**
     * Muestra una tabla con los usuarios
     *
     * @param usuarios Lista de usuarios a mostrar
     */
    public void mostrarUsuarios(List<Usuario> usuarios) {
        System.out.println("Lista de Usuarios:\n");

        ConsolaView.mostrar(Usuario.CABECERA_LISTADO);

        usuarios.forEach(System.out::println);
    }

    /**
     * Muestra una tabla con los usuarios normales
     *
     * @param usuariosNormales Lista de usuarios normales a mostrar
     */
    public void mostrarUsuariosNormales(List<UsuarioNormal> usuariosNormales) {
        System.out.println("Lista de Usuarios Normales:\n");

        ConsolaView.mostrar(UsuarioNormal.CABECERA_LISTADO);

        usuariosNormales.forEach(System.out::println);
    }

    /**
     * Muestra una tabla con los administradores
     *
     * @param administradores Lista de recursos a mostrar
     */
    public void mostrarAdministradores(List<Administrador> administradores) {
        System.out.println("Lista de Administradores:\n");

        ConsolaView.mostrar(Administrador.CABECERA_LISTADO);

        administradores.forEach(System.out::println);
    }

    /**
     * Crea un objeto {@link Usuario} a partir de la lectura de datos por consola. Dependiendo del tipo de usuario llama a crear también sus correspondientes datos.
     *
     * @return objeto usuario
     */
    public Usuario crearUsuario() throws SQLException {
        boolean entradaValida = false;
        //comprobamos que no exista un usuario con el mismo DNI
        String dni="";

        while (!entradaValida) {
            dni = ConsolaView.leerTexto("Introduce el DNI:").toUpperCase();
            if(ConsolaView.comprobarDNI(dni)) {
                try {
                    Usuario usuarioDuplicado = UsuarioDAO.getInstance().getUsuarioByDni(dni);
                    if (usuarioDuplicado == null)
                        entradaValida = true;
                    else
                        System.out.println("Error: el usuario ya existe.");
                } catch (SQLException e) {
                    throw new RuntimeException(e); //Lanzamos el error en forma de unchecked para manejarlo después
                }
            } else {System.out.println("Error: DNI incorrecto.");}
        }
        String nombre = ConsolaView.leerTexto("Introduce el nombre completo:");
        String email = null;
        boolean emailValido = false;
        while (!emailValido){
            String emailAportado = ConsolaView.leerTexto("Introduce el email:");
            if (!ConsolaView.validarEmail(emailAportado)) {
                System.out.println("Error: email incorrecto.");
            } else {
                email = emailAportado;
                emailValido = true;
            }
        }
        String contrasena = ConsolaView.leerTexto("Introduce la contraseña:");
        TipoUsuario tipoUsuario = null;
        boolean tipoValido = false;
        while (!tipoValido) {
            String respuestaTipo = ConsolaView.leerTexto("Introduce el tipo de usuario (Normal o Administrador): ").toLowerCase();
            tipoUsuario = ConsolaView.ComprobarTipoUsuario(respuestaTipo);
            if (tipoUsuario != null)
                tipoValido = true;
            else
                System.out.println("Tipo no válido.");
        }
        Date fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            String fecha = ConsolaView.leerTexto("Introduce la fecha de nacimiento (dd/MM/yyyy):");
            fechaNacimiento = ConsolaView.validarYFormatearFecha(fecha);
            if (fechaNacimiento != null)
                fechaValida = true;
            else
                System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
        }

        return new Usuario(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, 0, null);
    }

    /**
     * Crea un objeto {@link UsuarioNormal} a partir de la lectura de datos por consola
     *
     * @param usuario usuario asociado para coger los datos de la superclase
     * @return objeto usuarioNormal
     */
    public UsuarioNormal crearUsuarioNormal(Usuario usuario) {
        String direccion = null;
        String telefono = null;
        String fotografia = null;
        String respuesta;
        do {
            respuesta = ConsolaView.leerTexto("Introduce la dirección (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            direccion = respuesta;
        }

        do {
            respuesta = ConsolaView.leerTexto("Introduce el teléfono móvil (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            telefono = respuesta;
        }

        do {
            respuesta = ConsolaView.leerTexto("Introduce el enlace de fotografía (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            fotografia = respuesta;
        }

        return new UsuarioNormal(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), usuario.getIdUsuario(), usuario.getFechaRegistro(), direccion, telefono, fotografia);

    }

    /**
     * Crea un objeto {@link Administrador} a partir de la lectura de datos por consola
     *
     * @param usuario usuario asociado para coger los datos de la superclase
     * @return objeto administrdor
     */
    public Administrador crearAdministrador(Usuario usuario) {
        String telefonoGuardia = ConsolaView.leerTexto("Introduce el teléfono de guardia: ");

        return new Administrador(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), usuario.getIdUsuario(), usuario.getFechaRegistro(), telefonoGuardia);
    }
    public String obtenerDniAEliminar() {
        String dni = ConsolaView.leerTexto("Introduce el DNI del usuario a eliminar:").toUpperCase();
        while(!ConsolaView.comprobarDNI(dni)) {
            System.out.println("Error: DNI incorrecto.");
        }
        return dni;
    }

    /**
     *Crea un nuevo objeto {@link Usuario} a partir de otro con los datos actualizados leídos por consola. Dependiendo del tipo de usuario llama a crear también sus correspondientes datos.
     *
     * @param usuario usuario que se desea actualizar
     * @return objeto usuario actualizado
     */
    public Usuario obtenerDatosActualizados(Usuario usuario) {

        String nombre = ConsolaView.leerTexto("Introduce el nombre completo:");
        String email = null;
        boolean emailValido = false;
        while (!emailValido){
            String emailAportado = ConsolaView.leerTexto("Introduce el nuevo email:");
            if (!ConsolaView.validarEmail(emailAportado)) {
                System.out.println("Error: email incorrecto.");
            } else {
                email = emailAportado;
                emailValido = true;
            }
        }
        String contrasena = ConsolaView.leerTexto("Introduce la nueva contraseña:");

        Date fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            String fecha = ConsolaView.leerTexto("Introduce la nueva fecha de nacimiento (dd/MM/yyyy):");
            fechaNacimiento = ConsolaView.validarYFormatearFecha(fecha);
            if (fechaNacimiento != null)
                fechaValida = true;
            else
                System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
        }

        return new Usuario(usuario.getDni(), nombre, email, contrasena, usuario.getTipoUsuario(), fechaNacimiento, usuario.getIdUsuario(), usuario.getFechaRegistro());

    }

    /**
     *Crea un nuevo objeto {@link UsuarioNormal} a partir de otro con los datos actualizados leídos por consola
     *
     * @param usuario usuario que se desea actualizar
     * @return objeto usuarioNormal actualizado
     */
    public UsuarioNormal obtenerDatosActualizadosUsuarioNormal(Usuario usuario) {
        String direccion = null;
        String telefono = null;
        String fotografia = null;
        String respuesta;
        do {
            respuesta = ConsolaView.leerTexto("Introduce la nueva dirección (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            direccion = respuesta;
        }

        do {
            respuesta = ConsolaView.leerTexto("Introduce el nuevo teléfono móvil (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            telefono = respuesta;
        }

        do {
            respuesta = ConsolaView.leerTexto("Introduce el nuevo enlace de fotografía (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            fotografia = respuesta;
        }

        return new UsuarioNormal(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), usuario.getIdUsuario(), usuario.getFechaRegistro(), direccion, telefono, fotografia);
    }

    /**
     *Crea un nuevo objeto {@link Administrador} a partir de otro con los datos actualizados leídos por consola
     *
     * @param usuario usuario que se desea actualizar
     * @return objeto administrador actualizado
     */
    public Administrador obtenerDatosActualizadosAdministrador(Usuario usuario) {
        String telefonoGuardia = ConsolaView.leerTexto("Introduce el nuevo teléfono de guardia: ");

        return new Administrador(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), usuario.getIdUsuario(), usuario.getFechaRegistro(), telefonoGuardia);
    }

    /**
     * Pide al usuario que introduzca por consola el DNI del usuario que desea recuperar y llama a su validación
     *
     * @return texto con el DNI
     */
    public String obtenerDni() {
        String dni;
        do {
            dni = ConsolaView.leerTexto("Introduce el DNI del usuario:").toUpperCase();
            System.out.println("Error: DNI incorrecto.");
        } while(!ConsolaView.comprobarDNI(dni));
        return dni;
    }

    /**
     * Muestra una tabla con el usuario solicitado
     *
     * @param usuario usuario que se desea visualizar
     */
    public void mostrarUsuario(Usuario usuario) {
        if (usuario != null){
            ConsolaView.mostrar(Usuario.CABECERA_LISTADO);
            System.out.println(usuario);}
        else
            System.out.println("El usuario no existe");
    }

    /**
     * Muestra una tabla con el usuario normal solicitado
     *
     * @param usuarioNormal usuario normal que se desea visualizar
     */
    public void mostrarUsuarioNormal(UsuarioNormal usuarioNormal) {
        if (usuarioNormal != null){
            ConsolaView.mostrar(UsuarioNormal.CABECERA_LISTADO);
            System.out.println(usuarioNormal);}
        else
            System.out.println("El usuario no existe");
    }

    /**
     * Muestra una tabla con el administrador solicitado
     *
     * @param administrador administrador que se desea visualizar
     */
    public void mostrarAdministrador(Administrador administrador) {
        if (administrador != null){
            ConsolaView.mostrar(Administrador.CABECERA_LISTADO);
            System.out.println(administrador);}
        else
            System.out.println("El usuario no existe");
    }

    /**
     * Muestra el menú de usuarios
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE USUARIOS");
        System.out.println("1. Alta de usuario");
        System.out.println("2. Baja de usuario");
        System.out.println("3. Modificar usuario");
        System.out.println("4. Listar todos los usuarios");
        System.out.println("5. Listar todos los administradores");
        System.out.println("6. Listar todos los usuarios normales");
        System.out.println("7. Mostrar usuario por DNI");
        System.out.println("8. Mostrar cantidad total de usuarios");
        System.out.println("9. Mostrar cantidad total de administradores");
        System.out.println("10. Mostrar cantidad total de usuarios normales");
        System.out.println("0. Volver");
    }

 }