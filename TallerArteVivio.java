/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tallerartevivio;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * @author Fran
 */
public class TallerArteVivio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Datos
        String[] talleres = {"Pintura", "Escultura"};
        int[] preciosNetos = {15000, 18000};
        int[] cuposDisponibles = {3, 2};

        String[] clientes = new String[10];
        int[] edades = new int[10];
        String[] tallerInscrito = new String[10];
        ArrayList<String> tipoCliente = new ArrayList<>();
        ArrayList<Double> montosPagados = new ArrayList<>();

        int totalClientes = 0;
        int estudiantes = 0;
        int adultosMayores = 0;

        int opcion = 0;
        boolean salir = false;

        System.out.println("===== Bienvenido a Casa Cultural ArteVivo =====");

        while (!salir) {
            System.out.println("\nMenú Principal");
            System.out.println("1. Ver talleres disponibles");
            System.out.println("2. Inscribirse en un taller");
            System.out.println("3. Ver inscritos");
            System.out.println("4. Ver reporte final");
            System.out.println("5. Salir");

            System.out.print("Ingrese una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    for (int i = 0; i < talleres.length; i++) {
                        System.out.println(i + ". " + talleres[i] + " - Precio Neto: $" + preciosNetos[i] + " (Cupos: " + cuposDisponibles[i] + ")");
                    }
                }

                else if (opcion == 2) {
                    if (totalClientes >= clientes.length) {
                        System.out.println("❌ No hay más espacio para inscripciones.");
                    } else {
                        System.out.print("Ingrese su nombre: ");
                        String nombre = sc.nextLine();
                        while (nombre.isBlank()) {
                            System.out.print("❌ Nombre inválido. Intente de nuevo: ");
                            nombre = sc.nextLine();
                        }

                        int edad = 0;
                        boolean edadCorrecta = false;
                        while (!edadCorrecta) {
                            try {
                                System.out.print("Ingrese su edad: ");
                                edad = sc.nextInt();
                                sc.nextLine();
                                if (edad > 0 && edad < 120) {
                                    edadCorrecta = true;
                                } else {
                                    System.out.println("❌ Edad inválida. Ingrese entre 1 y 119.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("❌ Debe ingresar un número válido.");
                                sc.nextLine();
                            }
                        }

                        for (int i = 0; i < talleres.length; i++) {
                            System.out.println(i + ". " + talleres[i] + " (" + cuposDisponibles[i] + " cupos)");
                        }

                        int seleccion = -1;
                        boolean seleccionCorrecta = false;
                        while (!seleccionCorrecta) {
                            try {
                                System.out.print("Seleccione el número del taller: ");
                                seleccion = sc.nextInt();
                                sc.nextLine();
                                if (seleccion >= 0 && seleccion < talleres.length) {
                                    seleccionCorrecta = true;
                                } else {
                                    System.out.println("❌ Selección inválida.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("❌ Entrada inválida.");
                                sc.nextLine();
                            }
                        }

                        if (cuposDisponibles[seleccion] > 0) {
                            clientes[totalClientes] = nombre;
                            edades[totalClientes] = edad;
                            tallerInscrito[totalClientes] = talleres[seleccion];

                            double descuento = 0;
                            String tipo = "Normal";

                            if (edad >= 60) {
                                descuento = 30.0;
                                tipo = "Adulto Mayor";
                                adultosMayores++;
                            } else {
                                System.out.print("¿Es estudiante? (si/no): ");
                                String respuesta = sc.nextLine().trim().toLowerCase();
                                while (!(respuesta.equals("si") || respuesta.equals("no"))) {
                                    System.out.print("❌ Respuesta inválida. Responda si/no: ");
                                    respuesta = sc.nextLine().trim().toLowerCase();
                                }
                                if (respuesta.equals("si")) {
                                    descuento = 25.0;
                                    tipo = "Estudiante";
                                    estudiantes++;
                                }
                            }

                            tipoCliente.add(tipo);

                            double precioBase = preciosNetos[seleccion];
                            double montoConDescuento = precioBase * (1 - descuento / 100);
                            double montoConIVA = montoConDescuento * 1.19;

                            montosPagados.add(montoConIVA);
                            cuposDisponibles[seleccion]--;
                            totalClientes++;

                            System.out.println("✅ Inscripción exitosa.");
                            System.out.println("Detalle:");
                            System.out.println("Taller: " + talleres[seleccion]);
                            System.out.println("Precio Neto: $" + precioBase);
                            System.out.println("Descuento: " + descuento + "%");
                            System.out.println("IVA: $" + (montoConDescuento * 0.19));
                            System.out.println("Total a pagar: $" + montoConIVA);
                        } else {
                            System.out.println("❌ No quedan cupos en ese taller.");
                        }
                    }
                }

                else if (opcion == 3) {
                    System.out.println("\n📋 Listado de Inscritos:");
                    for (int i = 0; i < totalClientes; i++) {
                        if (clientes[i] != null) {
                            System.out.println(clientes[i] + " - Taller: " + tallerInscrito[i] + " - Tipo: " + tipoCliente.get(i) + " - Total Pagado: $" + montosPagados.get(i));
                        }
                    }
                }

                else if (opcion == 4) {
                    double total = 0;
                    for (double pago : montosPagados) {
                        total += pago;
                    }
                    System.out.println("===== Reporte Final =====");
                    System.out.println("Total inscritos: " + totalClientes);
                    System.out.println("Estudiantes: " + estudiantes);
                    System.out.println("Adultos mayores: " + adultosMayores);
                    System.out.println("Total recaudado (con IVA): $" + total);
                }

                else if (opcion == 5) {
                    salir = true;
                    System.out.println("👋 Gracias por usar ArteVivo. ¡Hasta pronto!");
                }

                else {
                    System.out.println("❌ Opción fuera de rango.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Error de entrada. Intente nuevamente.");
                sc.nextLine();
            }
        }

        sc.close();
    }
    
}
