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

/**
 * Clase principal del sistema de venta de entradas para Teatro Moro
 * Esta clase inicia el sistema y contiene el metodo main
 */
public class EFT_S9_Felix_Barahona {
    
    public static void main(String[] args) {
        // Inicializacion del sistema de teatro
        TheaterSystem system = new TheaterSystem();
        system.start();
    }
}

/**
 * Clase que maneja toda la logica del sistema de venta de entradas
 * Contiene metodos para vender entradas, mostrar disponibilidad y ventas
 */
class TheaterSystem {
    private Scanner scanner;
    private Theater theater;
    
    /**
     * Constructor que inicializa el sistema con un scanner para entrada de datos
     * y crea un nuevo teatro llamado "Teatro Moro"
     */
    public TheaterSystem() {
        scanner = new Scanner(System.in);
        theater = new Theater("Teatro Moro");
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
                    System.out.println("Gracias por usar el sistema de venta de entradas del Teatro Moro");
                    exit = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Muestra el menu principal con las opciones disponibles para el usuario
     */
    private void showMenu() {
        System.out.println("\n===== SISTEMA DE VENTA DE ENTRADAS - " + theater.name + " =====");
        System.out.println("1. Vender entrada");
        System.out.println("2. Mostrar disponibilidad de asientos");
        System.out.println("3. Mostrar ventas realizadas");
        System.out.println("4. Salir");
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
    private void sellTicket() {
        System.out.println("\n----- VENTA DE ENTRADA -----");
        
        // Solicitar y recopilar datos del cliente
        System.out.print("Nombre del cliente: ");
        String name = scanner.nextLine();
        
        System.out.print("Edad: ");
        int age = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Genero (M/F): ");
        char gender = scanner.nextLine().toUpperCase().charAt(0);
        
        System.out.print("Es estudiante? (S/N): ");
        boolean isStudent = scanner.nextLine().toUpperCase().charAt(0) == 'S';
        
        // Crear objeto cliente con los datos recopilados
        Customer customer = new Customer(name, age, gender, isStudent);
        
        // Mostrar y seleccionar seccion del teatro
        System.out.println("\nSecciones disponibles:");
        for (Section section : theater.sections) {
            System.out.println(section.id + ". " + section.name + " - Precio: $" + section.price);
        }
        
        System.out.print("Seleccione una seccion: ");
        int sectionId = Integer.parseInt(scanner.nextLine());
        Section selectedSection = theater.getSectionById(sectionId);
        
        if (selectedSection == null) {
            System.out.println("Seccion no valida.");
            return;
        }
        
        // Mostrar mapa de asientos disponibles en la seccion seleccionada
        System.out.println("\nAsientos disponibles en " + selectedSection.name + ":");
        selectedSection.showAvailableSeats();
        
        // Seleccionar asiento especifico
        System.out.print("Seleccione un asiento (fila-columna, ej: A-1): ");
        String seatPosition = scanner.nextLine();
        
        try {
            // Procesar la posicion del asiento ingresada
            String[] parts = seatPosition.split("-");
            char row = parts[0].toUpperCase().charAt(0);
            int column = Integer.parseInt(parts[1]);
            
            Seat selectedSeat = selectedSection.getSeat(row, column);
            
            // Verificar si el asiento existe y esta disponible
            if (selectedSeat == null || !selectedSeat.available) {
                System.out.println("Asiento no disponible o no valido.");
                return;
            }
            
            // Calcular precio con descuento segun tipo de cliente
            double originalPrice = selectedSection.price;
            double discount = calculateDiscount(customer, originalPrice);
            double finalPrice = originalPrice - discount;
            
            // Mostrar resumen de la compra para confirmacion
            System.out.println("\nResumen de compra:");
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
                
                // Imprimir boleta con detalles de la compra
                printReceipt(ticket, originalPrice, discount);
                
                System.out.println("Venta realizada con exito!");
            } else {
                System.out.println("Venta cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("Error al seleccionar asiento: " + e.getMessage());
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
    private double calculateDiscount(Customer customer, double originalPrice) {
        double discountPercentage = 0;
        
        // Aplicar descuento segun categoria del cliente
        if (customer.age < 18) {
            // Ninos: 10%
            discountPercentage = 0.10;
        } else if (customer.gender == 'F') {
            // Mujeres: 20%
            discountPercentage = 0.20;
        } else if (customer.student) {
            // Estudiantes: 15%
            discountPercentage = 0.15;
        } else if (customer.age >= 65) {
            // Tercera edad: 25%
            discountPercentage = 0.25;
        }
        
        return originalPrice * discountPercentage;
    }
    
    /**
     * Imprime la boleta con los detalles de la compra
     * 
     * @param ticket Ticket vendido
     * @param originalPrice Precio original antes del descuento
     * @param discount Monto del descuento aplicado
     */
    private void printReceipt(Ticket ticket, double originalPrice, double discount) {
        System.out.println("\n========================================");
        System.out.println("            BOLETA DE COMPRA            ");
        System.out.println("            " + theater.name + "            ");
        System.out.println("========================================");
        System.out.println("Cliente: " + ticket.customer.name);
        System.out.println("Seccion: " + ticket.section.name);
        System.out.println("Asiento: " + ticket.seat.getPosition());
        System.out.println("----------------------------------------");
        System.out.println("Precio original: $" + originalPrice);
        System.out.println("Descuento aplicado: $" + discount);
        System.out.println("TOTAL A PAGAR: $" + ticket.price);
        System.out.println("========================================");
        System.out.println("Gracias por su compra!");
        System.out.println("========================================");
    }
    
    /**
     * Muestra la disponibilidad de asientos en todas las secciones del teatro
     */
    private void showAvailability() {
        System.out.println("\n----- DISPONIBILIDAD DE ASIENTOS -----");
        
        for (Section section : theater.sections) {
            System.out.println("\nSeccion: " + section.name);
            section.showAvailableSeats();
        }
    }
    
    /**
     * Muestra un resumen de todas las ventas realizadas y el total recaudado
     */
    private void showSales() {
        System.out.println("\n----- VENTAS REALIZADAS -----");
        
        ArrayList<Ticket> sales = theater.sales;
        
        if (sales.isEmpty()) {
            System.out.println("No se han realizado ventas.");
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
}

/**
 * Clase que representa un teatro con sus secciones y registro de ventas
 */
class Theater {
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
        sections.add(new Section(1, "VIP", 50000, 3, 5));
        sections.add(new Section(2, "Palco", 40000, 4, 6));
        sections.add(new Section(3, "Platea Baja", 30000, 5, 8));
        sections.add(new Section(4, "Platea Alta", 20000, 6, 10));
        sections.add(new Section(5, "Galeria", 10000, 7, 12));
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
class Section {
    public int id;
    public String name;
    public double price;
    private Map<String, Seat> seats;
    public int rows;
    public int columns;
    
    /**
     * Constructor que inicializa una seccion con sus propiedades
     * 
     * @param id Identificador unico de la seccion
     * @param name Nombre de la seccion
     * @param price Precio base de la seccion
     * @param rows Numero de filas de asientos
     * @param columns Numero de columnas de asientos
     */
    public Section(int id, String name, double price, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rows = rows;
        this.columns = columns;
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
    
    /**
     * Muestra una representacion grafica de los asientos disponibles
     * O = disponible, X = ocupado
     */
    public void showAvailableSeats() {
        // Mostrar encabezado de columnas
        System.out.print("   ");
        for (int j = 1; j <= columns; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        // Mostrar asientos con su estado
        for (int i = 0; i < rows; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + ": ");
            
            for (int j = 1; j <= columns; j++) {
                Seat seat = getSeat(row, j);
                if (seat.available) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println("O = Disponible, X = Ocupado");
    }
}

/**
 * Clase que representa un asiento individual en el teatro
 */
class Seat {
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
class Customer {
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
class Ticket {
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