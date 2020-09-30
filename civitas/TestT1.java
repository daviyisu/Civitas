package civitas;
import java.util.ArrayList;

public class TestT1 {
    public static void main(String[] args){
        int pos_carcel = 5;
        MazoSorpresas mazo = new MazoSorpresas();
        Tablero t = new Tablero(pos_carcel);
        Jugador j1 = new Jugador("alberto");
        TituloPropiedad tit1 = new TituloPropiedad("prueba",200,200,200,200,200);
        j1.comprar(tit1);
        
        ArrayList<String> jugadores = new ArrayList<>();
       jugadores.add("Stelio");
       jugadores.add("Puidgemont");
       CivitasJuego juego = new CivitasJuego(jugadores);
       juego.vender(0);
        
        /*
        int numJugadores = 4;
        int tiradas = 100;
        //apartado 1
        int c1 , c2 , c3 , c4;
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        int empieza;
        for(int i=0 ; i<tiradas; i++){
            empieza = Dado.getinstance().quienEmpieza(numJugadores);
            switch(empieza){
                case 1:
                    c1++;
                    break;
                case 2:
                    c2++;
                    break;
                case 3:
                    c3++;
                    break;
                case 4:
                    c4++;
                    break;
            }
        }
        System.out.println("La cantidad de veces que ha salido el jugador 1 ha sido de "+c1+" veces");
        System.out.println("La cantidad de veces que ha salido el jugador 2 ha sido de "+c2+" veces");
        System.out.println("La cantidad de veces que ha salido el jugador 3 ha sido de "+c3+" veces");
        System.out.println("La cantidad de veces que ha salido el jugador 4 ha sido de "+c4+" veces");
        //apartado 2
        tiradas=9;
        int resultadoTirada;
        for(int i=0;i<tiradas;i++){
            if(i%2==0)
                Dado.instance.setDebug(true);
            else
                Dado.instance.setDebug(false);
            resultadoTirada = Dado.instance.tirar();
            System.out.println("Ha salido el numero "+resultadoTirada);
        }
        
        //apartado3
        System.out.println("EL ultimo resultado ha sido "+Dado.getinstance().getUltimoResultado());
        if(Dado.instance.salgoDeLaCarcel())
            System.out.println("logre salir de la carcel");
        else
            System.out.println("me quedo encerrado");
        */        
    }
}
