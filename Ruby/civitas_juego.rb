# encoding: utf-8
module Civitas
  require_relative "dado.rb"
  require_relative "mazo_sorpresas.rb"
  require_relative "tipo_casilla.rb"
  require_relative "tipo_sorpresa.rb"
  require_relative "operaciones_juego.rb"
  require_relative "sorpresa.rb"
  require_relative "tablero.rb"  
  require_relative "jugador.rb"
  require_relative "civitas_juego.rb"
  require_relative "casilla.rb"
  require_relative "gestor_estados.rb"
  require_relative "estados_juego.rb"

class CivitasJuego
  def initialize(*nombres) #tengo dudas con lo de gestorEstados pero para mi que está bien.
    @jugadores = Array.new
    for i in(0..nombres.size-1)
      @jugadores.push(Jugador.new(nombres[i]))
    end
    
    @gestorEstados = Gestor_estados.new
    @gestorEstados = @gestorEstados.estado_inicial
    @indiceJugadorActual = Dado.instance.quienEmpieza(@jugadores.size)
    @mazo = MazoSorpresas.new
    inicializaTablero(@mazo)
    inicializarMazoSorpresas(@tablero) 
  end
  
  public

  attr_reader :jugadores
  attr_reader :indideJugadorActual
  attr_reader :tablero
  
  def cancelarHipoteca(ip) #EL MÉTODO QUE DELEGA AÚN NO ESTÁ ACABADO!!!  
    @jugadores[@indiceJugadorActual].cancelarHipoteca(ip)
  end
  
  def comprar
        
    jugadorActual = @jugadores[@indiceJugadorActual]
    numCasillaActual = jugadorActual.numCasillaActual
    casilla = Casilla.new(@tablero.getCasillanumCasillaActual)
        
    titulo = casilla.tituloPropiedad
    res = jugadorActual.comprar(titulo)
  
  end
  
  def construirCasa(ip) #EL MÉTODO QUE DELEGA AÚN NO ESTÁ ACABADO!!!
    @jugadores[@indiceJugadorActual].construirCasa(ip)
  end
  
  def construirHotel(ip) #EL MÉTODO QUE DELEGA AÚN NO ESTÁ ACABADO!!!
    @jugadores[@indiceJugadorActual].construirHotel(ip)
  end
  
  def finalDelJuego #FUNCIONA!!!
    game_over = false
    for i in(0..@jugadores.size-1)
      if(@jugadores[i].saldo<0)
        game_over = true;
      end
    end
    return game_over
  end
    
  def getCasillaActual #FUNCIONA!!!
    return getJugadorActual.numCasillaActual
  end
  
  def getJugadorActual #FUNCIONA!!!
    return @jugadores[@indiceJugadorActual]
  end
  
  def hipotecar(ip) #EL MÉTODO QUE DELEGA AÚN NO ESTÁ ACABADO!!!
    @jugadores[@indiceJugadorActual].hipotecar(ip)
  end
  
  def infoJugadorTexto
    getJugadorActual.to_s
  end
  
  def salirCarcelPagando #FUNCIONA SEGURO!!!
    @jugadores[@indiceJugadorActual].salirCarcelPagando
  end
  
  def salirCarcelTirando #FUNCIONA SEGURO!!!
    @jugadores[@indiceJugadorActual].salirCarcelTirando
  end
  
  def siguientePaso #NO SE COMO PROBARLO PERO PINTA BIEN!!!
    jugadorActual = @jugadores[@indiceJugadorActual]
    operacion = @gestorEstados.operaciones_permitidas(jugadorActual, @estado)
    if(operacion == Operaciones_juego::PASAR_TURNO)
      pasarTurno
      siguientePasoCompletado(operacion)
    else
      if(operacion == Operaciones_juego::AVANZAR)
        avanzaJugador
        siguientePasoCompletado(operacion)
      end
    end
    return operacion
  end
  
  def siguientePasoCompletado(operacion) #NO SE COMO PROBARLO PERO PINTA BIEN!!!
   @estado = @gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual], @estado, operacion)
  end
  
  def vender(ip) #EL MÉTODO QUE DELEGA AÚN NO ESTÁ ACABADO!!!
    @jugadores[@indiceJugadorActual].vender(ip)
  end
  
  private
  
  def avanzaJugador
    jugadorActual = getJugadorActual
    posicionActual = jugadorActual.getNumCasillaActual
    tirada = Dado.instance.tirar
    posicionNueva = tablero.NuevaPosicion(posicionActual, tirada)
    casilla = tablero.getCasilla(posicionNueva)
    contabilizarPasosPorSalida(jugadorActual)
    jugadorActual.moverACasilla(posicionNueva)
    casilla.recibeJugador(indiceJugadorActual, Jugadores)
    contabilizarPasosPorSalida(jugadorActual)
  end
  
  def contabilizarPasosPorSalida(jugadorActual) #FUNCIONA!!! (CREO)
    contador = @tablero.getPorSalida
    while contador > 0
      @jugadores[jugadorActual].pasaPorSalida
      contador-=1      
    end    
  end    
  
  def inicializarMazoSorpresas(tablero) #FUNCIONA!!!    
    sorpresa1 = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, 750, "Cobras 750 Cividólares.")
    @mazo.alMazo(sorpresa1)
    sorpresa2 = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, -750, "Pagas 750 Cividólares.")
    @mazo.alMazo(sorpresa2)
    
    sorpresa3 = Sorpresa.new(TipoSorpresa::IRCASILLA, tablero, 9, "Vaya a la casilla")
    @mazo.alMazo(sorpresa3)
    sorpresa4 = Sorpresa.new(TipoSorpresa::IRCASILLA, tablero, 16, "Vaya a la casilla")
    @mazo.alMazo(sorpresa4)
    sorpresa5 = Sorpresa.new(TipoSorpresa::IRCASILLA, tablero, 15, "Vaya a la casilla juez.")
    @mazo.alMazo(sorpresa5)
    
    sorpresa6 = Sorpresa.new(TipoSorpresa::PORCASAHOTEL, 100, "Cobras por casas y hoteles.")
    @mazo.alMazo(sorpresa6)
    sorpresa7 = Sorpresa.new(TipoSorpresa::PORCASAHOTEL, -100, "Pagas por casas y hoteles.")
    @mazo.alMazo(sorpresa7)
    
    sorpresa8 = Sorpresa.new(TipoSorpresa::PORJUGADOR, 100, "Cobras 100 Cividólares por cada jugadores.")
    @mazo.alMazo(sorpresa8)
    sorpresa9 = Sorpresa.new(TipoSorpresa::PORJUGADOR, -100, "Pagas 100 Cividólares por cada jugadores.")
    @mazo.alMazo(sorpresa9)
    
    sorpresa10 = Sorpresa.new(TipoSorpresa::SALIRCARCEL, @mazo)
    @mazo.alMazo(sorpresa10)
    sorpresa11 = Sorpresa.new(TipoSorpresa::IRCARCEL, tablero)
    @mazo.alMazo(sorpresa11)
  end
  
  def inicializaTablero(mazo) #FUNCIONA!!!
    @tablero = Tablero.new(5)
    
    molinete = TituloPropiedad.new("Molinete", 60, 1.2,200,300,275)
    casilla1 = Casilla.new(TipoCasilla::CALLE, molinete)
    @tablero.añadeCasilla(casilla1)
    
    yohrm = TituloPropiedad.new("Yorhm", 60, 1.2,200,300,275)
    casilla2 = Casilla.new(TipoCasilla::CALLE, yohrm)
    @tablero.añadeCasilla(casilla2)
    
    seath = TituloPropiedad.new("Seath", 100, 1.15, 330, 500, 450)
    casilla3 = Casilla.new(TipoCasilla::CALLE, seath)
    @tablero.añadeCasilla(casilla3)
    
    mytha = TituloPropiedad.new("Mytha", 100, 1.15, 330, 550, 465)
    casilla4 = Casilla.new(TipoCasilla::CALLE, mytha)
    @tablero.añadeCasilla(casilla4)
    
    nito = TituloPropiedad.new("Nito",180, 1.2, 600, 900, 825)
    casilla5 = Casilla.new(TipoCasilla::CALLE, nito)
    @tablero.añadeCasilla(casilla5)
    
    casilla6 = Casilla.new(TipoCasilla::SORPRESA, @mazo, "Sorpresa 1")
    @tablero.añadeCasilla(casilla6)
    
    sullyvahn = TituloPropiedad.new("Sullyvahn", 180, 1.2, 600, 900, 825)
    casilla7 = Casilla.new(TipoCasilla::CALLE, sullyvahn)
    @tablero.añadeCasilla(casilla7)
    
    lothric = TituloPropiedad.new("Lothric", 200, 1.2, 660, 1000, 925)
    casilla8 = Casilla.new(TipoCasilla::CALLE, lothric)
    @tablero.añadeCasilla(casilla8)
    
    casilla9 = Casilla.new(TipoCasilla::DESCANSO, "Parking")
    @tablero.añadeCasilla(casilla9)
    
    manus = TituloPropiedad.new("Manus", 280, 1.25, 930, 1400, 1300)
    casilla10 = Casilla.new(TipoCasilla::CALLE, manus)
    @tablero.añadeCasilla(casilla10)
    
    casilla11 = Casilla.new(TipoCasilla::SORPRESA, @mazo, "Sorpresa 2")
    @tablero.añadeCasilla(casilla11)
    
    ornstein = TituloPropiedad.new("Ornstein", 300, 1.25, 1000, 1500, 1350)
    casilla12 = Casilla.new(TipoCasilla::CALLE, ornstein)
    @tablero.añadeCasilla(casilla12)
    
    smough = TituloPropiedad.new("Smough", 300, 1.25, 1000, 1500, 1350)
    casilla13 = Casilla.new(TipoCasilla::CALLE, smough)
    @tablero.añadeCasilla(smough)
    
    @tablero.añadeJuez
    
    raime = TituloPropiedad.new("Raime", 428, 1.3, 1426, 2140, 1900)
    casilla14 = Casilla.new(TipoCasilla::CALLE, raime)
    @tablero.añadeCasilla(casilla14)
    
    alonne = TituloPropiedad.new("Alonne", 600, 1.3, 1700, 2500, 2000)
    casilla15 = Casilla.new(TipoCasilla::CALLE, alonne)
    @tablero.añadeCasilla(casilla15)
    
    casilla16 = Casilla.new(TipoCasilla::SORPRESA, @mazo, "Sorpresa 3")
    @tablero.añadeCasilla(casilla16)
    
    casilla17 = Casilla.new(TipoCasilla::IMPUESTO, 1250, "Impuesto")
    @tablero.añadeCasilla(casilla17)
  end
  
  
  def pasarTurno #FUNCIONA!!!
    
    if(@indiceJugadorActual==@jugadores.size-1)
      @indiceJugadorActual = @indiceJugadorActual % 1 
    else
      @indiceJugadorActual+=1
    end
    
  end
  
  def ranking #FUNCIONA!!!
    array = Array.new
    array = @jugadores
    array.sort! {|b, a| a.saldo <=> b.saldo}
    return array
  end 
end
end