/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication12;

/**
 *
 * @author felix
 */
import java.util.Scanner;

public class TeatroMoro3 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean[] asientosVIP = new boolean[20];
        boolean[] asientosPB = new boolean[30];
        boolean[] asientosPA = new boolean[50];
        
        // Arrays para almacenar historial de compras
        char[] zonasVendidas = new char[100];
        int[] asientosVendidos = new int[100];
        double[] preciosBase = new double[100];
        double[] descuentosAplicados = new double[100];
        double[] preciosFinales = new double[100];
        int totalVentas = 0;
        
        boolean continuar = true;
        
        // Menu principal con ciclo for
        for (; continuar;) {
            try {
                System.out.println("\n=== TEATRO MORO ===");
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");
                System.out.print("Seleccione opcion: ");
                int opcion = entrada.nextInt();

                if (opcion == 1) {
                    boolean comprando = true;
                    while(comprando) {
                        // Mostrar plano del teatro
                        mostrarPlanoTeatro(asientosVIP, asientosPB, asientosPA);

                        // Variables para la compra
                        double precioBase = 0, precioFinal = 0;
                        char zona;
                        int asiento;
                        
                        // Seleccion de zona con if-else
                        System.out.print("\nSeleccione zona (V=VIP, B=Platea Baja, A=Platea Alta): ");
                        zona = entrada.next().toUpperCase().charAt(0);
                        
                        // Validar zona y asignar precio
                        if (zona == 'V' || zona == 'B' || zona == 'A') {
                            switch (zona) {
                                case 'V': precioBase = 30000; break;
                                case 'B': precioBase = 15000; break;
                                case 'A': precioBase = 18000; break;
                            }

                            // Seleccion de asiento con do-while
                            boolean asientoValido;
                            do {
                                System.out.print("Ingrese numero de asiento: ");
                                asiento = entrada.nextInt();
                                asientoValido = validarAsiento(zona, asiento, asientosVIP, asientosPB, asientosPA);
                                
                                if (!asientoValido) {
                                    System.out.println("Asiento invalido o ocupado");
                                }
                            } while (!asientoValido);

                            // Validacion de edad
                            System.out.print("Ingrese edad del comprador: ");
                            int edad = entrada.nextInt();

                            // Verificar edad mínima
                            if (edad < 3) {
                                System.out.println("\nLo sentimos, no se permite la entrada a menores de 3 años.");
                                System.out.println("Esto se debe a que el teatro requiere silencio absoluto durante las funciones.");
                                continue;
                            }

                            // Calcular descuento
                            double descuento = calcularDescuento(edad, precioBase);
                            precioFinal = precioBase - descuento;

                            // Guardar información de la venta
                            zonasVendidas[totalVentas] = zona;
                            asientosVendidos[totalVentas] = asiento;
                            preciosBase[totalVentas] = precioBase;
                            descuentosAplicados[totalVentas] = descuento;
                            preciosFinales[totalVentas] = precioFinal;
                            totalVentas++;

                            // Mostrar resumen de compra
                            mostrarResumenCompra(zona, asiento, precioBase, descuento, precioFinal);

                            // Preguntar si desea otra compra
                            boolean respuestaValida = false;
                            while (!respuestaValida) {
                                System.out.print("\nDesea realizar otra compra? (S/N): ");
                                char respuesta = entrada.next().toUpperCase().charAt(0);

                                if (respuesta == 'S') {
                                    System.out.println("\nContinuando con nueva compra...");
                                    respuestaValida = true;
                                } else if (respuesta == 'N') {
                                    mostrarResumenFinal(zonasVendidas, asientosVendidos, preciosBase, 
                                                      descuentosAplicados, preciosFinales, totalVentas);
                                    respuestaValida = true;
                                    comprando = false;
                                    continuar = false;
                                } else {
                                    System.out.println("Opcion invalida. Por favor seleccione S o N.");
                                }
                            }
                        } else {
                            System.out.println("Zona invalida");
                        }
                    }
                } else if (opcion == 2) {
                    if (totalVentas > 0) {
                        mostrarResumenFinal(zonasVendidas, asientosVendidos, preciosBase, 
                                          descuentosAplicados, preciosFinales, totalVentas);
                    } else {
                        System.out.println("\nNo se realizaron compras. ¡Hasta luego!");
                    }
                    continuar = false;
                } else {
                    System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor valido");
                entrada.nextLine();
            }
        }
        entrada.close();
    }
    
    // Método para mostrar el plano del teatro
    private static void mostrarPlanoTeatro(boolean[] asientosVIP, boolean[] asientosPB, boolean[] asientosPA) {
        System.out.println("\n===== PLANO DEL TEATRO =====");
        System.out.println("\n********************ESCENARIO********************");
        
        // Mostrar Zona VIP
        System.out.println("\nZONA VIP - $30,000");
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosVIP, 0, 10, "Fila 1: ");
        mostrarFila(asientosVIP, 10, 20, "Fila 2: ");
        System.out.println("-------------------------------------------------");
        
        // Mostrar Platea Baja
        System.out.println("\n\nZONA PLATEA BAJA - $15,000");
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosPB, 0, 10, "Fila 1: ");
        mostrarFila(asientosPB, 10, 20, "Fila 2: ");
        mostrarFila(asientosPB, 20, 30, "Fila 3: ");
        System.out.println("-------------------------------------------------");
        
        // Mostrar Platea Alta
        System.out.println("\n\nZONA PLATEA ALTA - $18,000");
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosPA, 0, 10, "Fila 1: ");
        mostrarFila(asientosPA, 10, 20, "Fila 2: ");
        mostrarFila(asientosPA, 20, 30, "Fila 3: ");
        mostrarFila(asientosPA, 30, 40, "Fila 4: ");
        mostrarFila(asientosPA, 40, 50, "Fila 5: ");
        System.out.println("-------------------------------------------------");
    }
    
    // Método para mostrar una fila de asientos
    private static void mostrarFila(boolean[] asientos, int inicio, int fin, String etiqueta) {
        System.out.println(etiqueta);
        for (int i = inicio; i < fin; i++) {
            System.out.print(asientos[i] ? "  X  " : String.format(" %3d ", i + 1));
        }
        System.out.println();
    }
    
    // Método para validar asiento
    private static boolean validarAsiento(char zona, int asiento, boolean[] asientosVIP, 
                                         boolean[] asientosPB, boolean[] asientosPA) {
        if (zona == 'V' && asiento > 0 && asiento <= 20 && !asientosVIP[asiento - 1]) {
            asientosVIP[asiento - 1] = true;
            return true;
        } else if (zona == 'B' && asiento > 0 && asiento <= 30 && !asientosPB[asiento - 1]) {
            asientosPB[asiento - 1] = true;
            return true;
        } else if (zona == 'A' && asiento > 0 && asiento <= 50 && !asientosPA[asiento - 1]) {
            asientosPA[asiento - 1] = true;
            return true;
        }
        return false;
    }
    
    // Método para calcular descuento
    private static double calcularDescuento(int edad, double precioBase) {
        if (edad >= 60) {
            return precioBase * 0.15; // 15% descuento tercera edad
        } else if (edad <= 18) {
            return precioBase * 0.10; // 10% descuento estudiante
        }
        return 0;
    }
    
    // Método para mostrar resumen de compra
    private static void mostrarResumenCompra(char zona, int asiento, double precioBase, 
                                           double descuento, double precioFinal) {
        System.out.println("\n=== RESUMEN DE COMPRA ===");
        System.out.println("Zona: " + (zona == 'V' ? "VIP" : 
                                     zona == 'B' ? "Platea Baja" : "Platea Alta"));
        System.out.println("Asiento: " + asiento);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento: $" + descuento);
        System.out.println("Precio final: $" + precioFinal);
    }
    
    // Método para mostrar resumen final
    private static void mostrarResumenFinal(char[] zonasVendidas, int[] asientosVendidos, 
                                          double[] preciosBase, double[] descuentosAplicados, 
                                          double[] preciosFinales, int totalVentas) {
        System.out.println("\n=== RESUMEN FINAL DE SUS COMPRAS ===");
        double totalFinal = 0;
        
        for (int i = 0; i < totalVentas; i++) {
            System.out.println("\nCompra #" + (i + 1));
            System.out.println("Zona: " + (zonasVendidas[i] == 'V' ? "VIP" : 
                                         zonasVendidas[i] == 'B' ? "Platea Baja" : "Platea Alta"));
            System.out.println("Asiento: " + asientosVendidos[i]);
            System.out.println("Precio base: $" + preciosBase[i]);
            System.out.println("Descuento aplicado: $" + descuentosAplicados[i]);
            System.out.println("Precio final: $" + preciosFinales[i]);
            totalFinal += preciosFinales[i];
        }
        
        System.out.println("\nTotal a pagar: $" + totalFinal);
        System.out.println("\nGracias por su compra!");
        System.out.println("Redirigiendo al sitio de pago...");
    }
}