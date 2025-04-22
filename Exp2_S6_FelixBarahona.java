/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp_s6_felixbarahona;

/**
 *
 * @author felix
 */
import java.util.*;

public class Exp2_S6_FelixBarahona {
    // Variables estaticas (de clase) para estadisticas globales
    private static int totalEntradasVendidas = 0;
    private static double totalIngresos = 0;
    private static int totalDescuentosAplicados = 0;
    
    // Variables para el sistema de reservas
    private static final int TIEMPO_LIMITE_RESERVA = 15; // minutos
    private static ArrayList<Reserva> reservasActivas = new ArrayList<>();
    
    // Variables de instancia para informacion persistente sobre entradas
    private int numeroEntrada;
    private String ubicacion;
    private double precioBase;
    private double precioFinal;
    private String tipoCliente;
    private int numeroAsiento;
    
    // Arreglos para almacenar informacion de entradas vendidas (maximo 100)
    private static int[] numerosEntradas = new int[100];
    private static String[] ubicacionesEntradas = new String[100];
    private static String[] tiposCliente = new String[100];
    private static double[] preciosBase = new double[100];
    private static double[] preciosFinales = new double[100];
    private static int[] asientosOcupados = new int[100];
    
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
    
    // Clase interna para manejar reservas
    private static class Reserva {
        int numeroAsiento;
        String ubicacion;
        long tiempoReserva;
        String nombreCliente;
    }
    
    // Clase para manejo de depuracion
    private static class Debug {
        private static final boolean ENABLED = true; // Activar/desactivar debugging
        
        public static void log(String area, String mensaje) {
            if (ENABLED) {
                System.out.println("[DEBUG - " + area + "] " + mensaje);
            }
        }
        
        public static void logVariable(String area, String nombreVariable, Object valor) {
            if (ENABLED) {
                System.out.println("[DEBUG - " + area + "] " + nombreVariable + " = " + valor);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Exp2_S6_FelixBarahona sistema = new Exp2_S6_FelixBarahona(); // Crear instancia de la clase
        boolean continuar = true;
        
        System.out.println("Bienvenido al Sistema de Ventas de " + NOMBRE_TEATRO + "!");
        System.out.println("Capacidad total: " + CAPACIDAD_TOTAL + " asientos");
        
        while (continuar) {
            try {
                // Limpiar reservas expiradas antes de mostrar el menu
                sistema.limpiarReservasExpiradas();
                
                // Menu principal
                System.out.println("\n===== MENU PRINCIPAL =====");
                System.out.println("1. Reservar entradas");
                System.out.println("2. Convertir reserva a compra");
                System.out.println("3. Comprar entradas directamente");
                System.out.println("4. Modificar una venta existente");
                System.out.println("5. Imprimir boleta");
                System.out.println("6. Promociones");
                System.out.println("7. Busqueda de entradas");
                System.out.println("8. Eliminacion de entradas");
                System.out.println("9. Ver estado del sistema (Debug)");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        sistema.reservarEntrada(scanner);
                        break;
                    case 2:
                        sistema.convertirReservaACompra(scanner);
                        break;
                    case 3:
                        sistema.venderEntrada(scanner);
                        break;
                    case 4:
                        sistema.modificarVenta(scanner);
                        break;
                    case 5:
                        sistema.imprimirBoleta(scanner);
                        break;
                    case 6:
                        sistema.mostrarPromociones();
                        break;
                    case 7:
                        sistema.buscarEntradas(scanner);
                        break;
                    case 8:
                        sistema.eliminarEntrada(scanner);
                        break;
                    case 9:
                        sistema.mostrarEstadoSistema();
                        break;
                    case 0:
                        System.out.println("Gracias por utilizar el sistema. Hasta pronto!");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor valido.");
                System.out.println("Detalle del error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        scanner.close();
    }
    
    // Metodo para reservar entradas
    private void reservarEntrada(Scanner scanner) {
        // Punto de depuracion: Inicio del proceso de reserva
        Debug.log("RESERVA", "Iniciando proceso de reserva");

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

        String ubicacion;
        double precioBase;

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
            return;
        }

        // Solicitar nombre del cliente
        System.out.print("Ingrese su nombre: ");
        String nombreCliente = scanner.nextLine();

        // Crear la reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.numeroAsiento = numeroAsiento;
        nuevaReserva.ubicacion = ubicacion;
        nuevaReserva.tiempoReserva = System.currentTimeMillis(); // Tiempo actual en milisegundos
        nuevaReserva.nombreCliente = nombreCliente;

        reservasActivas.add(nuevaReserva);

        // Marcar el asiento como reservado (temporalmente)
        marcarAsientoOcupado(ubicacion, numeroAsiento);

        System.out.println("\nReserva realizada con exito.");
        System.out.println("Asiento " + numeroAsiento + " en " + ubicacion + " reservado a nombre de " + nombreCliente + ".");
        System.out.println("Tiene " + TIEMPO_LIMITE_RESERVA + " minutos para completar la compra.");

        // Punto de depuracion: Reserva creada
        Debug.log("RESERVA", "Reserva creada para " + nombreCliente + " en " + ubicacion + " " + numeroAsiento);
    }
    
    // Metodo para convertir reserva a compra
    private void convertirReservaACompra(Scanner scanner) {
        // Punto de depuracion: Inicio de conversion de reserva
        Debug.log("RESERVA", "Iniciando proceso de conversion de reserva a compra");

        if (reservasActivas.isEmpty()) {
            System.out.println("No hay reservas activas en el sistema.");
            return;
        }

        // Mostrar reservas activas
        System.out.println("\n===== RESERVAS ACTIVAS =====");
        for (int i = 0; i < reservasActivas.size(); i++) {
            Reserva reserva = reservasActivas.get(i);
            long tiempoTranscurrido = (System.currentTimeMillis() - reserva.tiempoReserva) / 60000; // Convertir a minutos
            
            if (tiempoTranscurrido < TIEMPO_LIMITE_RESERVA) {
                System.out.println((i + 1) + ". Asiento " + reserva.numeroAsiento + 
                                 " en " + reserva.ubicacion + 
                                 " - Cliente: " + reserva.nombreCliente +
                                 " (Tiempo restante: " + (TIEMPO_LIMITE_RESERVA - tiempoTranscurrido) + " minutos)");
            }
        }

        // Solicitar numero de reserva
        System.out.print("\nSeleccione el numero de reserva a convertir: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (seleccion < 1 || seleccion > reservasActivas.size()) {
            System.out.println("Numero de reserva invalido.");
            return;
        }

        Reserva reservaSeleccionada = reservasActivas.get(seleccion - 1);
        long tiempoTranscurrido = (System.currentTimeMillis() - reservaSeleccionada.tiempoReserva) / 60000;

        // Verificar si la reserva aun es valida
        if (tiempoTranscurrido >= TIEMPO_LIMITE_RESERVA) {
            System.out.println("La reserva ha expirado. El tiempo limite es de " + TIEMPO_LIMITE_RESERVA + " minutos.");
            // Liberar el asiento
            liberarAsiento(reservaSeleccionada.ubicacion, reservaSeleccionada.numeroAsiento);
            reservasActivas.remove(seleccion - 1);
            return;
        }

        // Punto de depuracion: Reserva valida, procediendo con la compra
        Debug.log("RESERVA", "Reserva valida, procesando compra");

        // Variables locales para almacenar temporalmente datos
        int edad;
        double descuento = 0;
        boolean descuentoCumpleanos = false;
        double precioBase = obtenerPrecioBase(reservaSeleccionada.ubicacion);

        // Solicitar edad para determinar descuentos
        System.out.print("Ingrese la edad del cliente: ");
        edad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Determinar tipo de cliente y descuento
        String tipoCliente;
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
            double descuentoCumple = precioBase * 0.5; // 50% de descuento
            if (descuentoCumple > descuento) {
                descuento = descuentoCumple;
                tipoCliente += " (Promocion Cumpleanos)";
            }
        }

        // Calcular precio final
        double precioFinal = precioBase - descuento;

        // Incrementar contador y crear nueva entrada
        totalEntradasVendidas++;
        int numeroEntrada = totalEntradasVendidas;

        // Guardar informacion de la entrada en los arreglos
        int indice = totalEntradasVendidas - 1;
        numerosEntradas[indice] = numeroEntrada;
        ubicacionesEntradas[indice] = reservaSeleccionada.ubicacion;
        tiposCliente[indice] = tipoCliente;
        preciosBase[indice] = precioBase;
        preciosFinales[indice] = precioFinal;
        asientosOcupados[indice] = reservaSeleccionada.numeroAsiento;

        // Actualizar estadisticas
        totalIngresos += precioFinal;
        if (descuento > 0) {
            totalDescuentosAplicados++;
        }

        // Generar boleta
        generarBoleta(numeroEntrada, reservaSeleccionada.ubicacion, reservaSeleccionada.numeroAsiento, 
                    tipoCliente, precioBase, precioFinal);

        // Si es cumpleanos, procesar entrada gratis
        if (descuentoCumpleanos) {
            procesarEntradaGratisCumpleanos(scanner, reservaSeleccionada.ubicacion, tipoCliente);
        }

        // Eliminar la reserva de las reservas activas
        reservasActivas.remove(seleccion - 1);

        // Punto de depuracion: Compra completada
        Debug.log("RESERVA", "Conversion de reserva a compra completada exitosamente");
    }
    
    // Metodo para vender entradas (venta directa)
    private void venderEntrada(Scanner scanner) {
        boolean seguirComprando = true;
        
        while (seguirComprando) {
            // Variables locales para almacenar temporalmente datos
            int edad;
            double descuento = 0;
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
            
            // Asignar ubicacion y precio base a las variables de instancia
            switch (opcionUbicacion) {
                case 1:
                    this.ubicacion = "VIP";
                    this.precioBase = PRECIO_VIP;
                    break;
                case 2:
                    this.ubicacion = "Platea";
                    this.precioBase = PRECIO_PLATEA;
                    break;
                case 3:
                    this.ubicacion = "General";
                    this.precioBase = PRECIO_GENERAL;
                    break;
                default:
                    System.out.println("Ubicacion no valida. Volviendo al menu principal.");
                    return;
            }
            
            // Solicitar numero de asiento
            System.out.print("Ingrese numero de asiento: ");
            this.numeroAsiento = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Verificar disponibilidad del asiento
            if (!verificarDisponibilidadAsiento(this.ubicacion, this.numeroAsiento)) {
                System.out.println("El asiento seleccionado no esta disponible o no existe.");
                
                // Preguntar si desea intentar con otro asiento
                System.out.print("Desea intentar con otro asiento? (S/N): ");
                String respuestaOtroAsiento = scanner.nextLine().toUpperCase();
                if (respuestaOtroAsiento.equals("S")) {
                    continue; // Volver al inicio del ciclo
                } else {
                    return; // Volver al menu principal
                }
            }
            
            // Solicitar edad para determinar descuentos
            System.out.print("Ingrese la edad del cliente: ");
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Determinar tipo de cliente y descuento
            if (edad >= 60) {
                this.tipoCliente = "Tercera Edad";
                descuento = this.precioBase * DESCUENTO_TERCERA_EDAD;
            } else if (edad <= 18) {
                this.tipoCliente = "Estudiante";
                descuento = this.precioBase * DESCUENTO_ESTUDIANTE;
            } else {
                this.tipoCliente = "Regular";
            }
            
            // Preguntar si es cumpleanos
            System.out.print("Es el cumpleanos del cliente? (S/N): ");
            String respuestaCumpleanos = scanner.nextLine().toUpperCase();
            
            if (respuestaCumpleanos.equals("S")) {
                descuentoCumpleanos = true;
                System.out.println("Feliz cumpleanos! Promocion aplicada: Una entrada gratis y otra a mitad de precio.");
                
                // Informar que los descuentos no son acumulables
                System.out.println("IMPORTANTE: Los descuentos no son acumulables. Se aplicara el descuento mas beneficioso.");
                
                // Calcular descuento por cumpleanos (mitad de precio)
                double descuentoCumple = this.precioBase * 0.5; // 50% de descuento
                
                // Comparar descuentos y elegir el mas beneficioso
                if (descuentoCumple > descuento) {
                    descuento = descuentoCumple;
                    this.tipoCliente += " (Promocion Cumpleanos)";
                } else {
                    // Mantener el descuento por edad que es mas beneficioso
                    System.out.println("Se aplicara el descuento por " + this.tipoCliente + " ya que es mas beneficioso.");
                }
            } else if (descuento > 0) {
                // Si no es cumpleanos pero tiene descuento por edad, informar que no hay otros descuentos acumulables
                System.out.println("IMPORTANTE: Se aplica descuento por " + this.tipoCliente + ". Los descuentos no son acumulables.");
            }
            
            // Calcular precio final y asignarlo a la variable de instancia
            this.precioFinal = this.precioBase - descuento;
            
            // Marcar asiento como ocupado
            marcarAsientoOcupado(this.ubicacion, this.numeroAsiento);
            
            // Incrementar contador y asignar numero de entrada a la variable de instancia
            totalEntradasVendidas++;
            this.numeroEntrada = totalEntradasVendidas;
            
            // Guardar informacion de la entrada en los arreglos
            int indice = totalEntradasVendidas - 1;
            numerosEntradas[indice] = this.numeroEntrada;
            ubicacionesEntradas[indice] = this.ubicacion;
            tiposCliente[indice] = this.tipoCliente;
            preciosBase[indice] = this.precioBase;
            preciosFinales[indice] = this.precioFinal;
            asientosOcupados[indice] = this.numeroAsiento;
            
            // Actualizar estadisticas
            totalIngresos += this.precioFinal;
            if (descuento > 0) {
                totalDescuentosAplicados++;
            }
            
            // Generar boleta
            generarBoleta(this.numeroEntrada, this.ubicacion, this.numeroAsiento, 
                        this.tipoCliente, this.precioBase, this.precioFinal);
            
            // Si es cumpleanos, informar sobre la entrada gratis
            if (descuentoCumpleanos) {
                procesarEntradaGratisCumpleanos(scanner, this.ubicacion, this.tipoCliente);
            }
            
            // Preguntar si desea seguir comprando
            System.out.print("\nDesea comprar otra entrada? (S/N): ");
            String respuestaSeguirComprando = scanner.nextLine().toUpperCase();
            
            if (!respuestaSeguirComprando.equals("S")) {
                seguirComprando = false;
                System.out.println("Volviendo al menu principal...");
            }
        }
    }
    
    // Metodo para procesar entrada gratis por cumpleanos
    private void procesarEntradaGratisCumpleanos(Scanner scanner, String ubicacion, String tipoCliente) {
        System.out.println("\nAdemas recibe una entrada GRATIS para la misma ubicacion!");
        
        // Solicitar numero de asiento para la entrada gratis
        System.out.print("Ingrese numero de asiento para la entrada gratis: ");
        int asientoGratis = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Verificar disponibilidad del asiento
        if (!verificarDisponibilidadAsiento(ubicacion, asientoGratis)) {
            System.out.println("El asiento seleccionado no esta disponible o no existe. No se pudo asignar la entrada gratis.");
            return;
        }
        
        // Marcar asiento como ocupado
        marcarAsientoOcupado(ubicacion, asientoGratis);
        
        // Incrementar contador y asignar numero de entrada
        totalEntradasVendidas++;
        int numeroEntrada = totalEntradasVendidas;
        
        // Guardar informacion de la entrada gratis en los arreglos
        int indice = totalEntradasVendidas - 1;
        numerosEntradas[indice] = numeroEntrada;
        ubicacionesEntradas[indice] = ubicacion;
        tiposCliente[indice] = tipoCliente + " (Gratis por Cumpleanos)";
        preciosBase[indice] = obtenerPrecioBase(ubicacion);
        preciosFinales[indice] = 0; // Gratis
        asientosOcupados[indice] = asientoGratis;
        
        // Generar boleta para la entrada gratis
        generarBoleta(numeroEntrada, ubicacion, asientoGratis, 
                    tipoCliente + " (Gratis por Cumpleanos)", 
                    obtenerPrecioBase(ubicacion), 0);
        
        System.out.println("Entrada gratis asignada al asiento " + asientoGratis + 
                          " en ubicacion " + ubicacion);
    }
    
    // Metodo para modificar una venta existente
    private void modificarVenta(Scanner scanner) {
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas vendidas para modificar.");
            return;
        }
        
        System.out.print("Ingrese el numero de entrada a modificar: ");
        int numeroEntrada = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        int indiceModificar = -1;
        
        // Buscar la entrada por numero
        for (int i = 0; i < totalEntradasVendidas; i++) {
            if (numerosEntradas[i] == numeroEntrada) {
                indiceModificar = i;
                break;
            }
        }
        
        if (indiceModificar == -1) {
            System.out.println("No se encontro ninguna entrada con el numero " + numeroEntrada);
            return;
        }
        
        // Mostrar informacion actual de la entrada
        System.out.println("\nInformacion actual de la entrada:");
        mostrarInfoEntrada(indiceModificar);
        
        // Menu de modificacion
        System.out.println("\nQue desea modificar?");
        System.out.println("1. Ubicacion y asiento");
        System.out.println("2. Tipo de cliente (aplicar descuento)");
        System.out.println("3. Cancelar");
        System.out.print("Seleccione una opcion: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        switch (opcion) {
            case 1:
                // Liberar el asiento actual
                liberarAsiento(ubicacionesEntradas[indiceModificar], asientosOcupados[indiceModificar]);
                
                // Mostrar plano actualizado
                mostrarPlanoTeatro();
                
                // Solicitar nueva ubicacion
                System.out.println("\nUbicaciones disponibles:");
                System.out.println("1. VIP ($" + PRECIO_VIP + ")");
                System.out.println("2. Platea ($" + PRECIO_PLATEA + ")");
                System.out.println("3. General ($" + PRECIO_GENERAL + ")");
                System.out.print("Seleccione nueva ubicacion (1-3): ");
                
                int opcionUbicacion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                String nuevaUbicacion;
                double nuevoPrecioBase;
                
                switch (opcionUbicacion) {
                    case 1:
                        nuevaUbicacion = "VIP";
                        nuevoPrecioBase = PRECIO_VIP;
                        break;
                    case 2:
                        nuevaUbicacion = "Platea";
                        nuevoPrecioBase = PRECIO_PLATEA;
                        break;
                    case 3:
                        nuevaUbicacion = "General";
                        nuevoPrecioBase = PRECIO_GENERAL;
                        break;
                    default:
                        System.out.println("Ubicacion no valida. Operacion cancelada.");
                        // Volver a marcar el asiento original como ocupado
                        marcarAsientoOcupado(ubicacionesEntradas[indiceModificar], asientosOcupados[indiceModificar]);
                        return;
                }
                
                // Solicitar nuevo numero de asiento
                System.out.print("Ingrese nuevo numero de asiento: ");
                int nuevoAsiento = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                // Verificar disponibilidad del nuevo asiento
                if (!verificarDisponibilidadAsiento(nuevaUbicacion, nuevoAsiento)) {
                    System.out.println("El asiento seleccionado no esta disponible o no existe. Operacion cancelada.");
                    // Volver a marcar el asiento original como ocupado
                    marcarAsientoOcupado(ubicacionesEntradas[indiceModificar], asientosOcupados[indiceModificar]);
                    return;
                }
                
                // Calcular diferencia de precio
                double diferenciaPrecio = nuevoPrecioBase - preciosBase[indiceModificar];
                double nuevoPrecioFinal = preciosFinales[indiceModificar] + diferenciaPrecio;
                
                // Actualizar informacion de la entrada
                ubicacionesEntradas[indiceModificar] = nuevaUbicacion;
                asientosOcupados[indiceModificar] = nuevoAsiento;
                preciosBase[indiceModificar] = nuevoPrecioBase;
                preciosFinales[indiceModificar] = nuevoPrecioFinal;
                
                // Marcar el nuevo asiento como ocupado
                marcarAsientoOcupado(nuevaUbicacion, nuevoAsiento);
                
                // Actualizar ingresos totales
                totalIngresos += diferenciaPrecio;
                
                System.out.println("\nEntrada modificada con exito.");
                System.out.println("Nueva ubicacion: " + nuevaUbicacion);
                System.out.println("Nuevo asiento: " + nuevoAsiento);
                System.out.println("Diferencia de precio: $" + diferenciaPrecio);
                System.out.println("Nuevo precio final: $" + nuevoPrecioFinal);
                break;
                
            case 2:
                // Solicitar nueva edad para determinar descuentos
                System.out.print("Ingrese la nueva edad del cliente: ");
                int nuevaEdad = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                // Determinar nuevo tipo de cliente y descuento
                String nuevoTipoCliente;
                double nuevoDescuento = 0;
                
                if (nuevaEdad >= 60) {
                    nuevoTipoCliente = "Tercera Edad";
                    nuevoDescuento = preciosBase[indiceModificar] * DESCUENTO_TERCERA_EDAD;
                } else if (nuevaEdad <= 18) {
                    nuevoTipoCliente = "Estudiante";
                    nuevoDescuento = preciosBase[indiceModificar] * DESCUENTO_ESTUDIANTE;
                } else {
                    nuevoTipoCliente = "Regular";
                }
                
                // Preguntar si es cumpleanos
                System.out.print("Es el cumpleanos del cliente? (S/N): ");
                String respuestaCumpleanos = scanner.nextLine().toUpperCase();
                
                if (respuestaCumpleanos.equals("S")) {
                    double descuentoCumple = preciosBase[indiceModificar] * 0.5; // 50% de descuento
                    if (descuentoCumple > nuevoDescuento) {
                        nuevoDescuento = descuentoCumple;
                        nuevoTipoCliente += " (Promocion Cumpleanos)";
                    }
                }
                
                // Calcular nuevo precio final
                double nuevoPrecio = preciosBase[indiceModificar] - nuevoDescuento;
                
                // Actualizar informacion de la entrada
                tiposCliente[indiceModificar] = nuevoTipoCliente;
                preciosFinales[indiceModificar] = nuevoPrecio;
                
                // Actualizar ingresos totales
                double diferencia = nuevoPrecio - preciosFinales[indiceModificar];
                totalIngresos += diferencia;
                
                System.out.println("\nEntrada modificada con exito.");
                System.out.println("Nuevo tipo de cliente: " + nuevoTipoCliente);
                System.out.println("Nuevo descuento aplicado: $" + nuevoDescuento);
                System.out.println("Nuevo precio final: $" + nuevoPrecio);
                break;
                
            case 3:
                System.out.println("Operacion cancelada.");
                break;
                
            default:
                System.out.println("Opcion no valida.");
        }
    }
    
    // Metodo para imprimir boleta
    private void imprimirBoleta(Scanner scanner) {
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas vendidas para imprimir boleta.");
            return;
        }
        
        System.out.print("Ingrese el numero de entrada para imprimir boleta: ");
        int numeroEntrada = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        int indice = -1;
        
        // Buscar la entrada por numero
        for (int i = 0; i < totalEntradasVendidas; i++) {
            if (numerosEntradas[i] == numeroEntrada) {
                indice = i;
                break;
            }
        }
        
        if (indice == -1) {
            System.out.println("No se encontro ninguna entrada con el numero " + numeroEntrada);
            return;
        }
        
        // Generar boleta
        generarBoleta(numerosEntradas[indice], ubicacionesEntradas[indice], 
                    asientosOcupados[indice], tiposCliente[indice], 
                    preciosBase[indice], preciosFinales[indice]);
    }
    
    // Metodo para mostrar promociones disponibles
    private void mostrarPromociones() {
        System.out.println("\n===== PROMOCIONES DISPONIBLES =====");
        System.out.println("1. Promocion Cumpleanos:");
        System.out.println("   - Una entrada GRATIS");
        System.out.println("   - Una entrada a MITAD DE PRECIO");
        System.out.println("   * No acumulable con otros descuentos");
        System.out.println("\n2. Descuentos por tipo de cliente:");
        System.out.println("   - Estudiantes: 10% de descuento");
        System.out.println("   - Tercera Edad: 15% de descuento");
        System.out.println("\nIMPORTANTE: Todos los descuentos NO son acumulables.");
        System.out.println("Se aplicara siempre el descuento mas beneficioso para el cliente.");
    }
    
    // Metodo para buscar entradas
    private void buscarEntradas(Scanner scanner) {
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
    private void eliminarEntrada(Scanner scanner) {
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
        
        System.out.print("Esta seguro de eliminar esta entrada? (S/N): ");
        String confirmacion = scanner.nextLine().toUpperCase();
        
        if (confirmacion.equals("S")) {
            // Liberar el asiento
            liberarAsiento(ubicacionesEntradas[indiceEliminar], asientosOcupados[indiceEliminar]);
            
            // Restar el precio de la entrada eliminada del total de ingresos
            totalIngresos -= preciosFinales[indiceEliminar];
            
            // Desplazar elementos para eliminar la entrada
            for (int i = indiceEliminar; i < totalEntradasVendidas - 1; i++) {
                numerosEntradas[i] = numerosEntradas[i + 1];
                ubicacionesEntradas[i] = ubicacionesEntradas[i + 1];
                tiposCliente[i] = tiposCliente[i + 1];
                preciosBase[i] = preciosBase[i + 1];
                preciosFinales[i] = preciosFinales[i + 1];
                asientosOcupados[i] = asientosOcupados[i + 1];
            }
            
            totalEntradasVendidas--;
            System.out.println("Entrada eliminada con exito.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
    
    // Metodo para mostrar informacion de una entrada
    private void mostrarInfoEntrada(int indice) {
        System.out.println("\n----- Entrada #" + numerosEntradas[indice] + " -----");
        System.out.println("Ubicacion: " + ubicacionesEntradas[indice]);
        System.out.println("Asiento: " + asientosOcupados[indice]);
        System.out.println("Tipo de cliente: " + tiposCliente[indice]);
        System.out.println("Precio base: $" + preciosBase[indice]);
        System.out.println("Precio final: $" + preciosFinales[indice]);
    }
    
    // Metodo para verificar disponibilidad de asiento
    private boolean verificarDisponibilidadAsiento(String ubicacion, int numeroAsiento) {
        Debug.log("ASIENTOS", "Verificando disponibilidad de asiento");
        Debug.logVariable("ASIENTOS", "Ubicacion solicitada", ubicacion);
        Debug.logVariable("ASIENTOS", "Numero de asiento", numeroAsiento);
        
        boolean disponible = false;
        if (ubicacion.equals("VIP")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosVIP.length) {
                Debug.log("ASIENTOS", "Asiento VIP fuera de rango");
                return false;
            }
            disponible = !asientosVIP[numeroAsiento - 1];
        } else if (ubicacion.equals("Platea")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosPlatea.length) {
                Debug.log("ASIENTOS", "Asiento Platea fuera de rango");
                return false;
            }
            disponible = !asientosPlatea[numeroAsiento - 1];
        } else if (ubicacion.equals("General")) {
            if (numeroAsiento < 1 || numeroAsiento > asientosGeneral.length) {
                Debug.log("ASIENTOS", "Asiento General fuera de rango");
                return false;
            }
            disponible = !asientosGeneral[numeroAsiento - 1];
        }
        
        Debug.logVariable("ASIENTOS", "Disponibilidad", disponible);
        return disponible;
    }
    
    // Metodo para marcar asiento como ocupado
    private void marcarAsientoOcupado(String ubicacion, int numeroAsiento) {
        Debug.log("ASIENTOS", "Marcando asiento como ocupado");
        Debug.logVariable("ASIENTOS", "Ubicacion", ubicacion);
        Debug.logVariable("ASIENTOS", "Numero de asiento", numeroAsiento);
        
        if (ubicacion.equals("VIP")) {
            asientosVIP[numeroAsiento - 1] = true;
        } else if (ubicacion.equals("Platea")) {
            asientosPlatea[numeroAsiento - 1] = true;
        } else if (ubicacion.equals("General")) {
            asientosGeneral[numeroAsiento - 1] = true;
        }
    }
    
    // Metodo para liberar un asiento
    private void liberarAsiento(String ubicacion, int numeroAsiento) {
        Debug.log("ASIENTOS", "Liberando asiento");
        Debug.logVariable("ASIENTOS", "Ubicacion", ubicacion);
        Debug.logVariable("ASIENTOS", "Numero de asiento", numeroAsiento);
        
        if (ubicacion.equals("VIP")) {
            asientosVIP[numeroAsiento - 1] = false;
        } else if (ubicacion.equals("Platea")) {
            asientosPlatea[numeroAsiento - 1] = false;
        } else if (ubicacion.equals("General")) {
            asientosGeneral[numeroAsiento - 1] = false;
        }
    }
    
    // Metodo para mostrar el plano del teatro
    private void mostrarPlanoTeatro() {
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
    private void mostrarFila(boolean[] asientos, int inicio, int fin, String etiqueta) {
        System.out.println(etiqueta);
        for (int i = inicio; i < fin; i++) {
            System.out.print(asientos[i] ? "  X  " : String.format(" %3d ", i + 1));
        }
        System.out.println();
    }
    
    // Metodo para limpiar reservas expiradas
    private void limpiarReservasExpiradas() {
        Debug.log("RESERVA", "Verificando reservas expiradas");
        
        Iterator<Reserva> iterator = reservasActivas.iterator();
        while (iterator.hasNext()) {
            Reserva reserva = iterator.next();
            long tiempoTranscurrido = (System.currentTimeMillis() - reserva.tiempoReserva) / 60000;
            
            if (tiempoTranscurrido >= TIEMPO_LIMITE_RESERVA) {
                // Liberar el asiento
                liberarAsiento(reserva.ubicacion, reserva.numeroAsiento);
                iterator.remove();
                Debug.log("RESERVA", "Reserva expirada eliminada - Asiento " + 
                         reserva.numeroAsiento + " en " + reserva.ubicacion);
            }
        }
    }
    
    // Metodo para obtener el precio base segun la ubicacion
    private double obtenerPrecioBase(String ubicacion) {
        switch (ubicacion) {
            case "VIP":
                return PRECIO_VIP;
            case "Platea":
                return PRECIO_PLATEA;
            case "General":
                return PRECIO_GENERAL;
            default:
                return 0;
        }
    }
    
    // Metodo para generar boleta
    private void generarBoleta(int numeroEntrada, String ubicacion, int numeroAsiento, 
                             String tipoCliente, double precioBase, double precioFinal) {
        Debug.log("BOLETA", "Iniciando generacion de boleta");
        Debug.logVariable("BOLETA", "Numero de entrada", numeroEntrada);
        Debug.logVariable("BOLETA", "Ubicacion", ubicacion);
        Debug.logVariable("BOLETA", "Asiento", numeroAsiento);
        Debug.logVariable("BOLETA", "Tipo de cliente", tipoCliente);
        Debug.logVariable("BOLETA", "Precio base", precioBase);
        Debug.logVariable("BOLETA", "Precio final", precioFinal);
        Debug.logVariable("BOLETA", "Descuento aplicado", precioBase - precioFinal);
        
        System.out.println("\n========= BOLETA DE VENTA =========");
        System.out.println("Teatro: " + NOMBRE_TEATRO);
        System.out.println("Numero de entrada: " + numeroEntrada);
        System.out.println("Ubicacion: " + ubicacion);
        System.out.println("Asiento: " + numeroAsiento);
        System.out.println("Tipo de cliente: " + tipoCliente);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento aplicado: $" + (precioBase - precioFinal));
        System.out.println("Precio final: $" + precioFinal);
        System.out.println("Fecha y hora: " + new java.util.Date());
        System.out.println("==================================");
        
        Debug.log("BOLETA", "Boleta generada exitosamente");
    }
    
    // Metodo para mostrar el estado del sistema (debug)
    private void mostrarEstadoSistema() {
        Debug.log("SISTEMA", "Estado actual del sistema");
        Debug.logVariable("SISTEMA", "Total entradas vendidas", totalEntradasVendidas);
        Debug.logVariable("SISTEMA", "Total ingresos", totalIngresos);
        Debug.logVariable("SISTEMA", "Total descuentos aplicados", totalDescuentosAplicados);
        Debug.logVariable("SISTEMA", "Reservas activas", reservasActivas.size());
        
        // Mostrar disponibilidad por zona
        int vipDisponibles = contarAsientosDisponibles(asientosVIP);
        int plateaDisponibles = contarAsientosDisponibles(asientosPlatea);
        int generalDisponibles = contarAsientosDisponibles(asientosGeneral);
        
        System.out.println("\n===== ESTADO DEL SISTEMA =====");
        System.out.println("Total entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Total ingresos: $" + totalIngresos);
        System.out.println("Total descuentos aplicados: " + totalDescuentosAplicados);
        System.out.println("Reservas activas: " + reservasActivas.size());
        System.out.println("\nDisponibilidad de asientos:");
        System.out.println("- VIP: " + vipDisponibles + "/" + asientosVIP.length);
        System.out.println("- Platea: " + plateaDisponibles + "/" + asientosPlatea.length);
        System.out.println("- General: " + generalDisponibles + "/" + asientosGeneral.length);
    }
    
    // Metodo para contar asientos disponibles
    private int contarAsientosDisponibles(boolean[] asientos) {
        int disponibles = 0;
        for (boolean asiento : asientos) {
            if (!asiento) disponibles++;
        }
        return disponibles;
    }
}