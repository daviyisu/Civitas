/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author david
 */
public class CasillaImpuesto extends Casilla {

    float importe;

    CasillaImpuesto(float cantidad, String nombre) {

        super(nombre);
        this.importe = cantidad;
    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {  //PENDIENTE DE DEPURACIÓN!!!
        super.recibeJugador(iactual, todos);
        if (this.jugadorCorrecto(iactual, todos)) {
            Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " va a pagar sus impuestos");
            boolean paga_impuesto;
            paga_impuesto = todos.get(iactual).pagaImpuesto(importe);
        }
    }
}
