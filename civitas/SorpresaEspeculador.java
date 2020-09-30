/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

public class SorpresaEspeculador extends Sorpresa {
    
    int fianza;
    SorpresaEspeculador(int f){
        fianza = f;
        this.texto = "Vas a ser convertido en un Jugador Especulador";
        
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            super.informe(actual, todos);
            JugadorEspeculador jugador = new JugadorEspeculador(fianza,todos.get(actual));
            todos.set(actual, jugador);
        }
    }
}
