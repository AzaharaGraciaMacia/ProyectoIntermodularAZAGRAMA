package view;

import dao.UsuarioDAO;
import model.Administrador;
import model.TipoUsuario;
import model.Usuario;
import model.UsuarioNormal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UsuarioView {
    public Scanner scanner;

    public UsuarioView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        System.out.println("Lista de Usuarios:");
        // Cabecera
        System.out.printf("%-5s %-15s %-15s %-12s %-15s %-15s %-12s %-12s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Tipo de Usuario","Contraseña", "DNI", "Fecha de Registro" );

        System.out.println("---------------------------------------------------------------------");

        usuarios.forEach(System.out::println);
    }

    public void mostrarUsuariosNormales(List<UsuarioNormal> usuariosNormales) {
        System.out.println("Lista de Usuarios:");
        // Cabecera
        System.out.printf("%-5s %-15s %-15s %-12s %-15s %-15s %-12s %-15s %-15s %-15s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Dirección", "Teléfono Móvil", "Fotografía" );

        System.out.println("---------------------------------------------------------------------");

        usuariosNormales.forEach(System.out::println);
    }

    public void mostrarAdministradores(List<Administrador> administradores) {
        System.out.println("Lista de Usuarios:");
        // Cabecera
        System.out.printf("%-5s %-15s %-15s %-12s %-15s %-12s %-12s %-12s\n","ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Teléfono de Guardia" );

        System.out.println("---------------------------------------------------------------------");

        administradores.forEach(System.out::println);
    }

    public boolean omitirRespuesta = false;
    public void OmitirRespuesta(String respuesta) {
        if (respuesta.equals("OMITIR")) {
            omitirRespuesta = true;
        }
    }

    public Usuario crearUsuario() throws SQLException {
        boolean entradaValida = false;
        //comprobamos que no exista un usuario con el mismo DNI
        String dni="";
        while (!entradaValida) {
            System.out.println("Introduce el DNI:");
            dni = scanner.nextLine().toUpperCase();
            try {
                Usuario usuarioDuplicado = UsuarioDAO.getInstance().getUsuarioByDni(dni);
                if (usuarioDuplicado==null)//no existe
                    entradaValida = true;
                else
                    System.out.println("Error: el usuario ya existe.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Introduce el nombre completo:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el email:");
        String email = scanner.nextLine();
        System.out.println("Introduce la contraseña:");
        String contrasena = scanner.nextLine();
        System.out.println("Introduce el tipo de usuario (Normal o Administrador): ");
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(scanner.nextLine().toLowerCase());
        System.out.println("Introduce la fecha de nacimiento (DD/MM/YYYY):");
        Date fechaNacimiento = Date.valueOf(scanner.nextLine());

        return new Usuario(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, 0, null);

    }
    public UsuarioNormal crearUsuarioNormal(Usuario usuario) {
        String direccion = null;
        String telefono = null;
        String fotografia = null;
        System.out.println("Introduce la dirección (escribe OMITIR si no deseas aportar el dato): ");
        String respuesta = scanner.nextLine();
        OmitirRespuesta(respuesta.toUpperCase());
        if (!omitirRespuesta) direccion = respuesta;

        System.out.println("Introduce el teléfono móvil (escribe OMITIR si no deseas aportar el dato): ");
        String respuesta2 = scanner.nextLine();
        OmitirRespuesta(respuesta2.toUpperCase());
        if (!omitirRespuesta) telefono = respuesta;

        System.out.println("Introduce el enlace de fotografía (escribe OMITIR si no deseas aportar el dato): ");
        String respuesta3 = scanner.nextLine();
        OmitirRespuesta(respuesta3.toUpperCase());
        if (!omitirRespuesta) fotografia = respuesta;

        return new UsuarioNormal(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), 0, null, direccion, telefono, fotografia);

    }
    public Administrador crearAdministrador(Usuario usuario) {
        System.out.println("Introduce el teléfono de guardia: ");
        String telefonoGuardia = scanner.nextLine();

        return new Administrador(usuario.getDni(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipoUsuario(),usuario.getFechaNacimiento(), 0, null, telefonoGuardia);
    }
    public String obtenerDniAEliminar() {
        System.out.println("Introduce el DNI del usuario a eliminar:");
        return scanner.nextLine();
    }

    public Usuario obtenerDatosActualizados() {
        String direccion = null;
        String telefono = null;
        String fotografia = null;
        System.out.println("Introduce el DNI:");
        String dni = scanner.nextLine();
        System.out.println("Introduce el nombre completo:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el nuevo email:");
        String email = scanner.nextLine();
        System.out.println("Introduce la nueva contraseña:");
        String contrasena = scanner.nextLine();
        System.out.println("Introduce el tipo de usuario:");
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(scanner.nextLine());
        System.out.println("Introduce la fecha de nacimiento (DD/MM/YYYY):");
        Date fechaNacimiento = Date.valueOf(scanner.nextLine());
        if (tipoUsuario == TipoUsuario.valueOf("normal")){

            System.out.println("Introduce la dirección (escribe OMITIR si no deseas aportar el dato): ");
            String respuesta = scanner.nextLine();
            OmitirRespuesta(respuesta.toUpperCase());
            if (!omitirRespuesta) direccion = respuesta;

            System.out.println("Introduce el teléfono móvil (escribe OMITIR si no deseas aportar el dato): ");
            String respuesta2 = scanner.nextLine();
            OmitirRespuesta(respuesta2.toUpperCase());
            if (!omitirRespuesta) telefono = respuesta;

            System.out.println("Introduce el enlace de fotografía (escribe OMITIR si no deseas aportar el dato): ");
            String respuesta3 = scanner.nextLine();
            OmitirRespuesta(respuesta3.toUpperCase());
            if (!omitirRespuesta) fotografia = respuesta;

            return new UsuarioNormal(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, 0, null, direccion, telefono, fotografia);

        } else {
            System.out.println("Introduce el teléfono de guardia: ");
            String telefonoGuardia = scanner.nextLine();

            return new Administrador(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, 0, null, telefonoGuardia);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String obtenerDni() {
        System.out.println("Introduce el DNI del usuario:");
        return scanner.nextLine();
    }

    //muestra los datos del usuario
    public void mostrarUsuario(Usuario usuario) {
        if (usuario != null)
            System.out.println(usuario);
        else
            System.out.println("La persona no existe");
    }
    public void mostrarUsuarioNormal(UsuarioNormal usuarioNormal) {
        if (usuarioNormal != null)
            System.out.println(usuarioNormal);
        else
            System.out.println("La persona no existe");
    }
    public void mostrarAdministrador(Administrador administrador) {
        if (administrador != null)
            System.out.println(administrador);
        else
            System.out.println("La persona no existe");
    }

 }