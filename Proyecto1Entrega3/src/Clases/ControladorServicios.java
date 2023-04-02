package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<Servicio> getServicios() {
        return servicios;
    }
    public int getIdServicio(String nombre){
        int id = 0;
        for(int i=0;i<servicios.size();i++){
            if(servicios.get(i).getNombreServicio().equals(nombre)){
                id = i;
            }
        }
        return id;
    }
    public int getIdMenu(String nombre){
        int id = 0;
        for(int i=0;i<menu.size();i++){
            if(menu.get(i).getNombreServicio().equals(nombre)){
                id = i;
            }
        }
        return id;
    }
    public ArrayList<ProductoRestaurante> getMenu() {
        return menu;
    }
    public void crearProductoRestaurante(String nombre, String tipoProducto, String rangoHoras, double precio){
        ProductoRestaurante productoRestaurante =  new ProductoRestaurante(nombre, "persona", "Restaurante", precio, rangoHoras, tipoProducto);
        menu.add(productoRestaurante);
        try {
            Files.write(Paths.get("Proyecto1Entrega3/Datos/MenuRestaurante.txt"),("\n"+nombre+";persona;Restaurante;"+tipoProducto+";"+rangoHoras+";"+precio).getBytes(), StandardOpenOption.APPEND );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cambiarPrecio(String string, String servicio, double nuevaTarifa){
        int index = 3;
        if(string.equals("MenuRestaurante")){
            index = 5;
        }
        Path archivoServicios = Paths.get("Proyecto1Entrega3/Datos/" + string +".txt");
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoServicios, StandardCharsets.UTF_8);
            for (int i = 0; i < lineas.size(); i++) {
                String[] campos = lineas.get(i).split(";");
                if (campos[0].equals(servicio)) {
                    campos[index] = Double.toString(nuevaTarifa);
                    lineas.set(i, String.join(";", campos));
                    break;
                }
            }
            Files.write(archivoServicios, lineas, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);

            try (OutputStream out = new FileOutputStream(archivoServicios.toFile())) {
                int lastIndex = lineas.size() - 1;
                for (int a = 0; a < lineas.size(); a++) {
                    String line = lineas.get(a);
                    out.write(line.getBytes(StandardCharsets.UTF_8));
                    if (a != lastIndex) {
                        out.write('\n');}
}
            }
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }     
    }
    
}
