package view;

import dao.UsuarioDAO;
import model.TipoUsuario;
import model.Usuario;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        System.out.printf("%-5s %-12s %-15s %-15s %-5s\n","ID","DNI", "Nombre", "Apellidos","Edad");

        System.out.println("---------------------------------------------------------------------");

        usuarios.forEach(System.out::println);

    }

    public Usuario crearUsuario() {
        boolean entradaValida = false;
        //comprobamos que no exista una persona con el mismo DNI
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
        System.out.println("Introduce el tipo de usuario:");
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(scanner.nextLine());
        System.out.println("Introduce la fecha de nacimiento (DD/MM/YYYY):");
        Date fechaNacimiento = Date.valueOf(scanner.nextLine());
        int idUsuario = null;
        Timestamp fechaRegistro = null;

       /* entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.println("Introduce la edad:");
                edad = Integer.parseInt(scanner.nextLine());
                entradaValida = true; // si se convierte correctamente, salimos del bucle
            } catch (NumberFormatException e) {
                System.out.println("Error: debes introducir un número válido.");
            }
        }*/

        return new Usuario(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario, fechaRegistro);
    }

    public String obtenerDniAEliminar() {
        System.out.println("Introduce el DNI del usuario a eliminar:");
        return scanner.nextLine();
    }

    public Usuario obtenerDatosActualizados() {
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
        int idUsuario = null;
        Timestamp fechaRegistro = null;

        return new Usuario(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario, fechaRegistro);
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

    // Otros métodos de la vista...
}