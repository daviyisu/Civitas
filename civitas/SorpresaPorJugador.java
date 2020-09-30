/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int valor, String texto){
        this.texto = texto;
        this.valor = valor;
    } 
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if ( super.jugadorCorrecto(actual, todos) ){
            super.informe(actual, todos);
            Sorpresa s = new SorpresaPagarCobrar(-1*this.valor,texto);
            for(int i=0; i<todos.size(); i++){
                if( todos.get(actual) != todos.get(i) ){
                    s.aplicarAJugador(i, todos);
                }
            }
            Sorpresa next = new SorpresaPagarCobrar(this.valor*todos.size()-1,texto);
            next.aplicarAJugador(actual, todos);
        }
    }
}
