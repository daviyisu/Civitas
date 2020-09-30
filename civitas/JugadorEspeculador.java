package civitas;

public class JugadorEspeculador extends Jugador {

    private static int FactorEspeculador = 2;
    private int fianza;

    JugadorEspeculador(int f, Jugador player) {
        super(player);
        this.fianza = f;
        for (int i = 0; i < super.getPropiedades().size(); i++) {
            super.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }
    
    @Override
    protected int getCasasMax(){
    
        return this.getCasasMax()*JugadorEspeculador.FactorEspeculador;
    }
    
    @Override
    protected int getHotelesMax(){
        
        return this.getHotelesMax()*JugadorEspeculador.FactorEspeculador;
    }
                
                
    
    
    @Override
    boolean encarcelar(int numCasillaCarcel) {

        boolean encarcelar = false;
        if (debeSerEncarcelado()) {
            if (!tieneSalvoConducto()) {
                if (this.getSaldo() > this.fianza) {
                    encarcelar = true;
                }
            }
        }
        if (encarcelar) {
            this.moverACasilla(numCasillaCarcel);
            this.encarcelado = true;
            Diario.getInstance().ocurreEvento(this.getNombre() + " ha sido encarcelado.");
        }
        return encarcelar;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        if(this.isEncarcelado())
            return false;
        else
            return this.paga(cantidad/2);
    }
    
    
    String to_s(){
        return "Nombre: "+this.getNombre()+"\n"+
                "Saldo: "+this.getSaldo()+"\n"+
                "Casilla Actual: "+this.getNumCasillaActual()+"\n"+
                "Es un jugador especulador"+"\n";
    }

}
