#encoding:utf-8
require_relative "operaciones_juego.rb"
require 'io/console'
require_relative "salidas_carcel.rb"
require_relative "respuestas.rb"
require_relative "diario.rb"

module Civitas

  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
        "\n"+tab+"Elige una opción: ",
        tab+"Valor erróneo")
      return opcion
    end

    def salirCarcel  #PENDIENTE DE DEPURACIÓN!!!
      lista_opciones=["Pagando","Tirando"]
      iOpcion=menu("Elige como quiere salir de la cárcel",lista_opciones)
      return SalidasCarcel::Lista_SalidasCarcel[iOpcion]
    end
    
    def comprar #PENDIENTE DE DEPURACIÓN!!!
      lista_opciones=["No","Sí"]
      iOpcion=menu("¿Quieres comprar la propiedad?", lista_opciones)
      return Respuestas::Lista_Respuestas[iOpcion]
    end

    def gestionar #PENDIENTE DE DEPURACIÓN!!!
      lista_opciones=["Vender", "Hipotecar", "Cancelar hipoteca", "Construir casa", "Construir hotel", "Terminar"]
      @iGestion = menu("Elige el número de gestión inmobiliaria", lista_opciones)
      nombres = @juegoModel.jugadores[@juegoModel.indideJugadorActual].getNombrePropiedades
      @iPropiedad = menu("Elige la propiedad en la que realizar la acción", nombres)
    end
    endLista_Respuestas

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts operacion
    end

    def mostrarEventos #PENDIENTE DE DEPURACION!!!
      while(Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
      @juegoModel=civitas
      actualizarVista
    end

    def actualizarVista #PENDIENTE DE DEPURACIÓN
      indiceActual = @juegoModel.indideJugadorActual
      puts @juegoModel.jugadores[indiceActual].to_s
      puts @juegoModel.tablero.casillas[@juegoModel.jugadores[indiceActual].numCasillaActual].to_s
      nombres = Array.new
      nombres = @juegoModel.jugadores[indiceActual].getNombrePropiedades
      for i in(0..nombres.size-1)
        puts nombres[i]
      end
    end

    
  end

end
