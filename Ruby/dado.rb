# encoding: utf-8
require 'singleton'
require_relative 'diario'
module Civitas
  
    
    
    class Dado
      
      include Singleton

      @@SalidaCarcel = 5

      

      def initialize
        @debug = false
        @ultimoResultado = 0
      end
    
      def tirar
        if(!@debug)
          resultado = 1 + rand(6)
        else
          resultado = 1
        end
        @ultimoResultado = resultado
        return resultado
      end
    
      def salgoDeLaCarcel
        salgo = false
        tirar
        if(ultimoResultado==5||ultimoResultado>5)
          salgo = true
        end
        return salgo
      end
    
      def quienEmpieza(n)
        numero = rand(n)
        return numero
      end
    
      def setDebug(d)
        @debug = d
        if(d==true)
          estado = "activado"
        else
          estado = "desactivado"
        end
        Diario.instance.ocurre_evento("Debug está ahora "+estado)
      end
    
      attr_reader:ultimoResultado
    end
  
end
