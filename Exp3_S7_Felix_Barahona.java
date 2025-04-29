/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s7_felix_barahona;

/**
 *
 * @author felix
 */

  import java.util.*;

public class Exp3_S7_Felix_Barahona {
    
    // Variables estaticas para estadisticas globales
    private static double totalIngresos = 0;
    private static int totalDescuentosAplicados = 0;
    
    // Listas para almacenar informacion de entradas
    private static List<Integer> numerosEntradas = new ArrayList<>();
    private static List<String> ubicacionesEntradas = new ArrayList<>();
    private static List<String> tiposCliente = new ArrayList<>();
    private static List<Double> preciosBase = new ArrayList<>();
    private static List<Double> preciosFinales = new ArrayList<>();
    private static List<Integer> asientosOcupados = new ArrayList<>();
    
    // Listas para control de asientos
    private static List<Boolean> asientosVIP = new ArrayList<>(Collections.nCopies(20, false));
    private static List<Boolean> asientosPlatea = new ArrayList<>(Collections.nCopies(30, false));
    private static List<Boolean> asientosGeneral = new ArrayList<>(Collections.nCopies(50, false));
    
    // Constantes para precios
    private static final double PRECIO_VIP = 30000;
    private static final double PRECIO_PLATEA = 20000;
    private static final double PRECIO_GENERAL = 10000;
    
    // Constantes para descuentos
    private static final double DESCUENTO_ESTUDIANTE = 0.10;
    private static final double DESCUENTO_TERCERA_EDAD = 0.15;

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        System.out.println("Bienvenido al Sistema de Ventas de Teatro Moro" );
        
        while (continuar) {
            try {
                mostrarMenu();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        venderEntrada(scanner);
                        break;
                    case 2:
                        mostrarResumenVentas();
                        break;
                    case 3:
                        generarBoleta(scanner);
                        break;
                    case 4:
                        mostrarIngresosTotales();
                        break;
                    case 5:
                        continuar = false;
                        System.out.println("Gracias por usar el sistema");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor valido");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Vender entrada");
        System.out.println("2. Ver resumen de ventas");
        System.out.println("3. Generar boleta");
        System.out.println("4. Ver ingresos totales");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static void venderEntrada(Scanner scanner) {
        mostrarPlanoTeatro();
        
        // Solicitar ubicacion
        System.out.println("\nUbicaciones disponibles:");
        System.out.println("1. VIP ($" + PRECIO_VIP + ")");
        System.out.println("2. Platea ($" + PRECIO_PLATEA + ")");
        System.out.println("3. General ($" + PRECIO_GENERAL + ")");
        System.out.print("Seleccione ubicacion (1-3): ");
        
        int opcionUbicacion = scanner.nextInt();
        scanner.nextLine();
        
        String ubicacion;
        double precioBase;
        List<Boolean> asientosZona;
        
        switch (opcionUbicacion) {
            case 1:
                ubicacion = "VIP";
                precioBase = PRECIO_VIP;
                asientosZona = asientosVIP;
                break;
            case 2:
                ubicacion = "Platea";
                precioBase = PRECIO_PLATEA;
                asientosZona = asientosPlatea;
                break;
            case 3:
                ubicacion = "General";
                precioBase = PRECIO_GENERAL;
                asientosZona = asientosGeneral;
                break;
            default:
                System.out.println("Ubicacion no valida");
                return;
        }
        
        // Solicitar numero de asiento
        System.out.print("Ingrese numero de asiento: ");
        int numeroAsiento = scanner.nextInt();
        scanner.nextLine();
        
        if (numeroAsiento < 1 || numeroAsiento > asientosZona.size() || asientosZona.get(numeroAsiento - 1)) {
            System.out.println("Asiento no disponible o invalido");
            return;
        }
        
        // Solicitar edad para descuentos
        System.out.print("Ingrese edad del cliente: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        
        String tipoCliente;
        double descuento = 0;
        
        if (edad >= 60) {
            tipoCliente = "Tercera Edad";
            descuento = precioBase * DESCUENTO_TERCERA_EDAD;
        } else if (edad <= 18) {
            tipoCliente = "Estudiante";
            descuento = precioBase * DESCUENTO_ESTUDIANTE;
        } else {
            tipoCliente = "Regular";
        }
        
        // Calcular precio final
        double precioFinal = precioBase - descuento;
        
        // Guardar informacion
        numerosEntradas.add(numerosEntradas.size() + 1);
        ubicacionesEntradas.add(ubicacion);
        tiposCliente.add(tipoCliente);
        preciosBase.add(precioBase);
        preciosFinales.add(precioFinal);
        asientosOcupados.add(numeroAsiento);
        
        // Marcar asiento como ocupado
        asientosZona.set(numeroAsiento - 1, true);
        
        // Actualizar estadisticas
        totalIngresos += precioFinal;
        if (descuento > 0) {
            totalDescuentosAplicados++;
        }
        
        // Generar boleta
        generarBoletaVenta(numerosEntradas.size() - 1);
    }
    
    private static void mostrarResumenVentas() {
        if (numerosEntradas.isEmpty()) {
            System.out.println("No hay ventas registradas");
            return;
        }
        
        System.out.println("\n=== RESUMEN DE VENTAS ===");
        for (int i = 0; i < numerosEntradas.size(); i++) {
            System.out.println("\nEntrada #" + numerosEntradas.get(i));
            System.out.println("Ubicacion: " + ubicacionesEntradas.get(i));
            System.out.println("Asiento: " + asientosOcupados.get(i));
            System.out.println("Tipo de cliente: " + tiposCliente.get(i));
            System.out.println("Precio base: $" + preciosBase.get(i));
            System.out.println("Precio final: $" + preciosFinales.get(i));
        }
    }
    
    private static void generarBoleta(Scanner scanner) {
    if (numerosEntradas.isEmpty()) {
        System.out.println("No hay ventas registradas");
        return;
    }
    
    System.out.print("Ingrese numero de entrada: ");
    int numeroEntrada = scanner.nextInt();
    scanner.nextLine();
    
    int indice = numerosEntradas.indexOf(numeroEntrada);
    if (indice == -1) {
        System.out.println("Entrada no encontrada");
        return;
    }
    
    generarBoletaVenta(indice);
    
   }
    
    private static void generarBoletaVenta(int indice) {
        System.out.println("\n========= BOLETA DE VENTA =========");
        System.out.println("Teatro Moro");
        System.out.println("Numero de entrada: " + numerosEntradas.get(indice));
        System.out.println("Ubicacion: " + ubicacionesEntradas.get(indice));
        System.out.println("Asiento: " + asientosOcupados.get(indice));
        System.out.println("Tipo de cliente: " + tiposCliente.get(indice));
        System.out.println("Precio base: $" + preciosBase.get(indice));
        System.out.println("Descuento aplicado: $" + 
                         (preciosBase.get(indice) - preciosFinales.get(indice)));
        System.out.println("Precio final: $" + preciosFinales.get(indice));
        System.out.println("Fecha y hora: " + new Date());
        System.out.println("==================================");
        System.out.println("Gracias por su compra en Teatro Moro");
        System.out.println("Esperamos que disfrute de la funcion");
        System.out.println("Su apoyo contribuye a mantener viva");
        System.out.println("la magia del teatro en nuestra comunidad");
        System.out.println("==================================");
        
        // Agregar una pausa de 1 segundos
        try {
        Thread.sleep(1000); // 1000 milisegundos = 1 segundo
        } catch (InterruptedException e) {
        // Manejar la excepción (raramente ocurre)
        System.out.println("La pausa fue interrumpida");
        }
    }
    
    
    private static void mostrarIngresosTotales() {
        System.out.println("\n=== INGRESOS TOTALES ===");
        System.out.println("Total de ventas: " + numerosEntradas.size());
        System.out.println("Total de ingresos: $" + totalIngresos);
        System.out.println("Descuentos aplicados: " + totalDescuentosAplicados);
    }
    
    private static void mostrarPlanoTeatro() {
        System.out.println("\n=== PLANO DEL TEATRO ===");
        System.out.println("\n********************ESCENARIO********************");
        
        // Mostrar VIP
        System.out.println("\nZONA VIP - $" + PRECIO_VIP);
        mostrarAsientos(asientosVIP, 2);
        
        // Mostrar Platea
        System.out.println("\nZONA PLATEA - $" + PRECIO_PLATEA);
        mostrarAsientos(asientosPlatea, 3);
        
        // Mostrar General
        System.out.println("\nZONA GENERAL - $" + PRECIO_GENERAL);
        mostrarAsientos(asientosGeneral, 5);
    }
    
    private static void mostrarAsientos(List<Boolean> asientos, int filas) {
        int asientosPorFila = asientos.size() / filas;
        for (int fila = 0; fila < filas; fila++) {
            System.out.print("Fila " + (fila + 1) + ": ");
            for (int i = fila * asientosPorFila; i < (fila + 1) * asientosPorFila; i++) {
                System.out.print(asientos.get(i) ? "  X  " : String.format(" %3d ", i + 1));
            }
            System.out.println();
        }
    }
}