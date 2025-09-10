import ucn.*;

/**
 * El sistema de gestion de ventas para el minimarket.
 * Permite cargar los productos desde el archivo, registrar compras.
 * ver el stock y generar estadísticas de ventas. */
public class SuperJava {

    /**
     * Aqui pusimos el maximo número de productos que se pueden almacenar */
    static final int MAX = 25;

    /** Arreglos para almacenar la informacion de los productos */
    static String[] nombres_Producto = new String[MAX];
    static int[] precios = new int[MAX];
    static int[] stock = new int[MAX];
    static String[] categorias = new String[MAX];
    static int[] vendidos = new int[MAX];

    /** Las variables y estadísticas */
    static int productos_Total = 0;
    static int total_Vendido = 0;
    static int dinero_Recaudado = 0;

    /**
     * En esta parte cargamos los productos del archivo y muestramos el menu principal con las distintas opciones hasta que el usuario decida salir. */
    public static void main(String[] args) {
        StdOut.println(" Super Java ");
        cargarProductos();
        int opcion = 0;
        while (opcion != 4) {
            mostrarMenu();
            opcion = StdIn.readInt();

            if (opcion == 1) {
                verProductos();
            } else if (opcion == 2) {
                registrarCompra();
            } else if (opcion == 3) {
                verEstadisticas();
            } else if (opcion == 4) {
                StdOut.println("Gracias, Super Java se despide. Adios ");
            } else {
                StdOut.println(" Opción no disponible, por favor ingrese una opción valida. ");
            }
        }
    }

    /**
     * Muestra en la pantalla el menú principal. */
    public static void mostrarMenu() {
        StdOut.println("\n Menu ");
        StdOut.println("1. Ver productos ");
        StdOut.println("2. Registrar compra ");
        StdOut.println("3. Ver estadísticas ");
        StdOut.println("4. Salir ");
        StdOut.print("Elija una opción disponible ");
    }

    /**
     * Carga los productos desde el archivo y lo0s almacena. */
    public static void cargarProductos() {
        try {
            In arch = new In("Productos.txt");
            while (!arch.isEmpty()) {
                nombres_Producto[productos_Total] = arch.readString();
                precios[productos_Total] = arch.readInt();
                stock[productos_Total] = arch.readInt();
                categorias[productos_Total] = arch.readString();
                vendidos[productos_Total] = 0;
                productos_Total++;
            }
            arch.close();
        } catch (Exception e) {
            StdOut.println(" Error al leer el archivo de productos ");
        }
    }

    /**
     * Guarda los productos en el archivo en "Productos.txt". */
    public static void guardarProductos() {
        try {
            Out arch = new Out("Productos.txt");
            for (int i = 0; i < productos_Total; i++) {
                arch.println(nombres_Producto[i] + " " + precios[i] + " " + stock[i] + " " + categorias[i]);
            }
            arch.close();
        } catch (Exception e) {
            StdOut.println(" Error al guardar el archivo de productos ");
        }
    }

    /**
     * Muestra en pantalla la lista de productos disponibles.
     * Si un producto no tiene stock, se muestra un mensaje indicando que no hay stock. */
    public static void verProductos() {
        StdOut.println("\n Lista de productos ");
        for (int i = 0; i < productos_Total; i++) {
            if (stock[i] > 0) {
                StdOut.println((i + 1) + "." + nombres_Producto[i] + "-" + precios[i] +
                        " Stock " + stock[i] + "-" + categorias[i]);
            } else {
                StdOut.println((i + 1) + "." + nombres_Producto[i] + " No disponible ");
            }
        }
    }

    /**
     * Registra una compra de un producto.
     * Actualizamos el stock, la cantidad vendida, y el total de productos vendidos
     * Tambien el dinero recaudado. Si se llega a dar el caso de stock insuficiente, muestramos un mensaje que indica que no hay stock. */

    public static void registrarCompra() {
        verProductos();
        StdOut.print("\n Ingrese numero del producto ");
        int opcion = StdIn.readInt() - 1;

        if (opcion < 0 || opcion >= productos_Total) {
            StdOut.println("Producto inválido.");
            return;
        }

        StdOut.print("¿Cuantas unidades desea comprar? ");
        int cantidad = StdIn.readInt();

        if (cantidad <= stock[opcion]) {
            stock[opcion] -= cantidad;
            vendidos[opcion] += cantidad;
            total_Vendido += cantidad;
            int total = precios[opcion] * cantidad;
            dinero_Recaudado += total;

            StdOut.println("\n Comprobante ");
            StdOut.println(" Producto " + nombres_Producto[opcion]);
            StdOut.println(" Cantidad " + cantidad);
            StdOut.println(" Total a pagar " + total);
            StdOut.println(" Gracias por comprar en Super Java ");
        } else {
            StdOut.println("No hay suficiente stock.");
        }
    }

    /**
     * Muestra las estadísticas de las ventas
     * El total de productos vendidos
     * Dinero total que se recaudado
     * Producto mas vendido
     * Categoría más vendida */
    public static void verEstadisticas() {
        StdOut.println("\n Estadísticas ");
        StdOut.println(" Total de productos vendidos " + total_Vendido);
        StdOut.println(" Dinero recaudado " + dinero_Recaudado);


        int max = 0;
        int idxMax = -1;
        for (int i = 0; i < productos_Total; i++) {
            if (vendidos[i] > max) {
                max = vendidos[i];
                idxMax = i;
            }
        }
        if (idxMax != -1) {
            StdOut.println(" Producto mas vendido " + nombres_Producto[idxMax] +
                    " (" + vendidos[idxMax] + ")");
        } else {
            StdOut.println(" Producto mas vendido Ninguno");
        }
        String catMas = "";
        int maxCat = 0;
        for (int i = 0; i < productos_Total; i++) {
            int suma = 0;
            for (int j = 0; j < productos_Total; j++) {
                if (categorias[j].equals(categorias[i])) {
                    suma += vendidos[j];
                }
            }
            if (suma > maxCat) {
                maxCat = suma;
                catMas = categorias[i];
            }
        }
        if (maxCat > 0) {
            StdOut.println(" Categoria mas vendida: " + catMas + " (" + maxCat + ")");
        }
    }
}