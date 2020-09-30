# encoding: utf-8
require_relative "tipo_sorpresa.rb"
require_relative "sorpresa.rb"
require_relative "dado.rb"
require_relative "titulo_propiedad.rb"

module Civitas
  class Jugador
    
    attr_reader :nombre
    attr_reader :numCasillaActual
    attr_reader :propiedades
    attr_reader :puedeComprar
    attr_reader :saldo
    attr_reader :encarcelado
  
    @@SaldoInicial = 7500
    @@PasoPorSalida = 1000
    @@PrecioLibertad = 200
    @@CasasMax=4
    @@CasasPorHotel=4
    @@HotelesMax = 4
  
    def initialize(nombre_dado)  #FUNCIONA!    
      @encarcelado = false
      @nombre = nombre_dado
      @numCasillaActual = 0
      @puedeComprar = true
      @saldo = @@SaldoInicial
      @propiedades = Array.new
      @salvoconducto = nil    
    end
            
    def cancelarHipoteca(ip) #PENDIENTE DE DEPURACIÓN!!!      
      result = false
      
      if(isEncarcelado)
        return resultado
      end
      
      if(existeLaPropiedad(ip))
        propiedad = @propiedades[ip]
        cantidad = propiedad.getImporteCancelarHipoteca
        puedoGastar = puedoGastar(cantidad)
        
        if(puedoGastar)
          result = propiedad.cancelarHipoteca(self)
          
          if(result)
            Diario.instance.ocurre_evento("El jugador "+@nombre+ " cancela la hipoteca de la propiedad "+ ip.to_s)
          end
        end
      end
      result
    end
    
    def cantidadCasasHoteles
      cantidad = 0;
      for  i  in(0..@propiedades.length) 
        cantidad += @propiedades[i].numCasas
        cantidad += @propiedades[i].numHoteles
      end

      cantidad      
    end
    
    def comprar(titulo)
      result = false
      if (@encarcelado)
        return result
      end
      if (this.puedeComprar)
        precio = titulo.getPrecioCompra
        if ( puedoGastar(precio) )
          result = titulo.comprar(self)
          if(result)
            propiedades << titulo
            Diario.instance.ocurreEvento("El jugador "+@nombre+" compra la propiedad "+titulo.to_s)
          end
          @puedeComprar = false;
        end
      end
      result
    end
    
    def construirCasa(ip)  #PENDIENTE DE DEPURAR!!!
      
      result = false
      puedoEdificarCasa = false
      if(isEncarcelado)
        return result;
      else
        existe = existeLaPropiedad(ip)
        if(existe)
          propiedad = @propiedades[ip]
          puedoEdificarCasa = puedoEdificarCasa(propiedad)
          precio = propiedad.precioEdificar
          if(puedoGastar(precio)&&propiedad.numCasas<@@CasasMax)
            puedoEdificarCasa = true
          end
          if(puedoEdificarCasa)
            result = propiedad.construirCasa(self)
            if(result)
              Diario.instance.ocurre_evento("El jugador "+@nombre+" construye casa en la propiedad " +ip.to_s)
            end
          end
        end
      end
      return result
    end
    
    def construirHotel(ip)

      result = false
      if(isEncarcelado)
          return result

      elsif(existeLaPropiedad(ip)) 
        propiedad = @propiedades[ip]
        puedoEdificarHotel = puedoEdificarHotel(propiedad)
        if(puedoEdificarHotel)
          result = propiedad.construitHotel(self)
          casasPorHotel = @@CasasPorHotel
          propiedad.derruirCasa(casasPorHotel, self)
          Diario.instance.ocurre_evento("El jugador "+@nombre+" construye hotel en la propiedad "+ip.to_s)
        end
      end
      end
    end
   
    
    
    def enBancarrota
      rip = false
      if(@saldo < 0)
        rip = true
      end
    end
    
    def encarcelar(numCasillaCarcel)  #FUNCIONA!!!
      
      encarcelado = false;
      if (debeSerEncarcelado) 
        moverACasilla(numCasillaCarcel)
        @encarcelado = true
        encarcelado = @encarcelado
        Diario.instance.ocurre_evento(@nombre + " ha sido encarcelado.")
      end
      return encarcelado
     
    end
      
    def hipotecar(ip)
      result = false
        
      if (@encarcelado)
            return result
      end  
        
      if (this.existeLaPropiedad(ip))
            propiedad = @propiedades[ip]
            result = propiedad.hipotecar(self)
      end
        
      return result
    end
    
    
    def modificarSaldo(cantidad)  #FUNCIONA!!!
      
      @saldo += cantidad
      Diario.instance.ocurre_evento("El saldo ha sido modificado.")
      return true
      
    end
    
    def getCasas
       
      casas = 0;
      for i in(0..@propiedades.length)
        casas += @propiedades[i].numCasas
      end

      return casas
    end
    
    def getHoteles
      
      hoteles = 0;
      for i in(0..@propiedades.length)
        casas += @propiedades[i].numHoteles
      end

      return hoteles
      
    end
    
    def moverACasilla(numCasilla)  #FUNCIONA!!!
      
      
      ok = false
      if (!isEncarcelado) 

        @numCasillaActual = numCasilla
        puedeComprar = false
        Diario.instance.ocurre_evento(@nombre + " ha sido movido a la casilla " + numCasilla.to_s)
        ok = true

      end

      return ok
      
    end
    
    def obtenerSalvoconducto(sorpresa)  #FUNCIONA!!!
      realizado = false
      if (!isEncarcelado) 

        @salvoconducto = sorpresa
        realizado = true

      end

      return realizado
    end
    
    def paga(cantidad)  #FUNCIONA!!!
      
      return modificarSaldo(cantidad*-1)
      
    end
    
    def pagaAlquiler(cantidad)  #FUNCIONA!!!
      
      realizado = false
      if (!isEncarcelado) 
        realizado = paga(cantidad)
      end
      return realizado
      
      
    end
    
    def pagaImpuesto(cantidad) #FUNCIONA!!!
      
      realizado = false
      if (!isEncarcelado) 
        realizado = paga(cantidad)
      end
      return realizado
      
      
    end
    
    def pasaPorSalida #FUNCIONA!!!
      
      modificarSaldo(@@PasoPorSalida)
      Diario.instance.ocurre_evento(@nombre + " cobra el premio por pasar por la salida.")
      return true
      
    end
    
    def puedeComprarCasilla #FUNCIONA!!!
      
      resultado = true
      if (isEncarcelado) 
        resultado = false
      end
      @puedeComprar = resultado
      return resultado
      
    end
    
    def recibe(cantidad) #FUNCIONA!!!
      
      realizado = false
      if (!isEncarcelado) 
        realizado = modificarSaldo(cantidad)
      end
      return realizado
      
      
    end
    
    def salirCarcelPagando  #FUNCIONA!!!
      
      realizado = false;
      if (isEncarcelado && puedeSalirCarcelPagando) 

        paga(@@PrecioLibertad)
        @encarcelado = false
        Diario.instance.ocurre_evento(@nombre + " ha pagado para salir de la cárcel.")
        realizado = true

      end

      return realizado
      
    end
    
    def salirCarcelTirando #FUNCIONA!!!
      
      realizado = false
      if (Dado.instance.salgoDeLaCarcel) 

        @encarcelado = false
        Diario.instance.ocurre_evento(@nombre + " ha salido de la cárcel tirando el dado.")
        realizado = true
      end

      return realizado
      
    end
    
    def tieneAlgoQueGestionar  #FUNCIONA!!
      
      tiene = false;
      if(@propiedades.length>0)
        tiene = true
      end
      return tiene
      
    end
    
    def tieneSalvoconducto   #FUNCIONA!!
      tiene = false
      if(@salvoconducto!=nil)
        tiene = true
      end
      return tiene
      
    end
    
    def self.newJugador(original)   #FUNCIONA!!!
      jugador = Jugador.new(original.nombre)
      jugador.constructorCopia(original)
      jugador
    end
    
    def vender(ip)   #TIENE PINTA DE QUE FUNCIONA
      
      realizado = false;
      if (!isEncarcelado) 

        if (existeLaPropiedad(ip)) 

          if (@propiedades[ip].vender(self)) 
            @propiedades.delete_at(ip)
            Diario.instance.ocurre_evento(@nombre + " ha vendido la propiedad.")
            realizado = true
          end

        end

      end

      return realizado
      
    end
    
    private
    def existeLaPropiedad(ip)
      
      existe = false;
      for i in(0..@propiedades.length) 

        if (ip == i) 
          existe = true;
        end

      end
      return existe;      
    end
           
    def perderSalvoconducto  #FUNCIONA!!!
      @salvoconducto.usada
      @salvoconducto = nil
    end
    
    def puedeSalirCarcelPagando #FUNCIONA!!!
      
      puede = false
      if(@saldo>@@PrecioLibertad)
        puede = true
      end
      
      return puede
      
    end
    
    def puedoEdificarCasa(propiedad)
      
      puedo = false;
      if(!isEncarcelado&&@saldo>propiedad.PrecioEdificar&&propiedad.NumCasas<@@CasasMax)
        puedo = true
        return puedo
      
      end
    end
    
    def puedoEdificarHotel(propiedad) #DEPURAR!!!
      puedoEdificarHotel = false
      precio = propiedad.precioEdificar
      if(puedoGastar(precio))
        if(propiedad.numHoteles<@@HotelesMax)
          if(propiedad.numCasas>=@@casasPorHotel)
            puedoEdificarHotel = true
          end
        end
      end
      return puedoEdificarHotel
    end
    
    def puedoGastar(precio)  #FUNCIONA!!!     
      
      puedo = false;
      if (!isEncarcelado) 

        if (@saldo >= precio) 
          puedo = true
        end

      end

      return puedo
      
    end
    
    public
    def compareTo(otro) #FUNCIONA!!!
      
      return saldo <=> otro.saldo
    
      
    end
  
    def isEncarcelado #FUNCIONA!!!
      return @encarcelado
    end
    
    def to_s
      
      return "Nombre: #{@nombre} \n"+
              "Saldo: #{@saldo} \n"+
              "Casilla actual: #{@numCasillaActual}"
      
    end
   
    public
    def debeSerEncarcelado  #PERDERSALVOCONDUCTO NO FUNCIONA!!!
       
      if (isEncarcelado) 
        ok = false
      else 
        if (!tieneSalvoconducto) 
          ok = true      
        else 
          perderSalvoconducto
          Diario.instance.ocurre_evento(@nombre+" se libra de la cárcel")
          ok = false
        end
      end

      return ok  
    end
    
    
    def constructorCopia(original) #FUNCIONA!!!
      @encarcelado = original.encarcelado
      @numCasillaActual = original.numCasillaActual
      @puedeComprar = original.puedeComprar
      @saldo = original.saldo
      @propiedades = Array.new
      @propiedades = original.propiedades
      if(original.tieneSalvoconducto)
        @salvoconducto = Sorpresa.sorpresaEvitarCarcel(TipoSorpresa::SALIRCARCEL)
      else
        @salvoconducto = nil
      end
    end

  end
end
