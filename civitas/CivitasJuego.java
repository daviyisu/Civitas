
package civitas;
import java.util.ArrayList;
import GUI.Dado;

public class CivitasJuego {
    private int indiceJugadorActual;
    private EstadosJuego estado;
    private ArrayList<Jugador> Jugadores;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private GestorEstados gestorEstados;
    
    private void avanzaJugador(){
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = this.tablero.NuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, Jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int ip){    
        Jugador jugadorActual = this.getJugadorActual();
        return jugadorActual.cancelarHipoteca(ip);
    }
    
    public CivitasJuego(ArrayList<String> nombres){
        Jugadores = new ArrayList<>();
        for(int i=0; i<nombres.size(); i++){
            Jugador aux = new Jugador(nombres.get(i));
            Jugadores.add(aux);
        }
        
        gestorEstados = new GestorEstados();
        this.estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = Dado.getInstance().quienEmpieza(Jugadores.size());
        
        mazo = new MazoSorpresas();
        tablero = new Tablero(5);
        
        this.inicializarMazoSorpresas(tablero);
        this.inicializarTablero(mazo);
    }
    
    public boolean comprar(){
        boolean res;
        
        Jugador jugadorActual = this.Jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        CasillaCalle casilla = (CasillaCalle) this.tablero.getCasilla(numCasillaActual);
        
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        res = jugadorActual.comprar(titulo);
        
        return res;
    }
    
    public boolean construirCasa(int ip){   
        return this.getJugadorActual().getPropiedades().get(ip).construirCasa(this.getJugadorActual());   
    }
    
    public boolean construirHotel(int ip){    
        return this.getJugadorActual().getPropiedades().get(ip).construirHotel(this.getJugadorActual());   
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual) {
        if (tablero.getPorSalida() > 0){
            jugadorActual.recibe(Jugador.PasoPorSalida);
        }
    }
    
    public boolean finalDelJuego()
    {
        boolean finalizar = false;
        for(int i=0; i<this.Jugadores.size() ;i++){
            if(Jugadores.get(i).enBancarrota())
                finalizar = true;
        }
        return finalizar;
    }
    
    public Casilla getCasillaActual()
    {
        int posicion_casilla = this.getJugadorActual().getNumCasillaActual();
        return tablero.getCasilla(posicion_casilla);    
    }
    
    public Jugador getJugadorActual()
    {
        return this.Jugadores.get(this.indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){   
        return this.getJugadorActual().getPropiedades().get(ip).hipotecar(this.getJugadorActual());    
    }
    
    public String infoJugadorTexto(){
        return this.getJugadorActual().toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        Sorpresa sorpresa1 = new SorpresaPagarCobrar(750, "Cobras 750 Cividólares.");
        this.mazo.alMazo(sorpresa1);
        Sorpresa sorpresa2 = new SorpresaPagarCobrar(-750, "Pagas 750 Cividólares.");
        this.mazo.alMazo(sorpresa2);
    
        Sorpresa sorpresa3 = new SorpresaIrCasilla(tablero, 9, "Vaya a la casilla");
        this.mazo.alMazo(sorpresa3);
        Sorpresa sorpresa4 = new SorpresaIrCasilla(tablero, 16, "Vaya a la casilla");
        this.mazo.alMazo(sorpresa4);
        Sorpresa sorpresa5 = new SorpresaIrCasilla(tablero, 15, "Vaya a la casilla juez.");
        this.mazo.alMazo(sorpresa5);
    
        Sorpresa sorpresa6 = new SorpresaPorCasaHotel(100, "Cobras por casas y hoteles.");
        this.mazo.alMazo(sorpresa6);
        Sorpresa sorpresa7 = new SorpresaPorCasaHotel(-100, "Pagas por casas y hoteles.");
        this.mazo.alMazo(sorpresa7);

        Sorpresa sorpresa8 = new SorpresaPorJugador(100, "Cobras 100 Cividólares por cada jugadores.");
        this.mazo.alMazo(sorpresa8);
        Sorpresa sorpresa9 = new SorpresaPorJugador(-100, "Pagas 100 Cividólares por cada jugadores.");
        mazo.alMazo(sorpresa9);

        Sorpresa sorpresa10 = new SorpresaSalirCarcel(mazo);
        mazo.alMazo(sorpresa10);
        Sorpresa sorpresa11 = new SorpresaIrCarcel(tablero);
        mazo.alMazo(sorpresa11);
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
    
        TituloPropiedad molinete = new TituloPropiedad("Molinete", 60, 1.2f,200,300,275);
        CasillaCalle calle1 = new CasillaCalle(molinete);
        Casilla casilla1 = calle1;
        
        tablero.AñadeCasilla(casilla1);
        

        TituloPropiedad yohrm = new TituloPropiedad("Yorhm", 60, 1.2f,200,300,275);
        CasillaCalle calle2 = new CasillaCalle(yohrm);
        Casilla casilla2 = calle2;
        tablero.AñadeCasilla(casilla2);

        TituloPropiedad seath = new TituloPropiedad("Seath", 100, 1.15f, 330, 500, 450);
        CasillaCalle calle3 = new CasillaCalle(seath);
        Casilla casilla3 = calle3;
        tablero.AñadeCasilla(casilla3);

        TituloPropiedad mytha = new TituloPropiedad("Mytha", 100, 1.15f, 330, 550, 465);
        CasillaCalle calle4 = new CasillaCalle(mytha);
        Casilla casilla4 = calle4;
        tablero.AñadeCasilla(casilla4);

        TituloPropiedad nito = new TituloPropiedad("Nito",180, 1.2f, 600, 900, 825);
        CasillaCalle calle5 = new CasillaCalle(nito);
        Casilla casilla5 = calle5;
        tablero.AñadeCasilla(casilla5);

        
        Casilla casilla6 = new CasillaSorpresa("Sorpresa 1",mazo);
        tablero.AñadeCasilla(casilla6);

        TituloPropiedad sullyvahn = new TituloPropiedad("Sullyvahn", 180, 1.2f, 600, 900, 825);
        CasillaCalle calle6 = new CasillaCalle(sullyvahn);
        Casilla casilla7 = calle6;
        tablero.AñadeCasilla(casilla7);

        TituloPropiedad lothric = new TituloPropiedad("Lothric", 200, 1.2f, 660, 1000, 925);
        CasillaCalle calle7 = new CasillaCalle(lothric);
        Casilla casilla8 = calle7;
        tablero.AñadeCasilla(casilla8);

        Casilla casilla9 = new Casilla("Parking");
        tablero.AñadeCasilla(casilla9);

        TituloPropiedad manus = new TituloPropiedad("Manus", 280, 1.25f, 930, 1400, 1300);
        CasillaCalle calle9 = new CasillaCalle(manus);
        Casilla casilla10 = calle9;
        tablero.AñadeCasilla(casilla10);

        Casilla casilla11 = new CasillaSorpresa("Sorpresa 2", mazo);
        tablero.AñadeCasilla(casilla11);

        TituloPropiedad ornstein = new TituloPropiedad("Ornstein", 300, 1.25f, 1000, 1500, 1350);
        CasillaCalle calle10 = new CasillaCalle(ornstein);
        Casilla casilla12 = calle10;
        tablero.AñadeCasilla(casilla12);

        TituloPropiedad smough = new TituloPropiedad("Smough", 300, 1.25f, 1000, 1500, 1350);
        CasillaCalle calle11 = new CasillaCalle(smough);
        Casilla casilla13 = calle11;
        tablero.AñadeCasilla(casilla13);

        tablero.AñadeJuez();

        TituloPropiedad raime = new TituloPropiedad("Raime", 428, 1.3f, 1426, 2140, 1900);
        CasillaCalle calle12 = new CasillaCalle(raime);
        Casilla casilla14 = calle12;
        tablero.AñadeCasilla(casilla14);

        TituloPropiedad alonne = new TituloPropiedad("Alonne", 600, 1.3f, 1700, 2500, 2000);
        CasillaCalle calle13 = new CasillaCalle(alonne);
        Casilla casilla15 = calle13;
        tablero.AñadeCasilla(casilla15);

        Casilla casilla16 = new CasillaSorpresa("Sorpresa 3", mazo);
        tablero.AñadeCasilla(casilla16);

        Casilla casilla17 = new CasillaImpuesto(1250, "Impuesto");
        tablero.AñadeCasilla(casilla17);
    }
    
    private void pasarTurno(){
        this.indiceJugadorActual++;
        if(this.indiceJugadorActual == this.Jugadores.size())
            this.indiceJugadorActual = 0;
    }
    
    public ArrayList<Jugador> ranking(){    
        ArrayList<Jugador> ranking = new ArrayList<>();
        for (int i=0; i<this.Jugadores.size(); i++){
            Jugador aux = this.Jugadores.get(i);
            for ( int j=i ; j <this.Jugadores.size(); j++){
                if ( aux.getSaldo() > Jugadores.get(j).getSaldo())
                    aux = Jugadores.get(j);
            }
            ranking.add(aux);
        }
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return this.Jugadores.get(this.indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando() {
        return this.Jugadores.get(this.indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){    
        Jugador jugadorActual = this.getJugadorActual();
        OperacionesJuego operacion = this.gestorEstados.operacionesPermitidas(jugadorActual, this.estado);
        if(operacion == OperacionesJuego.PASAR_TURNO)
        {        
            this.pasarTurno();
            this.siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR)
        {        
            this.avanzaJugador();
            this.siguientePasoCompletado(operacion);
        }
        return operacion;   
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion)
    {
        this.estado = this.gestorEstados.siguienteEstado(this.getJugadorActual(),this.estado,operacion);
    }
    
    public boolean vender(int ip){
        return this.getJugadorActual().vender(ip);    
    }
}
