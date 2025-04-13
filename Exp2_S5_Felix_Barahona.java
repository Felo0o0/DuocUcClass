/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teatro;

/**
 *
 * @author felix
 */

import java.util.Scanner;

public class TeatroMoro {
    // Variables estaticas (de clase) para estadisticas globales
    private static int totalEntradasVendidas = 0;
    private static double totalIngresos = 0;
    private static int totalDescuentosAplicados = 0;
    
    // Variables de instancia para informacion persistente sobre entradas
    private int numeroEntrada;
    private String ubicacion;
    private double precioBase;
    private double precioFinal;
    
    // Arreglos para almacenar informacion de entradas vendidas (maximo 100)
    private static int[] numerosEntradas = new int[100];
    private static String[] ubicacionesEntradas = new String[100];
    private static String[] tiposCliente = new String[100];
    private static double[] preciosBase = new double[100];
    private static double[] preciosFinales = new double[100];
    
    // Arreglos para representar los asientos del teatro
    private static boolean[] asientosVIP = new boolean[20];
    private static boolean[] asientosPlatea = new boolean[30];
    private static boolean[] asientosGeneral = new boolean[50];
    
    // Constantes para precios de ubicaciones
    private static final double PRECIO_VIP = 30000;
    private static final double PRECIO_PLATEA = 20000;
    private static final double PRECIO_GENERAL = 10000;
    
    // Constantes para descuentos
    private static final double DESCUENTO_ESTUDIANTE = 0.10; // 10%
    private static final double DESCUENTO_TERCERA_EDAD = 0.15; // 15%
    
    // Nombre del teatro y capacidad
    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_TOTAL = asientosVIP.length + asientosPlatea.length + asientosGeneral.length;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        System.out.println("Bienvenido al Sistema de Ventas de " + NOMBRE_TEATRO);
        System.out.println("Capacidad total: " + CAPACIDAD_TOTAL + " asientos");
        
        while (continuar) {
            try {
                // Menu principal
                System.out.println("\n===== MENU PRINCIPAL =====");
                System.out.println("1. Venta de entradas");
                System.out.println("2. Promociones");
                System.out.println("3. Busqueda de entradas");
                System.out.println("4. Eliminacion de entradas");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opcion: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        venderEntrada(scanner);
                        break;
                    case 2:
                        mostrarPromociones();
                        break;
                    case 3:
                        buscarEntradas(scanner);
                        break;
                    case 4:
                        eliminarEntrada(scanner);
                        break;
                    case 5:
                        System.out.println("Gracias por utilizar el sistema. Hasta pronto!");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor valido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        scanner.close();
    }
    
    // Metodo para vender entradas
    private static void venderEntrada(Scanner scanner) {
        boolean seguirComprando = true;
        
        while (seguirComprando) {
            // Variables locales para almacenar temporalmente datos
            String ubicacion;
            String tipoCliente;
            int edad;
            double precioBase = 0;
            double descuento = 0;
            double precioFinal;
            boolean descuentoCumpleanos = false;
            
            // Mostrar plano del teatro
            mostrarPlanoTeatro();
            
            // Solicitar ubicacion
            System.out.println("\nUbicaciones disponibles:");
            System.out.println("1. VIP ($" + PRECIO_VIP + ")");
            System.out.println("2. Platea ($" + PRECIO_PLATEA + ")");
            System.out.println("3. General ($" + PRECIO_GENERAL + ")");
            System.out.print("Seleccione ubicacion (1-3): ");
            
            int opcionUbicacion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Asignar ubicacion y precio base
            switch (opcionUbicacion) {
                case 1:
                    ubicacion = "VIP";
                    precioBase = PRECIO_VIP;
                    break;
                case 2:
                    ubicacion = "Platea";
                    precioBase = PRECIO_PLATEA;
                    break;
                case 3:
                    ubicacion = "General";
                    precioBase = PRECIO_GENERAL;
                    break;
                default:
                    System.out.println("Ubicacion no valida. Volviendo al menu principal.");
                    return;
            }
            
            // Solicitar numero de asiento
            System.out.print("Ingrese numero de asiento: ");
            int numeroAsiento = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Verificar disponibilidad del asiento
            if (!verificarDisponibilidadAsiento(ubicacion, numeroAsiento)) {
                System.out.println("El asiento seleccionado no esta disponible o no existe.");
                
                // Preguntar si desea intentar con otro asiento
                System.out.print("¿Desea intentar con otro asiento? (S/N): ");
                String respuestaOtroAsiento = scanner.nextLine().toUpperCase();
                if (respuestaOtroAsiento.equals("S")) {
                    continue; // Volver al inicio del ciclo
                } else {
                    return; // Volver al menú principal
                }
            }
            
            // Solicitar edad para determinar descuentos
            System.out.print("Ingrese la edad del cliente: ");
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Determinar tipo de cliente y descuento
            if (edad >= 60) {
                tipoCliente = "Tercera Edad";
                descuento = precioBase * DESCUENTO_TERCERA_EDAD;
            } else if (edad <= 18) {
                tipoCliente = "Estudiante";
                descuento = precioBase * DESCUENTO_ESTUDIANTE;
            } else {
                tipoCliente = "Regular";
            }
            
            // Preguntar si es cumpleanos
            System.out.print("Es el cumpleanos del cliente? (S/N): ");
            String respuestaCumpleanos = scanner.nextLine().toUpperCase();
            
            if (respuestaCumpleanos.equals("S")) {
                descuentoCumpleanos = true;
                System.out.println("Feliz cumpleanos! Promocion aplicada: Una entrada gratis y otra a mitad de precio.");
                
                // Si ya tiene descuento por edad, informar que no es acumulable
                if (descuento > 0) {
                    System.out.println("Nota: Los descuentos no son acumulables. Se aplicara el descuento mas beneficioso.");
                    // Comparar descuentos y elegir el mejor
                    double descuentoCumple = precioBase * 0.5; // 50% de descuento (mitad de precio)
                    if (descuentoCumple > descuento) {
                        descuento = descuentoCumple;
                        tipoCliente += " (Promocion Cumpleanos)";
                    }
                } else {
                    // Aplicar descuento de cumpleanos (mitad de precio)
                    descuento = precioBase * 0.5;
                    tipoCliente += " (Promocion Cumpleanos)";
                }
            }
            
            // Calcular precio final
            precioFinal = precioBase - descuento;
            
            // Marcar asiento como ocupado
            marcarAsientoOcupado(ubicacion, numeroAsiento);
            
            // Guardar informacion de la entrada
            totalEntradasVendidas++;
            int indice = totalEntradasVendidas - 1;
            numerosEntradas[indice] = totalEntradasVendidas;
            ubicacionesEntradas[indice] = ubicacion;
            tiposCliente[indice] = tipoCliente;
            preciosBase[indice] = precioBase;
            preciosFinales[indice] = precioFinal;
            
            // Actualizar estadisticas
            totalIngresos += precioFinal;
            if (descuento > 0) {
                totalDescuentosAplicados++;
            }
            
            // Mostrar resumen de la venta
            System.out.println("\n===== RESUMEN DE VENTA =====");
            System.out.println("Numero de entrada: " + totalEntradasVendidas);
            System.out.println("Ubicacion: " + ubicacion);
            System.out.println("Asiento: " + numeroAsiento);
            System.out.println("Tipo de cliente: " + tipoCliente);
            System.out.println("Precio base: $" + precioBase);
            System.out.println("Descuento aplicado: $" + descuento);
            System.out.println("Precio final: $" + precioFinal);
            
            // Si es cumpleanos, informar sobre la entrada gratis
            if (descuentoCumpleanos) {
                System.out.println("\nAdemas recibe una entrada GRATIS para la misma ubicacion!");
                
                // Solicitar numero de asiento para la entrada gratis
                System.out.print("Ingrese numero de asiento para la entrada gratis: ");
                int asientoGratis = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                // Verificar disponibilidad del asiento
                if (!verificarDisponibilidadAsiento(ubicacion, asientoGratis)) {
                    System.out.println("El asiento seleccionado no esta disponible o no existe. No se pudo asignar la entrada gratis.");
                } else {
                    // Marcar asiento como ocupado
                    marcarAsientoOcupado(ubicacion, asientoGratis);
                    
                    // Guardar informacion de la entrada gratis
                    totalEntradasVendidas++;
                    indice = totalEntradasVendidas - 1;
                    numerosEntradas[indice] = totalEntradasVendidas;
                    ubicacionesEntradas[indice] = ubicacion;
                    tiposCliente[indice] = tipoCliente + " (Gratis por Cumpleanos)";
                    preciosBase[indice] = precioBase;
                    preciosFinales[indice] = 0; // Gratis
                    
                    System.out.println("Entrada gratis asignada al asiento " + asientoGratis + " en ubicacion " + ubicacion);
                }
            }
            
            System.out.println("\nVenta realizada con exito.");
            
            // Preguntar si desea seguir comprando
            System.out.print("\nDesea comprar otra entrada? (S/N): ");
            String respuestaSeguirComprando = scanner.nextLine().toUpperCase();
            
            if (!respuestaSeguirComprando.equals("S")) {
                seguirComprando = false;
                System.out.println("Volviendo al menu principal...");
            }
        }
    }
    
    // Metodo para mostrar promociones disponibles
    private static void mostrarPromociones() {
        System.out.println("\n===== PROMOCIONES DISPONIBLES =====");
        System.out.println("1. Promocion Cumpleanos:");
        System.out.println("   - Una entrada GRATIS");
        System.out.println("   - Una entrada a MITAD DE PRECIO");
        System.out.println("   * No acumulable con otros descuentos");
        System.out.println("\n2. Descuentos por tipo de cliente:");
        System.out.println("   - Estudiantes: 10% de descuento");
        System.out.println("   - Tercera Edad: 15% de descuento");
    }
    
    // Metodo para buscar entradas
    private static void buscarEntradas(Scanner scanner) {
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas vendidas para buscar.");
            return;
        }
        
        System.out.println("\n===== BUSQUEDA DE ENTRADAS =====");
        System.out.println("1. Buscar por numero de entrada");
        System.out.println("2. Buscar por ubicacion");
        System.out.println("3. Buscar por tipo de compra (Regular/Descuentos)");
        System.out.print("Seleccione una opcion: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        boolean encontrado = false;
        
        switch (opcion) {
            case 1:
                System.out.print("Ingrese numero de entrada: ");
                int numeroEntrada = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                for (int i = 0; i < totalEntradasVendidas; i++) {
                    if (numerosEntradas[i] == numeroEntrada) {
                        mostrarInfoEntrada(i);
                        encontrado = true;
                        break;
                    }
                }
                break;
                
            case 2:
                System.out.print("Ingrese ubicacion (VIP, Platea, General): ");
                String ubicacion = scanner.nextLine();
                
                System.out.println("\nEntradas encontradas en ubicacion " + ubicacion + ":");
                for (int i = 0; i < totalEntradasVendidas; i++) {
                    if (ubicacionesEntradas[i].equalsIgnoreCase(ubicacion)) {
                        mostrarInfoEntrada(i);
                        encontrado = true;
                    }
                }
                break;
                
            case 3:
                System.out.println("Tipos de compra disponibles:");
                System.out.println("1. Regular (sin descuentos)");
                System.out.println("2. Descuentos (Estudiante, Tercera Edad, Cumpleanos)");
                System.out.print("Seleccione tipo de compra (1-2): ");
                int tipoCompra = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                if (tipoCompra == 1) {
                    // Buscar compras regulares (sin descuento)
                    System.out.println("\nEntradas con compra Regular (sin descuentos):");
                    for (int i = 0; i < totalEntradasVendidas; i++) {
                        if (tiposCliente[i].equals("Regular")) {
                            mostrarInfoEntrada(i);
                            encontrado = true;
                        }
                    }
                } else if (tipoCompra == 2) {
                    // Buscar compras con descuentos
                    System.out.println("\nEntradas con Descuentos:");
                    for (int i = 0; i < totalEntradasVendidas; i++) {
                        if (!tiposCliente[i].equals("Regular")) {
                            mostrarInfoEntrada(i);
                            encontrado = true;
                        }
                    }
                } else {
                    System.out.println("Opcion no valida.");
                    return;
                }
                break;
                
            default:
                System.out.println("Opcion no valida.");
                return;
        }
        
        if (!encontrado) {
            System.out.println("No se encontraron entradas con los criterios especificados.");
        }
    }
    
    // Metodo para eliminar una entrada
    private static void eliminarEntrada(Scanner scanner) {
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas para eliminar.");
            return;
        }
        
        System.out.print("Ingrese el numero de entrada a eliminar: ");
        int numeroEntrada = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        int indiceEliminar = -1;
        
        // Buscar la entrada por numero
        for (int i = 0; i < totalEntradasVendidas; i++) {
            if (numerosEntradas[i] == numeroEntrada) {
                indiceEliminar = i;
                break;
            }
        }
        
        if (indiceEliminar == -1) {
            System.out.println("No se encontro ninguna entrada con el numero " + numeroEntrada);
            return;
        }
        
        // Mostrar informacion de la entrada a eliminar
        System.out.println("\nSe eliminara la siguiente entrada:");
        mostrarInfoEntrada(indiceEliminar);
        
        System.out.print("¿Esta seguro de eliminar esta entrada? (S/N): ");
        String confirmacion = scanner.nextLine().toUpperCase();
        
        if (confirmacion.equals("S")) {
            // Restar el precio de la entrada eliminada del total de ingresos
            totalIngresos -= preciosFinales[indiceEliminar];
            
            // Desplazar elementos para eliminar la entrada
            for (int i = indiceEliminar; i < totalEntradasVendidas - 1; i++) {
                numerosEntradas[i] = numerosEntradas[i + 1];
                ubicacionesEntradas[i] = ubicacionesEntradas[i + 1];
                tiposCliente[i] = tiposCliente[i + 1];
                preciosBase[i] = preciosBase[i + 1];
                preciosFinales[i] = preciosFinales[i + 1];
            }
            
            totalEntradasVendidas--;
            System.out.println("Entrada eliminada con exito.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
    
    // Metodo para mostrar informacion de una entrada
    private static void mostrarInfoEntrada(int indice) {
        System.out.println("\n----- Entrada #" + numerosEntradas[indice] + " -----");
        System.out.println("Ubicacion: " + ubicacionesEntradas[indice]);
        System.out.println("Tipo de cliente: " + tiposCliente[indice]);
        System.out.println("Precio base: $" + preciosBase[indice]);
        System.out.println("Precio final: $" + preciosFinales[indice]);
    }
    
    // Metodo para verificar disponibilidad de asiento
    private static boolean verificarDisponibilidadAsiento(String ubicacion, int numeroAsiento) {
        if (ubicacion.equals("VIP")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosVIP.length) {
                return false;
            }
            return !asientosVIP[numeroAsiento - 1];
        } else if (ubicacion.equals("Platea")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosPlatea.length) {
                return false;
            }
            return !asientosPlatea[numeroAsiento - 1];
        } else if (ubicacion.equals("General")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosGeneral.length) {
                return false;
            }
            return !asientosGeneral[numeroAsiento - 1];
        }
        return false;
    }
    
    // Metodo para marcar asiento como ocupado
    private static void marcarAsientoOcupado(String ubicacion, int numeroAsiento) {
        if (ubicacion.equals("VIP")) {
            asientosVIP[numeroAsiento - 1] = true;
        } else if (ubicacion.equals("Platea")) {
            asientosPlatea[numeroAsiento - 1] = true;
        } else if (ubicacion.equals("General")) {
            asientosGeneral[numeroAsiento - 1] = true;
        }
    }
    
    // Metodo para mostrar el plano del teatro
    private static void mostrarPlanoTeatro() {
        System.out.println("\n===== PLANO DEL TEATRO =====");
        System.out.println("\n********************ESCENARIO********************");
        
        // Mostrar Zona VIP
        System.out.println("\nZONA VIP - $" + PRECIO_VIP);
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosVIP, 0, 10, "Fila 1: ");
        mostrarFila(asientosVIP, 10, 20, "Fila 2: ");
        System.out.println("-------------------------------------------------");
        
        // Mostrar Platea
        System.out.println("\n\nZONA PLATEA - $" + PRECIO_PLATEA);
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosPlatea, 0, 10, "Fila 1: ");
        mostrarFila(asientosPlatea, 10, 20, "Fila 2: ");
        mostrarFila(asientosPlatea, 20, 30, "Fila 3: ");
        System.out.println("-------------------------------------------------");
        
        // Mostrar General
        System.out.println("\n\nZONA GENERAL - $" + PRECIO_GENERAL);
        System.out.println("-------------------------------------------------");
        mostrarFila(asientosGeneral, 0, 10, "Fila 1: ");
        mostrarFila(asientosGeneral, 10, 20, "Fila 2: ");
        mostrarFila(asientosGeneral, 20, 30, "Fila 3: ");
        mostrarFila(asientosGeneral, 30, 40, "Fila 4: ");
        mostrarFila(asientosGeneral, 40, 50, "Fila 5: ");
        System.out.println("-------------------------------------------------");
    }
    
    // Metodo para mostrar una fila de asientos
    private static void mostrarFila(boolean[] asientos, int inicio, int fin, String etiqueta) {
        System.out.println(etiqueta);
        for (int i = inicio; i < fin; i++) {
            System.out.print(asientos[i] ? "  X  " : String.format(" %3d ", i + 1));
        }
        System.out.println();
    }
}
