package Clases;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;

public class Grafo {
    public Grafo(){
        adjVertices = new HashMap<>();
        vertices = new LinkedList<Vertex>();
        cantVertices = 0;
    }

    private HashMap<Vertex, List<Vertex>> adjVertices;
    private List<Vertex> vertices;
    private int cantVertices;

    public boolean hayArista(Vertex desde, Vertex hasta){
        List<Vertex> e = adjVertices.get(desde);

        if(e == null) return false;

        for(Vertex v: e){
            if(v.equals(hasta)) return true;
        }

        return false;
    }

    public List<Vertex> getVertices(){
        return vertices;
    }

    public List<Vertex> getAdjacents(Vertex v){
        return adjVertices.get(v);
    }

    public int getCantVertices(){
        return cantVertices;
    }

    public void addVertex(Vertex v) {
        // List<Vertex> adj = new LinkedList<Vertex>();
        // adj.add(new Vertex(-1, true)); // Agregar un anzuelo
        adjVertices.putIfAbsent(v, new LinkedList<Vertex>());
        vertices.add(v);
        cantVertices++;
    }
     
    public void removeVertex(Vertex v) {
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(v);
        vertices.remove(v);
    }

    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(v2);
    }

    public void removeEdge(Vertex v1, Vertex v2) {
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    // Esto solo funciona en esta simulacion, no como concepto en grafos en general.
    public boolean esCiclico(Vertex principio) { 
        boolean primeraVez = true;
        Queue<Vertex> q = new LinkedList<>();
        q.add(principio);

        while(!q.isEmpty()){
            Vertex top = q.remove();

            if(top.equals(principio) && !primeraVez){
                return true;
            } 

            primeraVez = false;

            List<Vertex> adj = getAdjacents(top);
            if(adj != null){
                for(Vertex v: adj){
                    q.add(v);
                }
            }
        }
        return false; 
    }

    public void vaciarGrafo() { //lo deja limpio para el proximo caso de prueba
        adjVertices.clear();
        vertices.clear();
        cantVertices = 0;
    }
}
