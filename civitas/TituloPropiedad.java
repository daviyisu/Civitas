/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author nacho
 */
public class TituloPropiedad {
    
    private final float alquilerBase;
    private static float factorInteresesHipoteca = 1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private final String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        this.propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        if(this.hipotecado && this.esEsteElPropietario(jugador)){
            jugador.paga(this.getImporteCancelarHipoteca());
            this.hipotecado = false;
        }
        return !this.hipotecado; //si sigue hipotecado(true) no se ha quitado la hipoteca(false), en cambio si se quitado la hipoteca(true) no esta hipotecado(true)
    }
    
    
    
    int cantidadCasasHoteles(Jugador jugador){
        return this.numCasas+this.numHoteles;
    }
    
    boolean comprar(Jugador jugador){ 
        boolean result = false;
        if (!this.tienePropietario())
        {
            this.propietario = jugador;
            result = true;
            jugador.paga(this.precioCompra);
        }
        return result;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean constr = false;
        if(this.esEsteElPropietario(jugador)){
            jugador.paga(this.precioEdificar);
            this.numCasas ++;
            constr = true;
        }
        return constr;
    }
    
    boolean construirHotel(Jugador jugador){ //DEPURAR!!!
        boolean construir = false;
        if(this.esEsteElPropietario(jugador) && this.numCasas >= 4){
            jugador.paga(this.precioEdificar);
            construir = true;
            this.numHoteles++;
            this.numCasas = this.numCasas - 4;
        } 
        return construir;
    }
    
    boolean derruirCasa(int n, Jugador jugador){
        if(this.propietario == jugador && this.numCasas >= n){
            this.numCasas -= n;
            return true;
        }
        else
            return false;
    }
    
    public boolean esEsteElPropietario(Jugador jugador){
        return this.propietario.getNombre().equals(jugador.getNombre());
    }
    
    public boolean getHipotecado(){
        return this.hipotecado;
    }
    float getImporteCancelarHipoteca(){
        return this.hipotecaBase*this.factorInteresesHipoteca;
    }
    private float getImporteHipoteca(){
        return this.hipotecaBase*(1f+(numCasas*0.5f)+(numHoteles*2.5f));
    }
   public String getNombre(){
        return this.nombre;
    }
    public int getNumCasas(){
        return this.numCasas;
    }
    public int getNumHoteles(){
        return this.numHoteles;
    }
    private float getPrecioAlquiler(){
        float precio = this.alquilerBase*(1f + (this.numCasas*0.5f) + (this.numHoteles*2.5f));
        if(this.propietario != null){
            if (this.propietario.isEncarcelado() || this.hipotecado)
                precio = 0f;
        }
        return precio;
    }
    float getPrecioCompra(){
        return this.precioCompra;
    }
    float getPrecioEdificar(){
        return this.precioEdificar;
    }
    private float getPrecioVenta(){
        return this.getPrecioCompra()+(this.numCasas+5*this.numHoteles)*this.getPrecioEdificar()*this.factorRevalorizacion;
    }
    Jugador getPropietario(){
        return this.propietario;
    
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        if (!this.hipotecado && this.esEsteElPropietario(jugador)){
            jugador.recibe(this.getImporteHipoteca());
            this.hipotecado = true;
            salida = true;
        }
        return salida;
    }
    
    public boolean propietarioEncarcelado(){
        if (this.propietario.isEncarcelado())
            return true;
        else
            return false;
    }
    
   public boolean tienePropietario(){
        return (propietario != null);
    }
    
    TituloPropiedad(String nom, float ab,float fr, float hb, float pc, float pe){
        this.propietario = null;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.hipotecado = false;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.nombre = nom;
        this.precioCompra = pc;
        this.precioEdificar = pe;
    
    }
    @Override
    public String toString(){
        if(this.propietario != null){
        return  "nombre: "+this.nombre+
                "alquiler: "+this.getPrecioAlquiler()+
                "\n Propietario: "+ this.propietario.getNombre()+
                "\n nº casas: "+this.getNumCasas()+
                "\n nº hoteles: "+this.getNumHoteles()+
                "\n alquiler: "+this.getPrecioAlquiler()+
                "\n precio de compra"+this.getPrecioCompra()+
                "\n esta hipotecado?: "+this.getHipotecado()+
                "\n precio de edificar"+this.getPrecioEdificar()+
                "\n precio hipoteca: "+this.getImporteHipoteca();
        }
        else{
        return  "nombre: "+this.nombre+
                "alquiler: "+this.getPrecioAlquiler()+
                "\n nº casas: "+this.getNumCasas()+
                "\n nº hoteles: "+this.getNumHoteles()+
                "\n alquiler: "+this.getPrecioAlquiler()+
                "\n precio de compra "+this.getPrecioCompra()+
                "\n esta hipotecado?: "+this.getHipotecado()+
                "\n precio de edificar"+this.getPrecioEdificar()+
                "\n precio hipoteca: "+this.getImporteHipoteca();
   
        } 
    }
    
    void tramitarAlquiler(Jugador jugador){
        if (this.tienePropietario()){
            if(!this.esEsteElPropietario(jugador)){
                float precio = this.getPrecioAlquiler();
                jugador.pagaAlquiler(precio);
                this.propietario.recibe(precio);
            }
        }
    }
    
    boolean vender(Jugador jugador){
        if(this.esEsteElPropietario(jugador) && !hipotecado){
            propietario.recibe(this.getPrecioVenta());
            propietario = null;
            this.numCasas = 0;
            this.numHoteles = 0;
            return true;
        }
        else
            return false;
        
    }

}
