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
  require_relative "titulo_propiedad.rb"
  class Casilla 
    
    attr_reader :nombre
    attr_reader :tituloPropiedad
    
    def initialize (tipo, *argumentos)  #FUNCIONA!!!
      init
      @tipo = tipo
      case @tipo
      
      when TipoCasilla::DESCANSO
        casillaDescanso(argumentos[0])
      when TipoCasilla::CALLE
        casillaCalle(argumentos[0])
      when TipoCasilla::IMPUESTO
        casillaImpuesto(argumentos[0], argumentos[1])
      when TipoCasilla::JUEZ
        casillaCarcel(argumentos[0], argumentos[1])
      when TipoCasilla::SORPRESA
        casillaSorpresa(argumentos[0], argumentos[1]) 
      else
        
      end
    end
    
    def casillaDescanso(nombre) #FUNCIONA!!!
      init
      @nombre = nombre
      @tipo = TipoCasilla::DESCANSO
    end
      
    def casillaCalle(titulo) #FUNCIONA!!!
      init
      @tituloPropiedad = titulo
      @nombre = titulo.nombre
      @importe = titulo.precioCompra
      @tipo = TipoCasilla::CALLE      
    end
      
    def casillaImpuesto(cantidad, nombre) #FUNCIONA!!!        
      init
      @tipo = TipoCasilla::IMPUESTO
      @importe = cantidad
      @nombre = nombre        
    end
      
    def casillaCarcel(numCasillaCarcel, nombre) #FUNCIONA!!!        
      init
      @tipo=TipoCasilla::JUEZ
      @nombre = nombre
      @carcel = numCasillaCarcel                
    end
      
    def casillaSorpresa(mazo,nombre) #FUNCIONA!!!        
      init
      @nombre = nombre
      @mazo = mazo
      @sorpresa = mazo.siguiente
      @tipo = TipoCasilla::SORPRESA                
    end
      
    def recibeJugador(iactual,todos) #FUNCIONA!!!
      if(@tipo == TipoCasilla::CALLE)
        recibeJugadorCalle(iactual,todos)
      else
        if(@tipo == TipoCasilla::IMPUESTO)
          recibeJugadorImpuesto(iactual,todos)
        else
          if(@tipo == TipoCasilla::JUEZ)
            recibeJugadorJuez(iactual,todos)
          else
            if(@tipo == TipoCasilla::SORPRESA)
              recibeJugadorSorpresa(iactual,todos)
            else
              informe(iactual,todos)
            end
          end
        end
      end
    end
             
    public
               
    def toString #FUNCIONA!!!          
      return "Nombre: "+@nombre.to_s+"\n"+
        "Tipo: "+@tipo.to_s+"\n"+
        "Importe"+@importe.to_s          
    end
        
    def jugadorCorrecto(iactual,todos) #FUNCIONA!!!          
      correcto = false
      if(iactual>=0&&iactual<todos.length)
        correcto = true
      end
      return correcto          
    end
      
    private
    
    def informe(iactual,todos) #FUNCIONA!!!        
      Diario.instance.ocurre_evento(todos[iactual].nombre.to_s+" ha caido en "+ nombre + "\n" + toString)      
    end
      
    def recibeJugadorImpuesto(iactual,todos) #FUNCIONA!!!        
      if(jugadorCorrecto(iactual, todos))        
        informe(iactual, todos)
        todos[iactual].pagaImpuesto(@importe)        
      end        
    end
      
    def recibeJugadorJuez(iactual,todos) #FUNCIONA!!!        
      if(jugadorCorrecto(iactual, todos))        
        informe(iactual, todos)
        todos[iactual].encarcelar(@carcel)        
      end        
    end
                
    def init #FUNCIONA!!!              
      @importe = nil
      @mazo = nil
      @sorpresa = nil
      @tipo = nil
      @tituloPropiedad = nil        
    end
  end
  
  def recibeJugadorCalle(iactual,todos)
    if ( jugadorCorrecto(iactual, todos) )
      informe(iactual, todos)
            
      jugador = todos.get(iactual);
            
      if (!@tituloPropiedad.tienePropietario())
        jugador.puedeComprarCasilla();
      else
        @tituloPropiedad.tramitarAlquiler(jugador)
      end
    end
  end
 
  def recibeJugadorSorpresa(iactual,todos)
    if ( jugadorCorrecto(iactual, todos) )
      sorpresa = @mazo.siguiente
      informe(iactual, todos)
      sorpresa.aplicarAJugador(iactual, todos)
    end 
  end
   
end



