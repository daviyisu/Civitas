

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import GUI.*;
import juegoTexto.*;
/**
 *
 * @author nacho-c
 */
public class TestP3 {
    public static void main(String[] args) {
       VistaTextual vista = new VistaTextual();
       
       ArrayList<String> jugadores = new ArrayList<>();
       jugadores.add("Stelio");
       jugadores.add("Puidgemont");
       CivitasJuego juego = new CivitasJuego(jugadores);
       
       
       Dado.getInstance().setDebug(true);
       /*Controlador controlador = new Controlador(juego,vista);
       controlador.juega();*/
    }
    
}
