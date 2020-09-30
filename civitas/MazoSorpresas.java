/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author nacho
 */
public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSopresa;
    
    private void init(){
        sorpresas = new ArrayList <>();
        cartasEspeciales = new ArrayList <>();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(boolean d){
        this.init();
        this.debug = d;
        if(d)
            Diario.getInstance().ocurreEvento("Debug del mazo sorpresa activo");
    }
    
    MazoSorpresas(){
        this.init();
        this.debug = false;
    }
    
    void alMazo(Sorpresa s){
        if (!this.barajada)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente(){ 
        if(!this.barajada || usadas==sorpresas.size() && !debug){
            Collections.shuffle(sorpresas);
            this.usadas = 0;
            this.barajada = true;
        }
        
        this.usadas++;
        this.sorpresas.add(sorpresas.get(0));
        this.ultimaSopresa = this.sorpresas.get(0);
        this.sorpresas.remove(0);
        
        return this.ultimaSopresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        boolean encontrado = false;
        for( int i=0; i< this.sorpresas.size() && !encontrado; i++)
        {
            if (sorpresa == this.sorpresas.get(i))
            {
                this.sorpresas.remove(i);
                this.cartasEspeciales.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se ha inhabilitado la carta especial");
                encontrado = true;
            }
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        boolean encontrado = false;
        for (int i=0; i<this.sorpresas.size() && !encontrado; i++)
        {
            if (sorpresa == this.sorpresas.get(i))
            {
                this.sorpresas.remove(i);
                this.sorpresas.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se ha movido la sorpresa al final del mazo");
                encontrado = true;
            }
        }
    }
}
