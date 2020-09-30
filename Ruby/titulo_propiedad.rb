# encoding: utf-8
module Civitas
  require_relative "jugador.rb"
  

class TituloPropiedad
  attr_reader :nombre
  attr_reader :numCasas
  attr_reader :numHoteles
  attr_reader :precioCompra
  attr_reader :precioEdificar
  attr_reader :propietario
  attr_reader :hipotecado

  @@factorInteresesHipoteca = 1.1
  
  def initialize(nom,ab,fr,hb,pc,pe)  #FUNCIONA!!!
    
        @nombre = nom
        @alquilerBase = ab
        @factorRevalorizacion = fr
        @hipotecaBase = hb
        @precioCompra = pc
        @precioEdificar = pe
        @propietario = nil
        @numCasas = 0
        @numHoteles = 0
        @hipotecado = false
        
  end
  
  def actualizaPropietarioPorConversion(jugador)  #PENDIENTE DE DEPURACIÓN!!!
    @propietario = jugador
  end
  
  def cancelarHipoteca(jugador)  #PENDIENTE DE DEPURACIÓN!!!
    result = false
    if(@hipotecado)
      if(esEsteElPropietario(jugador))
        @propietario.paga(getImporteCancelarHipoteca)
        @hipotecado = false
        result = true
      end
    end
    return result
  end
  
  def cantidadCasasHoteles #FUNCIONA!!!
    return @numCasas + @numHoteles    
  end
  
  def comprar(jugador)
    result = false
    if (!tienePropietario)
      this.propietario = jugador;
      result = true;
      jugador.paga(@precioCompra)
    end
    return result;
  end
  
  def construirCasa(jugador) #PENDIENTE DE DEPURACIÓN!!!
    
    result = false
    if(esEsteElPropietario(jugador))
      jugador.paga(@precioEdificar)
      @NumCasas+=1
      result = true
    end
    return result
    
  end
  
  def construirHotel(jugador) #PENDIENTE DE DEPURACIÓN!!!
    
    result = false
    if(esEsteElPropietario(jugador))
      jugador.paga(@precioEdificar)
      @NumHoteles+=1
      result = true
    end
    return result
    
  end
  
  def derruirCasas(n, jugador)  #CREEMOS QUE FUNCIONA!!!
    
      realizado = false
        if(esEsteElPropietario(jugador)&&@numCasas>=n)
            @numCasas-=n
            realizado = true
        end
        return realizado
    
  end
  
  def getImporteCancelarHipoteca  #FUNCIONA!!!
    return getImporteHipoteca*@@factorInteresesHipoteca
  end
  
  def hipotecar(jugador)
    salida = false
    if (!@hipotecado && esEsteElPropietario(jugador))
      jugador.recibe(getImporteHipoteca)
      @hipotecado = true
      salida = true
    end
        return salida;
  end
  
  def tienePropietario  #FUNCIONA!!!  
    tiene = false
    if(@propietario!=nil)
      tiene = true
    end
    
    return tiene
  end
  
  def tramitarAlquiler(jugador) #CREEMOS QUE FUNCIONA!!!
    if(@propietario!=nil)
      if(!esEsteElPropietario(jugador))  
          jugador.pagaAlquiler(getPrecioAlquiler)
          @propietario.recibe(getPrecioAlquiler)
      end
    end
  end
  
  def vender(jugador) #FUNCIONA!!!
        realizado = false
        if(esEsteElPropietario(jugador)&&!hipotecado)
        
            jugador.modificarSaldo(getPrecioVenta)
            @propietario = nil
            @numCasas = 0
            @numHoteles = 0
            realizado = true
        
        end
        
        return realizado
    
  end
  
  public
  
  def to_s   #FUNCIONA!!!
    
    if(@propietario!=nil)
      texto1 = "Su propietario es "+@propietario.nombre
    else
      texto1= "No tiene propietario"
    end
    
        if(@hipotecado)
        
            texto = " está hipotecada";
            
        else
            texto = " no está hipotecada";
        end
        return "El nombre es "+@nombre+"\n"+
                "Su alquiler base es "+@alquilerBase.to_s+"\n"+
                "Su factor de revalorización "+@factorRevalorizacion.to_s+"\n"+
                "Su hipoteca base es "+@hipotecaBase.to_s+"\n"+
                "Su precio de compra es "+@precioCompra.to_s+"\n"+
                "Su precio para edificar es "+@precioEdificar.to_s+"\n"+
                 texto1+"\n"+ 
                "Tiene "+@numCasas.to_s+" casas."+"\n"+
                "Tiene "+@numHoteles.to_s+" hoteles."+"\n"+
                "La propiedad "+texto
                
  end
  
  private
  
  def esEsteElPropietario(jugador) #CREEMOS QUE FUNCIONA!!!
    ok = false
    if(@propietario!=nil)
      if(@propietario==jugador)
        ok = true
      end
    end
    return ok
  end
  
  def getImporteHipoteca  #FUNCIONA!!!
    return @hipotecaBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
  end
  
  def getPrecioAlquiler #FUNCIONA!!!
    precio = @alquilerBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
    if(hipotecado||propietarioEncarcelado)
      precio=0
    end
    return precio
  end
  
  def getPrecioVenta  #FUNCIONA!!! 
    return @precioCompra+(@precioEdificar*@factorRevalorizacion) 
  end
  
  def propietarioEncarcelado #FUNCIONA!!! 
    esta = true
    if(@propietario==nil)
      esta = false
    elsif(!@propietario.isEncarcelado)
      esta = false
    end
    return esta  
  end

end
end

