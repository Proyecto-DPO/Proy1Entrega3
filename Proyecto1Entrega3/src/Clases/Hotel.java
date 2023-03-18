package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Hotel {
    public ArrayList<Usuario> usuarios;
    public Hotel(){
        this.usuarios = new ArrayList<Usuario>();
    }
    public void cargarUsuarios() throws IOException{
        File archivo_usuarios = new File("Proyecto1Entrega3/Datos/Usuarios.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(archivo_usuarios))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Usuario usuario = new Usuario(split[0], split[1], split[2], split[3]);
                this.usuarios.add(usuario);}}}
    public ArrayList<Usuario> getUsuarios(){
        return this.usuarios;
    }
    public Usuario getUsuario(String user, String contraseña){
        Usuario usuario = null;
        for(int i=0;i<this.usuarios.size();i++){
            if(this.usuarios.get(i).getUsuario().equals(user) && 
                    this.usuarios.get(i).getContraseña().equals(contraseña)){
                 usuario = this.usuarios.get(i);}
                }
        return usuario;
    }
}
