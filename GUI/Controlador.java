
package GUI;
import civitas.*;
public class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    public Controlador(CivitasJuego juego, CivitasView vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    public void juega(){
        this.vista.setCivitasJuego(this.juego);
        
        while ( !this.juego.finalDelJuego() ){
            vista.actualizarVista();           
            
            OperacionesJuego oper = this.juego.siguientePaso();
            vista.mostrarSiguienteOperacion( oper );
            if (oper != OperacionesJuego.PASAR_TURNO)
                vista.mostrarEventos();
            
            if ( !juego.finalDelJuego() ){
                switch (oper){
                    
                    case COMPRAR:
                      
                            Respuestas r = vista.comprar();
                       if (r == Respuestas.SI)
                           this.juego.comprar();
                       this.juego.siguientePasoCompletado(oper);
                       
                        
                       break;
                    
                    case GESTIONAR:
                        vista.gestionar();
                        
                        int igestion = vista.getGestion();
                        int propiedad = vista.getPropiedad();
                        OperacionInmobiliaria op = new OperacionInmobiliaria(GestionesInmobiliarias.values()[igestion], propiedad);
                        switch (op.getGestion()){
                            case VENDER:
                                this.juego.vender(op.getNumPropiedad());
                                break;
                            case HIPOTECAR:
                                this.juego.hipotecar(op.getNumPropiedad());
                                break;
                            case CANCELAR_HIPOTECA:
                                this.juego.cancelarHipoteca(op.getNumPropiedad());
                                break;
                            case CONSTRUIR_CASA:
                                this.juego.construirCasa(op.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                this.juego.construirHotel(op.getNumPropiedad());
                                break;
                            default:
                                this.juego.siguientePasoCompletado(oper);
                                break;
                        }
                        vista.actualizarVista();
                        break;
                        
                    case SALIR_CARCEL:
                        SalidasCarcel s = vista.salirCarcel();
                        if (s == SalidasCarcel.PAGANDO)
                            this.juego.salirCarcelPagando();
                        else
                            this.juego.salirCarcelTirando();
                        
                        
                    
                        
                juego.siguientePasoCompletado(oper);
                vista.actualizarVista();
                break;
                }  
                
            }
        }
        
        if (this.juego.finalDelJuego())
            this.juego.ranking();
    }
    
    /*public void juega(){
        this.vista.setCivitasJuego(this.juego);
        
        while ( !this.juego.finalDelJuego() ){
            vista.actualizarVista();           
            
            OperacionesJuego oper = this.juego.siguientePaso();
            vista.mostrarSiguienteOperacion( oper );
            if (oper != OperacionesJuego.PASAR_TURNO)
                vista.mostrarEventos();
            
            if ( !juego.finalDelJuego() ){
                switch (oper){
                    
                    case COMPRAR:
                      
                            Respuestas r = vista.comprar();
                       if (r == Respuestas.SI)
                           this.juego.comprar();
                       this.juego.siguientePasoCompletado(oper);
                       
                        
                       break;
                    
                    case GESTIONAR:
                        vista.gestionar();
                        
                        int gestion =  vista.gestionarD.getGestion();
                        int propiedad = vista.gestionarD.getPropiedad();
                        OperacionInmobiliaria op = new OperacionInmobiliaria(GestionesInmobiliarias.values()[gestion],propiedad);
                        switch (GestionesInmobiliarias.values()[gestion]){
                            case VENDER:
                                this.juego.vender(op.getNumPropiedad());
                                break;
                            case HIPOTECAR:
                                this.juego.hipotecar(op.getNumPropiedad());
                                break;
                            case CANCELAR_HIPOTECA:
                                this.juego.cancelarHipoteca(op.getNumPropiedad());
                            case CONSTRUIR_CASA:
                                this.juego.construirCasa(op.getNumPropiedad());
                                
                            case CONSTRUIR_HOTEL:
                                this.juego.construirHotel(op.getNumPropiedad());
                                
                            default:
                                this.juego.siguientePasoCompletado(oper);
                                break;
                        }
                        break;
                        
                    case SALIR_CARCEL:
                        SalidasCarcel s = vista.salirCarcel();
                        if (s == SalidasCarcel.PAGANDO)
                            this.juego.salirCarcelPagando();
                        else
                            this.juego.salirCarcelTirando();
                        
                        break;
                    
                }   
            }
        }
        
        if (this.juego.finalDelJuego())
            this.juego.ranking();
    }*/
}
