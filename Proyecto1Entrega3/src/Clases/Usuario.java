package Clases;

public class Usuario {
    private String nombre;
    private String usuario;
    private String contraseña;
    private String rol;
    public Usuario(String nombre, String usuario, String contraseña, String rol){
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    public String getNombre(){
        return this.nombre;}
    public String getUsuario(){
        return this.usuario;}
    public String getContraseña(){
        return this.contraseña;}
    public String getRol(){
        return this.rol;}
}
