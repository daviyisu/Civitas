package civitas;
import java.util.ArrayList;
import GUI.Dado;
public class Jugador implements Comparable<Jugador>{
    static protected int CasasMax = 4;
    static protected int CasasPorHotel = 4;
    protected boolean encarcelado;
    static protected int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    static protected float PasoPorSalida = 1000;
    static protected float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    static private float SaldoInicial = 7500;
    private SorpresaSalirCarcel salvoConducto = null;
    private ArrayList<TituloPropiedad> propiedades ;
    
    boolean cancelarHipoteca(int ip){
        boolean result = false;
        if(this.isEncarcelado()){
        
            return result;
        }
        else if(this.existeLaPropiedad(ip)){
        
            TituloPropiedad propiedad = this.propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = this.puedoGastar(cantidad);
            if(puedoGastar){
            
                result = propiedad.cancelarHipoteca(this);
                if(result){
                
                    Diario.getInstance().ocurreEvento("El jugador "+this.getNombre()+" cancela la hipoteca de la propiedad "+ip);
                }
            }
        }
        return result;
    }
    
    TituloPropiedad getPropiedad(int ip){
    
        return this.propiedades.get(ip);
    }
    
    int getTamanioPropiedades(){
    
        return this.propiedades.size();
    }
    
    int cantidadCasasHoletes(){
        int cantidad = 0;
        for (TituloPropiedad propiedad : propiedades) {
            cantidad = cantidad + propiedad.cantidadCasasHoteles(this);
        }
        return cantidad;
    }
    
    @Override
    public int compareTo(Jugador otro){
        return Float.compare(saldo,otro.getSaldo());
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        if (this.encarcelado)
            return result;
        if (this.puedeComprar)
        {
            float precio = titulo.getPrecioCompra();
            if (this.puedoGastar(precio))
            {
                result = titulo.comprar(this);
                if(result)
                {
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador "+this.nombre+" compra la propiedad "+titulo.getNombre());
                }
                this.puedeComprar = false;
            }
        }
        return result;
    }
    
    boolean construirCasa(int ip){ //DEPURAR!!!
        boolean result = false;
        boolean puedoEdificarCasa = false;
        if(this.isEncarcelado()){
            return result;
        }
        else{
            boolean existe = this.existeLaPropiedad(ip);
            if(existe){
            
                TituloPropiedad propiedad = this.propiedades.get(ip);
                puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(this.puedoGastar(precio)&&propiedad.getNumCasas()<this.getCasasMax())
                {                
                    puedoEdificarCasa = true;                
                }
                if(puedoEdificarCasa)
                {                
                    result = propiedad.construirCasa(this);
                    if(result)
                    {                    
                        Diario.getInstance().ocurreEvento("El jugador "+this.getNombre()+" construye casa en la propiedad "+ip);                    
                    }                    
                }
            
            }
        }
        return result;
    }
    
    boolean construirHotel(int ip){  //PENDIENTE DE DEPURACIÓN!!!
        boolean result = false;
        if(this.isEncarcelado()){
        
            return result;
        }
        else if(this.existeLaPropiedad(ip)){
        
            TituloPropiedad propiedad = this.propiedades.get(ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            if(puedoEdificarHotel){
            
                result = propiedad.construirHotel(this);
                int casasPorHotel = this.getCasasPorHotel();
                propiedad.derruirCasa(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador "+this.getNombre()+" construye hotel en la propiedad "+ip);
            }
        }
        return result;
    }
    
    protected boolean debeSerEncarcelado(){
        if(this.encarcelado)
            return false;
        else{
            if(this.tieneSalvoConducto()){
                this.perderSalvoConducto();
                Diario.getInstance().ocurreEvento(nombre+" usa la carta para evitar la carcel");
                return false;
            }
            else return true;
        }
    }
    
    boolean enBancarrota(){
        return this.saldo<0;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(this.debeSerEncarcelado()){
            this.moverACasilla(numCasillaCarcel);
            this.encarcelado = true;
            Diario.getInstance().ocurreEvento(nombre + " es encarcelado");
        }
        return this.encarcelado;
    }
    
    private boolean existeLaPropiedad(int ip){
        return ip<propiedades.size();
    }
    
    protected int getCasasMax(){
        return Jugador.CasasMax;
    }    
    int getCasasPorHotel(){
        return Jugador.CasasPorHotel;
    }    
    protected int getHotelesMax(){
        return Jugador.HotelesMax;
    }
    public String getNombre(){
        return this.nombre;
    }    
    int getNumCasillaActual(){
        return this.numCasillaActual;
    }    
    private float getPrecioLibertad(){
        return Jugador.PrecioLibertad;
    }    
    private float getPremioPasoSalida(){
        return Jugador.PasoPorSalida;
    } 
    public ArrayList<TituloPropiedad> getPropiedades(){
       return propiedades; 
    }
    boolean getPuedeComprar(){
        return this.puedeComprar;
    }  
    public float getSaldo(){
        return this.saldo;
    }
    
    boolean hipotecar(int ip){
        boolean result = false;
        
        if (this.encarcelado)
            return result;
        
        if (this.existeLaPropiedad(ip)){
            TituloPropiedad propiedad = this.propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        
        return result;
    }
    
   public boolean isEncarcelado(){
        return this.encarcelado;
    }
    
    Jugador(String nombre){
        this.encarcelado = false;
        this.nombre = nombre;
        this.propiedades = new ArrayList<TituloPropiedad>();
        this.numCasillaActual = 0;
        this.puedeComprar = true;
        this.saldo = Jugador.SaldoInicial;
    }
    protected Jugador(Jugador otro){
        this.encarcelado = otro.isEncarcelado();
        this.nombre = otro.getNombre();
        this.numCasillaActual = otro.getNumCasillaActual();
        this.propiedades = otro.getPropiedades();
        this.puedeComprar = otro.getPuedeComprar();
        this.saldo = otro.getSaldo();
    }
    
    boolean modificarSaldo(float cantidad){
        this.saldo = saldo + cantidad;
        Diario.getInstance().ocurreEvento("El jugador "+nombre+" ha variado su saldo a "+saldo);
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        if(this.isEncarcelado())
            return false;
        else{
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" se ha desplazado a la casilla "+numCasilla);
            return true;
        }
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        if (!this.isEncarcelado()) {
            this.salvoConducto = sorpresa;
            return true;
        } else
            return false;
    }
    
    boolean paga(float cantidad){
        return this.modificarSaldo((-1)*cantidad);
    }
    boolean pagaAlquiler(float cantidad){
        if (this.isEncarcelado()){
            return false;
        }
        else{
            return this.paga(cantidad);
        }
    }
    boolean pagaImpuesto(float cantidad){
        if (this.isEncarcelado()){
            return false;
        }
        else{
            return this.paga(cantidad);
        }
    }
    
    boolean pasaPorSalida(){
        this.modificarSaldo(Jugador.PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador "+nombre+" ha pasado por salida");
        return true;
    }
    
    private void perderSalvoConducto(){
        this.salvoConducto.usada();
        this.salvoConducto = null;
    }
    
    boolean puedeComprarCasilla(){
        this.puedeComprar = !this.isEncarcelado();
        return this.puedeComprar;
    }
    private boolean puedeSalirCarcelPagando(){
        return this.saldo>Jugador.PrecioLibertad;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puedoEdificarCasa = false;
        float precio = propiedad.getPrecioEdificar();
        if(this.puedoGastar(precio)){
        
            if(propiedad.getNumCasas()<this.getCasasMax()){
            
                if(propiedad.getNumCasas()>=this.getCasasPorHotel()){
                
                    puedoEdificarCasa = true;
                
                }
            
            }
        
        }
        return puedoEdificarCasa;
    }
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){ //DEPURAR!!!
        boolean puedoEdificarHotel = false;
        float precio = propiedad.getPrecioEdificar();
        if(this.puedoGastar(precio))
        {        
            if(propiedad.getNumHoteles()<this.getHotelesMax())
            {            
                if(propiedad.getNumCasas()>=this.getCasasPorHotel())                               
                    puedoEdificarHotel = true;                                     
            }        
        }
        return puedoEdificarHotel;
    }

    public ArrayList<String> getNombrePropiedades() {

      
        ArrayList<String> nombres;
        nombres = new ArrayList<>();
        for(int i=0; i<propiedades.size(); i++){
            String s = this.propiedades.get(i).getNombre()+" "+this.propiedades.get(i).getNumCasas();
            nombres.add(i,this.propiedades.get(i).getNombre());
            
        }
        return nombres;
    }

    private boolean puedoGastar(float precio){
        if(this.isEncarcelado())
            return false;
        else{
            return saldo >= precio;
        }
    }
    
    boolean recibe(float cantidad){
        if(this.isEncarcelado())
            return false;
        else{
            return this.modificarSaldo(cantidad);
        }
    }
    
    boolean salirCarcelPagando(){
        if(this.puedeSalirCarcelPagando() && this.encarcelado){
            this.paga(Jugador.PasoPorSalida);
            this.encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" sale de prision");
            return true;
        }
        else
            return false;
    }
    boolean salirCarcelTirando(){
        this.encarcelado = Dado.getInstance().salgoDeLaCarcel();
        if (this.encarcelado)
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" sale de prision");
        return this.encarcelado;
    }
    
    boolean tieneAlgoQueGestionar(){
        return propiedades.size() > 0;
    }
    
    boolean tieneSalvoConducto(){//funciona
        boolean tiene = true;
        if (this.salvoConducto == null)
            tiene = false;
        return tiene;
    }
    
    @Override
    public String toString(){ //funciona
        return "Jugador: "+this.nombre+
               "\nse encuentra encarcelado?: "+this.encarcelado+
               "\ncon saldo "+this.saldo;
    }
    boolean vender(int ip){
        if ( this.isEncarcelado() )
            return false;
        else{
            if(this.existeLaPropiedad(ip)){
                Diario.getInstance().ocurreEvento("Vendida la propiedad "+propiedades.get(ip).getNombre());
                propiedades.get(ip).vender(this);
                propiedades.remove(ip);
                return true;
            }
            return false;
        }
    }
}
