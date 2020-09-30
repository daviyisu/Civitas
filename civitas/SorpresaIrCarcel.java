/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa{
    private Tablero tablero;
    
    SorpresaIrCarcel(Tablero tablero){
        this.tablero = tablero;
        this.texto = "Vaya a prision";
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
       if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
}
