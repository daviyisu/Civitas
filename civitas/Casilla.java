
package civitas;
import java.util.ArrayList;
public class Casilla {
    private String nombre;
          
    Casilla (String nombre_dado){
        this.nombre = nombre_dado;
    }
   
    
    public String GetNombre() {
        return nombre;
    }
   
    
    protected void informe(int iactual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador "+todos.get(iactual).getNombre()+" ha caido en la casilla:"+this.toString());
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){ 
        return iactual>= 0 && iactual<todos.size();
    }
   
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){  
            this.informe(iactual, todos);   
    }
    
    
    
    
    
    @Override
    public String toString(){        
        return " "+this.nombre+"\n";
    }
}

