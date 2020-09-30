# encoding: utf-8
module Civitas
  require_relative "casilla.rb"
  require_relative "tipo_casilla.rb"
  require_relative "sorpresa.rb"
  require_relative "tipo_sorpresa.rb"
  
  class Tablero
  
    def initialize(casilla)
      if(casilla>=1)
        @numCasillaCarcel = casilla
      else
        @numCasillaCarcel = 1
      end
      @casillas = Array.new
      casilla_salida = Casilla.new(TipoCasilla::DESCANSO, "Salida")
      @casillas.push(casilla_salida)
      @porSalida = 0
      @tieneJuez = false
  
    end
    
    
  
    private
    
    def correcto
      ok = false
      if(@casillas.length>@numCasillaCarcel&&@tieneJuez)
        ok = true
      end
      
      return ok
  
    end
    
    def correcto_casilla(numCasilla)
      ok = false
      if(correcto()&&numCasilla>0&&numCasilla<@casillas.length)
        ok = true
      end
    end
    
    public
    
    attr_accessor :numCasillaCarcel
    
    
    def getPorSalida
      resultado = @porSalida
      if(@porSalida > 0)
        @porSalida = @porSalida -1
      end
      return resultado
    end
    
    def compruebaCarcel
      if(@casillas.length==@numCasillaCarcel)
        casilla_carcel = Casilla.new(TipoCasilla::JUEZ, @numCasillaCarcel, "Cárcel")
        @casillas.push(casilla_carcel)
      end
    end
    
    def añadeCasilla(casilla)
      compruebaCarcel
      @casillas.push(casilla)
      compruebaCarcel
    end
    
    def añadeJuez
      if(!@tieneJuez)
        casilla_juez = Casilla.new(TipoCasilla::JUEZ, @numCasillaCarcel, "Juez")
        @casillas.push(casilla_juez)
        @tieneJuez=true
      end
    end
    
    def getCasilla(numCasilla)
      if(correcto(numCasilla))
        respuesta = @casillas[numCasilla]
      else
        respuesta = nil
      end
      return respuesta
    end
    
    def nuevaPosicion(actual, tirada)
      if(!correcto)
        respuesta = -1
      else
        nuevaPosicion = actual + tirada
      end
      if(nuevaPosicion > @casillas.length)
        nuevaPosicion = nuevaPosicion % @casillas.length - 1
        @porSalida = @porSalida + 1
      end
      return nuevaPosicion
    end
    
    
    
    def tamaño
      return @casillas.length
    end
    
    def calcularTirada(origen, destino)
      tirada = destino - origen
      if(tirada < 0)
        tirada += @casillas.length
      end
      return tirada
    end
    
    
  end
end

