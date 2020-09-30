
package civitas;
import java.util.ArrayList;

public class CasillaJuez extends Casilla {
    int numCasillaCarcel;
    CasillaJuez(int numCasilla, String nombre){
        super(nombre);
        this.numCasillaCarcel = numCasilla;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){  //PENDIENTE DE DEPURACIÓN!!!
        if (this.jugadorCorrecto(iactual, todos)) {
            super.informe(iactual, todos);
            Diario.getInstance().ocurreEvento("Se va a encarcelar a "+todos.get(iactual).getNombre());
            boolean encarcelar;
            encarcelar = todos.get(iactual).encarcelar(this.numCasillaCarcel);
        }
    }
}
