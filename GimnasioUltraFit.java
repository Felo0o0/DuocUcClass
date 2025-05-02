/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gimnasioultrafit;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Fran
 */
public class GimnasioUltraFit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Datos base
        String[] actividades = {"Funcional", "Pilates"};
        int[] preciosNetos = {20000, 18000};

        String[] clientes = new String[20];
        int[] edadesClientes = new int[20];
        String[] actividadesReservadas = new String[20];
        ArrayList<String> tipoCliente = new ArrayList<>();
        ArrayList<Double> descuentosAplicados = new ArrayList<>();
        ArrayList<Double> montosFinales = new ArrayList<>();

        int[] cuposDisponibles = {5, 4};
        int totalClientes = 0;
        int estudiantes = 0;
        int adultosMayores = 0;
        int opcion = 0;
        boolean salir = false;

        System.out.println("===== Bienvenido al Sistema de Reservas - Gimnasio UltraFit Pro =====");

        while (!salir) {
            System.out.println("\nMenú Principal");
            System.out.println("1. Mostrar actividades disponibles");
            System.out.println("2. Registrar inscripción");
            System.out.println("3. Cancelar inscripción");
            System.out.println("4. Mostrar inscritos");
            System.out.println("5. Reporte final");
            System.out.println("6. Salir");

            System.out.print("Ingrese una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    System.out.println("\n📋 Actividades disponibles:");
                    for (int i = 0; i < actividades.length; i++) {
                        System.out.println(i + ". " + actividades[i] + " (Precio Neto: $" + preciosNetos[i] + ", Cupos: " + cuposDisponibles[i] + ")");
                    }
                }

                else if (opcion == 2) {
                    if (totalClientes >= clientes.length) {
                        System.out.println("❌ No hay más espacio para inscripciones.");
                    } else {
                        System.out.print("\nIngrese su nombre: ");
                        String nombre = sc.nextLine();
                        while (nombre.isBlank()) {
                            System.out.print("❌ Nombre inválido. Ingrese su nombre: ");
                            nombre = sc.nextLine();
                        }

                        int edad = 0;
                        boolean edadValida = false;
                        while (!edadValida) {
                            try {
                                System.out.print("Ingrese su edad: ");
                                edad = sc.nextInt();
                                sc.nextLine();
                                if (edad > 0 && edad < 120) {
                                    edadValida = true;
                                } else {
                                    System.out.println("❌ Edad inválida. Debe ser entre 1 y 119 años.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("❌ Debe ingresar un número válido.");
                                sc.nextLine();
                            }
                        }

                        System.out.println("Seleccione la actividad:");
                        for (int i = 0; i < actividades.length; i++) {
                            System.out.println(i + ". " + actividades[i] + " (Cupos: " + cuposDisponibles[i] + ")");
                        }

                        int seleccion = -1;
                        boolean seleccionValida = false;
                        while (!seleccionValida) {
                            try {
                                System.out.print("Ingrese el número de actividad: ");
                                seleccion = sc.nextInt();
                                sc.nextLine();
                                if (seleccion >= 0 && seleccion < actividades.length) {
                                    seleccionValida = true;
                                } else {
                                    System.out.println("❌ Selección inválida. Intente nuevamente.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("❌ Debe ingresar un número válido.");
                                sc.nextLine();
                            }
                        }

                        if (cuposDisponibles[seleccion] > 0) {
                            clientes[totalClientes] = nombre;
                            edadesClientes[totalClientes] = edad;
                            actividadesReservadas[totalClientes] = actividades[seleccion];

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
                                    System.out.print("❌ Respuesta inválida. Ingrese 'si' o 'no': ");
                                    respuesta = sc.nextLine().trim().toLowerCase();
                                }
                                if (respuesta.equals("si")) {
                                    descuento = 25.0;
                                    tipo = "Estudiante";
                                    estudiantes++;
                                }
                            }

                            tipoCliente.add(tipo);
                            descuentosAplicados.add(descuento);

                            double precioBase = preciosNetos[seleccion];
                            double montoConDescuento = precioBase * (1 - descuento / 100);
                            double montoConIVA = montoConDescuento * 1.19;

                            montosFinales.add(montoConIVA);

                            cuposDisponibles[seleccion]--;
                            totalClientes++;

                            System.out.println("✅ Inscripción realizada exitosamente.");
                            System.out.println("\n=== Detalle de inscripción ===");
                            System.out.println("Nombre: " + nombre);
                            System.out.println("Actividad: " + actividades[seleccion]);
                            System.out.println("Precio Neto: $" + precioBase);
                            System.out.println("Descuento aplicado: " + descuento + "%");
                            System.out.println("Subtotal después de descuento: $" + montoConDescuento);
                            System.out.println("IVA (19%): $" + (montoConDescuento * 0.19));
                            System.out.println("Precio Final: $" + montoConIVA);
                        } else {
                            System.out.println("❌ No hay cupos disponibles en esa actividad.");
                        }
                    }
                }

                else if (opcion == 3) {
                    System.out.print("\nIngrese su nombre para cancelar inscripción: ");
                    String nombreCancelar = sc.nextLine();
                    boolean encontrado = false;

                    for (int i = 0; i < totalClientes; i++) {
                        if (clientes[i] != null && clientes[i].equalsIgnoreCase(nombreCancelar)) {
                            if (actividadesReservadas[i].equals("Funcional")) {
                                cuposDisponibles[0]++;
                            } else if (actividadesReservadas[i].equals("Pilates")) {
                                cuposDisponibles[1]++;
                            }

                            clientes[i] = null;
                            actividadesReservadas[i] = null;
                            edadesClientes[i] = 0;
                            descuentosAplicados.set(i, 0.0);
                            montosFinales.set(i, 0.0);

                            System.out.println("✅ Inscripción cancelada exitosamente.");
                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("❌ Cliente no encontrado.");
                    }
                }

                else if (opcion == 4) {
                    System.out.println("\n📋 Listado de inscritos:");
                    boolean hayInscritos = false;
                    for (int i = 0; i < totalClientes; i++) {
                        if (clientes[i] != null) {
                            System.out.println(clientes[i] + " - " + actividadesReservadas[i] +
                                    " - Edad: " + edadesClientes[i] +
                                    " años - Tipo: " + tipoCliente.get(i) +
                                    " - Total pagado: $" + montosFinales.get(i));
                            hayInscritos = true;
                        }
                    }
                    if (!hayInscritos) {
                        System.out.println("No hay inscritos actualmente.");
                    }
                }

                else if (opcion == 5) {
                    System.out.println("\n===== REPORTE FINAL =====");
                    int inscritosTotales = 0;
                    double netoTotal = 0;
                    double ivaTotal = 0;
                    double totalRecaudado = 0;

                    for (int i = 0; i < totalClientes; i++) {
                        if (clientes[i] != null) {
                            inscritosTotales++;
                            double montoNeto = montosFinales.get(i) / 1.19;
                            double iva = montoNeto * 0.19;
                            netoTotal += montoNeto;
                            ivaTotal += iva;
                            totalRecaudado += montosFinales.get(i);
                        }
                    }

                    System.out.println("Total inscritos: " + inscritosTotales);
                    System.out.println("Estudiantes: " + estudiantes);
                    System.out.println("Adultos mayores: " + adultosMayores);
                    System.out.println("Neto total recaudado: $" + netoTotal);
                    System.out.println("IVA total recaudado: $" + ivaTotal);
                    System.out.println("Total final recaudado: $" + totalRecaudado);
                }

                else if (opcion == 6) {
                    salir = true;
                    System.out.println("👋 ¡Gracias por usar el sistema! Hasta pronto.");
                }

                else {
                    System.out.println("❌ Opción inválida. Ingrese un número entre 1 y 6.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Debe ingresar un número.");
                sc.nextLine();
            }
        }

        sc.close();
                
        
    }

}
