/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import GUI.*;
/**
 *
 * @author nachobc82
 */
public class Tablero {
    private int numcasillacarcel;
    private ArrayList<Casilla> casillas;
    private int porsalida;
    private boolean tienejuez;
    
    Tablero(int carcel){
        this.numcasillacarcel = carcel;
        this.casillas = new ArrayList<>();
        this.casillas.add(new Casilla("Salida"));
        this.porsalida = 0;
        this.tienejuez = false;
    }
    
    private boolean Correcto(){
        boolean resultado = true;
        if (casillas.size() < this.numcasillacarcel || !this.tienejuez)
            resultado = false;
        return resultado;
    }
    
    private boolean Correcto(int numCasilla){
        boolean resultado = this.Correcto();
        if (numCasilla > casillas.size())
            resultado = false;
        return resultado;
    }
    
    int getCarcel(){
        return numcasillacarcel;
    }
    
    int getPorSalida(){
        int resultado;
        resultado = this.porsalida;
        if(this.porsalida > 0)
            this.porsalida--;
        return resultado;
    }
    
    void AñadeCasilla(Casilla casilla){
        if (this.casillas.size() == this.numcasillacarcel)
            this.casillas.add(new Casilla("Carcel"));
        this.casillas.add(casilla);
        if (this.casillas.size() == this.numcasillacarcel)
            this.casillas.add(new Casilla("Carcel"));
    }
    
    void AñadeJuez(){
        if( !this.tienejuez){
            CasillaJuez casilla = new CasillaJuez(this.numcasillacarcel, "Juez");
            this.AñadeCasilla(casilla);
            this.tienejuez = true;
        } 
    }
    
    Casilla getCasilla(int numCasilla){
        if(this.Correcto(numCasilla)){
            return casillas.get(numCasilla);
        }
        else
            return null;
    }
    
    int NuevaPosicion(int actual, int tirada){
        int casilla_final = -1;
        if (this.Correcto()){
            casilla_final = (actual + tirada)%casillas.size();
            if(casilla_final != actual+tirada)
                this.porsalida++;
        }
        return casilla_final;
    }
    
    int CalcularTirada(int origen, int destino){
        int casilla_final = destino - origen;
        if(casilla_final < 0){
            casilla_final = casilla_final + casillas.size(); 
        }
        return casilla_final;
    }
}


