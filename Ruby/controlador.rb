# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  require_relative "civitas_juego.rb"
  require_relative "vista_textual.rb"
  class Controlador
    def initialize(juego,vista)
      @juego = juego
      @vista = vista
    end
    
    def juega
      @vista.setCivitasJuego(this.juego)
        
      while ( !@juego.finalDelJuego ) do
        @vista.actualizarVista         
        @vista.pausa
            
        oper = @juego.siguientePaso
        @vista.mostrarSiguienteOperacion( oper )
        if (oper != OperacionesJuego::PASAR_TURNO)
          @vista.mostrarEventos()
            
          if ( !juego.finalDelJuego )
            case oper
            when OperacionesJuego::COMPRAR
              r = @vista.comprar
              if (r == Respuestas::SI)
                @juego.comprar
                @juego.siguientePasoCompletado(oper)
              end
                       
            when OperacionesJuego::GESTIONAR
              @vista.gestionar
                        
              gestion = @vista.getGestion
              propiedad = @vista.getPropiedad
              op = OperacionInmobiliaria.new(GestionesInmobiliarias[gestion],propiedad)
                        
              if (GestionesInmobiliarias[gestion] == GestionesInmobiliarias::TERMINAR)
                @juego.siguientePasoCompletado(op)
              end
                  
            when Operaciones::SALIR_CARCEL
              s = @vista.salirCarcel
              if (s == SalidasCarcel.PAGANDO)
                @juego.salirCarcelPagando
              else
                @juego.salirCarcelTirando
              end      
            end
          end
        end
        
        if ( @juego.finalDelJuego )
          @juego.ranking
        end
      end
    end
  end
end
    
