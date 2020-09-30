/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa{
    private int valor;
    
    SorpresaPorCasaHotel(int valor, String texto){
        this.texto = texto;
        this.valor = valor;
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if (super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            todos.get(actual).modificarSaldo( valor * todos.get(actual).getCasasPorHotel() );
        }
    }
}
