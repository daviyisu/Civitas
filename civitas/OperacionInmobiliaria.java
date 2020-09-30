package civitas;
public class OperacionInmobiliaria {
    private int numPropiedad;
   private GestionesInmobiliarias gestion;
    
    public GestionesInmobiliarias getGestion(){
        return this.gestion;
    }
    
    public int getNumPropiedad(){
        return this.numPropiedad;
    }
    
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){
        this.gestion = gest;
        this.numPropiedad = ip;
    }
}
