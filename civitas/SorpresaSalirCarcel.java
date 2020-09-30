/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
public class SorpresaSalirCarcel extends Sorpresa{
    private MazoSorpresas mazo;
    
    SorpresaSalirCarcel(MazoSorpresas mazo){
        this.mazo = mazo;
        super.texto = "Esta sorpresa te permite salir de la carcel";
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        SorpresaSalirCarcel temp = new SorpresaSalirCarcel(mazo);
        boolean nadie = true;
        if(jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            for(int i=0; i<todos.size();i++){
                if(todos.get(i).tieneSalvoConducto())
                    nadie = false;
            }         
        }
             
        if(nadie){
            todos.get(actual).obtenerSalvoconducto(temp);
            this.salirDelMazo();
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
    
}
