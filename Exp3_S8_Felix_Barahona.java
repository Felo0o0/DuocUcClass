/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_felix_barahona;

/**
 *
 * @author felix
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Exp3_S8_Felix_Barahona {
    public static void main(String[] args) {
        // Inicializar el teatro
        Theater theater = new Theater("Teatro Moro", 8, 10); // 8 filas, 10 asientos por fila

        // Inicializar el sistema de ventas
        SalesSystem salesSystem = new SalesSystem(theater);

        // Ejecutar el menu interactivo
        salesSystem.runMenu();
    }
}

// Theater.java - Representa el teatro con asientos
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

        // Inicializar todos los asientos
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

// Seat.java - Representa un asiento individual en el teatro
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

// Customer.java - Representa un cliente
class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private CustomerType type;
    private int age; // Campo de edad añadido

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

// CustomerType.java - Enumeracion para diferentes tipos de clientes
enum CustomerType {
    REGULAR,
    STUDENT,
    SENIOR
}

// Sale.java - Representa una venta de entrada
class Sale {
    private int id;
    private Customer customer;
    private Seat seat;
    private double originalPrice;
    private double discountAmount;
    private double priceAfterDiscount;
    private double iva; // Campo IVA añadido
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

// Reservation.java - Representa una reserva de asiento
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

// SalesSystem.java - Maneja ventas, descuentos y reservas
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
    private static final double BASE_TICKET_PRICE = 10000.0; 
    private static final double IVA_RATE = 0.19; // Tasa de IVA del 19%

    // Contadores para tipos de clientes
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

        // Inicializar descuentos predeterminados
        initializeDiscounts();
    }

    private void initializeDiscounts() {
        // Añadir descuento para estudiantes (10%)
        discounts.add(new Discount("Descuento Estudiante", CustomerType.STUDENT, 0.10));

        // Añadir descuento para adultos mayores (15%)
        discounts.add(new Discount("Descuento Adulto Mayor", CustomerType.SENIOR, 0.15));
    }

    /**
     * Ejecuta el menu interactivo principal del sistema
     * Muestra las opciones disponibles y procesa la selección del usuario
     * Incluye opciones para ver asientos, comprar entradas, ver clientes,
     * generar reportes, eliminar ventas, actualizar clientes y cancelar reservas
     */
    public void runMenu() {
        boolean exit = false;

        System.out.println("===== Bienvenido al Sistema de Gestion del Teatro Moro =====");

        while (!exit) {
            System.out.println("\nMenu Principal");
            System.out.println("1. Ver Asientos Disponibles");
            System.out.println("2. Comprar Entrada");
            System.out.println("3. Ver Todos los Clientes");
            System.out.println("4. Ver Reporte de Ventas");
            System.out.println("5. Eliminar Venta");
            System.out.println("6. Actualizar Cliente");
            System.out.println("7. Cancelar Reserva");
            System.out.println("8. Salir");

            System.out.print("Ingrese su opcion: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumir nueva línea

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
                        deleteTicketInteractive();
                        break;
                    case 6:
                        updateCustomerInteractive();
                        break;
                    case 7:
                        cancelReservationInteractive();
                        break;
                    case 8:
                        exit = true;
                        System.out.println("Gracias por usar el Sistema de Gestion del Teatro Moro. Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opcion invalida. Por favor ingrese un numero entre 1 y 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor ingrese un numero.");
                scanner.nextLine(); // Consumir entrada inválida
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Método interactivo para vender una entrada
     * Este método guía al usuario a través del proceso completo de venta de una entrada:
     * 1. Verifica la disponibilidad de asientos en el teatro
     * 2. Recopila la información del cliente (nombre, edad, email, teléfono, tipo)
     * 3. Muestra el mapa de asientos para que el usuario seleccione uno disponible
     * 4. Procesa la venta aplicando descuentos según el tipo de cliente
     * 5. Muestra un resumen detallado de la venta realizada
     * 
     * Maneja excepciones para entradas inválidas y asientos no disponibles
     */
    private void sellTicketInteractive() {
        try {
            // Verificar si hay asientos disponibles
            if (theater.getAvailableSeatsCount() == 0) {
                System.out.println("Lo sentimos, no hay asientos disponibles en el teatro.");
                return;
            }

            // Obtener informacion del cliente
            Customer customer = getCustomerInfoInteractive();
            if (customer == null) return;

            // Mostrar mapa de asientos
            theater.displaySeatingChart();

            // Obtener seleccion de asiento
            int row = -1;
            int seatNumber = -1;
            boolean validSeat = false;

            // Bucle para validar la selección de asiento
            while (!validSeat) {
                try {
                    System.out.print("Ingrese numero de fila (1-" + theater.getRows() + "): ");
                    row = scanner.nextInt() - 1; // Convertir a índice base 0

                    System.out.print("Ingrese numero de asiento (1-" + theater.getSeatsPerRow() + "): ");
                    seatNumber = scanner.nextInt() - 1; // Convertir a índice base 0

                    scanner.nextLine(); // Consumir nueva línea

                    // Validar que el asiento esté dentro de los límites
                    if (row < 0 || row >= theater.getRows() || seatNumber < 0 || seatNumber >= theater.getSeatsPerRow()) {
                        System.out.println("Seleccion de asiento invalida. Por favor intente de nuevo.");
                    } else if (!theater.isSeatAvailable(row, seatNumber)) {
                        // Validar que el asiento esté disponible
                        System.out.println("Este asiento ya esta ocupado. Por favor seleccione otro asiento.");
                    } else {
                        validSeat = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada invalida. Por favor ingrese un numero.");
                    scanner.nextLine(); // Consumir entrada inválida
                }
            }

            // Procesar la venta del ticket
            Sale sale = sellTicket(customer, row, seatNumber);

            // Mostrar detalles de la venta
            System.out.println("\nEntrada vendida exitosamente!");
            System.out.println("=== Detalles de la Venta ===");
            System.out.println("Cliente: " + sale.getCustomer().getName());
            System.out.println("Asiento: " + sale.getSeat());
            System.out.println("Precio Base: $" + sale.getOriginalPrice());

            // Mostrar descuento si es aplicable
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

    /**
     * Método auxiliar para obtener información del cliente interactivamente
     * Este método guía al usuario a través del proceso de recopilación de datos del cliente:
     * 1. Solicita y valida el nombre del cliente (no puede estar vacío)
     * 2. Solicita y valida la edad (debe ser un número entre 1 y 119)
     * 3. Solicita y valida el email (debe contener '@')
     * 4. Solicita y valida el teléfono (no puede estar vacío)
     * 5. Determina el tipo de cliente basado en la edad y estado de estudiante:
     *    - Si es mayor de 60 años, se clasifica como SENIOR
     *    - Si es estudiante, se clasifica como STUDENT
     *    - De lo contrario, se clasifica como REGULAR
     * 
     * @return Un objeto Customer con la información recopilada, o null si ocurre un error
     */
    private Customer getCustomerInfoInteractive() {
        try {
            System.out.println("\n=== Informacion del Cliente ===");

            // Obtener nombre
            System.out.print("Ingrese nombre del cliente: ");
            String name = scanner.nextLine();
            while (name.trim().isEmpty()) {
                System.out.print("El nombre no puede estar vacio. Ingrese nombre del cliente: ");
                name = scanner.nextLine();
            }

            // Obtener edad
            int age = 0;
            boolean validAge = false;
            while (!validAge) {
                try {
                    System.out.print("Ingrese edad del cliente: ");
                    age = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    if (age > 0 && age < 120) {
                        validAge = true;
                    } else {
                        System.out.println("Edad invalida. Por favor ingrese una edad entre 1 y 119.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada invalida. Por favor ingrese un numero.");
                    scanner.nextLine(); // Consumir entrada inválida
                }
            }

            // Obtener email
            System.out.print("Ingrese email del cliente: ");
            String email = scanner.nextLine();
            while (!email.contains("@") || email.trim().isEmpty()) {
                System.out.print("Formato de email invalido. Ingrese email del cliente: ");
                email = scanner.nextLine();
            }

            // Obtener telefono
            System.out.print("Ingrese telefono del cliente: ");
            String phone = scanner.nextLine();
            while (phone.trim().isEmpty()) {
                System.out.print("El telefono no puede estar vacio. Ingrese telefono del cliente: ");
                phone = scanner.nextLine();
            }

            // Determinar tipo de cliente basado en edad y estado de estudiante
            CustomerType type = CustomerType.REGULAR;

            if (age >= 60) {
                // Si es mayor de 60 años, se clasifica como adulto mayor
                type = CustomerType.SENIOR;
                seniorCustomers++;
            } else {
                // Preguntar si es estudiante
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

            // Añadir el cliente
            return addCustomer(name, email, phone, type, age);

        } catch (Exception e) {
            System.out.println("Error al obtener informacion del cliente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Muestra todos los clientes registrados en el sistema
     * Si no hay clientes, muestra un mensaje indicándolo
     */
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

    /**
     * Genera un reporte detallado de ventas
     * Incluye información sobre:
     * - Total de entradas vendidas
     * - Ingresos netos y totales
     * - IVA recaudado
     * - Descuentos aplicados
     * - Desglose de clientes por tipo
     * - Información de ocupación de asientos
     */
    private void generateSalesReport() {
        if (salesCount == 0) {
            System.out.println("No hay datos de ventas disponibles para el reporte.");
            return;
        }

        System.out.println("\n===== REPORTE DE VENTAS =====");

        // Calcular totales
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

        // Mostrar reporte
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

    /**
     * Método interactivo para eliminar una venta
     * Solicita al usuario el ID de la venta a eliminar y procesa la eliminación
     * Muestra mensajes de éxito o error según corresponda
     */
    private void deleteTicketInteractive() {
        if (salesCount == 0) {
            System.out.println("No hay ventas registradas para eliminar.");
            return;
        }

        // Mostrar todas las ventas para que el usuario pueda elegir
        System.out.println("\n===== Ventas Registradas =====");
        for (int i = 0; i < salesCount; i++) {
            Sale sale = sales[i];
            System.out.println("ID: " + sale.getId() + 
                    ", Cliente: " + sale.getCustomer().getName() + 
                    ", Asiento: " + sale.getSeat() + 
                    ", Fecha: " + sale.getDate());
        }

        try {
            System.out.print("\nIngrese el ID de la venta a eliminar: ");
            int saleId = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            boolean deleted = deleteSale(saleId);

            if (deleted) {
                System.out.println("Venta eliminada exitosamente.");
            } else {
                System.out.println("No se encontró una venta con el ID especificado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Por favor ingrese un numero.");
            scanner.nextLine(); // Consumir entrada inválida
        }
    }

    /**
     * Método interactivo para actualizar información de un cliente
     * Solicita al usuario el ID del cliente y los nuevos datos a actualizar
     * Permite mantener los datos actuales si no se desea cambiarlos
     */
    private void updateCustomerInteractive() {
        if (customerCount == 0) {
            System.out.println("No hay clientes registrados para actualizar.");
            return;
        }

        // Mostrar todos los clientes para que el usuario pueda elegir
        displayAllCustomers();

        try {
            System.out.print("\nIngrese el ID del cliente a actualizar: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            Customer customer = findCustomerById(customerId);

            if (customer == null) {
                System.out.println("No se encontró un cliente con el ID especificado.");
                return;
            }

            System.out.println("\nDatos actuales del cliente:");
            System.out.println("Nombre: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Teléfono: " + customer.getPhone());

            // Solicitar nuevos datos (o dejar en blanco para mantener los actuales)
            System.out.println("\nIngrese los nuevos datos (deje en blanco para mantener los actuales):");

            System.out.print("Nuevo nombre: ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = null;
            }

            System.out.print("Nuevo email: ");
            String newEmail = scanner.nextLine();
            if (newEmail.trim().isEmpty()) {
                newEmail = null;
            } else if (!newEmail.contains("@")) {
                System.out.println("Formato de email inválido. No se actualizará el email.");
                newEmail = null;
            }

            System.out.print("Nuevo teléfono: ");
            String newPhone = scanner.nextLine();
            if (newPhone.trim().isEmpty()) {
                newPhone = null;
            }

            boolean updated = updateCustomer(customerId, newName, newEmail, newPhone);

            if (updated) {
                System.out.println("Información del cliente actualizada exitosamente.");
            } else {
                System.out.println("No se pudo actualizar la información del cliente.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Por favor ingrese un numero para el ID.");
            scanner.nextLine(); // Consumir entrada inválida
        }
    }

    /**
     * Método interactivo para cancelar una reserva
     * Solicita al usuario el ID de la reserva a cancelar y procesa la cancelación
     * Muestra mensajes de éxito o error según corresponda
     */
    private void cancelReservationInteractive() {
        if (reservations.isEmpty()) {
            System.out.println("No hay reservas registradas para cancelar.");
            return;
        }

        // Mostrar todas las reservas para que el usuario pueda elegir
        System.out.println("\n===== Reservas Registradas =====");
        for (Reservation reservation : reservations) {
            System.out.println("ID: " + reservation.getId() + 
                    ", Cliente: " + reservation.getCustomer().getName() + 
                    ", Asiento: " + reservation.getSeat() + 
                    ", Fecha: " + reservation.getDate());
        }

        try {
            System.out.print("\nIngrese el ID de la reserva a cancelar: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            boolean canceled = cancelReservation(reservationId);

            if (canceled) {
                System.out.println("Reserva cancelada exitosamente.");
            } else {
                System.out.println("No se encontró una reserva con el ID especificado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Por favor ingrese un numero.");
            scanner.nextLine(); // Consumir entrada inválida
        }
    }

    /**
     * Elimina una venta del sistema por su ID
     * @param saleId ID de la venta a eliminar
     * @return true si la venta fue eliminada correctamente, false si no se encontró
     */
    public boolean deleteSale(int saleId) {
        // Buscar la venta por su ID
        int indexToRemove = -1;
        for (int i = 0; i < salesCount; i++) {
            if (sales[i].getId() == saleId) {
                indexToRemove = i;
                break;
            }
        }

        // Si no se encontró la venta, retornar false
        if (indexToRemove == -1) {
            return false;
        }

        // Liberar el asiento asociado a la venta
        sales[indexToRemove].getSeat().cancelReservation();

        // Desplazar los elementos para eliminar la venta
        for (int i = indexToRemove; i < salesCount - 1; i++) {
            sales[i] = sales[i + 1];
        }

        // Reducir el contador de ventas y limpiar la última posición
        salesCount--;
        sales[salesCount] = null;

        return true;
    }

    /**
     * Actualiza la información de un cliente existente
     * @param id ID del cliente a actualizar
     * @param name Nuevo nombre (o null para mantener el actual)
     * @param email Nuevo email (o null para mantener el actual)
     * @param phone Nuevo teléfono (o null para mantener el actual)
     * @return true si el cliente fue actualizado, false si no se encontró
     */
    public boolean updateCustomer(int id, String name, String email, String phone) {
        // Buscar el cliente por su ID
        Customer customer = findCustomerById(id);

        if (customer == null) {
            return false;
        }

        // Crear un nuevo cliente con los datos actualizados
        // (manteniendo los valores actuales si los nuevos son null)
        Customer updatedCustomer = new Customer(
            id,
            (name != null) ? name : customer.getName(),
            (email != null) ? email : customer.getEmail(),
            (phone != null) ? phone : customer.getPhone(),
            customer.getType(),
            customer.getAge()
        );

        // Actualizar el cliente en el arreglo
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId() == id) {
                customers[i] = updatedCustomer;
                return true;
            }
        }

        return false;
    }

    /**
     * Cancela una reserva existente por su ID
     * @param reservationId ID de la reserva a cancelar
     * @return true si la reserva fue cancelada, false si no se encontró
     */
    public boolean cancelReservation(int reservationId) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getId() == reservationId) {
                // Liberar el asiento
                reservation.getSeat().cancelReservation();
                // Eliminar la reserva de la lista
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Añade un nuevo cliente al sistema
     * @param name Nombre del cliente
     * @param email Email del cliente
     * @param phone Teléfono del cliente
     * @param type Tipo de cliente (REGULAR, STUDENT, SENIOR)
     * @param age Edad del cliente
     * @return El cliente creado
     * @throws IllegalArgumentException si los datos son inválidos
     * @throws RuntimeException si se alcanza el límite de clientes
     */
    public Customer addCustomer(String name, String email, String phone, CustomerType type, int age) {
        // Validar entrada
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

        // Verificar si tenemos espacio para un nuevo cliente
        if (customerCount >= MAX_CUSTOMERS) {
            throw new RuntimeException("Se ha alcanzado el numero maximo de clientes");
        }

        // Crear y añadir el nuevo cliente
        Customer customer = new Customer(customerCount + 1, name, email, phone, type, age);
        customers[customerCount] = customer;
        customerCount++;

        return customer;
    }

    /**
     * Encuentra un cliente por su ID
     * @param id ID del cliente a buscar
     * @return El cliente encontrado o null si no existe
     */
    public Customer findCustomerById(int id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId() == id) {
                return customers[i];
            }
        }
        return null;
    }

    /**
     * Vende una entrada a un cliente para un asiento específico
     * @param customer Cliente que compra la entrada
     * @param row Fila del asiento
     * @param seatNumber Número del asiento en la fila
     * @return La venta realizada
     * @throws IllegalArgumentException si los datos son inválidos
     * @throws RuntimeException si se alcanza el límite de ventas
     */
    public Sale sellTicket(Customer customer, int row, int seatNumber) {
        // Validar entrada
        if (customer == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        // Verificar si el asiento está disponible
        if (!theater.isSeatAvailable(row, seatNumber)) {
            throw new IllegalArgumentException("El asiento no esta disponible");
        }

        // Obtener el asiento y marcarlo como reservado
        Seat seat = theater.getSeat(row, seatNumber);
        seat.reserve();

        // Calcular el precio con descuentos aplicables
        double originalPrice = BASE_TICKET_PRICE;
        double discountRate = getDiscountRate(customer);
        double discountAmount = originalPrice * discountRate;
        double priceAfterDiscount = originalPrice - discountAmount;
        double iva = priceAfterDiscount * IVA_RATE;
        double finalPrice = priceAfterDiscount + iva;

        // Crear el registro de venta
        String date = getCurrentDate();
        Sale sale = new Sale(salesCount + 1, customer, seat, originalPrice, discountAmount, 
                            priceAfterDiscount, iva, finalPrice, date);

        // Añadir la venta al arreglo
        if (salesCount >= MAX_SALES) {
            throw new RuntimeException("Se ha alcanzado el numero maximo de ventas");
        }
        sales[salesCount] = sale;
        salesCount++;

        return sale;
    }

    /**
     * Obtiene la tasa de descuento aplicable a un cliente
     * @param customer Cliente para el que se calcula el descuento
     * @return Tasa de descuento (0.0 a 1.0)
     */
    private double getDiscountRate(Customer customer) {
        for (Discount discount : discounts) {
            if (discount.getCustomerType() == customer.getType()) {
                return discount.getDiscountRate();
            }
        }
        return 0.0;
    }

    /**
     * Obtiene todas las ventas registradas
     * @return Arreglo con todas las ventas
     */
    public Sale[] getAllSales() {
        Sale[] result = new Sale[salesCount];
        System.arraycopy(sales, 0, result, 0, salesCount);
        return result;
    }

    /**
     * Obtiene todos los clientes registrados
     * @return Arreglo con todos los clientes
     */
    public Customer[] getAllCustomers() {
        Customer[] result = new Customer[customerCount];
        System.arraycopy(customers, 0, result, 0, customerCount);
        return result;
    }

    /**
     * Obtiene la fecha y hora actual formateada
     * @return Cadena con la fecha y hora actual
     */
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

// Discount.java - Representa un tipo de descuento
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
