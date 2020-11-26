package Clases;
import java.util.Queue;
import java.util.LinkedList;
public class RCB{
    public RCB(String n, int tiempoEspera){
        id = idNumero;
        aumentarID();
        nombre = n;
        tiempoEsperaMaximo = tiempoEspera;
        procesosAsociados = new LinkedList<>();
    }

    private int id;
    private String nombre;
    private int tiempoEsperaMaximo; // Tiempo en quantums de procesador.
    private int tiempoEsperaActual = 0;
    private Queue<PCB> procesosAsociados;
    private boolean enUso;

    public void avanzar(){
        tiempoEsperaActual++;
    }

    public boolean termino(){
        return tiempoEsperaActual == tiempoEsperaMaximo;
    }

    public boolean getDisponibilidad(){
        return procesosAsociados.isEmpty();
    }

    public String getNombre () {
        return this.nombre;
    }

    public boolean getUso (){
        return this.enUso;
    }

    public PCB getProceso(){
        return procesosAsociados.peek();
    }

    public int getId(){
        return id;
    }

    public void setTiempoEspera(int tiempo){
        tiempoEsperaMaximo = tiempo;
    }
    
    public void agregarProceso (PCB proceso){
        procesosAsociados.add(proceso);
    }

    public void removerProceso(){
        procesosAsociados.remove();
        
        if(procesosAsociados.peek() != null)
            procesosAsociados.peek().cambiarEstado("Listo");
    }

    public void setUso (boolean estado){
        this.enUso = estado;
    }

    public void setTiempoAcutal(int tiempo){
        tiempoEsperaActual = tiempo;
    }

    private static int idNumero = 0;
    private void aumentarID(){
        idNumero++;
    }

    @Override
    public String toString(){
        return "Recurso " + nombre;
    }

    @Override
    public boolean equals(Object a){
        RCB comp = (RCB)a;
        return comp.getNombre().equals(this.nombre);
    }

    public void resetID(){
        idNumero = 0;
    }
}