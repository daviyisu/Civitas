# encoding: utf-8
module Civitas
  require_relative "mazo_sorpresas.rb"
  require_relative "tipo_casilla.rb"
  require_relative "tipo_sorpresa.rb"
  require_relative "tablero.rb"  
  require_relative "jugador.rb"
  require_relative "diario.rb"  
  
  class Sorpresa

    attr_reader :texto
    attr_reader :tipo
    attr_reader :valor

    def initialize(tipo, *argumentos) #FUNCIONA!!!
    
      init
      @tipo = tipo
      case @tipo
      
      when TipoSorpresa::IRCARCEL
        sorpresaCarcel(tipo, argumentos[0])
      when TipoSorpresa::IRCASILLA
        sorpresaMoverACasilla(tipo, argumentos[0], argumentos[1], argumentos[2])
      when TipoSorpresa::SALIRCARCEL
        sorpresaEvitarCarcel(tipo, argumentos[0])
      else
        sorpresa(@tipo, argumentos[0], argumentos[1])
      end
    
    end
  
    def aplicarAJugador(actual, todos)  #FUNCIONA!!!
    
      case @tipo
      
      when TipoSorpresa::IRCARCEL
        aplicarAJugador_irCarcel(actual, todos)
      when TipoSorpresa::IRCASILLA
        aplicarAJugador_irACasilla(actual, todos)
      when TipoSorpresa::PAGARCOBRAR
        aplicarAJugador_pagarCobrar(actual, todos)
      when TipoSorpresa::PORCASAHOTEL
        aplicarAJugador_porCasaHotel(actual, todos)
      when TipoSorpresa::PORJUGADOR
        aplicarAJugador_porJugador(actual, todos)
      when TipoSorpresa::SALIRCARCEL
        aplicarAJugador_salirCarcel(actual, todos)
      end

    end
  
    def salirDelMazo #FUNCIONA!!!
    
      if(@tipo==TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitarCartaEspecial(self)
    
      end
    end
  
    def sorpresaCarcel(tipo, tablero)  #FUNCIONA!!!  
      init
        
      @texto = "Vaya inmediatamente a la cárcel"
      @tablero = tablero
      @tipo = tipo
            
    end
  
    def sorpresaMoverACasilla(tipo, tablero, valor, texto) #FUNCIONA!!!
      init
      @tipo = tipo
      @texto = texto+" "+valor.to_s+"."
      @valor = valor
      @tablero = tablero
    end
  
  
    def sorpresa(tipo, valor, texto)  #FUNCIONA!!!
      init
      @tipo = tipo
      @valor = valor
      @texto = texto
    end
  
    def sorpresaEvitarCarcel(tipo, mazo)  #FUNCIONA!!!
      init
      @tipo = tipo
      @mazo = mazo
      @texto = "Con esta sorpresa podrás evitar ir a la cárcel"
    end
  
    def usada #FUNCIONA!!!
      if(@tipo==TipoSorpresa::SALIRCARCEL)
        @mazo.habilitarCartaEspecial(self)
      end
    end
  
    public
  
    def jugadorCorrecto(actual, todos) #FUNCIONA!!!
      correcto = false
      if(actual>=0&&actual<todos.length)
        correcto = true
      end
      return correcto
    end
  
    def to_s #FUNCIONA!!!
      return "Tipo: #{@tipo}"+
             "#{@texto}          "
    end
  
    private
  
    def aplicarAJugador_irACasilla(actual, todos) #FUNCIONA TODO LO QUE TIENE QUE FUNCIONAR!!!
    
      if(jugadorCorrecto(actual, todos))
        
        informe(actual, todos)
        temp = todos[actual].numCasillaActual
        tirada = @tablero.calcularTirada(temp, @valor)
        todos[actual].moverACasilla(@tablero.nuevaPosicion(temp, tirada))
        @tablero.getCasilla(@tablero.nuevaPosicion(temp, tirada)).recibeJugador(actual, todos)

      end
    
    end
  
    def aplicarAJugador_irCarcel(actual, todos)  #FUNCIONA!!!
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end
  
    def aplicarAJugador_pagarCobrar(actual, todos) #FUNCIONA!!!
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor)
      end
    end
  
    def aplicarAJugador_porCasaHotel(actual, todos) #NO PUEDO REVISARLO SIN EL MÉTODO COMPRAR QUE AUN NO SE IMPLEMENTA PERO CREO QUE ESTÁ BIEN!!!
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor*(todos[actual].getCasas+todos[actual].getHoteles))
      end
    end
  
    def aplicarAJugador_porJugador(actual, todos) #FUNCIONA!!!
    
      temp = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, @valor*-1,@valor.to_s+" dineros")
      temp2 = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, @valor*(todos.length-1), (@valor*(todos.length-1)).to_s+" dineros")
      if(jugadorCorrecto(actual, todos))
        
        informe(actual, todos)
        for i in(0..todos.length-1)
          if(todos[i]!=todos[actual])
            temp.aplicarAJugador(i, todos) 
          end
        end

        temp2.aplicarAJugador(actual, todos)
      end
    end
  
    def aplicarAJugador_salirCarcel(actual, todos) #FUNCIONA!!!
      temp = Sorpresa.new(TipoSorpresa::SALIRCARCEL, @mazo)
      nadie = true
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        for i in(0..todos.length-1)
             
          if(todos[i].tieneSalvoconducto)
            nadie = false           
          end
        end
             
        if(nadie)
          todos[actual].obtenerSalvoconducto(temp)
          salirDelMazo  
        end

      end
    end
  
    def informe(actual, todos)  #FUNCIONA!!!
      Diario.instance.ocurre_evento("Se está aplicando una sorpresa en "+todos[actual].nombre )
    end
  
    def init #FUNCIONA!!!
      @valor=-1
      @tablero=nil
      @mazo=nil
    end
  end
end
