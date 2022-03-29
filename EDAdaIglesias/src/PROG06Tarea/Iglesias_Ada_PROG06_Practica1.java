
package PROG06Tarea;

import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

/**
 * Este programa permite jugar a un juego de preguntas y respuestas en el que hay que tratar de acertar el resultado de varias expresiones matemáticas.
 * 
 * Al principio de cada partida se le piden a los jugadores sus nombres, se sortea el turno de cada jugador, y se pone su marcador a 0 puntos.
 * Después se pasa al juego en sí,en el que en varias rondas los jugadores tienen que acertar el resultado de una expresión matemática generada
 * aleatoriamente. Si el jugador acierta, se llevará un punto, y si falla, su puntuación se quedará como estaba. 
 * No hay rebotes así que cada jugador contesta a una expresión matemática diferente generada para ese jugador.
 * Después de cada ronda se muestran las puntuaciones de todos los jugadores y al finalizar la partida se vuelven a mostrar, y se muestra el nombre
 * del ganador o ganadores del juego.
 * 
 * El número de jugadores permitido es de: 1-6 jugadores, y los nombres de los jugadores no pueden tener espacios ni pueden repetirse.
 * 
 * El número de rondas a las que se puede jugar es: 3 (partida rápida), 5 (partida corta), 10 (partida normal) o 20 (partida larga).
 * 
 * Todas las expresiones matemáticas tienen entre 4 y 8 enteros, con un valor de entre 2 y 12, y están formadas por sumas, restas y multiplicaciones.
 * 
 * @author Ada Iglesias Pasamontes
 * 
 * date 10-03-2022
 */
public class Iglesias_Ada_PROG06_Practica1 {
    
    // Variables generales del programa:
    // Variables que almacenan el número de jugadores y el número de rondas
    static int numJugadores = 0, numRondas = 0;
    
    // Variables que almacenan el resultado de cada expresión matemática y la respuesta que introduce cada jugador
    static int resultado = 0, respuesta = 0;
    
    // Vector que almacena los nombres de los jugadores
    static String[] jugadores;
    
    // Matriz que almacena en una columna el turno de cada jugador y en otra sus puntuaciones totales
    static int[][] puntuaciones;
    
    // objeto Scanner para leer lo que introduzca el usuario
    static Scanner entrada = new Scanner(System.in);
    
    /**
     * Método main que ejecuta todo el juego 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        iniciarJuego();
        
        jugarPartida();
        
        mostrarGanador();
    }
    
    
    /**
     * Método que llama a todas las funciones necesarias para preparar la partida antes de empezar a jugar
     */
    public static void iniciarJuego() {
        
        inicializarJugadores();
        
        inicializarTipoJuego();
        
        ordenarJugadores();
    }
    
    
    /**
     * Método que le pide al usuario el nº de jugadores de la partida y rellena el array jugadores con los nombres de los jugadores.
     * El número de jugadores tiene que ser entre 1 y 6.
     * Los nombres tienen que ser únicos (es decir, no puede haber dos jugadores con el mismo nombre) y no pueden tener espacios.
     */
    public static void inicializarJugadores() {
        
        // Se le pide al usuario el número de jugadores
        do {
            System.out.print("Introduce el número de jugadores (max 6): ");
            
            numJugadores = entrada.nextInt();
            entrada.nextLine();
            
            if(numJugadores < 1 || numJugadores > 6) {
                System.out.print("Número de jugadores no válido. ");
            }
        }
        while (numJugadores < 1 || numJugadores > 6); 
        
        // Se indica ya el número de elementos del array jugadores que será el número de jugadores
        jugadores = new String[numJugadores];
        
        // Se rellena el array jugadores con los nombres de los jugadores
        for(int i = 0; i < jugadores.length; i++) {
            
            do {
                System.out.println("Introduce el nombre del jugador " + (i+1) + ": ");
            
                jugadores[i] = entrada.nextLine();
                
                if(jugadores[i].contains(" ")) {
                    System.out.print("El nombre no puede contener espacios. ");
                }
                
                if(esRepetido(i)) {
                    System.out.print("El nombre tiene que ser único. ");
                }
            }
            while(jugadores[i].contains(" ") || esRepetido(i)); 
        }
    }
    
    
    /**
     * Función que comprueba si el último nombre introducido de un jugador es un nombre repetido en la partida o no
     * 
     * @param i que es el índice en el array jugadores del último nombre introducido
     * @return true o false según si el nombre ya existe en el array jugadores o no
     */
    public static boolean esRepetido(int i) {
        
        boolean esRepetido = false;
        
        // Se comprueba el último nombre introducido con todos los nombres que se han introducido anteriormente
        for(int j = i - 1; j >= 0; j--) {
            
            if(jugadores[i].compareToIgnoreCase(jugadores[j]) == 0) {
                esRepetido = true;
            }
        }
        
        return esRepetido;
    }
    
    
    /**
     * Método que le pide al usuario cuántas rondas quiere jugar.
     * El usuario puede elegir entre 3 rondas (partida rápida), 5 rondas (partida corta), 10 rondas (partida normal)
     * o 20 rondas (partida larga)
     */
    public static void inicializarTipoJuego() {
        
        do{
            System.out.println("\nIntroduce el número de rondas que quiere jugar: 3 (partida rápida), 5 (partida corta)," 
                            + " 10 (partida normal), 20 (partida larga)");
            
            numRondas = entrada.nextInt();
            entrada.nextLine();
            
            if(!(numRondas == 3 || numRondas == 5 || numRondas == 10 || numRondas == 20)) {
                System.out.print("Número de rondas no válido. ");
            }
        }
        while(!(numRondas == 3 || numRondas == 5 || numRondas == 10 || numRondas == 20));
    }
    
    
    /**
     * Método que ordena los jugadores de forma aleatoria e inicializa sus puntuaciones a 0.
     * El orden aleatorio de los jugadores se almacena en la primera columna de la matriz puntuaciones.
     * Las puntuaciones inicializadas a 0 se almacenan en la segunda columna de la matriz puntuaciones.
     */
    public static void ordenarJugadores() {
        
        // Una vez se sabe el número de jugadores se indica el número de elementos que tendrá la matriz puntuaciones
        puntuaciones = new int[numJugadores][2];
        
        /* Vector auxiliar que se utiliza para evitar que se elija el turno de un mismo jugador más de una vez. 
        Cada elemento del vector representa si el jugador almacenado en la misma posición del array jugadores ya 
        tiene el turno asignado o no*/
        boolean[] jugadoresYaElegidos = new boolean[jugadores.length];
        Arrays.fill(jugadoresYaElegidos, false);
        
        // Variable auxiliar que almacena el número del jugador que se va a colocar en la matriz puntuaciones.
        int jugadorAleatorio = 0;
        
        for (int i = 0; i < puntuaciones.length; i++) {
            
            // Se elige el jugador que va a tener el turno i + 1
            do {
                jugadorAleatorio = (int)(Math.random()*jugadores.length);
            }
            while(jugadoresYaElegidos[jugadorAleatorio] == true);
            
            // Se indica en el vector auxiliar que el jugador ya tiene turno
            jugadoresYaElegidos[jugadorAleatorio] = true;
            
            // Se guarda el número de jugador y una puntuación de 0 en la matriz puntuaciones
            puntuaciones[i][0] = jugadorAleatorio;
            puntuaciones[i][1] = 0;
        }
    }
    
    
    /**
     * Método que ejecuta el número de rondas indicado por el usuario y muestra las puntuaciones al final de cada ronda
     */
    public static void jugarPartida() {
        
        for(int i = 0; i < numRondas; i++) {
            
            jugarRonda();
            
            mostrarPuntuaciones();
        }
    }
    
    
    /**
     * Método que le pregunta a cada jugador el resultado de una expresión matemática generada aleatoriamente y le 
     * indica si ha acertado o no y aumenta su puntuación si ha acertado
     */
    public static void jugarRonda(){
        
        for(int i = 0; i < puntuaciones.length; i++) {
            
            String expresionMatematica = generarExpresionMatematica();
            
            resultado = evaluarExpresion(expresionMatematica);
            
            System.out.println("\nPregunta para " + jugadores[puntuaciones[i][0]] + ", ¿cuál es el resultado de la "
                    + "siguiente expresión matemática?: " + expresionMatematica);
            
            System.out.println(resultado);
            
            respuesta = entrada.nextInt();
            entrada.nextLine();
            
            // Se compara el resultado calculado con la respuesta del jugador
            if(comprobarRespuesta()) {  
                System.out.println("¡Respuesta correcta!");
                puntuaciones[i][1]++;
            }
            else {
                System.out.println("¡Respuesta incorrecta! La respuesta correcta era: " + resultado);
            }
        }
    }
    
    
    /**
     * Función que genera una expresión matemática aleatoria con de 4 a 8 números enteros con sumas, restas y multiplicaciones.
     * Cada número entero de la expresión matemática también se genera aleatoriamente y tiene que tener un valor entre 2 y 12, ambos incluidos.
     * 
     * @return expresion que es un String con la expresión matemática aleatoria generada
     */
    public static String generarExpresionMatematica() {
        
        // String que almacena la expresión matemática aleatoria
        String expresion = "";
        
        // Variable que almacena el número de operandos que va a tener la expresión que tiene que ser entre 4 y 8
        int numEnteros = 4 + (int)(Math.random()*(8 + 1 - 4));
        
        // Variable que almacena el número de operadores que va a tener la expresión
        int numOperadores = numEnteros - 1;
        
        /* Variable auxiliar para almacenar el valor aleatorio que se va a ir añadiendo uno a uno al String expresion
        hasta completar la expresión matemática */
        int valor;
        
        
        for(int i = 0; i < numEnteros + numOperadores; i++) {
            
            /* Los caracteres que estén en una posición par del String (teniendo en cuenta que el primer caracter es el 
            caracter 0) tienen que ser un número entero, es decir, un operando. Y los caracteres que estén en una posición
            impar del String tienen que ser los operadores (o + o - o *) */
            if(i % 2 == 0) {
               
                // Los operandos tienen que tener un valor entre 2 y 12 
                valor = 2 + (int)(Math.random()*(12 + 1 - 2));
               
                // Se añade el nuevo número entero al String expresion;
                expresion += Integer.toString(valor);
            }
            else {
               
                /* Los operadores se codifican para poder aleatorizarlos. La suma es un -1, la resta un -2 y la 
                multiplicación un -3 */
                valor = -1 * (1 + (int)(Math.random()*(3 + 1 - 1)));
               
                // Se añade o un + o un - o un * al String expresion
                switch (valor) {
                    case -1:
                        expresion += "+";
                        break;
                    case -2:
                        expresion += "-";
                        break;
                    case -3:
                        expresion += "*";
                        break;
                }
            }
        }
       
        return expresion;
    }
    
    
    /**
     * Función que evalúa una expresión matemática en forma de String y devuelve el resultado de dicha expresión
     * 
     * @param expresion que la expresión matemática en tipo String
     * @return valor que es el resultado de la expresión matemática
     */
    public static int evaluarExpresion (String expresion) {
        int valor = 0;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            Object result = engine.eval(expresion);
            valor = Integer.decode(result.toString());
            
        } catch (Exception e) {
            e.getMessage();
        }
        return valor;
    }
    
    
    /**
     * Función que comprueba si el jugador ha introducido la respuesta correcta a la expresión matemática
     * 
     * @return haAcertado que será true o false según si el jugador ha acertado o no
     */
    public static boolean comprobarRespuesta() {
        
        boolean haAcertado = false;
        
        if(respuesta == resultado) {
            haAcertado = true;
        }
        
        return haAcertado;
    }
    
    
    /**
     * Método que imprime por pantalla las puntuaciones actuales de todos los jugadores
     */
    public static void mostrarPuntuaciones(){
        
        System.out.println("\nPuntuaciones:");
        
        for(int i = 0; i < puntuaciones.length; i++) {
            
            // Se imprime el nombre y la puntuación de cada jugador
            System.out.println(jugadores[puntuaciones[i][0]] + ": " + puntuaciones[i][1]);
        }
    }
    
    
    /**
     * Función que muestra por pantalla el nombre del ganador del juego o nombres de los ganadores si hay empates
     */
    public static void mostrarGanador(){
        
        int puntuacionMax = 0;
        
        // Se comprueba cuál es la mayor puntuación de entre todos los jugadores
        for(int i = 0; i < puntuaciones.length; i++){
            
            if(puntuaciones[i][1] > puntuacionMax) {
                puntuacionMax = puntuaciones[i][1];
            }
        }
        
        // Se muestran por pantalla los nombres de los jugadores que tengan esa puntuación
        System.out.println("\nEl ganador o ganadores es...");
        for(int i = 0; i < puntuaciones.length; i++) {
            
            if(puntuaciones[i][1] == puntuacionMax) {
                System.out.println("¡" + jugadores[puntuaciones[i][0]] + "!");
            }
        }
    }
}
