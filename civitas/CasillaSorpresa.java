/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {

    private Sorpresa sorpresa;
    private MazoSorpresas mazo;

    CasillaSorpresa(String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.sorpresa = mazo.siguiente();
        this.mazo = mazo;
    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {  //PENDIENTE DE DEPURACIÓN!!!
        super.recibeJugador(iactual, todos);
        if (this.jugadorCorrecto(iactual, todos)) {
            Sorpresa sorpresa = this.mazo.siguiente();
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }

}
