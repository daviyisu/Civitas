/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa{
    private int valor;
    
    SorpresaPagarCobrar(int valor, String texto){//resto sorpresas
        this.texto = texto;
        this.valor = valor;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if (super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
}
