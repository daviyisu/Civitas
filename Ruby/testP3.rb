# encoding: utf-8
module Civitas
require_relative "vista_textual.rb"
require_relative "controlador.rb"
require_relative "civitas_juego.rb"
require_relative "dado.rb"
    class TestP3
      def self.main
      vista = Vista_textual.new
      juego = CivitasJuego.new("Alonso", "Javito","Rauliyo" ,"Alberto","Adri" ,"Ali")
      Dado.instance.setDebug(true)
      controlador = Controlador.new(juego, vista)
      controlador.juega
      end
      main
    end
end