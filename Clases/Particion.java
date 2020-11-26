package Clases;
import java.util.Queue;
import java.util.LinkedList;

/** Particion
     *      static int largo;
     *      Programa progAsociado;
     *      PCB[] bla = new PCB[largo];
     *      Queue<PCB> enEspera;
     * 
     *  Viene un PCB, va a la particion relacionada a su programa se intenta poner en memoria:
     *      Si hay lugar, vpi se agrega a ese lugar que queda y se agrega al scheduler.
     *      Si no hay lugar, se guarda en la cola y se saca por PEPS cuando termina algún PCB.
     */
public class Particion {

    public Particion(int l, int id){
        largo = l;
        idPrograma = id;

        memoria = new PCB[largo];
        enEspera = new LinkedList<>();
    }

    private int largo;
    private int idPrograma;
    private PCB[] memoria;
    private Queue<PCB> enEspera;

    public boolean agregarAMemoria(PCB proceso){
        for(int i = 0; i < largo; i++){
            if(memoria[i] == null){
                memoria[i] = proceso;
                System.out.println(colores.ANSI_ORANGE + "El " + proceso + " fue agregado a memoria." + colores.ANSI_RESET);
                Procesador.Current().addProceso(proceso);
                return true;
            }
        }

        enEspera.add(proceso);
        System.out.println(colores.ANSI_ORANGE + "No habia lugar disponible para " + proceso + ", fue agregado a la lista de espera." + colores.ANSI_RESET);
        return false;
    }

    public void removerDeMemoria(PCB proceso){
        for(int i = 0; i < largo; i++){
            if(memoria[i] == null) continue;
            
            if(memoria[i].equals(proceso)){
                PCB nuevo = enEspera.poll();
                memoria[i] = null;
                System.out.println(colores.ANSI_ORANGE + "El " + proceso + " fue removido de memoria." + colores.ANSI_RESET);
                if(nuevo != null){
                    agregarAMemoria(nuevo);
                }
            }
        }
    }

    public int getIdPrograma(){
        return idPrograma;
    }
}
