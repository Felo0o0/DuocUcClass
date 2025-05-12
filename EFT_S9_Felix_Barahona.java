/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_felix_barahona;

/**
 *
 * @author felix
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Clase principal del sistema de venta de entradas para Teatro Moro
 * Esta clase inicia el sistema y contiene el metodo main
 */
public class EFT_S9_Felix_Barahona { 
    public static void main(String[] args) {
        try {
            // Inicializacion del sistema
            TheaterSystem system = new TheaterSystem();
            system.start();
        } catch (Exception e) {
            ConsoleUI.printError("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/**
 * Excepciones personalizadas para el sistema de teatro
 */
class TheaterException extends Exception {
    public TheaterException(String message) {
        super(message);
    }
}

class InvalidSeatException extends TheaterException {
    public InvalidSeatException(String message) {
        super(message);
    }
}

class SeatNotAvailableException extends TheaterException {
    public SeatNotAvailableException(String message) {
        super(message);
    }
}

class InvalidSectionException extends TheaterException {
    public InvalidSectionException(String message) {
        super(message);
    }
}

class InvalidCustomerDataException extends TheaterException {
    public InvalidCustomerDataException(String message) {
        super(message);
    }
}

/**
 * Clase para mejorar la interfaz de usuario con colores y formato
 */
class ConsoleUI {
    // Códigos ANSI para colores
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    
    /**
     * Imprime un título con formato
     * @param title Texto del título
     */
    public static void printTitle(String title) {
        String border = "=".repeat(title.length() + 8);
        System.out.println("\n" + BLUE + border + RESET);
        System.out.println(BLUE + "    " + title + "    " + RESET);
        System.out.println(BLUE + border + RESET);
    }
    
    /**
     * Imprime un subtítulo con formato
     * @param subtitle Texto del subtítulo
     */
    public static void printSubtitle(String subtitle) {
        System.out.println("\n" + CYAN + "--- " + subtitle + " ---" + RESET);
    }
    
    /**
     * Imprime un mensaje de éxito
     * @param message Mensaje a mostrar
     */
    public static void printSuccess(String message) {
        System.out.println(GREEN + "OK " + message + RESET);
    }
    
    /**
     * Imprime un mensaje de error
     * @param message Mensaje a mostrar
     */
    public static void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }
    
    /**
     * Imprime un mensaje de advertencia
     * @param message Mensaje a mostrar
     */
    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠ " + message + RESET);
    }
    
    /**
     * Imprime una opción de menú
     * @param number Número de opción
     * @param description Descripción de la opción
     */
    public static void printMenuOption(int number, String description) {
        System.out.println(PURPLE + number + ". " + RESET + description);
    }
    
    /**
     * Imprime un mapa de asientos con colores
     * @param section Sección a mostrar
     */
    public static void printSeatMap(Section section) {
        // Encabezado de columnas
        System.out.print("   ");
        for (int j = 1; j <= section.columns; j++) {
            System.out.print(BLUE + j + " " + RESET);
        }
        System.out.println();
        
        // Filas de asientos
        for (int i = 0; i < section.rows; i++) {
            char row = (char) ('A' + i);
            System.out.print(BLUE + row + ": " + RESET);
            
            for (int j = 1; j <= section.columns; j++) {
                Seat seat = section.getSeat(row, j);
                if (seat.available) {
                    System.out.print(GREEN + "O " + RESET);
                } else {
                    System.out.print(RED + "X " + RESET);
                }
            }
            System.out.println();
        }
        System.out.println(GREEN + "O" + RESET + " = Disponible, " + 
                          RED + "X" + RESET + " = Ocupado");
    }
    
    /**
     * Imprime una boleta con formato
     * @param ticket Ticket vendido
     * @param originalPrice Precio original
     * @param discount Descuento aplicado
     */
    public static void printReceipt(Ticket ticket, double originalPrice, double discount) {
        String border = "=".repeat(40);
        System.out.println("\n" + YELLOW + border + RESET);
        System.out.println(YELLOW + "            BOLETA DE COMPRA            " + RESET);
        System.out.println(YELLOW + "            " + ticket.section.theater.name + "            " + RESET);
        System.out.println(YELLOW + border + RESET);
        System.out.println("Cliente: " + ticket.customer.name);
        System.out.println("Seccion: " + ticket.section.name);
        System.out.println("Asiento: " + ticket.seat.getPosition());
        System.out.println(YELLOW + "-".repeat(40) + RESET);
        System.out.println("Precio original: $" + originalPrice);
        System.out.println("Descuento aplicado: $" + discount);
        System.out.println(GREEN + "TOTAL A PAGAR: $" + ticket.price + RESET);
        System.out.println(YELLOW + border + RESET);
        System.out.println(CYAN + "Gracias por su compra!" + RESET);
        System.out.println(YELLOW + border + RESET);
    }
}

/**
 * Clase para validar datos de entrada
 */
class Validator {
    /**
     * Valida los datos del cliente
     * @param name Nombre del cliente
     * @param age Edad del cliente
     * @param gender Género del cliente
     * @throws InvalidCustomerDataException Si los datos son inválidos
     */
    public static void validateCustomerData(String name, int age, char gender) 
            throws InvalidCustomerDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidCustomerDataException("El nombre no puede estar vacio");
        }
        
        if (age < 0 || age > 120) {
            throw new InvalidCustomerDataException("La edad debe estar entre 0 y 120 anos");
        }
        
        if (gender != 'M' && gender != 'F') {
            throw new InvalidCustomerDataException("El genero debe ser M o F");
        }
    }
    
    /**
     * Valida la selección de sección
     * @param sectionId ID de la sección
     * @param maxSections Número máximo de secciones
     * @throws InvalidSectionException Si la sección es inválida
     */
    public static void validateSectionSelection(int sectionId, int maxSections) 
            throws InvalidSectionException {
        if (sectionId < 1 || sectionId > maxSections) {
            throw new InvalidSectionException(
                    "ID de seccion invalido. Debe estar entre 1 y " + maxSections);
        }
    }
    
    /**
     * Valida la selección de asiento
     * @param seatPosition Posición del asiento (ej: "A-1")
     * @throws InvalidSeatException Si el formato es inválido
     */
    public static void validateSeatFormat(String seatPosition) throws InvalidSeatException {
        if (seatPosition == null || !seatPosition.matches("[A-Z]-\\d+")) {
            throw new InvalidSeatException(
                    "Formato de asiento invalido. Debe ser LETRA-NUMERO (ej: A-1)");
        }
    }
}

/**
 * Clase para manejar la persistencia de datos
 */
class DataManager {
    private static final String SALES_FILE = "ventas_teatro.dat";
    
    /**
     * Guarda las ventas en un archivo
     * @param sales Lista de ventas a guardar
     * @throws IOException Si ocurre un error al guardar
     */
    public static void saveSales(ArrayList<Ticket> sales) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(SALES_FILE))) {
            out.writeObject(sales);
            System.out.println("Datos guardados correctamente en " + SALES_FILE);
        }
    }
    
    /**
     * Carga las ventas desde un archivo
     * @return Lista de ventas cargadas
     * @throws IOException Si ocurre un error al cargar
     * @throws ClassNotFoundException Si hay problemas con las clases
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Ticket> loadSales() throws IOException, ClassNotFoundException {
        File file = new File(SALES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(SALES_FILE))) {
            return (ArrayList<Ticket>) in.readObject();
        }
    }
    
    /**
     * Genera un reporte de ventas en un archivo de texto
     * @param sales Lista de ventas para el reporte
     * @throws IOException Si ocurre un error al generar el reporte
     */
    public static void generateSalesReport(ArrayList<Ticket> sales) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "reporte_ventas_" + dateFormat.format(new Date()) + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== REPORTE DE VENTAS - TEATRO MORO ===");
            writer.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.println("Total de ventas: " + sales.size());
            writer.println();
            
            double totalAmount = 0;
            
            for (int i = 0; i < sales.size(); i++) {
                Ticket ticket = sales.get(i);
                writer.println("Venta #" + (i+1));
                writer.println("  Cliente: " + ticket.customer.name);
                writer.println("  Edad: " + ticket.customer.age);
                writer.println("  Seccion: " + ticket.section.name);
                writer.println("  Asiento: " + ticket.seat.getPosition());
                writer.println("  Precio: $" + ticket.price);
                writer.println();
                
                totalAmount += ticket.price;
            }
            
            writer.println("TOTAL RECAUDADO: $" + totalAmount);
            System.out.println("Reporte generado: " + fileName);
        }
    }
}

/**
 * Clase mejorada para gestionar estadísticas y optimizar búsquedas
 */
class TheaterStats {
    private Map<String, Integer> sectionSalesCount;
    private Map<Character, Integer> customerAgeGroups;
    private double totalRevenue;
    
    public TheaterStats() {
        sectionSalesCount = new HashMap<>();
        customerAgeGroups = new HashMap<>();
        totalRevenue = 0;
    }
    
    /**
     * Registra una venta para estadísticas
     * @param ticket Ticket vendido
     */
    public void registerSale(Ticket ticket) {
        // Contar ventas por sección
        String sectionName = ticket.section.name;
        sectionSalesCount.put(sectionName, 
                sectionSalesCount.getOrDefault(sectionName, 0) + 1);
        
        // Agrupar clientes por edad
        char ageGroup = getAgeGroup(ticket.customer.age);
        customerAgeGroups.put(ageGroup, 
                customerAgeGroups.getOrDefault(ageGroup, 0) + 1);
        
        // Sumar ingresos
        totalRevenue += ticket.price;
    }
    
    /**
     * Determina el grupo de edad
     * @param age Edad del cliente
     * @return Grupo de edad (N: niño, J: joven, A: adulto, M: mayor)
     */
    private char getAgeGroup(int age) {
        if (age < 18) return 'N';
        if (age < 30) return 'J';
        if (age < 65) return 'A';
        return 'M';
    }
    
    /**
     * Obtiene la sección más vendida
     * @return Nombre de la sección más vendida
     */
    public String getMostPopularSection() {
        if (sectionSalesCount.isEmpty()) return "Ninguna";
        
        return sectionSalesCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }
    
    /**
     * Obtiene el grupo de edad con más compras
     * @return Descripción del grupo de edad más frecuente
     */
    public String getMostFrequentAgeGroup() {
        if (customerAgeGroups.isEmpty()) return "Ninguno";
        
        char group = customerAgeGroups.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
        
        switch (group) {
            case 'N': return "Ninos (menores de 18)";
            case 'J': return "Jovenes (18-29)";
            case 'A': return "Adultos (30-64)";
            case 'M': return "Mayores (65+)";
            default: return "Desconocido";
        }
    }
    
    /**
     * Obtiene el ingreso total
     * @return Total de ingresos por ventas
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }
    
    /**
     * Muestra estadísticas completas
     */
    public void showStats() {
        ConsoleUI.printSubtitle("ESTADISTICAS DE VENTAS");
        System.out.println("Seccion mas popular: " + getMostPopularSection());
        System.out.println("Grupo de edad mas frecuente: " + getMostFrequentAgeGroup());
        System.out.println("Ingresos totales: $" + getTotalRevenue());
        
        System.out.println("\nVentas por seccion:");
        for (Map.Entry<String, Integer> entry : sectionSalesCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " entradas");
        }
        
        System.out.println("\nDistribucion por edad:");
        for (Map.Entry<Character, Integer> entry : customerAgeGroups.entrySet()) {
            String groupName;
            switch (entry.getKey()) {
                case 'N': groupName = "Ninos (menores de 18)"; break;
                case 'J': groupName = "Jovenes (18-29)"; break;
                case 'A': groupName = "Adultos (30-64)"; break;
                case 'M': groupName = "Mayores (65+)"; break;
                default: groupName = "Desconocido"; break;
            }
            System.out.println("  " + groupName + ": " + entry.getValue() + " clientes");
        }
    }
}

/**
 * Clase para realizar pruebas automatizadas del sistema
 */
class TheaterSystemTester {
    
    /**
     * Ejecuta todas las pruebas del sistema
     * @return true si todas las pruebas pasan, false en caso contrario
     */
    public static boolean runAllTests() {
        ConsoleUI.printTitle("EJECUTANDO PRUEBAS DEL SISTEMA");
        
        boolean allTestsPassed = true;
        
        // Ejecutar cada prueba y acumular resultados
        allTestsPassed &= testTheaterInitialization();
        allTestsPassed &= testSeatAvailability();
        allTestsPassed &= testDiscountCalculation();
        allTestsPassed &= testTicketCreation();
        allTestsPassed &= testDataPersistence();
        allTestsPassed &= testExceptionHandling();
        
        // Mostrar resultado final
        if (allTestsPassed) {
            ConsoleUI.printSuccess("TODAS LAS PRUEBAS PASARON CORRECTAMENTE");
        } else {
            ConsoleUI.printError("ALGUNAS PRUEBAS FALLARON");
        }
        
        return allTestsPassed;
    }
    
    /**
     * Prueba la inicialización correcta del teatro y sus secciones
     */
    private static boolean testTheaterInitialization() {
        ConsoleUI.printSubtitle("Prueba: Inicialización del Teatro");
        
        try {
            Theater theater = new Theater("Teatro de Prueba");
            
            // Verificar que se crearon las secciones
            if (theater.sections.size() != 5) {
                ConsoleUI.printError("Error: No se crearon las 5 secciones esperadas");
                return false;
            }
            
            // Verificar que cada sección tiene asientos
            for (Section section : theater.sections) {
                if (section.rows <= 0 || section.columns <= 0) {
                    ConsoleUI.printError("Error: La sección " + section.name + " no tiene dimensiones válidas");
                    return false;
                }
            }
            
            ConsoleUI.printSuccess("Inicialización del teatro correcta");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error inesperado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Prueba la disponibilidad de asientos
     */
    private static boolean testSeatAvailability() {
        ConsoleUI.printSubtitle("Prueba: Disponibilidad de Asientos");
        
        try {
            Theater theater = new Theater("Teatro de Prueba");
            Section section = theater.sections.get(0);
            
            // Verificar que todos los asientos están disponibles inicialmente
            Seat seat = section.getSeat('A', 1);
            if (!seat.available) {
                ConsoleUI.printError("Error: El asiento debería estar disponible inicialmente");
                return false;
            }
            
            // Marcar un asiento como ocupado
            seat.available = false;
            
            // Verificar que el asiento ahora está ocupado
            if (seat.available) {
                ConsoleUI.printError("Error: El asiento debería estar ocupado después de marcarlo");
                return false;
            }
            
            ConsoleUI.printSuccess("Gestión de disponibilidad de asientos correcta");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error inesperado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Prueba el cálculo de descuentos
     */
    private static boolean testDiscountCalculation() {
        ConsoleUI.printSubtitle("Prueba: Cálculo de Descuentos");
        
        try {
            // Crear instancia del sistema para usar su método de cálculo de descuentos
            TheaterSystem system = new TheaterSystem();
            
            // Probar descuento para niño (10%)
            Customer child = new Customer("Niño Prueba", 10, 'M', false);
            double childDiscount = system.calculateDiscount(child, 100);
            if (Math.abs(childDiscount - 10.0) > 0.01) {
                ConsoleUI.printError("Error: El descuento para niños debería ser 10%, pero fue " + childDiscount + "%");
                return false;
            }
            
            // Probar descuento para mujer (20%)
            Customer woman = new Customer("Mujer Prueba", 30, 'F', false);
            double womanDiscount = system.calculateDiscount(woman, 100);
            if (Math.abs(womanDiscount - 20.0) > 0.01) {
                ConsoleUI.printError("Error: El descuento para mujeres debería ser 20%, pero fue " + womanDiscount + "%");
                return false;
            }
            
            // Probar descuento para estudiante (15%)
            Customer student = new Customer("Estudiante Prueba", 20, 'M', true);
            double studentDiscount = system.calculateDiscount(student, 100);
            if (Math.abs(studentDiscount - 15.0) > 0.01) {
                ConsoleUI.printError("Error: El descuento para estudiantes debería ser 15%, pero fue " + studentDiscount + "%");
                return false;
            }
            
            // Probar descuento para tercera edad (25%)
            Customer senior = new Customer("Mayor Prueba", 70, 'M', false);
            double seniorDiscount = system.calculateDiscount(senior, 100);
            if (Math.abs(seniorDiscount - 25.0) > 0.01) {
                ConsoleUI.printError("Error: El descuento para tercera edad debería ser 25%, pero fue " + seniorDiscount + "%");
                return false;
            }
            
            ConsoleUI.printSuccess("Cálculo de descuentos correcto");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error inesperado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Prueba la creación de tickets
     */
    private static boolean testTicketCreation() {
        ConsoleUI.printSubtitle("Prueba: Creación de Tickets");
        
        try {
            Theater theater = new Theater("Teatro de Prueba");
            Section section = theater.sections.get(0);
            Seat seat = section.getSeat('A', 1);
            Customer customer = new Customer("Cliente Prueba", 30, 'M', false);
            
            // Crear un ticket
            Ticket ticket = new Ticket(customer, section, seat, 45000);
            
            // Verificar que el ticket se creó correctamente
            if (ticket.customer != customer || ticket.section != section || 
                ticket.seat != seat || Math.abs(ticket.price - 45000) > 0.01) {
                ConsoleUI.printError("Error: El ticket no se creó con los valores correctos");
                return false;
            }
            
            ConsoleUI.printSuccess("Creación de tickets correcta");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error inesperado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Prueba la persistencia de datos
     */
    private static boolean testDataPersistence() {
        ConsoleUI.printSubtitle("Prueba: Persistencia de Datos");
        
        try {
            // Crear datos de prueba
            Theater theater = new Theater("Teatro de Prueba");
            Section section = theater.sections.get(0);
            Seat seat = section.getSeat('A', 1);
            Customer customer = new Customer("Cliente Prueba", 30, 'M', false);
            Ticket ticket = new Ticket(customer, section, seat, 45000);
            
            ArrayList<Ticket> testSales = new ArrayList<>();
            testSales.add(ticket);
            
            // Guardar datos
            DataManager.saveSales(testSales);
            
            // Cargar datos
            ArrayList<Ticket> loadedSales = DataManager.loadSales();
            
            // Verificar que los datos se cargaron correctamente
            if (loadedSales.size() != 1) {
                ConsoleUI.printError("Error: No se cargó correctamente la venta guardada");
                return false;
            }
            
            Ticket loadedTicket = loadedSales.get(0);
            if (!loadedTicket.customer.name.equals(customer.name) || 
                !loadedTicket.section.name.equals(section.name) ||
                !loadedTicket.seat.getPosition().equals(seat.getPosition()) ||
                Math.abs(loadedTicket.price - 45000) > 0.01) {
                ConsoleUI.printError("Error: Los datos cargados no coinciden con los guardados");
                return false;
            }
            
            ConsoleUI.printSuccess("Persistencia de datos correcta");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error en prueba de persistencia: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Prueba el manejo de excepciones
     */
    private static boolean testExceptionHandling() {
        ConsoleUI.printSubtitle("Prueba: Manejo de Excepciones");
        
        try {
            // Probar validación de datos de cliente
            try {
                Validator.validateCustomerData("", -5, 'X');
                ConsoleUI.printError("Error: No se lanzó excepción con datos inválidos de cliente");
                return false;
            } catch (InvalidCustomerDataException e) {
                // Excepción esperada
            }
            
            // Probar validación de sección
            try {
                Validator.validateSectionSelection(10, 5);
                ConsoleUI.printError("Error: No se lanzó excepción con sección inválida");
                return false;
            } catch (InvalidSectionException e) {
                // Excepción esperada
            }
            
            // Probar validación de formato de asiento
            try {
                Validator.validateSeatFormat("Z12");
                ConsoleUI.printError("Error: No se lanzó excepción con formato de asiento inválido");
                return false;
            } catch (InvalidSeatException e) {
                // Excepción esperada
            }
            
            ConsoleUI.printSuccess("Manejo de excepciones correcto");
            return true;
        } catch (Exception e) {
            ConsoleUI.printError("Error inesperado: " + e.getMessage());
            return false;
        }
    }
}

/**
 * Clase que maneja toda la logica del sistema de venta de entradas
 * Contiene metodos para vender entradas, mostrar disponibilidad y ventas
 */
class TheaterSystem {
    private Scanner scanner;
    private Theater theater;
    private TheaterStats stats;
    
    /**
     * Constructor que inicializa el sistema con un scanner para entrada de datos
     * y crea un nuevo teatro llamado "Teatro Moro"
     */
    public TheaterSystem() {
        scanner = new Scanner(System.in);
        theater = new Theater("Teatro Moro");
        stats = new TheaterStats();
        
        // Intentar cargar ventas previas
        try {
            ArrayList<Ticket> savedSales = DataManager.loadSales();
            if (!savedSales.isEmpty()) {
                theater.sales = savedSales;
                ConsoleUI.printSuccess("Se cargaron " + savedSales.size() + " ventas previas");
                
                // Actualizar estadísticas con las ventas cargadas
                for (Ticket ticket : savedSales) {
                    stats.registerSale(ticket);
                }
            }
        } catch (Exception e) {
            ConsoleUI.printWarning("No se pudieron cargar ventas previas: " + e.getMessage());
        }
    }
    
    /**
     * Metodo principal que inicia el sistema y muestra el menu de opciones
     * Permite al usuario interactuar con el sistema hasta que decida salir
     */
    public void start() {
        boolean exit = false;
        
        while (!exit) {
            showMenu();
            int option = getOption();
            
            try {
                switch (option) {
                    case 1:
                        sellTicket();
                        break;
                    case 2:
                        showAvailability();
                        break;
                    case 3:
                        showSales();
                        break;
                    case 4:
                        showStatistics();
                        break;
                    case 5:
                        generateReport();
                        break;
                    case 6:
                        saveData();
                        break;
                    case 7:
                        deleteTicket();
                        break;
                    case 8:
                        TheaterSystemTester.runAllTests();
                        break;
                    case 9:
                        ConsoleUI.printSuccess("Gracias por usar el sistema de venta de entradas del Teatro Moro");
                        exit = true;
                        break;
                    default:
                        ConsoleUI.printError("Opcion no valida. Intente nuevamente.");
                }
            } catch (Exception e) {
                ConsoleUI.printError("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    /**
     * Muestra el menu principal con las opciones disponibles para el usuario
     */
    private void showMenu() {
        ConsoleUI.printTitle("SISTEMA DE VENTA DE ENTRADAS - " + theater.name);
        ConsoleUI.printMenuOption(1, "Vender entrada");
        ConsoleUI.printMenuOption(2, "Mostrar disponibilidad de asientos");
        ConsoleUI.printMenuOption(3, "Mostrar ventas realizadas");
        ConsoleUI.printMenuOption(4, "Ver estadisticas");
        ConsoleUI.printMenuOption(5, "Generar reporte de ventas");
        ConsoleUI.printMenuOption(6, "Guardar datos");
        ConsoleUI.printMenuOption(7, "Borrar venta");
        ConsoleUI.printMenuOption(8, "Ejecutar pruebas del sistema");
        ConsoleUI.printMenuOption(9, "Salir");
        System.out.print("Seleccione una opcion: ");
    }
    
    /**
     * Obtiene la opcion seleccionada por el usuario y maneja posibles errores de formato
     * @return El numero de opcion seleccionado o -1 si hay un error
     */
    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Proceso completo de venta de una entrada
     * Solicita datos del cliente, seleccion de seccion y asiento
     * Calcula descuentos y genera la boleta
     */
    private void sellTicket() throws TheaterException {
        ConsoleUI.printSubtitle("VENTA DE ENTRADA");
        
        // Solicitar y recopilar datos del cliente
        System.out.print("Nombre del cliente: ");
        String name = scanner.nextLine();
        
        System.out.print("Edad: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidCustomerDataException("La edad debe ser un numero valido");
        }
        
        System.out.print("Genero (M/F): ");
        char gender = scanner.nextLine().toUpperCase().charAt(0);
        
        // Validar datos del cliente
        Validator.validateCustomerData(name, age, gender);
        
        System.out.print("Es estudiante? (S/N): ");
        boolean isStudent = scanner.nextLine().toUpperCase().charAt(0) == 'S';
        
        // Crear objeto cliente con los datos recopilados
        Customer customer = new Customer(name, age, gender, isStudent);
        
        // Mostrar y seleccionar seccion del teatro
        ConsoleUI.printSubtitle("SECCIONES DISPONIBLES");
        for (Section section : theater.sections) {
            System.out.println(section.id + ". " + section.name + " - Precio: $" + section.price);
        }
        
        System.out.print("Seleccione una seccion: ");
        int sectionId;
        try {
            sectionId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidSectionException("El ID de seccion debe ser un numero valido");
        }
        
        // Validar selección de sección
        Validator.validateSectionSelection(sectionId, theater.sections.size());
        Section selectedSection = theater.getSectionById(sectionId);
        
        if (selectedSection == null) {
            throw new InvalidSectionException("Seccion no encontrada");
        }
        
        // Mostrar mapa de asientos disponibles en la seccion seleccionada
        ConsoleUI.printSubtitle("ASIENTOS DISPONIBLES EN " + selectedSection.name);
        ConsoleUI.printSeatMap(selectedSection);
        
        // Seleccionar asiento especifico
        System.out.print("Seleccione un asiento (fila-columna, ej: A-1): ");
        String seatPosition = scanner.nextLine().toUpperCase();
        
        // Validar formato del asiento
        Validator.validateSeatFormat(seatPosition);
        
        String[] parts = seatPosition.split("-");
        char row = parts[0].charAt(0);
        int column = Integer.parseInt(parts[1]);
        
        Seat selectedSeat = selectedSection.getSeat(row, column);
        
        if (selectedSeat == null) {
            throw new InvalidSeatException("Asiento no encontrado");
        }
        
        if (!selectedSeat.available) {
            throw new SeatNotAvailableException("El asiento seleccionado no esta disponible");
        }
        
        // Calcular precio con descuento segun tipo de cliente
        double originalPrice = selectedSection.price;
        double discount = calculateDiscount(customer, originalPrice);
        double finalPrice = originalPrice - discount;
        
        // Mostrar resumen de la compra para confirmacion
        ConsoleUI.printSubtitle("RESUMEN DE COMPRA");
        System.out.println("Cliente: " + customer.name);
        System.out.println("Seccion: " + selectedSection.name);
        System.out.println("Asiento: " + selectedSeat.getPosition());
        System.out.println("Precio original: $" + originalPrice);
        System.out.println("Descuento aplicado: $" + discount);
        System.out.println("Precio final: $" + finalPrice);
        
        System.out.print("\nConfirmar compra? (S/N): ");
        boolean confirm = scanner.nextLine().toUpperCase().charAt(0) == 'S';
        
        if (confirm) {
            // Crear ticket, marcar asiento como ocupado y registrar la venta
            Ticket ticket = new Ticket(customer, selectedSection, selectedSeat, finalPrice);
            selectedSeat.available = false;
            theater.registerSale(ticket);
            
            // Actualizar estadísticas
            stats.registerSale(ticket);
            
            // Imprimir boleta con detalles de la compra
            ConsoleUI.printReceipt(ticket, originalPrice, discount);
            
            ConsoleUI.printSuccess("Venta realizada con exito!");
        } else {
            ConsoleUI.printWarning("Venta cancelada.");
        }
    }
    
    /**
     * Calcula el descuento aplicable segun el tipo de cliente
     * Ninos: 10%, Mujeres: 20%, Estudiantes: 15%, Tercera edad: 25%
     * 
     * @param customer Cliente al que se le aplicara el descuento
     * @param originalPrice Precio original de la entrada
     * @return Monto del descuento a aplicar
     */
        public double calculateDiscount(Customer customer, double originalPrice) {
        double discountPercentage = 0;
        
        // Aplicar el mayor descuento disponible
        if (customer.age >= 60) {
            // Tercera edad: 25%
            discountPercentage = 0.25;
        } else if (customer.gender == 'F') {
            // Mujeres: 20%
            discountPercentage = 0.20;
        } else if (customer.student) {
            // Estudiantes: 15%
            discountPercentage = 0.15;
        } else if (customer.age < 18) {
            // Niños: 10%
            discountPercentage = 0.10;
        }
        
        return originalPrice * discountPercentage;
    }
    
    /**
     * Muestra la disponibilidad de asientos en todas las secciones del teatro
     */
    private void showAvailability() {
        ConsoleUI.printSubtitle("DISPONIBILIDAD DE ASIENTOS");
        
        for (Section section : theater.sections) {
            ConsoleUI.printSubtitle("SECCION: " + section.name);
            ConsoleUI.printSeatMap(section);
        }
    }
    
    /**
     * Muestra un resumen de todas las ventas realizadas y el total recaudado
     */
    private void showSales() {
        ConsoleUI.printSubtitle("VENTAS REALIZADAS");
        
        ArrayList<Ticket> sales = theater.sales;
        
        if (sales.isEmpty()) {
            ConsoleUI.printWarning("No se han realizado ventas.");
            return;
        }
        
        double totalSales = 0;
        
        // Mostrar cada venta con sus detalles
        for (int i = 0; i < sales.size(); i++) {
            Ticket ticket = sales.get(i);
            System.out.println((i+1) + ". Cliente: " + ticket.customer.name + 
                              " | Seccion: " + ticket.section.name + 
                              " | Asiento: " + ticket.seat.getPosition() + 
                              " | Precio: $" + ticket.price);
            totalSales += ticket.price;
        }
        
        System.out.println("\nTotal de ventas: $" + totalSales);
    }
    
    /**
     * Muestra estadísticas detalladas de ventas
     */
    private void showStatistics() {
        stats.showStats();
    }
    
    /**
     * Genera un reporte de ventas en un archivo de texto
     */
    private void generateReport() throws IOException {
        DataManager.generateSalesReport(theater.sales);
        ConsoleUI.printSuccess("Reporte generado exitosamente");
    }
    
    /**
     * Guarda los datos de ventas en un archivo
     */
    private void saveData() throws IOException {
        DataManager.saveSales(theater.sales);
        ConsoleUI.printSuccess("Datos guardados exitosamente");
    }
    
    /**
     * Permite borrar una venta específica del sistema
     * Libera el asiento correspondiente y actualiza las estadísticas
     */
    private void deleteTicket() {
        ConsoleUI.printSubtitle("BORRAR VENTA");
        
        ArrayList<Ticket> sales = theater.sales;
        
        if (sales.isEmpty()) {
            ConsoleUI.printWarning("No hay ventas para borrar.");
            return;
        }
        
        // Mostrar lista de ventas
        for (int i = 0; i < sales.size(); i++) {
            Ticket ticket = sales.get(i);
            System.out.println((i+1) + ". Cliente: " + ticket.customer.name + 
                              " | Seccion: " + ticket.section.name + 
                              " | Asiento: " + ticket.seat.getPosition() + 
                              " | Precio: $" + ticket.price);
        }
        
        // Solicitar número de venta a borrar
        System.out.print("\nIngrese el número de la venta a borrar (1-" + sales.size() + "): ");
        int ticketNumber;
        
        try {
            ticketNumber = Integer.parseInt(scanner.nextLine());
            
            if (ticketNumber < 1 || ticketNumber > sales.size()) {
                ConsoleUI.printError("Número de venta inválido.");
                return;
            }
            
            // Confirmar borrado
            System.out.print("¿Está seguro que desea borrar esta venta? (S/N): ");
            boolean confirm = scanner.nextLine().toUpperCase().charAt(0) == 'S';
            
            if (confirm) {
                // Obtener el ticket a borrar
                Ticket ticketToDelete = sales.get(ticketNumber - 1);
                
                // Liberar el asiento
                ticketToDelete.seat.available = true;
                
                // Eliminar la venta
                sales.remove(ticketNumber - 1);
                
                // Reconstruir estadísticas
                stats = new TheaterStats();
                for (Ticket ticket : sales) {
                    stats.registerSale(ticket);
                }
                
                ConsoleUI.printSuccess("Venta borrada exitosamente.");
                
                // Guardar cambios automáticamente
                try {
                    DataManager.saveSales(sales);
                    ConsoleUI.printSuccess("Datos actualizados guardados.");
                } catch (IOException e) {
                    ConsoleUI.printError("Error al guardar los cambios: " + e.getMessage());
                }
            } else {
                ConsoleUI.printWarning("Operación cancelada.");
            }
            
        } catch (NumberFormatException e) {
            ConsoleUI.printError("Debe ingresar un número válido.");
        }
    }
}

/**
 * Clase que representa un teatro con sus secciones y registro de ventas
 */
class Theater implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String name;
    public ArrayList<Section> sections;
    public ArrayList<Ticket> sales;
    
    /**
     * Constructor que inicializa un teatro con su nombre
     * Crea las secciones y prepara el registro de ventas
     * 
     * @param name Nombre del teatro
     */
    public Theater(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
        this.sales = new ArrayList<>();
        
        // Inicializar secciones del teatro
        initializeSections();
    }
    
    /**
     * Crea las diferentes secciones del teatro con sus precios y capacidades
     */
    private void initializeSections() {
        // Crear secciones con sus precios y dimensiones (filas x columnas)
        sections.add(new Section(1, "VIP", 50000, 3, 5, this));
        sections.add(new Section(2, "Palco", 40000, 4, 6, this));
        sections.add(new Section(3, "Platea Baja", 30000, 5, 8, this));
        sections.add(new Section(4, "Platea Alta", 20000, 6, 10, this));
        sections.add(new Section(5, "Galeria", 10000, 7, 12, this));
    }
    
    /**
     * Busca una seccion por su ID
     * 
     * @param id ID de la seccion a buscar
     * @return La seccion encontrada o null si no existe
     */
    public Section getSectionById(int id) {
        for (Section section : sections) {
            if (section.id == id) {
                return section;
            }
        }
        return null;
    }
    
    /**
     * Registra una venta en el sistema
     * 
     * @param ticket Ticket vendido a registrar
     */
    public void registerSale(Ticket ticket) {
        sales.add(ticket);
    }
}

/**
 * Clase que representa una seccion del teatro con sus asientos
 */
class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int id;
    public String name;
    public double price;
    private Map<String, Seat> seats;
    public int rows;
    public int columns;
    public Theater theater;
    
    /**
     * Constructor que inicializa una seccion con sus propiedades
     * 
     * @param id Identificador unico de la seccion
     * @param name Nombre de la seccion
     * @param price Precio base de la seccion
     * @param rows Numero de filas de asientos
     * @param columns Numero de columnas de asientos
     * @param theater Teatro al que pertenece esta seccion
     */
    public Section(int id, String name, double price, int rows, int columns, Theater theater) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rows = rows;
        this.columns = columns;
        this.theater = theater;
        this.seats = new HashMap<>();
        
        // Crear los asientos de esta seccion
        initializeSeats();
    }
    
    /**
     * Crea todos los asientos de la seccion segun sus dimensiones
     * Las filas se identifican con letras (A, B, C...) y las columnas con numeros
     */
    private void initializeSeats() {
        for (int i = 0; i < rows; i++) {
            char row = (char) ('A' + i);
            for (int j = 1; j <= columns; j++) {
                String position = row + "-" + j;
                seats.put(position, new Seat(row, j));
            }
        }
    }
    
    /**
     * Obtiene un asiento especifico por su posicion
     * 
     * @param row Fila del asiento (letra)
     * @param column Columna del asiento (numero)
     * @return El asiento en esa posicion o null si no existe
     */
    public Seat getSeat(char row, int column) {
        String position = row + "-" + column;
        return seats.get(position);
    }
}

/**
 * Clase que representa un asiento individual en el teatro
 */
class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public char row;
    public int column;
    public boolean available;
    
    /**
     * Constructor que inicializa un asiento en una posicion especifica
     * Por defecto, todos los asientos se crean como disponibles
     * 
     * @param row Fila del asiento (letra)
     * @param column Columna del asiento (numero)
     */
    public Seat(char row, int column) {
        this.row = row;
        this.column = column;
        this.available = true;
    }
    
    /**
     * Obtiene la posicion del asiento en formato "fila-columna"
     * 
     * @return String con la posicion del asiento (ej: "A-1")
     */
    public String getPosition() {
        return row + "-" + column;
    }
}

/**
 * Clase que representa a un cliente del teatro
 */
class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String name;
    public int age;
    public char gender;
    public boolean student;
    
    /**
     * Constructor que inicializa un cliente con sus datos personales
     * 
     * @param name Nombre del cliente
     * @param age Edad del cliente
     * @param gender Genero del cliente (M/F)
     * @param student Indica si el cliente es estudiante
     */
    public Customer(String name, int age, char gender, boolean student) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.student = student;
    }
}

/**
 * Clase que representa una entrada vendida
 */
class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Customer customer;
    public Section section;
    public Seat seat;
    public double price;
    
    /**
     * Constructor que inicializa un ticket con todos sus detalles
     * 
     * @param customer Cliente que compra la entrada
     * @param section Seccion donde se ubica el asiento
     * @param seat Asiento asignado
     * @param price Precio final con descuento aplicado
     */
    public Ticket(Customer customer, Section section, Seat seat, double price) {
        this.customer = customer;
        this.section = section;
        this.seat = seat;
        this.price = price;
    }
}