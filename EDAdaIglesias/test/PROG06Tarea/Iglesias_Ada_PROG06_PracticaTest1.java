/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROG06Tarea;

import static PROG06Tarea.Iglesias_Ada_PROG06_Practica.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alumno
 */
public class Iglesias_Ada_PROG06_PracticaTest1 {

    /**
     * Test of esRepetido method, of class Iglesias_Ada_PROG06_Practica.
     */
    @Test
    public void testEsRepetido() {
        int i = 0;
        String[] jugadores2 = {"Juan"};
        boolean result = Iglesias_Ada_PROG06_Practica.esRepetido(i, jugadores2);
        assertFalse(result);
        
        
        i = 5;
        String[] jugadores3 = {"Juan", "Ana", "Jose", "Sara", "Soraya", "Juan"};
        result = Iglesias_Ada_PROG06_Practica.esRepetido(i, jugadores3);
        assertTrue(result);
    }



    /**
     * Test of evaluarExpresion method, of class Iglesias_Ada_PROG06_Practica.
     */
    @Test
    public void testEvaluarExpresion() {
        String expresion = "2 + 2 - 3 + 2 * 2 + 10";
        int expResult = 15;
        int result = Iglesias_Ada_PROG06_Practica.evaluarExpresion(expresion);
        assertEquals(expResult, result);
        
        expresion = "2 * 2 * 2 + 8 - 10 - 5 * 3";
        expResult = -9;
        result = Iglesias_Ada_PROG06_Practica.evaluarExpresion(expresion);
        assertEquals(expResult, result);
    }

    
    
    /**
     * Test of comprobarRespuesta method, of class Iglesias_Ada_PROG06_Practica.
     */
    @Test
    public void testComprobarRespuesta() {
        int respuesta = 15;
        int resultado = 15;
        boolean result = Iglesias_Ada_PROG06_Practica.comprobarRespuesta(respuesta, resultado);
        assertTrue(result);
        
        respuesta = 15;
        resultado = 10;
        result = Iglesias_Ada_PROG06_Practica.comprobarRespuesta(respuesta, resultado);
        assertFalse(result);
    }
}
