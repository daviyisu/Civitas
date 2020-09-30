# encoding: utf-8
module Civitas
  class OperacionInmobiliaria
    attr_reader :numPropiedad
    attr_reader :gestion
    
    def initialize(gest,ip)
      @numPropiedad = ip
      @gestion = gest
    end
  end
end
