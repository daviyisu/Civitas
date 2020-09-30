# encoding: utf-8
require_relative "diario.rb"
  require_relative "sorpresa.rb"
module Civitas
  

class MazoSorpresas
  def initialize
    @barajada = false
    @usadas = 0
    @sorpresas = Array.new
    @cartasEspeciales = Array.new
    @debug = false
  end
  
  public
  attr_reader :sorpresas
  def MazoSorpresas(modo)
    initialize
    debug = modo
    if(debug)
      Diario.instance.ocurre_evento("El modo debug está activado")
    end
  end
  
 
  
  
  
  def alMazo(s)
    if(!barajada)
      @sorpresas.push(s)
    end
  end
  
  def siguiente
    if(!barajada||@usadas==@sorpresas.length)
      if(!debug)
        @sorpresas.shuffle
        @usadas=0
        barajada=true
      end
    end
    @usadas+=1
    @sorpresas.push(@sorpresas[0])
    @ultimaSorpresa = @sorpresas[0]
    @sorpresas.delete_at(0)
    return @ultimaSorpresa
  end
  
  def inhabilitarCartaEspecial(sorpresa)
    for i in(0..@sorpresas.length)
      if(@sorpresas[i]==sorpresa)
        @sorpresas.delete_at(i)
        @cartasEspeciales.push(sorpresa)
        Diario.instance.ocurre_evento("La carta especial ha sido inhabilitada")
      end
    end
  end
  
  def habilitarCartaEspecial(sorpresa)
    for i in(0..@cartasEspeciales.length)
      if(@cartasEspeciales[i]==sorpresa)
        @sorpresas.push(sorpresa)
        @cartasEspeciales.delete_at(i)
        Diario.instance.ocurre_evento("La carta especial ha sido habilitada")
      end
    end
  end
  attr_accessor:debug
  attr_accessor:barajada
end
end