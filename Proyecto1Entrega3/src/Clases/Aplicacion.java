package Clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aplicacion {
    private Hotel hotel = new Hotel();
    public static void main(String args[]) throws IOException{
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.ejecutarAplicacion();
    }
    private void ejecutarAplicacion() throws IOException {
        this.hotel.cargarUsuarios();
        ingresarUsuario();
    }
    public void ingresarUsuario(){
        String usuario = input("Ingrese su usuario");
        String contraseña = input("Ingrese su contraseña");
        Usuario user = this.hotel.getUsuario(usuario, contraseña);
        if(user == null){
            System.out.println("No se encontró el usuario.");
        }
        else{
            System.out.println("Bienvenido señor/a: " + user.getNombre() + ". Cargo: " + user.getRol()+".");}
    }
    public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
}

