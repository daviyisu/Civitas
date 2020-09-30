package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;
import GUI.Respuestas;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  public VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  public void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  public SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  public Respuestas comprar() {
    int opcion = this.menu("¿Quieres comprar la propiedad?", new ArrayList<> (Arrays.asList("No","Sí")));
    return (Respuestas.values()[opcion]);
  }

  public void gestionar() {
  
      this.iGestion = menu("Elige el número de gestión inmobiliaria", new ArrayList<> (Arrays.asList("Vender","Hipotecar", "Cancelar hipoteca", "Construir casa", "Construir hotel", "Terminar")));
      ArrayList<String> nombres;
      nombres = new ArrayList<>();
      nombres = this.juegoModel.getJugadorActual().getNombrePropiedades();
      this.iPropiedad = menu("Elige la propiedad en la que realizar la acción.", nombres);
  }
  
  public int getGestion(){
      
      return this.iGestion;
  }
  
  public int getPropiedad(){
      return this.iPropiedad;
  }
    

  public void mostrarSiguienteOperacion(OperacionesJuego operacion) {
  
       System.out.println(operacion);
  }


  public void mostrarEventos() {
  
      while(Diario.getInstance().eventosPendientes()){
          System.out.println(Diario.getInstance().leerEvento());
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
    }
  
  public void actualizarVista(){
      System.out.println(this.juegoModel.getJugadorActual().toString());
      System.out.println(this.juegoModel.getCasillaActual().toString());
      ArrayList<String> nombres;
      nombres = new ArrayList<>();
      nombres = this.juegoModel.getJugadorActual().getNombrePropiedades();
      for(int i=0; i<nombres.size(); i++){
          System.out.println(nombres.get(i)+" "+this.juegoModel.getJugadorActual().getPropiedades().get(i).getNumCasas());
          System.out.println("\n");
      }
  } 
}
