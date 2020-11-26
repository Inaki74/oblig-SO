package Clases;

public class Vertex{
    private int id; // id del PCB o RCB
    // Si esProceso == true, entones es un proceso. Si no es un recurso.
    private boolean esProceso;

    Vertex(){}

    Vertex(int myId, boolean myEsProc){
        id = myId;
        esProceso = myEsProc;
    }

    public int getId(){
        return id;
    }

    public boolean getEsProceso(){
        return esProceso;
    }

    @Override
    public boolean equals(Object o){
        Vertex comp = (Vertex)o;
        return this.id == comp.getId() &&
               this.esProceso == comp.getEsProceso();
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.id);
    }
};
