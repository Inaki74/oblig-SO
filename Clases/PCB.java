package Clases;
import java.util.ArrayList;
import java.util.Random;

public class PCB{
    public PCB(int ids, String[] prog, int idProg){
        id = ids;
        idPrograma = idProg;
        estadoActual = Estado.Listo;
        recursosUtilizados = new ArrayList<>();

        programa = new String[prog.length];
        Random r = new Random();
        for(int i = 0; i < prog.length; i++){
            programa[i] = prog[i]; 
            Instruccion aux = new Instruccion(prog[i], 0);
            if(!listaInstrucciones.contains(aux)){
                Instruccion inst = new Instruccion(prog[i], r.nextInt(5) + 1);
                listaInstrucciones.add(inst);
            }
        }
        usuario = null;

        System.out.println(this.toString() + " fue creado.");
    }

    private enum Estado{
        Listo, // 0
        EnEjecucion, // 1
        Bloqueado, // 2
    }


    private Estado estadoActual;
    private int id;
    private int idPrograma;
    private int linea = 0;
    private ArrayList<RCB> recursosUtilizados;
    private String[] programa;
    private ArrayList<Instruccion> listaInstrucciones = new ArrayList<Instruccion>();
    private Usuario usuario;

    public boolean ejecutar(){
        cambiarEstado("EnEjecucion");

        boolean termino = false;
        boolean bloqueado = false;
        int quantum = Procesador.Current().getQuantum();
        int cicloActual = 0;

        while(!termino && cicloActual < quantum && !bloqueado){
            // Conseguimos la instruccion, su posicion y su quantum.
            String instruccionActual = programa[linea];
            int posicionInstruccion = listaInstrucciones.indexOf(new Instruccion(instruccionActual, 0));
            int ciclosInstruccion = listaInstrucciones.get(posicionInstruccion).ciclos;

            // Logeamos la instruccion y su ejecucion
            System.out.println(listaInstrucciones.get(posicionInstruccion).logInstruccion());

            // Se interpreta la instruccion y vemos si involucra un recurso.
            interaccionRecurso(listaInstrucciones.get(posicionInstruccion));
            
            cicloActual += ciclosInstruccion;
            linea++;
            
            if(enEstado("Bloqueado")){
                bloqueado = true;
            }

            if(linea >= programa.length) termino = true;
            
        }
        return termino;
    }

    public void cambiarEstado(String estado){
        Estado estadoAnterior = estadoActual;
        switch(estado){
            case "Listo": 
                estadoActual = Estado.Listo;
                break;
            case "EnEjecucion": 
                estadoActual = Estado.EnEjecucion;
                break;
            case "Bloqueado": 
                estadoActual = Estado.Bloqueado;
                break;
            default: System.out.println(colores.ANSI_RED + "PCB/cambiarEstado: Escribiste mal el estado" + colores.ANSI_RESET);
        }

        System.out.println(colores.ANSI_CYAN + imprimirEstado(estadoActual) + razonCambio(estadoActual, estadoAnterior)  + colores.ANSI_RESET);
    }

    private String razonCambio(Estado estadoNuevo, Estado estadoAnterior){
        if(estadoNuevo == Estado.EnEjecucion && estadoAnterior == Estado.Listo) {
            return " por despacho.";
        }

        if(estadoNuevo == Estado.Listo && estadoAnterior == Estado.EnEjecucion) {
            return " por timeout.";
        }

        if(estadoNuevo == Estado.Bloqueado && estadoAnterior == Estado.EnEjecucion) {
            return " por uso/espera de recurso (E/S)";
        }

        if(estadoNuevo == Estado.Listo && estadoAnterior == Estado.Bloqueado){
            return " por finalizada ejecucion del recurso ";
        }

        return colores.ANSI_RED + "PCB/razonCambio/ERROR: Caso no ponderado de cambio de estado. Mira que paso"  + colores.ANSI_RESET;
    }

    private String imprimirEstado(Estado estado){
        String fin = "";
        switch(estado){
            case EnEjecucion:
                fin = "En Ejecucion";
                break;
            case Listo:
                fin = "Listo";
                break;
            case Bloqueado:
                fin = "Bloqueado";
                break;

        }
        return "El estado de " + this.toString() + " cambio a " + fin;
    }

    private void interaccionRecurso (Instruccion ins) {
        // Si es pedir, usar o devolver, reaccionar con recurso adecuado
        // De lo contrario, ignorar.

        String tipo = ins.getTipo();
        switch (tipo) {
            case "Pedir": 
                String nombreRecurso = ins.getIdRecurso();
                pedirRecurso(nombreRecurso);
                break;
            case "Usar": 
                String nombreRecurso1 = ins.getIdRecurso();
                usarRecurso(nombreRecurso1);
                break;
            case "Devolver": 
                String nombreRecurso2 = ins.getIdRecurso();
                devolverRecurso(nombreRecurso2);
                break;
            default: 
                return;
        }
    }

    private void pedirRecurso (String nombre) {
        Sistema sistema = Sistema.Current();
        RCB recurso = sistema.getRCB(nombre);

        if(!usuario.getPermisoRecurso(recurso.getId())){
            System.out.println(colores.ANSI_PURPLE + usuario.getUsuario() +
            " no tiene los permisos para usar el/la " +  recurso.getNombre() + colores.ANSI_RESET);

            matarProceso();
            return;
        }

        if(recurso.getDisponibilidad()){
            if(sistema.hayArista(this.id, true, recurso.getId(), false)){
                sistema.removerArista(this.id, true, recurso.getId(), false);
            }

            // r -> p
            sistema.agregarAristaAGrafo(recurso.getId(), false, this.id, true);
            if(sistema.verCiclo(recurso.getId(), false)){
                sistema.removerArista(recurso.getId(), false, this.id, true);
                System.out.println(colores.ANSI_RED + "DEADLOCK DETECTADO: Para asignacion de " + recurso.getNombre() +
                 " hacia " + this.toString() + colores.ANSI_RESET);
                // Matar proceso
                matarProceso();
                return;
            }

            System.out.println(colores.ANSI_WHITE_BOLD + recurso.getNombre() +
            " se encuentra disponible, se lo asigna al proceso " + this.id + colores.ANSI_RESET);
        }else{
            System.out.println(colores.ANSI_WHITE_BOLD + recurso.getNombre() +
            " no se encuentra disponible." + colores.ANSI_RESET);
            
            sistema.agregarAristaAGrafo(this.id, true, recurso.getId(), false);
            if(sistema.verCiclo(this.id, true)){
                sistema.removerArista(this.id, true, recurso.getId(), false);
                System.out.println(colores.ANSI_RED + "DEADLOCK DETECTADO: Para pedido de " + recurso.getNombre() +
                 " hacia " + this.toString() + colores.ANSI_RESET);
                // Matar proceso
                matarProceso();
                return;
            }

            cambiarEstado("Bloqueado");
        }

        recursosUtilizados.add(recurso);
        recurso.agregarProceso(this);
    }

    private void usarRecurso (String nombre) {
        Sistema sistema = Sistema.Current();
        RCB recurso = sistema.getRCB(nombre);

        cambiarEstado("Bloqueado");
        RCB comp = new RCB(nombre, 0);
        int indexOfComp = recursosUtilizados.indexOf(comp);
        recursosUtilizados.get(indexOfComp).setUso(true);

        if(sistema.hayArista(this.id, true, recurso.getId(), false)){
            sistema.removerArista(this.id, true, recurso.getId(), false);
            sistema.agregarAristaAGrafo(recurso.getId(), false, this.id, true);
            if(sistema.verCiclo(this.id, true)){
                sistema.removerArista(recurso.getId(), false, this.id, true);
                System.out.println(colores.ANSI_RED + "DEADLOCK DETECTADO: Para pedido de " + recurso.getNombre() +
                 " hacia " + this.toString() + colores.ANSI_RESET);
                // Matar proceso
                matarProceso();
                return;
            }
        }
    }

    private void devolverRecurso (String nombre) {
        Sistema sistema = Sistema.Current();
        RCB comp = new RCB(nombre, 0);
        int indexOfComp = recursosUtilizados.indexOf(comp);
        RCB found = recursosUtilizados.get(indexOfComp);

        sistema.removerArista(found.getId(), false, this.id, true);

        System.out.println(colores.ANSI_WHITE_BOLD + "El " + found + " fue devuelto. " + colores.ANSI_RESET);
        found.removerProceso();
        recursosUtilizados.remove(found);
    }

    private void matarProceso(){
        Sistema sistema = Sistema.Current();
        System.out.println(colores.ANSI_RED + "Se mata al " + toString() + colores.ANSI_RESET); 
        for(int i = 0; i < recursosUtilizados.size(); i++){
            RCB comp = new RCB(recursosUtilizados.get(i).getNombre(), 0);
            int indexOfComp = recursosUtilizados.indexOf(comp);
            RCB found = recursosUtilizados.get(indexOfComp);

            sistema.removerArista(found.getId(), false, this.id, true);

            System.out.println(colores.ANSI_WHITE_BOLD + "El " + found + " fue devuelto. " + colores.ANSI_RESET);
            found.removerProceso();
        }
        recursosUtilizados.clear();
        linea = programa.length - 1;
    }

    public boolean enEstado (String estado) {
        boolean mismoEstado = false;
        switch(estadoActual){
            case Listo: 
                mismoEstado = (estado == "Listo");
                break;
            case EnEjecucion:  
                mismoEstado = (estado == "EnEjecucion");
                break;
            case Bloqueado: 
                mismoEstado = (estado == "Bloqueado");
                break;
            default: System.out.println(colores.ANSI_RED + "PCB/enEstado: Pediste un estado que no existe" + colores.ANSI_RESET);
        }
        return mismoEstado;
    }

    public int getLinea(){
        return linea + 1;
    }

    public RCB getRecurso(String nombre){
        RCB comp = new RCB(nombre, 0);
        int indexOfComp = recursosUtilizados.indexOf(comp);
        return recursosUtilizados.get(indexOfComp);
    }

    public int getId(){
        return id;
    }

    public int getIdPrograma(){
        return idPrograma;
    }

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario p){
        usuario = p;
    }

    @Override
    public String toString(){
        return "Proceso " + id;
    }

    @Override
    public boolean equals(Object o){
        PCB comp = (PCB)o;
        return comp.getId() == this.id;
    }
}