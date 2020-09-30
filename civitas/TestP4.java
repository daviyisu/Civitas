
package civitas;

import java.util.ArrayList;
public class TestP4 {

    
    public static void main(String[] args) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Jugador j1 = new Jugador("Alberto");
        jugadores.add(j1);
        TituloPropiedad molinete = new TituloPropiedad("Molinete", 60, 1.2f,200,300,275);
        jugadores.get(0).comprar(molinete);
        Sorpresa sorpresa12 = new SorpresaEspeculador(100);
        
        System.out.println("Alberto tiene: ");
        System.out.println(jugadores.get(0).getNombrePropiedades().get(0));
        sorpresa12.aplicarAJugador(0, jugadores);
        System.out.println("Alberto el especulador tiene: ");
        System.out.println(jugadores.get(0).getNombrePropiedades().get(0));
        
        
    }
    
}
