/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.util.ArrayList;
import civitas.*;
/**
 *
 * @author nacho-c
 */
public class TestP5 {

    public static void main(String[] args) {
        CivitasView prueba = new CivitasView();
        Dado.createInstance(prueba);
        Dado.getInstance().setDebug(true);
        CapturaNombres captura = new CapturaNombres(prueba,true);
        ArrayList<String> nombres = new ArrayList<>();
        nombres = captura.getNombres();
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(juego, prueba);
        prueba.setCivitasJuego(juego);
        controlador.juega();
    }
    
}
