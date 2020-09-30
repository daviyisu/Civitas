/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

abstract public class Sorpresa {
    protected String texto;
    
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            Diario.getInstance().ocurreEvento(texto);
        }
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return actual <= 4 && actual>=0 && actual<todos.size();
    }
    
    @Override
    public String toString(){
        return this.texto;
    }
}
