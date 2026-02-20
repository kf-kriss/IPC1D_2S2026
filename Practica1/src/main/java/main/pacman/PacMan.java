/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main.pacman;

import java.util.Random;
import java.util.Scanner;

//Kristhoper Franklin Ucelo Pirir
//202502428

public class PacMan {
    
    static String[] historialUsuarios = new String[100];
    static int[] historialPuntos = new int[100];
    static int contadorHistorial = 0;

    //Main
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
    
        menu(scanner, random);
    }
    
    //Menú
    public static void menu(Scanner scanner, Random random) {
    int opcionmenu = 0;
    String usuario = "";
    
    while (opcionmenu != 3) {
        System.out.println("===== MENU PRINCIPAL =====");
        System.out.println("1. Iniciar Juego");
        System.out.println("2. Historial de Partidas");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opcion: ");

        opcionmenu = scanner.nextInt();

        switch (opcionmenu) {
            case 1:
                System.out.println("Iniciando juego...");
                
                usuario = solicitarUsuario(scanner);
                
                int[][] board = crearTablero(scanner);
                generarCantidades(board, random, scanner);
                mostrarTablero(board);
                int[]posicionPacman = colocarPacman(board,scanner);
                mostrarTablero(board);
                
                jugar(board, posicionPacman,usuario, scanner);
                
                break;
                
            case 2:
                if(contadorHistorial == 0){
                    System.out.println("No hay partidas registradas.");
                } else {
                    System.out.println("===== HISTORIAL =====");
                    for(int i = contadorHistorial - 1; i >= 0; i--){
                        System.out.println(historialUsuarios[i] + " - " + historialPuntos[i] + " puntos");
                    }
                }
                break;
                
            case 3:
                System.out.println("Saliendo del programa...");
                break;
                
            default:
                System.out.println("Opcion invalida.");
        }
    }
    }
    
    //Ingresar Usuario
    public static String solicitarUsuario(Scanner scanner) {
    scanner.nextLine(); // limpiar buffer

    System.out.print("Ingrese su usuario: ");
    String usuario = scanner.nextLine();

    System.out.println("Bienvenido " + usuario);

    return usuario;
    }
    
    
    //Crear Tablero
    public static int[][] crearTablero(Scanner scanner) {

    char tipo;

    System.out.print("Seleccione tipo de tablero (P = Pequeño | G = Grande): ");
    tipo = scanner.next().charAt(0);

    int filas;
    int columnas;

    if (tipo == 'P' || tipo == 'p') {
        filas = 5;
        columnas = 6;
    } else {
        filas = 10;
        columnas = 10;
    }

    int[][] board = new int[filas][columnas];

    // Inicializar en 0
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            board[i][j] = 0;
        }
    }
    return board;
    }
    
    
    //Generar Cantidades
    public static void generarCantidades(int[][]board,Random random, Scanner scanner){
    int filas = board.length;
    int columnas = board[0].length;
    int totalEspacios = filas * columnas;
        
    int maxPremiosSimples = (int)(totalEspacios * 0.30); // (int) corta, no redondea
    int maxPremiosEspeciales = (int)(totalEspacios * 0.10);
    int maxParedes = (int)(totalEspacios * 0.20);
    int maxFantasmas = (int)(totalEspacios * 0.20);

    int premios = 0;
    int especiales = 0;
    int paredes = 0;
    int fantasmas = 0;

    System.out.println("Premios simples (1 - " + maxPremiosSimples + ")");
    premios = scanner.nextInt();
        while(premios < 1 || premios > maxPremiosSimples){
        System.out.println("Numero invalido:");
        premios = scanner.nextInt();
        }

    System.out.println("Premios especiales (1 - " + maxPremiosEspeciales + ")");
    especiales = scanner.nextInt();
        while(especiales < 1 || especiales > maxPremiosEspeciales){
        System.out.println("Numero invalido:");
        especiales = scanner.nextInt();
        }

    System.out.println("Ingrese cantidad de paredes (1 - " + maxParedes + ")");
    paredes = scanner.nextInt();

        while(paredes < 1 || paredes > maxParedes){
        System.out.println("Numero invalido. Intente otra vez:");
        paredes = scanner.nextInt();
        }
    
    System.out.println("Ingrese cantidad de fantasmas (1 - " + maxFantasmas + ")");
    fantasmas = scanner.nextInt();

        while(fantasmas < 1 || fantasmas > maxFantasmas){
        System.out.println("Numero invalido. Intente otra vez:");
        fantasmas = scanner.nextInt();
        }
    
        
    int colocados;
    
    //Premio (2)
    colocados = 0;
    while (colocados < premios){
        int fila = random.nextInt(filas);
        int columna = random.nextInt(columnas);

        if(board[fila][columna] == 0){
            board[fila][columna] = 2;
            colocados++;
        }
    }
    
    // Premio especial (3)
    colocados = 0;
    while(colocados < especiales){
        int fila = random.nextInt(filas);
        int columna = random.nextInt(columnas);

        if(board[fila][columna] == 0){
            board[fila][columna] = 3;
            colocados++;
        }
    }
    
    //Pared (4)
    colocados = 0;
    while(colocados < paredes){
        int fila = random.nextInt(filas);
        int columna = random.nextInt(columnas);

        if(board[fila][columna] == 0){
            board[fila][columna] = 4;
            colocados++;
        }
    }
    
    //Fantasma (1)
    colocados = 0;
    while(colocados < fantasmas){
        int fila = random.nextInt(filas);
        int columna = random.nextInt(columnas);

        if(board[fila][columna] == 0){
            board[fila][columna] = 1;
            colocados++;
        }
    }
    
    
    }
    
    //Mostrar Tablero
    public static void mostrarTablero(int[][] board){
        
        for(int i = 0; i < board[0].length * 2 + 2; i++){
        System.out.print("-");
        }
        
        System.out.println();
        
    for(int i = 0; i < board.length; i++){
        
        System.out.print("|");
        
        for(int j = 0; j < board[0].length; j++){

            switch(board[i][j]){
                case 1 -> System.out.print("@ ");  // Fantasma
                case 2 -> System.out.print("0 ");  // Premio 
                case 3 -> System.out.print("$ ");  // Premio Especial
                case 4 -> System.out.print("X ");  // Pared
                case 9 -> System.out.print("< ");  // Pacman
                default -> System.out.print("  "); // Vacío
            }

        }
        System.out.println("|");
    }
        for(int i = 0; i < board[0].length * 2 + 2; i++){
        System.out.print("-");
        }
        System.out.println();
    }
    
    
    //Ingresar el Pac Man 
    public static int [] colocarPacman(int [][] board, Scanner scanner){
        int fila, columna;
        
        int filas = board.length;
        int columnas = board[0].length;
        
        System.out.println("Posicione su Pacman...");
        
        System.out.print("Ingrese fila (0 a " + (filas - 1) + "): ");
        fila = scanner.nextInt();

        System.out.print("Ingrese columna (0 a " + (columnas - 1) + "): ");
        columna = scanner.nextInt();

    while(fila < 0 || fila >= filas || columna < 0 || columna >= columnas 
          || board[fila][columna] != 0){

        System.out.println("Posicion invalida. Intente otra vez.");

        System.out.print("Ingrese fila (0 a " + (filas - 1) + "): ");
        fila = scanner.nextInt();

        System.out.print("Ingrese columna (0 a " + (columnas - 1) + "): ");
        columna = scanner.nextInt();
    }
    board[fila][columna] = 9;
    return new int[]{fila, columna};
    }
    
    
    //Jugar
    public static void jugar(int[][]board, int[]posicion,String usuario, Scanner scanner){
        
        int vidas = 3;
        int puntos = 0;
        int premiosRestantes = contarPremios(board);
        
        mostrarPanel(usuario, puntos, vidas);
        mostrarTablero(board);

        int filas = board.length;
        int columnas = board[0].length;
        
        boolean seguir = true;   
        
        while(seguir){

            mostrarTablero(board);
            mostrarPanel(usuario, puntos, vidas);

            System.out.println("Mover: 8 Arriba | 5 Abajo | 6 Derecha | 4 Izquierda | F Pausa");
            String entrada = scanner.next();

            if(entrada.equalsIgnoreCase("F")){
    
            System.out.println("1. Regresar");
            System.out.println("2. Terminar partida");

            int opcion = scanner.nextInt();

            if(opcion == 2){
                seguir = false;
                break;
            } else {
                continue;
            }
        }

        int movimiento = Integer.parseInt(entrada);

        int nuevaFila = posicion[0];
        int nuevaColumna = posicion[1];

        if(movimiento == 8) nuevaFila--;
        if(movimiento == 5) nuevaFila++;
        if(movimiento == 6) nuevaColumna++;
        if(movimiento == 4) nuevaColumna--;

        // -------- BORDES INFINITOS --------
        if(nuevaFila < 0) nuevaFila = board.length - 1;
        if(nuevaFila >= board.length) nuevaFila = 0;
        if(nuevaColumna < 0) nuevaColumna = board[0].length - 1;
        if(nuevaColumna >= board[0].length) nuevaColumna = 0;

        // -------- PARED --------
        if(board[nuevaFila][nuevaColumna] == 4){
            System.out.println("Hay una pared!");
            continue;
        }

        // -------- FANTASMA --------
        if(board[nuevaFila][nuevaColumna] == 1){
            vidas--;
            board[nuevaFila][nuevaColumna] = 0;
            System.out.println("Perdiste una vida!");
        }

        // -------- PREMIO SIMPLE --------
        if(board[nuevaFila][nuevaColumna] == 2){
            puntos += 10;
            board[nuevaFila][nuevaColumna] = 0;
            premiosRestantes--;
        }

        // -------- PREMIO ESPECIAL --------
        if(board[nuevaFila][nuevaColumna] == 3){
            puntos += 15;
            board[nuevaFila][nuevaColumna] = 0;
            premiosRestantes--;
        }

        // Mover Pacman
        board[posicion[0]][posicion[1]] = 0;
        posicion[0] = nuevaFila;
        posicion[1] = nuevaColumna;
        board[nuevaFila][nuevaColumna] = 9;

        // -------- VERIFICAR FIN --------
        if(vidas == 0){
            System.out.println("Perdiste el juego!");
            seguir = false;
        }

        if(premiosRestantes == 0){
            System.out.println("Ganaste el juego!");
            seguir = false;
        }  
        }
        
        historialUsuarios[contadorHistorial] = usuario;
        historialPuntos[contadorHistorial] = puntos;
        contadorHistorial++;
    }
    
    
    //Panel de Control
    public static void mostrarPanel(String usuario, int puntos, int vidas){

    System.out.println("=================================");
    System.out.println("Jugador: " + usuario);
    System.out.println("Punteo: " + puntos);
    System.out.println("Vidas: " + vidas);
    System.out.println("=================================");
    }

    
    //Contar Premios
    public static int contarPremios(int[][] board){

    int contador = 0;

    for(int i = 0; i < board.length; i++){
        for(int j = 0; j < board[0].length; j++){

            if(board[i][j] == 2 || board[i][j] == 3){
                contador++;
            }
        }
    }
    return contador;
    }
}
