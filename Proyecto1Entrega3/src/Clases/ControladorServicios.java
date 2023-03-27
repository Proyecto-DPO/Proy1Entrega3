package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ControladorServicios {

    private ArrayList<Servicio> servicios;
    private ArrayList<ProductoRestaurante> menu;

    public ControladorServicios(){
        this.servicios = new ArrayList<Servicio>();
        this.menu = new ArrayList<ProductoRestaurante>();
    }
    public void cargarServiciosYMenu(File archivoServicios, File archivoMenu) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(archivoServicios))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Servicio servicio = new Servicio(split[0],split[1],split[2], Double.parseDouble(split[3]));
                this.servicios.add(servicio);}}
        try (BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                ProductoRestaurante productoRestaurante = new ProductoRestaurante(split[0],split[1],split[2],Double.parseDouble(split[5]) ,split[3],split[4]);
                this.menu.add(productoRestaurante);}}
    }
    public String mostrarServicios(){
        String retorno = "";
        for(int i=0;i<this.servicios.size();i++){
            retorno += i + ":" + this.servicios.get(i).getNombreServicio() +"'\n";
        }
        return retorno;
    }
    public String mostrarMenu(){
        String retorno = "";
        for(int i=0;i<this.menu.size();i++){
            retorno += i + ":" + this.menu.get(i).getNombreServicio() +"'\n";
        }
        return retorno;
    }
    public Servicio getServicioId(int id){
        return this.servicios.get(id);
    }
    public ProductoRestaurante getMenuId(int id){
        return this.menu.get(id);
    }
}
