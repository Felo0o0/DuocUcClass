/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_felix_barahona;

/**
 *
 * @author felix
 */
// File: Exp3_S8_Felix_Barahona.java

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Exp3_S8_Felix_Barahona {
    public static void main(String[] args) {
        // Initialize the theater
        Theater theater = new Theater("Teatro Moro", 8, 10); // 8 rows, 10 seats per row
        
        // Initialize the sales system
        SalesSystem salesSystem = new SalesSystem(theater);
        
        // Run the interactive menu
        salesSystem.runMenu();
    }
}

// Theater.java - Represents the theater with seats
class Theater {
    private String name;
    private Seat[][] seats;
    private int rows;
    private int seatsPerRow;
    
    public Theater(String name, int rows, int seatsPerRow) {
        this.name = name;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new Seat[rows][seatsPerRow];
        
        // Initialize all seats
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public Seat getSeat(int row, int seatNumber) {
        if (row >= 0 && row < rows && seatNumber >= 0 && seatNumber < seatsPerRow) {
            return seats[row][seatNumber];
        }
        return null;
    }
    
    public boolean isSeatAvailable(int row, int seatNumber) {
        Seat seat = getSeat(row, seatNumber);
        return seat != null && !seat.isReserved();
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getSeatsPerRow() {
        return seatsPerRow;
    }
    
    public void displaySeatingChart() {
        System.out.println("\n===== Teatro Moro - Mapa de Asientos =====");
        System.out.print("    ");
        for (int j = 0; j < seatsPerRow; j++) {
            System.out.printf("%3d ", j + 1);
        }
        System.out.println("\n");
        
        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d: ", i + 1);
            for (int j = 0; j < seatsPerRow; j++) {
                if (seats[i][j].isReserved()) {
                    System.out.print(" [X] ");
                } else {
                    System.out.print(" [ ] ");
                }
            }
            System.out.println();
        }
        System.out.println("\n[X] - Reservado, [ ] - Disponible");
    }
    
    public int getAvailableSeatsCount() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                if (!seats[i][j].isReserved()) {
                    count++;
                }
            }
        }
        return count;
    }
}

// Seat.java - Represents a single seat in the theater
class Seat {
    private int row;
    private int seatNumber;
    private boolean reserved;
    
    public Seat(int row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.reserved = false;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public boolean isReserved() {
        return reserved;
    }
    
    public void reserve() {
        this.reserved = true;
    }
    
    public void cancelReservation() {
        this.reserved = false;
    }
    
    @Override
    public String toString() {
        return "Fila " + (row + 1) + ", Asiento " + (seatNumber + 1);
    }
}

// Customer.java - Represents a customer
class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private CustomerType type;
    private int age; // Added age field
    
    public Customer(int id, String name, String email, String phone, CustomerType type, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.age = age;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public CustomerType getType() {
        return type;
    }
    
    public int getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", edad=" + age +
                ", tipo=" + type +
                '}';
    }
}

// CustomerType.java - Enum for different types of customers
enum CustomerType {
    REGULAR,
    STUDENT,
    SENIOR
}

// Sale.java - Represents a ticket sale
class Sale {
    private int id;
    private Customer customer;
    private Seat seat;
    private double originalPrice;
    private double discountAmount;
    private double priceAfterDiscount;
    private double iva; // Added IVA field
    private double finalPrice;
    private String date;
    
    public Sale(int id, Customer customer, Seat seat, double originalPrice, double discountAmount, 
                double priceAfterDiscount, double iva, double finalPrice, String date) {
        this.id = id;
        this.customer = customer;
        this.seat = seat;
        this.originalPrice = originalPrice;
        this.discountAmount = discountAmount;
        this.priceAfterDiscount = priceAfterDiscount;
        this.iva = iva;
        this.finalPrice = finalPrice;
        this.date = date;
    }
    
    public int getId() {
        return id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public double getOriginalPrice() {
        return originalPrice;
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }
    
    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }
    
    public double getIva() {
        return iva;
    }
    
    public double getFinalPrice() {
        return finalPrice;
    }
    
    public String getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", cliente=" + customer.getName() +
                ", asiento=" + seat +
                ", precioOriginal=" + originalPrice +
                ", precioFinal=" + finalPrice +
                ", fecha='" + date + '\'' +
                '}';
    }
}

// Reservation.java - Represents a seat reservation
class Reservation {
    private int id;
    private Customer customer;
    private Seat seat;
    private String date;
    
    public Reservation(int id, Customer customer, Seat seat, String date) {
        this.id = id;
        this.customer = customer;
        this.seat = seat;
        this.date = date;
    }
    
    public int getId() {
        return id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public String getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + customer.getName() +
                ", asiento=" + seat +
                ", fecha='" + date + '\'' +
                '}';
    }
}

// SalesSystem.java - Handles sales, discounts, and reservations
class SalesSystem {
    private Theater theater;
    private Sale[] sales;
    private int salesCount;
    private Customer[] customers;
    private int customerCount;
    private List<Discount> discounts;
    private List<Reservation> reservations;
    private Scanner scanner;
    
    private static final int MAX_SALES = 1000;
    private static final int MAX_CUSTOMERS = 500;
    private static final double BASE_TICKET_PRICE = 10000.0; // Base price in Chilean pesos
    private static final double IVA_RATE = 0.19; // 19% IVA rate
    
    // Counters for customer types
    private int regularCustomers = 0;
    private int studentCustomers = 0;
    private int seniorCustomers = 0;
    
    public SalesSystem(Theater theater) {
        this.theater = theater;
        this.sales = new Sale[MAX_SALES];
        this.salesCount = 0;
        this.customers = new Customer[MAX_CUSTOMERS];
        this.customerCount = 0;
        this.discounts = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        
        // Initialize default discounts
        initializeDiscounts();
    }
    
    private void initializeDiscounts() {
        // Add student discount (10%)
        discounts.add(new Discount("Descuento Estudiante", CustomerType.STUDENT, 0.10));
        
        // Add senior discount (15%)
        discounts.add(new Discount("Descuento Adulto Mayor", CustomerType.SENIOR, 0.15));
    }
    
    // Run the interactive menu
    public void runMenu() {
        boolean exit = false;
        
        System.out.println("===== Bienvenido al Sistema de Gestion del Teatro Moro =====");
        
        while (!exit) {
            System.out.println("\nMenu Principal");
            System.out.println("1. Ver Asientos Disponibles");
            System.out.println("2. Comprar Entrada");
            System.out.println("3. Ver Todos los Clientes");
            System.out.println("4. Ver Reporte de Ventas");
            System.out.println("5. Salir");
            
            System.out.print("Ingrese su opcion: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        theater.displaySeatingChart();
                        break;
                    case 2:
                        sellTicketInteractive();
                        break;
                    case 3:
                        displayAllCustomers();
                        break;
                    case 4:
                        generateSalesReport();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Gracias por usar el Sistema de Gestion del Teatro Moro. Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opcion invalida. Por favor ingrese un numero entre 1 y 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor ingrese un numero.");
                scanner.nextLine(); // Consume invalid input
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    // Interactive method to sell a ticket
    private void sellTicketInteractive() {
        try {
            // Check if there are available seats
            if (theater.getAvailableSeatsCount() == 0) {
                System.out.println("Lo sentimos, no hay asientos disponibles en el teatro.");
                return;
            }
            
            // Get customer information
            Customer customer = getCustomerInfoInteractive();
            if (customer == null) return;
            
            // Display seating chart
            theater.displaySeatingChart();
            
            // Get seat selection
            int row = -1;
            int seatNumber = -1;
            boolean validSeat = false;
            
            while (!validSeat) {
                try {
                    System.out.print("Ingrese numero de fila (1-" + theater.getRows() + "): ");
                    row = scanner.nextInt() - 1; // Convert to 0-based index
                    
                    System.out.print("Ingrese numero de asiento (1-" + theater.getSeatsPerRow() + "): ");
                    seatNumber = scanner.nextInt() - 1; // Convert to 0-based index
                    
                    scanner.nextLine(); // Consume newline
                    
                    if (row < 0 || row >= theater.getRows() || seatNumber < 0 || seatNumber >= theater.getSeatsPerRow()) {
                        System.out.println("Seleccion de asiento invalida. Por favor intente de nuevo.");
                    } else if (!theater.isSeatAvailable(row, seatNumber)) {
                        System.out.println("Este asiento ya esta ocupado. Por favor seleccione otro asiento.");
                    } else {
                        validSeat = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada invalida. Por favor ingrese un numero.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
            
            // Sell the ticket
            Sale sale = sellTicket(customer, row, seatNumber);
            
            // Display sale details
            System.out.println("\nEntrada vendida exitosamente!");
            System.out.println("=== Detalles de la Venta ===");
            System.out.println("Cliente: " + sale.getCustomer().getName());
            System.out.println("Asiento: " + sale.getSeat());
            System.out.println("Precio Base: $" + sale.getOriginalPrice());
            
            // Display discount if applicable
            if (sale.getDiscountAmount() > 0) {
                System.out.println("Descuento: $" + sale.getDiscountAmount() + 
                        " (" + (sale.getDiscountAmount() / sale.getOriginalPrice() * 100) + "%)");
                System.out.println("Precio despues de descuento: $" + sale.getPriceAfterDiscount());
            }
            
            System.out.println("IVA (19%): $" + sale.getIva());
            System.out.println("Precio Final: $" + sale.getFinalPrice());
            System.out.println("Fecha: " + sale.getDate());
            
        } catch (Exception e) {
            System.out.println("Error al vender entrada: " + e.getMessage());
        }
    }
    
    // Helper method to get customer information interactively
    private Customer getCustomerInfoInteractive() {
        try {
            System.out.println("\n=== Informacion del Cliente ===");
            
            // Get name
            System.out.print("Ingrese nombre del cliente: ");
            String name = scanner.nextLine();
            while (name.trim().isEmpty()) {
                System.out.print("El nombre no puede estar vacio. Ingrese nombre del cliente: ");
                name = scanner.nextLine();
            }
            
            // Get age
            int age = 0;
            boolean validAge = false;
            while (!validAge) {
                try {
                    System.out.print("Ingrese edad del cliente: ");
                    age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    if (age > 0 && age < 120) {
                        validAge = true;
                    } else {
                        System.out.println("Edad invalida. Por favor ingrese una edad entre 1 y 119.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada invalida. Por favor ingrese un numero.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
            
            // Get email
            System.out.print("Ingrese email del cliente: ");
            String email = scanner.nextLine();
            while (!email.contains("@") || email.trim().isEmpty()) {
                System.out.print("Formato de email invalido. Ingrese email del cliente: ");
                email = scanner.nextLine();
            }
            
            // Get phone
            System.out.print("Ingrese telefono del cliente: ");
            String phone = scanner.nextLine();
            while (phone.trim().isEmpty()) {
                System.out.print("El telefono no puede estar vacio. Ingrese telefono del cliente: ");
                phone = scanner.nextLine();
            }
            
            // Determine customer type based on age and student status
            CustomerType type = CustomerType.REGULAR;
            
            if (age >= 60) {
                type = CustomerType.SENIOR;
                seniorCustomers++;
            } else {
                System.out.print("Es el cliente estudiante? (si/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                
                while (!response.equals("si") && !response.equals("no")) {
                    System.out.print("Respuesta invalida. Por favor ingrese 'si' o 'no': ");
                    response = scanner.nextLine().trim().toLowerCase();
                }
                
                if (response.equals("si")) {
                    type = CustomerType.STUDENT;
                    studentCustomers++;
                } else {
                    regularCustomers++;
                }
            }
            
            // Add the customer
            return addCustomer(name, email, phone, type, age);
            
        } catch (Exception e) {
            System.out.println("Error al obtener informacion del cliente: " + e.getMessage());
            return null;
        }
    }
    
    // Display all customers
    private void displayAllCustomers() {
        if (customerCount == 0) {
            System.out.println("No hay clientes registrados aun.");
            return;
        }
        
        System.out.println("\n===== Todos los Clientes =====");
        for (int i = 0; i < customerCount; i++) {
            Customer customer = customers[i];
            System.out.println("ID: " + customer.getId() +
                    ", Nombre: " + customer.getName() +
                    ", Edad: " + customer.getAge() +
                    ", Tipo: " + customer.getType() +
                    ", Email: " + customer.getEmail());
        }
    }
    
    // Generate a sales report
    private void generateSalesReport() {
        if (salesCount == 0) {
            System.out.println("No hay datos de ventas disponibles para el reporte.");
            return;
        }
        
        System.out.println("\n===== REPORTE DE VENTAS =====");
        
        // Calculate totals
        int totalSales = salesCount;
        double totalNetRevenue = 0;
        double totalIva = 0;
        double totalRevenue = 0;
        double totalDiscounts = 0;
        double regularRevenue = 0;
        double studentRevenue = 0;
        double seniorRevenue = 0;
        
        for (int i = 0; i < salesCount; i++) {
            Sale sale = sales[i];
            totalNetRevenue += sale.getPriceAfterDiscount();
            totalIva += sale.getIva();
            totalRevenue += sale.getFinalPrice();
            totalDiscounts += sale.getDiscountAmount();
            
            switch (sale.getCustomer().getType()) {
                case REGULAR:
                    regularRevenue += sale.getFinalPrice();
                    break;
                case STUDENT:
                    studentRevenue += sale.getFinalPrice();
                    break;
                case SENIOR:
                    seniorRevenue += sale.getFinalPrice();
                    break;
            }
        }
        
        // Display report
        System.out.println("Total entradas vendidas: " + totalSales);
        System.out.println("Ingresos netos (antes de IVA): $" + totalNetRevenue);
        System.out.println("Total IVA recaudado: $" + totalIva);
        System.out.println("Ingresos totales (con IVA): $" + totalRevenue);
        System.out.println("Total descuentos aplicados: $" + totalDiscounts);
        
        System.out.println("\nDesglose de Clientes:");
        System.out.println("Clientes regulares: " + regularCustomers + " (Ingresos: $" + regularRevenue + ")");
        System.out.println("Clientes estudiantes: " + studentCustomers + " (Ingresos: $" + studentRevenue + ")");
        System.out.println("Clientes adultos mayores: " + seniorCustomers + " (Ingresos: $" + seniorRevenue + ")");
        
        System.out.println("\nInformacion de Asientos:");
        int totalSeats = theater.getRows() * theater.getSeatsPerRow();
        int occupiedSeats = totalSeats - theater.getAvailableSeatsCount();
        double occupancyRate = (double) occupiedSeats / totalSeats * 100;
        
        System.out.println("Total asientos: " + totalSeats);
        System.out.println("Asientos ocupados: " + occupiedSeats);
        System.out.println("Asientos disponibles: " + theater.getAvailableSeatsCount());
        System.out.printf("Tasa de ocupacion: %.2f%%\n", occupancyRate);
    }
    
    // Add a new customer
    public Customer addCustomer(String name, String email, String phone, CustomerType type, int age) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Formato de email invalido");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono no puede estar vacio");
        }
        if (age <= 0 || age >= 120) {
            throw new IllegalArgumentException("Edad invalida");
        }
        
        // Check if we have space for a new customer
        if (customerCount >= MAX_CUSTOMERS) {
            throw new RuntimeException("Se ha alcanzado el numero maximo de clientes");
        }
        
        // Create and add the new customer
        Customer customer = new Customer(customerCount + 1, name, email, phone, type, age);
        customers[customerCount] = customer;
        customerCount++;
        
        return customer;
    }
    
    // Find a customer by ID
    public Customer findCustomerById(int id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId() == id) {
                return customers[i];
            }
        }
        return null;
    }
    
    // Sell a ticket
    public Sale sellTicket(Customer customer, int row, int seatNumber) {
        // Validate input
        if (customer == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        
        // Check if the seat is available
        if (!theater.isSeatAvailable(row, seatNumber)) {
            throw new IllegalArgumentException("El asiento no esta disponible");
        }
        
        // Get the seat and mark it as reserved
        Seat seat = theater.getSeat(row, seatNumber);
        seat.reserve();
        
        // Calculate the price with applicable discounts
        double originalPrice = BASE_TICKET_PRICE;
        double discountRate = getDiscountRate(customer);
        double discountAmount = originalPrice * discountRate;
        double priceAfterDiscount = originalPrice - discountAmount;
        double iva = priceAfterDiscount * IVA_RATE;
        double finalPrice = priceAfterDiscount + iva;
        
        // Create the sale record
        String date = getCurrentDate();
        Sale sale = new Sale(salesCount + 1, customer, seat, originalPrice, discountAmount, 
                            priceAfterDiscount, iva, finalPrice, date);
        
        // Add the sale to the array
        if (salesCount >= MAX_SALES) {
            throw new RuntimeException("Se ha alcanzado el numero maximo de ventas");
        }
        sales[salesCount] = sale;
        salesCount++;
        
        return sale;
    }
    
    // Get discount rate for a customer
    private double getDiscountRate(Customer customer) {
        for (Discount discount : discounts) {
            if (discount.getCustomerType() == customer.getType()) {
                return discount.getDiscountRate();
            }
        }
        return 0.0;
    }
    
    // Get all sales
    public Sale[] getAllSales() {
        Sale[] result = new Sale[salesCount];
        System.arraycopy(sales, 0, result, 0, salesCount);
        return result;
    }
    
    // Get all customers
    public Customer[] getAllCustomers() {
        Customer[] result = new Customer[customerCount];
        System.arraycopy(customers, 0, result, 0, customerCount);
        return result;
    }
    
    // Get current date as string
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

// Discount.java - Represents a discount type
class Discount {
    private String name;
    private CustomerType customerType;
    private double discountRate;
    
    public Discount(String name, CustomerType customerType, double discountRate) {
        this.name = name;
        this.customerType = customerType;
        this.discountRate = discountRate;
    }
    
    public String getName() {
        return name;
    }
    
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public double getDiscountRate() {
        return discountRate;
    }
    
    @Override
    public String toString() {
        return name + " (" + (discountRate * 100) + "%)";
    }
}