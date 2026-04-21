package view;

import dao.UsuarioDAO;
import model.Administrador;
import model.TipoUsuario;
import model.Usuario;
import model.UsuarioNormal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UsuarioView {


    public void mostrarUsuarios(List<Usuario> usuarios) {
        System.out.println("Lista de Usuarios:");

        System.out.printf("%-5s %-30s %-30s %-25s %-25s %-30s %-12s %-50s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Tipo de Usuario","Contraseña", "DNI", "Fecha de Registro" );

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

        usuarios.forEach(System.out::println);
    }

    public void mostrarUsuariosNormales(List<UsuarioNormal> usuariosNormales) {
        System.out.println("Lista de Usuarios Normales:");

        System.out.printf("%-5s %-30s %-30s %-25s %-30s %-12s %-25s %-50s %-15s %-15s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Dirección", "Teléfono Móvil", "Fotografía" );

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        usuariosNormales.forEach(System.out::println);
    }

    public void mostrarAdministradores(List<Administrador> administradores) {
        System.out.println("Lista de Administradores:");

        System.out.printf("%-5s %-30s %-30s %-25s %-30s %-12s %-25s %-12s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Teléfono de Guardia" );

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        administradores.forEach(System.out::println);
    }

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
    public Administrador obtenerDatosActualizadosAdministrador(Usuario usuario) {
        String telefonoGuardia = ConsolaView.leerTexto("Introduce el nuevo teléfono de guardia: ");

        return new Administrador(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), usuario.getIdUsuario(), usuario.getFechaRegistro(), telefonoGuardia);
    }

    public String obtenerDni() {
        String dni;
        do {
            dni = ConsolaView.leerTexto("Introduce el DNI del usuario:").toUpperCase();
            System.out.println("Error: DNI incorrecto.");
        } while(!ConsolaView.comprobarDNI(dni));
        return dni;
    }

    public void mostrarUsuario(Usuario usuario) {
        if (usuario != null)
            System.out.println(usuario);
        else
            System.out.println("El usuario no existe");
    }

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