package Clases;

public class Usuario {
    public Usuario(String nom, boolean[] permisosRecurso, boolean[] permisosPrograma){
        nombre = nom;
        this.permisosRecurso = permisosRecurso;
        this.permisosPrograma = permisosPrograma;
    }

    @Override
    public boolean equals(Object in){
        Instruccion aux = (Instruccion)in;
        return aux.nombre == this.nombre;
    }

    public String getUsuario (){
        return nombre;
    }

    public boolean getPermisoRecurso (int idRecurso){
        return permisosRecurso[idRecurso];
    }

    public boolean getPermisoPrograma (int idPrograma){
        return permisosPrograma[idPrograma];
    }

    public String nombre; // fork(), stackalloc(), 
    public boolean[] permisosRecurso;
    public boolean[] permisosPrograma;

    @Override
    public String toString(){
        return "usuario " + nombre;
    }
}
