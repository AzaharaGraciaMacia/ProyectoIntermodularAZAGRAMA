package app;

import controller.UsuarioController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //VistaPersona vista = new VistaPersona();
        UsuarioController controlador = new UsuarioController();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar todos los usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Mostrar usuario por DNI");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    controlador.mostrarTodosLosUsuarios();
                    break;
                case 2:
                    controlador.crearUsuario();
                    System.out.println("Usuario creado correctamente.");
                    break;
                case 3:
                    controlador.actualizarUsuario();
                    System.out.println("Usuario actualizado correctamente.");
                    break;
                case 4:
                    controlador.eliminarUsuario();
                    System.out.println("Usuario eliminado correctamente.");
                    break;
                case 5:
                    controlador.mostrarUsuarioDNI();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }
}