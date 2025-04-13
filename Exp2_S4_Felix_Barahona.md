llevo horas pensando y usando copilot para redactar este documento bien asi que aqué esta el resultado

1. Fase de Planificación
Antes de comenzar a escribir el código, se realizó una planificación detallada del sistema para identificar sus componentes principales y la forma en que interactuarían entre sí.
1.1. Identificación de Requisitos
Se identificaron los siguientes requisitos principales para el sistema:
•	Mostrar un menú principal con opciones para comprar entradas o salir.
•	Mostrar un plano del teatro con la disposición de los asientos en diferentes zonas.
•	Permitir la selección de zona y asiento, validando que el asiento exista y esté disponible.
•	Solicitar la edad del comprador para aplicar descuentos según corresponda.
•	Mostrar un resumen de cada compra realizada.
•	Permitir realizar múltiples compras sin reiniciar el programa.
•	Mostrar un resumen final de todas las compras realizadas al salir del programa.
1.2. Diseño de la Estructura de Datos
Se diseñó la siguiente estructura de datos para almacenar la información necesaria:
•	Arrays booleanos para representar los asientos de cada zona (VIP, Platea Baja y Platea Alta).
•	Arrays paralelos para almacenar la información de cada venta (zona, número de asiento, precio base, descuento aplicado y precio final).
•	Variable para llevar el registro del número total de ventas realizadas.
1.3. Diseño del Flujo del Programa
Se diseñó el siguiente flujo para el programa:
•	Mostrar el menú principal.
•	Si se selecciona 'Comprar entrada', iniciar el proceso de compra.
•	Si se selecciona 'Salir', mostrar el resumen final (si se realizaron compras) y terminar el programa.
•	El proceso de compra incluye: mostrar el plano del teatro, solicitar zona y asiento, validar la selección, solicitar edad, calcular precio y descuentos, mostrar resumen y preguntar si desea realizar otra compra.
2. Fase de Implementación
Una vez completada la fase de planificación, se procedió a implementar el código siguiendo un enfoque modular y estructurado.
2.1. Creación de la Clase Principal
Se creó la clase TeatroMoro como contenedor principal de toda la lógica del sistema. Esta clase incluye el método main() como punto de entrada del programa.

public class TeatroMoro {
    public static void main(String[] args) {
        // Código del programa
    }
}

2.2. Declaración de Variables
Se declararon las variables necesarias para almacenar la información del sistema:

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

2.3. Implementación del Menú Principal
Se implementó el menú principal utilizando un ciclo for sin condición de finalización explícita, controlado por una variable booleana:

// Menu principal con ciclo for
for (; continuar;) {
    try {
        System.out.println("\n=== TEATRO MORO ===");
        System.out.println("1. Comprar entrada");
        System.out.println("2. Salir");
        System.out.print("Seleccione opcion: ");
        int opcion = entrada.nextInt();

        if (opcion == 1) {
            // Proceso de compra
        } else if (opcion == 2) {
            // Proceso de salida
            continuar = false;
        } else {
            System.out.println("Opcion invalida");
        }
    } catch (Exception e) {
        System.out.println("Error: Ingrese un valor valido");
        entrada.nextLine();
    }
}

2.4. Implementación del Proceso de Compra
Se implementó el proceso de compra utilizando un ciclo while que permite realizar múltiples compras:

boolean comprando = true;
while(comprando) {
    // Mostrar plano del teatro
    mostrarPlanoTeatro(asientosVIP, asientosPB, asientosPA);

    // Solicitar y validar zona
    // Solicitar y validar asiento
    // Solicitar edad y calcular descuentos
    // Registrar venta y mostrar resumen
    // Preguntar si desea realizar otra compra
}

2.5. Implementación de la Visualización del Teatro
Se implementó la visualización del plano del teatro utilizando ciclos for para recorrer los arrays de asientos:

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

private static void mostrarFila(boolean[] asientos, int inicio, int fin, String etiqueta) {
    System.out.println(etiqueta);
    for (int i = inicio; i < fin; i++) {
        System.out.print(asientos[i] ? "  X  " : String.format(" %3d ", i + 1));
    }
    System.out.println();
}

2.6. Implementación de la Selección y Validación de Asientos
Se implementó la selección y validación de asientos utilizando estructuras condicionales y un ciclo do-while:

// Seleccion de zona con if-else
System.out.print("\nSeleccione zona (V=VIP, B=Platea Baja, A=Platea Alta): ");
char zona = entrada.next().toUpperCase().charAt(0);
if (zona == 'V' || zona == 'B' || zona == 'A') {
    switch (zona) {
        case 'V': precioBase = 30000; break;
        case 'B': precioBase = 15000; break;
        case 'A': precioBase = 18000; break;
    }

    // Seleccion de asiento con do-while
    int asiento;
    boolean asientoValido;
    do {
        System.out.print("Ingrese numero de asiento: ");
        asiento = entrada.nextInt();
        asientoValido = validarAsiento(zona, asiento, asientosVIP, asientosPB, asientosPA);

        if (!asientoValido) {
            System.out.println("Asiento invalido o ocupado");
        }
    } while (!asientoValido);

    // Resto del código...
} else {
    System.out.println("Zona invalida");
}

2.7. Implementación de la Validación de Asientos
Se implementó la validación de asientos en un método separado para mejorar la modularidad del código:

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

2.8. Implementación de la Validación de Edad y Cálculo de Descuentos
Se implementó la validación de edad y el cálculo de descuentos utilizando estructuras condicionales:

// Validacion de edad y calculo de descuento con if-else
System.out.print("Ingrese edad del comprador: ");
int edad = entrada.nextInt();

// Verificar si la edad es menor a 3
if (edad < 3) {
    System.out.println("\nLo sentimos, no se permite la entrada a menores de 3 años.");
    System.out.println("Esto se debe a que el teatro requiere silencio absoluto durante las funciones.");
    continue; // Volver al inicio del proceso de compra
}

precioFinal = precioBase;
double descuento = calcularDescuento(edad, precioBase);
precioFinal = precioBase - descuento;

// Método para calcular descuento
private static double calcularDescuento(int edad, double precioBase) {
    if (edad >= 60) {
        return precioBase * 0.15; // 15% descuento tercera edad
    } else if (edad <= 18) {
        return precioBase * 0.10; // 10% descuento estudiante
    }
    return 0;
}

2.9. Implementación del Registro de Ventas
Se implementó el registro de ventas almacenando la información en los arrays correspondientes:

// Guardar informacion de la venta
zonasVendidas[totalVentas] = zona;
asientosVendidos[totalVentas] = asiento;
preciosBase[totalVentas] = precioBase;
descuentosAplicados[totalVentas] = descuento;
preciosFinales[totalVentas] = precioFinal;
totalVentas++;

2.10. Implementación del Resumen de Compra
Se implementó el resumen de compra mostrando la información detallada de la transacción:

// Mostrar resumen de compra
mostrarResumenCompra(zona, asiento, precioBase, descuento, precioFinal);

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

2.11. Implementación de la Opción de Realizar Otra Compra
Se implementó la opción de realizar otra compra utilizando un ciclo while para validar la respuesta del usuario:

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

2.12. Implementación del Resumen Final
Se implementó el resumen final mostrando la información detallada de todas las transacciones realizadas:

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

2.13. Implementación del Manejo de Excepciones
Se implementó el manejo de excepciones utilizando una estructura try-catch para capturar y manejar posibles errores durante la entrada de datos:

try {
    // Código que puede generar excepciones
} catch (Exception e) {
    System.out.println("Error: Ingrese un valor valido");
    entrada.nextLine(); // Limpiar el buffer de entrada
}

3. Fase de Pruebas
Una vez completada la implementación, se realizaron pruebas exhaustivas para verificar el correcto funcionamiento del sistema.
3.1. Pruebas de Funcionalidad
Se realizaron las siguientes pruebas de funcionalidad:
•	Verificar que el menú principal se muestre correctamente y responda adecuadamente a las opciones seleccionadas.
•	Verificar que el plano del teatro se muestre correctamente y refleje el estado actual de los asientos.
•	Verificar que la selección de zona y asiento funcione correctamente, validando que el asiento exista y esté disponible.
•	Verificar que el cálculo de descuentos según la edad funcione correctamente.
•	Verificar que el resumen de compra muestre la información correcta.
•	Verificar que se pueda realizar múltiples compras sin reiniciar el programa.
•	Verificar que el resumen final muestre la información correcta de todas las compras realizadas.
3.2. Pruebas de Validación
Se realizaron las siguientes pruebas de validación:
•	Verificar que el sistema valide correctamente la zona seleccionada, aceptando solo las opciones válidas (V, B, A).
•	Verificar que el sistema valide correctamente el asiento seleccionado, aceptando solo asientos que existan en la zona seleccionada y que estén disponibles.
•	Verificar que el sistema valide correctamente la edad del comprador, rechazando edades menores a 3 años.
•	Verificar que el sistema valide correctamente la respuesta del usuario cuando se le pregunta si desea realizar otra compra, aceptando solo las opciones válidas (S, N).
•	Verificar que el sistema maneje correctamente las excepciones durante la entrada de datos, mostrando mensajes de error adecuados y permitiendo al usuario corregir su entrada.
3.3. Pruebas de Robustez
Se realizaron las siguientes pruebas de robustez:
•	Verificar que el sistema maneje correctamente entradas inválidas en el menú principal.
•	Verificar que el sistema maneje correctamente entradas inválidas en la selección de zona.
•	Verificar que el sistema maneje correctamente entradas inválidas en la selección de asiento.
•	Verificar que el sistema maneje correctamente entradas inválidas en la edad del comprador.
•	Verificar que el sistema maneje correctamente entradas inválidas en la respuesta del usuario cuando se le pregunta si desea realizar otra compra.
4. Fase de Refinamiento
Después de las pruebas, se realizaron ajustes y mejoras al código para optimizar su funcionamiento y mejorar la experiencia del usuario.
4.1. Mejoras Visuales
Se realizaron las siguientes mejoras visuales:
•	Se mejoró la presentación del plano del teatro, agregando líneas divisorias entre las zonas y una representación del escenario.
•	Se mejoró la alineación de los números de asiento utilizando la función String.format() para garantizar que todos los números ocupen el mismo espacio.
•	Se mejoró la presentación de los resúmenes de compra y final, agregando separadores y mejorando la organización de la información.
4.2. Mejoras Funcionales
Se realizaron las siguientes mejoras funcionales:
•	Se agregó una validación para rechazar la entrada de menores de 3 años, mostrando un mensaje educado explicando la razón.
•	Se mejoró la validación de la respuesta del usuario cuando se le pregunta si desea realizar otra compra, utilizando un ciclo while para solicitar repetidamente la respuesta hasta que se ingrese una opción válida.
•	Se modularizó el código extrayendo funcionalidades repetitivas en métodos separados, lo que mejoró la legibilidad y mantenibilidad del código.
4.3. Optimizaciones
Se realizaron las siguientes optimizaciones:
•	Se redujo la duplicación de código extrayendo funcionalidades repetitivas en métodos separados.
•	Se mejoró el manejo de excepciones para aumentar la robustez del programa.
•	Se optimizó la estructura del código para mejorar su legibilidad y mantenibilidad.
5. Conclusión
El desarrollo del sistema de venta de entradas para el Teatro Moro siguió un proceso estructurado que incluyó planificación, implementación, pruebas y refinamiento. El resultado es un programa robusto y funcional que cumple con todos los requisitos especificados.
El código está modularizado en métodos con responsabilidades claras y específicas, lo que mejora su legibilidad y mantenibilidad. Además, implementa validaciones para garantizar que los datos ingresados sean correctos y maneja excepciones para aumentar su robustez.
El sistema permite a los usuarios seleccionar asientos en diferentes zonas del teatro, aplica descuentos según la edad del comprador y muestra resúmenes detallados de las compras realizadas. Además, implementa validaciones para garantizar que los datos ingresados sean correctos y que los asientos seleccionados estén disponibles.
