package Clases;

public class Instruccion{
    public Instruccion(String nom, int cic){
        nombre = nom;
        ciclos = cic;
    }

    @Override
    public boolean equals(Object in){
        Instruccion aux = (Instruccion)in;
        return aux.nombre == this.nombre;
    }

    public String logInstruccion(){
        String log = "La instruccion " + this.nombre + " ejecutada en " + this.ciclos + " ciclos.";
        //Idea: Cuando tengamos definido un mensaje, agregar un switch que devuelve log personalizado basado en cada Comando

        return log;
    }

    public String getTipo (){
        //tipos: Pedir, Usar, Devolver
        String[] arrayInstruccion = nombre.split(" ");
        String tipo = arrayInstruccion[0];
        return tipo;
    }

    public String getIdRecurso () {

        String[] arrayInstruccion = nombre.split(" ");
        String tipo = arrayInstruccion[1];
        
        return tipo;
    }
    
    public String nombre; // fork(), stackalloc(), 
    public int ciclos;
}