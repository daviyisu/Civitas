/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {

    private TituloPropiedad propiedad;

    CasillaCalle(TituloPropiedad titulo) {
        super(titulo.getNombre());
        this.propiedad = titulo;
    }

    public TituloPropiedad getTituloPropiedad() {
        return this.propiedad;
    }

    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos) {  //PENDIENTE DE DEPURACIÓN!!!

        if (this.jugadorCorrecto(iactual, todos)) {
            super.recibeJugador(iactual, todos);
            Jugador jugador = todos.get(iactual);

            if (!this.getTituloPropiedad().tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                this.getTituloPropiedad().tramitarAlquiler(jugador);
            }
        }

    }
    
    @Override
    public String toString(){
        return super.toString() +
               "Precio: " + this.propiedad.getPrecioCompra() + "\n" +
                "Num. Casas: " + this.propiedad.getNumCasas() + "\n" +
                "Num. Hoteles: " + this.propiedad.getNumHoteles() + "\n" +
                "Precio cancelar hipoteca: " + this.propiedad.getImporteCancelarHipoteca() + "\n";
    }
}
