package civitas;
import java.util.ArrayList;
public class SorpresaIrCasilla extends Sorpresa{
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCasilla(Tablero tablero,int valor, String texto){
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            int origen = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.CalcularTirada(origen, this.valor);
            int nueva_pos = tablero.NuevaPosicion(actual, tirada);
            todos.get(actual).moverACasilla(nueva_pos);
        }
    }
}
